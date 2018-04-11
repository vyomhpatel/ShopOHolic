package b12app.vyom.com.shopoholic;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Home extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = findViewById(R.id.bottom_menu);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                HomeFragment homeFragment = new HomeFragment();
                getFragmentManager().beginTransaction().add(R.id.frame_home,homeFragment).commit();

               switch (item.getItemId()){

                   case R.id.menu_home:

                        homeFragment = new HomeFragment();
                        getFragmentManager().beginTransaction().replace(R.id.frame_home,homeFragment).commit();

                       break;

                   case R.id.menu_category:

                       break;

                   case R.id.menu_user_account:

                       accountFragment = new AccountFragment();
                       getFragmentManager().beginTransaction().replace(R.id.frame_home,accountFragment).commit();
                       break;

                   case R.id.menu_bag:
                       break;


               }
                return false;
            }
        });


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        //getMenuInflater().inflate(R.menu.main, menu);
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.bottom_menu, menu);
//        return true;
//    }
}
