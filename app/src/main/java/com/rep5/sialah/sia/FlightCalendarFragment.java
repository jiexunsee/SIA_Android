package com.rep5.sialah.sia;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by jiarui on 6/10/16.
 */

public class FlightCalendarFragment extends DialogFragment {
    public interface OnFlightCalendarInteractionListener {
        void onFlightCalendarConfirm();
        void onFlightCalendarCancel();
    }

    OnFlightCalendarInteractionListener mListener;

    @Override
    public void onAttach(android.content.Context context) {
        super.onAttach(context);

        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (OnFlightCalendarInteractionListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement OnFeedbackDialogInteractionListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.activity_flight_calendar, null);

        builder.setView(view);


        Button submitButton = (Button) view.findViewById(R.id.feedback_submit_button);
        Button cancelButton = (Button) view.findViewById(R.id.feedback_cancel_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFlightCalendarConfirm();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFlightCalendarCancel();
            }
        });

        return builder.create();
    }
}
