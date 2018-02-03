package com.final254.onedayjob_4present;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 19/12/2560.
 */

public class Department_All_Food2 extends Activity{

    ArrayList<HashMap<String, String>> MyArrList;


    /////////////////////////////////////
    // String station_id = "";
    TextView tvView1;

    @SuppressLint("NewApi")

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_all_food2);


        //รับค่าส่งมาจากจากหน้า page_showdata2 เพื่อมาแสดง
        tvView1 = (TextView) findViewById(R.id.tvView1);


        final Intent intent = getIntent();
        String sCategory_ID = intent.getStringExtra("Category_ID");


        tvView1.setText("" + sCategory_ID);
        /////////////////////////////////////




        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ShowData();


        final Button btnSearch = (Button) findViewById(R.id.btnSearch);
        //btnSearch.setBackgroundColor(Color.TRANSPARENT);
        // Perform action on click
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShowData();
            }
        });

    }

    public void ShowData() {

        final ListView lisView1 = (ListView) findViewById(R.id.listView1);
        EditText strKeySearch = (EditText) findViewById(R.id.txtKeySearch);
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(strKeySearch.getWindowToken(), 0);

        final Intent intent = getIntent();
        String sCategory_ID = intent.getStringExtra("Category_ID");


        tvView1.setText("" + sCategory_ID);

      //  String url = "https://imsu.co/u/13570254/show_job_all_food.php";

        //http://thaiprojectapp.com/kaiau/show_category_all.php

       // String url = "http://thaiprojectapp.com/kaiau/show_job_all_food.php";
       // String url = "http://thaiprojectapp.com/kaiau/show_category_all_job_food?category_id="+ sCategory_ID;
        //String url = "http://thaiprojectapp.com/kaiau/show_category_all_job_food.php?category_id=1";
        String url = "http://thaiprojectapp.com/kaiau/show_category_all_job_food.php?category_id="+ sCategory_ID;


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("txtKeyword", strKeySearch.getText().toString()));

        try {
            JSONArray data = new JSONArray(getJSONUrl(url, params));

            MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();

                //  map.put("CreateDate", c.getString("CreateDate"));

                map.put("job_category_id", c.getString("job_category_id"));
                map.put("category_id", c.getString("category_id"));
                map.put("job_ID", c.getString("job_ID"));
                map.put("job_Name", c.getString("job_Name"));
                map.put("job_Detail", c.getString("job_Detail"));
                map.put("job_Price", c.getString("job_Price"));
                map.put("job_Pic", c.getString("job_Pic"));
                map.put("job_Date", c.getString("job_Date"));
                map.put("job_Time", c.getString("job_Time"));
                MyArrList.add(map);

            }

            lisView1.setAdapter(new Department_All_Food2.ImageAdapter(this));



//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }



          //  lisView1.setAdapter(new ImageAdapter(this));

            lisView1.setAdapter(new Department_All_Food2.ImageAdapter(this));


    // OnClick Item
            lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> myAdapter, View myView,
        int position, long mylng) {

           String sjob_ID = MyArrList.get(position).get("job_ID")
                  .toString();


            //เป็นโค้ดส่งค่าไปแสดงอีกหน้า
           Intent newActivity = new Intent(Department_All_Food2.this, Department_All_Food2_Detail2.class);
            newActivity.putExtra("Job_ID", sjob_ID);
            startActivity(newActivity);

        }
    });

} catch (JSONException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
        }




    public class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context c) {
            // TODO Auto-generated method stub
            context = c;
        }

        public int getCount() {
            // TODO Auto-generated method stub
            return MyArrList.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.department_all_food_column, null);
            }

            TextView txt_id = (TextView) convertView.findViewById(R.id.txt_id);
            txt_id.setPadding(10, 0, 0, 0);
            txt_id.setText(MyArrList.get(position).get("job_ID") + ".");

            // R.id.
            TextView txt1 = (TextView) convertView.findViewById(R.id.txt1);
            txt1.setPadding(5, 0, 0, 0);
            txt1.setText(MyArrList.get(position).get("job_Name") + ".");

            // R.id.
            TextView txt2 = (TextView) convertView.findViewById(R.id.txt2);
            txt2.setPadding(5, 0, 0, 0);
            txt2.setText(MyArrList.get(position).get("job_Detail") + ".");
            // R.id.

            // R.id.
            TextView txt3 = (TextView) convertView.findViewById(R.id.txt3);
            txt3.setPadding(5, 0, 0, 0);
            txt3.setText(MyArrList.get(position).get("job_Detail") + ".");
            // R.id.

            TextView txt4 = (TextView) convertView.findViewById(R.id.txtPrice);
            txt4.setPadding(5, 0, 0, 0);
            txt4.setText(MyArrList.get(position).get("job_Price"));

            TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            txtDate.setPadding(5, 0, 0, 0);
            txtDate.setText(MyArrList.get(position).get("job_Date"));

            TextView txtTime = (TextView) convertView.findViewById(R.id.txtTime);
            txtTime.setPadding(5, 0, 0, 0);
            txtTime.setText(MyArrList.get(position).get("job_Time"));

            ImageView imgview1 = (ImageView) convertView.findViewById(R.id.image1);

            new DownloadImageTask(imgview1).execute(MyArrList.get(position).get("job_Pic"));


            return convertView;




        }

    }

    public String getJSONUrl(String url, List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {

            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));


            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
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

