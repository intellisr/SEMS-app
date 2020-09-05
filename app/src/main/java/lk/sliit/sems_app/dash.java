package lk.sliit.sems_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

public class dash extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public DatabaseReference databaseReferenceUnits;
    public DatabaseReference databaseReference2;
    public DatabaseReference databaseReferencecode;
    public String proPic;
    public String name;
    public String uid;
    public FirebaseAuth mAuth;
    public Boolean isPaired;
    private TextView uni1;
    private TextView uni2;
    private TextView uni3;
    private TextView uname;
    private TextView snumber;
    private ImageView userPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        uni1=findViewById(R.id.unit1);
        uni2=findViewById(R.id.unit2);
        uni3=findViewById(R.id.unit3);
        uname=findViewById(R.id.name);
        snumber=findViewById(R.id.sno);
        userPic=(ImageView)findViewById(R.id.upic);


        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        assert user != null;
        proPic = user.getPhotoUrl().toString();
        name=user.getDisplayName();
        uid=user.getUid();

        uname.setText(name);
        Glide.with(this).load(proPic).into(userPic);
        Glide.with(getApplicationContext()).load(proPic)
                .thumbnail(0.5f)
                .crossFade()
                .into(userPic);


        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Units");
        databaseReferenceUnits = databaseReference.child(uid);
        databaseReferenceUnits.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                live units = dataSnapshot.getValue(live.class);
                if(units.col10 != null) {
                    snumber.setText(units.getCol10());
                    uni1.setText("Unit1 : " + Long.toString(units.getCol7()));
                    uni2.setText("Unit2 : " + Long.toString(units.getCol8()));
                    uni3.setText("Unit3 : " + Long.toString(units.getCol9()));
                }else{
                    goFirstTimeActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    public void goForecastView(View view){
        Intent forecast=new Intent(this, forcast.class);
        startActivity(forecast);
    }

    public void goFirstTimeActivity(){
        Intent first=new Intent(this, first.class);
        startActivity(first);
        finish();
    }

}
