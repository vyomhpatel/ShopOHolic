package b12app.vyom.com.shopoholic.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import b12app.vyom.com.shopoholic.activities.Login;
import b12app.vyom.com.shopoholic.R;
import b12app.vyom.com.shopoholic.adapters.AccountItemAdapter;

public class AccountFragment extends Fragment{

    private ListView listViewActions;
   // private int[] icons;
    private String[] actions;
    private Integer[] icons;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = (Slide) TransitionInflater.from(getActivity()).inflateTransition(R.transition.fragment_slide).setDuration(2000);
        getActivity().getWindow().setExitTransition(slide);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupWindowAnimations();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.account,container,false);
        listViewActions = view.findViewById(R.id.accountListView);




        actions = getResources().getStringArray(R.array.account_array);
        icons = new Integer[]{R.drawable.orders_icon,R.drawable.account,R.drawable.change_pass, R.drawable.logout_icon};
        final Bundle receiveHome = getArguments();

        AccountItemAdapter itemsAdapter = new AccountItemAdapter(icons,actions,getActivity());
        listViewActions.setAdapter(itemsAdapter);
        listViewActions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //launching different account related fragments based on user click events on the listview for account.

                switch (position){

                    case 0:


                        MyOrdersFragment myOrdersFragment = new MyOrdersFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
                                R.animator.fragment_slide_left_exit,
                                R.animator.fragment_slide_right_enter,
                                R.animator.fragment_slide_right_exit);
                        ft.replace(R.id.frame_home,myOrdersFragment).addToBackStack("account").commit();

                        break;
                    case 1:
                        ProfileUpdateFragment profileUpdateFragment = new ProfileUpdateFragment();
                        getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_home,profileUpdateFragment).addToBackStack("account").commit();
                        profileUpdateFragment.setArguments(receiveHome);
                        break;
                    case 2:

                        ChangePassword changePassword = new ChangePassword();
                        getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_home,changePassword).addToBackStack("account").commit();

                        break;
                    case 3:

                        Intent intent = new Intent(getActivity(), Login.class);
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        //clearing the shared preference values from user_details.
                        editor.putString("mobile","");
                        editor.putString("password","");
                        editor.putString("id","");
                        editor.putString("app_api_key","");
                        editor.putString("appapikey","");
                        editor.putString("firstname","");
                        editor.putString("lastname","");
                        editor.putString("email","");
                        editor.putString("user_id","");
                        editor.commit();
                        startActivity(intent);
                        break;
                }

            }
        });

        return view;
    }




}
