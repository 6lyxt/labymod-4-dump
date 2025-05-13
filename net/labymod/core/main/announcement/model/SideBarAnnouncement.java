// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.announcement.model;

import net.labymod.api.Constants;
import java.util.Locale;
import com.google.gson.annotations.SerializedName;
import java.awt.Color;

public class SideBarAnnouncement
{
    private EnumAnnouncementSide side;
    private String title;
    private Color color;
    private String link;
    private boolean social;
    @SerializedName("exclamation_mark")
    private boolean exclamationMark;
    @SerializedName("hover_color")
    private Color hoverColor;
    @SerializedName("icon_name")
    private String iconName;
    
    public SideBarAnnouncement() {
        this.color = Color.WHITE;
        this.social = true;
        this.hoverColor = Color.WHITE;
    }
    
    public EnumAnnouncementSide getSide() {
        return this.side;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public Color getHoverColor() {
        return this.hoverColor;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getLink() {
        return this.link;
    }
    
    public String getIconName() {
        return this.iconName;
    }
    
    public boolean hasExclamationMark() {
        return this.exclamationMark;
    }
    
    public boolean isSocial() {
        return this.social;
    }
    
    public String getIconUrl() {
        return String.format(Locale.ROOT, "%s/%s.png", Constants.Urls.ANNOUNCEMENTS_ICONS, this.iconName);
    }
}
