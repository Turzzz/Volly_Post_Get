package com.example.volly_post_get;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView get_response_text,post_response_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button get_request_button=findViewById(R.id.get_data);
        Button post_request_button=findViewById(R.id.post_data);

        get_response_text=findViewById(R.id.get_respone_data);
        post_response_text=findViewById(R.id.post_respone_data);


        get_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendGetRequest();
            }
        });

        post_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequest();
            }
        });
    }

    private void postRequest() {
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url="http://192.168.30.145/files/volley_sample/post_data.php";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    post_response_text.setText("Data 1 : " + jsonObject.getString("Product_Name")+"\n");
                    post_response_text.append("Data 2 : " + jsonObject.getString("ID")+"\n");
                    post_response_text.append("Data 3 : " + jsonObject.getString("Price")+"\n");


                    Gson gson = new Gson();
                    String json = gson.toJson(jsonObject);
                    post_response_text.append(json +"\n");

//                    public class TestObjectToJson {
//                        private int data1 = 100;
//                        private String data2 = "hello";
//                            TestObjectToJson obj = new TestObjectToJson();
//                            Gson gson = new Gson();
//
//                            //convert java object to JSON format
//                            String json = gson.toJson(obj);
//
//                            System.out.println(json);
//                    }


                }
                catch (Exception e){
                    e.printStackTrace();
                    post_response_text.setText("POST DATA : unable to Parse Json");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                post_response_text.setText("Post Data : Response Failed");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("Product_Name","Watch");
                params.put("ID","12345");
                params.put("Price","300 taka");
                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void sendGetRequest() {
        //get working now
        //let's try post and send some data to server
        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        String url="http://192.168.30.145/files/volley_sample/get_data.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                get_response_text.setText("Data : "+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    get_response_text.setText("Data 1 :"+jsonObject.getString("data_1")+"\n");
                    get_response_text.append("Data 2 :"+jsonObject.getString("data_2")+"\n");
                    get_response_text.append("Data 3 :"+jsonObject.getString("data_3")+"\n");
                }
                catch (Exception e){
                    e.printStackTrace();
                    get_response_text.setText("Failed to Parse Json");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                get_response_text.setText("Data : Response Failed");
            }
        });

        queue.add(stringRequest);
    }
}
