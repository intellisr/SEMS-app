package lk.sliit.sems_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;


public class powerpro extends AppCompatActivity {
    Spinner solarSpin;
    Spinner maleSpin;
    Spinner femaleSpin;
    Spinner childrenSpin;
    Spinner adultsSpin;
    Spinner employedSpin;
    Spinner incomeSpin;
    Spinner districtSpin;
    Spinner sizeSpin;
    EditText Airconx;
    EditText Fanx;
    EditText microx;
    EditText tvx;
    EditText refigx;
    EditText ironx;
    EditText geysx;
    EditText washx;

    public FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public DatabaseReference databaseReferencechild;
    public String uid;
    public int result;
    public String profile;
    public String units;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_powerpro);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        uid = user.getUid();

        solarSpin = (Spinner) findViewById(R.id.solarspin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.yesnoArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        solarSpin.setAdapter(adapter);

        maleSpin = (Spinner) findViewById(R.id.malespin);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.basicArray, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maleSpin.setAdapter(adapter2);

        femaleSpin = (Spinner) findViewById(R.id.femaleSpin);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.basicArray, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        femaleSpin.setAdapter(adapter3);

        childrenSpin = (Spinner) findViewById(R.id.childrenSpin);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.basicArray, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        childrenSpin.setAdapter(adapter4);

        adultsSpin = (Spinner) findViewById(R.id.adultsSpin);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this, R.array.basicArray, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adultsSpin.setAdapter(adapter5);

        employedSpin = (Spinner) findViewById(R.id.employedSpin);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this, R.array.basicArray, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employedSpin.setAdapter(adapter6);

        incomeSpin = (Spinner) findViewById(R.id.incomeSpin);
        ArrayAdapter<CharSequence> adapter7 = ArrayAdapter.createFromResource(this, R.array.incomeArray, android.R.layout.simple_spinner_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeSpin.setAdapter(adapter7);

        districtSpin = (Spinner) findViewById(R.id.districtSpin);
        ArrayAdapter<CharSequence> adapter8 = ArrayAdapter.createFromResource(this, R.array.districArray, android.R.layout.simple_spinner_item);
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpin.setAdapter(adapter8);

        sizeSpin = (Spinner) findViewById(R.id.sizeSpin);
        ArrayAdapter<CharSequence> adapter9 = ArrayAdapter.createFromResource(this, R.array.sizeArray, android.R.layout.simple_spinner_item);
        adapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpin.setAdapter(adapter9);

         Airconx = findViewById(R.id.aircon);
         Fanx= findViewById(R.id.fan);
         microx= findViewById(R.id.Microwaves);
         tvx= findViewById(R.id.tv);
         refigx= findViewById(R.id.Refrigerators);
         ironx= findViewById(R.id.iron);
         geysx= findViewById(R.id.geysers);
         washx= findViewById(R.id.wash);

    }



    public void submitMyHome(View view) throws MalformedURLException {
        int solar=(int)solarSpin.getSelectedItemId();
        int maleCount=(int)maleSpin.getSelectedItemId();
        int femaleCount=(int)femaleSpin.getSelectedItemId();
        int childCount=(int)childrenSpin.getSelectedItemId();
        int adultCount=(int)adultsSpin.getSelectedItemId();
        int empCount=(int)employedSpin.getSelectedItemId();
        int income=(int)incomeSpin.getSelectedItemId();
        int district=(int)districtSpin.getSelectedItemId();
        int size=(int)sizeSpin.getSelectedItemId();
        int Aircon=Integer.parseInt(Airconx.getText().toString());
        int Fan=Integer.parseInt(Fanx.getText().toString());
        int micro=Integer.parseInt(microx.getText().toString());
        int tv=Integer.parseInt(tvx.getText().toString());
        int refig=Integer.parseInt(refigx.getText().toString());
        int iron=Integer.parseInt(ironx.getText().toString());
        int geys=Integer.parseInt(geysx.getText().toString());
        int wash=Integer.parseInt(washx.getText().toString());


        Home myhome = new Home(solar, maleCount, femaleCount, childCount, adultCount, empCount, income, district, size, Aircon, Fan, micro, tv, refig, iron, geys, wash);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Home");
        databaseReferencechild = databaseReference.child(uid);
        databaseReferencechild.setValue(myhome);
        //payload="{ \'solar\':\'"+solar+"\',\'male\':\'"+maleCount+"\',\'female\':\'"+femaleCount+"\',\'child:\'"+childCount+"\',adult:"+adultCount+",emp:"+empCount+",income:"+income+",district:"+district+",size:"+size+",aircon:"+Aircon+",fan:"+Fan+",oven:"+Oven+",micro:"+Microwaves+",refig:"+Refrigerators+",car:"+Car+",geys:"+Geysers+"}";

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = getString(R.string.SEMSserver)+"predict_Profile";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("solar", 0);
            jsonBody.put("male", maleCount);
            jsonBody.put("female", femaleCount);
            jsonBody.put("child", childCount);
            jsonBody.put("adult", adultCount);
            jsonBody.put("emp", empCount);
            jsonBody.put("income", income);
            jsonBody.put("district", maleCount);
            jsonBody.put("size", size);
            jsonBody.put("aircon", Aircon);//aircon
            jsonBody.put("fan", Fan);//fan
            jsonBody.put("micro", micro);//mcwave
            jsonBody.put("tv", tv);//tv
            jsonBody.put("refig", refig);//refig
            jsonBody.put("iron", iron);//iron
            jsonBody.put("geys", geys);//gays
            jsonBody.put("wash", wash);//washmach
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    double num = Double.parseDouble(response);
                    result=(int)num;
                    if(result == 0){
                        profile="Low Profile";
                        units="0 - 160.5 Units";
                    }else if(result == 2){
                        profile="Medium Profile";
                        units="160.5 - 440 Units";
                    }else{
                        profile="High Profile";
                        units="Over 440 Units";
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(powerpro.this);
                    builder.setMessage("You could used "+units+" per month")
                            .setTitle("Your home have a "+profile);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    SharedPreferences sharePref= PreferenceManager.getDefaultSharedPreferences(powerpro.this);
                    SharedPreferences.Editor editor = sharePref.edit();
                    editor.putString("profile",profile);
                    editor.apply();

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


    public void submitHomeToPredict(View view) throws MalformedURLException {
        int solar=(int)solarSpin.getSelectedItemId();
        int maleCount=(int)maleSpin.getSelectedItemId();
        int femaleCount=(int)femaleSpin.getSelectedItemId();
        int childCount=(int)childrenSpin.getSelectedItemId();
        int adultCount=(int)adultsSpin.getSelectedItemId();
        int empCount=(int)employedSpin.getSelectedItemId();
        int income=(int)incomeSpin.getSelectedItemId();
        int district=(int)districtSpin.getSelectedItemId();
        int size=(int)sizeSpin.getSelectedItemId();
        int Aircon=Integer.parseInt(Airconx.getText().toString());
        int Fan=Integer.parseInt(Fanx.getText().toString());
        int micro=Integer.parseInt(microx.getText().toString());
        int tv=Integer.parseInt(tvx.getText().toString());
        int refig=Integer.parseInt(refigx.getText().toString());
        int iron=Integer.parseInt(ironx.getText().toString());
        int geys=Integer.parseInt(geysx.getText().toString());
        int wash=Integer.parseInt(washx.getText().toString());



        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = getString(R.string.SEMSserver)+"predict_Profile";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("solar", 0);
            jsonBody.put("male", maleCount);
            jsonBody.put("female", femaleCount);
            jsonBody.put("child", childCount);
            jsonBody.put("adult", adultCount);
            jsonBody.put("emp", empCount);
            jsonBody.put("income", income);
            jsonBody.put("district", district);
            jsonBody.put("size", size);
            jsonBody.put("aircon", Aircon);//aircon
            jsonBody.put("fan", Fan);//fan
            jsonBody.put("micro", micro);//mcwave
            jsonBody.put("tv", tv);//tv
            jsonBody.put("refig", refig);//refig
            jsonBody.put("iron", iron);//iron
            jsonBody.put("geys", geys);//gays
            jsonBody.put("wash", wash);//washmach
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    double num = Double.parseDouble(response);
                    result=(int)num;
                    if(result == 0){
                        profile="Law Profile";
                        units="0 - 160.5 Units";
                    }else if(result == 2){
                        profile="Medium Profile";
                        units="160.5 - 440 Units";
                    }else{
                        profile="High Profile";
                        units="Over 440 Units";
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(powerpro.this);
                    builder.setMessage("It could used "+units+" per month")
                            .setTitle("This home have a "+profile);
                    AlertDialog dialog = builder.create();
                    dialog.show();


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
