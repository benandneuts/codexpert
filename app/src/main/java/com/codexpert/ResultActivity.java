package com.codexpert;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener{

    private Button share,detail_rep,suite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // Cette ligne cache la barre implémenté par défaut

        setContentView(R.layout.activity_main);

        TextView score = findViewById(R.id.score);
        TextView txtScore = findViewById(R.id.res_txt);
        share = findViewById(R.id.shareButton);
        suite = findViewById(R.id.suite);
        detail_rep = findViewById(R.id.details);

        score.setText("35/40");
        txtScore.setText("Félicitation !");

        // click events
        share.setOnClickListener(this);
        detail_rep.setOnClickListener(this);
        suite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == share) {
            partagerScore(); // TODO && temp
        }
        else if(v == detail_rep) {
            afficherDetail(); // TODO && temp
        }
        else if(v == suite) {
            afficherSuite(); // TODO && temp
        }
    }

    private void afficherSuite() {
    }

    private void afficherDetail() {
    }

    private void partagerScore() {
    }
}
