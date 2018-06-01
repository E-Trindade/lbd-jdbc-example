package com.mycompany.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Report {
    DatabasePool pool;

    public Report(DatabasePool pool) {
        this.pool = pool;
    }

    public ResultSet getDayAccumulatedSales(int month, int year) throws SQLException{
        Connection conn = this.pool.getConnection();
        PreparedStatement ps = conn.prepareStatement(Queries.DAY_ACCUMULATED_SALES_IN_MONTH);
        ps.setInt(1, month);
        ps.setInt(2, year);
        
        return ps.executeQuery();
    }

    public ResultSet getRankingDayAccumulatedSales(int month){
        return null;
    }
}