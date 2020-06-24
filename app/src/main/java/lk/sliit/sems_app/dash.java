package lk.sliit.sems_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dash extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();;
    private String email;
    private String name;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        assert user != null;
        email=user.getEmail();
        name=user.getDisplayName();

        Log.d(TAG, "firebaseAuthWithGoogle:" + email);
    }
}
