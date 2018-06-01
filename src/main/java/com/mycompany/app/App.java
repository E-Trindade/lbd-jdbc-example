package com.mycompany.app;

import java.io.FileReader;
import java.util.Properties;

import com.google.gson.Gson;

import com.mycompany.app.database.DatabasePool;
import com.mycompany.app.report.MonthlyReportResponse;
import com.mycompany.app.report.Report;

public class App {
    public static void main( String[] args ) throws Exception {
        DatabasePool pool = new DatabasePool(new FileReader(args[0]));
        Report report = new Report(pool);
        MonthlyReportResponse response = report.getDayAccumulatedSales(3, 2018);
        
        Gson gson = new Gson();
        System.out.println( gson.toJson(response) );

    }
}
