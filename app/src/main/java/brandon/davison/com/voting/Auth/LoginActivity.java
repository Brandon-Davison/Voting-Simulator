package brandon.davison.com.voting.Auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import brandon.davison.com.voting.R;
import brandon.davison.com.voting.users.VoterManager;
import brandon.davison.com.voting.voting.VoteSettings;

public class LoginActivity extends AppCompatActivity {

    // Login UI
    private EditText login_id, login_password;
    private Button login_btn;

    // Sign up UI
    private EditText register_id, register_password, register_name;
    private Button register_btn;

    // Non-UI variables
    String id, name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews(); // connect components to the IDs provided in the xml

        final VoteSettings voteSettings = new VoteSettings();

        // This is really bad but it works so oh well
        Thread waitForSettings = new Thread(new Runnable() {
            @Override
            public void run() {
                while (voteSettings.getVotesToWin() == 0 || voteSettings.getVotesAvailable() == 0
                    || voteSettings.getStartedWasChanged() == false) { }
                //voteSettings.setStarted(true);
                Log.d("SettingTesting", "Id test in run: " + voteSettings.getStarted()
                        + ", " + voteSettings.getVotesToWin() + ", " + voteSettings.getVotesAvailable());

                VoterManager idManager = new VoterManager(voteSettings);
            }
        });
        waitForSettings.start();

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
                id = register_id.getText().toString();
            }
        });

        register_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = register_name.getText().toString();
            }
        });

        register_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = register_password.getText().toString();
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: add code to setup user account then display a Toast msg that their
                // TODO: account has been created
            }
        });
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
