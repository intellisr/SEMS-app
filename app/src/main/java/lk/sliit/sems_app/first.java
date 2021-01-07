package lk.sliit.sems_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
    public GoogleSignInClient mGoogleSignInClient;

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

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    public void pairWithDevice(View view) {
        String itemNo = snumber.getText().toString();
        databaseReferencechild = databaseReference.child(itemNo);
        databaseReferencechild.setValue(uid);

        databaseReferencechild2=databaseReference2.child(itemNo);
        databaseReferencechild2sub1 = databaseReferencechild2.child("sub1");
        databaseReferencechild2sub1.setValue(1);
        databaseReferencechild2sub2 = databaseReferencechild2.child("sub2");
        databaseReferencechild2sub2.setValue(1);
        databaseReferencechild2sub3 = databaseReferencechild2.child("sub3");
        databaseReferencechild2sub3.setValue(1);

        Intent dash=new Intent(this, dash.class);
        startActivity(dash);
        finish();
    }

    public void signOut(View view){
        mAuth.signOut();
        mGoogleSignInClient.signOut();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
