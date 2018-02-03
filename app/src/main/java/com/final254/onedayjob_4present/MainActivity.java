package com.final254.onedayjob_4present;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
        ArrayList<HashMap<String, String>> MyArrList;

        @SuppressLint("NewApi")
        @Override

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Permission StrictMode
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }



            //Button
            Button find_job = (Button) findViewById(R.id.find_job);
            find_job.setOnClickListener (new View.OnClickListener() {
                public void onClick(View  V) {
                    Intent intent =  new Intent (V.getContext(), Department_All.class);
                    startActivityForResult(intent, 0);
                }

            });//Button

            Button profile = (Button) findViewById(R.id.profile);
            profile.setOnClickListener (new View.OnClickListener() {
                public void onClick(View  V) {
                    Intent intent =  new Intent (V.getContext(), Profile_Payment.class);
                    startActivityForResult(intent, 0);
                }

            });//Button

            //MyJobs
            final Button MyJobs = (Button) findViewById(R.id.MyJobs);
            MyJobs.setOnClickListener (new View.OnClickListener() {
                public void onClick(View  V) {
                    Intent intent =  new Intent (V.getContext(), My_Job_2.class);
                    startActivityForResult(intent, 0);
                }

            });//Button


//            Button find_job_for_you = (Button) findViewById(R.id.find_job_for_you);
//            find_job_for_you.setOnClickListener (new View.OnClickListener() {
//                public void onClick(View  V) {
//                    Intent intent =  new Intent (V.getContext(), Job_menu.class);
//                    startActivityForResult(intent, 0);
//                }
//
//            });//Button

            Button qrcode = (Button) findViewById(R.id.qrcode);
            qrcode.setOnClickListener (new View.OnClickListener() {
                public void onClick(View  V) {
                    Intent intent =  new Intent (V.getContext(), QRCode.class);
                    startActivityForResult(intent, 0);
                }

            });//Button
            /////////////// onBackPressed








            ShowData();


            // btnSearch
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
        // listView1

        final ListView lisView1 = (ListView) findViewById(R.id.listView1);

        // keySearch
        EditText strKeySearch = (EditText) findViewById(R.id.txtKeySearch);

        // Disbled Keyboard auto focus
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);






        //String url = "https://imsu.co/u/13570254/show_jobs.php";
        String url = "http://thaiprojectapp.com/kaiau/show_jobs.php";

        // List<NameValuePair> params = new ArrayList<NameValuePair>();
        // params.add(new BasicNameValuePair("sStation_id", Station_id));

        // Paste Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("txtKeyword", strKeySearch.getText().toString()));
        // params.add(new BasicNameValuePair("txtKeyword", strKeySearch.getText().toString()));
        // httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        try {
            JSONArray data = new JSONArray(getJSONUrl(url, params));

            MyArrList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for (int i = 0; i < data.length(); i++) {
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("job_ID", c.getString("job_ID"));
                map.put("job_Name", c.getString("job_Name"));
                map.put("job_Detail", c.getString("job_Detail"));
                map.put("job_Price", c.getString("job_Price"));
                map.put("job_Pic", c.getString("job_Pic"));
                map.put("job_Date", c.getString("job_Date"));
                map.put("job_Time", c.getString("job_Time"));

                //txtTime



                MyArrList.add(map);

            }



            lisView1.setAdapter(new ImageAdapter(this));

            // OnClick Item
            lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> myAdapter, View myView,
                                        int position, long mylng) {

                    // String sProduct_ID = MyArrList.get(position).get("id")
                    //        .toString();
                    String sProduct_ID = MyArrList.get(position).get("job_ID")
                            .toString();


                    // Intent newActivity = new Intent(Show_Product.this, Show_Product_Detail.class);
                    //Intent newActivity = new Intent(Show_Category_And_Product.this, Show_Category_And_Product_Detail_Favorite.class);
                    //   Intent newActivity = new Intent(Show_Category_And_Product.this, Show_Category_And_Product_Detail.class);
                    // newActivity.putExtra("ProductID", sProduct_ID);

                    Intent newActivity = new Intent(MainActivity.this, Show_Job_Detail.class);
                    newActivity.putExtra("job_ID", sProduct_ID);



                    startActivity(newActivity);

                }
            });


//            lisView1.setAdapter(new ImageAdapter(this));
//
//            final Button btnJoin = (Button) findViewById(R.id.btn_join);
//            // OnClick Item
//            lisView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                public void onItemClick(AdapterView<?> myAdapter, View myView,
//                                        int position, long mylng) {
//
//                    String sProduct_ID = MyArrList.get(position).get("job_ID")
//                            .toString();
//
//
//
//                    Intent newActivity = new Intent(MainActivity.this, Show_Job_Detail.class);
//                    newActivity.putExtra("job_ID", sProduct_ID);
//                    startActivity(newActivity);
//
//                }
//            });

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
                convertView = inflater.inflate(R.layout.activity_show_job_column, null);
            }

            // R.id.
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
            //httpPost.setEntity(new UrlEncodedFormEntity(params));
            httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));


            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
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

