package brandon.davison.com.voting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import brandon.davison.com.voting.permissions.PermissionManager;
import brandon.davison.com.voting.users.Candidate;
import brandon.davison.com.voting.users.CandidateManager;
import brandon.davison.com.voting.users.Voter;
import brandon.davison.com.voting.users.VoterManager;
import brandon.davison.com.voting.voting.VoteSettings;

public class MainActivity extends AppCompatActivity {

    /* UI views */
    private TextView voterNameView, voterIdView, votesCastedView, votesLeftView, votesNeededView,
        candidate1View, candidate2View, winnerView;
    private Spinner spinner;
    private Button submitVote;

    /* Non-UI variables */

    // if Candidates have been read in from database yet
    private boolean ready = false;
    private SpinnerListener spinnerListener = new SpinnerListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();


        PermissionManager permissionManager = new PermissionManager(getApplicationContext(), MainActivity.this);
        permissionManager.requestStoragePermission();

        Intent intent = getIntent();
        final String voterId = intent.getStringExtra("voterID").substring(0, 5);
        final int candidates = intent.getIntExtra("candidates", -1);
        boolean started = intent.getBooleanExtra("started", true);
        final int votesAvailable = intent.getIntExtra("votesAvailable", -1);
        int votesToWin = intent.getIntExtra("votesToWin", -1);

        final VoteSettings voteSettings = new VoteSettings(candidates, started, votesAvailable, votesToWin);

        final VoterManager voterManager = new VoterManager(voteSettings);
        final CandidateManager candidateManager = new CandidateManager(voteSettings);


        int i = 0;
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

                            // Set votes available and
                            votesCastedView.setText("Votes Casted: " + String.valueOf(candidateManager.getVotesCasted()));
                            votesLeftView.setText("Votes Left: " + String.valueOf(voteSettings.getVotesAvailable() - candidateManager.getVotesCasted()));
                            votesNeededView.setText("Votes candidate needs to win: " + String.valueOf(voteSettings.getVotesToWin()));

                            // Setup Candidate Spinner
                            List<String> names = new ArrayList<>();
                            for (Candidate candidate : candidateManager.getCandidates()) {
                                names.add(candidate.getName());
                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(MainActivity.this,
                                    android.R.layout.simple_spinner_item, names);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dataAdapter);
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error, could not find voter",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        waitForCandidates.start();

        submitVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int id = spinnerListener.getId();

                Candidate candidate = candidateManager.getCandidate(id);
                candidate.addVote();

                Log.d("candidateTesting", "ID: " +id);
                Log.d("candidateTesting", "Name selected: " + candidate.getName());
                Log.d("candidateTesting", "Name selected: " + candidate.getVotesReceived());

                candidateManager.updateCandidate(id);

                if (id == 1) {
                    candidate1View.setText(candidate.getName() + " vote tally: " + String.valueOf(candidate.getVotesReceived()));
                } else if (id == 2) {
                    candidate2View.setText(candidate.getName() + " vote tally: " + String.valueOf(candidate.getVotesReceived()));
                }

                if (candidate.getVotesReceived() == voteSettings.getVotesToWin()) {
                    winnerView.setText(candidate.getName() + " won the election!");
                }
            }
        });
    }

    private void setupViews() {
        voterNameView = findViewById(R.id.voter_name);
        voterIdView = findViewById(R.id.voter_id);
        submitVote = findViewById(R.id.submit_vote);
        votesCastedView = findViewById(R.id.votes_casted);
        votesLeftView = findViewById(R.id.votes_left);
        votesNeededView = findViewById(R.id.votes_needed);
        candidate1View = findViewById(R.id.candidate1);
        candidate2View = findViewById(R.id.candidate2);
        winnerView = findViewById(R.id.winner);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(spinnerListener);
    }

    private void runCandidateDataTests(VoterManager voterManager, CandidateManager candidateManager, int voterId) {
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
}
