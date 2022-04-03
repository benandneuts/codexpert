package com.codexpert;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;


public class EntrainementActivity extends AppCompatActivity implements QuestionFragmentCallBack {

    HashMap<Integer, Questions> questions;
    HashMap<Integer, ArrayList<Integer>> reponses;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    QuestionFragment qfrag;
    ImageFragment ifrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // Cette ligne cache la barre implémenté par défaut
        setContentView(R.layout.activity_entrainement);
        Intent intent = getIntent();

        questions = (HashMap<Integer, Questions>) intent.getSerializableExtra(String.valueOf(R.string.questions));

        reponses = new HashMap<Integer, ArrayList<Integer>>();

        fragmentManager = getSupportFragmentManager();

        transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

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

                ifrag = new ImageFragment();
                Bundle bdi = new Bundle();
                bdi.putString(String.valueOf(R.string.imageF), question.lienImage);
                ifrag.setArguments(bdi);

                qfrag = new QuestionFragment();
                Bundle bdq = new Bundle();
                bdq.putStringArrayList(String.valueOf(R.string.questF), question.question);
                bdq.putStringArrayList(String.valueOf(R.string.respsF), question.reponses);
                qfrag.setArguments(bdq);

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_question, qfrag)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout_image, ifrag)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();


            }

            @Override
            public void onAnimationEnd(Animator animator) {
                reponses.put(index[0], qfrag.picked);
                index[0]++;
                if(index[0]<=questions.size()){
                    animation.start();
                }
                else {
                    Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                    i.putExtra(String.valueOf(R.string.finalResp), reponses);
                    i.putExtra(String.valueOf(R.string.finalQuest), questions);
                    startActivity(i);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) { }

            @Override
            public void onAnimationRepeat(Animator animator) { }
        });
        animation.start();


// Replace whatever is in the fragment_container view with this fragment

// Commit the transaction
        transaction.commit();

        Button suivant = (Button) findViewById(R.id.suivant);
        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation.end();
            }
        });
    }

}
