package com.final254.onedayjob_4present;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 23/12/2560.
 */


public class QRCode extends Activity {

    String strMemberID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);


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
            Intent newActivity = new Intent(QRCode.this, MainActivity.class);
            startActivity(newActivity);
        }

        //*** Get Member ID from Session
        strMemberID = usrHelper.getMemberID();

        //*** Show User Info
        show_User_Profile();
    }


    public void show_User_Profile()
    {

        final TextView tMemberID = (TextView)findViewById(R.id.txtMemberID);
        final TextView tUsername = (TextView)findViewById(R.id.txtUsername);
        final TextView tPicture = (TextView)findViewById(R.id.txtPicture);
        final TextView tQrcode = (TextView)findViewById(R.id.txtPicture);




        String url = "http://thaiprojectapp.com/kaiau/app_qrcode.php?user_id="+ strMemberID;

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sMemberID", strMemberID));


        String resultServer  = MyHttpLogin.getHttpPost(url,params);

        String strMemberID = "";
        String strUsername = "";
        String strPicture = "";
        String strQrcode = "";


        JSONObject c;
        try {
            c = new JSONObject(resultServer);
            strMemberID = c.getString("user_id");
            strUsername = c.getString("username");
            strPicture = c.getString("picture");
            strQrcode = c.getString("qrcode");



            ImageView Picture = (ImageView)findViewById(R.id.picture);
            new DownloadImageTask(Picture).execute(strQrcode);


            if(!strMemberID.equals(""))
            {
                tMemberID.setText(strMemberID);
                tUsername.setText(strUsername);
                tPicture.setText(strPicture);
                tQrcode.setText(strQrcode);

            }
            else
            {
                tMemberID.setText("-");
                tUsername.setText("-");
                tPicture.setText("-");
                tQrcode.setText("-");

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }



}
