package com.example.brecht.sensortest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class JSON extends ActionBarActivity {

    TextView Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.json);

        Data = (TextView) findViewById(R.id.Data);

        Button ParseJSON = (Button) findViewById(R.id.Parse);
        ParseJSON.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new MyAsyncTask().execute();
            }
        });
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(JSON.this);
        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            progressDialog.setMessage("Login...");
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface arg0) {
                    MyAsyncTask.this.cancel(true);
                }
            });
        }

        @Override
        protected Void doInBackground(Void... params) {

            String url_select = "http://php-brechtcarlier.rhcloud.com/";

            try {
                // Set up HTTP post

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url_select);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
                inputStream = httpEntity.getContent();
            } catch (Exception e) {
                progressDialog.setMessage("Can't login");
                progressDialog.show();
            }

            // Convert response to string using String Builder
            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                inputStream.close();
                result = sBuilder.toString();
            }

            catch (Exception e) {
                Log.e("StringBuilding & BufferedReader", "Error converting result " + e.toString());
            }


            return null;
        }

        protected void onPostExecute(Void v) {
            //parse JSON data
            String OutputData = "";
            JSONObject jsonResponse;

            try {

                // Creates a new JSONObject with name/value mappings from the JSON string.
                jsonResponse = new JSONObject(result);

                // Returns the value mapped by name if it exists and is a JSONArray.
                //  Returns null otherwise.
                JSONArray jsonMainNode = jsonResponse.optJSONArray("users_info");

                // Process each JSON Node

                int lengthJsonArr = jsonMainNode.length();

                for(int i=0; i < lengthJsonArr; i++)
                {
                    //Get Object for each JSON node.
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                    //Fetch node values
                    int id = Integer.parseInt(jsonChildNode.optString("ID").toString());
                    String username = jsonChildNode.optString("Username").toString();
                    String password = jsonChildNode.optString("Password").toString();


                    OutputData += "Node:\n"+ id +" | "
                            + username +" | "
                            + password +" \n\n";
                }

                //Show Output on screen/activity
                Data.setText( OutputData );

                //Close the progressDialog!
                this.progressDialog.dismiss();

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
    }

}
