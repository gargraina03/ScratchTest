package com.gdgvitvellore.scratchtest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cooltechworks.views.ScratchTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    private TextView placeholderTextView;
    private ScratchTextView scratchView;
    private Button boolFlipper;
    private Boolean flipSwitch = false;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = database.getReference();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    public static final String CouponKey = "couponKey";
    public static final String checkGeneratedKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placeholderTextView = (TextView) findViewById(R.id.placeholder_text);
        scratchView = (ScratchTextView) findViewById(R.id.scratchView);
        boolFlipper = (Button) findViewById(R.id.bool_flipper);

        scratchView.setVisibility(View.GONE);   //intiallly set to false

        SharedPreferences sharedpreferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (sharedpreferences.contains(CouponKey)) {
            scratchView.setText(sharedpreferences.getString(CouponKey, ""));
        }
        else{
            String key = scratchView.getText().toString();  //key to be taken from Firebase
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(CouponKey, key);
            editor.putBoolean(checkGeneratedKey,true);
            editor.commit();
        }

        boolFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipSwitch = !flipSwitch;

                if(flipSwitch) {
                    placeholderTextView.setVisibility(View.GONE);
                    scratchView.setVisibility(View.VISIBLE);
                } else {
                    placeholderTextView.setVisibility(View.VISIBLE);
                    scratchView.setVisibility(View.GONE);
                }
            }

        });

        scratchView.setRevealListener(new ScratchTextView.IRevealListener() {
            @Override
            public void onRevealed(ScratchTextView scratchTextView) {

            }

            @Override
            public void onRevealPercentChangedListener(ScratchTextView scratchTextView, float v) {
                if(v>0.5) {
                    scratchTextView.reveal();
                }
            }
        });
    }
}
