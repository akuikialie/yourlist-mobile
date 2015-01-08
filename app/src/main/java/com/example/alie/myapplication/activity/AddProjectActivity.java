package com.example.alie.myapplication.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alie.myapplication.Constants;
import com.example.alie.myapplication.R;
import com.example.alie.myapplication.connection.ConnectionDetector;
import com.example.alie.myapplication.connection.InternetCheck;
import com.example.alie.myapplication.connection.ServiceHandler;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class AddProjectActivity extends Activity {
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
        Log.d(msg, "Muncul tampilan create project");
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.mylogin);
//        username = (EditText) findViewById(R.id.textUsername);
//        password = (EditText) findViewById(R.id.textPassword);
//        login = (Button) findViewById(R.id.buttonSubmitLogin);
    }
}
