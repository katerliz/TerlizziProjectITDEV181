package com.example.terlizziprojectitdev181;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class Phone extends AppCompatActivity {
    Button btnCallContacts,btnAAAPhone,btnECPhone,btnGoogleMaps;
    TextView tvSpace;
    public static final String MY_PREFS_FILENAME = "com.example.terlizziprojectitdev181.Settings";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        btnCallContacts=findViewById(R.id.btnCallContacts);
        btnAAAPhone = findViewById(R.id.btnCallAAA);
        btnECPhone = findViewById((R.id.btnCallEC));
        btnGoogleMaps = findViewById(R.id.GoogleMaps);
        tvSpace = (TextView)findViewById(R.id.tvSpace);


        SharedPreferences prefs = getSharedPreferences(
                MY_PREFS_FILENAME, Context.MODE_PRIVATE);
        String AAAPhone = prefs.getString("AAA Phone","");
        String ECPhone = prefs.getString("Emergency Contact Phone","");

        if(AAAPhone.equals("")&&(ECPhone.equals(""))){

            tvSpace.setHeight(600);
        }
        else if (AAAPhone.equals("")&&(!ECPhone.equals(""))){

            tvSpace.setHeight(200);

        }else{

            tvSpace.setHeight(0);
        }

        if(AAAPhone.equals(""))
        {
            btnAAAPhone.setVisibility(View.GONE);
        }else
        {
            btnAAAPhone.setVisibility(View.VISIBLE);
        }


        if(ECPhone.equals("")){
            btnECPhone.setVisibility(View.GONE);
        }
        else{
            btnECPhone.setVisibility(View.VISIBLE);

        }

        //When visible, this button auto-dials the user entered Emergency Contact Phone number
        //using android dial
        btnECPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:"+ECPhone);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(callIntent);
            }
        });

        //This button will bring up the user's android contacts page
        btnCallContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactsIntent= new Intent(Intent.CATEGORY_APP_CONTACTS);
                contactsIntent.setAction("android.intent.action.MAIN");
                contactsIntent.addCategory("android.intent.category.LAUNCHER");
                contactsIntent.addCategory("android.intent.category.DEFAULT");

                startActivity(contactsIntent);
            }
        });

        //When visible, this button will auto-dial an AAA phone number if entered by user,
        // Using android dial
        btnAAAPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri number = Uri.parse("tel:"+AAAPhone);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                callIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(callIntent);
            }
        });

        //When selected, this button will transition to the android Google Maps page
        btnGoogleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapsIntent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=an+address+city"));
                startActivity(mapsIntent);
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

                else if (menuItemId ==  R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), Settings2.class));
                    return true;
                }

                else if (menuItemId ==  R.id.phone) {
                    return true;
                }
                else  {
                    startActivity(new Intent(getApplicationContext(), Log2.class));
                    return true;
                }




                return true;


            }
        });

    }//end of onCreate
}