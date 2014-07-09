package constants;

import java.nio.file.Paths;
import java.util.Random;

/**
 * Created by ivan on 04/07/2014.
 */
public class Consts {

    //Identify the folder where generated visualization will be stored
    public static final String GENERATED_TARGET_FOLDER = "/example.library/products/";
    public static final String RUNTIME_FOLDER = Paths.get("").toAbsolutePath().toString();

    //Data Library
    //SenML
    public static final String PRES_SENML = "http://users.polytech.unice.fr/~logre/resources/pres2.senml";
    public static final String TEMP_SENML = "http://users.polytech.unice.fr/~logre/resources/temp2.senml";
    public static final String ALTITUDE_SENML = "http://users.polytech.unice.fr/~logre/resources/altitude.senml";
    public static final String SPEED_SENML = "http://users.polytech.unice.fr/~logre/resources/ground_speed.senml";
    public static final String CATEGORIZED_SENML = "http://users.polytech.unice.fr/~logre/resources/categorizedData.senml";
    //TODO handle the particular deserialization for GPS senml data
    public static final String LATITUDE_SENML = "http://users.polytech.unice.fr/~logre/resources/latitude.senml";
    public static final String LONGITUDE_SENML = "http://users.polytech.unice.fr/~logre/resources/longitude.senml";

    //SmartCampus
    //public static final String Virtual_TEMP_SMARTCAMPUS = "http://54.76.227.250:80/data-api/sensors/TEMP_442V/data?date=2014-06-28%2007:00:00/2014-06-28%2010:00:00";
    public static final String Raw_TEMP_SMARTCAMPUS = "http://54.76.227.250:80/data-api/sensors/TEMP_442/data?date=2014-06-28%2007:00:00/2014-06-28%2010:00:00";
    public static final String Raw_LIGHT_SMARTCAMPUS = "http://54.76.227.250:80/data-api/sensors/LIGHT_442/data?date=2014-06-28%2007:00:00/2014-06-28%2010:00:00";


   public static String getRandomSenMLData(){
       Random random = new Random();
       switch(random.nextInt(Integer.MAX_VALUE)%4) {
           case 0:
               return PRES_SENML;
           case 1:
               return ALTITUDE_SENML;
           case 2:
               return SPEED_SENML;
           default:
               return TEMP_SENML;
       }
   }


    public static String getRandomSmartCampusData(){
        Random random = new Random();
        switch(random.nextInt(Integer.MAX_VALUE)%2) {
            /*case 0:
                return Virtual_TEMP_SMARTCAMPUS;*/
            case 0:
                return Raw_TEMP_SMARTCAMPUS;
            default:
                return Raw_LIGHT_SMARTCAMPUS;
        }
    }
}
