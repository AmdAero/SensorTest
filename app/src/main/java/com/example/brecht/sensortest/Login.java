package com.example.brecht.sensortest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Login extends ActionBarActivity {

    Button btnLogin;
    Button btnRegister;
    EditText Email;
    EditText Password;

    JSONObject jsonResponse;

    public ActionBar actionBar = getSupportActionBar();

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_screens, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = null;
        switch (item.getItemId()) {
            case R.id.stopwatch:
                i = new Intent(Login.this , Stopwatch.class);
                startActivity(i);
                return true;
            case R.id.gravityR:
                i = new Intent(Login.this , Gravity_raw.class);
                startActivity(i);
                return true;
            case R.id.gyroscope:
                i = new Intent(Login.this , Gyroscope.class);
                startActivity(i);
                return true;
            case R.id.magneetometer:
                i = new Intent(Login.this , Magnetometer.class);
                startActivity(i);
                return true;
            case R.id.rotation:
                i = new Intent(Login.this , Rotation.class);
                startActivity(i);
                return true;
            case R.id.fileWriter:
                i = new Intent(Login.this , FileWriter.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.LoginButton);
        btnRegister = (Button) findViewById(R.id.RegisterButton);

        btnRegister.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v){
               Intent i = new Intent(Login.this, Register.class);
               startActivity(i);
           }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new MyAsyncTask().execute();
            }
        });
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(Login.this);
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
                    List<NameValuePair> jsonArray = new ArrayList<NameValuePair>();
                    jsonArray.add(new BasicNameValuePair("tag", "login"));
                    jsonArray.add(new BasicNameValuePair("email", String.valueOf(Email.getText())));
                    jsonArray.add(new BasicNameValuePair("password", String.valueOf(Password.getText())));

                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url_select);
                    httpPost.setEntity(new UrlEncodedFormEntity(jsonArray));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();

                    // Read content & Log
                    inputStream = httpEntity.getContent();
                } catch (Exception e) {
                    this.progressDialog.dismiss();
                    cancel(true);
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
                    Log.e("StringBuilding", "Error converting result " + e.toString());
                }
                return null;
        }

        protected void onPostExecute(Void v) {
            //parse JSON data

                try {
                    jsonResponse = new JSONObject(result);

                    String tag = jsonResponse.optString("tag").toString();
                    String success = jsonResponse.optString("success").toString();
                    String error = jsonResponse.optString("error").toString();
                    String error_msg = jsonResponse.optString("error_msg").toString();

                    //Close the progressDialog!
                    this.progressDialog.dismiss();
                    if (jsonResponse.optString("success").toString().equals("1")) {
                        super.onPostExecute(v);
                        Intent intent = new Intent(Login.this, Welkom.class);
                        intent.putExtra("Username", String.valueOf(Email.getText()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    }
                    else if(jsonResponse.optString("error").toString().equals("1")){
                        Toast.makeText(getApplicationContext(), jsonResponse.optString("error_msg").toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Can't login", Toast.LENGTH_SHORT).show();
        }
    }

}