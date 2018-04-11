package com.example.guest.spiritguide.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.example.guest.spiritguide.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;

    private DatabaseReference mAskedQuestionReference;
    private ValueEventListener mAskedQuestionReferenceListener;

    @BindView(R.id.getResponseButton) Button mGetResponseButton;
    @BindView(R.id.userQuestionEditText) EditText mUserQuestionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mAskedQuestionReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_ASKED_QUESTION);

       mAskedQuestionReferenceListener = mAskedQuestionReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    String question = questionSnapshot.getValue().toString();
                    Log.d("Question updated", "question: " + question);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();


        mGetResponseButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if (v == mGetResponseButton) {
            String question = mUserQuestionEditText.getText().toString();


            saveQuestionToFirebase(question);


//            if(!(question).equals("")) {
//                addToSharedPreferences(question);
//            }



            if (question.equals("")) {
                Toast.makeText(MainActivity.this, "Please type a question", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(MainActivity.this, SolutionsActivity.class);
                intent.putExtra("question", question);
                startActivity(intent);
            }
        }


    }

    private void saveQuestionToFirebase(String question) {
        mAskedQuestionReference.push().setValue(question);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAskedQuestionReference.removeEventListener(mAskedQuestionReferenceListener);
    }



//    private void addToSharedPreferences(String question) {
//        mEditor.putString(Constants.PREFERENCES_QUESTION_KEY, question).apply();
//    }
}


