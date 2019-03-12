package com.cyf.eventsapi;


import com.cyf.utility.HelperMethods;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class EventsSubscription
{

    Logger logger = LoggerFactory.getLogger(EventsSubscription.class);

    @Value("${botToken}")
    private String botToken;

    @Value("${token}")
    private String token;

    //Handle incoming challengeparameter when registering eventAPI
    //publish your local api to the outside through ngrok

    @RequestMapping(path="/",method=POST, consumes = "application/json" )
    public String getChallengeParameter(@RequestBody String body) {
        String output = "";
        try {

            logger.info("challengeparms=" + body.toString());
            JSONParser parser = new JSONParser();
             JSONObject json = (JSONObject) parser.parse(body.toString());
             if(json.containsKey("challenge"))
                return json.get("challenge").toString();
             else
             {
                 if(json.containsKey("event"))
                 {
                     JSONObject eventObject = (JSONObject) json.get("event");

                     if(eventObject.get("type").toString().equals("reaction_added"))
                     {
                         logger.info("yes");
                         String user = eventObject.get("user").toString();

                         // post message to that user
                         String https_url = "https://slack.com/api/chat.postMessage";
                         JSONObject jsonObject = new JSONObject();
                         jsonObject.put("channel", eventObject.get("user"));
                         jsonObject.put("text", "Hi! I am StudentBot. Welcome to Code Your Future. Are you Student or a Volunteer....");
                         return HelperMethods.submitURL(https_url, jsonObject, token);
                     }
                     else if(eventObject.get("type").toString().equals("message"))
                     {
                         if(eventObject.get("text").toString().equals("Student")) {
                             JSONObject imeventObject = (JSONObject) json.get("event");
                             if( imeventObject.get("text").toString().equals("Student")) {

                                 String https_url = "https://slack.com/api/chat.postMessage";
                                 JSONObject jsonObject = new JSONObject();
                                 jsonObject.put("channel",eventObject.get("user") );
                                 jsonObject.put("text", "Welcome! Please join Java forum, Javascript forum channels...");
                                 return HelperMethods.submitURL(https_url, jsonObject, token);
                             }
                         }
                     }
                 }
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }




}
