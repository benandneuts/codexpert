package com.codexpert;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Tache asynchrone
 * @author Frédéric RALLO - March 2022
 */
public class HttpAsyncGet {
    private static final String TAG = "codExpert " + HttpAsyncGet.class.getSimpleName();    //Pour affichage en cas d'erreur
    private String urlAddress;
    private HttpHandler webService;
    private HashMap<Integer, Questions> questions;

    public HttpAsyncGet(String url) {
        super();
        this.urlAddress = url;
        webService = new HttpHandler();
        questions = new HashMap<Integer, Questions>();
    }

    /**
     * you must change this method
     * don't forget to initialize item with cast (T)yourModel at the end;
     */
    public void doInBackGround(){

        // get the jsonStr to parse
        String jsonStr = webService.makeServiceCall(urlAddress);

        //parse jsonStr
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                JSONArray c = jsonObj.getJSONArray("questions");

                for (int i = 0; i<c.length(); i++) {
                    JSONObject o = (JSONObject) c.get(i);
                    JSONArray Jquestions = o.getJSONArray("name");
                    ArrayList<String> quest = new ArrayList<String>();
                    for (int j = 0; j<Jquestions.length(); j++) {
                        quest.add(Jquestions.getString(j));
                    }
                    JSONArray Jresponses = o.getJSONArray("responses");
                    ArrayList<String> responses = new ArrayList<String>();
                    for (int j = 0; j<Jresponses.length(); j++) {
                        responses.add(Jresponses.getString(j));
                    }
                    JSONArray Jsolutions = o.getJSONArray("solutions");
                    int[] solutions = new int[Jsolutions.length()];
                    for (int j = 0; j<Jsolutions.length(); j++) {
                        solutions[j] = Jsolutions.getInt(j);
                    }
                    String image  = o.getString("img");
                    questions.putIfAbsent(o.getInt("ID"), new Questions(quest, responses, solutions, image));
                }

            } catch (final JSONException e) {
                Log.d(TAG, "Erreur JSON " + e.getMessage());

            }
        } else {
            Log.e(TAG, "Probleme connexion ");
        }
        Log.d(TAG, "question="+jsonStr);
    }

    public HashMap<Integer, Questions> getItemResult() {
        return questions;
    }

    class HttpHandler { //innerClass

        public String makeServiceCall(String reqUrl) {
            String response = null;
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(reqUrl).openConnection();
                connection.setRequestMethod("GET");
                // lecture du fichier
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = convertStreamToString(inputStream);
            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "IOException: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
            return response;
        }

        //Conversion flux en String
        private String convertStreamToString(InputStream inputStream) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append('\n');
                    Log.e(TAG,line);
                }
            }
            catch (IOException e) {  e.printStackTrace();   }
            finally {
                try { inputStream.close(); } catch (IOException e) { e.printStackTrace();  }
            }
            return stringBuilder.toString();
        }
    }
}