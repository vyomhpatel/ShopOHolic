package b12app.vyom.com.shopoholic.utility;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class ShopOholicFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG ="tag" ;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);


     //   sendRegistrationToServer(refreshedToken);
    }
}
