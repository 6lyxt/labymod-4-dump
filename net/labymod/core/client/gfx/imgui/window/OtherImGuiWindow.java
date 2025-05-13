// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.imgui.window;

import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.component.format.TextColor;
import java.util.Collection;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;
import net.labymod.core.client.screenshot.Screenshot;
import net.labymod.core.main.LabyMod;
import net.labymod.api.notification.Notification;
import java.util.ArrayList;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.chat.Title;
import java.util.List;
import net.labymod.api.client.gfx.shader.exception.ShaderException;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.component.event.ClickEvent;
import java.util.Iterator;
import net.labymod.api.debug.DebugFeature;
import net.labymod.api.debug.DebugRegistry;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.core.test.TestMenuActivity;
import net.labymod.api.client.gfx.imgui.LabyImGui;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.imgui.type.ImGuiBooleanType;
import net.labymod.core.client.gui.screen.theme.DefaultThemeService;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gfx.imgui.ImGuiWindow;

public class OtherImGuiWindow extends ImGuiWindow
{
    private static final Logging LOGGER;
    private final DefaultThemeService defaultThemeService;
    
    public OtherImGuiWindow(@Nullable final ImGuiBooleanType visible) {
        super("Other", visible, 0);
        this.defaultThemeService = (DefaultThemeService)Laby.references().themeService();
    }
    
    @Override
    protected void renderContent() {
        if (LabyImGui.button("Reload LabyMod")) {
            Laby.labyAPI().refresh();
        }
        if (LabyImGui.button("Switch Theme")) {
            this.switchTheme();
        }
        if (LabyImGui.button("Reload theme")) {
            this.defaultThemeService.reload(true);
        }
        if (LabyImGui.button("Open Test Activity")) {
            Laby.labyAPI().minecraft().minecraftWindow().displayScreen(new TestMenuActivity());
        }
        LabyImGui.separator();
        this.tests();
        this.shaders();
        LabyImGui.separator();
        LabyImGui.text("Debug Features");
        for (final DebugFeature debugFeature : DebugRegistry.getDebugFeatures()) {
            LabyImGui.checkbox(debugFeature.getName(), debugFeature.getState());
        }
    }
    
    private void tests() {
        final boolean expanded = LabyImGui.treeNodeEx("Tests", 0);
        if (expanded) {
            if (LabyImGui.button("Display Title")) {
                this.showTitle();
            }
            if (LabyImGui.button("Display Title with Translation")) {
                this.showTitleWithTranslation();
            }
            if (LabyImGui.button("Push Example Notifications")) {
                this.pushExampleNotification();
            }
            if (LabyImGui.button("Display Action Bar")) {
                this.showActionBar();
            }
            if (LabyImGui.button("Open URL")) {
                Laby.references().chatExecutor().performAction(ClickEvent.openUrl("https://www.labymod.net/"));
            }
            LabyImGui.popTree();
        }
    }
    
    private void showActionBar() {
        Laby.labyAPI().minecraft().chatExecutor().displayActionBar("LabyMod 4 is awesome!");
    }
    
    private void shaders() {
        final boolean expanded = LabyImGui.treeNodeEx("Shaders", 0);
        if (expanded) {
            final List<ShaderProgram> shaders = Laby.references().shaderProgramManager().getShaders();
            for (final ShaderProgram shaderProgram : shaders) {
                LabyImGui.pushId(shaderProgram.toString());
                LabyImGui.beginGroup();
                if (LabyImGui.button("Reload")) {
                    try {
                        shaderProgram.recompile();
                        OtherImGuiWindow.LOGGER.info("Reloaded shader program {}", shaderProgram);
                    }
                    catch (final ShaderException exception) {
                        OtherImGuiWindow.LOGGER.error("Could not reload shader program {}", shaderProgram, exception);
                    }
                }
                LabyImGui.sameLine(0.0f, 5.0f);
                LabyImGui.text(shaderProgram.toString());
                LabyImGui.endGroup();
                LabyImGui.popId();
            }
            LabyImGui.popTree();
        }
    }
    
    private void showTitle() {
        if (!Laby.labyAPI().minecraft().isIngame()) {
            OtherImGuiWindow.LOGGER.warn("You are not in game!", new Object[0]);
            return;
        }
        final Title build = Title.builder().title(Component.text("LabyMod 4", NamedTextColor.BLUE)).subTitle(Component.text("Freezes, lags, and poor FPS are a thing of the past.", NamedTextColor.YELLOW)).stay(60).fadeIn(20).fadeOut(20).build();
        build.show();
    }
    
    private void showTitleWithTranslation() {
        if (!Laby.labyAPI().minecraft().isIngame()) {
            OtherImGuiWindow.LOGGER.warn("You are not in game!", new Object[0]);
            return;
        }
        final Title build = Title.builder().title(Component.translatable("labymod.notification.outOfMemoryWarning.title", NamedTextColor.RED)).subTitle(Component.translatable("labymod.notification.outOfMemoryWarning.description", NamedTextColor.YELLOW).arguments(Component.text("A"), Component.text("B"))).stay(60).fadeIn(20).fadeOut(20).build();
        build.show();
    }
    
    private void pushExampleNotification() {
        final TextColor color = NamedTextColor.WHITE;
        final Icon icon = Icon.head(Laby.labyAPI().getUniqueId());
        final TextComponent title = Component.text(Laby.labyAPI().getName()).color(color);
        final TextComponent text = Component.text((Math.random() < 0.5) ? "Hey, this is a test! What do you think? Does it look good?" : ((Math.random() < 0.5) ? "Hey, this is a test! What do you think?" : "Hey, this is a very long message that should display over multiple lines! What do you think? Does it look good? It should go over 5 lines until it cuts off. This is the last message you should be able to read because we are reaching the maximum length!")).color(color);
        final List<Notification.NotificationButton> buttons = new ArrayList<Notification.NotificationButton>();
        for (int i = 0; i < 4; ++i) {
            if (Math.random() < 0.5) {
                final String buttonText = (i == 0) ? "Add" : ((i == 1) ? "Remove" : ((i == 2) ? "Copy" : "Information"));
                buttons.add((Math.random() < 0.2) ? Notification.NotificationButton.primary(Component.text(buttonText), () -> {}) : Notification.NotificationButton.of(Component.text(buttonText), () -> {}));
            }
        }
        final List<Screenshot> screenshots = LabyMod.references().screenshotBrowser().getScreenshots();
        final Screenshot latestScreenshot = screenshots.isEmpty() ? null : screenshots.getFirst();
        if (latestScreenshot != null) {
            latestScreenshot.updateQuality(Screenshot.QualityType.HIGH);
        }
        final Icon thumbnail = (latestScreenshot != null) ? latestScreenshot.getIcon() : null;
        Laby.references().notificationController().push(Notification.builder().title(title).text(text).icon((Math.random() < 0.5) ? icon : null).secondaryIcon((Math.random() < 0.5) ? icon : null).thumbnail((Math.random() < 0.5) ? thumbnail : null).onClick(notification -> {
            if (latestScreenshot != null) {
                ScreenshotBrowserActivity.openScreenshot(latestScreenshot.getPath());
            }
        }).addButtons(buttons).duration((long)(1000.0 * (5.0 + 50.0 * Math.random()))).type(Notification.Type.SYSTEM).build());
    }
    
    private void switchTheme() {
        final Theme currentTheme = this.defaultThemeService.currentTheme();
        final String name = currentTheme.getId().equals("vanilla") ? "fancy" : "vanilla";
        this.defaultThemeService.reload(name, true);
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
