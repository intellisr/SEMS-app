package lk.sliit.sems_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class forcast extends AppCompatActivity {

    public LineChart chart ;
    public Typeface mTf;
    public Spinner weeks;
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
    public AlertDialog dialog2;
    public int timeState = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forcast);

         daydata = new ArrayList<Double>();
         Entrydata = new ArrayList<String>();

        chart = findViewById(R.id.chart1);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        assert user != null;
        uid=user.getUid();

        weeks = (Spinner) findViewById(R.id.weeks);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.weekArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weeks.setAdapter(adapter);

    }

    private void setupChart(LineChart chart, LineData data, int color) {
        chart.setData(data);
        chart.invalidate();

        ((LineDataSet) data.getDataSetByIndex(0)).setCircleHoleColor(color);
        Description dis=new Description();
        dis.setText("GAP Forecasting");
        dis.setTextColor(Color.BLUE);
        dis.setTextSize(30);
        chart.setDescription(dis);
        chart.setDrawGridBackground(false);


        chart.setNoDataText("You must click forecast button");
        chart.setDrawBorders(true);
        chart.setBorderColor(Color.RED);

        chart.setTouchEnabled(true);
        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);
        chart.setBackgroundColor(Color.rgb(255, 255, 250));
        // set custom chart offsets (automatic offset calculation is hereby disabled)

        // get the legend (only possible after setting data)
        chart.getXAxis().setValueFormatter(new ValueFormatter() {
                                               @Override
                                               public String getFormattedValue(float value) {
                                                   String day=getNextDate((int)value);
                                                   return day;
                                               }
                                           });
        chart.getXAxis().setLabelCount(4, false);
        chart.animateX(2500);
        chart.notifyDataSetChanged();
        chart.invalidate();
    }

    private LineData getData(List<Entry> values) {

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(values, "WEEKS");
        // set1.setFillAlpha(110);
        set1.setFillColor(Color.RED);

        set1.setLineWidth(1.75f);
        set1.setCircleRadius(7f);
        set1.setCircleHoleRadius(3.5f);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(forcast.this);
        builder.setMessage("Processing...")
                            .setTitle("Please wait..");
        dialog2 = builder.create();
        dialog2.show();

        nWeeks = (int)weeks.getSelectedItemId() +1;
        SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(this);
        code = sharePref.getString("code",null);
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = getString(R.string.SEMSserver)+"forcastGAP";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("fname", code);
            jsonBody.put("weeks", nWeeks);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    dialog2.hide();
                    mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
                    try {
                        List<Entry> listObjects = new ArrayList<Entry>();
                        JSONArray jsonArray = new JSONArray(response);
                        double z=1;
                        for (int i=0; i < jsonArray.length(); i++) {
                            double x=0;
                            JSONArray arr=jsonArray.getJSONArray(i);
                            for (int j=0; j < arr.length(); j++) {
                                x =arr.getDouble(j);
                                float xval=(float) z;
                                float yval=(float) x;
                                Entry entry = new Entry(xval, yval);
                                listObjects.add(entry);
                                z++;
                            }
                            Log.i("SRA", arr.toString());
                        }
                        LineData data = getData(listObjects);
                        data.setValueTypeface(mTf);
                        setupChart(chart, data, Color.rgb(137, 230, 81));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("SRA", e.toString());
                    }

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

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    300000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public static String getNextDate(int  x) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date=java.util.Calendar.getInstance().getTime();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, x);
        return format.format(calendar.getTime());
    }

}
