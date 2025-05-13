// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.cave;

import net.labymod.api.client.gui.element.Element;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.math.MathHelper;
import java.io.IOException;
import net.labymod.api.Constants;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import java.util.Objects;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import net.labymod.core.generated.DefaultReferenceStorage;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.core.client.render.schematic.SchematicRenderer;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.screen.widget.widgets.title.MainMenuWidget;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.core.test.TestActivity;

@Link("activity/main-menu.lss")
@AutoActivity
public class BackgroundWorldTestActivity extends TestActivity
{
    private MainMenuWidget mainMenuWidget;
    private DynamicBackgroundController world;
    private SchematicRenderer schematicRenderer;
    private CinematicCamera camera;
    private boolean freeCam;
    private float prevMouseX;
    private float prevMouseY;
    private final float flySpeed = 0.05f;
    private float jumpMovementFactor;
    private double prevX;
    private double prevY;
    private double prevZ;
    private double x;
    private double y;
    private double z;
    private double motionX;
    private double motionY;
    private double motionZ;
    private float moveForward;
    private float moveStrafing;
    private boolean jumping;
    private boolean sprinting;
    private boolean sneaking;
    
    public BackgroundWorldTestActivity() {
        this.freeCam = false;
        this.jumpMovementFactor = 0.02f;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DefaultReferenceStorage references = LabyMod.references();
        final AbstractBootLogoRenderer logo = references.bootLogoController().renderer();
        final DynamicBackgroundController world = references.dynamicBackgroundController();
        final boolean fadeIn = this.labyAPI.config().appearance().fadeOutAnimation().get() == FadeOutAnimationType.FADING;
        (this.mainMenuWidget = new MainMenuWidget(logo, world, fadeIn)).addId("main-menu");
        ((AbstractWidget<MainMenuWidget>)this.document).addChild(this.mainMenuWidget);
        this.world = this.mainMenuWidget.world();
        this.schematicRenderer = world.getSchematicRenderer();
        this.camera = this.schematicRenderer.camera();
    }
    
    @Override
    public boolean shouldRenderBackground() {
        return false;
    }
    
    @Override
    public void render(final ScreenContext context) {
        this.mainMenuWidget.logo().updateProgress(this.getProgress(), true);
        super.render(context);
        final MutableMouse mouse = context.mouse();
        if (this.freeCam) {
            final float mouseDeltaX = mouse.getX() - this.prevMouseX;
            final float mouseDeltaY = mouse.getY() - this.prevMouseY;
            final Location location = this.camera.positionModifier(3);
            final float partialTicks = this.labyAPI.minecraft().getPartialTicks();
            final double x = this.prevX + (this.x - this.prevX) * partialTicks;
            final double y = this.prevY + (this.y - this.prevY) * partialTicks;
            final double z = this.prevZ + (this.z - this.prevZ) * partialTicks;
            location.setPosition(x, y, z);
            if (this.prevMouseX != -1.0f && this.prevMouseY != -1.0f) {
                location.addRotation(mouseDeltaX * 0.5f, mouseDeltaY * 0.5f, 0.0);
            }
            if (location.getPitch() > 90.0) {
                location.setPitch(90.0);
            }
            else if (location.getPitch() < -90.0) {
                location.setPitch(-90.0);
            }
            this.prevMouseX = (float)mouse.getX();
            this.prevMouseY = (float)mouse.getY();
        }
        else {
            this.prevMouseX = -1.0f;
            this.prevMouseY = -1.0f;
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        if (!this.world.isOpeningPlayed() && this.getProgress() >= 1.0f) {
            this.world.playOpening();
        }
        if (!this.world.isOpeningPlayed() || this.camera.getProgress() < 1.0f || ((Document)this.document).getLastInitialTime() + 8000L < TimeUtil.getMillis()) {}
        this.updateMovementInput();
        this.tickMovement();
    }
    
    private void updateMovementInput() {
        final Minecraft minecraft = this.labyAPI.minecraft();
        this.moveForward = 0.0f;
        this.moveStrafing = 0.0f;
        if (minecraft.isKeyPressed(Key.W)) {
            ++this.moveForward;
        }
        if (minecraft.isKeyPressed(Key.S)) {
            --this.moveForward;
        }
        if (minecraft.isKeyPressed(Key.A)) {
            ++this.moveStrafing;
        }
        if (minecraft.isKeyPressed(Key.D)) {
            --this.moveStrafing;
        }
        this.jumping = minecraft.isKeyPressed(Key.SPACE);
        this.sprinting = minecraft.options().sprintInput().isActuallyDown();
        this.sneaking = minecraft.options().sneakInput().isActuallyDown();
        if (!this.freeCam && (this.moveForward != 0.0f || this.moveStrafing != 0.0f)) {
            this.updateFreeCam(true, false);
        }
    }
    
    private void tickMovement() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        if (Math.abs(this.motionX) < 0.003) {
            this.motionX = 0.0;
        }
        if (Math.abs(this.motionY) < 0.003) {
            this.motionY = 0.0;
        }
        if (Math.abs(this.motionZ) < 0.003) {
            this.motionZ = 0.0;
        }
        this.moveStrafing *= 0.98f;
        this.travelFlying(this.moveForward *= 0.98f, 0.0f, this.moveStrafing);
    }
    
    private void travelFlying(final float forward, final float vertical, final float strafe) {
        if (this.sneaking) {
            this.moveStrafing = strafe / 0.3f;
            this.moveForward = forward / 0.3f;
            final double motionY = this.motionY;
            Objects.requireNonNull(this);
            this.motionY = motionY - 0.05f * 3.0f;
        }
        if (this.jumping) {
            final double motionY2 = this.motionY;
            Objects.requireNonNull(this);
            this.motionY = motionY2 + 0.05f * 3.0f;
        }
        final double prevMotionY = this.motionY;
        final float prevJumpMovementFactor = this.jumpMovementFactor;
        Objects.requireNonNull(this);
        this.jumpMovementFactor = 0.05f * (this.sprinting ? 2 : 1);
        this.travel(forward, vertical, -strafe);
        this.motionY = prevMotionY * 0.6;
        this.jumpMovementFactor = prevJumpMovementFactor;
    }
    
    private void travel(double forward, double vertical, double strafe) {
        double distance = strafe * strafe + vertical * vertical + forward * forward;
        if (distance >= 9.999999747378752E-5) {
            distance = Math.sqrt(distance);
            if (distance < 1.0) {
                distance = 1.0;
            }
            distance = this.jumpMovementFactor / distance;
            strafe *= distance;
            vertical *= distance;
            forward *= distance;
            final Location location = this.camera.positionModifier(3);
            final double fixedYaw = this.camera.location().getYaw();
            final double yaw = -location.getYaw() - fixedYaw;
            final double yawRadians = Math.toRadians(-yaw);
            final double sin = Math.sin(yawRadians);
            final double cos = Math.cos(yawRadians);
            this.motionX += strafe * cos + forward * sin;
            this.motionY += vertical;
            this.motionZ += -forward * cos + strafe * sin;
        }
        this.x += this.motionX;
        this.y -= this.motionY;
        this.z += this.motionZ;
        this.motionX *= 0.9100000262260437;
        this.motionY *= 0.9800000190734863;
        this.motionZ *= 0.9100000262260437;
    }
    
    private void updateFreeCam(final boolean enabled, final boolean showUI) {
        ((AbstractWidget<Element>)this.mainMenuWidget).getChild("ui").setVisible(showUI);
        this.world.setWind(!enabled);
        if (enabled) {
            this.labyAPI.minecraft().mouseHandler().grabMouseNative();
            final Location location = this.camera.location();
            this.camera.moveTo(0L, CubicBezier.LINEAR, new Location(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), 0.0));
            final Location wind = this.camera.positionModifier(0);
            wind.setPosition(0.0, 0.0, 0.0);
            wind.setRotation(0.0, 0.0, 0.0);
        }
        else {
            this.labyAPI.minecraft().mouseHandler().ungrabMouseNative();
        }
        this.freeCam = enabled;
        this.prevMouseX = -1.0f;
        this.prevMouseY = -1.0f;
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.ESCAPE || key == Key.T) {
            this.updateFreeCam(!this.freeCam, this.freeCam && key != Key.T);
            return true;
        }
        if (key == Key.O) {
            if (this.freeCam) {
                this.updateFreeCam(false, true);
            }
            this.resetFreeCamPosition();
            final DefaultReferenceStorage references = LabyMod.references();
            final AbstractBootLogoRenderer logo = references.bootLogoController().renderer();
            logo.initialize();
            this.world.reset();
            this.labyAPI.minecraft().minecraftWindow().displayScreen(new BackgroundWorldTestActivity());
            return true;
        }
        if (KeyHandler.isControlDown() && key == Key.S) {
            try {
                this.schematicRenderer.schematic().saveTo(Constants.Files.LABYMOD_DIRECTORY.resolve("cave.schem"));
            }
            catch (final IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (key == Key.R) {
            this.world.getSchematicRenderer().setDirty();
            return true;
        }
        if (key == Key.H) {
            this.resetFreeCamPosition();
            return true;
        }
        return super.keyPressed(key, type);
    }
    
    private void resetFreeCamPosition() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        final Location location = this.camera.positionModifier(3);
        location.setRotation(0.0, 0.0, 0.0);
        location.setPosition(0.0, 0.0, 0.0);
    }
    
    private float getProgress() {
        final long timePassed = TimeUtil.getMillis() - this.mainMenuWidget.getLastInitialTime();
        return MathHelper.clamp(timePassed / 2000.0f, 0.0f, 1.0f);
    }
}
