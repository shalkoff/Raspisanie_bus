package ru.bus.raspisanie.shalk_off.raspisanie_bus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by shalk_off on 09.10.2016.
 */

public class Caclulate {
    private int im=0;
   private  String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public  String status(String tt)
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date myDate = new Date();
        Date today = new Date();
        try
        {
            myDate = dateFormat.parse(tt); //время рейса
            String s = getDateTime();
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("HH:mm");
            today= format.parse(s);
            if (today.compareTo(myDate)<0) {
                System.out.println("Today Date is Lesser than my Date");
                return time1337(tt);
            }
            else if (today.compareTo(myDate)>0) {


                String s1337 = "";
                System.out.println("Today Date is Greater than my date");
                s1337 = time1488(tt);
                return s1337;
            }
            else {
                System.out.println("Both Dates are equal");
                return "Только что выехал!";
            }


        } catch (ParseException e)
        {
            System.out.print("какая-то досадная ошибка!");
            return "error";
        }
    }
    public  String time1337(String dateStr)
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        try
        {
            //преобразуем String в Date
            d = dateFormat.parse(dateStr);
            //получаем миллисекунды из даты
            long myMills=d.getTime();
            //получаем текущие миллисекунды
            long currentMils=System.currentTimeMillis();
            //теперь можно сравнивать
            long doIt=myMills-currentMils;

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String lol = format.format(new Date(doIt));
            try {
                String pattern131 = "HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern131);
                Date date1 = sdf.parse(lol);
                Date date2 = sdf.parse("6:00");

                // Outputs -1 as date1 is before date2
                if (date1.compareTo(date2)==-1)
                {
                    im= R.drawable.ic_ne_viehal;
                    return "Отправление через: "+lol;
                } else
                    // Outputs 1 as date1 is after date1
                    if (date2.compareTo(date1) ==1 )
                    {
                        im= R.drawable.ic_ne_viehal;
                        return "Еще не выехал";
                    } else
                    {
                        im= R.drawable.ic_ne_viehal;
                        return "Еще не выехал";
                    }


            } catch (ParseException e){
                // Exception handling goes here
            }
            im= R.drawable.ic_ne_viehal;
            return "Отправление через: "+lol;

        } catch (ParseException e)
        {
            System.out.print("какая-то досадная ошибка!");
            return "error";
        }
    }
    public  String time1488(String dateStr)
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        try
        {
            //преобразуем String в Date
            d = dateFormat.parse(dateStr);
            //получаем миллисекунды из даты
            long myMills=d.getTime();
            //получаем текущие миллисекунды
            long currentMils=System.currentTimeMillis();
            //теперь можно сравнивать
            long doIt=currentMils-myMills;

            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            String lol = format.format(new Date(doIt));
            try {
                String pattern131 = "HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern131);
                Date date1 = sdf.parse(lol);
                Date date2 = sdf.parse("2:00");

                // Outputs -1 as date1 is before date2
                if (date1.compareTo(date2)==-1)
                {
                    im= R.drawable.ic_vputi;
                    return "В пути уже: "+lol;
                } else
                    // Outputs 1 as date1 is after date1
                    if (date2.compareTo(date1) ==1 )
                    {
                        im= R.drawable.ic_end;
                        return "Рейс окончен";
                    } else
                    {
                        im= R.drawable.ic_end;
                        return "Рейс окончен";
                    }


            } catch (ParseException e){
                // Exception handling goes here
            }
            return "В пути уже: "+lol;



        } catch (ParseException e)
        {
            System.out.print("какая-то досадная ошибка!");
            return "error";
        }

    }
    public int image(){
        return this.im;
    }
    public String[] parseAvto(String rasp) {
        ArrayList<String> tmp = new ArrayList<>();
        for (String s : rasp.split(";")) {
            tmp.add(s);
        }
        return tmp.toArray(new String[tmp.size()]);
    }
}
