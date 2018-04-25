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
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import b12app.vyom.com.shopoholic.model.Product;
import b12app.vyom.com.shopoholic.adapters.ProductListAdapter;
import b12app.vyom.com.shopoholic.R;

import static b12app.vyom.com.shopoholic.utility.AppController.TAG;
import static b12app.vyom.com.shopoholic.utility.AppController.getInstance;

public class ProductListFragment extends Fragment {

    private  String sc_id_productList, c_id_productList;
    private  String app_api_key_productList, user_id_productList;
    private SharedPreferences sharedPreferencesProductList;
    private List<Product.ProductsBean> productsBeanList;
    private GridView gridViewProduct;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_list,container,false);

        gridViewProduct = view.findViewById(R.id.grid_product);

        Bundle recId = getArguments();
        if(recId!=null){
            c_id_productList = recId.getString("c_id","");
            sc_id_productList = recId.getString("selected_sc_id","");
        }

        sharedPreferencesProductList = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);

        app_api_key_productList = sharedPreferencesProductList.getString("app_api_key","");
        user_id_productList = sharedPreferencesProductList.getString("id","");
        Log.i(TAG, "sharedPreferencesProductList"+app_api_key_productList+" "+user_id_productList+" "+c_id_productList+" "+sc_id_productList);

        getProductList();

        gridViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String pb_id = productsBeanList.get(position).getId();
                String pb_name = productsBeanList.get(position).getPname();
                String pb_price = productsBeanList.get(position).getPrize();
                String pb_description = productsBeanList.get(position).getDiscription();
                String pb_image = productsBeanList.get(position).getImage();

                Product.ProductsBean productDetail = new Product.ProductsBean(pb_id,pb_name,"1",pb_price,pb_description,pb_image);
                Bundle productDetailsBundle  = new Bundle();
                productDetailsBundle.putSerializable("product",productDetail);
                ProductDetailsFragment productDetailsFragment = new ProductDetailsFragment();
                productDetailsFragment.setArguments(productDetailsBundle);
                getFragmentManager().beginTransaction().replace(R.id.frame_home,productDetailsFragment).addToBackStack("product list").commit();
            }
        });


        return view;
    }


    public  void getProductList(){
        final String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/product_details.php?cid="+c_id_productList+"&scid="+sc_id_productList+"&api_key="+app_api_key_productList+"&user_id="+user_id_productList;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
                productsBeanList = new ArrayList<>();

                try {
                    JSONArray products = response.getJSONArray("products");

                    for(int i = 0 ; i<products.length();i++){
                        JSONObject product = products.getJSONObject(i);
                      String   p_id = product.getString("id");
                      String p_name = product.getString("pname");
                        String p_quantity = product.getString("quantity");
                        String p_prize = product.getString("prize");
                        String p_discription = product.getString("discription");
                        String p_image = product.getString("image");


                        Product.ProductsBean productsBeanInstance = new Product.ProductsBean(p_id,p_name,p_quantity,p_prize,p_discription,p_image);
                        Log.i(TAG, "sub category: "+p_id+" "+p_name+" "+p_discription);
                        productsBeanList.add(productsBeanInstance);
                    }
//
                    ProductListAdapter productListAdapter = new ProductListAdapter(productsBeanList,getActivity());
                    gridViewProduct.setAdapter(productListAdapter);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) ;

        Volley.newRequestQueue(getInstance()).add(request);


    }
}
