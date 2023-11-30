package com.example.terlizziprojectitdev181;

import static java.security.AccessController.getContext;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

import java.security.AccessControlContext;
import java.util.ArrayList;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MaintenanceData
{
    //public static final String MY_PREFS_FILENAME = "com.example.terlizziprojectitdev181.Settings";
    private int mileage;
    private String carMake;
    private String carModel;




    private boolean newCar;


    //private final Context ourContext;

    private final Context ourContext;



    public MaintenanceData(Context context,boolean newCar) throws java.sql.SQLException {

        ourContext = context;
        this.newCar=newCar;


    } // end constructor Maintance Data


    //Getters,Setters
    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }


    //Method to return action string given a certain mileage marker inputted by user in MainAct.
    public String getMileageData(int mileage) {
        String data;
        int mileageRange;
        if(mileage<=30000){
            data="Check for dirty air filter and fuel filter. Check spark plugs.\n"+"\n";
        }
        else if(mileage<=60000){
            data="Test Battery charge and check terminals. Replace Brake Fluid. \nCheck Brake Pads for replacement.\n" +
                    "AT 60,000 Miles: Resurface or replace brake rotors, Replace Coolant. Check and\n"+
                    "Check Transmission fluid.\n"+
                    "\n";

        }
        else if(mileage<=75000){
            data="At 75,000 miles, flush and replace power steering fluid.\n"
                    +"\n";
        }
        else if(mileage<=90000){
            data="Check hoses for leaks. Flush power steering fluid if not done at\n"+
                    "at 75,000 miles.  Spark plugs may start to fail.\n" +
                    "Consider REPLACING TIMING BELT which can fail at 120,000+\n"+
                    "\n";
        }
        else{
            data="Don’t let little things pile up. Instead, have your car inspected at each "+
                    "oil change and replace parts as they wear out. \n"+"\n"+
                    "Be sure to keep an eye on and replace these components as needed:\n" +
                    "Filters    Spark Plugs\n" +
                    "Belts      Hoses\n" +
                    "Gaskets    Shocks/Struts\n"+
                    "\n";
        }



        return data;

    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }



} // end class
