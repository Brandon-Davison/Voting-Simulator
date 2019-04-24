package brandon.davison.com.voting.users;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class CandidateEventListener implements ValueEventListener {

    private List<PropertyChangeListener> listener = new ArrayList<>();

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        try {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                String id = snapshot.child("id").getValue().toString();
                String name = snapshot.child("name").getValue().toString();
                String hasWon = snapshot.child("hasWon").getValue().toString();
                String votesReceived = snapshot.child("votesReceived").getValue().toString();

                notifyListeners(this, "name", "", name);
                notifyListeners(this, "id", "", id);
                notifyListeners(this, "hasWon", "false", hasWon);
                notifyListeners(this, "votesReceived", "", votesReceived);

                Candidate newCandidate = new Candidate(name, Integer.parseInt(id),
                        Boolean.parseBoolean(hasWon), Integer.parseInt(votesReceived));
            }
        } catch (Exception e) {
            Log.d("candidateTesting", "NULL ERRORS");
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Log.d("candidateTesting", "NULL ERRORS");
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
