package com.example.era_project_mad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        t1=findViewById(R.id.t1);
        tv=findViewById(R.id.tv);
        btnStart=findViewById(R.id.btn_start);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startprocess();
            }
        });
    }

    public void startprocess()
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Tap To Activate ERA");
        try {
            startActivityForResult(intent, 922);
        }catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Error in code", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 922 && resultCode==RESULT_OK)
        {
            ArrayList<String> commands = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String raw = commands.get(0);
            String[] arrSplit = raw.split("");
                if (arrSplit[0].equals("insert"))
                {
                    if (arrSplit.length == 3)
                       t1.setText(arrSplit[1]+arrSplit[2]);
                    else
                        t1.setText(arrSplit[1]);
                    FirebaseDatabase.getInstance().getReference("commandsRecords").push().setValue(t1.getText().toString());
                    Toast.makeText(getApplicationContext(), "Inserted to Firebase", Toast.LENGTH_LONG).show();
                }
                if (arrSplit[0].equals("fetch"))
                {
                    tv.setText("");
                    DatabaseReference dr = FirebaseDatabase.getInstance().getReference("commandsRecords");
                    dr.addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapShot){
                                for(DataSnapshot ds: snapShot.getChildren()){
                                    tv.append(ds.getValue(String.class)+ "\n");
                                }
                            }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error){

                        }

                    });
                }
                if (arrSplit[0].equals("clear")){
                    t1.setText("");
                    tv.setText("");
                }
                if (arrSplit[0].equals("hello era"))
                {
                    t1.setText("Hello Professor");
                }
        }
    }
}