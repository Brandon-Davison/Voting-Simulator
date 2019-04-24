package brandon.davison.com.voting.users;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import brandon.davison.com.voting.voting.VoteSettings;

public class CandidateManager implements PropertyChangeListener {

    private static int currCandidateNumber = 0;
    private boolean hasWonChanged = false, candidatesReady = false;
    private ArrayList<Candidate> candidates = new ArrayList<>();
    private CandidateEventListener listener = new CandidateEventListener();
    private int candidatesCount = 0, votesCasted = 0;

    public CandidateManager(VoteSettings settings) {
        listener.addChangeListener(this);
        candidatesCount = settings.getCandidates();
        readInCandidates(settings);
    }

    // Only used to insert dummy data into database
    private void insertCandidates() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference candidateRef = db.child("candidates");

        Candidate trump = new Candidate("Donald Trump", 1, false, 0);
        Candidate hilary = new Candidate("Hilary Clinton", 2, false, 0);

        candidateRef.child("1").setValue(trump.getModel());
        candidateRef.child("2").setValue(hilary.getModel());
    }

    private void readInCandidates(final VoteSettings settings) {
        for (int i = 0; i < settings.getCandidates(); i++) {
            candidates.add(new Candidate("", -1, false, -1));
        }

        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference candidateRef = db.child("candidates");

        candidateRef.addListenerForSingleValueEvent(listener);
    }

    public void updateCandidate(int id) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference candidateRef = db.child("candidates").child(Integer.toString(id));

        for (Candidate candidate : candidates) {
            if (candidate.getid() == id) {
                candidateRef.updateChildren(candidate.getModel());
            }
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("name")) {
            String name = evt.getNewValue().toString();
            candidates.get(currCandidateNumber).setName(name);
         //   Log.d("candidateTesting", "name: " + name);
        }
        if (evt.getPropertyName().equals("id")) {
            int id = Integer.parseInt(evt.getNewValue().toString());
            candidates.get(currCandidateNumber).setId(id);
        //    Log.d("candidateTesting", "id: " + id);
        }
        if (evt.getPropertyName().equals("hasWon")) {
            boolean hasWon = Boolean.valueOf(evt.getNewValue().toString());
            candidates.get(currCandidateNumber).setHasWon(hasWon);
            hasWonChanged = true;
         //   Log.d("candidateTesting", "hasWon: " + hasWon);
        }
        if (evt.getPropertyName().equals("votesReceived")) {
            int votesReceived = Integer.valueOf(evt.getNewValue().toString());
            candidates.get(currCandidateNumber).setVotesReceived(votesReceived);
            votesCasted += votesReceived;
         //   Log.d("candidateTesting", "votesReceived: " + votesReceived);
        }

        Candidate candidate = candidates.get(currCandidateNumber);
        if (!candidate.getName().equals("") && candidate.getid() != -1 &&
            candidate.getVotesReceived() != -1 && hasWonChanged) {
            currCandidateNumber++;
            hasWonChanged = false;
           // Log.d("candidateTesting", "Candidate count & currNum: " + candidatesCount
           //     + ", " + currCandidateNumber);
            if (currCandidateNumber == candidatesCount) {
                candidatesReady = true;
            }
        }
    }

    public Candidate getCandidate(int id) {
        for (Candidate candidate : candidates) {
            if (candidate.getid() == id) {
                return candidate;
            }
        }
        return null;
    }

    public void castVote(int id) {
        votesCasted++;
        getCandidate(id).addVote();
    }

    public int getVotesCasted() {
        return votesCasted;
    }

    public boolean getCandidatesReady() {
        return candidatesReady;
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

}
