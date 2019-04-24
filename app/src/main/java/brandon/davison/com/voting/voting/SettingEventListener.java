package brandon.davison.com.voting.voting;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class SettingEventListener implements ValueEventListener {

    private List<PropertyChangeListener> listener = new ArrayList<>();

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        try {
            String votesToWin = dataSnapshot.child("votesToWin").getValue().toString();
            String votesAvailable = dataSnapshot.child("votesAvailable").getValue().toString();
            String started = dataSnapshot.child("started").getValue().toString();
            String candidates = dataSnapshot.child("candidates").getValue().toString();

            notifyListeners(this, "votesToWin", "0", votesToWin);
            notifyListeners(this, "votesAvailable", "0", votesAvailable);
            notifyListeners(this, "started", "false", started);
            notifyListeners(this, "candidates", "0", candidates);
        } catch (Exception e) {
            Log.e("SettingTesting", "Type error from db");
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    private void notifyListeners(Object object, String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

}
