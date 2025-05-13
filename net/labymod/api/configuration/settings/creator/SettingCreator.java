// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator;

import net.labymod.api.configuration.loader.annotation.SearchTag;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.settings.annotation.SettingRequiresExclude;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import java.lang.reflect.Field;
import net.labymod.api.configuration.settings.SettingHandler;
import net.labymod.api.configuration.settings.widget.WidgetRegistry;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.event.labymod.config.SettingCreateEvent;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.revision.Revision;
import net.labymod.api.configuration.settings.type.list.ListSetting;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import java.util.Objects;
import net.labymod.api.configuration.loader.annotation.ParentSwitch;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.settings.annotation.SettingExperimental;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.api.configuration.settings.type.SettingPermissionHolder;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.TextFormat;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyHolder;
import net.labymod.api.configuration.settings.type.SettingHeader;
import net.labymod.api.configuration.settings.creator.availability.MemberAvailabilityContext;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import net.labymod.api.configuration.settings.annotation.SettingListener;
import java.lang.annotation.Annotation;
import net.labymod.api.configuration.settings.SwitchableInfo;
import net.labymod.api.Laby;
import net.labymod.api.configuration.settings.SwitchableHandler;
import net.labymod.api.configuration.settings.type.SettingElement;
import java.util.Iterator;
import java.util.Comparator;
import net.labymod.api.util.reflection.Reflection;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.creator.hotkey.SwitchSettingHotkeyFactory;
import net.labymod.api.configuration.settings.creator.hotkey.DefaultHotkeyFactory;
import net.labymod.api.configuration.settings.creator.availability.OperatingSystemCompatibleMemberAvailability;
import net.labymod.api.configuration.settings.creator.availability.VersionCompatibleMemberAvailability;
import net.labymod.api.configuration.settings.creator.availability.DevelopmentMemberAvailability;
import net.labymod.api.configuration.settings.creator.availability.ExcludeMemberAvailability;
import java.util.ArrayList;
import net.labymod.api.configuration.settings.annotation.CustomTranslation;
import net.labymod.api.configuration.settings.annotation.SettingOrder;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.settings.creator.hotkey.HotkeyFactory;
import net.labymod.api.configuration.settings.creator.availability.MemberAvailability;
import java.util.List;
import net.labymod.api.configuration.settings.creator.accessor.SettingAccessorFactoryRegistry;
import net.labymod.api.configuration.settings.creator.visibility.VisibleProcessorRegistry;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.LabyAPI;
import net.labymod.api.util.logging.Logging;

public class SettingCreator
{
    private static final Logging LOGGER;
    private static final int DEFAULT_SPRITE_SIZE = 128;
    private final LabyAPI labyAPI;
    private final Config config;
    private final Class<? extends Config> configClass;
    private final VisibleProcessorRegistry visibleProcessorRegistry;
    private final SettingAccessorFactoryRegistry settingAccessorFactoryRegistry;
    private final List<MemberAvailability> memberAvailabilities;
    private final List<SettingEntry> entries;
    private final List<HotkeyFactory> hotkeyFactories;
    private SpriteTexture spriteTexture;
    private SettingRequires parentSwitchable;
    private SettingOrder parentOrder;
    private CustomTranslation parentCustomTranslation;
    
    public SettingCreator(final LabyAPI labyAPI, final Config config) {
        this.memberAvailabilities = new ArrayList<MemberAvailability>();
        this.entries = new ArrayList<SettingEntry>();
        this.hotkeyFactories = new ArrayList<HotkeyFactory>();
        this.labyAPI = labyAPI;
        this.config = config;
        this.configClass = config.getClass();
        this.visibleProcessorRegistry = new VisibleProcessorRegistry(this.labyAPI);
        this.settingAccessorFactoryRegistry = new SettingAccessorFactoryRegistry(this.labyAPI);
        this.registerDefaults();
    }
    
    private void registerDefaults() {
        this.memberAvailabilities.add(new ExcludeMemberAvailability());
        this.memberAvailabilities.add(new DevelopmentMemberAvailability(this.labyAPI));
        this.memberAvailabilities.add(new VersionCompatibleMemberAvailability(this.labyAPI));
        this.memberAvailabilities.add(new OperatingSystemCompatibleMemberAvailability());
        this.hotkeyFactories.add(new DefaultHotkeyFactory());
        this.hotkeyFactories.add(new SwitchSettingHotkeyFactory());
    }
    
    public List<Setting> createSettings(@Nullable final Setting parent, SpriteTexture spriteTexture) {
        final SpriteTexture configSpriteTexture = this.configClass.getAnnotation(SpriteTexture.class);
        if (configSpriteTexture != null) {
            spriteTexture = configSpriteTexture;
        }
        this.spriteTexture = spriteTexture;
        this.parentSwitchable = Reflection.getAnnotation(this.configClass, SettingRequires.class);
        this.parentOrder = Reflection.getAnnotation(this.configClass, SettingOrder.class);
        this.parentCustomTranslation = Reflection.getAnnotation(this.configClass, CustomTranslation.class);
        Reflection.getMembers(this.configClass, true, member -> this.handleMember(parent, member));
        this.entries.sort(Comparator.comparingInt(o -> o.setting().getOrderValue()));
        final List<Setting> settings = new ArrayList<Setting>(this.entries.size());
        for (final SettingEntry entry : this.entries) {
            if (entry.header() != null) {
                settings.add(entry.header());
            }
            settings.add(entry.setting());
        }
        this.entries.clear();
        this.applySwitchAccessors(settings);
        final List<SettingListenerMethod> settingListeners = SettingListenerCollector.collect(this.configClass);
        if (settingListeners.isEmpty()) {
            return settings;
        }
        this.applyListeners(settings, settingListeners);
        return settings;
    }
    
    private void applySwitchAccessors(final List<Setting> settings) {
        for (final Setting setting : settings) {
            if (!setting.isElement()) {
                continue;
            }
            final SettingElement element = setting.asElement();
            if (element.getSwitchableInfo() == null) {
                continue;
            }
            this.findTargetSwitchSetting(element, settings);
        }
    }
    
    private void findTargetSwitchSetting(final SettingElement element, final List<Setting> settings) {
        for (final Setting switchSetting : settings) {
            final SwitchableInfo switchableInfo = element.getSwitchableInfo();
            if (switchSetting.isElement() && switchSetting.getId().equals(switchableInfo.getSwitchId())) {
                final SettingElement switchSettingElement = switchSetting.asElement();
                switchableInfo.setSwitchAccessor(switchSettingElement.getAccessor());
                final Annotation settingAnnotation = switchSettingElement.getAnnotation();
                if (settingAnnotation != null) {
                    final net.labymod.api.configuration.settings.annotation.SettingElement switchElement = settingAnnotation.annotationType().getAnnotation(net.labymod.api.configuration.settings.annotation.SettingElement.class);
                    if (switchElement != null) {
                        final Class<? extends SwitchableHandler> switchableClass = switchElement.switchable();
                        if (switchableClass != SwitchableHandler.class) {
                            final SwitchableHandler handler = Laby.references().switchableHandlerRegistry().getHandler(switchableClass);
                            if (handler != null) {
                                switchableInfo.setHandler(handler);
                            }
                        }
                    }
                    break;
                }
                break;
            }
        }
    }
    
    private void applyListeners(final List<Setting> settings, final List<SettingListenerMethod> listeners) {
        for (final Setting setting : settings) {
            if (!setting.isElement()) {
                continue;
            }
            for (final SettingListenerMethod listener : listeners) {
                if (!listener.target().equals(setting.getId())) {
                    continue;
                }
                final SettingListener.EventType eventType = listener.eventType();
                if (eventType == SettingListener.EventType.RESET) {
                    setting.asElement().setResetListener(() -> listener.invoke(this.config, setting));
                }
                else {
                    listener.invoke(this.config, setting);
                }
            }
        }
    }
    
    private <T extends Member & AnnotatedElement> void handleMember(@Nullable final Setting parent, final T member) {
        final MemberInspector inspector = new MemberInspector(member);
        final String namespace = this.labyAPI.getNamespace(this.configClass);
        final MemberAvailabilityContext context = new MemberAvailabilityContext(namespace, inspector);
        for (final MemberAvailability memberAvailability : this.memberAvailabilities) {
            final boolean available = memberAvailability.isAvailable(context);
            if (!available) {
                return;
            }
        }
        final SettingHeader header = this.createHeader(inspector);
        final SettingEntry settingEntry = new SettingEntry(header, this.createElement(parent, inspector, namespace));
        this.registerHotkeyForSetting(settingEntry, inspector);
        this.entries.add(settingEntry);
    }
    
    private void registerHotkeyForSetting(final SettingEntry entry, final MemberInspector inspector) {
        final String displayName = this.labyAPI.getDisplayName(this.configClass);
        for (final HotkeyFactory hotkeyFactory : this.hotkeyFactories) {
            if (!hotkeyFactory.hasSettingAnnotation(inspector)) {
                continue;
            }
            final Hotkey hotkey = hotkeyFactory.create(inspector, displayName, entry);
            if (hotkey == null) {
                continue;
            }
            HotkeyHolder.getInstance().registerHotkey(hotkey);
            break;
        }
    }
    
    private void dumpIcon(@Nullable final Setting parent, final SettingElement settingElement) {
        final Icon icon = settingElement.getIcon();
        if (icon == null || !IdeUtil.DUMP_SPRITE_ICONS) {
            return;
        }
        settingElement.setParent(parent);
        final String locationPath = settingElement.getTranslationKey();
        String path = TextFormat.CAMEL_CASE.toSnakeCase(locationPath);
        path = path.replace("classic_pv_p", "classic_pvp");
        IdeUtil.dumpSpriteIcons(path, icon);
    }
    
    private SettingElement createElement(@Nullable final Setting parent, final MemberInspector inspector, final String namespace) {
        final String id = inspector.getName();
        final Icon icon = this.createIcon(namespace, inspector);
        final SettingRequires switchable = inspector.orElse(SettingRequires.class, this.parentSwitchable);
        final SettingOrder order = inspector.orElse(SettingOrder.class, this.parentOrder);
        final SettingPermissionHolder permissionHolder = inspector.getOrElse(PermissionRequired.class, SettingPermissionHolder::new, new SettingPermissionHolder());
        final byte orderValue = (byte)((order == null) ? 0 : order.value());
        final String translationKey = this.buildTranslationKey(id, inspector);
        final String[] tags = this.getTags(inspector);
        final SwitchableInfo switchableInfo = this.createSwitchableInfo(id, switchable, inspector);
        final SettingElement settingElement = new SettingElement(id, icon, translationKey, tags, permissionHolder, switchableInfo, orderValue);
        this.dumpIcon(parent, settingElement);
        settingElement.setVisibleSupplier(this.visibleProcessorRegistry.canSeeElement(inspector));
        settingElement.setExperimental(inspector.isAnnotationPresent(SettingExperimental.class));
        final SettingAccessor accessor = this.settingAccessorFactoryRegistry.createAccessor(settingElement, inspector, this.config);
        if (inspector.isAnnotationPresent(ShowSettingInParent.class) && parent instanceof SettingElement) {
            final SettingElement parentElement = (SettingElement)parent;
            parentElement.setSearchTags(tags);
            parentElement.setAdvancedAccessor(accessor);
            parentElement.setRequiredPermission(permissionHolder.getPermissionId());
            final SettingInfo info = new SettingInfo(this.config, (M)inspector.member());
            final WidgetRegistry widgetRegistry = this.labyAPI.widgetRegistry();
            final Widget[] widgets = widgetRegistry.createWidgets(settingElement, info, accessor);
            parentElement.setWidgets(widgets);
            if (inspector.getAnnotation(ShowSettingInParent.class).showOnlyInParent()) {
                return settingElement;
            }
        }
        if (inspector.isAnnotationPresent(ParentSwitch.class) && parent instanceof SettingElement) {
            final SettingElement parentElement = (SettingElement)parent;
            parentElement.setSearchTags(tags);
            parentElement.setAdvancedAccessor(accessor);
            parentElement.setRequiredPermission(permissionHolder.getPermissionId());
            final SettingInfo info = new SettingInfo(this.config, (M)inspector.member());
            final WidgetRegistry widgetRegistry = this.labyAPI.widgetRegistry();
            Widget[] widgets = widgetRegistry.createWidgets(settingElement, info, accessor);
            if (widgets == null) {
                final SettingAccessor obj = accessor;
                Objects.requireNonNull(obj);
                final SwitchWidget controlButton = SwitchWidget.create(obj::set);
                controlButton.setValue(accessor.get());
                controlButton.addId("advanced-control-button");
                widgets = new Widget[] { controlButton };
            }
            parentElement.setWidgets(widgets);
            if (inspector.getAnnotation(ParentSwitch.class).hide()) {
                return settingElement;
            }
        }
        final WidgetRegistry widgetRegistry2 = this.labyAPI.widgetRegistry();
        settingElement.setParent(parent);
        final SettingInfo info = new SettingInfo(this.config, (M)inspector.member());
        settingElement.setWidgets(widgetRegistry2.createWidgets(settingElement, info, accessor));
        if (accessor != null && List.class.isAssignableFrom(accessor.getType()) && settingElement.getWidgets() == null) {
            try {
                return new ListSetting(id, icon, translationKey, tags, permissionHolder, switchableInfo, orderValue, accessor);
            }
            catch (final Exception exception) {
                SettingCreator.LOGGER.error("Failed to create ListSetting", exception);
            }
        }
        settingElement.setAccessor(accessor);
        final Revision revision = inspector.get(IntroducedIn.class, introducedIn -> Laby.references().revisionRegistry().getRevision(namespace, introducedIn.value()));
        settingElement.setRevision(revision);
        this.initializeChildConfig(settingElement, inspector);
        Laby.fireEvent(new SettingCreateEvent(settingElement));
        final SettingHandler handler = settingElement.handler();
        if (handler != null) {
            handler.created(settingElement);
        }
        return settingElement;
    }
    
    private void initializeChildConfig(final SettingElement setting, final MemberInspector inspector) {
        final AnnotatedElement member = inspector.member();
        if (!(member instanceof Field)) {
            return;
        }
        final Field field = (Field)member;
        if (!Config.class.isAssignableFrom(field.getType())) {
            return;
        }
        final Config childConfig = Reflection.invokeGetterField(this.config, field);
        if (childConfig == null) {
            return;
        }
        setting.addSettings(childConfig.toSettings(setting, this.spriteTexture));
    }
    
    @Nullable
    private SettingHeader createHeader(final MemberInspector inspector) {
        final SettingSection annotation = inspector.getAnnotation(SettingSection.class);
        if (annotation == null) {
            return null;
        }
        final String translationId = annotation.value();
        final String sectionId = translationId.replace(".", "_");
        return new SettingHeader(sectionId, annotation.center(), annotation.translation(), translationId);
    }
    
    private SwitchableInfo createSwitchableInfo(final String id, final SettingRequires switchable, final MemberInspector inspector) {
        if (switchable == null) {
            return null;
        }
        if (id.equals(switchable.value())) {
            return null;
        }
        if (inspector.isAnnotationPresent(SettingRequiresExclude.class)) {
            return null;
        }
        return new SwitchableInfo(switchable);
    }
    
    @Nullable
    private Icon createIcon(final String namespace, final MemberInspector inspector) {
        if (this.spriteTexture == null) {
            return null;
        }
        final SpriteSlot slot = inspector.getAnnotation(SpriteSlot.class);
        if (slot == null) {
            return null;
        }
        final ThemeTextureLocation location = Laby.references().resourceLocationFactory().createThemeTexture(namespace, "textures/" + this.getSpritePath(slot), 128, 128);
        return Icon.sprite(location, slot.x(), slot.y(), slot.size(), slot.size());
    }
    
    private String getSpritePath(final SpriteSlot slot) {
        final int page = slot.page();
        final String suffix = (page == 0) ? "" : ("_" + page);
        String texturePath = this.spriteTexture.value();
        final int lastDotIndex = texturePath.lastIndexOf(46);
        if (lastDotIndex != -1) {
            texturePath = texturePath.substring(0, lastDotIndex);
        }
        return texturePath + suffix + "." + this.spriteTexture.type();
    }
    
    private String[] getTags(final MemberInspector inspector) {
        final SearchTag annotation = inspector.getAnnotation(SearchTag.class);
        if (annotation == null) {
            return new String[0];
        }
        return annotation.value();
    }
    
    private String buildTranslationKey(final String id, final MemberInspector inspector) {
        String translationKey = inspector.getOrElse(CustomTranslation.class, CustomTranslation::value, this.buildParentTranslationKey());
        if (translationKey != null && translationKey.endsWith(".")) {
            translationKey += id;
        }
        return translationKey;
    }
    
    private String buildParentTranslationKey() {
        final CustomTranslation translation = this.parentCustomTranslation;
        if (translation == null) {
            return null;
        }
        String translationKey = translation.value();
        if (!translationKey.endsWith(".")) {
            translationKey = translationKey;
        }
        return translationKey;
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
