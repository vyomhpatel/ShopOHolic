package b12app.vyom.com.shopoholic.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import b12app.vyom.com.shopoholic.R;

public class ShopOholicMessageService extends FirebaseMessagingService {


    //token:fnLm4320B4g:APA91bE_IkLfCur0yz-HX_iw1Esc4tZrymZDG2PNz_QM5yta1Onx5QesYUj80fkRly29wWUJzB6UlfX59XPT38je5hMQvq8LbxjojoBAlKXXCofTJDGObsmX2IxQ_w_fZZF523UPIf7j

    static String TAG = "tag Meesage service";
    Context context;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        if(remoteMessage.getData().size()>0){

            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            Notification.Builder builder = new Notification.Builder(context).setSmallIcon(R.drawable.logo).setContentTitle(title).setContentText(message);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(0,builder.build());

        }

    }
}
