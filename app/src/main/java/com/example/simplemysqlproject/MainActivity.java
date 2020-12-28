package com.example.simplemysqlproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private ProgressDialog progressDialog;
    private Button btCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUsername=(EditText)findViewById(R.id.etUsername);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPassword=(EditText)findViewById(R.id.etPassword);
        progressDialog=new ProgressDialog(this);
        btCreate=(Button)findViewById(R.id.btCreate);
        btCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btCreate){
            RegisterUser();
        }
    }

    private void RegisterUser() {
        String username=etUsername.getText().toString().trim();
        String email=etEmail.getText().toString().trim();
        String password=etPassword.getText().toString().trim();
        progressDialog.setMessage("Registering User...");
        progressDialog.show();
        StringRequest req=new StringRequest(Request.Method.POST, Constants.Register_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
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
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        RequestQueue reqQueue= Volley.newRequestQueue(this);
        reqQueue.add(req);
    }
}