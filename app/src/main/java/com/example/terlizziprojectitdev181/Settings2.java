package com.example.terlizziprojectitdev181;

import static android.app.PendingIntent.getActivity;

import static java.lang.Character.isDigit;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Settings2 extends AppCompatActivity {

    Button btnNewCar,btnSubmitSettings;
    TextView tvCarMakelbl, tvCarModellbl, tvMileagelbl, tvEnterAAA, tvEnterEC, tvEnterEPhone;
    EditText etCarMake, etCarModel,etStartMileage,etAAAPhone,etECPhone,etECName;
    Boolean continueNewCar=false;

    public static final String MY_PREFS_FILENAME = "com.example.terlizziprojectitdev181.Settings2";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);


        btnNewCar= findViewById(R.id.btnNewCar);
        etCarMake = findViewById(R.id.etCarMake);
        etAAAPhone = findViewById(R.id.etAAA);
        tvEnterAAA=findViewById(R.id.tvAAAlbl);
        tvEnterEC=findViewById(R.id.tvECNamelbl);
        tvEnterEPhone=findViewById(R.id.tvECPhonelbl);
        etECName = findViewById(R.id.etECName);
        etECPhone = findViewById(R.id.etECPhone);
        tvCarMakelbl = findViewById(R.id.tvCarMakelbl);
        tvCarModellbl=findViewById(R.id.tvCarModellbl);
        etCarModel=findViewById((R.id.etCarModel));
        tvMileagelbl=findViewById(R.id.tvMileagelbl);
        etStartMileage=findViewById(R.id.etStartMileage);
        btnSubmitSettings=findViewById(R.id.btnSubmitSettings);

        String AlertMsg="Entering a new car will move the current maintenence log," +
                "if it exists, to Downloads.";

        btnNewCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder adb = new AlertDialog.Builder(Settings2.this);
                adb.setMessage(AlertMsg);
                adb.setTitle("Alert! \nThis option uses the device database.");
                adb.setCancelable(false);
                adb.setPositiveButton("Continue",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User wishes to proceed with new car entry
                        tvCarMakelbl.setVisibility(View.VISIBLE);
                        etCarMake.setVisibility(View.VISIBLE);
                        tvCarModellbl.setVisibility(View.VISIBLE);
                        etCarModel.setVisibility(View.VISIBLE);
                        tvMileagelbl.setVisibility(View.VISIBLE);
                        etStartMileage.setVisibility(View.VISIBLE);

                        continueNewCar=true;

                        etAAAPhone.setVisibility((View.GONE));
                        tvEnterAAA.setVisibility((View.GONE));
                        etECPhone.setVisibility(View.GONE);
                        tvEnterEC.setVisibility(View.GONE);
                        etECName.setVisibility(View.GONE);
                        tvEnterEPhone.setVisibility(View.GONE);

                        //LogDB db = new LogDB(Settings2.this);

                        //try{
                            //db.open();
                        //}catch (Exception e){

                        //}



                    }
                    })
                        .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancels the adding a new car
                                //Make Car entry fields invisible
                                tvCarMakelbl.setVisibility(View.GONE);
                                etCarMake.setVisibility(View.GONE);
                                tvCarModellbl.setVisibility(View.GONE);
                                etCarModel.setVisibility(View.GONE);
                                tvMileagelbl.setVisibility(View.GONE);
                                etStartMileage.setVisibility(View.GONE);
                                continueNewCar = false;


                                etAAAPhone.setVisibility((View.VISIBLE));
                                tvEnterAAA.setVisibility((View.VISIBLE));
                                etECPhone.setVisibility(View.VISIBLE);
                                tvEnterEC.setVisibility(View.VISIBLE);
                                etECName.setVisibility(View.VISIBLE);
                                tvEnterEPhone.setVisibility(View.VISIBLE);
                            }

                });
                AlertDialog ad = adb.create();
                ad.show();




            }
        });



       btnSubmitSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text,version="0";
                String AAAPhone,ECPhone,ECName;

                String carMake = null, carModel=null, oldCarMake=null, oldCarModel=null,startMileage;
                boolean check = false;
                File mLog=null;

                //Save to user preference file

                //Check if Shared Pref file has values- first time saving

                carMake = etCarMake.getText().toString().trim();
                carModel = etCarModel.getText().toString().trim();
                startMileage = etStartMileage.getText().toString().trim();



                // Check if values were entered for car Make and car Model
                if(etCarMake.getVisibility()==View.VISIBLE) {
                    String convert=startMileage.replaceAll(",","");
                    try {
                        int numeral = Integer.parseInt(convert);
                        check = true;

                    } catch (Exception e) {
                        check = false;
                        Toast.makeText(Settings2.this, "Please enter a numeral for starting mileage.", Toast.LENGTH_LONG).show();
                    }
                    if (carMake.equals("") || carModel.equals("") || (check == false)) {
                        Toast.makeText(Settings2.this, "Please enter car information.", Toast.LENGTH_LONG).show();
                    } else {

                        //Process new car entry if necessary
                        if (continueNewCar == true) {

                            //File file = new File(MY_PREFS_FILENAME);
                            //String getPath = file.getAbsolutePath();
                            SharedPreferences pref = getSharedPreferences(
                                    MY_PREFS_FILENAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();

                            //Check if this is the first car entered.

                            if (!pref.contains("initialized")) {
                                carMake = etCarMake.getText().toString().trim();
                                carModel = etCarModel.getText().toString().trim();
                                startMileage = etStartMileage.getText().toString().trim();


                                editor.putString("Car Make", carMake);
                                editor.putString("Car Model", carModel);
                                editor.putString("Start Mileage", startMileage);
                                editor.putString("Old Car Make", carMake);
                                editor.putString("Old Car Model", carModel);
                                // editor.putString("New Car", "false");
                                //editor.putString("initialized", "true");
                                //editor.putString("Reset DB", "false");
                                editor.putString("Reset DB", "true");
                                editor.putString("First Car", "true");
                                editor.commit();

                                LogDB db = new LogDB(Settings2.this);

                                editor.putString("initialized", "true");



                                // Once new car data is entered, the edit text windows are removed
                                // from display and the regular(AAA, Emergency Contact) edit text
                                // buttons are displayed
                                Toast.makeText(Settings2.this, "NEW CAR INFORMATION SAVED", Toast.LENGTH_SHORT).show();
                                tvCarMakelbl.setVisibility(View.GONE);
                                etCarMake.setVisibility(View.GONE);
                                tvCarModellbl.setVisibility(View.GONE);
                                etCarModel.setVisibility(View.GONE);
                                tvMileagelbl.setVisibility(View.GONE);
                                etStartMileage.setVisibility(View.GONE);

                                etAAAPhone.setVisibility((View.VISIBLE));
                                tvEnterAAA.setVisibility((View.VISIBLE));
                                etECPhone.setVisibility(View.VISIBLE);
                                tvEnterEC.setVisibility(View.VISIBLE);
                                etECName.setVisibility(View.VISIBLE);
                                tvEnterEPhone.setVisibility(View.VISIBLE);

                                etCarMake.setText("");
                                etCarModel.setText("");
                                etStartMileage.setText("");


                                continueNewCar = false;
                                editor.putString("Reset DB","false");

                                editor.putString("New Car","false");
                                editor.commit();


                            } else {//not the first car ever being entered.  Delete old maint log and create new one. update Shared PRef

                                //push db table information as a file to downloads

                                editor.putString("Reset DB", "false");


                                try {


                                    oldCarMake = pref.getString("Old Car Make", null);
                                    oldCarModel = pref.getString("Old Car Model", null);
                                    startMileage = pref.getString("Start Mileage", null);


                                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                                    path = new File(path, "CarDrApp");

                                    //IF CarDrApp folder already exists in /storage/emulater/0/Downloads
                                    // in android studio, the download process will not work
                                    if (!(path.exists())) {
                                        path.mkdir();
                                        //Files.createDirectory(path.toPath());
                                    } else {
                                        //File savedFiles=new File("/storage/emulated/0/Download/CarDrApp");

                                        boolean deleted = path.delete();
                                        if (deleted == true) {
                                            Toast.makeText(Settings2.this, "deleted =" + path, Toast.LENGTH_LONG).show();
                                            path.mkdir();
                                        }
                                        else{
                                            Toast.makeText(Settings2.this,"Could not delete in /storage",Toast.LENGTH_LONG).show();
                                            path.mkdir();
                                        }
                                    }
                                    //path.mkdir();

                                    //append the log file name with a date to make it unique because android
                                    // will not overwrite a file in downloads, if it exists
                                    String dateFormat = (new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date()));
                                    version = dateFormat.replaceAll("/","");


                                    path = new File(path, ("mLog"+version + ".txt"));
                                    //mLog=path;


                                    Toast.makeText(Settings2.this, "Building LOG OUTPUT FILE: ", Toast.LENGTH_LONG).show();


                                    //Use the db to obtain the log information for the previous car
                                    LogDB db = new LogDB(Settings2.this);



                                    try {

                                        FileOutputStream fileOut = new FileOutputStream(path);
                                        text = db.getCarMaintLog(oldCarMake, oldCarModel, startMileage);

                                        fileOut.write(text.getBytes());
                                        fileOut.close();


                                        DownloadManager downloadManager = (DownloadManager) Settings2.this.getSystemService(DOWNLOAD_SERVICE);

                                        //downloadManager.addCompletedDownload(path.getName(), mLog.getName(), true, "text/plain", mLog.getAbsolutePath(), mLog.length(), true);
                                        downloadManager.addCompletedDownload(path.getName(), mLog.getName(), true, "text/plain", mLog.getAbsolutePath(), mLog.length(), true);

                                        Toast.makeText(Settings2.this, "LOG WRITTEN to Downloads.", Toast.LENGTH_LONG).show();


                                    } catch (IOException e) {
                                        //Toast.makeText(Settings2.this, "Unable to open log for writing.", Toast.LENGTH_LONG).show();
                                        //Toast.makeText(Settings2.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    //Toast.makeText(Settings2.this, "E1 " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                                //Get old car make and model in order to delete previous db maint log table


                                editor.putString("MLog Version", version);
                                editor.putString("Old Car Make", carMake);
                                editor.putString("Old Car Model", carModel);

                                //get new car info and store in Shared Preferences
                                carMake = etCarMake.getText().toString().trim();
                                carModel = etCarModel.getText().toString().trim();
                                startMileage = etStartMileage.getText().toString().trim();


                                editor.putString("Car Make", carMake);
                                editor.putString("Car Model", carModel);
                                editor.putString("Start Mileage", startMileage);
                                editor.putString("New Car","true");
                                editor.putString("Reset DB", "true");

                                // Create a new car db
                                LogDB db = new LogDB(Settings2.this);

                                editor.putString("initialized","true");
                                editor.commit();
                                Toast.makeText(Settings2.this, "NEW CAR INFORMATION SAVED", Toast.LENGTH_SHORT).show();

                                continueNewCar = false;


                                //Clear data entry fields for car make,model,mileage
                                etCarMake.setText("");
                                etCarModel.setText("");
                                etStartMileage.setText("");


                                //Make Car entry fields invisible
                                tvCarMakelbl.setVisibility(View.GONE);
                                etCarMake.setVisibility(View.GONE);
                                tvCarModellbl.setVisibility(View.GONE);
                                etCarModel.setVisibility(View.GONE);
                                tvMileagelbl.setVisibility(View.GONE);
                                etStartMileage.setVisibility(View.GONE);

                                etAAAPhone.setVisibility((View.VISIBLE));
                                tvEnterAAA.setVisibility((View.VISIBLE));
                                etECPhone.setVisibility(View.VISIBLE);
                                tvEnterEC.setVisibility(View.VISIBLE);
                                etECName.setVisibility(View.VISIBLE);
                                tvEnterEPhone.setVisibility(View.VISIBLE);


                            }//end else not first car being entered
                        }// end continue new car is true
                    }  // end enter new car info
                } else{
                    AAAPhone=etAAAPhone.getText().toString().trim();
                    ECPhone=etECPhone.getText().toString().trim();
                    ECName=etECName.getText().toString().trim();
                    etAAAPhone.setText("");
                    etECPhone.setText("");
                    etECName.setText("");

                    // Save remaining info  to Shared Preferences
                    SharedPreferences pref = getSharedPreferences(
                            MY_PREFS_FILENAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();


                    if(!AAAPhone.equals("")){
                        editor.putString("AAA Phone",AAAPhone);
                        Toast.makeText(Settings2.this, "AAA PHONE SAVED", Toast.LENGTH_SHORT).show();
                    }
                    if(!ECName.equals("")){
                        editor.putString("Emergency Contact Name",ECName);
                        Toast.makeText(Settings2.this, "EMERGENCY CONTACT NAME SAVED", Toast.LENGTH_SHORT).show();
                     }
                    if(!ECPhone.equals("")){
                        editor.putString("Emergency Contact Phone",ECPhone);
                        Toast.makeText(Settings2.this, "EMERGENCY CONTACT PHONE SAVED", Toast.LENGTH_SHORT).show();
                    }
                    editor.commit();
                    etAAAPhone.setText("");
                    etECName.setText("");
                    etECPhone.setText("");
                }//end else car Make and Model not entered
            }// end onCLick
       });// end Listener



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
                    etAAAPhone.setText("");
                    etECName.setText("");
                    etECPhone.setText("");
                    etCarMake.setText("");
                    etCarModel.setText("");
                    etStartMileage.setText("");
                    //startActivity(new Intent(getApplicationContext(), Settings2.class));
                    return true;
                }
                else if (menuItemId ==  R.id.phone) {
                    startActivity(new Intent(getApplicationContext(), Phone.class));
                    return true;
                }

                else if (menuItemId ==  R.id.settings) {

                    return true;
                }
                else {
                    startActivity(new Intent(getApplicationContext(), Log2.class));
                    return true;
                }




               // return true;


            }// end on itemSelected
        });  //end nav listener







    }  //end onCreate
} //end Class