// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.debug;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import com.google.gson.JsonObject;
import java.io.Reader;
import java.nio.file.OpenOption;
import java.nio.charset.StandardCharsets;
import net.labymod.api.util.GsonUtil;
import com.google.gson.JsonElement;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.io.File;
import net.labymod.api.notification.Notification;
import java.io.IOException;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.Component;
import net.labymod.api.platform.launcher.MinecraftLauncher;
import net.labymod.api.platform.launcher.MinecraftLauncherFactory;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.platform.launcher.LauncherVendorType;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.widget.action.SliderInteraction;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@AutoActivity
@Link("activity/debug/memory-upgrade.lss")
public class MemoryUpgradeActivity extends SimpleActivity implements SliderInteraction
{
    private static final String TRANSLATION_UI_BUTTON_KEY = "labymod.ui.button.";
    private static final String TRANSLATION_ACTIVITY_KEY = "labymod.activity.memoryUpgrade.";
    private static final String PROFILES_KEY = "profiles";
    private static final String JAVA_ARGS_KEY = "javaArgs";
    private static final int SLIDER_STEPS = 512;
    private static final long MIN_MEMORY = 2048L;
    private static final long MAX_MEMORY = 5120L;
    private ComponentWidget memorySliderLabelWidget;
    private float value;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final MinecraftLauncherFactory factory = Laby.references().minecraftLauncherFactory();
        final MinecraftLauncher launcher = factory.create(LauncherVendorType.MOJANG);
        final DivWidget containerWidget = new DivWidget();
        containerWidget.addId("container");
        final ComponentWidget titleWidget = ComponentWidget.i18n("labymod.activity.memoryUpgrade.title");
        titleWidget.addId("title");
        ((AbstractWidget<ComponentWidget>)containerWidget).addChild(titleWidget);
        final Runtime runtime = Runtime.getRuntime();
        final ComponentWidget descriptionWidget = ComponentWidget.i18n("labymod.activity.memoryUpgrade.description");
        descriptionWidget.addId("description");
        ((AbstractWidget<ComponentWidget>)containerWidget).addChild(descriptionWidget);
        final HorizontalListWidget horizontalListWidget = new HorizontalListWidget();
        ((AbstractWidget<Widget>)horizontalListWidget).addId("memory-slider-container");
        ((AbstractWidget<HorizontalListWidget>)containerWidget).addChild(horizontalListWidget);
        final SliderWidget memorySliderWidget = new SliderWidget(512.0f, this);
        memorySliderWidget.range(2048.0f, 5120.0f);
        (this.memorySliderLabelWidget = ComponentWidget.text("")).addId("memory-slider-label");
        memorySliderWidget.setValue((double)MathHelper.clamp(runtime.maxMemory() / 1024L / 1024L, 2048L, 5120L), true);
        memorySliderWidget.addId("memory-slider");
        horizontalListWidget.addEntry(memorySliderWidget);
        horizontalListWidget.addEntry(this.memorySliderLabelWidget);
        final HorizontalListWidget controlsContainerWidget = new HorizontalListWidget();
        ((AbstractWidget<Widget>)controlsContainerWidget).addId("controls-container");
        final ButtonWidget cancelButtonWidget = ButtonWidget.i18n("labymod.ui.button.cancel");
        cancelButtonWidget.setPressable(this::displayPreviousScreen);
        controlsContainerWidget.addEntry(cancelButtonWidget);
        final ButtonWidget quitGameButtonWidget = ButtonWidget.i18n("labymod.ui.button.quit");
        quitGameButtonWidget.setPressable(() -> this.killLauncher(launcher));
        controlsContainerWidget.addEntry(quitGameButtonWidget);
        ((AbstractWidget<HorizontalListWidget>)containerWidget).addChild(controlsContainerWidget);
        ((AbstractWidget<DivWidget>)super.document()).addChild(containerWidget);
    }
    
    @Override
    public void updateValue(final float value) {
        this.value = value;
        if (this.memorySliderLabelWidget != null) {
            this.memorySliderLabelWidget.setText("= " + this.value / 1024.0f + "GB");
        }
    }
    
    private void killLauncher(final MinecraftLauncher launcher) {
        try {
            if (!launcher.kill()) {
                this.onLauncherProfileUpdateFailed("labymod.activity.memoryUpgrade.launcherCouldNotClosed", true, new Component[0]);
                return;
            }
            if (this.updateLauncherProfile(launcher.getDirectory(), (long)this.value)) {
                Laby.labyAPI().minecraft().shutdownGame();
            }
            else {
                String profile = Laby.labyAPI().labyModLoader().getProfile();
                profile = ((profile == null) ? "unknown" : profile);
                this.onLauncherProfileUpdateFailed("labymod.activity.memoryUpgrade.profileNotFound", true, Component.text(profile, NamedTextColor.YELLOW));
            }
        }
        catch (final IOException exception) {
            this.onLauncherProfileUpdateFailed(exception.getMessage(), false, new Component[0]);
        }
    }
    
    private void onLauncherProfileUpdateFailed(final String errorMessage, final boolean translatable, final Component... args) {
        final Notification notification = Notification.builder().title(Component.translatable("labymod.notification.outOfMemoryWarning.title", new Component[0])).text((Component)(translatable ? Component.translatable(errorMessage, args) : Component.text(errorMessage))).type(Notification.Type.SYSTEM).build();
        Laby.references().notificationController().push(notification);
    }
    
    private boolean updateLauncherProfile(final File directory, final long mb) {
        return this.updateLauncherProfile(directory.toPath(), mb);
    }
    
    private boolean updateLauncherProfile(final Path directory, final long mb) {
        final Path launcherProfilesPath = directory.resolve("launcher_profiles.json");
        if (Files.notExists(launcherProfilesPath, new LinkOption[0])) {
            return false;
        }
        try (final Reader reader = Files.newBufferedReader(launcherProfilesPath)) {
            final JsonElement profilesElement = (JsonElement)GsonUtil.DEFAULT_GSON.fromJson(reader, (Class)JsonElement.class);
            if (this.updateLauncherProfile(profilesElement, mb)) {
                Files.write(launcherProfilesPath, GsonUtil.DEFAULT_GSON.toJson(profilesElement).getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
                final boolean b = true;
                if (reader != null) {
                    reader.close();
                }
                return b;
            }
        }
        catch (final IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }
    
    private boolean updateLauncherProfile(final JsonElement element, final long mb) {
        if (!element.isJsonObject()) {
            return false;
        }
        final JsonObject object = element.getAsJsonObject();
        if (!object.has("profiles")) {
            return false;
        }
        final JsonElement profilesElement = object.get("profiles");
        if (!profilesElement.isJsonObject()) {
            return false;
        }
        final JsonObject profilesObject = profilesElement.getAsJsonObject();
        final String currentProfile = Laby.labyAPI().labyModLoader().getProfile();
        if (!profilesObject.has(currentProfile)) {
            return false;
        }
        final JsonElement profileElement = profilesObject.get(currentProfile);
        if (!profileElement.isJsonObject()) {
            return false;
        }
        final JsonObject profileObject = profileElement.getAsJsonObject();
        if (profileObject.has("javaArgs")) {
            final JsonElement javaArgsElement = profileObject.get("javaArgs");
            if (!javaArgsElement.isJsonPrimitive()) {
                return false;
            }
            String arguments = javaArgsElement.getAsString();
            arguments = arguments.replaceAll("-[x|X][m|M][x|X][0-9]+\\w+", "-Xmx" + mb);
            profileObject.addProperty("javaArgs", arguments);
        }
        else {
            profileObject.addProperty("javaArgs", "-Xmx" + mb);
        }
        return true;
    }
}
