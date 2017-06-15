/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesmanager.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danieljr
 */
public class DateUtils {

    public static Date getRandomDate(String begin, String end) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yy");
            Date d1;
            d1 = sdf.parse(begin);
            Date d2 = sdf.parse(end);
            long random = ThreadLocalRandom.current().nextLong(d1.getTime(), d2.getTime());
            Date date = new Date(random);
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            return date;
        } catch (ParseException ex) {
            Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }
}
