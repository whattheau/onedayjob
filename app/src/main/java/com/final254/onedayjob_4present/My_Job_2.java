package com.final254.onedayjob_4present;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class My_Job_2 extends TabActivity {

    TabHost TabHostWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myjob_2);

        //Assign id to Tabhost.
        TabHostWindow = (TabHost)findViewById(android.R.id.tabhost);

        //Creating tab menu.
        TabSpec TabMenu1 = TabHostWindow.newTabSpec("First tab");
        TabSpec TabMenu2 = TabHostWindow.newTabSpec("Second Tab");
        TabSpec TabMenu3 = TabHostWindow.newTabSpec("Third Tab");

        //Setting up tab 1 name.
        TabMenu1.setIndicator("Pending");
        //Set tab 1 activity to tab 1 menu.
        TabMenu1.setContent(new Intent(this,My_Job.class));

        //Setting up tab 2 name.
        TabMenu2.setIndicator("Approve");
        //Set tab 3 activity to tab 1 menu.
        TabMenu2.setContent(new Intent(this,My_Job_Approve.class));

        //Setting up tab 2 name.
        TabMenu3.setIndicator("Finished");
        //Set tab 3 activity to tab 3 menu.
        TabMenu3.setContent(new Intent(this,My_Job_Finished.class));

        //Adding tab1, tab2, tab3 to tabhost view.

        TabHostWindow.addTab(TabMenu1);
        TabHostWindow.addTab(TabMenu2);
        TabHostWindow.addTab(TabMenu3);

    }
}
