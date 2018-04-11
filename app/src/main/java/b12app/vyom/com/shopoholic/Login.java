package b12app.vyom.com.shopoholic;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static final String TAG = "tag" ;
    private TextView tvSignup, tvForgotPw;
    private EditText etMobile, etPass;
    private Button btnLogin;
    private String current_mobile, current_password;
    private RequestQueue mQueue;
    private SharedPreferences sharedPreferencesUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mQueue = Volley.newRequestQueue(this);

        etMobile = findViewById(R.id.etMobile);
        etPass = findViewById(R.id.etPss);

        tvSignup = findViewById(R.id.tvSignup);
        tvSignup.setText(Html.fromHtml("Don't have an account? <font color='#EB367E'>Sign Up</font> here."));


    }

    public void launchSignup(View view) {

        startActivity(new Intent(Login.this,Registration.class));
    }









    private void jsonParse(){


        String url = "https://rjtmobile.com/aamir/e-commerce/android-app/shop_login.php?";

        JsonArrayRequest request =new JsonArrayRequest(Request.Method.POST, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject user = response.getJSONObject(0);

                    String id = user.getString("id");
                    String appapikey = user.getString("appapikey ");

                    sharedPreferencesUser = getSharedPreferences("user_details",MODE_PRIVATE);
                    SharedPreferences.Editor shareDetails = sharedPreferencesUser.edit();

                    startActivity(new Intent(Login.this,Home.class));
                    finish();

                    shareDetails.putString("id",id);
                    shareDetails.putString("app_api_key",appapikey);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this,"Log in Failed",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder loginAlert = new AlertDialog.Builder(Login.this);
                loginAlert.setTitle("Invalid Credentials");
                loginAlert.setMessage("The phone number and password you provided is incorrect");
                loginAlert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                loginAlert.show();

               // error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("mobile", current_mobile);
                params.put("password", current_password);
                return params;
            }
        };

        mQueue.add(request);
    }


    public void logUserIn(View view) {

        current_mobile = etMobile.getText().toString();
        current_password = etPass.getText().toString();
        jsonParse();


    }
}

