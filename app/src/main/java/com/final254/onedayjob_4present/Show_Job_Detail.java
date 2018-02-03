package com.final254.onedayjob_4present;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Show_Job_Detail extends Activity {

    TextView tvView1;


    String New_String_Product_id;



    private MyHttpPoster poster;

    String strMemberID = "";
    @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_job_detail);


        //*** Get Session Login
        final UserHelper usrHelper = new UserHelper(this);

        //*** Get Login Status
        if(!usrHelper.getLoginStatus())
        {
            Intent newActivity = new Intent(Show_Job_Detail.this, MainActivity.class);
            startActivity(newActivity);
        }

        //*** Get Member ID from Session
        strMemberID = usrHelper.getMemberID();



        tvView1 = (TextView) findViewById(R.id.tvView1);
       // editText_amount = (EditText)findViewById(R.id.editText_amount);


        /////////////// exitApp
        Button b_add = (Button) findViewById(R.id.b_add);
        b_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(Show_Job_Detail.this);
                dialog.setTitle("add_job");
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setCancelable(true);
                dialog.setMessage("Do you want to add jobs?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getBaseContext(),"Already Add! ",Toast.LENGTH_SHORT).show();

                        poster = new MyHttpPoster("http://thaiprojectapp.com/kaiau/add_jobs.php");

                        ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();


                         data.add(new BasicNameValuePair("dataname1", strMemberID));
                         data.add(new BasicNameValuePair("dataname2", New_String_Product_id));


                        poster.doPost(data, new Handler() {
                            public void handleMessage(android.os.Message msg) {
                                switch (msg.what) {
                                    case MyHttpPoster.HTTP_POST_OK:

                                        String resultValue = (String) msg.obj;
                                        //result.setText(resultValue);

                                        finish();


                                }
                            }


                        });


                    }

                });



                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        Toast.makeText(getBaseContext(),"cancel",Toast.LENGTH_SHORT).show();

                    }
                });

                dialog.show();
            }




        });

        showInfo();
    }


    public void showInfo() {

        final TextView tProductID = (TextView) findViewById(R.id.txtProductID);
        final TextView tProduct_name = (TextView) findViewById(R.id.txtProduct_name);
        final TextView tProduct_detail = (TextView) findViewById(R.id.txtProduct_detail);
        final TextView tProduct_price = (TextView) findViewById(R.id.txtProduct_price);
        final TextView tProduct_img_name1 = (TextView) findViewById(R.id.txtProduct_img_name1);



        final Intent intent = getIntent();
        String sProductID = intent.getStringExtra("job_ID");


        tvView1.setText("" + sProductID);


        String url = "https://imsu.co/u/13570254/show_jobs_detail.php?job_ID="+ sProductID;


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ProductID", sProductID));


        String resultServer = getHttpPost(url, params);

        String strProductID = "";
        String strProduct_name = "";
        String strProduct_detail = "";
        String strProduct_price = "";
        String strProduct_img_name1 = "";





        JSONObject c;
        try {
            c = new JSONObject(resultServer);


            strProductID = c.getString("job_ID");
            strProduct_name = c.getString("job_Name");
            strProduct_detail = c.getString("job_Detail");
            strProduct_price = c.getString("job_Price");
            strProduct_img_name1 = c.getString("job_Pic");




            New_String_Product_id = c.getString("job_ID");




            new DownloadImageTask((ImageView) findViewById(R.id.image1)).execute(strProduct_img_name1);


            if (!strProductID.equals("")) {
                tProductID.setText(strProductID);
                tProduct_name.setText(strProduct_name);
                tProduct_detail.setText(strProduct_detail);
                tProduct_price.setText(strProduct_price);
                tProduct_img_name1.setText(strProduct_img_name1);


            } else {
                tProductID.setText("-");
                tProduct_name.setText("-");
                tProduct_detail.setText("-");
                tProduct_price.setText("-");
                tProduct_img_name1.setText("-");


            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



    public String getHttpPost(String url, List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            // httpPost.setEntity(new UrlEncodedFormEntity(params));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

}


