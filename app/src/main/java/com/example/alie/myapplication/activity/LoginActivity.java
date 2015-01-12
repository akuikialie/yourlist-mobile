package com.example.alie.myapplication.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask.Status;

import com.example.alie.myapplication.Me;
import com.example.alie.myapplication.connection.InternetCheck;
import com.example.alie.myapplication.connection.ServiceHandler;
import com.example.alie.myapplication.connection.ConnectionDetector;
import com.example.alie.myapplication.Constants;

import com.example.alie.myapplication.R;

import org.json.JSONObject;

public class LoginActivity extends Activity {
    private EditText username = null;
    private EditText password = null;
    private Button login;
    Context c;
    ServiceHandler sHandler;
    private boolean isExist;
    private ConnectionDetector connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylogin);

        c = this;

        initializeComponents();
        if(!Constants.URL_SERVER.matches("http://.*"))
            Constants.URL_SERVER = "http://"+Constants.URL_SERVER;

        username = (EditText) findViewById(R.id.textUsername);
        password = (EditText) findViewById(R.id.textPassword);
        login = (Button) findViewById(R.id.buttonSubmitLogin);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void loginAction(View view) {
        if(connection.isConnectToInternet()){
            if(loginAct != null && loginAct.getStatus() == Status.RUNNING) loginAct.cancel(true);
            AsyncTask loginAct = new LoginAction().execute("");
        }
        else
            Toast.makeText(this, "You're is offline", Toast.LENGTH_LONG).show();
    }

    LoginAction loginAct;

    protected void onDestroy(){
        super.onDestroy();
        if(loginAct != null && loginAct.getStatus() == Status.RUNNING) loginAct.cancel(true);
    }

    private void initializeComponents(){
        username = (EditText) findViewById(R.id.textUsername);
        password = (EditText) findViewById(R.id.textPassword);
        connection = new ConnectionDetector(this);
    }

    private class LoginAction extends AsyncTask<String, String, Void> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            progressDialog = new ProgressDialog(c);
            progressDialog.setMessage("Please waiting....");
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);
                }
            });
            progressDialog.setCancelable(true);
            progressDialog.show();

            sHandler = new ServiceHandler();
            isExist=false;
        }
        
        @Override
        protected Void doInBackground(String... params) {
            String respondHttpRequest = sHandler.makeServiceCall(Constants.URL_SERVER+"/api/auth/login?username="+
                    username.getText().toString()+"&password="+
                    password.getText().toString(), ServiceHandler.GET);

            try {
                JSONObject respondUser = new JSONObject(respondHttpRequest);
                if(respondUser.optInt("status", 0) == 1){
                    isExist=true;
                    Constants.User.USER_ID = respondUser.getJSONObject("data").getString("id_user");
                    Constants.User.USER_ID_LEVEL = respondUser.getJSONObject("data").getInt("id_level");
                    Constants.User.USER_NAME = respondUser.getJSONObject("data").getString("username");
                    Constants.User.USER_KEY = respondUser.getJSONObject("data").getString("key");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void a) {
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            if(isExist){
                Me.setUsername(LoginActivity.this, Constants.User.USER_NAME);
                Me.setKey(LoginActivity.this, Constants.User.USER_KEY);
                Me.setLevel(LoginActivity.this, Constants.User.USER_ID_LEVEL);

                if (Constants.User.USER_ID_LEVEL == 1) {
                    startActivity(new Intent(getApplicationContext(), HomeOwnerActivity.class));
                } else if (Constants.User.USER_ID_LEVEL == 2) {
                    startActivity(new Intent(getApplicationContext(), HomeManagerActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), HomeTeamActivity.class));
                }
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Wrong Credential!", Toast.LENGTH_LONG).show();
            }
        }

    }
}
