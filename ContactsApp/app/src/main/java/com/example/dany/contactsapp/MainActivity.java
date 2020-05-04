package com.example.dany.contactsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Players";

    private final String FILENAME = "contact_data";

    private ImageView mPlayerImage;
    private TextView mPlayerNameTextView;
    private TextView mPlayerSkillTextView;
    private RatingBar mPlayerRatingSkillRatingBar;
    private Button btnNextPlayer;
    private Button btnPreviousPlayer;

    private EditText mPlayerLocationEditText;
    private TextView mPlayerLocationTextView;


    private final Players[] mPlayers = new Players[]{
            new Players(R.string.andres_name, R.drawable.andres),
            new Players(R.string.messi_name, R.drawable.messi),
            new Players(R.string.suarez_name, R.drawable.suarez),
            new Players(R.string.cristiano_name, R.drawable.ronaldo)
    };


    //first player to show iniesta
    private int mCurrentIndex = 0;
    private final int NUM_PLAYERS = 4;

    private static final String KEY_CONTACT_INDEX = "contact_index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);



        for(int i = 0; i < NUM_PLAYERS; i++) {
            //key: getString will obtain actual string the resource points ID to, append "_location"
            //value: the contact's current location (string)
            mPlayers[i].setmLocation(sharedPref.getString(getString(mPlayers[i].getmPlayerNameResId()) + "_location", ""));
            mPlayers[i].setSkill(sharedPref.getFloat(getString(mPlayers[i].getmPlayerNameResId()) + "_skill",0));

            //key: getString will obtain actual string the resource ID points to, append "_found"
            //value: whether contact has been found (boolean)

        }

        //update with saved state whe re-creating this activity

        if (savedInstanceState != null)
            mCurrentIndex = savedInstanceState.getInt(KEY_CONTACT_INDEX, 0);


        mPlayerImage = (ImageView) findViewById(R.id.mPlayerImageView);
        mPlayerNameTextView = (TextView) findViewById(R.id.playerNameTextView);
        btnNextPlayer = (Button) findViewById(R.id.nextButton);
        btnPreviousPlayer = (Button) findViewById(R.id.prevButton);
        mPlayerLocationEditText = (EditText) findViewById(R.id.positionEditText);
        mPlayerLocationTextView = (TextView) findViewById(R.id.contactPositionTextView);
        mPlayerSkillTextView = (TextView) findViewById(R.id.contactSkillTextView);

        mPlayerRatingSkillRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        LayerDrawable stars = (LayerDrawable) mPlayerRatingSkillRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        mPlayerLocationEditText.addTextChangedListener(nameEditTextWatcher);

        mPlayerLocationEditText.setText(mPlayers[mCurrentIndex].getmLocation());


        update();

        mPlayerRatingSkillRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mPlayers[mCurrentIndex].setSkill(v);
                update();
            }
        });

        btnNextPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % 4;

                update();
                mPlayerLocationEditText.setText(mPlayers[mCurrentIndex].getmLocation());

            }
        });

        btnPreviousPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex - 1);

                if (mCurrentIndex == -1)
                    mCurrentIndex = 3;


                update();

                mPlayerLocationEditText.setText(mPlayers[mCurrentIndex].getmLocation());

            }
        });


    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause()");

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        for (int i=0; i<NUM_PLAYERS; i++)
        {
            editor.putString(getString(mPlayers[i].getmPlayerNameResId())+"_location", mPlayers[i].getmLocation());
            editor.putFloat(getString(mPlayers[i].getmPlayerNameResId())+"_skill", mPlayers[i].getSkill());
            Log.d(TAG, "Writing location, found: " + mPlayers[i].getmLocation());

        }

        editor.apply();

    }



    private final TextWatcher nameEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            mPlayers[mCurrentIndex].setmLocation(charSequence.toString());
            update();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(KEY_CONTACT_INDEX, mCurrentIndex);
    }














    private void update(){
        mPlayerImage.setImageResource(mPlayers[mCurrentIndex].getmImageResId());
        mPlayerNameTextView.setText(mPlayers[mCurrentIndex].getmPlayerNameResId());
        mPlayerRatingSkillRatingBar.setRating(mPlayers[mCurrentIndex].getSkill());

        mPlayerLocationTextView.setText(mPlayers[mCurrentIndex].getmLocation());
        mPlayerSkillTextView.setText(String.valueOf(mPlayers[mCurrentIndex].getSkill()));
    }







}
