package brandon.davison.com.voting.voting;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.HashMap;


// TODO: when data is queried, it takes some milliseconds and runs in a Async.
// TODO   so when the data from this class is accessed, the query is still being performed.
public class VoteSettings implements PropertyChangeListener {

    private Date[] dateRange; // TODO: implement dateRange on voting

    private int votesAvailable, votesToWin;
    private boolean started;
    private HashMap<String, Object> model;

    MySettingValueEventListener settingValueEventListener;

    public VoteSettings() {
        settingValueEventListener = new MySettingValueEventListener();
        settingValueEventListener.addChangeListener(this);
        model = new HashMap<>();

        readSettingsFromDb();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        votesToWin = Integer.valueOf(evt.getNewValue().toString());
        Log.d("SettingTesting", "Property changed: " + votesToWin);
    }

    private void readSettingsFromDb() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference settingsRef = ref.child("settings");


        settingsRef.addListenerForSingleValueEvent(settingValueEventListener);

        Log.d("SettingTesting", "In vote settings: " + settingValueEventListener.getVotesToWin());
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
