package com.rep5.sialah.sia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

public class FlightCalendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_calendar);

        initializeCalendar();
    }

    public void initializeCalendar() {
        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
    }




    public void GoBack(View view) {
        this.onBackPressed();
        ChatBot.getChatBotInstance().ChooseFlight();
    }
}
