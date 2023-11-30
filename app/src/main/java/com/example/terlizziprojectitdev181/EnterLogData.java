package com.example.terlizziprojectitdev181;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EnterLogData extends AppCompatActivity {

    EditText etEnterLogData;

    Button btnSubmitLog;

    String logData, tableName;
    String AlertMsg = "The data entered here will be added to the currently viewed mileage table " +
            "on the previous page.";

    /*void EnterLogData(String tableName){
        this.tableName=tableName;
    }
 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_log_data);

        etEnterLogData=(EditText)findViewById(R.id.etAddLogData);
        btnSubmitLog=(Button)findViewById(R.id.btnSubmitLog);

        //Create an alert dialog box for data that will be stored in db

        AlertDialog.Builder adb = new AlertDialog.Builder(EnterLogData.this);
        adb.setMessage(AlertMsg);
        adb.setTitle("Alert! \nThis option uses the device database.");
        adb.setCancelable(false);
        adb.setPositiveButton("Continue",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        Toast.makeText(EnterLogData.this,"Please enter text to proceed.",Toast.LENGTH_LONG).show();
                        //get the current date/time stamp

                        String dateFormat = (new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()));

                        //insert date into text entry window
                        etEnterLogData.setText( dateFormat+": ");
                    }
                })
                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getApplicationContext(), Log2.class));
                    }

                });
        AlertDialog ad = adb.create();
        ad.show();



        btnSubmitLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get valid log data
                logData = etEnterLogData.getText().toString().trim();
                if(logData.equals(null)){
                    Toast.makeText(EnterLogData.this,"Please enter text to proceed.",Toast.LENGTH_LONG).show();
                }
                else{

                    //get the current displayed table on Log 2 page (getTable)
                    Intent intent=getIntent();
                    String table = intent.getStringExtra("tableName");


                    //Add user entered log data to db table
                    LogDB db = new LogDB(getApplicationContext());    //KEEP THIS!

                    //Create a new entry in a database table for this data
                    db.createEntry(table,logData,"true");

                    //Reset textentry window
                    etEnterLogData.setText("");


                    //startActivity(new Intent(getApplicationContext(), Log2.class));
                    startActivity(new Intent(EnterLogData.this,Log2.class));
                    intent.putExtra("Update Listview","true");
                    intent.putExtra("Table", table);
                }
            }//endOnCLick
        }); //end setOnClick


        BottomNavigationView bnv=findViewById(R.id.bottom_navigation);
        //bnv.setSelectedItemId(R.id.home);

        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int menuItemId =  item.getItemId();

                if (menuItemId ==  R.id.home) {

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                }
                else if(menuItemId == R.id.clear){
                    etEnterLogData.setText("");

                }
                else if (menuItemId ==  R.id.phone) {
                    startActivity(new Intent(getApplicationContext(), Phone.class));
                    return true;
                }

                else if (menuItemId ==  R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), Settings2.class));
                    return true;
                }
                else  {

                    return true;
                }


                return true;


            }
        });
    }  //end onCreate
}//end class