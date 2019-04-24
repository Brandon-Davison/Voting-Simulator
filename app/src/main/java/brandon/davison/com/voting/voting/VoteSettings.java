package brandon.davison.com.voting.voting;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.HashMap;

public class VoteSettings implements PropertyChangeListener {

    private Date[] dateRange;

    private int votesAvailable, votesToWin, candidates;
    private boolean started, startedWasChanged = false;
    private HashMap<String, Object> model;

    private SettingEventListener settingValueEventListener;

    public VoteSettings() {
        settingValueEventListener = new SettingEventListener();
        settingValueEventListener.addChangeListener(this);
        model = new HashMap<>();

        readSettingsFromDb();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("votesToWin")) {
            votesToWin = Integer.valueOf(evt.getNewValue().toString());
            Log.d("SettingTesting", "Property changed: " + votesToWin);
        }
        if (evt.getPropertyName().equals("votesAvailable")) {
            votesAvailable =  Integer.valueOf(evt.getNewValue().toString());
            Log.d("SettingTesting", "Property changed: " + votesAvailable);
        }
        if (evt.getPropertyName().equals("started")) {
            started  = Boolean.valueOf(evt.getNewValue().toString());
            startedWasChanged = true;
            Log.d("SettingTesting", "Property changed: " + started);
        }
        if (evt.getPropertyName().equals("candidates")) {
            candidates = Integer.valueOf(evt.getNewValue().toString());
            Log.d("SettingTesting", "Property changed: " + candidates);
        }
    }

    public boolean getStartedWasChanged() {
        return startedWasChanged;
    }

    private void readSettingsFromDb() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference settingsRef = ref.child("settings");

        settingsRef.addListenerForSingleValueEvent(settingValueEventListener);
    }

    public void setStarted(boolean started) {
        this.started = started;
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

    public int getCandidates() {
        return candidates;
    }

    public Date[] getDateRange() {
        return dateRange;
    }


}
