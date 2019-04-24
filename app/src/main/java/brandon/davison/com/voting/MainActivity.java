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
        final CandidateManager candidateManager = new CandidateManager(voteSettings);

        // Thread waiting for candidates to be read in from database. (Bad practice way to do it, short on time)
        Thread waitForCandidates = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!candidateManager.getCandidatesReady()) { }
                for (Candidate candidate : candidateManager.getCandidates()) {
                    Log.d("candidateTesting", "From Main");
                    Log.d("candidateTesting", candidate.getName());
                    Log.d("candidateTesting", " " + candidate.getid());
                    Log.d("candidateTesting", " " + candidate.getVotesReceived());
                    Log.d("candidateTesting", " " + candidate.hasWon());

                }
            }
        });
        waitForCandidates.start();
    }

    private void setupViews() {
        voterName = findViewById(R.id.voter_name);
    }
}
