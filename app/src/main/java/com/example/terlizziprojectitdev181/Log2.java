package com.example.terlizziprojectitdev181;

import static com.example.terlizziprojectitdev181.R.id.cbCheck;
import static com.example.terlizziprojectitdev181.R.id.etAddLogData;
import static com.example.terlizziprojectitdev181.R.layout.log_list_item_layout;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;


import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.security.AccessControlContext;
import java.util.ArrayList;

public class Log2 extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    TextView tvTest;
    EditText etEnterLogData;

    Button btnEnterLogData;

    public void setLog2Status(boolean log2Status) {
        this.Log2Status = log2Status;
    }

    ListView lvLog;

    TextView tv30,tv60,tv75,tv90,tv100,tvLogSpace,tvMaintLogTitle,tvLogTextSpace;

    public  String getDbTable() {
        return dbTable;
    }

    public void setDbTable(String dbTable) {
        this.dbTable = dbTable;
    }

    private String dbTable;

    int pos;

    public boolean getLog2Status() {
        return Log2Status;
    }

    LogAdapter adapter;

    private boolean Log2Status;

    String table;

    String  carMake,carModel;
    public static final String MY_PREFS_FILENAME = "com.example.terlizziprojectitdev181.Settings2";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log2);

        tvTest = (TextView) findViewById(R.id.tvTest);
        lvLog = (ListView) findViewById(R.id.lvLog);
        tv30=(TextView)findViewById(R.id.tv30);
        tv60=(TextView)findViewById(R.id.tv60);
        tv75=(TextView)findViewById(R.id.tv75);
        tv90=(TextView)findViewById(R.id.tv90);
        tv100=(TextView)findViewById(R.id.tv100);
        tvLogSpace=(TextView)findViewById(R.id.tvlogSpace);
        btnEnterLogData = (Button)findViewById(R.id.btnEnterLog);
        tvMaintLogTitle = (TextView)findViewById(R.id.tvLog2Title);
        tvLogTextSpace = (TextView)findViewById(R.id.tvLogTextSpace);


        LayoutInflater inflator;
        AccessControlContext myContext = getContext();


        //cbStatus = (CheckBox)findViewById(cbCheck);

        tvLogSpace.setText("");


        //Get stored car data from Shared Preferences
        SharedPreferences pref = getSharedPreferences(
                MY_PREFS_FILENAME, Context.MODE_PRIVATE);

        String updateLog = pref.getString("Update Log",null);
        carMake = pref.getString("Car Make","");
        carModel = pref.getString("Car Model","");
        String title="Maintenance Log: "+carMake+" "+carModel;
        tvMaintLogTitle.setText(title);

        //Check if a car was ever entered - db initialized
        if (pref.contains("initialized")) {
            try {

                tv30.setVisibility(View.VISIBLE);
                tv60.setVisibility(View.VISIBLE);
                tv75.setVisibility(View.VISIBLE);
                tv90.setVisibility(View.VISIBLE);
                tv100.setVisibility(View.VISIBLE);
                btnEnterLogData.setVisibility(View.VISIBLE);


                LogDB logDB = new LogDB(Log2.this);
                try{
                    logDB.open();
                }catch(Exception e) {
                    Toast.makeText(Log2.this, "Failed DB OPEN", Toast.LENGTH_LONG).show();
                    Toast.makeText(Log2.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                tvTest.setText("");

                // Load initial table from db (using getListData) for display

                setDbTable("Thirty");
                table = getDbTable();
                adapter = new LogAdapter(Log2.this, logDB.getListData(table));
                lvLog.setAdapter(adapter);
                lvLog.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                tvLogSpace.setHeight(250);
                tvLogTextSpace.setHeight(0);


            } catch (Exception e) {
                Toast.makeText(Log2.this, "ERROR:" + e.getMessage(), Toast.LENGTH_LONG).show();

            }

        // A car make and model has not yet been entered.
        } else {
            tvTest.setText("There is no current Maintenance Log.  You can enter a Car Make and Model info in Settings," +
                    " to create a Maintenance Log. Access to the device database is required to have a Maintenance Log.");
            tv30.setVisibility(View.INVISIBLE);
            tv60.setVisibility(View.INVISIBLE);
            tv75.setVisibility(View.INVISIBLE);
            tv90.setVisibility(View.INVISIBLE);
            tv100.setVisibility((View.INVISIBLE));
            btnEnterLogData.setVisibility((View.GONE));

            tvLogTextSpace.setHeight(600);

        }

        // Set listeners for mileage buttons. Get appropriate data from db and display


        tv30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDbTable("Thirty");
                LogDB ldb = new LogDB(Log2.this);

                adapter = new LogAdapter(Log2.this,  ldb.getListData("Thirty"));

                lvLog.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                tvLogSpace.setHeight(250);

            }
        });

        tv60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDbTable("Sixty");
                LogDB ldb = new LogDB(Log2.this);

                adapter = new LogAdapter(Log2.this, ldb.getListData("Sixty"));

                lvLog.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                tvLogSpace.setHeight(0);

            }
        });

        tv75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDbTable("SeventyFive");
                LogDB ldb = new LogDB(Log2.this);

                adapter = new LogAdapter(Log2.this, ldb.getListData("SeventyFive"));

                lvLog.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                tvLogSpace.setHeight(700);

            }
        });

        tv90.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDbTable("Ninety");
                LogDB ldb = new LogDB(Log2.this);

                adapter = new LogAdapter(Log2.this, ldb.getListData("Ninety"));

                lvLog.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                tvLogSpace.setHeight(0);

            }
        });

        tv100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDbTable("Old");
                LogDB ldb = new LogDB(Log2.this);

                adapter = new LogAdapter(Log2.this, ldb.getListData("Old"));


                lvLog.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                tvLogSpace.setHeight(300);

            }
        });

        //Process a selection of a List Item  Done Status - toggle button

        lvLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position+1;
                String table = getDbTable();
                Toast.makeText(Log2.this,"TABLE NAME: "+table, Toast.LENGTH_LONG).show();
                LogDB ldb = new LogDB(Log2.this);

                setLog2Status (ldb.getStatus(pos-1,table));

                boolean toggleStatus = !getLog2Status();

                ldb.updateStatus2(table,(pos),String.valueOf(toggleStatus));

                long result=ldb.updateStatus(Integer.toString(pos),toggleStatus,table);

                 setLog2Status(toggleStatus);

                //Display updated list

                ArrayList<MaintLogList> list2 = ldb.getListData(table);
                adapter = new LogAdapter(Log2.this,list2);
                adapter.setStatus(toggleStatus);

                lvLog.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });  // onClick

        btnEnterLogData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String table = getDbTable();

                // Pass table name for new data to new intent and start new Enter Log Data
                Intent intent = new Intent(Log2.this,EnterLogData.class);
                intent.putExtra("tableName",table);
                startActivity(intent);
            }
        });



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
    }//end onCreate



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


    }




}//end class