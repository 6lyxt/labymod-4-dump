// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Survey
{
    private int id;
    private Type type;
    private String question;
    private String description;
    private String url;
    @SerializedName("image_url")
    private String imageUrl;
    private List<SurveyOption> options;
    private boolean participated;
    @SerializedName("participant_count")
    private int participantCount;
    
    public int getId() {
        return this.id;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public String getQuestion() {
        return this.question;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public String getImageUrl() {
        return this.imageUrl;
    }
    
    public List<SurveyOption> getOptions() {
        return this.options;
    }
    
    public boolean hasParticipated() {
        return this.participated;
    }
    
    public void setParticipated(final boolean participated) {
        this.participated = participated;
    }
    
    public enum Type
    {
        SINGLE_CHOICE, 
        MULTIPLE_CHOICE, 
        TEXT;
    }
}
