package com.example.terlizziprojectitdev181;

import static com.google.android.material.navigation.NavigationBarView.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements DetailFrag.ShowMessage
{

    EngineData ed = new EngineData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        BottomNavigationView bnv=findViewById(R.id.bottom_navigation);
        //bnv.setSelectedItemId(R.id.home);

        bnv.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int menuItemId =  item.getItemId();

                if (menuItemId ==  R.id.home) {


                }
                else if (menuItemId ==  R.id.clear){
                    String message = "";
                    updateMessage(message);
                    EditText tvMiles = findViewById(R.id.tvMiles);
                    tvMiles.setText("");
                    EditText tvCode = findViewById(R.id.tvCode);
                    tvCode.setText("");
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
                    startActivity(new Intent(getApplicationContext(), Log2.class));
                    return true;
                }


               return true;


            }
        });

        //ImageView btnTemp=(ImageView)findViewById(R.id.imgEngTemp);

        Fragment detail = new DetailFrag();
        Fragment ribbon = new IconRibbon();

        FragmentManager mgr = getSupportFragmentManager();
        FragmentTransaction ft1 = mgr.beginTransaction();
             ft1.replace(R.id.ribbonMain,ribbon,"ribbon");
             ft1.replace(R.id.detailMain,detail,"detail");

             ft1.commit();
    }

    // interface implementation from Detail Fragment
    @Override
    public void updateMessage(String msg) {
        TextView newText = findViewById(R.id.tvMeaning);
        newText.setText("");
        newText.setText(msg);

    }





    // handler methods to make ImageViews selectable for onClick in component attributes
    public void engineTemp(View view){
        updateMessage(ed.getEngineTempData());
    }
    public void EngineTemp(View view){
        updateMessage(ed.getEngineTempData());
    }
    public void engineWarning(View view){
        updateMessage(ed.getEngineWarningData());
    }

    public void wrench(View view){
        updateMessage(ed.getWrenchData());
    }

    public void tirePress(View view){
        updateMessage(ed.getTirePressData());
    }

    public void oil(View view){
        updateMessage(ed.getOilData());
    }

    public void battery(View view){
        updateMessage(ed.getBatteryData());
    }

    //public void mileage(View view){updateMessage(ed.getMileageData());}

}