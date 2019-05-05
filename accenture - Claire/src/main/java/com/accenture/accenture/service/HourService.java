package com.accenture.accenture.service;

import com.accenture.accenture.model.Hour;
import com.accenture.accenture.repository.IHourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HourService {

    @Autowired
    private IHourRepository hourRepository;

    private Logger logger = LoggerFactory.getLogger(HourService.class);

    public Map<Integer, String> getMonths(){
        Map<Integer, String> months = new HashMap<>();
        months.put(1, "January");
        months.put(2, "February");
        months.put(3, "March");
        months.put(4, "April");
        months.put(5, "May");
        months.put(6, "June");
        months.put(7, "July");
        months.put(8, "August");
        months.put(9, "September");
        months.put(10, "October");
        months.put(11, "November");
        months.put(12, "December");
        return months;
    }

    public List<Hour> getHoursForDayAndMonth(int month, int year, int employeeId){
        List<Hour> hours = null;
        hours= hourRepository.findByMonthAndYear(month, year, employeeId);
        return hours;
    }

    public void saveHours(Hour hour){
        Timestamp date=null;
        try {
            DateFormat  df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date parsedDate = df.parse(hour.getSubmittingdate().toString());
            date= new Timestamp(parsedDate.getTime());
        }catch (ParseException ex) {
            ex.printStackTrace();
            logger.error("Error found: {}", ex);
        }
        hour.setSubmittingdate(date);
        hourRepository.save(hour);
    }
}
