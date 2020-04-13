package com.tsystems.service.impl;

import com.tsystems.service.api.RestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Stateless
public class RestServiceImpl implements RestService {
    private static final Logger LOGGER= LogManager.getLogger(RestServiceImpl.class);

    @Override
    public String executeRequest(String targetUrl) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            if (connection.getResponseCode() != 200) {
                throw new IOException();
            }

            //Get Response
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String json = br.readLine();
            br.close();
            return json;
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            LOGGER.error(stringWriter.toString());
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
