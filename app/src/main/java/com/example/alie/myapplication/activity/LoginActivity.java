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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.AsyncTask.Status;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

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
    private InternetCheck internet;
    String msg = "Android : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mylogin);
        username = (EditText) findViewById(R.id.textUsername);
        password = (EditText) findViewById(R.id.textPassword);
        login = (Button) findViewById(R.id.buttonSubmitLogin);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void loginAction(View view) {
            if (ca != null && ca.getStatus() == AsyncTask.Status.RUNNING) ca.cancel(true);
                ca = new CallAPI();
                ca.execute("");
        /*if(username.getText().toString().equals("") &&
            password.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Successfull!",
                Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

        else{
            Toast.makeText(getApplicationContext(), "Wrong Credential!",
                    Toast.LENGTH_SHORT).show();
        }*/
    }

    CallAPI ca;

    protected void onDestroy(){
        super.onDestroy();
        if(ca != null && ca.getStatus() == Status.RUNNING) ca.cancel(true);
    }

    private void initializeComponents(){
        username = (EditText) findViewById(R.id.textUsername);
        password = (EditText) findViewById(R.id.textPassword);
    }

    private class CallAPI extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try{
                HttpClient Client = new DefaultHttpClient();

                String URL = Constants.URL_SERVER+"/api/auth/login?username="+username.getText().toString()+"&password="+password.getText().toString();
                try
                {
                    String respondServer = "";

                    HttpGet httpget = new HttpGet(URL);
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    respondServer = Client.execute(httpget, responseHandler);

                    JSONObject a = new JSONObject(respondServer);
                    if(a.optInt("status", 0) == 1) {
                        isExist = true;
                        Constants.User.USER_ID = a.getJSONObject("data").getString("id_user");
                        Constants.User.USER_ID_LEVEL = a.getJSONObject("data").getInt("id_user");
                        Constants.User.USER_NAME = a.getJSONObject("data").getString("username");
                        Constants.User.USER_KEY = a.getJSONObject("data").getString("key");
                    }
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (isExist) {
                Toast.makeText(getApplicationContext(), "Successfull!",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Wrong Credential!",
                        Toast.LENGTH_SHORT).show();
            }
            Log.d(msg, "onPostExecute() event");
        }

    } // end CallAPI
}
