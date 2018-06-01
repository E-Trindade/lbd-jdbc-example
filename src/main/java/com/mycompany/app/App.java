package com.mycompany.app;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import com.google.gson.Gson;
// import org.nanohttpd.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD;


import com.mycompany.app.database.DatabasePool;
import com.mycompany.app.report.MonthlyReportResponse;
import com.mycompany.app.report.Report;

public class App extends NanoHTTPD{
 
    private DatabasePool pool;

    public App(String confPath) throws IOException, SQLException {
        super(8000);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
        
        this.pool = new DatabasePool(new FileReader(confPath));
    }

    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> params = session.getParms();
        String uri = session.getUri(); 
        System.out.println(session.getRemoteHostName() + ": " + session.getMethod() + " " + uri + "?" + session.getQueryParameterString());
        String body;
        try{

            body = "{\"error\": \"Not Found\"}";
            if(uri.equals("/api/monthly-report")){
                Report report = new Report(this.pool);
                MonthlyReportResponse response = report.getDayAccumulatedSales(
                    Integer.parseInt(params.get("month")), 
                    Integer.parseInt(params.get("year"))
                );
                Gson gson = new Gson();
                body = gson.toJson(response);
            }
            if(uri.equals("/monthly-report-ranked")){
                
            }
        }catch(Exception e){
            body = "{\"error\": \"Internal error\"}";
            e.printStackTrace();
        }
        return newFixedLengthResponse(body);
    }
        
    public static void main( String[] args ) throws Exception {
        try {
            new App(args[0]);
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }
}
