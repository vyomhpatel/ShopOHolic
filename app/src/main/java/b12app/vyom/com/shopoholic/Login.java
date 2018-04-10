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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    private static final String TAG = "tag" ;
    private TextView tvSignup;
    private EditText etMobile, etPass;
    private Button btnLogin;
    private String current_mobile, current_password;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mQueue = Volley.newRequestQueue(this);

        etMobile = findViewById(R.id.etMobile);
        etPass = findViewById(R.id.etPss);

        tvSignup = findViewById(R.id.tvSignup);
        tvSignup.setText(Html.fromHtml("Already have an account? <font color='#EB367E'>Sign Up</font> here."));
    }

    public void launchSignup(View view) {

        startActivity(new Intent(Login.this,Registration.class));
    }









    private void jsonParse(){


        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_login.php?mobile="+current_mobile+"&password="+current_password;

        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONObject user = response.getJSONObject(0);
                    String firstname = user.getString("firstname");
                    String lastname = user.getString("lastname");
                    String email = user.getString("email");
                    String phone = user.getString("mobile");

                    Log.i(TAG, "user details "+firstname+" "+lastname+" "+email+" ");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Login.this,"Log in Failed",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }


    public void logUserIn(View view) {

        current_mobile = etMobile.getText().toString();
        current_password = etPass.getText().toString();
        jsonParse();
    }
}

