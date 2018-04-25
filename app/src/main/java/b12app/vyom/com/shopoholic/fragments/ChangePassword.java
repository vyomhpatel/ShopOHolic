package b12app.vyom.com.shopoholic.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import b12app.vyom.com.shopoholic.activities.Login;
import b12app.vyom.com.shopoholic.R;

public class ChangePassword extends Fragment {

    private Button btnResetPass;
    private EditText etMobileResetPW, etOldPassword, etNewPassword;
    private String TAG ="Change pw Tag";
    private RequestQueue mQueue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.reset_password,container,false);
        etMobileResetPW = view.findViewById(R.id.etMobileResetPW);
        etOldPassword = view.findViewById(R.id.oldPassword);
        etNewPassword = view.findViewById(R.id.newPassword);
        btnResetPass = view.findViewById(R.id.btnReset);

        mQueue = Volley.newRequestQueue(getActivity());

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String mobileReset = etMobileResetPW.getText().toString();
                String oldPass = etOldPassword.getText().toString();
                final String newPass = etNewPassword.getText().toString();


                //making a string request in order to check the old password and update the new password if old password is valid.

                String url="http://rjtmobile.com/aamir/e-commerce/android-app/shop_reset_pass.php?&mobile="+mobileReset+"&password="+oldPass+"&newpassword="+newPass;

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Log.i(TAG, "Reset Password Response: "+response);
                        if(response=="password reset successfully") {
                            Intent intent = new Intent(getActivity(), Login.class);
                            startActivity(intent);

                        } else{

                            //displaying the wrong password alert in case of old password do not match with the password in the server.

                            AlertDialog.Builder loginAlert = new AlertDialog.Builder(getActivity());
                            loginAlert.setTitle("Invalid Credentials");
                            loginAlert.setMessage("Either Mobile number is wrong Or Passwords do not match!");
                            loginAlert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        ChangePassword changePasswordRecursive = new ChangePassword();
                                        getFragmentManager().beginTransaction().replace(R.id.frame_home,changePasswordRecursive).commit();
                                }
                            });
                            loginAlert.show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "Profile Update Error: "+error);
                    }
                });

                mQueue.add(stringRequest);


            }
        });



        return view;
    }
}
