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

    private static int votesAvailable, votesToWin;
    private static boolean started;
    private static HashMap<String, Object> model;

    // Indicates if settings could be read from db on startup
     static boolean settingsRead;

    public VoteSettings() {
        model = new HashMap<>();
        readSettingsFromDb();

        Log.d("SettingTesting", "Settings read: " + settingsRead);


        if (settingsRead) {
            try {
                this.votesAvailable = Integer.valueOf(model.get("votesAvailable").toString());
                Log.d("SettingTesting", "db type sdddddddddddddderror");

            } catch (NullPointerException e) {
                Log.d("SettingTesting", "db type error");
            }

        }
        Log.d("SettingTesting", "Moment of truth: " + votesToWin);
    }

    private void readSettingsFromDb() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference settingsRef = ref.child("settings");
        Log.d("SettingTesting", "read from db: " + settingsRef.getDatabase());

        settingsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model.put("votesAvailable", dataSnapshot.child("votesAvailable").getValue());
                model.put("votesToWin", dataSnapshot.child("votesToWin").getValue());
                model.put("started", dataSnapshot.child("started").getValue());

                Log.d("SettingTesting", "MapVal: " + model.get("started"));
                Log.d("SettingTesting", "Votes to win: " + model.get("votesToWin"));
                Log.d("SettingTesting", "read from db: " + dataSnapshot.getValue());

                settingsRead = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                settingsRead = false;
            }
        });
        Log.d("SettingTesting", "Settings read in method: " + settingsRead);

    }

    public boolean getSettingsRead() {
        return settingsRead;
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
