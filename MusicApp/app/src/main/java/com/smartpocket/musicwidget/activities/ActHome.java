package com.smartpocket.musicwidget.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smartpocket.musicwidget.R;

public class ActHome extends AppCompatActivity {


    Button btnOnline, btnOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);

        btnOnline = (Button)findViewById(R.id.btnOnline);
        btnOffline = (Button)findViewById(R.id.btnOffline);




        btnOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(ActHome.this, ActOnlineList.class);
                startActivity(i1);
            }
        });


        btnOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(ActHome.this, ActOfflineSong.class);
                startActivity(i1);
            }
        });



    }
}
