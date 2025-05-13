// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.user.group;

import net.labymod.api.client.component.format.TextColor;
import org.jetbrains.annotations.NotNull;
import java.awt.Color;
import com.google.gson.annotations.SerializedName;

public class Group
{
    @SerializedName("id")
    private int identifier;
    @SerializedName("name")
    private String name;
    @SerializedName("nice_name")
    private String displayName;
    @SerializedName("color_hex")
    private String colorHex;
    @SerializedName("color_minecraft")
    private char minecraftColorChar;
    @SerializedName("tag_name")
    private String tagName;
    @SerializedName("display_type")
    private String displayTypeName;
    @SerializedName("is_staff")
    private boolean isStaff;
    private transient GroupDisplayType displayType;
    @NotNull
    private transient Color color;
    private transient TextColor textColor;
    
    public Group(final int identifier, final String name, final String displayName, final String colorHex, final char minecraftColorChar, final String tagName, final String displayTypeName, final boolean isStaff) {
        this.color = Color.WHITE;
        this.identifier = identifier;
        this.name = name;
        this.displayName = displayName;
        this.colorHex = colorHex;
        this.minecraftColorChar = minecraftColorChar;
        this.tagName = tagName;
        this.displayTypeName = displayTypeName;
        this.isStaff = isStaff;
    }
    
    public Group initialize() {
        if (this.displayTypeName == null) {
            this.displayType = GroupDisplayType.NONE;
        }
        else {
            final GroupDisplayType displayType = GroupDisplayType.getDisplayType(this.displayTypeName);
            this.displayType = ((displayType == null) ? GroupDisplayType.NONE : displayType);
        }
        try {
            if (this.colorHex != null && !this.colorHex.isEmpty()) {
                this.color = Color.decode("#" + this.colorHex);
                this.textColor = TextColor.color(this.color.getRGB());
            }
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
        return this;
    }
    
    public int getIdentifier() {
        return this.identifier;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public String getColorHex() {
        return this.colorHex;
    }
    
    public char getMinecraftColorChar() {
        return this.minecraftColorChar;
    }
    
    public String getTagName() {
        return this.tagName;
    }
    
    public GroupDisplayType getDisplayType() {
        return this.displayType;
    }
    
    @NotNull
    public Color getColor() {
        return this.color;
    }
    
    public TextColor getTextColor() {
        return this.textColor;
    }
    
    public boolean isDefault() {
        return this.identifier == 0;
    }
    
    public boolean isStaff() {
        return this.isStaff;
    }
    
    public boolean isStaffOrCosmeticCreator() {
        return this.isStaff || this.identifier == 11;
    }
    
    public boolean isLabyModPlus() {
        return this.identifier == 10;
    }
    
    @Override
    public String toString() {
        return "Group[" + this.name + "(" + this.identifier + ")]";
    }
}
