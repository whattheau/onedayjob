package com.final254.onedayjob_4present;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Page_Menu extends Activity {

    String strMemberID = "";
    String New_String_MemberID;
    String New_String_Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_menu);

        //*** Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //*** Get Session Login
        final UserHelper usrHelper = new UserHelper(this);

        //*** Get Login Status
        if(!usrHelper.getLoginStatus())
        {
            Intent newActivity = new Intent(Page_Menu.this, MainActivity.class);
            startActivity(newActivity);
        }

        //*** Get Member ID from Session
        strMemberID = usrHelper.getMemberID();

        //*** Show User Info
        showUserLoginInfo();



        //*** Button Next
        final Button btnNext1 = (Button) findViewById(R.id.btnNext1);
        btnNext1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Goto Activity 2
                Intent newActivity = new Intent(Page_Menu.this,MainActivity.class);
                startActivity(newActivity);
            }
        });

//        ////////////////////////////////


        final Button my_job = (Button) findViewById(R.id.my_job);
        my_job.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Goto Activity 2
                Intent newActivity = new Intent(Page_Menu.this,My_Job_2.class);
                startActivity(newActivity);
            }
        });



//        ////////////////////////////////
        //*** Button Logout
        final Button btnLogout = (Button) findViewById(R.id.Logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Clear Session
                usrHelper.deleteSession();

                // Goto Acitity2
                Intent newActivity = new Intent(Page_Menu.this,Login.class);
                startActivity(newActivity);
            }
        });
    }

    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Exit");
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setCancelable(true);
        dialog.setMessage("Do you want to exit?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialog.show();
    }  /////////////// onBackPressed





    public void showUserLoginInfo()
    {

        final TextView tMemberID = (TextView)findViewById(R.id.txtMemberID);
        final TextView tUsername = (TextView)findViewById(R.id.txtUsername);




        String url = "http://thaiprojectapp.com/kaiau/app_get_user_id.php";


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sMemberID", strMemberID));


        String resultServer  = MyHttpLogin.getHttpPost(url,params);

        String strMemberID = "";
        String strUsername = "";


        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strMemberID = c.getString("MemberID");
            strUsername = c.getString("Username");

            if(!strMemberID.equals(""))
            {
                tMemberID.setText(strMemberID);
                tUsername.setText(strUsername);

            }
            else
            {
                tMemberID.setText("-");
                tUsername.setText("-");

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
