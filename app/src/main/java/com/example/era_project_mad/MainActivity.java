package com.example.era_project_mad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

/*
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
*/
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText t1;
    TextView tv;
    Button btnStart;
    private File item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.txt_HMIHY);
        btnStart = findViewById(R.id.btn_start);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        int MY_CAMERA_REQUEST_CODE = 100;
        int MY_READ_CONTACTS_REQUEST_CODE=200;
        int MY_CALL_REQUEST_CODE=300;
        int MY_READ_CALL_LOGS_REQUEST_CODE = 300;
        int MY_WRITE_CALENDER_REQUEST_CODE=400;
        int MY_READ_CALENDER_REQUEST_CODE =500;
        int MY_READ_FILES_REQUEST_CODE=600;


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
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, MY_READ_FILES_REQUEST_CODE);
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
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "No contact reading permission given", Toast.LENGTH_LONG).show();
            }
            //for make a call
            if (raw.equals("make a call"))
            {
                String number=t1.getText().toString();
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel" + number));
                    startActivity(callIntent);
                }
                else
                    Toast.makeText(getApplicationContext(), "No calls permission given", Toast.LENGTH_LONG).show();
            }
            //for my call logs
            if (raw.equals("show my call logs"))
            {

                if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://call_log/calls"));
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "No read call logs permission given", Toast.LENGTH_LONG).show();
            }
            //for schedule calender
            if (raw.equals("schedule a date"))
            {

                if (checkSelfPermission(Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                    Calendar cal = Calendar.getInstance();
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra("beginTime", cal.getTimeInMillis());
                    intent.putExtra("allDay", true);
                    intent.putExtra("rrule", "FREQ=YEARLY");
                    intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                    intent.putExtra("title", "A Test Event from android app");
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "No write calender permission given", Toast.LENGTH_LONG).show();
            }
            //for read schedule calender
            if (raw.equals("show my schedule"))
            {

                if (checkSelfPermission(Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    startActivity(intent);
                }
                else
                Toast.makeText(getApplicationContext(), "No read calender permission given", Toast.LENGTH_LONG).show();
            }
            //for read files
             if (raw.equals("show reports"))
            {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW);
                    myIntent.setData(Uri.fromFile(item));
                    Intent j = Intent.createChooser(myIntent, "Choose an application to open with:");
                    startActivity(j);
                }
                else
                Toast.makeText(getApplicationContext(), "No read calender permission given", Toast.LENGTH_LONG).show();
            }



        }
    }
}