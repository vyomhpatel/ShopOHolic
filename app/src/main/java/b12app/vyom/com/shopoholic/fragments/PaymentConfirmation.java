package b12app.vyom.com.shopoholic.fragments;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import b12app.vyom.com.shopoholic.R;

public class PaymentConfirmation extends AppCompatActivity  {

    TextView tv;
    Button button;
    private static final String GET_TOKEN_URL = "http://rjtmobile.com/aamir/braintree-paypal-payment/main.php?";
    private static final String SEND_PAYMENT_DETAIL_URL = "http://rjtmobile.com/aamir/braintree-paypal-payment/mycheckout.php?";
    private String clientToke;
    private String totalAmount="100";
    HashMap<String, String> paramHash;
    private static final int BRAINTREE_REQUEST_CODE = 4949;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);
//        tv=findViewById(R.id.textView);
//        button=findViewById(R.id.button);
        getToken();


//        button.setOnClickListener(this);
    }

    private void getToken() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, GET_TOKEN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("String Request Response", response.toString());
                clientToke=response.toString();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("String Request Error", error.toString());

            }
        });

        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }


//    @Override
//    public void onClick(View view) {
//        DropInRequest dropInRequest= new DropInRequest().clientToken(clientToke);
//
//        startActivityForResult(dropInRequest.getIntent(this), BRAINTREE_REQUEST_CODE);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==BRAINTREE_REQUEST_CODE){
//            if(resultCode== Activity.RESULT_OK){
//                DropInResult result=data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
//                String paymentNonce = result.getPaymentMethodNonce().getNonce();
//                paramHash = new HashMap<>();
//                paramHash.put("amount", totalAmount);
//                paramHash.put("nonce", paymentNonce);
//                //send payment infromation to braintree app server
//                Log.d("Drop In result responce", "Testing the app here");
//                sendPaymentNonceToServer();
//
//            }
//            else if(resultCode==Activity.RESULT_CANCELED){/*cancelled*/}
//            else {
//                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
//            }
//        }
//    }
//
//    private void sendPaymentNonceToServer() {
//        RequestQueue queue=Volley.newRequestQueue(this);
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, SEND_PAYMENT_DETAIL_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if(response.contains("Successful")){
//                    Toast.makeText(MainActivity.this, "Transaction successful", Toast.LENGTH_LONG).show();
//                    sendOrderToServer();
//                }
//                else
//                    Toast.makeText(MainActivity.this, "Transaction failed", Toast.LENGTH_LONG).show();
//                Log.d("mylog", "Final Response: " + response.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("mylog", "Volley error : " + error.toString());
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                if (paramHash == null)
//                    return null;
//                Map<String, String> params = new HashMap<>();
//                for (String key : paramHash.keySet()) {
//                    params.put(key, paramHash.get(key));
//                    Log.d("mylog", "Key : " + key + " Value : " + paramHash.get(key));
//                }
//
//                return params;
//            }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/x-www-form-urlencoded");
//                return params;
//            }
//        };
//        queue.add(stringRequest);
//    }
    private void sendOrderToServer()
    {
//        http://rjtmobile.com/ansari/shopingcart/androidapp/orders.php?&item_id=701&item_names=laptop&item_quantity=1&final_price=100000&mobile=654987&api_key=kflasfkla&user_id=2
    }


}
