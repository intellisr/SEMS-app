package lk.sliit.sems_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class forcast extends AppCompatActivity {

    public LineChart chart ;
    public Typeface mTf;
    public EditText weeks;
    public int nWeeks;
    public String code;
    public FirebaseAuth mAuth;
    public String uid;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public DatabaseReference databaseReferencechild;
    public DatabaseReference databaseReferenc2;
    public DatabaseReference databaseReferencechild2;
    public List<Double> daydata;
    public List<String> Entrydata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);

         daydata = new ArrayList<Double>();
         Entrydata = new ArrayList<String>();

        chart = findViewById(R.id.chart1);
        weeks = findViewById(R.id.weeks);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        assert user != null;
        uid=user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("forcastStatus");
        databaseReferencechild = databaseReference.child(uid);
        databaseReferencechild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer x=dataSnapshot.getValue(Integer.class);
                if(x == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(forcast.this);
                    builder.setMessage("Processing...")
                            .setTitle("Please wait..");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        databaseReferenc2 = firebaseDatabase.getReference("forcast");
        databaseReferencechild2 = databaseReferenc2.child(uid);
        databaseReferencechild2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                         daydata = (List<Double>) ds.getValue();
                         Entrydata.add(Arrays.toString(daydata.toArray()));
                    }
                    String arr=Arrays.toString(Entrydata.toArray());
                    AlertDialog.Builder builder = new AlertDialog.Builder(forcast.this);
                    builder.setMessage(arr)
                            .setTitle("Week wise Electricity forecast");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    //LineData data = getData(daydata);
                    //data.setValueTypeface(mTf);
                    //setupChart(chart, data, Color.rgb(137, 230, 81));
                }else{
                    chart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    private void setupChart(LineChart chart, LineData data, int color) {
        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(color);
        // no description text
        chart.getDescription().setEnabled(true);
         //chart.setDrawHorizontalGrid(false);
        // enable / disable grid background
        chart.setDrawGridBackground(true);
        //chart.getRenderer().getGridPaint().setGridColor(Color.WHITE & 0x70FFFFFF);
        // enable touch gestures
        chart.setTouchEnabled(true);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);
        chart.setBackgroundColor(Color.rgb(255, 255, 250));
        // set custom chart offsets (automatic offset calculation is hereby disabled)
        chart.setViewPortOffsets(10, 0, 10, 0);
        // add data
        chart.setData(data);
        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();
        l.setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisLeft().setSpaceTop(40);
        chart.getAxisLeft().setSpaceBottom(40);
        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setEnabled(true);
        //chart.getAxis(YAxis).setEnabled(true);
        // animate calls invalidate()...
        chart.animateX(2500);
    }

    private LineData getData(List<Entry> values) {

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "DataSet 1");
        // set1.setFillAlpha(110);
        set1.setFillColor(Color.RED);

        set1.setLineWidth(1.75f);
        set1.setCircleRadius(5f);
        set1.setCircleHoleRadius(2.5f);
        set1.setColor(Color.GREEN);
        set1.setCircleColor(Color.BLUE);
        set1.setHighLightColor(Color.WHITE);
        set1.setDrawValues(false);

        // create a data object with the data sets
        return new LineData(set1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.only_github, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewGithub: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/LineChartActivityColored.java"));
                startActivity(i);
                break;
            }
        }

        return true;
    }

    public void forcastUnits(View view){
        nWeeks = Integer.parseInt(weeks.getText().toString());
        SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(this);
        code = sharePref.getString("code",null);
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://18.222.100.162:5000/forcastGAP";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("fname", code);
            jsonBody.put("weeks", nWeeks);
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

    public void createGraph()
    {

    }
}
