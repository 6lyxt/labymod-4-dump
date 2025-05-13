// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.type;

import net.labymod.api.loader.LabyModLoader;
import net.labymod.api.configuration.loader.property.CustomRequires;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.revision.Revision;
import java.util.function.BooleanSupplier;
import java.lang.annotation.Annotation;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.configuration.settings.SettingOverlayInfo;
import net.labymod.api.configuration.settings.SettingHandler;
import net.labymod.api.configuration.settings.SwitchableInfo;

public class SettingElement extends AbstractSettingRegistry
{
    private final SettingPermissionHolder permissionHolder;
    private final byte orderValue;
    private String customTranslation;
    private String[] searchTags;
    private SwitchableInfo switchableInfo;
    private SettingHandler handler;
    private SettingOverlayInfo overlayInfo;
    private Widget[] widgets;
    private SettingAccessor accessor;
    private SettingAccessor advancedAccessor;
    private Annotation annotation;
    private Runnable resetListener;
    private BooleanSupplier visibleSupplier;
    private boolean extended;
    private Revision revision;
    
    public SettingElement(final String id, final Icon icon, final String customTranslation, final String[] searchTags, final SettingPermissionHolder permissionHolder, final SwitchableInfo switchableInfo, final byte orderValue) {
        super(id, icon);
        this.permissionHolder = new SettingPermissionHolder();
        this.extended = false;
        this.revision = null;
        this.customTranslation = customTranslation;
        this.searchTags = searchTags;
        this.permissionHolder.set(permissionHolder);
        this.switchableInfo = switchableInfo;
        this.orderValue = orderValue;
    }
    
    public SettingElement(final String id, final Icon icon, final String customTranslation, final String[] searchTags, final String requiredPermission, final boolean canForceEnable, final SwitchableInfo switchableInfo, final byte orderValue) {
        this(id, icon, customTranslation, searchTags, new SettingPermissionHolder(requiredPermission, canForceEnable), switchableInfo, orderValue);
    }
    
    public SettingElement(final String id, final Icon icon, final String customTranslation, final String[] searchTags) {
        this(id, icon, customTranslation, searchTags, null, false, null, (byte)0);
    }
    
    public SettingAccessor getAccessor() {
        return this.accessor;
    }
    
    public void setAccessor(final SettingAccessor accessor) {
        this.accessor = accessor;
    }
    
    public void setRevision(final Revision revision) {
        this.revision = revision;
    }
    
    public Runnable getResetListener() {
        return this.resetListener;
    }
    
    public void setResetListener(final Runnable resetListener) {
        this.resetListener = resetListener;
    }
    
    public boolean isVisible() {
        if (this.accessor != null && this.accessor.property() != null) {
            BooleanSupplier visibilitySupplier = this.accessor.property().getVisibilitySupplier();
            if (visibilitySupplier != null) {
                return visibilitySupplier.getAsBoolean();
            }
            final SettingAccessor advancedAccessor = this.accessor.setting().getAdvancedAccessor();
            if (advancedAccessor != null) {
                visibilitySupplier = advancedAccessor.property().getVisibilitySupplier();
                if (visibilitySupplier != null) {
                    return visibilitySupplier.getAsBoolean();
                }
            }
        }
        return this.visibleSupplier == null || this.visibleSupplier.getAsBoolean();
    }
    
    public void setVisibleSupplier(final BooleanSupplier visibleSupplier) {
        this.visibleSupplier = visibleSupplier;
    }
    
    public boolean isExtended() {
        return this.extended;
    }
    
    public void setExtended(final boolean extended) {
        this.extended = extended;
    }
    
    public Annotation getAnnotation() {
        return this.annotation;
    }
    
    public void setAnnotation(final Annotation annotation) {
        this.annotation = annotation;
    }
    
    public Widget[] getWidgets() {
        return this.widgets;
    }
    
    public void setWidgets(final Widget[] widgets) {
        this.widgets = widgets;
    }
    
    @Nullable
    public Revision getRevision() {
        return this.revision;
    }
    
    @Override
    public boolean isEnabled() {
        if (!super.isEnabled()) {
            return false;
        }
        if (this.switchableInfo == null || this.switchableInfo.getSwitchAccessor() == null) {
            if (this.accessor != null) {
                final CustomRequires<Object> customRequires = this.accessor.property().getCustomRequires();
                if (customRequires != null) {
                    return customRequires.isEnabled(this.accessor.get());
                }
            }
            return true;
        }
        final SettingAccessor accessor = this.switchableInfo.getSwitchAccessor();
        final SettingElement setting = accessor.setting();
        if (!setting.isEnabled()) {
            return false;
        }
        final Object value = accessor.get();
        final boolean invert = this.switchableInfo.isInvert();
        if (value instanceof Boolean) {
            return invert != (boolean)value;
        }
        if (setting.advancedAccessor != null) {
            final Object advancedValue = setting.advancedAccessor.get();
            if (advancedValue instanceof Boolean) {
                return invert != (boolean)advancedValue;
            }
        }
        if (this.switchableInfo.getHandler() != null) {
            return invert != this.switchableInfo.getHandler().isEnabled(this, value, this.switchableInfo);
        }
        final CustomRequires<Object> switchable = accessor.property().getCustomRequires();
        if (switchable != null) {
            return invert != switchable.isEnabled(value);
        }
        final LabyModLoader labyModLoader = Laby.labyAPI().labyModLoader();
        if (labyModLoader.isLabyModDevelopmentEnvironment() || labyModLoader.isAddonDevelopmentEnvironment()) {
            Laby.labyAPI().minecraft().crashGame("No CustomSwitchable was set for the config property \"" + accessor.getField().getName() + "\". (Use ConfigProperty#setCustomRequires(CustomRequires))", (Throwable)new RuntimeException());
            return false;
        }
        return true;
    }
    
    @Override
    public boolean hasControlButton() {
        return this.advancedAccessor != null;
    }
    
    public SettingAccessor getAdvancedAccessor() {
        return this.advancedAccessor;
    }
    
    public void setAdvancedAccessor(final SettingAccessor advancedAccessor) {
        this.advancedAccessor = advancedAccessor;
    }
    
    @Override
    public String getTranslationKey() {
        return (this.customTranslation == null) ? super.getTranslationKey() : this.customTranslation;
    }
    
    public void setCustomTranslation(final String customTranslation) {
        this.customTranslation = customTranslation;
    }
    
    @Override
    public String[] getSearchTags() {
        return this.searchTags;
    }
    
    public void setSearchTags(final String[] searchTags) {
        this.searchTags = searchTags;
    }
    
    @Nullable
    @Override
    public String getRequiredPermission() {
        return this.permissionHolder.getPermissionId();
    }
    
    public void setRequiredPermission(@Nullable final String requiredPermission) {
        this.permissionHolder.setPermissionId(requiredPermission);
    }
    
    @Override
    public boolean canForceEnable() {
        return this.permissionHolder.isCanForceEnable();
    }
    
    public void setCanForceEnable(final boolean canForceEnable) {
        this.permissionHolder.setCanForceEnable(canForceEnable);
    }
    
    public SwitchableInfo getSwitchableInfo() {
        return this.switchableInfo;
    }
    
    public void setSwitchableInfo(final SwitchableInfo switchableInfo) {
        this.switchableInfo = switchableInfo;
    }
    
    public byte getOrderValue() {
        return this.orderValue;
    }
    
    @Override
    public SettingHandler handler() {
        return this.handler;
    }
    
    public void setHandler(final SettingHandler handler) {
        this.handler = handler;
    }
    
    public SettingOverlayInfo getOverlayInfo() {
        return this.overlayInfo;
    }
    
    public void setOverlayInfo(final SettingOverlayInfo overlayInfo) {
        this.overlayInfo = overlayInfo;
    }
    
    @Deprecated
    public void searchTags(final String[] searchTags) {
        this.setSearchTags(searchTags);
    }
}
