package com.example.terlizziprojectitdev181;

//import android.util.SparseArray;
//import java.util.HashMap;
//import java.util.Map;


public class EngineData {

    //private SparseArray<Object> obd = new SparseArray<>(30);
    //can't get this to work!  "put" is in red
    //obd.put(420,"Catalyst System Efficiency Below Threshold (Bank 1)");
    //private HashMap<Integer,String> obd = new HashMap<>(30);
    //obd.put


    //Engine dashboard warnings and Engine Code meanings


    private String engineTempData = "Possibilities include:\n"+
    "Low, leaking or old coolant.\n A defective thermostat that is stuck closed.\n"+
    "A bad water pump.\n"+"Radiator issues.\n"+ "Blocked coolant passages inside the engine.\n"
            + "Cooling fan has failed.\n"+ "Low oil level.\n"+ "Blown head gasket.\n"+
            "Bad temperature sensor.\n"+"\n";

    private String engineWarningData = "It can blink on to warn you of a number of issues, " +
            "from minor (a loose gas cap) to major (wiring problems).";

    private String wrenchData = "Car Service necessary. Usually, it's because of some sort "+
            "of time-based milestone not being met, or there's a fault with the car "+
            "that needs professional attention.\n"+"\n";

   private String tirePressData = "The pressure in one or more of your tires is too low"
    +" and needs to be attended to.";

   private String oilData = "Your engine is running low on oil, or there is a problem"
    +" with your car’s oil pressure system.";

   private String batteryData ="The car’s charging system is short of power. \n"+
           "You are running solely off the battery.";


  //private int mileage;
  private String code;
  private int intCode;

  public String getCodeData(String codeData) {

        String meaning,code;
        if(codeData.charAt(0)=='P'){
            code=codeData;
            String newCode=codeData.replace("P","").trim();
            intCode = Integer.parseInt(newCode);
        }
        else if(codeData.charAt(0)=='U'){
            code = "U";
        }
        else if (codeData.charAt(0)=='B'){
            code = "B";
        }
        else if (codeData.charAt(0)=='C'){
            code = "C";
        }
        else{
            code="UNKNOWN";
        }

        switch(code){
            case "P0420":
                meaning="Catalyst System Efficiency Below Threshold (Bank 1). \n"+
                "Indicates that your car's engine computer has detected underperformance"+
                        " from the Bank 1 catalytic converter.";
                break;
            case "P0340":
                meaning="Camshaft Position Sensor A Circuit Malfunction.";
                break;
            case "P0446":
                meaning = "Evaporative Emission Control System Vent Control Circuit Malfunction.";
                break;
            case "P0430":
                meaning = "Catalyst System Efficiency Below Threshold (Bank 2). \n"+
                        "Indicates that your car's engine computer has detected underperformance"+
                    " from the Bank 2 catalytic converter.";
                break;
           case "P0300":
                meaning = "Random or Multiple Cylinder Misfire Detected.";
                break;
            case "P0100":
                meaning="Lost Communication with engine computer ECM/PCM  'A' and another module.";
                break;
            case "P0011":
                meaning = "\"A\" Camshaft Timing Over Advanced (Bank 1).";
                break;
            case "P2096":
                meaning = "Post Catalyst Fuel Trim System Too Lean (Bank 1).";
                break;
            case "P0172":
                meaning = "Fuel System Too Rich (Bank 1) - has too much fuel, not enough air.";
                break;
            case "P0073":
                meaning = "Control Module Communication Bus \"A\" Off - car's data network not working.";
                break;
            case "P0017":
                meaning="Camshaft Position B – Camshaft Position Correlation (Bank 1).\n"+
                "Indicates that the engine computer detects a misalignment"+
                        "between the crankshaft and camshaft positions.";
                break;
            case "P2138":
                meaning="Throttle/Pedal Position Sensor/Switch D/E Voltage Correlation problem.";
                break;
            case "P0141":
                meaning="Oxygen Sensor Heater Circuit Malfunction (Bank 1, Sensor 2).";
                break;
            case "P0302":
                meaning="Cylinder 2 Misfire Detected.";
                break;
            case "P0113":
                meaning = "Intake Air Temperature Sensor 1 Circuit High Input\n"+
                        " indicates the engine computer has determined that there's a problem"+
                        "with the IAT sensor or its circuit.";
                break;
            case "P0128":
                meaning="Coolant thermostat below thermostat.";
                 break;
            case "P0171":
                meaning="System too lean.";
                break;
            case "P0174":
                meaning="System too lean.";
                break;
            case "P0401":
                meaning="Exhaust gas recirculation 'A' flow insufficient.";
                break;

            case "P0442":
                meaning="Small evaporative emission system leak detected.";
                break;

            case "U":  //OBD Codes with a "U"
                meaning="Problem with the onboard computers and integration"+
                        "functions that the OBD manages.";
                break;

            case "B":  //OBD Codes with a "B"
                meaning="Problem with the car body." ;
                break;

            case "C":  //OBD Codes with a "C"
                meaning="Problem with the car chasis.";
                break;

            case "UNKNOWN" :
                meaning="UNKNOWN OBD CODE";
                break;

            default:


                if ((intCode>=300) && (intCode<=312)){
                    meaning = "Related to engine misfires." ;
                }
                else if ((intCode>=335) && (intCode <=339)){
                    meaning = "Related to the crankshaft position sensor.";
                }
                else if ((intCode>=400) && (intCode<=408)) {
                    meaning = "Related to exhaust gas recirculation (EGR) flow.";
                }
                 else if ((intCode>=420) && (intCode<=434)){
                     meaning="Related to the catalytic converter.";
                 }
                 else if ((intCode>=460) && (intCode <= 464)){
                     meaning = "Related to the fuel level sensor.";
                }
                 else if((intCode>=500) && (intCode<=503)){
                     meaning="Related to the vehicle speed sensor";
                }
                 else{
                     meaning = "Unknown Code";
                } // end last else/else if

        }//end switch

        return meaning;
    }// end of getCodeData

    public void setCode(String code) {
        this.code = code;
    }


    /*
    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }*/

    public String getTirePressData() {
        return tirePressData;
    }

    public String getBatteryData() {
        return batteryData;
    }

    public String getOilData() {
        return oilData;
    }

    public String getEngineWarningData() {
        return engineWarningData;
    }


    public String getWrenchData() {
        return wrenchData;
    }

    public String getEngineTempData() {
        return engineTempData;
    }

}
