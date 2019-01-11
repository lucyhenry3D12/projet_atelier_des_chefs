package com.example.android.bluetoothchat;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.common.activities.SampleActivityBase;

import java.util.ArrayList;

public class Send extends SampleActivityBase{

    private ListView mListView;
    ArrayList <String> prenoms=new ArrayList<String>();
    ArrayAdapter<String> adapter;
    int TEST = 12;

    EditText tp;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        final Intent myIntent = new Intent(this, basket.class);




        mListView = (ListView) findViewById(R.id.listView);

        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Send.this,
                android.R.layout.simple_list_item_1, prenoms);
        mListView.setAdapter(adapter);

        Button next = (Button) findViewById(R.id.envoie);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {





            //    for(int i = 0; i<prenoms.size();i++) {
                    myIntent.putExtra("yes", prenoms);
                  //  Log.d("variable", prenoms.get(i));
           //     }
                    startActivityForResult(myIntent, 0);






            }

        });

        Button next1 = (Button) findViewById(R.id.Ajoute);




        next1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {



                tp = (EditText) findViewById(R.id.edit_text_out1);
                String var = tp.getText().toString();
                tp.setText("");

                // ADD INFORMATION INTO DATABASE
                FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(getApplicationContext());
                SQLiteDatabase db = mDbHelper.getWritableDatabase();


                TextView textView = (TextView) view.findViewById(R.id.edit_text_out);

                ContentValues values = new ContentValues();

                values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, var);
                long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME
                        ,null,values);

//##################################################################################################


                // READ INFORMATION FROM DATABASE
                SQLiteDatabase db1 = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.

                String[] projection = {
                        BaseColumns._ID,
                        FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE,

                };



// Filter results W

// How you want the results sorted in the resulting Cursor


                Cursor cursor = db.query(
                        FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                        null,             // The array of columns to return (pass null to get all)
                        null,              // The columns for the WHERE clause
                        null,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        null               // The sort order
                );


                ArrayList prenoms = new ArrayList<>();
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, prenoms);
                mListView.setAdapter(adapter);
                while(cursor.moveToNext()) {
                    String itemId = cursor.getString(
                            cursor.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
                    prenoms.add(itemId);
                }
                cursor.close();





//##################################################################################################


                adapter.notifyDataSetChanged();
                myIntent.putExtra("yes",prenoms);







            }
            });

    }

}