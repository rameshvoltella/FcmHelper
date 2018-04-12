package com.newfcm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ramz.fcmhelperlib.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
if(FirebaseHelper.getFcmToken()!=null)
{
    Log.d("whats my token","mine>>"+FirebaseHelper.getFcmToken());
}
else
{
    Log.d("whats my token","bullshit man");

}
    }
}
