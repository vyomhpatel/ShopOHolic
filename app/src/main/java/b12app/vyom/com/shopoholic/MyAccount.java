package b12app.vyom.com.shopoholic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import b12app.vyom.com.shopoholic.fragments.AccountFragment;

public class MyAccount extends AppCompatActivity {

    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        accountFragment = new AccountFragment();
        getFragmentManager().beginTransaction().add(R.id.frame_account,accountFragment).commit();

    }
}
