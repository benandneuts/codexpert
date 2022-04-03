package com.codexpert;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {
    public ImageFragment() { };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_image, null);
        Bundle arguments = getArguments();
        return result;
    }
}
