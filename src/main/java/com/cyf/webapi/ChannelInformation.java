package com.cyf.webapi;

import com.cyf.utility.HelperMethods;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class ChannelInformation {

    Logger logger = LoggerFactory.getLogger(ChannelInformation.class);
    Map<String,String> userMap = new TreeMap<String,String>();

    @Value("${token}")
    private String token;


// To access above request add permission in your slackApi channels:read,channels:write,chat:write:user

    @RequestMapping("/api/channelList")
    public String getChannelsList()
    {
        String output =" ";
        try {
            String https_url = "https://slack.com/api/channels.list";
            JSONObject jsonObject = new JSONObject();
             output = HelperMethods.submitURL(https_url, jsonObject, token);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return output;
    }

    @RequestMapping("/api/channelMessage")
    public String sendMessageToChannel()
    {
        String https_url = "https://slack.com/api/chat.postMessage";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("channel","pixel,general");
        jsonObject.put("text","Hi there!");
        return HelperMethods.submitURL(https_url, jsonObject, token);
    }

    @RequestMapping("/api/usersList")
    public String getUserList()
    {
        String output =" ";
        try {
            String https_url = "https://slack.com/api/users.list";
            JSONObject jsonObject = new JSONObject();
            output = HelperMethods.submitURL(https_url, jsonObject, token);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(output.toString());
            JSONArray membersObject = (JSONArray) json.get("members");

           // JSONArray values = new JSONArray();
           // values.add(membersObject);

            for (int i = 0; i < membersObject.size(); i++)
            {

                JSONObject user = (JSONObject) membersObject.get(i);
                JSONObject profileObject = (JSONObject) user.get("profile");
                System.out.println("profileObject"+profileObject);
                if (profileObject.get("display_name") != null && !profileObject.get("display_name").equals("")) {
                        System.out.println("id=" + user.get("id").toString() + "display_name=" + profileObject.get("display_name").toString());
                        userMap.put(user.get("id").toString(), profileObject.get("display_name").toString());
                    }

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return output;
    }

    @RequestMapping("/api/usersConversations")
    public String getUserConversations(@RequestParam String userName)
    {
        String https_url = "https://slack.com/api/users.conversations";
        JSONObject jsonObject = new JSONObject();
        Iterator keys = userMap.keySet().iterator();
        logger.info("userMap="+userMap.size());
        for(Map.Entry<String,String> entry : userMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if(value.equals(userName))
                jsonObject.put("user",key);
        }

        return HelperMethods.submitURL(https_url, jsonObject, token);
    }


    @RequestMapping("/api/usersReactions")
    public String getUserReactions(@RequestParam String userName)
    {
        String https_url = "https://slack.com/api/reactions.list";
        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String,String> entry : userMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            logger.info(key + " => " + value);
            if(value.equals(userName))
                jsonObject.put("user",key);
        }

        return HelperMethods.submitURL(https_url, jsonObject, token);
    }
}

