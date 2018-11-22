package cordova.plugin;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class ProgressNotification extends CordovaPlugin {
    private static final String TAG = "progress-notification";
    private static final Integer NOTIFICATION_DEFAULT_ID = 6;
    private static final Integer MAX_VALUE = 100;

    private NotificationManager notificationManager;
    private android.support.v4.app.NotificationCompat.Builder builder;
    private boolean indeterminate;

    private NotificationManager getNotificationManager() {
        if (this.notificationManager == null) {
            this.notificationManager = (NotificationManager) this.cordova.getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return this.notificationManager;
    }

    private Integer getResource(String resource) {
        return this.cordova.getActivity().getResources().getIdentifier(resource, "drawable", this.cordova.getActivity().getPackageName());
    }

    private android.support.v4.app.NotificationCompat.Builder getBuilder() {
        if (this.builder == null) {
            String id = "Aqila_Atualizacao";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                android.app.NotificationChannel channel = new android.app.NotificationChannel(id,
                        id,
                        NotificationManager.IMPORTANCE_DEFAULT);
                getNotificationManager().createNotificationChannel(channel);
            }

            this.builder = new android.support.v4.app.NotificationCompat.Builder(this.cordova.getActivity(), id).setSmallIcon(getResource("launcher_icon"));
        }

        return this.builder;
    }

    private void updateOrShow() {
        getNotificationManager().notify(NOTIFICATION_DEFAULT_ID, this.getBuilder().build());
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("show")) {
            String title = args.getString(0);
            String text = args.getString(1);
            this.indeterminate = args.getBoolean(2);

            this.getBuilder()
                    .setContentTitle(title)
                    .setContentText(text)
                    .setProgress(MAX_VALUE, 0, indeterminate)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setSmallIcon(android.R.drawable.ic_menu_upload)
                    .setOngoing(true);

            this.updateOrShow();

            callbackContext.success();
        }

        if (action.equals("update")) {
            Integer value = args.getInt(0);
            getBuilder()
                .setProgress(MAX_VALUE, value, indeterminate);
            this.updateOrShow();

            callbackContext.success();
        }

        if (action.equals("finish")) {
            Integer value = args.getInt(1);
            getBuilder()
                .setContentText(args.getString(0))
                .setProgress(MAX_VALUE, value, false)
                .setOngoing(false);
            this.updateOrShow();
            this.builder = null;

            callbackContext.success();
        }

        if (action.equals("dismiss")) {
            this.getNotificationManager().cancel(NOTIFICATION_DEFAULT_ID);
            this.builder = null;

            callbackContext.success();
        }


        return true;
   }

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        if (this.builder != null) {
            updateOrShow();
        }

    }
}
