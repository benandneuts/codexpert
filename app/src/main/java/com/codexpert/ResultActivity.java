package com.codexpert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    private static final String LOG_TAG = "CodExpert";
    private static final int vitesseReapparition = 1000;
    final Vector<ImageView> mesEtoiles = new Vector<>();

    private Button share,detail_rep,suite;

    private int nbQuestion;
    private int res = 0;
    private String numeroTel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();

        HashMap<Integer, ArrayList<Integer>> rep = i.getParcelableExtra(String.valueOf(R.string.finalResp));
        HashMap<Integer, Questions> quest = i.getParcelableExtra(String.valueOf(R.string.finalQuest));
        HashMap<Integer, ArrayList<Integer>> correction = new HashMap<Integer, ArrayList<Integer>>();

        for (Integer c : quest.keySet()) {
            ArrayList<Integer> tmplist = new ArrayList<>();
            for (int solution : quest.get(c).solutions) {
                tmplist.add(solution);
            }
            correction.put(c, tmplist);
        }

        nbQuestion = quest.size();

        for (Integer c : rep.keySet()) {
            if(rep.get(c).containsAll((Collection<?>) correction.get(c)) && correction.get(c).containsAll((Collection<?>) rep.get(c))) {
                res++;
            }
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // Cette ligne cache la barre implémenté par défaut

        setContentView(R.layout.activity_result);

        TextView score = findViewById(R.id.score);
        TextView txtScore = findViewById(R.id.res_txt);
        share = findViewById(R.id.shareButton);
        suite = findViewById(R.id.suite);
        detail_rep = findViewById(R.id.details);

        ImageView fStar = findViewById(R.id.fStar);
        ImageView sStar = findViewById(R.id.sStar);
        ImageView tStar = findViewById(R.id.tStar);

        mesEtoiles.add(fStar);
        mesEtoiles.add(sStar);
        mesEtoiles.add(tStar);

        score.setText(res+"/"+nbQuestion);

        if (res >= nbQuestion*0.875){
            txtScore.setText(R.string.reussit);
        } else if (res >= nbQuestion*0.75){
            txtScore.setText(R.string.presque);
        }  else if (res >= nbQuestion*0.5){
            txtScore.setText(R.string.encore_effort);
        }   else {
            txtScore.setText(R.string.finito);
        }

        starAnimation();

        // click events
        share.setOnClickListener(this);
        detail_rep.setOnClickListener(this);
        suite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == share) {
            shareDialog(v);
        }
        else if(v == detail_rep) {
        }
        else if(v == suite) {
        }
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    private void starAnimation() {
        if (res >= nbQuestion*0.5){
            mesEtoiles.get(0).setImageResource(R.drawable.full_star);
            if(res >= nbQuestion*0.75){
                mesEtoiles.get(1).setImageResource(R.drawable.full_star);
                if(res >= nbQuestion*0.875){
                    mesEtoiles.get(2).setImageResource(R.drawable.full_star);
                }   else{
                    mesEtoiles.get(2).setImageResource(R.drawable.empty_star);
                }
            }   else{
                mesEtoiles.get(1).setImageResource(R.drawable.empty_star);
                mesEtoiles.get(2).setImageResource(R.drawable.empty_star);
            }
        }   else{
            mesEtoiles.get(0).setImageResource(R.drawable.empty_star);
            mesEtoiles.get(1).setImageResource(R.drawable.empty_star);
            mesEtoiles.get(2).setImageResource(R.drawable.empty_star);
        }
        Animation animReapparition = AnimationUtils.loadAnimation( ResultActivity.this, R.anim.smooth_translation );
        animReapparition.setDuration(vitesseReapparition);
        for (ImageView etoile : mesEtoiles)
            etoile.startAnimation(animReapparition);
    }

    public void shareDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Veuillez saisir un numéro");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            setNumeroTel(input.getText().toString());
            askPermissionAndSendSMS();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    protected void askPermissionAndSendSMS() {

        // With Android Level >= 23, you have to ask the user
        // for permission to send SMS.
        if (android.os.Build.VERSION.SDK_INT >=  android.os.Build.VERSION_CODES.M) { // 23

            // Check if we have send SMS permission
            int sendSmsPermisson = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS);

            if (sendSmsPermisson != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSION_REQUEST_CODE_SEND_SMS
                );
                return;
            }
        }
        this.sendSMS_by_smsManager();
    }

    private void sendSMS_by_smsManager()  {

        String message = "Votre ami a eu "+this.res+" sur CodExpert !";

        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            // Send Message
            smsManager.sendTextMessage(getNumeroTel(),
                    null,
                    message,
                    null,
                    null);

            Log.d(LOG_TAG, "Your sms has successfully sent!");
            Toast.makeText(getApplicationContext(),"Your sms has successfully sent!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.e( LOG_TAG,"Your sms has failed...", ex);
            Toast.makeText(getApplicationContext(),"Your sms has failed... " + ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }


    // When you have the request results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //
        if (requestCode == MY_PERMISSION_REQUEST_CODE_SEND_SMS) {// Note: If request is cancelled, the result arrays are empty.
            // Permissions granted (SEND_SMS).
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(LOG_TAG, "Permission granted!");
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();

                this.sendSMS_by_smsManager();
            }
            // Cancelled or denied.
            else {
                Log.i(LOG_TAG, "Permission denied!");
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show();
            }
        }
    }

    // When results returned
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_PERMISSION_REQUEST_CODE_SEND_SMS) {
            if (resultCode == RESULT_OK) {
                // Do something with data (Result returned).
                Toast.makeText(this, "Action OK", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Action canceled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}