package com.example.terlizziprojectitdev181;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class LogDB
{

    public static final String KEY_ROWID = "_id";
    public static final String KEY_DESC = "description";
    public static final String KEY_STATUS = "status";

    public static final String[] COLUMNS= {KEY_ROWID,KEY_DESC,KEY_STATUS};
    private String DATABASE_NAME="Logdb4";
    private String DATABASE_TABLE1 = "Thirty";

    public String getDATABASE_NAME() {
        return DATABASE_NAME;
    }

    private String DATABASE_TABLE2 = "Sixty";
    private String DATABASE_TABLE3 ="SeventyFive";
    private String DATABASE_TABLE4 = "Ninety";

    private String DATABASE_TABLE5 = "Old";
    private int DATABASE_VERSION = 1;

    private int newCar=0;

   private String OLD_DATABASE_TABLE;

    private String Mileage;

    public String getMileage() {
        return Mileage;
    }

    public void setMileage(String mileage) {
        Mileage = mileage;
    }

    private int miles;
    private String oldCarMake,oldCarModel;





    private DBHelper2 dbh;

    public SQLiteDatabase getOurDB() {
        return ourDB;
    }

    //private final Context ourContext;
    private static SQLiteDatabase ourDB;

    private final Context ourContext;




    private String log;

    public String getLog() {
        return log;
    }

    public static final String MY_PREFS_FILENAME = "com.example.terlizziprojectitdev181.Settings2";



    public LogDB(Context context)
    {
        ourContext=context;
        SharedPreferences pref = ourContext.getSharedPreferences(
                MY_PREFS_FILENAME, Context.MODE_PRIVATE);


        String oldCarMake = pref.getString("Old Car Make","");
        String carMake = pref.getString("Car Make","");


        String carModel = pref.getString("Car Model","");
        String resetDB = pref.getString("Reset DB","");


       // Update the db for a new car just entered
        if(resetDB.equals("true")) {

            //Get car entry status from shared preferences
            SharedPreferences.Editor editor = pref.edit();
            String firstCar = pref.getString("First Car","false");

            // Check if this is the first car being entered
            if(firstCar.equals("false")) {

                // Close and delete the previous car db, its already written to downloads
                ourDB.close();
                ourContext.deleteDatabase(DATABASE_NAME);

            }
            else{
                //This is the first car entered, nothing to close, update status for next car
                editor.putString("First Car","false");
            }

            // Create this new car db by updating the db name
            newCar+=1;
            String oldDBName=DATABASE_NAME;
            DATABASE_NAME=carMake+carModel+(Integer.toString(newCar));


            // Update status in shared preferences.
            // If the user jumps right to Log page, no need to update the db in constructor
            editor.putString("ourDB",DATABASE_NAME);
            editor.putString("initialized","true");
            editor.putString("Reset DB", "false");
            editor.putString("New Car","false");
            editor.putString("First Car","false");
            editor.commit();



        // resetDB is not true
        }else{

            String defaultDB=DATABASE_NAME;

            DATABASE_NAME=pref.getString("ourDB",defaultDB);

        }




    }//end constructor

    private class DBHelper2 extends SQLiteOpenHelper
            //class DBHelper extends SQLiteOpenHelper
    {


        public DBHelper2(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION );

        }

        // only run when db version changes
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Toast.makeText(ourContext,"IN UPGRADE DB",Toast.LENGTH_LONG).show();
            //db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE);
            //String base_TableName=getDATABASE_TABLE();
            //String tname = "Thirty";
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE1);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE3);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE4);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE5);
                onCreate(db);

        }

        // Only run when db doesn't exist or is created very first time
        @Override
        public void onCreate(SQLiteDatabase db) {

            String status;
            Object dbException;


                String string1 = "CREATE TABLE " + DATABASE_TABLE1 + " ("
                        + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + KEY_DESC + " TEXT, "
                        + KEY_STATUS + " STRING);";

                db.execSQL(string1);

               String string2 = "CREATE TABLE " + DATABASE_TABLE2 + " ("
                        + KEY_ROWID +  " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + KEY_DESC + " TEXT, "
                       + KEY_STATUS + " STRING);";

            db.execSQL(string2);

            String string3 = "CREATE TABLE " + DATABASE_TABLE3 + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_DESC + " TEXT, "
                    + KEY_STATUS + " STRING);";

            db.execSQL(string3);


            String string4 = "CREATE TABLE " + DATABASE_TABLE4 + " (" +
                    KEY_ROWID +  " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_DESC + " TEXT, "
                    + KEY_STATUS + " STRING);";

            db.execSQL(string4);

            String string5 = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE5 + " (" +
                    KEY_ROWID +  " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_DESC + " TEXT, "
                    + KEY_STATUS + " STRING);";

            db.execSQL(string5);

                //Build 30,000 mile maintenance data
                String SQLString2 = "INSERT INTO " + DATABASE_TABLE1 + " (\"description\",\"status\") VALUES('At 30,000: Check air filter.','false');";
                db.execSQL(SQLString2);
                String SQLString3 = "INSERT INTO " + DATABASE_TABLE1 + " (\"description\",\"status\") VALUES('At 30,000: Check fuel filter.','false');";
                db.execSQL(SQLString3);
                String SQLString4 = "INSERT INTO " + DATABASE_TABLE1 + " (\"description\",\"status\") VALUES('At 30,000: Check spark plugs.','false');";
                db.execSQL(SQLString4);
              // createEntry(DATABASE_TABLE1,"TEST INSERT",false);

               // Toast.makeText(ourContext, "ADDED DATA TO TABLE: " + ("Thirty"), Toast.LENGTH_LONG).show();

                //Build 60,000 mile maintenance data
            String SQLString5 = "INSERT INTO " + DATABASE_TABLE2 + " (\"description\",\"status\") VALUES('At 60,000: Test Battery charge and check terminals.','false');";
            db.execSQL(SQLString5);

            String SQLString6 = "INSERT INTO " + DATABASE_TABLE2 + " (\"description\",\"status\") VALUES('At 60,000: Replace Brake Fluid.','false');";
            db.execSQL(SQLString6);

            String SQLString7 = "INSERT INTO " + DATABASE_TABLE2 + " (\"description\",\"status\") VALUES('At 60,000: Check Brake Pads for replacement.','false');";
            db.execSQL(SQLString7);

            String SQLString8 = "INSERT INTO " + DATABASE_TABLE2 + " (\"description\",\"status\") VALUES('At 60,000: Resurface or replace brake rotors.','false');";
            db.execSQL(SQLString8);

            String SQLString9 = "INSERT INTO " + DATABASE_TABLE2 + " (\"description\",\"status\") VALUES('At 60,000: Replace Coolant.','false');";
            db.execSQL(SQLString9);

            String SQLString10 = "INSERT INTO " + DATABASE_TABLE2 + " (\"description\",\"status\") VALUES('At 60,000: Check Transmission fluid','false');";
            db.execSQL(SQLString10);

            //Build 75,000 mile maintenance data
            String SQLString11 = "INSERT INTO " + DATABASE_TABLE3 + " (\"description\",\"status\") VALUES('At 75,000: Flush and replace power steering fluid','false');";
            db.execSQL(SQLString11);

            //Build 90,000 mile maintenance data
            String SQLString12 = "INSERT INTO " + DATABASE_TABLE4 + " (\"description\",\"status\") VALUES('At 90,000: Check hoses for leaks.','false');";
            db.execSQL(SQLString12);

            String SQLString13 = "INSERT INTO " + DATABASE_TABLE4 + " (\"description\",\"status\") VALUES('At 90,000: Flush power steering fluid if not done at 75,000 miles. ','false');";
            db.execSQL(SQLString13);

            String SQLString14 = "INSERT INTO " + DATABASE_TABLE4 + " (\"description\",\"status\") VALUES('At 90,000: Check Spark plugs (they may start to fail).','false');";
            db.execSQL(SQLString14);

            String SQLString15 = "INSERT INTO " + DATABASE_TABLE4 + " (\"description\",\"status\") VALUES('At 90,000: Consider REPLACING TIMING BELT which can fail at 120,000+ ','false');";
            db.execSQL(SQLString15);

            //Build Old maintenance table
            String SQLString16 = "INSERT INTO " + DATABASE_TABLE5 + " (\"description\",\"status\") VALUES('At 100,000+:Don’t let little things pile up. Have your car inspected at oil changes.','false');";
            db.execSQL(SQLString16);

            String SQLString17 = "INSERT INTO " + DATABASE_TABLE5 + " (\"description\",\"status\") VALUES('At 100,000+: Consider REPLACING TIMING BELT which can fail at 120,000+ ','false');";
            db.execSQL(SQLString17);

            String SQLString18 = "INSERT INTO " + DATABASE_TABLE5 + " (\"description\",\"status\") VALUES('At 100,000+: Check filters.','false');";
            db.execSQL(SQLString18);

            String SQLString19 = "INSERT INTO " + DATABASE_TABLE5 + " (\"description\",\"status\") VALUES('At 100,000+: Check spark plugs.','false');";
            db.execSQL(SQLString19);

            String SQLString20 = "INSERT INTO " + DATABASE_TABLE5 + " (\"description\",\"status\") VALUES('At 100,000+: Check hoses.','false');";
            db.execSQL(SQLString20);

            String SQLString21 = "INSERT INTO " + DATABASE_TABLE5 + " (\"description\",\"status\") VALUES('At 100,000+: Check gaskets.','false');";
            db.execSQL(SQLString21);

            String SQLString22 = "INSERT INTO " + DATABASE_TABLE5 + " (\"description\",\"status\") VALUES('At 100,000+: Check shocks,struts.','false');";
            db.execSQL(SQLString22);


        }// end of onCreate
    }// end dbHelper


    //DB use Methods
    public LogDB open() throws SQLException
    {
        //open the db for reading and writing
        dbh = new LogDB.DBHelper2(ourContext);
        ourDB = dbh.getWritableDatabase();
        return this;
    }// end method open

    //method to close db
    public void close()
    {
        dbh.close();
    }

    public void updateStatus2(String table, int pos, String isChecked){
       // Toast.makeText(ourContext,"UPDATE_STATUS IN DB:"+isChecked,Toast.LENGTH_LONG).show();
        String SQLString = "UPDATE " + table+" SET "+KEY_STATUS+" = "+isChecked+" WHERE "+KEY_ROWID+" = "+pos;
        ourDB.execSQL(SQLString);

    }

    // Method to create a table entry in the db, when user enters log data.
    public void createEntry(String DBTable,String descript, String status)
    {
        //String thisStatus = String.valueOf(status);
        ContentValues cv = new ContentValues();
        cv.put(KEY_DESC,descript);
        cv.put(KEY_STATUS,status);
       // Toast.makeText(ourContext,"ABOUT TO ADD ENTRY in CREATE-ENTRY:",Toast.LENGTH_LONG).show();
        // insert() will return the number of entries inserted
        long result =ourDB.insert(DBTable,null,cv);
      //  Toast.makeText(ourContext,"RESULT OF ADD in CREATE-ENTRY:",Toast.LENGTH_LONG).show();

    }



    // Method to get data from a database table
    public String getData(String DATABASE_TABLE){

        //Toast.makeText(ourContext,"IN GETDATA ",Toast.LENGTH_LONG).show();
        String [] columns = new String [] {KEY_ROWID,KEY_DESC,KEY_STATUS};
        Cursor c = ourDB.query(DATABASE_TABLE,columns,null,null,null,null,null,null);
        StringBuilder result = new StringBuilder();
        result.append("");
        int iRowID = c.getColumnIndex(KEY_ROWID);
        int iDesc = c.getColumnIndex(KEY_DESC);
        int iStatus = c.getColumnIndex(KEY_STATUS);

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result.append (c.getString(iRowID) +": "+c.getString(iDesc)+"  "+
                    c.getString(iStatus)+"\n");
        } // end for

        c.close();

        return result.toString();
    }

    // Method to get data from a db table and convert T/F to done, not done for download of
    // Maintenance Log
    public String convertDataForLog(String DATABASE_TABLE){

        String [] columns = new String [] {KEY_ROWID,KEY_DESC,KEY_STATUS};
        Cursor c = ourDB.query(DATABASE_TABLE,columns,null,null,null,null,null,null);
        StringBuilder result = new StringBuilder();
        result.append("");
        int iRowID = c.getColumnIndex(KEY_ROWID);
        int iDesc = c.getColumnIndex(KEY_DESC);
        int iStatus = c.getColumnIndex(KEY_STATUS);

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            String done = "not done";

            if (c.getString(iStatus).equals("true")){
                done = "done";
            }else{
                done = "not done";
            }


            result.append (c.getString(iRowID) +": "+c.getString(iDesc)+"  "+
                            done+"\n");
        } // end for

        c.close();

        return result.toString();


    }

    //Method to build the car maintenance log with every table, to pass onto downloads
    public String getCarMaintLog(String carMake, String carModel, String mileage){

        StringBuilder log= new StringBuilder("");


        log.append("Maintenance Log for " +carMake + " " + carModel+"\nStarting Mileage:"+mileage+ ".\n\n");

        log.append(convertDataForLog("Thirty"));
        log.append("\n");
        log.append(convertDataForLog("Sixty"));
        log.append("\n");
        log.append(convertDataForLog("SeventyFive"));
        log.append("\n");
        log.append(convertDataForLog("Ninety"));
        log.append("\n");
        log.append(convertDataForLog("Old"));


        return log.toString();
    }

    // Method to build arraylist of a db table data to pass to ListView adapter
    public ArrayList<MaintLogList> getListData(String DATABASE_TABLE){

        String [] columns = new String [] {KEY_ROWID,KEY_DESC,KEY_STATUS};
        Cursor c = ourDB.query(DATABASE_TABLE,columns,null,null,null,null,null,null);
        ArrayList<MaintLogList> items = new ArrayList<MaintLogList>();

        int iRowID = c.getColumnIndex(KEY_ROWID);
        int iDesc = c.getColumnIndex(KEY_DESC);
        int iStatus = c.getColumnIndex(KEY_STATUS);

        for (c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            MaintLogList mlg = new MaintLogList();
            mlg.setDescription(c.getString(iDesc));
            mlg.setDone(Boolean.parseBoolean(c.getString(iStatus)));

            items.add(mlg);
        } // end for

        c.close();

        return items;
    }


    public boolean getStatus(int index,String DBTable){
        boolean status= false;
        String result = "";
        String [] columns = new String []{KEY_ROWID,KEY_DESC,KEY_STATUS};
        Cursor c = ourDB.query(DBTable,columns,null,null,null,null,null,null);

        int iStatus = c.getColumnIndex(KEY_STATUS);
        int iRowID = c.getColumnIndex(KEY_ROWID);
        int iDesc = c.getColumnIndex(KEY_DESC);

        //where clause to get the single status of a main log item

        c.moveToPosition(index);
        c.getString(iRowID);
        c.getString(iDesc);
        result = result+ c.getString(iStatus);
        status=Boolean.parseBoolean((result));
        Toast.makeText(ourContext,"status in getStatus"+status,Toast.LENGTH_LONG).show();
        //} // end for

        c.close();


        return status;
    }
    public long updateStatus(String rowId,boolean status,String DBTable){

        ContentValues cv = new ContentValues();
        String thisStatus = String.valueOf(status);

        cv.put(KEY_STATUS,thisStatus);
        // insert() will return the number of entries inserted
        return ourDB.update(DBTable,cv,KEY_ROWID+" = ?",new String[]{rowId});


    }

}// end class
