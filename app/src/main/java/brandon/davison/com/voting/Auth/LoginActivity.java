package brandon.davison.com.voting.Auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import brandon.davison.com.voting.MainActivity;
import brandon.davison.com.voting.R;
import brandon.davison.com.voting.users.CandidateManager;
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
    VoterManager voterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews(); // connect components to the IDs provided in the xml

        final VoteSettings voteSettings = new VoteSettings();

        // Thread waiting for candidates to be read in from database. (Bad practice way to do it, short on time)
        Thread waitForSettings = new Thread(new Runnable() {
            @Override
            public void run() {
                while (voteSettings.getVotesToWin() == 0 || voteSettings.getVotesAvailable() == 0
                    || voteSettings.getStartedWasChanged() == false || voteSettings.getCandidates() == 0) { }
                //voteSettings.setStarted(true);
                Log.d("SettingTesting", "Id test in run: " + voteSettings.getStarted()
                        + ", " + voteSettings.getVotesToWin() + ", " + voteSettings.getVotesAvailable() +
                        ", " + voteSettings.getCandidates());

                voterManager = new VoterManager(voteSettings);
            }
        });
        waitForSettings.start();

        /* ---------------- OnClick Listeners for login and register */

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = login_id.getText().toString();
                String password = login_password.getText().toString();

                // TODO: remove later (for testing purposes)
                Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                intent2.putExtra("voterID", "6e1bea4e-c3dc-468c-b472-e687f2fa778d"); // TODO change
                intent2.putExtra("candidates", voteSettings.getCandidates());
                intent2.putExtra("started", voteSettings.getStarted());
                intent2.putExtra("votesAvailable", voteSettings.getVotesAvailable());
                intent2.putExtra("votesToWin", voteSettings.getVotesToWin());
                LoginActivity.this.startActivity(intent2);

                if (id.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this,
                            "Please fill out all login forms", Toast.LENGTH_LONG).show();
                    return;
                }

                if (voterManager.validCredentials(id, password)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);                    //intent.putExtra("id", id);
                    intent.putExtra("id", id);

                    Toast.makeText(LoginActivity.this, "successfully logged in",
                            Toast.LENGTH_LONG).show();
                    LoginActivity.this.startActivity(intent);
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = register_id.getText().toString();
                String password = register_password.getText().toString();
                String name = register_name.getText().toString();

                if (id.equals("") || password.equals("") || name.equals("")) {
                    Toast.makeText(LoginActivity.this,
                            "Please fill out all register forms", Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d("idTesting", "" + voterManager.canRegister(id));

                if (voterManager.canRegister(id)) {
                    voterManager.registerVoter(id, name, password);
                    Toast.makeText(LoginActivity.this, "Account created", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Sorry, you can't register",
                            Toast.LENGTH_LONG).show();
                }
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
