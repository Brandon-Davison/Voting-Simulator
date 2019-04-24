package brandon.davison.com.voting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import brandon.davison.com.voting.permissions.PermissionManager;
import brandon.davison.com.voting.users.Candidate;
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

        Intent intent = getIntent();
        String voterId = intent.getStringExtra("voterID");
        int candidates = intent.getIntExtra("candidates", -1);
        boolean started = intent.getBooleanExtra("started", true);
        int votesAvailable = intent.getIntExtra("votesAvailable", -1);
        int votesToWin = intent.getIntExtra("votesToWin", -1);

        VoteSettings voteSettings = new VoteSettings(candidates, started, votesAvailable, votesToWin);

        CandidateManager candidateManager = new CandidateManager(voteSettings);

        Log.d("candidateTesting", " " + candidateManager.getCandidates().size());


        for (Candidate candidate : candidateManager.getCandidates()) {
         //   Log.d("candidateTesting", candidate.getName());
        }
    }

    private void setupViews() {
        voterName = findViewById(R.id.voter_name);
    }
}
