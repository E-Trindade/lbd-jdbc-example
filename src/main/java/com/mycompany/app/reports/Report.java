package com.mycompany.app.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mycompany.app.database.Queries;
import com.mycompany.app.database.DatabasePool;

public class Report {
    DatabasePool pool;

    public Report(DatabasePool pool) {
        this.pool = pool;
    }

    public MonthlyReportResponse getDayAccumulatedSales(int month, int year) throws SQLException {
        Connection conn = this.pool.getConnection();
        PreparedStatement ps = conn.prepareStatement(Queries.DAY_ACCUMULATED_SALES_IN_MONTH);
        ps.setInt(1, month);
        ps.setInt(2, year);

        ResultSet rs = ps.executeQuery();
        MonthlyReportResponse response = new MonthlyReportResponse(month, year);
        response.build(rs);
        return response;
    }

    // public ReportResponse getRankingDayAccumulatedSales(int month) {
    //     return null;
    // }
}