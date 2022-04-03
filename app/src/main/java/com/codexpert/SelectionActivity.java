package com.codexpert;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import androidx.appcompat.app.AppCompatActivity;

public class SelectionActivity extends AppCompatActivity implements ThemeListener {
    public ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // Cette ligne cache la barre implémenté par défaut
        setContentView(R.layout.activity_selection);

        ListTheme themeList = new ListTheme();

        selectionAdapter adapter = new selectionAdapter(this, themeList);

        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);

        adapter.addListener(this);
    }

    private void executeInThread(String lien){
        onPreExecute();
        HttpAsyncGet getQuestion = new HttpAsyncGet(lien);
        Runnable runnable = ()->{
            getQuestion.doInBackGround();
            runOnUiThread( ()-> onPostExecute( getQuestion.getItemResult()) );
        };
        Executors.newSingleThreadExecutor().execute( runnable );
    }

    /**
     * This method is called before the asynchronous webConnexion start
     */
    private void onPreExecute() {
        progressDialog = new ProgressDialog(SelectionActivity.this);
        progressDialog.setMessage("Récuperation des questions ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    /**
     * asynchrone callBack task
     * this is HttpAsyncGet instance is ready
     * @param questions is the return item from webService
     */
    private void onPostExecute(HashMap<Integer, Questions> questions) {
        progressDialog.dismiss();
        Intent i = new Intent(getApplicationContext(), EntrainementActivity.class);
        i.putExtra(String.valueOf(R.string.questions), questions);
        startActivity(i);
    }

    @Override
    public void onClick(Theme item, int position) {
        executeInThread(item.getLien());
    }

    @Override
    public void notAvaibleClick(Theme item, int position) {
        Toast toast = Toast.makeText(getApplicationContext(), item.getNom()+" n'est pas disponible", Toast.LENGTH_LONG);
        toast.show();
    }
}
