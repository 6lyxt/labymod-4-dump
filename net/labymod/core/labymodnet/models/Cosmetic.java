// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.models;

import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.client.gui.icon.Icon;
import org.jetbrains.annotations.Nullable;
import com.google.gson.annotations.SerializedName;

public class Cosmetic
{
    private final int id;
    @SerializedName("item_id")
    private int itemId;
    private final String name;
    private final String[] options;
    private String[] data;
    private String[] defaultData;
    private boolean enabled;
    @SerializedName("group_id")
    @Nullable
    private Integer groupId;
    private final String category;
    private transient Icon icon;
    
    public Cosmetic(final int id, final int itemId, final String name, final String[] options, final String[] data, final String[] defaultData, final boolean enabled, final int groupId, final String category) {
        this.id = id;
        this.itemId = itemId;
        this.name = name;
        this.options = options;
        this.data = data;
        this.defaultData = defaultData;
        this.enabled = enabled;
        this.groupId = groupId;
        this.category = category;
    }
    
    public int getId() {
        return this.id;
    }
    
    public int getItemId() {
        return this.itemId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String[] getOptions() {
        return this.options;
    }
    
    public String[] getData() {
        return this.data;
    }
    
    public void setData(final String[] data) {
        this.data = data;
    }
    
    public String[] getDefaultData() {
        return this.defaultData;
    }
    
    public void setDefaultData(final String[] defaultData) {
        this.defaultData = defaultData;
    }
    
    public void resetData() {
        if (this.defaultData == null) {
            final Notification notification = Notification.builder().title(Component.translatable("labymod.misc.error", new Component[0])).text(Component.translatable("labymod.notification.cosmetic_default_data.description", new Component[0])).build();
            Laby.references().notificationController().push(notification);
            return;
        }
        System.arraycopy(this.defaultData, 0, this.data, 0, this.data.length);
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    @Nullable
    public Integer getGroupId() {
        return this.groupId;
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public Icon icon() {
        if (this.icon == null) {
            this.icon = Icon.url(String.format(Locale.ROOT, "https://www.labymod.net/page/tpl/assets/images/shop/products/%s_0.png", this.name.toLowerCase(Locale.ROOT).replace(" ", "-")));
        }
        return this.icon;
    }
}
