package brandon.davison.com.voting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import brandon.davison.com.voting.permissions.PermissionManager;
import brandon.davison.com.voting.users.CandidateManager;
import brandon.davison.com.voting.voting.VoteSettings;

public class MainActivity extends AppCompatActivity {

    private TextView voterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        PermissionManager permissionManager = new PermissionManager(getApplicationContext(), MainActivity.this);
        permissionManager.requestStoragePermission();
    }

    private void setupViews() {
        voterName = findViewById(R.id.voter_name);
    }
}
