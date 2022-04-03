package com.codexpert;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;


public class EntrainementActivity extends AppCompatActivity {

    HashMap<Integer, Questions> questions;
    HashMap<Integer, ArrayList<Integer>> reponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        questions = (HashMap<Integer, Questions>) intent.getSerializableExtra(String.valueOf(R.string.questions));
        reponses = new HashMap<Integer, ArrayList<Integer>>();
        final int[] index = {1};
        setContentView(R.layout.activity_entrainement);
        ProgressBar pb = (ProgressBar)findViewById(R.id.chargebar);
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", 0, 100);
        animation.setDuration(25000);
        animation.setInterpolator(new LinearInterpolator());
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Questions question = questions.get(index[0]);

                ImageFragment ifrag = new ImageFragment();
                QuestionFragment qfrag = new QuestionFragment();

                if(question.question.size()>0 && question.question.size()<=2){
                    if(question.question.size()==2){

                    }

                }

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //do something when the countdown is complete
                index[0]++;
                if(index[0]<=questions.size()){
                    animation.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
        animation.start();

        Button suivant = (Button) findViewById(R.id.suivant);
        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation.end();
            }
        });
    }

}
