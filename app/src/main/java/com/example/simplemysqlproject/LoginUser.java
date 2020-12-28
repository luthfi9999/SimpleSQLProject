package com.example.simplemysqlproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginUser extends AppCompatActivity implements View.OnClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private Boolean error;
    private Button btLogin;
    private TextView btRegister;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_user);
        etUsername=(EditText)findViewById(R.id.etUsername);
        etPassword=(EditText)findViewById(R.id.etPassword);
        btLogin=(Button)findViewById(R.id.btLogin);
        progressDialog=new ProgressDialog(this);
        btLogin.setOnClickListener(this);
        btRegister=(TextView)findViewById(R.id.btRegister);
        btRegister.setOnClickListener(this);
    }
    private void Login(){
        String username=etUsername.getText().toString().trim();
        String password=etPassword.getText().toString().trim();
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        StringRequest req=new StringRequest(Request.Method.POST, Constants.Login_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            error=jsonObject.getBoolean("error");

                            if(!error)
                                startActivity(new Intent(getApplicationContext(),UserTable.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue reqQueue= Volley.newRequestQueue(this);
        reqQueue.add(req);
    }
    @Override
    public void onClick(View v) {
        if(v==btLogin){
            Login();
        }
        else if(v==btRegister){
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }
    }
}
