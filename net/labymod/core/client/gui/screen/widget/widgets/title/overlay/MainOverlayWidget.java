// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.title.overlay;

import java.lang.invoke.CallSite;
import java.lang.reflect.UndeclaredThrowableException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.StringConcatFactory;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextDecoration;
import java.util.UUID;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.LabyConnect;
import java.util.Locale;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.core.labyconnect.session.DefaultLabyConnectSession;
import net.labymod.api.labyconnect.protocol.LabyConnectState;
import net.labymod.core.main.announcement.model.SideBarAnnouncement;
import net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig;
import net.labymod.api.notification.Notification;
import net.labymod.core.configuration.labymod.main.laby.other.DefaultAdvancedConfig;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.BuildData;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.notification.announcement.Announcement;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.core.main.announcement.DefaultAnnouncementService;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class MainOverlayWidget extends AbstractWidget<Widget>
{
    public static final boolean IS_MODDED_INDICATOR_VERSION;
    public static final Component COPYRIGHT_TEXT;
    private final DefaultAnnouncementService announcementService;
    private ComponentWidget copyrightWidget;
    private ComponentWidget labyModVersionWidget;
    private int devToolsClicks;
    
    public MainOverlayWidget() {
        this.devToolsClicks = 0;
        (this.announcementService = (DefaultAnnouncementService)Laby.references().announcementService()).setResolveListener(() -> ThreadSafe.executeOnRenderThread(this::reInitialize));
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final VerticalListWidget<Widget> leftList = new VerticalListWidget<Widget>();
        leftList.addId("left-side-announcements", "side-announcements");
        ((AbstractWidget<VerticalListWidget<Widget>>)this).addChild(leftList);
        final VerticalListWidget<Widget> rightList = new VerticalListWidget<Widget>();
        rightList.addId("right-side-announcements", "side-announcements");
        ((AbstractWidget<VerticalListWidget<Widget>>)this).addChild(rightList);
        this.initializeSideAnnouncements(leftList, rightList);
        final List<Announcement> announcements = this.announcementService.getAnnouncements();
        if (!announcements.isEmpty()) {
            final VerticalListWidget<Widget> centerList = new VerticalListWidget<Widget>();
            centerList.addId("center-announcements");
            for (final Announcement announcement : announcements) {
                centerList.addChild(ComponentWidget.component(announcement.getText()).addId("announcement"));
            }
            ((AbstractWidget<VerticalListWidget<Widget>>)this).addChild(centerList);
        }
        this.initializeDashboardHead(rightList);
        this.initializeFooter();
    }
    
    private void initializeFooter() {
        final HorizontalListWidget footer = new HorizontalListWidget();
        ((AbstractWidget<Widget>)footer).addId("footer");
        final VerticalListWidget<ComponentWidget> versionList = new VerticalListWidget<ComponentWidget>();
        versionList.addId("version-list");
        (this.labyModVersionWidget = ComponentWidget.text("LabyMod " + BuildData.getVersion())).addId("version");
        versionList.addChild(this.labyModVersionWidget);
        final Minecraft minecraft = this.labyAPI.minecraft();
        final String version = "Minecraft " + minecraft.getVersion() + (minecraft.isDemo() ? " Demo" : ("release".equalsIgnoreCase(minecraft.getVersionType()) ? "" : /* invokedynamic(!) */ProcyonInvokeDynamicHelper_5.invoke(minecraft.getVersionType()))) + (MainOverlayWidget.IS_MODDED_INDICATOR_VERSION ? minecraft.getTranslation("menu.modded") : "");
        final ComponentWidget minecraftVersion = ComponentWidget.text(version);
        minecraftVersion.addId("build");
        versionList.addChild(minecraftVersion);
        footer.addEntry(versionList);
        final DivWidget copyrightWrapper = new DivWidget();
        copyrightWrapper.addId("copyright-wrapper");
        (this.copyrightWidget = ComponentWidget.component(MainOverlayWidget.COPYRIGHT_TEXT)).addId("copyright");
        this.copyrightWidget.setPressable(() -> this.labyAPI.minecraft().minecraftWindow().displayScreen(NamedScreen.CREDITS));
        ((AbstractWidget<ComponentWidget>)copyrightWrapper).addChild(this.copyrightWidget);
        footer.addEntry(copyrightWrapper);
        ((AbstractWidget<HorizontalListWidget>)this).addChild(footer);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        if (mouseButton != MouseButton.RIGHT || !this.labyModVersionWidget.bounds().isInRectangle(mouse)) {
            return false;
        }
        ++this.devToolsClicks;
        if (this.devToolsClicks < 5) {
            return false;
        }
        final AdvancedConfig advanced = LabyConfigProvider.INSTANCE.get().other().advanced();
        if (advanced instanceof final DefaultAdvancedConfig defaultAdvanced) {
            this.devToolsClicks = 0;
            defaultAdvanced.devTools().set(!defaultAdvanced.devTools().get());
            final Notification notification = Notification.builder().title(Component.text("DevTools")).text(Component.text("DevTools " + (((boolean)defaultAdvanced.devTools().get()) ? "enabled" : "disabled"))).build();
            Laby.references().notificationController().push(notification);
            return true;
        }
        return false;
    }
    
    private void initializeSideAnnouncements(final VerticalListWidget<Widget> leftList, final VerticalListWidget<Widget> rightList) {
        final boolean displaySocial = this.labyAPI.config().appearance().titleScreen().socialMediaLinks().get();
        for (final SideBarAnnouncement announcement : this.announcementService.getSideBarAnnouncements()) {
            if (!displaySocial && announcement.isSocial()) {
                continue;
            }
            final VerticalListWidget<Widget> list = announcement.getSide().isRight() ? rightList : leftList;
            list.addChild(new SideAnnouncementWidget(announcement));
        }
    }
    
    private void initializeDashboardHead(final VerticalListWidget<Widget> list) {
        final LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        final LabyConnectSession labyConnectSession = labyConnect.getSession();
        if (labyConnect.state() == LabyConnectState.PLAY && labyConnectSession != null) {
            final DefaultLabyConnectSession session = (DefaultLabyConnectSession)labyConnectSession;
            final UUID uniqueId = this.labyAPI.minecraft().sessionAccessor().getSession().getUniqueId();
            final IconWidget headWidget = new IconWidget(Icon.head(uniqueId));
            headWidget.addId("dashboard-widget");
            headWidget.setPressable(() -> {
                final Minecraft minecraft = this.labyAPI.minecraft();
                session.requestDashboardPin(pin -> minecraft.chatExecutor().openUrl(String.format(Locale.ROOT, "https://www.labymod.net/key/?id=%s&pin=%s", uniqueId, pin)));
                return;
            });
            final DivWidget dashboardWrapper = new DivWidget();
            dashboardWrapper.addId("dashboard-wrapper");
            ((AbstractWidget<IconWidget>)dashboardWrapper).addChild(headWidget);
            list.addChild(dashboardWrapper);
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        final Style style = MainOverlayWidget.COPYRIGHT_TEXT.style();
        final boolean hovered = this.copyrightWidget.isHovered();
        if (style.hasDecoration(TextDecoration.UNDERLINED) != hovered) {
            MainOverlayWidget.COPYRIGHT_TEXT.style(hovered ? style.decorate(TextDecoration.UNDERLINED) : style.undecorate(TextDecoration.UNDERLINED));
            this.copyrightWidget.updateComponent();
        }
    }
    
    static {
        IS_MODDED_INDICATOR_VERSION = MinecraftVersions.V1_16.orNewer();
        COPYRIGHT_TEXT = Component.text("Copyright Mojang AB. Do not distribute!");
    }
    
    // This helper class was generated by Procyon to approximate the behavior of an
    // 'invokedynamic' instruction that it doesn't know how to interpret.
    private static final class ProcyonInvokeDynamicHelper_5
    {
        private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
        private static MethodHandle handle;
        private static volatile int fence;
        
        private static MethodHandle handle() {
            final MethodHandle handle = ProcyonInvokeDynamicHelper_5.handle;
            if (handle != null)
                return handle;
            return ProcyonInvokeDynamicHelper_5.ensureHandle();
        }
        
        private static MethodHandle ensureHandle() {
            ProcyonInvokeDynamicHelper_5.fence = 0;
            MethodHandle handle = ProcyonInvokeDynamicHelper_5.handle;
            if (handle == null) {
                MethodHandles.Lookup lookup = ProcyonInvokeDynamicHelper_5.LOOKUP;
                try {
                    handle = ((CallSite)StringConcatFactory.makeConcatWithConstants(lookup, "makeConcatWithConstants", MethodType.methodType(String.class, String.class), "/\u0001")).dynamicInvoker();
                }
                catch (Throwable t) {
                    throw new UndeclaredThrowableException(t);
                }
                ProcyonInvokeDynamicHelper_5.fence = 1;
                ProcyonInvokeDynamicHelper_5.handle = handle;
                ProcyonInvokeDynamicHelper_5.fence = 0;
            }
            return handle;
        }
        
        private static String invoke(String p0) {
            try {
                return ProcyonInvokeDynamicHelper_5.handle().invokeExact(p0);
            }
            catch (Throwable t) {
                throw new UndeclaredThrowableException(t);
            }
        }
    }
}
