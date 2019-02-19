/**
 * Created by Patryk on 2017-04-13.
 */

public class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");}
