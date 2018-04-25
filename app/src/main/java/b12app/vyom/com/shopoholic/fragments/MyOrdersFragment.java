package b12app.vyom.com.shopoholic.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import b12app.vyom.com.shopoholic.model.OrderHistory;
import b12app.vyom.com.shopoholic.R;
import b12app.vyom.com.shopoholic.adapters.OrderHistoryAdapter;

public class MyOrdersFragment extends Fragment{

    List<OrderHistory.OrderhistoryBean> orderhistoryBeanList;
    String TAG = "tag";
    ListView orderHistoryListView;
    RequestQueue mQueue;
    Bundle bundle;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(2000);
        getActivity().getWindow().setEnterTransition(fade);

        Slide slide = new Slide();
        fade.setDuration(2000);
        getActivity().getWindow().setReturnTransition(slide);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_orders_fragment,container,false);

        setupWindowAnimations();

        orderhistoryBeanList = new ArrayList<>();
        mQueue = Volley.newRequestQueue(getActivity());
        orderHistoryListView = view.findViewById(R.id.orderList);
        SharedPreferences user_order_details = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        String user_id = user_order_details.getString("user_id","");
        String app_api_key = user_order_details.getString("app_api_key","");
        String mobile = user_order_details.getString("mobile","");
        String url = "http://rjtmobile.com/aamir/e-commerce/android-app/order_history.php?api_key="+app_api_key+"&user_id="+user_id+"&mobile="+mobile;


        JsonObjectRequest ordersRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i(TAG, "Order history "+response);

                try {
                    JSONArray Orderhistory = response.getJSONArray("Order history");
                    for(int i = 0; i<Orderhistory.length();i++){

                        JSONObject order = Orderhistory.getJSONObject(i);
                        String order_id = order.getString("orderid");
                        String orderstatus = order.getString("orderstatus");
                        String name = order.getString("name");
                        String billingadd = order.getString("billingadd");
                        String deliveryadd = order.getString("deliveryadd");
                        String mobile = order.getString("mobile");
                        String email = order.getString("email");
                        String itemid= order.getString("itemid");
                        String itemname= order.getString("itemname");
                        String itemquantity= order.getString("itemquantity");
                        String totalprice= order.getString("totalprice");
                        String paidprice= order.getString("paidprice");
                        String placedon= order.getString("placedon");

                        OrderHistory.OrderhistoryBean orderhistoryBeanInstance = new OrderHistory.OrderhistoryBean(order_id,orderstatus,name,billingadd,deliveryadd,mobile,email,itemid,itemname,itemquantity,totalprice,paidprice,placedon);
                            orderhistoryBeanList.add(orderhistoryBeanInstance);
                    }

                    final OrderHistoryAdapter orderHistoryAdapter = new OrderHistoryAdapter(orderhistoryBeanList,getActivity());
                    orderHistoryListView.setAdapter(orderHistoryAdapter);
                     bundle = new Bundle();
                    orderHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            OrderTrackFragment orderTrackFragment = new OrderTrackFragment();


                            String trackingNo = orderhistoryBeanList.get(position).getOrderid();
                            bundle.putString("trackingNo",trackingNo);
                            orderTrackFragment.setArguments(bundle);
                            getFragmentManager().beginTransaction().replace(R.id.frame_home,orderTrackFragment).addToBackStack("orders").commit();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
            mQueue.add(ordersRequest);
        return view;


    }


}
