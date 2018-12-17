package com.gemalto.assignment.data;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import timber.log.Timber;

/**
 * Created by jacksondeng on 15/12/18.
 */

public class Dob {
    @SerializedName("date")
    private String dob;
    @SerializedName("age")
    private int age;

    public int getAge() {
        return age;
    }

    public String getDob() {
        return dob;
    }

    public Dob(String dob){
        updateDobStr(dob);
        calculateAge(dob);
    }

    private void updateDobStr(String dob){
        if(validateDate(dob,"yyyy-MM-dd'T'HH:mm:ssZ")) {
            this.dob = changeDateFormat(dob,"yyyy-MM-dd'T'HH:mm:ssZ","dd MMM yyyy");
        } else{
            this.dob = dob;
        }
    }

    private void calculateAge(String dob){
        if(validateDate(dob,"dd MMM yyyy")){
            dob = changeDateFormat(dob,"dd MMM yyyy","yyyy-MM-dd'T'HH:mm:ssZ");
            DateTime currentTime = new DateTime(System.currentTimeMillis());
            DateTime dateTime = new DateTime(dob);
            this.age = currentTime.getYear() - dateTime.getYear();
        }
        else if(validateDate(dob,"yyyy-MM-dd'T'HH:mm:ssZ")){
            DateTime currentTime = new DateTime(System.currentTimeMillis());
            DateTime dateTime = new DateTime(dob);
            this.age = currentTime.getYear() - dateTime.getYear();
        }
        if (age < 0) {
            age = 0;
        }
    }

    private String changeDateFormat(String dob,String patterIn , String patterOut){
        DateTimeFormatter dtf = DateTimeFormat.forPattern(patterIn);
        DateTime jodatime = dtf.parseDateTime(dob);
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern(patterOut);
        Timber.d("JODAA " + dtfOut.print(jodatime));
        return dtfOut.print(jodatime);
    }

    private boolean validateDate(String dob,String pattern){;
        try {
            DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
            fmt.parseDateTime(dob);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
