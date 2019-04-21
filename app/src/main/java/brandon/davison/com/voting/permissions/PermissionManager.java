package brandon.davison.com.voting.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class PermissionManager {

    private static final int STORAGE_PERMISSION_CODE = 23;

    private Context context;
    private Activity activity;

    public PermissionManager(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public boolean readStorageIsAllowed() {
        final int permissionStatus = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissionStatus == PackageManager.PERMISSION_GRANTED;
    }

    public void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(activity.getApplicationContext(),
                    "App can't function unless permission is granted.",
                    Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(activity, new String[]
                {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
}