package com.example.terlizziprojectitdev181;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass. This fragment is the main body of the Main Activity/
 * Landing page.  Combines with Ribbon and Bottom Nav.
 *
 * Use the {@link DetailFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static final String MY_PREFS_FILENAME = "com.example.terlizziprojectitdev181.OBDCodes2";
    ImageView btnTemp, btnWarning, btnWrench, btnTire, btnOil, btnBattery;
    TextView tvMeaning;
    Button btnSubmit,btnShowCode;
    EditText tvMiles, tvCode;
    ShowMessage activity;
    DetailFrag detailFrag = DetailFrag.this;

    String code;

    public DetailFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFrag newInstance(String param1, String param2) {
        DetailFrag fragment = new DetailFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ShowMessage) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        btnTemp=(ImageView)v.findViewById(R.id.imgEngTemp);
        btnWarning=(ImageView)v.findViewById(R.id.imgEngWarn);
        btnWrench=(ImageView)v.findViewById(R.id.imgWrench);
        btnTire=(ImageView)v.findViewById(R.id.imgTirePress);
        btnOil=(ImageView)v.findViewById(R.id.imgOil);
        btnBattery=(ImageView)v.findViewById(R.id.imgBattery);
        btnSubmit=(Button)v.findViewById(R.id.btnSubmit);
        btnShowCode=(Button)v.findViewById(R.id.btnShowCode);
        tvMeaning=(TextView)v.findViewById(R.id.tvMeaning);
        tvMiles =(EditText)v.findViewById(R.id.tvMiles);
        tvCode=(EditText)v.findViewById(R.id.tvCode);




        tvMeaning.setMovementMethod(new ScrollingMovementMethod());

       btnSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               EngineData ed = new EngineData();

               //Get valid input data for Mileage or Engine Code

               String finalCode,upperSCode;
               //boolean codeDone=false;
               if(tvMiles.getText().toString().isEmpty()){
                   if(tvCode.getText().toString().isEmpty()) {
                       Toast.makeText(getActivity(), "Please enter a value.",
                               Toast.LENGTH_LONG).show();
                   }
                   else{

                       code = tvCode.getText().toString().trim();
                       upperSCode=code.toUpperCase();

                        //Valid Engine codes generally have a P, U, B or C plus a number

                       if(!((upperSCode.charAt(0)=='P') || (upperSCode.charAt(0)=='U') || (upperSCode.charAt(0)=='B') || (upperSCode.charAt(0)=='C'))){
                               //Toast.makeText(getActivity(), "Integer code:"+code,
                               //  Toast.LENGTH_LONG).show();
                                   Toast.makeText(getActivity(), "Please enter code format Pxxxx or U/B/Cxxxx." ,
                                           Toast.LENGTH_LONG).show();
                                   tvCode.setText("");

                       }
                       else   //process a valid code
                       {
                               //Send Code back to EngineData class
                               ed.setCode(upperSCode);
                               tvCode.setText("");
                      // }  //end process valid code


                       //ed.setCode(code);

                       // Save code to Shared Preferences
                            SharedPreferences prefs = (SharedPreferences) getContext().getSharedPreferences(
                               MY_PREFS_FILENAME,Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = (SharedPreferences.Editor)
                               getContext().getSharedPreferences(MY_PREFS_FILENAME,Context.MODE_PRIVATE).edit();
                            int number = (prefs.getAll().size())+1;

                            String index = Integer.toString(number);

                            editor.putString(index,upperSCode);

                            editor.commit();

                       //update the textview window showing explanation of code
                           activity.updateMessage(ed.getCodeData(upperSCode));
                       //Reset OBD Code entry textedit box
                           tvCode.setText("");
                        }  // end process valid code

                   }// end else process an entered code
               }//end if miles is empty
               else{  //process an entered mileage -get a valid value, display the corresponding msg,
                      // stored in the Engine Data class, associated with the mileage mark.

                   String s1 = tvMiles.getText().toString().trim();
                   String sMileage=s1.replace(",","");

                   try{
                       int mileage = Integer.parseInt(sMileage);

                       MaintenanceData md = new MaintenanceData(detailFrag.getContext(),false);
                       md.setMileage(mileage);
                       activity.updateMessage(md.getMileageData(mileage));
                       tvMiles.setText("");

                   }catch(NumberFormatException e) {
                       Toast.makeText(getActivity(), "Please enter only numeric values.",
                               Toast.LENGTH_LONG).show();
                       tvMiles.setText("");
                   }
                   catch(Exception e){
                       Toast.makeText(getActivity(), "ERROR:"+e.getMessage(),
                               Toast.LENGTH_LONG).show();
                   }// end Excetpion
               }//end else process mileage
           } //end OnClick
       });  //end Listener

       btnShowCode.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences prefs = (SharedPreferences) getContext().getSharedPreferences(
                       MY_PREFS_FILENAME,Context.MODE_PRIVATE);

               EngineData ed = new EngineData();

               // Get all the previously entered Engine codes and display as text
               Map<String, ?> codeList = prefs.getAll();
               int number = (prefs.getAll().size());

               String sNumber = Integer.toString(number);

               StringBuilder previousCodes= new StringBuilder("");
               String value;

                previousCodes.append("Scroll to see complete list: \n");

               for(int i=1;i<=number;i++){
                   String index = Integer.toString(i);
                   value= String.valueOf(codeList.get(index));


                   previousCodes.append((value) + "\n");



               }  //end for
               previousCodes.append("\nEND\n\n");
               String msg = String.valueOf(previousCodes);

               activity.updateMessage(msg);


           }
       });  //end Listener


        // Return view for inflated layout
        return v;

    }  //end onCreate

    //Interface used to show meaning of engine icons
    public interface ShowMessage{

        void updateMessage(String msg);
    }




} //end class