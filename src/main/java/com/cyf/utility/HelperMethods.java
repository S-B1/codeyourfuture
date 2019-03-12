package com.cyf.utility;

import com.cyf.webapi.ChannelInformation;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class HelperMethods
{
    public static Logger logger = LoggerFactory.getLogger(HelperMethods.class);
    public static String  submitURL(String https_url, JSONObject jsonObject, String token)
    {
        StringBuffer response = new StringBuffer();
        URL url;
        try {
            url = new URL(https_url);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            //Request
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json; charset=UTF-8");
            connection.setRequestProperty( "Authorization", "Bearer "+token);
            connection.setDoOutput(true);
            logger.info("JSONString:"+jsonObject.toJSONString());
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(jsonObject.toJSONString());
            wr.flush();
            wr.close();
            ///Get Response
            String readLine = null;
            int responseCode = connection.getResponseCode();
            logger.info("responseCode=" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
            }
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }


}
