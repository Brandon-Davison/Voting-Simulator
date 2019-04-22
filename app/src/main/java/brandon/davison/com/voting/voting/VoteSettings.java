package brandon.davison.com.voting.voting;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;


public class VoteSettings {

    private Date[] dateRange; // TODO: implement dateRange on voting


    private int votesAvailable, votesToWin;
    private boolean started;
    private HashMap<String, Object> model;

    public VoteSettings(int votesToWin, int votesAvailable, boolean started) {
        this.votesToWin = votesToWin;
        this.votesAvailable = votesAvailable;
        this.started = started;

        model = new HashMap<>();
        readSettingsFromDb();
    }

    public void readSettingsFromDb() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        DatabaseReference settingsRef = ref.child("settings");
        Log.d("SDFLSDFSsf", "read from db: " + settingsRef.getDatabase());

        settingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("SDFLSDFSsf", "read from db: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public HashMap<String, Object> getModel() {
        return model;
    }

    public boolean getStarted() {
        return started;
    }

    public int getVotesAvailable() {
        return votesAvailable;
    }

    public int getVotesToWin() {
        return votesToWin;
    }

    public Date[] getDateRange() {
        return dateRange;
    }


}
