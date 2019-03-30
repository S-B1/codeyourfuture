package com.cyf.webapi;

import com.cyf.entities.User;
import com.cyf.entities.UserRepository;
import com.cyf.utility.HelperMethods;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class ChannelInformation
{
    Logger logger = LoggerFactory.getLogger(ChannelInformation.class);
    Map<String,String> userMap = new TreeMap<String,String>();

    @Autowired
    UserRepository userRepository;

    @Value("${token}")
    private String token;

    @RequestMapping("/api/channelList")
    public String getChannelsList()
    {
        String output =" ";
        try {
            String https_url = "https://slack.com/api/channels.list";
            JSONObject jsonObject = new JSONObject();
             output = HelperMethods.submitURL(https_url, jsonObject, token);

        } catch (Exception e)
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
    public String saveUserList()
    {
        String output = " ";
        try {
            String https_url = "https://slack.com/api/users.list";
            JSONObject jsonObject = new JSONObject();
            output = HelperMethods.submitURL(https_url, jsonObject, token);
            JSONTokener temp = new JSONTokener(output.toString());
            JSONObject json = new JSONObject(temp);

            JSONArray membersObj = (JSONArray)json.get("members");
            System.out.println(membersObj.length());

            for (int i = 0; i < membersObj.length(); i++)
            {
                JSONObject membersDetails = (JSONObject)membersObj.get(i);

                if ( membersDetails.get("is_bot").toString().equals("false") )
                {
                    JSONObject userDetails = (JSONObject)membersObj.get(i);
                    JSONObject profile = userDetails.getJSONObject("profile");

                    userRepository.save(new User(
                            membersDetails.get("id").toString(),
                            membersDetails.get("name").toString(),
                            "")
                    );
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return output;
    }

    @RequestMapping("/api/getUsersList")
    public List<User> getUserList()
    {
        List<User> userList = userRepository.findAll();
        return userList;
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

        for (Map.Entry<String,String> entry : userMap.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            logger.info(key + " => " + value);
            if(value.equals(userName))
                jsonObject.put("user",key);
        }

        return HelperMethods.submitURL(https_url, jsonObject, token);
    }
}
