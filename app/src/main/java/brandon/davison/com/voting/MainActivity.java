package brandon.davison.com.voting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import brandon.davison.com.voting.permissions.PermissionManager;
import brandon.davison.com.voting.users.Candidate;
import brandon.davison.com.voting.users.CandidateManager;
import brandon.davison.com.voting.users.Voter;
import brandon.davison.com.voting.users.VoterManager;
import brandon.davison.com.voting.voting.VoteSettings;

public class MainActivity extends AppCompatActivity {

    /* UI views */
    private TextView voterNameView, voterIdView;

    /* Non-UI variables */

    // if Candidates have been read in from database yet
    private boolean ready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        PermissionManager permissionManager = new PermissionManager(getApplicationContext(), MainActivity.this);
        permissionManager.requestStoragePermission();

        Intent intent = getIntent();
        final String voterId = intent.getStringExtra("voterID").substring(0, 5);
        int candidates = intent.getIntExtra("candidates", -1);
        boolean started = intent.getBooleanExtra("started", true);
        int votesAvailable = intent.getIntExtra("votesAvailable", -1);
        int votesToWin = intent.getIntExtra("votesToWin", -1);

        VoteSettings voteSettings = new VoteSettings(candidates, started, votesAvailable, votesToWin);

        final VoterManager voterManager = new VoterManager(voteSettings);
        final CandidateManager candidateManager = new CandidateManager(voteSettings);


        // Thread waiting for candidates to be read in from database. (Bad practice way to do it, short on time)
        Thread waitForCandidates = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!candidateManager.getCandidatesReady()) { }
                ready = true;

                final Voter voter = voterManager.getVoter(voterId);
                if (voter != null) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            voterNameView.setText(voter.getName());
                            voterIdView.setText(voter.getId().substring(0, 5));
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error, could not find voter",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d("candidateTesting", "Voter size: " + voterManager.getVoters().size());
                Log.d("candidateTesting", "ID: " + voterId);
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
        voterNameView = findViewById(R.id.voter_name);
        voterIdView = findViewById(R.id.voter_id);
    }
}
