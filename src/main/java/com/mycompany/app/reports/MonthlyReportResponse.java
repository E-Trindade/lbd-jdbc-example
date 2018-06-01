package com.mycompany.app.report;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class MonthlyReportResponse {

    public static class ReportDay {
        public int day;
        public int month;
        public int year;
        public double revenue;

        public int dayOfWeek;
        public int weekOfMonth;

        public double fractionInMonth;
        public String classification;
    }

    public int month;
    public int year;

    public double salesSum;
    public String status;
    public List<ReportDay> daySales;

    MonthlyReportResponse(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public void build(ResultSet rs) throws SQLException {
        Calendar myCal = new GregorianCalendar(this.year, this.month, 1);
        int daysInMonth = myCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        this.daySales = new LinkedList<>();

        boolean finished = !rs.next();
        for (int i = 0; i < daysInMonth; i++) {
            ReportDay day = new ReportDay();
            day.day = i;
            day.month = this.month;
            day.year = this.year;
            myCal = new GregorianCalendar(this.year, this.month, i);
            day.dayOfWeek = myCal.get(Calendar.DAY_OF_WEEK);
            day.weekOfMonth = myCal.get(Calendar.WEEK_OF_MONTH);

            day.revenue = 0;
            if (!finished && rs.getInt("dia") == i) {
                day.revenue = rs.getDouble("receita");
                
                this.salesSum += day.revenue;
                finished = !rs.next();
            }
            this.daySales.add(day);
        }

        if(this.salesSum == 0)
            return;

        for(ReportDay day : this.daySales){
            day.fractionInMonth = (double) day.revenue / this.salesSum;
        }
    }
}