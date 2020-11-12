package lk.sliit.sems_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class controll extends AppCompatActivity {
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public DatabaseReference databaseReferenceUnits;
    public DatabaseReference sub1;
    public DatabaseReference sub2;
    public DatabaseReference sub3;
    public String uid;
    public FirebaseAuth mAuth;
    public ImageView light1;
    public ImageView light2;
    public ImageView light3;
    public ImageView graph;
    public String code;
    public int unit1;
    public int unit2;
    public int unit3;
    private StorageReference mStorageRef;
    private StorageReference riversRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controll);

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        assert user != null;
        uid=user.getUid();

        light1=(ImageView) findViewById(R.id.ligh1);
        light2=(ImageView)findViewById(R.id.ligh2);
        light3=(ImageView)findViewById(R.id.ligh3);
        graph=(ImageView)findViewById(R.id.graph);

        SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(this);
        code = sharePref.getString("code",null);

        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Switch");
        databaseReferenceUnits = databaseReference.child(code);
        databaseReferenceUnits.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Switch units = dataSnapshot.getValue(Switch.class);
                if(units != null) {
                    unit1=units.getSub1();
                    unit2=units.getSub2();
                    unit3=units.getSub3();

                    if(unit1 == 0){
                        light1.setImageResource(R.drawable.cercleshapegray);
                    }else{
                        light1.setImageResource(R.drawable.bulb_white);
                    }

                    if(unit2 == 0){
                        light2.setImageResource(R.drawable.cercleshapegray);
                    }else{
                        light2.setImageResource(R.drawable.bulb_white);
                    }

                    if(unit3 == 0){
                        light3.setImageResource(R.drawable.cercleshapegray);
                    }else{
                        light3.setImageResource(R.drawable.bulb_white);
                    }


                }else{

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        String PICURL= getString(R.string.SEMSserver) + "static/" + code +"plot.png";
        Picasso.get().load(PICURL).into(graph);


    }

    public void switch1OnOff(View view) {
        sub1=databaseReferenceUnits.child("sub1");
        if(unit1 == 1){
            sub1.setValue(0);
        }else{
            sub1.setValue(1);
        }
    }

    public void switch2OnOff(View view) {
        sub2=databaseReferenceUnits.child("sub2");
        if(unit2 == 1){
            sub2.setValue(0);
        }else{
            sub2.setValue(1);
        }
    }
    public void switch3OnOff(View view) {
        sub3=databaseReferenceUnits.child("sub3");
        if(unit3 == 1){
            sub3.setValue(0);
        }else{
            sub3.setValue(1);
        }
    }

    public void checkAnamaly(View view) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = getString(R.string.SEMSserver)+"anamaly";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("fname", code);
            jsonBody.put("user", uid);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

            };

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
