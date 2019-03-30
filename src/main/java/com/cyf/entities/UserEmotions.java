package com.cyf.entities;

public class UserEmotions
{
    private String emojiName;
    private String userId;
    private String channelName;

    public UserEmotions(String emojiName, String userId, String channelName)
    {
        this.emojiName = emojiName;
        this.userId = userId;
        this.channelName = channelName;
    }

    public String getEmojiName() {
        return emojiName;
    }

    public void setEmojiName(String emojiName)
    {
        this.emojiName = emojiName;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getChannelName()
    {
        return channelName;
    }

    public void setChannelName(String channelName)
    {
        this.channelName = channelName;
    }
}
