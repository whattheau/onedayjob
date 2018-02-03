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

public class Department_All_Food2_Detail2 extends Activity {

    TextView tvView1;


    String New_String_Product_id;
    String New_String_Product_name;
    String New_String_Product_price;


    // EditText editText_amount;
    //EditText editText_amount = "1";

    private MyHttpPoster poster;

    String strMemberID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_all_food2_detail2);


        //*** Get Session Login
        final UserHelper usrHelper = new UserHelper(this);

        //*** Get Login Status
        if(!usrHelper.getLoginStatus())
        {
            Intent newActivity = new Intent(Department_All_Food2_Detail2.this, MainActivity.class);
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


                AlertDialog.Builder dialog = new AlertDialog.Builder(Department_All_Food2_Detail2.this);
                dialog.setTitle("add_jobs");
                dialog.setIcon(R.mipmap.ic_launcher);
                dialog.setCancelable(true);
                dialog.setMessage("Do you want to add_jobs?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent intent = new Intent(Intent.ACTION_MAIN);
                        //intent.addCategory(Intent.CATEGORY_HOME);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // startActivity(intent);

                        // Toast.makeText(getBaseContext(),"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(getBaseContext(),"You Clicked :favorite ",Toast.LENGTH_SHORT).show();

                        poster = new MyHttpPoster("http://thaiprojectapp.com/kaiau/add_jobs.php");
                        // Data to sent
                        ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();
                        //  data.add(new BasicNameValuePair("dataname1", datatext_1));
                        // data.add(new BasicNameValuePair("dataname2", datatext_2));
                        //  data.add(new BasicNameValuePair("dataname3", datatext_3));

                        // data.add(new BasicNameValuePair("dataname1", New_String_temple_id));
                        // data.add(new BasicNameValuePair("dataname2", New_String_temple_id));


                        data.add(new BasicNameValuePair("dataname1", strMemberID));
                        data.add(new BasicNameValuePair("dataname2", New_String_Product_id));


                        poster.doPost(data, new Handler() {
                            public void handleMessage(android.os.Message msg) {
                                switch (msg.what) {
                                    case MyHttpPoster.HTTP_POST_OK:
                                        // ok
                                        String resultValue = (String) msg.obj;
                                        //result.setText(resultValue);



                                        //Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                                        // Toast.makeText(this, "Name : " + result, Toast.LENGTH_SHORT).show();
                                        // Toast.makeText(getBaseContext(), "ko " , Toast.LENGTH_SHORT).show();
                                        finish();

                                        //  case MyHttpPoster.HTTP_POST_ERROR:
                                        // Error
                                        //Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                        //    Toast.makeText(getBaseContext(), "Error" , Toast.LENGTH_SHORT).show();
                                        //    finish();

                                        //     break;
                                }
                            }

                            ;
                        });
                        //  }

                    }

                });

///////////////////จบfav



                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                        Toast.makeText(getBaseContext(),"cancel",Toast.LENGTH_SHORT).show();

                    }
                });

                dialog.show();
            }




        }); /////////////// exitApp

        //////////////////////////////////


//        // btnBack
//        final Button btnBack = (Button) findViewById(R.id.btnBack);
//        // Perform action on click
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent newActivity = new Intent(Show_Category_And_Product_Detail_Order.this, Show_Category_And_Product_Detail_Order2.class);
//                startActivity(newActivity);
//            }
//        });

        showInfo();
    }


    public void showInfo() {




        // txtMemberID,txtMemberID,txtUsername,txtPassword,txtName,txtEmail,txtTel
        final TextView tProductID = (TextView) findViewById(R.id.txtProductID);
        final TextView tProduct_name = (TextView) findViewById(R.id.txtProduct_name);
        final TextView tProduct_detail = (TextView) findViewById(R.id.txtProduct_detail);
        final TextView tProduct_price = (TextView) findViewById(R.id.txtProduct_price);
        final TextView tProduct_img_name1 = (TextView) findViewById(R.id.txtProduct_img_name1);


        final Intent intent = getIntent();
        String sProductID = intent.getStringExtra("Job_ID");


        tvView1.setText("" + sProductID);
        /////////////////////////////////////





        String url = "https://imsu.co/u/13570254/show_category_all_job_food_detail.php?job_ID="+ sProductID;






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
