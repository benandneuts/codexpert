package com.codexpert;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class EntrainementActivity extends Fragment {
    private final String TAG = "frallo "+getClass().getSimpleName();
    private OnButtonClickedListener callBackActivity;


    // --------------


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_main, container, false);
        result.findViewById(R.id.fragment_main_button).setOnClickListener( clic -> {
            int value=Integer.parseInt(((EditText) result.findViewById(R.id.editText)).getText().toString());
            Log.d(TAG,"clic with value="+value);
            callBackActivity.onButtonClicked(value);
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
            callBackActivity = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }
}
