package com.mycompany.app;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

import com.google.gson.Gson;
// import org.nanohttpd.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD;

import com.mycompany.app.database.DatabasePool;
import com.mycompany.app.report.MonthlyReportResponse;
import com.mycompany.app.report.Report;

public class App extends NanoHTTPD {

    private DatabasePool pool;

    public App(String confPath) throws IOException, SQLException {
        super(8000);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);

        this.pool = new DatabasePool(new FileReader(confPath));

        System.out.println("\nRunning! Point your browsers to http://localhost:8080/ \n");
        try{
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://localhost:8000"));
            }
        }catch(Exception e){
            // e.printStackTrace();
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> params = session.getParms();
        String uri = session.getUri();
        if (uri.equals("/"))
            uri = "/index.html";
        System.out.println(session.getRemoteHostName() + ": " + session.getMethod() + " " + uri + "?"
                + session.getQueryParameterString());
        NanoHTTPD.Response res = null;
        try {
            if (uri.equals("/api/monthly-report")) {
                Report report = new Report(this.pool);
                MonthlyReportResponse response = report.getDayAccumulatedSales(Integer.parseInt(params.get("month")),
                        Integer.parseInt(params.get("year")));
                Gson gson = new Gson();
                String body = gson.toJson(response);

                res = newFixedLengthResponse(Response.Status.OK, "application/json", body);
            } else if (uri.equals("/api/monthly-report-ranked")) {
                String body = "";
                res = newFixedLengthResponse(Response.Status.OK, "application/json", body);
            } else {
                String filename = "www/" + uri.substring(1);
                // InputStream in = ClassLoader.getSystemResourceAsStream(filename);
                Scanner scn = new Scanner(new FileReader(filename));
                String body = "";
                while (scn.hasNext()) {
                    body += scn.nextLine() + "\n";
                }
                if (filename.indexOf(".js") != -1)
                    res = newFixedLengthResponse(Response.Status.OK, "application/javascript", body);
                else
                    res = newFixedLengthResponse(Response.Status.OK, "text/html", body);
            }

        } catch (FileNotFoundException e) {
            res = newFixedLengthResponse(Response.Status.NOT_FOUND, "application/json", "{\"error\": \"Not Found\"}");
        } catch (Exception e) {
            e.printStackTrace();
            res = newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "application/json",
                    "{\"error\": \"Internal error\"}");
        }
        res.addHeader("Access-Control-Allow-Origin", "*");
        return res;
    }

    public static void main(String[] args) throws Exception {
        try {
            new App(args[0]);
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }
}
