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
    public static final String TEMP_NEG_SENML = "http://users.polytech.unice.fr/~logre/resources/air_temp_neg.senml";
    public static final String TEMP_SENML = "http://users.polytech.unice.fr/~logre/resources/temp2.senml";
    public static final String ALTITUDE_SENML = "http://users.polytech.unice.fr/~logre/resources/altitude.senml";
    public static final String SPEED_SENML = "http://users.polytech.unice.fr/~logre/resources/ground_speed.senml";
    public static final String CATEGORIZED_STACKED = "http://users.polytech.unice.fr/~logre/resources/categorizedData.senml";
    //TODO handle the particular deserialization for GPS senml data
    public static final String LATITUDE_SENML = "http://users.polytech.unice.fr/~logre/resources/latitude.senml";
    public static final String LONGITUDE_SENML = "http://users.polytech.unice.fr/~logre/resources/longitude.senml";

    //SmartCampus
    //public static final String Virtual_TEMP_SMARTCAMPUS = "http://54.76.227.250:80/data-api/sensors/TEMP_442V/data?date=2014-06-28%2007:00:00/2014-06-28%2010:00:00";
    public static final String Raw_TEMP_SMARTCAMPUS = "http://smartcampus.unice.fr/data-api/sensors/TEMP_442/data?date=2014-08-01%2007:00:00/2014-08-01%2010:00:00";
    public static final String Raw_LIGHT_SMARTCAMPUS = "http://smartcampus.unice.fr/data-api/sensors/LIGHT_442/data?date=2014-08-01%2007:00:00/2014-08-01%2010:00:00";

    // Map data
    public static final String SVG_DESCRIPTION = Consts.class.getClassLoader().getResource("plan_T1_4e.svg").getPath();
    public static final String LAST_STATE_DOORS = Consts.class.getClassLoader().getResource("last_states_doors.json").getPath();
    public static final String LAST_STATE_LIGHTS = Consts.class.getClassLoader().getResource("last_states_lights.json").getPath();
    public static final String LAST_STATE_MOTIONS = Consts.class.getClassLoader().getResource("last_states_motions.json").getPath();
    public static final String LAST_STATE_TEMPS = Consts.class.getClassLoader().getResource("last_states_temps.json").getPath();
    public static final String LAST_STATE_WINDOWS = Consts.class.getClassLoader().getResource("last_states_windows.json").getPath();
        //Icon
        public static final String ICON_DOOR = Consts.class.getClassLoader().getResource("door.png").getPath();
        public static final String ICON_WINDOW = Consts.class.getClassLoader().getResource("window.png").getPath();
        public static final String ICON_TEMP = Consts.class.getClassLoader().getResource("temp.png").getPath();
        public static final String ICON_BROKEN = Consts.class.getClassLoader().getResource("bad.png").getPath();
        public static final String ICON_LIGHT = Consts.class.getClassLoader().getResource("light.png").getPath();
        public static final String ICON_MOTION = Consts.class.getClassLoader().getResource("motion.png").getPath();



   public static String getRandomSenMLData(){
       Random random = new Random();
       switch(random.nextInt(Integer.MAX_VALUE)%4) {
           case 0:
               return TEMP_NEG_SENML;
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
