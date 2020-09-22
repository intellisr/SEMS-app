package lk.sliit.sems_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.apptakk.http_request.HttpRequest;
import com.apptakk.http_request.HttpRequestTask;
import com.apptakk.http_request.HttpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    public FirebaseAuth mAuth;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public DatabaseReference databaseReferencechild;
    public String uid;
    public String response;
    public String payload;

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
        int Aircon=0;
        int Fan=0;
        int Oven=0;
        int Microwaves=0;
        int Refrigerators=0;
        int Car=0;
        int Geysers=0;

        boolean isCheckedAircon = ((CheckBox) findViewById(R.id.aircon)).isChecked();
        if(isCheckedAircon){
             Aircon=1;
        }

        boolean isCheckedFan = ((CheckBox) findViewById(R.id.fan)).isChecked();
        if(isCheckedFan){
             Fan=1;
        }

        boolean isCheckedOven = ((CheckBox) findViewById(R.id.oven)).isChecked();
        if(isCheckedOven){
             Oven=1;
        }

        boolean isCheckedMicrowaves = ((CheckBox) findViewById(R.id.Microwaves)).isChecked();
        if(isCheckedMicrowaves){
             Microwaves=1;
        }

        boolean isCheckedRefrigerators= ((CheckBox) findViewById(R.id.Refrigerators)).isChecked();
        if(isCheckedRefrigerators){
             Refrigerators=1;
        }

        boolean isCheckedCar = ((CheckBox) findViewById(R.id.car)).isChecked();
        if(isCheckedCar){
             Car=1;
        }

        boolean isCheckedGeysers= ((CheckBox) findViewById(R.id.geysers)).isChecked();
        if(isCheckedGeysers){
             Geysers=1;
        }


        Home myhome = new Home(solar,maleCount,femaleCount,childCount,adultCount,empCount,income,district,size,Aircon,Fan,Oven,Microwaves,Refrigerators,Car,Geysers);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Home");
        databaseReferencechild = databaseReference.child(uid);
        databaseReferencechild.setValue(myhome);
        //payload="{ \'solar\':\'"+solar+"\',\'male\':\'"+maleCount+"\',\'female\':\'"+femaleCount+"\',\'child:\'"+childCount+"\',adult:"+adultCount+",emp:"+empCount+",income:"+income+",district:"+district+",size:"+size+",aircon:"+Aircon+",fan:"+Fan+",oven:"+Oven+",micro:"+Microwaves+",refig:"+Refrigerators+",car:"+Car+",geys:"+Geysers+"}";
        new HttpRequestTask(
                new HttpRequest("http://13.59.11.87:5000/predict_Profile", HttpRequest.POST, "{ \'solar\':\'"+solar+"\',\'male\':\'"+maleCount+"\',\'female\':\'"+femaleCount+"\',\'child:\'"+childCount+"\'}"),
                new HttpRequest.Handler() {
                    @Override
                    public void response(HttpResponse response) {
                        if (response.code == 200) {
                            Log.d(this.getClass().toString(), "Request successful!");
                        } else {
                            Log.e(this.getClass().toString(), "Request unsuccessful: " + response);
                        }
                    }
                }).execute();


        String result="result is " + response;
        Toast.makeText(powerpro.this, result,
                Toast.LENGTH_SHORT).show();
    }

    public static String makePostRequest(String stringUrl, String payload) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        String line;
        StringBuffer jsonString = new StringBuffer();

        uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        uc.setRequestMethod("POST");
        uc.setDoInput(true);
        uc.setInstanceFollowRedirects(false);
        uc.connect();
        OutputStreamWriter writer = new OutputStreamWriter(uc.getOutputStream(), "UTF-8");
        writer.write(payload);
        writer.close();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while((line = br.readLine()) != null){
                jsonString.append(line);
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        uc.disconnect();
        return jsonString.toString();
    }


}
