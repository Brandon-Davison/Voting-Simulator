package brandon.davison.com.voting.voting;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySettingValueEventListener implements ValueEventListener {

    private int votesToWin = 0;
    private List<PropertyChangeListener> listener = new ArrayList<>();

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        //model.put("votesAvailable", dataSnapshot.child("votesAvailable").getValue());
        //model.put("votesToWin", dataSnapshot.child("votesToWin").getValue());
        //model.put("started", dataSnapshot.child("started").getValue());

        setVotesToWin(55);
        notifyListeners(this, "votesToWin", 0, 55);
        Log.d("SettingTesting", "In listener: " + votesToWin);

        //Log.d("SettingTesting", "MapVal: " + model.get("started"));
        //Log.d("SettingTesting", "Votes to win: " + model.get("votesToWin"));
        //Log.d("SettingTesting", "Votes available: " + model.get("votesAvailable"));
        //Log.d("SettingTesting", "read from db: " + dataSnapshot.getValue());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    private void notifyListeners(Object object, String property, int oldValue, int newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }

    public void setVotesToWin(int a) {
        Log.d("SettingTesting", "In votesToWin: " + a);
        this.votesToWin = a;
    }

    public int getVotesToWin() {
        return votesToWin;
    }

}
