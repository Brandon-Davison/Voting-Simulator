package brandon.davison.com.voting;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {

    private String candidate;
    private int id;

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        candidate = parent.getItemAtPosition(pos).toString();
        this.id = pos + 1; // candidate ID

        Toast.makeText(parent.getContext(),
                "Selected : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public String getCandidate() {
        return candidate;
    }

    public int getId() {
        return id;
    }

}
