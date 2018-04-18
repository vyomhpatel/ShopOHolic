
package b12app.vyom.com.shopoholic.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import java.util.HashMap;

import b12app.vyom.com.shopoholic.R;

public class Registration extends AppCompatActivity {

    private static final String TAG = "tag" ;
    private TextView tvLogin;
    private EditText etFirstName, etLastName, etPhone, etPassword,etAddress, etEmail;
    private Button btnRegister;
    private String firstName, lastName, phone, password, address, email;
    private RequestQueue mQueue;
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setText(Html.fromHtml("Already have an account? <font color='#ef5350'>Sign In</font> here."));

//        mQueue = Volley.newRequestQueue(this);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPss);
        btnRegister = findViewById(R.id.btnSignup);

        //applying custom regex for validating mobile, email, firstname, lastname and password fields.

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.etFirstName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.etLastName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);

        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.etPss, "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", R.string.passworderror);
        awesomeValidation.addValidation(this, R.id.etMobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);




    }

    public void launchLogin(View view) {
        startActivity(new Intent(Registration.this,Login.class));
    }

    public void registerUser(View view) {

        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();
        email = etEmail.getText().toString();
        phone = etPhone.getText().toString();
        password = etPassword.getText().toString();
        address = etAddress.getText().toString();


            //checking the input with regex

            if(awesomeValidation.validate()) {
                jsonParse();
                startActivity(new Intent(Registration.this, Login.class));
                finish();
            }
    }



    //making a string request to the server in order to register the user in the registration api

    private void jsonParse(){


        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_reg.php?" ;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG, "response: "+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "user registration failed for"+firstName);
                error.printStackTrace();
            }
        }){
            @Override
            protected HashMap<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("fname", firstName);
                params.put("lname", lastName);
                params.put("address", address);
                params.put("email", email);
                params.put("mobile", phone);
                params.put("password", password);

                return params;

            }
        };



        Volley.newRequestQueue(Registration.this).add(stringRequest);

    }
}
