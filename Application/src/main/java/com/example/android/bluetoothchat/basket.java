package com.example.android.bluetoothchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;

import com.example.android.common.activities.SampleActivityBase;
import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;

import java.io.Serializable;
import java.util.ArrayList;


public class basket extends SampleActivityBase implements Serializable{
    public static final String TAG = "MainActivity";
    private boolean mLogShown;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket);



        //##########################################################################
        // L'idée est de recuperer le contenu du edittext de Send.java sur basket.java.
        // Normalement, cela fonctionne ...
        //Cependant il faut ensuite envoyer cette même variable sur un fragment : BluetoothChatFragment
        // Tout cela afin de faire un SetText de l'edittext permettant l'envoie Bluetooth.
        // Et c'est la que ca coince : Ici le code semble fonctionner (ou pas?)
        // La recuperation de cette même variable s'effectue ensuite dans ==> BluetoothChatFragment.java


// Recuperation de la 1ere activity (Fonctionne)
        Intent myIntent = getIntent();
        String str = "";
        ArrayList<String>str1= new ArrayList<String>();
        if (myIntent.hasExtra("yes")) {
             str1 =   getIntent().getExtras().getStringArrayList("yes");
             for(int i=0; i<str1.size();i++)
             android.util.Log.d("stari", str1.get(i));
        }

//###########################################################################################

        Intent intent = getIntent();

        String str2 = "";
        if (intent.hasExtra("tooto")) {
            str2 =   getIntent().getExtras().getString("yes");

                android.util.Log.d("TEST", str2);
        }





        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            BluetoothChatFragment fragment = new BluetoothChatFragment();
            Bundle bundle123 = new Bundle();
            bundle123.putSerializable("key123", str1);
            fragment.setArguments(bundle123);

            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem logToggle = menu.findItem(R.id.menu_toggle_log);
        logToggle.setVisible(findViewById(R.id.sample_output) instanceof ViewAnimator);
        logToggle.setTitle(mLogShown ? R.string.sample_hide_log : R.string.sample_show_log);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_toggle_log:
                mLogShown = !mLogShown;
                ViewAnimator output = (ViewAnimator) findViewById(R.id.sample_output);
                if (mLogShown) {
                    output.setDisplayedChild(1);
                } else {
                    output.setDisplayedChild(0);
                }
                supportInvalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Create a chain of targets that will receive log data */
    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }
}
