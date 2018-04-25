package b12app.vyom.com.shopoholic.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import b12app.vyom.com.shopoholic.model.Category;
import b12app.vyom.com.shopoholic.R;
import b12app.vyom.com.shopoholic.HomeRecycleViewAdapter;
import b12app.vyom.com.shopoholic.utility.Events;
import b12app.vyom.com.shopoholic.utility.GlobalBus;

import static b12app.vyom.com.shopoholic.utility.AppController.TAG;
import static b12app.vyom.com.shopoholic.utility.AppController.getInstance;

public class HomeFragment extends Fragment  {


    List<Category.CategoryBean> categoryBeansList;
    ViewPager pager_category;
    SharedPreferences sharedPreferencesHome;
    String user_id, app_api_key;
    private RequestQueue mQueue;
    RecyclerView recyclerView;
    String c_id_from_adapter;
    HomeRecycleViewAdapter adapter;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {

        //registeting to global bus in order to receive event update
        super.onStart();
        GlobalBus.getBus().register(this);
    }

    @Override
    public void onStop() {

        //unregistering self from global bus in order to stop receiving update when goes in to background.
        super.onStop();
        GlobalBus.getBus().unregister(this);
    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        jsonParse();
        View view = inflater.inflate(R.layout.fragment_home_final,container,false);

        recyclerView = view.findViewById(R.id.recyleView);



        mQueue = Volley.newRequestQueue(getActivity());


        return view;
    }

    @SuppressLint("ResourceType")
    @Subscribe
    public void getMessage(Events.AdapterToHomeFragmentMessage adapterToHomeFragmentMessage){

        c_id_from_adapter = adapterToHomeFragmentMessage.getMessage();
        Toast.makeText(getActivity(),"c_id:"+c_id_from_adapter,Toast.LENGTH_SHORT).show();
        SubCategoryFragment subCategoryFragment = new SubCategoryFragment();

        Bundle bundle = new Bundle();
        bundle.putString("c_id",c_id_from_adapter);
        bundle.putString("app_api_key",app_api_key);
        bundle.putString("user_id",user_id);
        subCategoryFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.frame_home,subCategoryFragment).addToBackStack("home fragment").commit();
        Log.i(TAG, "bundle: "+bundle);


    }






//making a json object request in order to receive the category details from the server based on app_api key and user_id.

    private void jsonParse(){
        sharedPreferencesHome = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        user_id = sharedPreferencesHome.getString("id","");
        app_api_key = sharedPreferencesHome.getString("app_api_key","");


        Log.i(TAG, "ID and APP KEY "+user_id+" "+app_api_key);

        String url = "http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php?api_key="+app_api_key+"&user_id="+user_id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                categoryBeansList = new ArrayList<>();

                try {
                    JSONArray categories = response.getJSONArray("category");

                    for(int i = 0 ; i<categories.length();i++){
                       JSONObject department = categories.getJSONObject(i);
                       String cid = department.getString("cid");
                       String cname = department.getString("cname");
                       String cdiscription = department.getString("cdiscription");
                       String image = department.getString("cimagerl");


                       //initializing categorybean pojo in order to handle details in structured manner

                       Category.CategoryBean categoryBeanInstane = new Category.CategoryBean(cid,cname,cdiscription,image);
                        Log.i(TAG, "Product Details: "+cid+" "+cname+" "+cdiscription+" "+image);

                        //adding instance of categorybean in the list of categorybeans.
                        categoryBeansList.add(categoryBeanInstane);
                    }


                    initRecyclerView();

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

    //initializing recylcer view adapter, setting the animation and applying adapter to recycler view.

    private void initRecyclerView() {


        RecyclerView.LayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        adapter = new HomeRecycleViewAdapter(getActivity(),categoryBeansList);
        recyclerView.setAdapter(adapter);



    }



//specifying the style for grids in the recycler view.
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}

