package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText nameET, emailET, passET;
    String name, email, pass;
    Button button;

    static OutputStream outputStream;
    static InputStream inputStream;
    static JSONObject jsonObject;
    static String jsonStr = "";
    
    static String apiUrl = "http://kakufarm.in/kakufarm/apis/temp_delete_user.php";
    static int success;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        AsyncTaskClass asyncTaskClass = new AsyncTaskClass();

        nameET = findViewById(R.id.name);
        emailET = findViewById(R.id.email);
        passET = findViewById(R.id.pass);
        button = findViewById(R.id.btn);

        tv=findViewById(R.id.textView);

        button.setOnClickListener(view -> {
            name = nameET.getText().toString();
            email = emailET.getText().toString();
            pass = passET.getText().toString();

            asyncTaskClass.execute(name, email, pass);

        });
    }


    class AsyncTaskClass extends AsyncTask{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HashMap <String, String> apiData = new HashMap<String, String>();

            jsonObject=getData(apiData, "http://kakufarm.in/kakufarm/apis/temp_delete_rahul_show.php");

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            try {
                success = jsonObject.getInt("success");

                if(success==1)
                {
                    JSONArray jsonArray = jsonObject.getJSONArray("record");
                    for(int j = 0; j < jsonArray.length(); j++){
                        JSONObject joo = jsonArray.getJSONObject(j);
                        String name = joo.getString("name");
                        String email = joo.getString("email");
                        tv.setText("Name is : " + name + " Email is : " + email);
                    }
                }
                else
                {
                    tv.setText("failed");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }

    static void postData(HashMap<String, String> apiData){
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");

            StringBuilder postData = new StringBuilder();

            for (Map.Entry<String, String> param : apiData.entrySet()){
                if (postData.length()!=0)
                    postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }

            String postdata = postData.toString();

            byte[] postDataBytes = postdata.getBytes();

            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setDoOutput(true);
            outputStream = urlConnection.getOutputStream();
            outputStream.write(postDataBytes);

            inputStream = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine())!= null){
                sb.append(line+"\n");
            }
            inputStream.close();
            jsonStr = sb.toString();

            JSONObject jsonObject = new JSONObject(jsonStr);
            success = jsonObject.getInt("success");


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    static JSONObject getData(HashMap<String, String> apiData, String api_url){

        JSONObject json_obj=null;

        try {
            URL url = new URL(api_url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");

            StringBuilder postData = new StringBuilder();

            for (Map.Entry<String, String> param : apiData.entrySet()){
                if (postData.length()!=0)
                    postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            }

            String postdata = postData.toString();

            byte[] postDataBytes = postdata.getBytes();

            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setDoOutput(true);
            outputStream = urlConnection.getOutputStream();
            outputStream.write(postDataBytes);

            inputStream = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine())!= null){
                sb.append(line+"\n");
            }
            inputStream.close();
            jsonStr = sb.toString();

            Log.d("JSONstring", "getData: " + jsonStr);
            json_obj = new JSONObject(jsonStr);


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return json_obj;
    }

}