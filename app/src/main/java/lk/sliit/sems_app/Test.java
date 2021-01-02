package lk.sliit.sems_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Test extends AppCompatActivity {

    EditText boxtext;
    TextView viewtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        boxtext=findViewById(R.id.box);
        viewtext=findViewById(R.id.tfield);

    }


    public void show(View view){

        String textdata=boxtext.getText().toString();
        viewtext.setText(textdata);

    }
}
