package com.codexpert;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrainement);
        TextView score = findViewById(R.id.score);
        TextView txtScore = findViewById(R.id.res_txt);
        Button share = findViewById(R.id.shareButton);
        Button suite = findViewById(R.id.suivant);
        Button detail_rep = findViewById(R.id.details);
    }
}
