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

import b12app.vyom.com.shopoholic.R;
import b12app.vyom.com.shopoholic.model.SubCategory;
import b12app.vyom.com.shopoholic.adapters.SubCategoryAdapter;
import b12app.vyom.com.shopoholic.utility.Events;

import static b12app.vyom.com.shopoholic.utility.AppController.TAG;
import static b12app.vyom.com.shopoholic.utility.AppController.getInstance;

public class SubCategoryFragment extends Fragment implements AdapterView.OnItemClickListener {

   private List<SubCategory.SubcategoryBean> subcategoryBeanList;
   private String c_id, app_api_key, user_id, sc_id;
   private SharedPreferences sharedPreferencesSub;
    GridView gridView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sub_category,container,false);

        gridView = view.findViewById(R.id.grid_sub_category);
        gridView.setOnItemClickListener(this);

        sharedPreferencesSub = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        c_id = getArguments().getString("c_id");
        app_api_key = sharedPreferencesSub.getString("app_api_key","");
        user_id = sharedPreferencesSub.getString("id","");
        Log.i(TAG, "bundle: sub"+app_api_key+" "+user_id);

        displaySubCategories();


        return view;
    }

    private void displaySubCategories(){
        final String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id="+c_id+"&api_key="+app_api_key+"&user_id="+user_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), url, Toast.LENGTH_SHORT).show();
                subcategoryBeanList = new ArrayList<>();

                try {
                    JSONArray subcategories = response.getJSONArray("subcategory");

                    for(int i = 0 ; i<subcategories.length();i++){
                        JSONObject subcategory = subcategories.getJSONObject(i);
                         sc_id = subcategory.getString("scid");
                        String scname = subcategory.getString("scname");
                        String scdiscription = subcategory.getString("scdiscription");
                        String scimageurl = subcategory.getString("scimageurl");

                        SubCategory.SubcategoryBean subCategoryBeanInstane = new SubCategory.SubcategoryBean(sc_id,scname,scdiscription,scimageurl);
                        Log.i(TAG, "sub category: "+sc_id+" "+scname+" "+scdiscription+" "+scimageurl);
                        subcategoryBeanList.add(subCategoryBeanInstane);
                    }
//
                    SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(subcategoryBeanList,getActivity());
                    gridView.setAdapter(subCategoryAdapter);



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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Events.SubCategoryFragmentToProductListFragment subCategoryIdMessage = new Events.SubCategoryFragmentToProductListFragment(sc_id);
        ProductListFragment productListFragment = new ProductListFragment();
        Bundle cIdBundle = new Bundle();
        String selected_sc_id = subcategoryBeanList.get(position).getScid();
        cIdBundle.putString("c_id",c_id);
        cIdBundle.putString("selected_sc_id",selected_sc_id);
        productListFragment.setArguments(cIdBundle);

        getFragmentManager().beginTransaction().replace(R.id.frame_home,productListFragment).addToBackStack("sub category list").commit();

    }
}
