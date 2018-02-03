package com.final254.onedayjob_4present;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by User on 26/1/2561.
 */

public class Profile_Payment extends TabActivity {

    TabHost TabHostWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_payment);

        //Assign id to Tabhost.
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);

        //Creating tab menu.
        TabHost.TabSpec TabMenu1 = TabHostWindow.newTabSpec("First tab");
        TabHost.TabSpec TabMenu2 = TabHostWindow.newTabSpec("Second Tab");


        //Setting up tab 1 name.
        TabMenu1.setIndicator("Profile");
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(this,My_Profile.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("Payment");
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this,My_Payment.class));

        //Setting up tab 2 name.


        //Adding tab1, tab2, tab3 to tabhost view.

        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);


    }
}
