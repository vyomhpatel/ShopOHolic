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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import b12app.vyom.com.shopoholic.R;
import b12app.vyom.com.shopoholic.utility.AppController;

public class OrderTrackFragment extends Fragment {

    TextView tvOrderNo,tvStatus;
    ImageView statusImage;
    private String user_id,app_api_key,order_no, status;
    private String TAG = "order status tag";
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_track_fragment,container,false);

        tvOrderNo =view.findViewById(R.id.tvOrderNo);
        tvStatus = view.findViewById(R.id.tvStatus);
        statusImage = view.findViewById(R.id.statusImage);

        bundle = getArguments();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
       user_id =  sharedPreferences.getString("user_id","");
       app_api_key = sharedPreferences.getString("app_api_key","");
       order_no = bundle.getString("trackingNo","");
        Log.i(TAG, "order no log: "+order_no);
       getOrderStatus();


        return view;
    }

   public void getOrderStatus(){


        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/shipment_track.php?api_key="+app_api_key+"&user_id="+user_id+"&order_id="+bundle.getString("trackingNo");

       JsonObjectRequest orderStatusRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {

               Log.i(TAG, "order status response"+response);

               try {
                   JSONArray statusArray = response.getJSONArray("Shipment track");
                   for(int x=0; x<statusArray.length();x++){
                       JSONObject orderStatusObject  =  statusArray.getJSONObject(x);
                       status = orderStatusObject.getString("shipmentstatus");
                       if(status.equals("1")){

                           statusImage.setBackgroundResource(R.drawable.icon_delivery);
                           tvOrderNo.setText("Order No: "+order_no);
                           tvStatus.setText("Order Confirmed");

                       } else if(status.equals("2")){
                           statusImage.setBackgroundResource(R.drawable.dispatched_icon);
                           tvOrderNo.setText("Order No: "+order_no);
                           tvStatus.setText("Order Dispatched");
                       } else if(status.equals("3")){
                           statusImage.setBackgroundResource(R.drawable.delivery_icon);
                           tvOrderNo.setText("Order No: "+order_no);
                           tvStatus.setText("Order Shipped");
                       } else if(status.equals("4")){
                           statusImage.setBackgroundResource(R.drawable.delivered_icon);
                           tvOrderNo.setText("Order No: "+order_no);
                           tvStatus.setText("Order Delivered");
                       }


                   }
               } catch (JSONException e) {
                   e.printStackTrace();
                   Log.i(TAG, "Log status error"+e);
               }

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Log.i(TAG, "Log status error"+error);
           }
       });

       AppController.getInstance().addToRequestQueue(orderStatusRequest);

    }
}
