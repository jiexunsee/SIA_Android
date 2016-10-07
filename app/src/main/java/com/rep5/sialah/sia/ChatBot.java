package com.rep5.sialah.sia;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreePaymentActivity;
import com.braintreepayments.api.models.PaymentMethodNonce;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

public class ChatBot extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, FlightCalendarFragment.OnFlightCalendarInteractionListener{

    private static ChatBot instance;
    public static ImageView title;
    public static boolean wifiState = false;
    private static final int REQUEST_CODE = 99;

    private static final String TAG = "ChatBot";
    public static final String MESSAGES_CHILD = "messages";
    private static final int REQUEST_INVITE = 1;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 10;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;

    private EditText mMessageEditText;
    private ImageButton sendButton;

    // Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private FlightCalendarFragment mFlightCalendarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.siabglogo);

        SendToken.send();

        new Thread(new Runnable() {
            @Override
            public void run() {
                RestClient.getTarget("http://lhhong.asuscomm.com:8080/sia/signals/start").request().get();
            }
        }).start();

        instance = this;
        title = (ImageView) findViewById(R.id.convoTitle);

        setContentView(R.layout.activity_chat_bot);

        FirebaseMessaging.getInstance().subscribeToTopic("sia");

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = ANONYMOUS;

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        // Initialize Firebase Remote Config.
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // Define Firebase Remote Config Settings.
        FirebaseRemoteConfigSettings firebaseRemoteConfigSettings =
                new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build();

        // Define default config values. Defaults are used when fetched config values are not
        // available. Eg: if an error occurred fetching values from the server.
        Map<String, Object> defaultConfigMap = new HashMap<>();
        defaultConfigMap.put("friendly_msg_length", 10L);

        // Apply config settings and default values.
        mFirebaseRemoteConfig.setConfigSettings(firebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaults(defaultConfigMap);

        // Fetch remote config.
        fetchConfig();


        sendButton = (ImageButton) findViewById(R.id.realSendButton);
        sendButton.setEnabled(false);
        sendButton.setImageResource(R.drawable.sendbutton_faded);

        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mSharedPreferences
                .getInt(CodelabPreferences.FRIENDLY_MSG_LENGTH, DEFAULT_MSG_LENGTH_LIMIT))});
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    sendButton.setEnabled(true);
                    sendButton.setImageResource(R.drawable.sendbutton);
                } else {
                    sendButton.setEnabled(false);
                    sendButton.setImageResource(R.drawable.sendbutton_faded);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = mMessageEditText.getText().toString();
                StaticClass.messageHistory.add(new ChatBotMessage("user", text));

                //DISPLAY MESSAGE
                mMessageEditText.setText("");

                View messageSendView = getLayoutInflater().inflate(R.layout.message_send_view, null, false);
                TextView textView = (TextView)messageSendView.findViewById(R.id.send_text_view);
                textView.setText(text);

                ViewGroup chatbubbles = (ViewGroup) findViewById(R.id.conversation);
                chatbubbles.addView(messageSendView);

                messageSendView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MessageOptions.class);
                        startActivity(intent);
                        return true;
                    }
                });

                Log.d(text, text);


                // send message to heok hong
                SendText send = new SendText();
                send.setText(text);
                send.setUrl("http://lhhong.asuscomm.com:8080/sia/messages/");
                Thread thread = new Thread(send);
                thread.start();

                final ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
                scroll.post(new Runnable()
                {
                    public void run()
                    {
                        scroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in.
        // TODO: Add code to check if user is signed in.
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fresh_config_menu:
                fetchConfig();
                return true;
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    // Fetch the config to determine the allowed length of messages.
    public void fetchConfig() {
        long cacheExpiration = 0; // 1 hour in seconds
        // If developer mode is enabled reduce cacheExpiration to 0 so that
        // each fetch goes to the server. This should not be used in release
        // builds.
        if (mFirebaseRemoteConfig.getInfo().getConfigSettings()
                .isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Make the fetched config available via
                        // FirebaseRemoteConfig get<type> calls.
                        mFirebaseRemoteConfig.activateFetched();
                        applyRetrievedLengthLimit();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // There has been an error fetching the config
                        Log.w(TAG, "Error fetching config: " +
                                e.getMessage());
                        applyRetrievedLengthLimit();
                    }
                });
    }


    /**
     * Apply retrieved length limit to edit text field.
     * This result may be fresh from the server or it may be from cached
     * values.
     */
    private void applyRetrievedLengthLimit() {
        Long friendly_msg_length =
                mFirebaseRemoteConfig.getLong("friendly_msg_length");
        mMessageEditText.setFilters(new InputFilter[]{new
                InputFilter.LengthFilter(friendly_msg_length.intValue())});
        Log.d(TAG, "FML is: " + friendly_msg_length);
    }

    public void addReceivedMessage(SiaMessage siaMessage) {
        String message = siaMessage.getMessage();

        StaticClass.messageHistory.add(new ChatBotMessage("Sia", message));

        ViewGroup chatbubbles = (ViewGroup) findViewById(R.id.conversation);

        if (siaMessage.getContext().getSiaData().getFakeBooking()) {
            FakeBooking(chatbubbles, message);
        }

        else {

            if (!siaMessage.getContext().getSiaData().getIsCusService()) {

                ImageView title = (ImageView) ChatBot.getChatBotInstance().findViewById(R.id.convoTitle);
                title.setImageResource(R.drawable.maintitle);
                LinearLayout topBar = (LinearLayout) ChatBot.getChatBotInstance().findViewById(R.id.topBar);
                topBar.setBackgroundResource(R.drawable.toolbar_gradient);
                EditText editText = (EditText) ChatBot.getChatBotInstance().findViewById(R.id.messageEditText);
                editText.setHint("Type a message to Sia");

                View messageReplyView = getLayoutInflater().inflate(R.layout.message_receive_view, null, false);
                TextView messageTextView = (TextView) messageReplyView.findViewById(R.id.receive_text_view);
                messageTextView.setText(message);

                chatbubbles.addView(messageReplyView);

                messageReplyView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MessageOptions.class);
                        startActivity(intent);
                        return true;
                    }
                });

            }

            else {

                ImageView title = (ImageView) ChatBot.getChatBotInstance().findViewById(R.id.convoTitle);
                title.setImageResource(R.drawable.title_customerservice);
                LinearLayout topBar = (LinearLayout) ChatBot.getChatBotInstance().findViewById(R.id.topBar);
                topBar.setBackgroundColor(Color.parseColor("#A98160"));
                EditText editText = (EditText) ChatBot.getChatBotInstance().findViewById(R.id.messageEditText);
                editText.setHint("Talk to Customer Service");

                View messageReplyView = getLayoutInflater().inflate(R.layout.custserv_receive_view, null, false);
                TextView messageTextView = (TextView) messageReplyView.findViewById(R.id.receive_text_view);
                messageTextView.setText(message);

                chatbubbles.addView(messageReplyView);

                messageReplyView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MessageOptions.class);
                        startActivity(intent);
                        return true;
                    }
                });
            }

            if (siaMessage.getContext().getSiaData().getNeedsCustomerService()) {
                StaticClass.SendMessageHistory();

            }
        }

        final ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.post(new Runnable()
        {
            public void run()
            {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });

    }

    public void ShowDropDownMenu(View view) {
        Intent intent = new Intent(this, DropDownMenu.class);
        startActivity(intent);
    }

    public void ShowPlaneChat(View view) {
        Intent intent = new Intent(this, PlaneChat.class);
        startActivity(intent);
    }


    public void FakeBooking(ViewGroup chatbubbles, String message) {
        View chooseButtonView = getLayoutInflater().inflate(R.layout.message_single_button_view, null, false);
        TextView messageTextView = (TextView)chooseButtonView.findViewById(R.id.message_single_action_text_view);
        TextView chooseButton = (TextView)chooseButtonView.findViewById(R.id.message_single_action_button);

        messageTextView.setText(message);

        chatbubbles.addView(chooseButtonView);

        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlightCalendarDialog();
            }
        });

        messageTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageOptions.class);
                startActivity(intent);
                return true;
            }
        });

        final ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.post(new Runnable()
        {
            public void run()
            {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });

    }

    public void ChooseFlight() {
        View messageReplyView = getLayoutInflater().inflate(R.layout.message_receive_view, null, false);
        TextView messageTextView = (TextView)messageReplyView.findViewById(R.id.receive_text_view);
        messageTextView.setText("I have found the following flights on your chosen date. Click to select one of them:");

        ViewGroup chatbubbles = (ViewGroup) findViewById(R.id.conversation);
        chatbubbles.addView(messageReplyView);

        messageReplyView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageOptions.class);
                startActivity(intent);
                return true;
            }
        });

        View choice1 = getLayoutInflater().inflate(R.layout.flight_option1, null, false);
        chatbubbles.addView(choice1);

        View choice2 = getLayoutInflater().inflate(R.layout.flight_option2, null, false);
        chatbubbles.addView(choice2);


        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(PlaneChat.getPaymentRequest("Flight from Singapore to San Francisco", "05 December 2016, 20:00", "$2,031.00")
                        .getIntent(ChatBot.this), REQUEST_CODE);
            }
        });

        choice1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageOptions.class);
                startActivity(intent);
                return true;
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(PlaneChat.getPaymentRequest("Flight SQ32 - Singapore to San Francisco", "05 December 2016, 18:30", "$1,971.80")
                        .getIntent(ChatBot.this), REQUEST_CODE);
            }
        });

        choice2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MessageOptions.class);
                startActivity(intent);
                return true;
            }
        });

        final ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.post(new Runnable()
        {
            public void run()
            {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(
                        BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE
                );

                PlaneChat.showPaymentDialog(this, "You have successfully purchased: Flight SQ32 - Singapore to San Francisco.").show();

                View messageReplyView = getLayoutInflater().inflate(R.layout.message_receive_view, null, false);
                TextView messageTextView = (TextView)messageReplyView.findViewById(R.id.receive_text_view);
                messageTextView.setText("Hurray, you're on your way to San Francisco!");

                messageReplyView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MessageOptions.class);
                        startActivity(intent);
                        return true;
                    }
                });

                ViewGroup chatbubbles = (ViewGroup) findViewById(R.id.conversation);
                chatbubbles.addView(messageReplyView);

                final ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
                scroll.post(new Runnable()
                {
                    public void run()
                    {
                        scroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }
    }


    public static ChatBot getChatBotInstance() {
        return instance;
    }


    //for hiding keyboard when popup comes out
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //Fragment Methods
    private void startFlightCalendarDialog() {
        mFlightCalendarFragment = new FlightCalendarFragment();
        mFlightCalendarFragment.show(getSupportFragmentManager(), "FragmentManager");
    }


    @Override
    public void onFlightCalendarConfirm() {
        mFlightCalendarFragment.dismiss();
        ChooseFlight();
        hideKeyboard(this);
    }

    @Override
    public void onFlightCalendarCancel() {
        mFlightCalendarFragment.dismiss();
        hideKeyboard(this);
    }

    public void focus(View view) {
        final ScrollView scroll = (ScrollView) findViewById(R.id.scrollView);
        scroll.post(new Runnable()
        {
            public void run()
            {
                scroll.fullScroll(View.FOCUS_DOWN);
            }
        });

    }
}
