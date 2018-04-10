package com.example.guest.spiritguide.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guest.spiritguide.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SolutionsActivity extends AppCompatActivity  implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SHAKE_THRESHOLD = 3000;
    private final Random r = new Random();
    private final String[] magicanswers = {"yes", "no", "maybe", "count on it", "cannot tell now", "ask again later", "no doubt"};
    private TextView text;
    private SharedPreferences mSharedPreferences;
    private String mRecentQuestion;
    @BindView(R.id.questionTextView) TextView mQuestionTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solutions);
        ButterKnife.bind(this);
        text = (TextView) findViewById(R.id.resultTextView);

        Intent intent = getIntent();
        String question = intent.getStringExtra("question");

        mQuestionTextView.setText("Your Question: " + question);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mRecentQuestion = mSharedPreferences.getString(Constants.PREFERENCES_QUESTION_KEY, null);
        Log.d("SHAREDPREF", mRecentQuestion);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mSensor, mSensorManager.SENSOR_DELAY_NORMAL);



    }

    @Override
    public void onSensorChanged(SensorEvent event){
        Sensor sensor = event.sensor;

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - last_x - last_y - last_z)/diffTime * 10000;

                if(speed > SHAKE_THRESHOLD) {
                    Toast.makeText(SolutionsActivity.this, "shake works", Toast.LENGTH_LONG).show();

                    eightball();

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }


            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void eightball() {
        int rand = r.nextInt(magicanswers.length);
        text.setText(magicanswers[rand]);
    }



}
