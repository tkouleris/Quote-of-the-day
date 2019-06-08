package com.software.darkheart.quoteoftheday;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Context context = this;
        final String url ="http://quotesondesign.com/wp-json/posts?filter[orderby]=rand&filter[posts_per_page]=1";

        final Button button = findViewById(R.id.btn_quote);
        final TextView txt_content = findViewById(R.id.txt_content);
        final TextView txt_title = findViewById(R.id.txt_title);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject jresponse = response.getJSONObject(0);
                                    String title = " - ";
                                    title = title + jresponse.getString("title");
                                    String content = jresponse.getString("content");
                                    content = Html.fromHtml(content).toString();

                                    txt_content.setText(content);
                                    txt_title.setText(title);

                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("TAG", "Error: " + error.getMessage());
                            }
                        });

                // Access the RequestQueue through your singleton class.
                MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);

            }
        });
    }

}
