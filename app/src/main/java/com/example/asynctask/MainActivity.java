package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textview;
    Button button;
    EditText editText;

    String t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = findViewById(R.id.tv);
        button = findViewById(R.id.btn);
        editText = findViewById(R.id.ET);


        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        t=editText.getText().toString();

        Test te = new Test();
        te.execute(t);
    }

    class Test extends AsyncTask<String, String, String>
    {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd=ProgressDialog.show(MainActivity.this, "progress","wait for "+t+" secs");

        }

        @Override
        protected String doInBackground(String... strings) {

            int ti = Integer.parseInt(strings[0])*1000;

            publishProgress("waiting");

            String res="waited for "+ti+" secs";

            try {
                Thread.sleep(ti);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            textview.setText(s);
            pd.dismiss();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            textview.setText(values[0]);
        }
    }
}