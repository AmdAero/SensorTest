package com.example.brecht.sensortest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class LoginFragment extends Fragment implements View.OnClickListener{

    private View view;

    Button btnLogin;
    Button btnRegister;
    EditText Email;
    EditText Password;

    JSONObject jsonResponse;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login, container, false);

        Email = (EditText) view.findViewById(R.id.email);
        Password = (EditText) view.findViewById(R.id.password);
        btnLogin = (Button) view.findViewById(R.id.LoginButton);
        btnRegister = (Button) view.findViewById(R.id.RegisterButton);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.LoginButton:
                new MyAsyncTask().execute();
                break;
            case R.id.RegisterButton:
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack

                transaction.replace(R.id.root_login, registerFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
                break;
            default:
                break;
        }




        /*
        btnRegister.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent i = new Intent(Login.this, register.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new MyAsyncTask().execute();
            }
        });
        */
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog progressDialog = new ProgressDialog(getActivity());
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
                    //Close the progressDialog!
                    this.progressDialog.dismiss();

                    //Check if login succeeded
                    if (jsonResponse.optString("success").toString().equals("1")) {
                        WelcomeFragment wut = new WelcomeFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("username", String.valueOf(Email.getText()));
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        // Replace whatever is in the fragment_container view with this fragment,
                        // and add the transaction to the back stack

                        transaction.replace(R.id.root_login, wut);
                        wut.setArguments(bundle);
                        transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();
                    }
                    else if(jsonResponse.optString("error").toString().equals("1")){
                        Toast.makeText(getActivity().getApplicationContext(), jsonResponse.optString("error_msg").toString(), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getActivity().getApplicationContext(), "Can't login", Toast.LENGTH_SHORT).show();
        }
    }

}