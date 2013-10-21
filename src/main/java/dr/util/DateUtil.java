/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dr.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Util class, turn easy all operations with all kind of date
 *
 * @author andregnhoato
 */
public abstract class DateUtil {

    static Calendar calendar = Calendar.getInstance();
    private static Date utilDate;

    /*
     * method that returns the exact time of day at default format
     */
    public static Date getTime() {
        return calendar.getTime();
    }

    public static String formatDate(Date data) {
        if (data != null) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(data);
        } else {
            return null;
        }
    }
    
    public static String formatHora(Date data) {
        if (data != null) {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            return df.format(data);
        } else {
            return null;
        }
    }

    public static Date formatDate(String data) {
        if (data != null && data != "") {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            try {
                return df.parse(data);
            } catch (ParseException ex) {
                Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }
    
    public static String formatHora(String hora) {
        if (hora != null && hora != "") {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            try {
                return df.parse(hora)+"";
            } catch (ParseException ex) {
                Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;

    }
}
