/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.util;

import java.util.Calendar;
import java.util.Date;

/**
 *  Util class, turn easy all operations with all kind of date
 *  
 * @author andregnhoato
 */
public abstract class DateUtil{
    
    static Calendar calendar = Calendar.getInstance();
    private static Date utilDate;
    
    /*
     * method that returns the exact time of day at default format
     */
    public static Date getTime(){
        return calendar.getTime();
    }
}
