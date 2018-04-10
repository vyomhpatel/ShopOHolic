
package b12app.vyom.com.shopoholic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class Registration extends AppCompatActivity {

    private static final String TAG = "tag" ;
    private TextView tvLogin;
    private EditText etFirstName, etLastName, etPhone, etPassword,etAddress, etEmail;
    private Button btnRegister;
    private String firstName, lastName, phone, password, address, email;
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setText(Html.fromHtml("Already have an account? <font color='#EB367E'>Sign In</font> here."));

        mQueue = Volley.newRequestQueue(this);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAddress = findViewById(R.id.etAddress);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etMobile);
        etPassword = findViewById(R.id.etPss);
        btnRegister = findViewById(R.id.btnSignup);




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

        jsonParse();
    }


    private void jsonParse(){


        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_reg.php?fname="+firstName+"&lname="+lastName+"&address="+address+"& email="+email+"&mobile="+phone+"&password="+password ;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG, "user registration complete for"+firstName);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "user registration failed for"+firstName);
            }
        });

        mQueue.add(stringRequest);
    }
}
