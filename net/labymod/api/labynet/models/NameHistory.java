// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.models;

import java.text.ParseException;
import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;

public class NameHistory
{
    private static final SimpleDateFormat DATE_FORMAT;
    private boolean accurate;
    private String name;
    @SerializedName("changed_at")
    private String changedAt;
    private long changedAtTimestamp;
    
    public NameHistory() {
        this.changedAtTimestamp = -1L;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getChangedAtString() {
        return this.changedAt;
    }
    
    public boolean isAccurate() {
        return this.accurate;
    }
    
    public long getChangedAt() {
        if (this.changedAtTimestamp == -1L) {
            try {
                this.changedAtTimestamp = NameHistory.DATE_FORMAT.parse(this.changedAt).getTime();
            }
            catch (final ParseException e) {
                this.changedAtTimestamp = 0L;
            }
        }
        return this.changedAtTimestamp;
    }
    
    static {
        DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    }
}
