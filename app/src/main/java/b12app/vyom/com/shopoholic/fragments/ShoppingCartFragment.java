package b12app.vyom.com.shopoholic.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import b12app.vyom.com.shopoholic.utility.CartDbHelper;
import b12app.vyom.com.shopoholic.model.CartItem;
import b12app.vyom.com.shopoholic.R;
import b12app.vyom.com.shopoholic.adapters.CartItemAdapter;
import b12app.vyom.com.shopoholic.utility.AppController;
import b12app.vyom.com.shopoholic.utility.EventTotal;
import b12app.vyom.com.shopoholic.utility.Events;
import b12app.vyom.com.shopoholic.utility.GlobalBus;

public class ShoppingCartFragment extends Fragment {

    // shopping cart functionality

    private ListView cartListView;
    private Button btnCheckout, btnApplyCoupon;
    private TextView tvCartTotal;
    private static List<CartItem.ProductsBean> cartProductList;
    private int total = 0,taxes = 0, item_price, final_total =0, discountAmount = 0 ;
    static CartDbHelper cartDbHelper;
    CartItem.ProductsBean productsBean;
    private Events.AdapterToCart adapterToCart;
    private int product_price_specific;
    private TextView  cartTaxes,etTotalNotax, etDelivery;
    private EditText cartCoupon;
    private List<CartItem.ProductsBean> checkoutList;
    private static final String GET_TOKEN_URL = "http://rjtmobile.com/aamir/braintree-paypal-payment/main.php?";
    private static final String SEND_PAYMENT_DETAIL_URL = "http://rjtmobile.com/aamir/braintree-paypal-payment/mycheckout.php?";
    private String clientToke;
    HashMap<String, String> paramHash;
    private static final int BRAINTREE_REQUEST_CODE = 4949;
    private String user_name,email;
    private OrderSummeryFragment orderSummeryFragment;
    private Bundle bundle;

    String user_id, app_api_key, mobile;
    CartItemAdapter cartItemAdapter;

    SQLiteDatabase sqLiteDb;
    static String TAG = "cart tag";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shopping_cart, container, false);

        getToken();

        cartListView = view.findViewById(R.id.cartListView);
        btnCheckout = view.findViewById(R.id.btnPlaceOrder);

        tvCartTotal = view.findViewById(R.id.tvCartTotal);
        cartCoupon = view.findViewById(R.id.cartCoupon);
        cartTaxes = view.findViewById(R.id.cartTaxes);
        etDelivery = view.findViewById(R.id.etDelivery);
        etTotalNotax = view.findViewById(R.id.etTotalNotax);
        btnApplyCoupon = view.findViewById(R.id.btnApplyCoupon);
        etDelivery.setClickable(false);
        cartTaxes.setClickable(false);
        etTotalNotax.setClickable(false);


        cartDbHelper = new CartDbHelper(getActivity());
        sqLiteDb = cartDbHelper.getWritableDatabase();

        //  GlobalBus.getBus().register(this);

        SharedPreferences userDetails = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        user_id = userDetails.getString("user_id", "");

        app_api_key = userDetails.getString("app_api_key","");
        mobile = userDetails.getString("mobile","");
        user_name = userDetails.getString("firstname","");
        email = userDetails.getString("email","");

        cartProductList = new ArrayList<>();
         checkoutList = new ArrayList<>();

        Cursor cursor = sqLiteDb.rawQuery("SELECT * FROM " + CartDbHelper.TABLE_NAME + " WHERE " + CartDbHelper.USER_ID + " = " + user_id, null);

        if(cursor.getCount()>0) {

            cursor.moveToFirst();
            do {
                String cursor_product_id = cursor.getString(cursor.getColumnIndex(CartDbHelper.PRODUCT_ID));
                String cursor_product_name = cursor.getString(cursor.getColumnIndex(CartDbHelper.PRODUCT_NAME));
                String cursor_product_quantity = cursor.getString(cursor.getColumnIndex(CartDbHelper.PRODUCT_QUANTITY));
                String cursor_product_prize = cursor.getString(cursor.getColumnIndex(CartDbHelper.PRODUCT_PRICE));
                String cursor_product_image = cursor.getString(cursor.getColumnIndex(CartDbHelper.PRODUCT_IMAGE));

                Log.i(TAG, "cart tag " + cursor_product_name);

                cartProductList.add(new CartItem.ProductsBean(cursor_product_id,
                        cursor_product_name,
                        cursor_product_quantity,
                        cursor_product_prize,
                        cursor_product_image));
            }
            while (cursor.moveToNext());

            cartItemAdapter = new CartItemAdapter(cartProductList, getActivity());
            cartListView.setAdapter(cartItemAdapter);
            justifyListViewHeightBasedOnChildren(cartListView);
        }
//        cartItemAdapter.notifyDataSetChanged();


        countTotal();
        final_total = total+taxes;
        etTotalNotax.setText("₹ "+String.valueOf(total));
        tvCartTotal.setText("₹ "+String.valueOf(final_total));
        cartTaxes.setText("₹ "+String.valueOf(taxes));

        btnApplyCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCopon();

            }
        });


        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DropInRequest dropInRequest= new DropInRequest().clientToken(clientToke);


                startActivityForResult(dropInRequest.getIntent(getActivity()), BRAINTREE_REQUEST_CODE);


            }
        });


        return view;
    }

    public static void justifyListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    private void countTotal() {
        for (int a = 0; a < cartProductList.size(); a++) {

            total = total + Integer.valueOf(cartProductList.get(a).getPrize()) * Integer.valueOf(cartProductList.get(a).getQuantity());
            taxes = total/10;
        }

    }

    @Subscribe
    public void onEvent(EventTotal eventTotal) {
        for (int a = 0; a < cartProductList.size(); a++) {

            if (cartProductList.get(a).getId().equals(eventTotal.getPid())){
                cartProductList.get(a).setQuantity(eventTotal.getQuantity());
                break;
            }
        }

        total = 0;
        for (int i = 0; i < cartProductList.size(); i++){
            total = total + Integer.valueOf(cartProductList.get(i).getPrize()) * Integer.valueOf(cartProductList.get(i).getQuantity());
            taxes = total/10;
        }

        final_total = total+taxes;
        etTotalNotax.setText("₹ "+String.valueOf(total));
        tvCartTotal.setText("₹ "+String.valueOf(final_total));
        cartTaxes.setText("₹ "+String.valueOf(taxes));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!GlobalBus.getBus().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        for(int row = 0; row<cartProductList.size(); row++){
            String query = "SELECT * FROM "+CartDbHelper.TABLE_NAME+" WHERE "+CartDbHelper.USER_ID+" = "+user_id;
            Cursor cursor = sqLiteDb.rawQuery(query,null);
            cursor.moveToFirst();
            do {
                if (cursor.getCount() > 0) {
                    String corsor_productid = cursor.getString(cursor.getColumnIndex(CartDbHelper.PRODUCT_ID));
                    Log.i(TAG, "onDestroyView: " + corsor_productid);


                    String updateCart = "UPDATE "+CartDbHelper.TABLE_NAME+" " +
                            "SET "+CartDbHelper.PRODUCT_QUANTITY+" = "+cartProductList.get(row).getQuantity()+
                                                " WHERE " +
                            ""+CartDbHelper.USER_ID+" = "+user_id+" AND "+CartDbHelper.PRODUCT_ID+" = "+cartProductList.get(row).getId();

                    sqLiteDb.execSQL(updateCart);
                    cartItemAdapter.notifyDataSetChanged();
                }
            } while (cursor.moveToNext());

        }

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

        RequestQueue queue= Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    public void sendOrderToServer(){

        for(int orderItem=0; orderItem<cartProductList.size();orderItem++) {


            String url = "http://rjtmobile.com/aamir/e-commerce/android-app/orders.php?&item_id=" + cartProductList.get(orderItem).getId() + "&item_names=" + cartProductList.get(orderItem).getPname() + "&item_quantity=" + cartProductList.get(orderItem).getQuantity() + "&final_price=" + final_total + "&&api_key=" + app_api_key + "&" +
                    "user_id=" + user_id + "&user_name=" + user_name + "&billingadd=Noida&deliveryadd=9341 Twin Oaks ln, Des Plaines, IL 60016&mobile=" + mobile + "&email=" + email;

            Toast.makeText(getActivity(),"order successful",Toast.LENGTH_SHORT).show();

            JsonObjectRequest orderRequest= new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    Log.i(TAG, "On response for payment:" + response);
                    orderSummeryFragment = new OrderSummeryFragment();

                    try {
                        JSONArray orderConfirmed = response.getJSONArray("Order confirmed");
                        for(int i = 0 ;i<orderConfirmed.length();i++){

                            JSONObject order = orderConfirmed.getJSONObject(i);
                            String orderid = order.getString("orderid");
                            String orderstatus = order.getString("orderstatus");
                            String name = order.getString("name");
                            String deliveryadd = order.getString("deliveryadd");
                            String mobile = order.getString("mobile");
                            String itemname = order.getString("itemname");
                            String itemquantity = order.getString("itemquantity");
                            String totalprice = order.getString("totalprice");
                            String placedon = order.getString("placedon");

                            bundle = new Bundle();

                            bundle.putString("orderid",orderid);
                            bundle.putString("orderstatus",orderstatus);
                            bundle.putString("name",name);
                            bundle.putString("deliveryadd",deliveryadd);
                            bundle.putString("mobile",mobile);
                            bundle.putString("itemname",itemname);
                            bundle.putString("itemquantity",itemquantity);
                            bundle.putString("totalprice",totalprice);









                        }

                        orderSummeryFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().addToBackStack("payment comfirm").replace(R.id.frame_home,orderSummeryFragment).commit();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String delete = "DELETE FROM "+CartDbHelper.TABLE_NAME+" WHERE "+CartDbHelper.USER_ID+" = "+user_id;
                    sqLiteDb.execSQL(delete);
                    cartProductList.clear();
                    cartItemAdapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(orderRequest);
        }

    }


    //starting activity for result in order to send payment update to server and order confirmation to server in case of transaction has been made
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==BRAINTREE_REQUEST_CODE){
            if(resultCode== Activity.RESULT_OK){
                DropInResult result=data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentNonce = result.getPaymentMethodNonce().getNonce();
                paramHash = new HashMap<>();
                paramHash.put("amount", String.valueOf(final_total));
                paramHash.put("nonce", paymentNonce);
                //send payment infromation to braintree app server
                Log.d("Drop In result responce", "Testing the app here");
                sendPaymentNonceToServer();
                sendOrderToServer();



            }
            else if(resultCode==Activity.RESULT_CANCELED){/*cancelled*/}
            else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    //sending the payment nonce to server in order to inform the server whether or not transaction has been successfully went through.
    private void sendPaymentNonceToServer() {
        RequestQueue queue=Volley.newRequestQueue(getActivity());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, SEND_PAYMENT_DETAIL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Successful")){
                    Toast.makeText(getActivity(), "Transaction successful", Toast.LENGTH_LONG).show();

                }
                else
                    Toast.makeText(getActivity(), "Transaction failed", Toast.LENGTH_LONG).show();
                Log.d("mylog", "Final Response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mylog", "Volley error : " + error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (paramHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for (String key : paramHash.keySet()) {
                    params.put(key, paramHash.get(key));
                    Log.d("mylog", "Key : " + key + " Value : " + paramHash.get(key));
                }

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void validateCopon(){

       String copoun =  cartCoupon.getText().toString();

        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/coupon.php?api_key="+app_api_key+"&user_id="+user_id+"&couponno="+copoun;

        Log.i(TAG, "coupon url "+url);
        JsonObjectRequest couponRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    Log.i(TAG, "Copoun Response"+response);

                        JSONArray couponDiscount = response.getJSONArray("Coupon discount");
                        JSONObject couponCurrent = couponDiscount.getJSONObject(0);
                        String discount = couponCurrent.getString("discount");
                        discountAmount = Integer.valueOf(discount);
                    if(total>0&& discountAmount>0) {
                        total = total - total* discountAmount/100;
                        etTotalNotax.setText("₹ "+total);
                        taxes = total/10;
                        cartTaxes.setText("₹ "+taxes);
                        final_total = total+taxes;
                        tvCartTotal.setText("₹ "+final_total);
                        btnApplyCoupon.setClickable(false);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    //setting the error message in case of wrong copoun code
                    cartCoupon.setError("Coupon Not Valid");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "coupon error"+error);
                cartCoupon.setError("Coupon Not Valid");

            }
        });
        AppController.getInstance().addToRequestQueue(couponRequest);

    }

}
