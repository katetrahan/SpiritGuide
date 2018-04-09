package com.example.guest.spiritguide.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.guest.spiritguide.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SolutionsActivity extends AppCompatActivity {
    @BindView(R.id.questionTextView) TextView mQuestionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solutions);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String question = intent.getStringExtra("question");

        mQuestionTextView.setText("Your Question: " + question);

    }




}
