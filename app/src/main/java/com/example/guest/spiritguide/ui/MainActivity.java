package com.example.guest.spiritguide.ui;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.guest.spiritguide.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.getResponseButton) Button mGetResponseButton;
    @BindView(R.id.userQuestionEditText) EditText mUserQuestionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mGetResponseButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if (v == mGetResponseButton) {
            String question = mUserQuestionEditText.getText().toString();


            if (question.equals("")) {
                Toast.makeText(MainActivity.this, "Please type a question", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(MainActivity.this, SolutionsActivity.class);
                intent.putExtra("question", question);
                startActivity(intent);
            }
        }
    }
}
