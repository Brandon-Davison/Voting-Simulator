package brandon.davison.com.voting.Auth;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MyIdValueEventListener implements ValueEventListener {

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        try {
           // String hasVoted = dataSnapshot.child("id").child("hasVoted").toString();
           // Log.e("idTesting", "HasVoted: " + hasVoted);

        } catch (Exception e) {
            Log.e("idTesting", "Type error from db");
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

}
