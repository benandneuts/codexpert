package com.codexpert;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class QuestionFragment extends Fragment {
    private final String TAG = "codeExpert "+getClass().getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_question, container, false);
        result.findViewById(R.id.frame_layout_main).setOnClickListener( clic -> {

            Log.d(TAG,"question");
        });
        Log.d(TAG,"result="+result);
        return result;
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

