package brandon.davison.com.voting;

import android.Manifest;
import android.os.storage.StorageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import brandon.davison.com.voting.permissions.PermissionManager;

public class MainActivity extends AppCompatActivity {

    private static String log = "MainActivity ErrorCode 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionManager permissionManager = new PermissionManager(getApplicationContext(), MainActivity.this);

        Toast.makeText(MainActivity.this, "onStart", Toast.LENGTH_LONG).show();

        permissionManager.requestStoragePermission();
        Log.d("PermissionManager", "Storage = " + permissionManager.readStorageIsAllowed());
    }
}
