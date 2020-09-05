package lk.sliit.sems_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class first extends AppCompatActivity {

    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public DatabaseReference databaseReferencechild;
    public DatabaseReference databaseReference2;
    public DatabaseReference databaseReferencechild2;
    public DatabaseReference databaseReferencechild2sub1;
    public DatabaseReference databaseReferencechild2sub2;
    public DatabaseReference databaseReferencechild2sub3;
    public FirebaseAuth mAuth;
    private TextView snumber;
    public String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        snumber=findViewById(R.id.sno);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        uid = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Paired");
        databaseReference2 = firebaseDatabase.getReference("Switch");

    }


    public void pairWithDevice(View view) {
        databaseReferencechild = databaseReference.child(snumber.getText().toString());
        databaseReferencechild.setValue(uid);

        databaseReferencechild2=databaseReference2.child(snumber.getText().toString());
        databaseReferencechild2sub1 = databaseReferencechild2.child("sub1");
        databaseReferencechild2sub1.setValue(1);
        databaseReferencechild2sub2 = databaseReferencechild2.child("sub2");
        databaseReferencechild2sub2.setValue(1);
        databaseReferencechild2sub3 = databaseReferencechild2.child("sub3");
        databaseReferencechild2sub3.setValue(1);
    }
}
