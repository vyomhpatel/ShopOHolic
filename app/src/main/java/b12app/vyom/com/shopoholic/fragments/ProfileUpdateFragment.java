package b12app.vyom.com.shopoholic.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import b12app.vyom.com.shopoholic.R;
import b12app.vyom.com.shopoholic.model.User;

public class ProfileUpdateFragment extends Fragment {

    private static final String TAG = "tag";
    SharedPreferences sharedPreferences;
    private String tempPhone, tempPass;
    RequestQueue mQueue;
    String getfirstname,getlastname, getemail, getmobile, getaddress;
    private EditText etFirstNameUpdate, etLastNameUpdate, etMobileUpdate, etAddressUpdate, etEmailUpdate, etAddress;
    private Button btnUpdate;
    private User user;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile_update,container,false);


        sharedPreferences = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        mQueue = Volley.newRequestQueue(getActivity());


        etFirstNameUpdate = view.findViewById(R.id.etFirstNameUpdate);
        etLastNameUpdate  = view.findViewById(R.id.etLastNameUpdate);
        etEmailUpdate = view.findViewById(R.id.etEmailUpdate);
        etMobileUpdate = view.findViewById(R.id.mobileUpdate);
        etMobileUpdate.setFocusable(false);
        etMobileUpdate.setClickable(false);
        etAddressUpdate = view.findViewById(R.id.addressUpdate);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        Bundle bundle = getArguments();
        Log.i(TAG, "Profile Update bundle"+bundle);


            etFirstNameUpdate.setText(sharedPreferences.getString("firstname",""));
            etLastNameUpdate.setText(sharedPreferences.getString("lastname",""));
            etEmailUpdate.setText(sharedPreferences.getString("email",""));
            etMobileUpdate.setText(sharedPreferences.getString("mobile",""));







        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fname = etFirstNameUpdate.getText().toString();
                final String lname = etLastNameUpdate.getText().toString();
                final String email = etEmailUpdate.getText().toString();
                String address=etAddressUpdate.getText().toString();
                final String mobile = etMobileUpdate.getText().toString();

                final String url="http://rjtmobile.com/aamir/e-commerce/android-app/edit_profile.php?fname="+fname+"&lname="+lname+"&address="+address+"& email="
                +email+"&mobile="+mobile;

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, "Profile Update url"+url);

                        Log.i(TAG, "Profile Update Response: "+response);
                        AccountFragment accountFragment = new AccountFragment();
                        getFragmentManager().beginTransaction().replace(R.id.frame_home,accountFragment).commit();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("firstname",fname);
                        editor.putString("lastname",lname);
                        editor.putString("email",email);
                        editor.putString("mobile",mobile);
                        editor.commit();

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
