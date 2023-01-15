package com.example.era_project_mad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText t1;
    TextView tv;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.t1);
        tv = findViewById(R.id.tv);
        btnStart = findViewById(R.id.btn_start);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        int MY_CAMERA_REQUEST_CODE = 100;
        int MY_READ_CONTACTS_REQUEST_CODE=200;
        int MY_CALL_REQUEST_CODE=300;
        int MY_READ_CALL_LOGS_REQUEST_CODE = 300;
        int MY_WRITE_CALENDER_REQUEST_CODE=400;
        int MY_READ_CALENDER_REQUEST_CODE =500;

        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
        }
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, MY_READ_CONTACTS_REQUEST_CODE);
        }
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, MY_CALL_REQUEST_CODE);
        }
        if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, MY_READ_CALL_LOGS_REQUEST_CODE);
        }
        if (checkSelfPermission(Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_CALENDAR}, MY_WRITE_CALENDER_REQUEST_CODE);
        }
        if (checkSelfPermission(Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, MY_READ_CALENDER_REQUEST_CODE);
        }

        btnStart.setOnClickListener(view -> startprocess());
    }

    public void startprocess() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Tap To Activate ERA");
        try {
            startActivityForResult(intent, 922);
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error in code", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 922 && resultCode == RESULT_OK)
        {
            ArrayList<String> commands = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String raw = commands.get(0).toString();
            String[] arrSplit = raw.split("");

            /*
            //insert
            if (arrSplit[0].equals("insert"))
            {
                if (arrSplit.length==3)
                {
                    t1.setText(arrSplit[1] + arrSplit[2]);
                }
                else
                {
                    t1.setText(arrSplit[1].toString());
                }
                FirebaseDatabase.getInstance().getReference("commandsrecords").push().setValue(t1.getText().toString());
                Toast.makeText(getApplicationContext(), "Record inserted to firebase database", Toast.LENGTH_SHORT).show();
            }

            //fetch
            if (arrSplit[0].equals("fetch"))
            {
                tv.setText("");
                DatabaseReference dr = FirebaseDatabase.getInstance().getReference("commandsrecords");
                dr.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren())
                        {
                            tv.append(ds.getValue(String.class) + "\n");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            //Delete
            if (arrSplit[0].equals("clear"))
            {
                t1.setText("");
                tv.setText("");
            }
            if(arrSplit[0].equals("hello"))
            {
                t1.setText("Hello Sir");
            }
            //Update
            */



            //for camera
            if (raw.equals("open camera"))
            {

                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "No camera permission given", Toast.LENGTH_LONG).show();
            }
            //for read contacts
            if (raw.equals("read contacts"))
            {

                if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.action.READ_CONTACTS");
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "No camera permission given", Toast.LENGTH_LONG).show();
            }
            //for make a call
            if (raw.equals("make a call"))
            {

                if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.action.CALL_PHONE");
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "No calls permission given", Toast.LENGTH_LONG).show();
            }
            //for my call logs
            if (raw.equals("show my call logs"))
            {

                if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.action.READ_CALL_LOG");
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "No read call logs permission given", Toast.LENGTH_LONG).show();
            }
            //for schedule calender
            if (raw.equals("schedule a date"))
            {

                if (checkSelfPermission(Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.action.WRITE_CALENDAR");
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "No write calender permission given", Toast.LENGTH_LONG).show();
            }
            //for read schedule calender
            if (raw.equals("show my schedule"))
            {

                if (checkSelfPermission(Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent("android.action.READ_CALENDAR");
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "No read calender permission given", Toast.LENGTH_LONG).show();
            }
        }
    }
}