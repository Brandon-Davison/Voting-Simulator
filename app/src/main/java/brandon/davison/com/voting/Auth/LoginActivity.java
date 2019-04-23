package brandon.davison.com.voting.Auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import brandon.davison.com.voting.R;
import brandon.davison.com.voting.voting.VoteSettings;

public class LoginActivity extends AppCompatActivity {

    int temp;

    // Admin (settings are going to be read in either from database variables, or setup page in app through admin login)
    // Which ever one is easier
    private static final String adminUserName = "Admin";
    private static final int adminPassword = 123;

    // Regular user (voter)
    private EditText login_id, login_password;
    private Button login_btn;

    private EditText register_id, register_password, register_name;
    private Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();

       // VoteSettings settings = new VoteSettings();
      //  Log.d("SettingTesting", "In activity: " + settings.getVotesToWin());

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference settingsRef = ref.child("settings");

        final TextView textView = findViewById(R.id.textview);

        VoteSettings voteSettings = new VoteSettings();

        /* LoginActivity onClick listeners */
        login_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        login_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /* Register onClick listeners */
        register_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        register_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        register_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        register_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public int wat() {
        return temp;
    }

    private void setupViews() {
        login_id = findViewById(R.id.login_id_edit_text);
        login_password = findViewById(R.id.login_password_edit_text);
        login_btn = findViewById(R.id.login_btn);

        register_id = findViewById(R.id.register_id_edit_text);
        register_password = findViewById(R.id.register_enter_password);
        register_name = findViewById(R.id.register_name_edit_text);
        register_btn = findViewById(R.id.register_btn);
    }
}
