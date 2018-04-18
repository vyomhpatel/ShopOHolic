package b12app.vyom.com.shopoholic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import b12app.vyom.com.shopoholic.fragments.AccountFragment;
import b12app.vyom.com.shopoholic.fragments.HomeFragment;
import b12app.vyom.com.shopoholic.fragments.ShoppingCartFragment;

public class Home extends AppCompatActivity {

    private static final String TAG = "";
    private BottomNavigationView bottomNavigationView;
    private AccountFragment accountFragment;
    private Bundle bundle;
    private HomeFragment homeFragment;
    private ShoppingCartFragment shoppingCartFragment;
    private String getlastname;
    private String getfirstname;
    private String getemail;
    private String getmobile;
    private Bundle profileBundle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Intent intent = getIntent();
        final Bundle receive = intent.getExtras();

        Log.i(TAG, "receive: "+receive);


        Log.i(TAG, "Home Bundle user: "+receive);

        Log.i(TAG, "onNavigation");
        homeFragment = new HomeFragment();
        getFragmentManager().beginTransaction().add(R.id.frame_home,homeFragment).commit();


        bottomNavigationView = findViewById(R.id.bottom_menu);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                //logic for bottom navigation.
               switch (item.getItemId()){

                   case R.id.menu_home:


                        getFragmentManager().beginTransaction().replace(R.id.frame_home,homeFragment).addToBackStack("Home Activity").commit();

                       break;

                   case R.id.menu_user_account:

                       accountFragment = new AccountFragment();
                       getFragmentManager().beginTransaction().replace(R.id.frame_home,accountFragment).addToBackStack("home activity").commit();
                       accountFragment.setArguments(receive);
                       break;

                   case R.id.menu_bag:
                       shoppingCartFragment = new ShoppingCartFragment();
                       getFragmentManager().beginTransaction().replace(R.id.frame_home,shoppingCartFragment).addToBackStack("home activity").commit();
                       break;


               }
                return false;
            }
        });


    }


}
