package b12app.vyom.com.shopoholic.activities;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import b12app.vyom.com.shopoholic.Home;
import b12app.vyom.com.shopoholic.R;

public class Login extends AppCompatActivity {

    private static final String TAG = "tag" ;
    private TextView tvSignup, tvForgotPw;
    private EditText etMobile, etPass;
    private Button btnLogin;
    private CheckBox cbRemember;
    private String current_mobile, current_password;
    private RequestQueue mQueue;
    private SharedPreferences sharedPreferencesUser;
    private  SharedPreferences.Editor shareDetails;
    private AwesomeValidation awesomeValidation;
    private String tempMobile, tempPassword;
    private static String LOGGED_MOBILE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializing request queue.
        mQueue = Volley.newRequestQueue(this);

        sharedPreferencesUser = getSharedPreferences("user_details", MODE_PRIVATE);

        tempMobile = sharedPreferencesUser.getString("mobile","");
        tempPassword =sharedPreferencesUser.getString("password","");


        //checking if mobile number and password has been saved in shared preferences or not if so skip the login page.

        if(tempMobile!=""&& tempPassword!=""){
            this.finish();
            startActivity(new Intent(Login.this,Home.class));

        }


        etMobile = findViewById(R.id.etMobile);
        etPass = findViewById(R.id.etPss);
        cbRemember = findViewById(R.id.checkBox);

        tvSignup = findViewById(R.id.tvSignup);
        tvSignup.setText(Html.fromHtml("Don't have an account? <font color='#ef5350'>Sign Up</font> here."));

        //applying regular expression validation to mobile number in order to validate user input for mobile number

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.etMobile, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);




    }


    //sign up launch method

    public void launchSignup(View view) {

        startActivity(new Intent(Login.this,Registration.class));
    }


    //data parsing for login request to the login api

    private void jsonParse(){


        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shop_login.php?mobile="+current_mobile+"&password="+current_password;

        JsonArrayRequest request =new JsonArrayRequest(Request.Method.POST, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for(int i=0; i<response.length();i++) {
                        JSONObject user = response.getJSONObject(i);

                        String id = user.getString("id");
                        Log.i(TAG, "ID FROM SERVER:" + id);
                        String appapikey = user.getString("appapikey ");
                        String firstname = user.getString("firstname");
                        String lastname = user.getString("lastname");
                        String email = user.getString("email");
                        String mobile = user.getString("mobile");


                        Log.i(TAG, "login: response:"+response);





                        Intent intent = new Intent(Login.this, Home.class);
                        Bundle bundle = new Bundle();

                        //storing data to shared preference.

                        shareDetails.putString("firstname",firstname);
                        shareDetails.putString("lastname",lastname);
                        shareDetails.putString("email",email);
                        shareDetails.putString("mobile",mobile);
                        shareDetails.putString("user_id",user.getString("id"));
                        shareDetails.putString("id", id);
                        shareDetails.putString("app_api_key", appapikey);


                        intent.putExtras(bundle);
                        Log.i(TAG, "bundle:" + bundle);


                        startActivity(intent);
                        finish();



                            shareDetails.commit();

                    }
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

                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }


    public void logUserIn(View view) {


        //applying validation

       if( awesomeValidation.validate()) {

           shareDetails = sharedPreferencesUser.edit();
           current_mobile = etMobile.getText().toString();
           current_password = etPass.getText().toString();
           LOGGED_MOBILE = current_mobile;

           if(cbRemember.isChecked()) {

               shareDetails.putString("password",current_password);

           }





           jsonParse();

       }
    }
}

