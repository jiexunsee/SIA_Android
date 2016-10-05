package com.rep5.sialah.sia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;

import org.json.JSONObject;

public class DropDownMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down_menu);
    }

    public void GoBack(View view) {
        this.onBackPressed();
    }

    public void ShowTicket(View view) {
        Intent intent = new Intent (this, Ticket.class);
        startActivity(intent);
    }

    public void ContactCustServ(View view) {
        StaticClass.SendMessageHistory();
    }
}
