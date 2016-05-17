package Controller.Util;

import java.util.Date;

/**
 * Created by mond on 17.05.2016.
 */
public class DateChecker {
    public static boolean checkDate(String input){
        String[] datetimesplit = input.split(" ");
        String date = datetimesplit[1];
        String time = datetimesplit[0];
        Boolean inputOK = true;
        for(String s:time.split(":")){
            if(Integer.parseInt(s) >= 60){
                inputOK = false;
            }
        }
        return inputOK;
    }

    public static void main(String[] args) {
        System.out.print(DateChecker.checkDate("99:99:99 99.99.9999"));
    }
}
