package com.mycompany.app;

import java.io.FileReader;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        DatabasePool pool = new DatabasePool(new FileReader(args[0]));
        Report report = new Report(pool);
        ResultSet rs = report.getDayAccumulatedSales(3, 2018);
        System.out.println( rs.toString() );
    }
}
