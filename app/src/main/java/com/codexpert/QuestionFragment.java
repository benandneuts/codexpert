package com.codexpert;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;


public class QuestionFragment extends Fragment {
    private final String TAG = "codeExpert "+getClass().getSimpleName();
    View view;
    ArrayList<String> questions;
    ArrayList<String> reponses;
    ArrayList<Integer> picked;
    QuestionFragmentCallBack callBack;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question, container, false);
        Bundle bundle = this.getArguments();

        callBack = (QuestionFragmentCallBack) getContext();

        questions = bundle.getStringArrayList(String.valueOf(R.string.questF));
        reponses = bundle.getStringArrayList(String.valueOf(R.string.respsF));
        picked = new ArrayList<Integer>();

        if(questions.size() == 1){
            TextView quest = new TextView(this.getContext());
            quest.setText(questions.get(0));

            ((LinearLayout)view.findViewById(R.id.layoutQuest)).addView(quest);
            if(reponses.size() <= 2){
                RadioGroup rg = new RadioGroup(getContext());
                for(int i = 0; i<reponses.size(); i++) {
                    RadioButton rb = new RadioButton(getContext());
                    int finalI = i;
                    rb.append(reponses.get(i));
                    rg.addView(rb);
                }
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        picked.remove(Integer.valueOf(0));
                        picked.remove(Integer.valueOf(1));
                        picked.add(i);
                    }
                });
            }
            else {
                for(int i = 0; i<reponses.size(); i++) {
                    CheckBox cb = new CheckBox(getContext());
                    int finalI = i;
                    cb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(cb.isChecked()){
                                picked.add(finalI);
                            }
                            else{
                                picked.remove(Integer.valueOf(finalI));
                            }
                        }
                    });
                    cb.append(reponses.get(i));
                    ((LinearLayout)view.findViewById(R.id.layoutQuest)).addView(cb);
                }
            }
        }
        else if(questions.size() == 2){
            for(int i = 0; i<questions.size(); i++) {
                TextView quest = new TextView(this.getContext());
                quest.setText(questions.get(i));

                ((LinearLayout)view.findViewById(R.id.layoutQuest)).addView(quest);

                RadioGroup rg = new RadioGroup(getContext());
                for(int j = 0; j<2; j++) {
                    RadioButton rb = new RadioButton(getContext());
                    int finalI = j+i*2;
                    rb.append(reponses.get(j+i*2));
                    rb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(picked.contains(finalI)){
                                picked.remove(finalI);
                            }
                            else {
                                picked.add(finalI);
                            }
                        }
                    });
                    rg.addView(rb);
                }
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        picked.remove(Integer.valueOf(i));
                        picked.remove(Integer.valueOf(i+1));
                        picked.add(i);
                    }
                });
                ((LinearLayout)view.findViewById(R.id.layoutQuest)).addView(rg);
            }
        }
        Log.d(TAG,"result="+view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createCallbackToParentActivity();
    }


    // Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {

        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }

}

