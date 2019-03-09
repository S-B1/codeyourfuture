package com.cyf.webapi;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class ChannelInformation {

    Logger logger = LoggerFactory.getLogger(ChannelInformation.class);

    @Value("${token}")
    private String token;

    @RequestMapping("/api/channelList")
    public String getChannelsList()
    {
        String https_url = "https://slack.com/api/channels.list";
        JSONObject jsonObject = new JSONObject();

        return submitURL(https_url,jsonObject);

    }

    @RequestMapping("/api/channelMessage")
    public String sendMessageToChannel()
    {
        String https_url = "https://slack.com/api/chat.postMessage";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channel","pixel");
        jsonObject.put("text","Hi there!");
        return submitURL(https_url,jsonObject);
    }


    public String submitURL(String https_url,JSONObject jsonObject)
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

