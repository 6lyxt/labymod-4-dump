// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.model;

import com.google.gson.annotations.SerializedName;

public class SurveyOption
{
    private int id;
    @SerializedName("survey_id")
    private int surveyId;
    private String text;
    private String url;
    private String imageUrl;
    private boolean voted;
    @SerializedName("vote_count")
    private int voteCount;
    
    public int getId() {
        return this.id;
    }
    
    public String getText() {
        return this.text;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public String getImageUrl() {
        return this.imageUrl;
    }
    
    public boolean isVoted() {
        return this.voted;
    }
    
    public void setVoted(final boolean voted) {
        this.voted = voted;
    }
    
    public int getVoteCount() {
        return this.voteCount;
    }
}
