package com.accenture.accenture.controller;



import com.accenture.accenture.model.Hour;
import com.accenture.accenture.service.EmployeeService;
import com.accenture.accenture.service.HourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private HourService hourService;

    @Value("${year.begginig}")
    private int yearBegining;

    @Value("${year.ending}")
    private int yearEnding;

    @Value("${max.hour.per.week}")
    private int hourPerWeek;

    @Value("${months.folder}")
    private String monthsFolder;

    @Value("${years.folder}")
    private String yearsFolder;

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/")
    private String displayHome(Model model){
        addCommonVariables(model);
        return "home";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    private String getHours(@RequestParam(value = "month") final int month, @RequestParam(value = "year") final int year, Model model){

        List<Hour> hours= hourService.getHoursForDayAndMonth(month, year, employeeService.getCurrentUserId());
        addCommonVariables(model, month, year);
        if(hours.isEmpty()) {
            return "registerHours";
        }
        else {
            model.addAttribute("hours", hours);
            return "submittedHours";
        }
    }

    public void addCommonVariables(Model model, int month, int year){
        model.addAttribute("monthSelected", month);
        model.addAttribute("yearSelected", year);
        Map<Integer, String> months=hourService.getMonths();
        model.addAttribute("months", months);
        model.addAttribute("yearBegining", this.yearBegining);
        model.addAttribute("yearEnding", this.yearEnding);
    }

    public void addCommonVariables(Model model){
        Map<Integer, String> months=hourService.getMonths();
        model.addAttribute("months", months);
        model.addAttribute("yearBegining", this.yearBegining);
        model.addAttribute("yearEnding", this.yearEnding);
    }

    @PostMapping("/save")
    private String saveHours(@ModelAttribute Hour hour,  Model model){
        hour.getHourID().setReferencepk(employeeService.getCurrentUserId());
        addCommonVariables(model, hour.getHourID().getMonth(), hour.getHourID().getYear());
        if(validateHourPerWeek(hour)) {
            hourService.saveHours(hour);
            logger.info("Hour save correctly");
            fileMonthsWrite(hour);
            fileYearsWrite(hour);
            return "redirect:/";
        }else {
            model.addAttribute("error", "One week has bigger hour amount than amount allowed");
            return "registerHours";
        }
    }

    private boolean validateHourPerWeek(Hour hour){
        if(hour.getWeek1()>this.hourPerWeek|| hour.getWeek2()> this.hourPerWeek ||hour.getWeek3()>this.hourPerWeek|| hour.getWeek4()> this.hourPerWeek)
            return false;
        else
            return true;
    }

    public void fileMonthsWrite(Hour hour){
        Map<Integer, String> months=hourService.getMonths();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String content = "Employee Login: "+ employeeService.getCurrentUserLogin()
                + " Month: " + months.get(hour.getHourID().getMonth())
                + " Year: " + hour.getHourID().getYear()
                + " Total amount of Hours Registered: " + hour.getTotalhours()
                + " Date registered: " + dateFormat.format(date)
                + " Date submitted: " + hour.getSubmittingdate().toString();
        fileWrite(content, this.monthsFolder,months.get(hour.getHourID().getMonth()));
    }

    public void fileYearsWrite(Hour hour){
        Map<Integer, String> months=hourService.getMonths();
        String content = "Month: " + months.get(hour.getHourID().getMonth())
                + " Year: " + hour.getHourID().getYear()
                + " Total amount of Hours Registered: " + hour.getTotalhours();
        fileWrite(content, this.yearsFolder,Integer.toString(hour.getHourID().getYear()));
    }

    public void fileWrite(String content, String fileFolder, String fileName){

        try {
            File idea = new File(fileFolder, fileName + ".txt");
            FileWriter fw;
            if (!idea.exists()) {
                fw = new FileWriter(idea.getAbsoluteFile());
            }else
            {
                fw = new FileWriter(idea.getAbsoluteFile(), true);
            }
            PrintWriter printWriter = new PrintWriter(fw);
            printWriter.println(content);
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error found: {}", e);
        }

    }
}
