package com.final254.onedayjob_4present;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        //*** Button Next
        final Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Goto Activity 2
                Intent newActivity = new Intent(Login.this,MainActivity.class);
                startActivity(newActivity);
            }
        });


        //*** Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //*** Session Login
        final UserHelper usrHelper = new UserHelper(this);

        //*** txtUsername & txtPassword
        final EditText txtUser = (EditText)findViewById(R.id.txtUsername);
        final EditText txtPass = (EditText)findViewById(R.id.txtPassword);

        //*** Alert Dialog
        final AlertDialog.Builder ad = new AlertDialog.Builder(this);

        //*** Login Button
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String url = "http://thaiprojectapp.com/kaiau/app_login.php";

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("strUser", txtUser.getText().toString()));
                params.add(new BasicNameValuePair("strPass", txtPass.getText().toString()));

                /** Get result from Server (Return the JSON Code)
                 * StatusID = ? [0=Failed,1=Complete]
                 * MemberID = ? [Eg : 1]
                 * Error	= ?	[On case error return custom error message]
                 *
                 * Eg Login Failed = {"StatusID":"0","MemberID":"0","Error":"Incorrect Username and Password"}
                 * Eg Login Complete = {"StatusID":"1","MemberID":"2","Error":""}
                 */

                String resultServer  = MyHttpLogin.getHttpPost(url,params);

                /*** Default Value ***/
                String strStatusID = "0";
                String strMemberID = "0";
                String strError = "Unknow Status!";

                JSONObject c;
                try {
                    c = new JSONObject(resultServer);
                    strStatusID = c.getString("StatusID");
                    strMemberID = c.getString("MemberID");
                    strError = c.getString("Error");

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                // Prepare Login
                if(strStatusID.equals("0"))
                {
                    // Dialog
                    ad.setTitle("Error! ");
                    ad.setIcon(android.R.drawable.btn_star_big_on);
                    ad.setPositiveButton("Close", null);
                    ad.setMessage(strError);
                    ad.show();
                    txtUser.setText("");
                    txtPass.setText("");
                }
                else
                {
                    Toast.makeText(Login.this, "Login OK", Toast.LENGTH_SHORT).show();

                    // Create Session
                    usrHelper.createSession(strMemberID);

                    // Goto Activity2
                    Intent newActivity = new Intent(Login.this,Page_Menu.class);
                    startActivity(newActivity);

                }

            }
        });

    }


}

