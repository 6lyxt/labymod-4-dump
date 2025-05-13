// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.spray;

import net.labymod.api.client.component.builder.StyleableBuilder;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.util.SprayCooldownTracker;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.main.user.shop.spray.SprayRegistry;
import net.labymod.core.main.LabyMod;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.util.math.Direction;
import net.labymod.api.client.world.block.RenderShape;
import net.labymod.api.client.world.phys.hit.BlockHitResult;
import net.labymod.api.client.world.phys.hit.HitResult;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.core.main.user.shop.spray.model.Spray;
import net.labymod.api.event.client.input.KeyEvent;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.core.client.gui.screen.widget.widgets.spray.SpraySegmentWidget;
import net.labymod.api.util.CharSequences;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.TimeUnit;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.activity.util.PageNavigator;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.screen.widget.widgets.spray.SprayWheelWidget;
import net.labymod.api.Constants;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.component.format.Style;
import java.util.function.Function;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity;

@AutoActivity
@Link("activity/overlay/spray-wheel.lss")
public class SprayWheelOverlay extends AbstractWheelInteractionOverlayActivity
{
    private static final Function<String, String> TRANSLATION_KEY_FACTORY;
    private static final String SELECT;
    private static final String NEXT_SPRAY;
    private static final String NO_SPRAYS;
    private static final String ATTEMPT_TO_SPRAY_ON_ENTITY;
    private static final String NOT_CONNECTED;
    private static final String FULL_BLOCK;
    private static final String COOLDOWN;
    private static final Style DEFAULT_STYLE;
    private final ClientWorld clientLevel;
    private boolean sprayed;
    
    public SprayWheelOverlay() {
        this.clientLevel = Laby.references().clientWorld();
    }
    
    @Override
    public void initialize(final Parent parent) {
        this.refreshUserData();
        super.initialize(parent);
    }
    
    @Override
    protected void openInteractionOverlay() {
        super.openInteractionOverlay();
        if (!this.isSearchActivityOpen()) {
            Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_SPRAY_CAN_SHAKE, 1.0f, 1.0f);
        }
    }
    
    private void refreshUserData() {
        final SprayWheelWidget.Storage storage = SprayWheelWidget.Storage.INSTANCE;
        storage.refreshUserData();
        final PageNavigator pageNavigator = this.pageNavigator();
        pageNavigator.setMinimumPage(0);
        final int maxPages = MathHelper.ceil(storage.getSprays().size() / (float)this.getSegmentCount()) - 1;
        pageNavigator.setMaximumPage(maxPages);
    }
    
    @Override
    protected void renderInteractionOverlay(final Stack stack, final MutableMouse mouse, final float partialTicks) {
    }
    
    @Override
    public void tick() {
        super.tick();
        final boolean interactionOpen = this.isInteractionOpen();
        if (interactionOpen) {
            this.titleWidget().setComponent(this.createTitleComponent());
        }
        if (interactionOpen && this.canSpray() && this.sprayed) {
            this.refresh(true);
            this.sprayed = false;
        }
    }
    
    @Override
    protected Component createTitleComponent() {
        if (!this.hasEntries()) {
            return Component.translatable(SprayWheelOverlay.NO_SPRAYS, new Component[0]);
        }
        if (this.canSpray()) {
            return Component.translatable(SprayWheelOverlay.SELECT, new Component[0]);
        }
        return this.createNextSprayComponent();
    }
    
    private Component createNextSprayComponent() {
        return Component.translatable(SprayWheelOverlay.NEXT_SPRAY, Component.text(TimeUnit.parseToString(this.sprayCooldownTracker().getClientDuration()), NamedTextColor.YELLOW));
    }
    
    @Override
    protected boolean hasEntries() {
        return !SprayWheelWidget.Storage.INSTANCE.getSprays().isEmpty();
    }
    
    @Override
    protected WheelWidget createWheelWidget() {
        final SprayWheelWidget wheel = new SprayWheelWidget(() -> this.pageNavigator().getCurrentPage(), () -> rec$.getSegmentCount());
        wheel.querySupplier(() -> {
            final CharSequence searchText = this.getSearchText();
            if (CharSequences.isEmpty(searchText)) {
                return null;
            }
            else {
                return searchText;
            }
        });
        wheel.segmentSupplier((index, wheelIndex, spray) -> {
            final boolean canUseSpray = this.canSpray();
            if (spray == null) {
                final WheelWidget.Segment segment = new WheelWidget.Segment();
                segment.setSelectable(false);
                return segment;
            }
            else {
                final SpraySegmentWidget segment2 = new SpraySegmentWidget(spray, canUseSpray);
                segment2.addId("spray-wrapper");
                segment2.setSelectable(true);
                return segment2;
            }
        });
        return wheel;
    }
    
    @Override
    protected Key getKeyToOpen() {
        return this.labyAPI.config().hotkeys().sprayWheelKey().get();
    }
    
    @Override
    protected void onInitializeMappedKeys(final Object2IntMap<Key> mappedKeys) {
        mappedKeys.put((Object)Key.NUM1, 0);
        mappedKeys.put((Object)Key.NUM2, 1);
        mappedKeys.put((Object)Key.NUM3, 2);
        mappedKeys.put((Object)Key.NUM4, 3);
        mappedKeys.put((Object)Key.NUM5, 4);
        mappedKeys.put((Object)Key.NUM6, 5);
        mappedKeys.put((Object)Key.NUMPAD1, 0);
        mappedKeys.put((Object)Key.NUMPAD2, 1);
        mappedKeys.put((Object)Key.NUMPAD3, 2);
        mappedKeys.put((Object)Key.NUMPAD4, 3);
        mappedKeys.put((Object)Key.NUMPAD5, 4);
        mappedKeys.put((Object)Key.NUMPAD6, 5);
    }
    
    @Override
    protected void onKey(final Key key, final KeyEvent.State state) {
    }
    
    @Override
    protected boolean shouldOpenInteractionMenu() {
        return this.labyAPI.config().ingame().spray().enabled().get();
    }
    
    @Override
    protected void closeInteractionOverlay() {
        this.spray(null, false);
        super.closeInteractionOverlay();
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.isSearchActivityOpen()) {
            this.spray(null, true);
            this.closeScreen();
        }
        return super.mouseClicked(mouse, mouseButton);
    }
    
    private void spray(final Spray forcedSpray, final boolean closeMenu) {
        Spray spray = forcedSpray;
        if (spray == null) {
            spray = this.findSelectedSpray();
        }
        this.spray(spray);
        if (closeMenu) {
            this.closeInteraction();
        }
    }
    
    @Nullable
    private Spray findSelectedSpray() {
        for (final AbstractWidget<?> child : this.wheelWidget().getChildren()) {
            if (child instanceof final SpraySegmentWidget segmentWidget) {
                if (segmentWidget.isSelectable() && segmentWidget.isSegmentSelected()) {
                    return segmentWidget.getSpray();
                }
                continue;
            }
        }
        return null;
    }
    
    private void spray(final Spray spray) {
        if (spray == null) {
            return;
        }
        if (!this.canSpray()) {
            return;
        }
        final HitResult result = Laby.labyAPI().minecraft().getHitResult();
        if (result == null) {
            SprayWheelOverlay.LOGGER.error("result returned null, this shouldn't happen!", new Object[0]);
            return;
        }
        final HitResult.HitType type = result.type();
        if (type == HitResult.HitType.ENTITY) {
            this.displayClientMessage(Component.translatable(SprayWheelOverlay.ATTEMPT_TO_SPRAY_ON_ENTITY, SprayWheelOverlay.DEFAULT_STYLE));
            return;
        }
        if (type == HitResult.HitType.MISS) {
            this.displayClientMessage(Component.translatable(SprayWheelOverlay.FULL_BLOCK, SprayWheelOverlay.DEFAULT_STYLE));
            return;
        }
        final BlockHitResult blockHitResult = (BlockHitResult)result;
        final BlockState blockState = this.clientLevel.getBlockState(blockHitResult.getBlockPosition());
        boolean invisible = false;
        if (blockState.renderShape() == RenderShape.INVISIBLE) {
            invisible = true;
        }
        else {
            final AxisAlignedBoundingBox bounds = blockState.bounds();
            if (!MathHelper.isBox(bounds)) {
                invisible = true;
            }
        }
        if (invisible) {
            this.displayClientMessage(Component.translatable(SprayWheelOverlay.FULL_BLOCK, SprayWheelOverlay.DEFAULT_STYLE));
            return;
        }
        final MinecraftCamera camera = this.labyAPI.minecraft().getCamera();
        final Direction blockDirection = blockHitResult.getBlockDirection();
        final FloatVector3 location = result.location();
        float rotation;
        if (blockDirection == Direction.UP) {
            rotation = 180.0f - camera.getYaw();
        }
        else if (blockDirection == Direction.DOWN) {
            rotation = camera.getYaw();
        }
        else {
            rotation = this.calculateDegrees(camera.renderPosition(), location, blockDirection);
        }
        this.performSpray(spray, location, blockDirection, rotation);
    }
    
    private void performSpray(final Spray spray, final FloatVector3 location, final Direction blockDirection, final float rotation) {
        final SprayRegistry sprayRegistry = LabyMod.references().sprayRegistry();
        final SprayRegistry.SprayState state = sprayRegistry.sprayClient((short)spray.getId(), location.getX(), location.getY(), location.getZ(), blockDirection, rotation);
        if (state == SprayRegistry.SprayState.SUCCESS) {
            this.spray();
        }
        else if (state == SprayRegistry.SprayState.SPRAY_COOLDOWN) {
            this.displayClientMessage(Component.translatable(SprayWheelOverlay.COOLDOWN, SprayWheelOverlay.DEFAULT_STYLE).argument(Component.text(this.sprayCooldownTracker().getClientDuration() / 1000L, NamedTextColor.YELLOW)));
        }
        else {
            this.displayClientMessage(Component.translatable(SprayWheelOverlay.NOT_CONNECTED, SprayWheelOverlay.DEFAULT_STYLE));
        }
    }
    
    private void displayClientMessage(final Component component) {
        this.labyAPI.minecraft().chatExecutor().displayClientMessage(component);
    }
    
    private float calculateDegrees(final DoubleVector3 source, final FloatVector3 destination, final Direction direction) {
        float degrees = 0.0f;
        if (direction == Direction.WEST) {
            degrees = (float)Math.toDegrees(Math.atan2(destination.getZ() - source.getZ(), destination.getX() - source.getX()));
            degrees = -degrees;
        }
        else if (direction == Direction.EAST) {
            degrees = (float)Math.toDegrees(Math.atan2(destination.getZ() - source.getZ(), destination.getX() - source.getX()));
            degrees = 180.0f - degrees;
        }
        else if (direction == Direction.NORTH) {
            degrees = (float)Math.toDegrees(-Math.atan2(destination.getX() - source.getX(), destination.getZ() - source.getZ()));
            degrees = -degrees;
        }
        else if (direction == Direction.SOUTH) {
            degrees = (float)Math.toDegrees(-Math.atan2(destination.getX() - source.getX(), destination.getZ() - source.getZ()));
            degrees = 180.0f - degrees;
        }
        if (degrees >= 180.0f) {
            degrees -= 360.0f;
        }
        if (degrees <= -180.0f) {
            degrees += 360.0f;
        }
        if (degrees >= -90.0f && degrees <= 90.0f) {
            final boolean negative = degrees < 0.0f;
            final double value = Math.abs(degrees) / 90.0;
            final double curve = new CubicBezier(0.9, 0.2, 0.9, 0.2).curve(value);
            degrees = (float)(curve * 90.0) * (negative ? -1 : 1);
        }
        return degrees;
    }
    
    private SprayCooldownTracker sprayCooldownTracker() {
        final DefaultGameUser user = (DefaultGameUser)Laby.references().gameUserService().clientGameUser();
        return user.sprayCooldownTracker();
    }
    
    private void spray() {
        this.sprayCooldownTracker().sprayClient();
        this.sprayed = true;
    }
    
    private boolean canSpray() {
        return this.sprayCooldownTracker().canSprayClient();
    }
    
    static {
        TRANSLATION_KEY_FACTORY = (s -> "labymod.activity.sprayWheel." + s);
        SELECT = SprayWheelOverlay.TRANSLATION_KEY_FACTORY.apply("select");
        NEXT_SPRAY = SprayWheelOverlay.TRANSLATION_KEY_FACTORY.apply("nextSpray");
        NO_SPRAYS = SprayWheelOverlay.TRANSLATION_KEY_FACTORY.apply("noSprays");
        ATTEMPT_TO_SPRAY_ON_ENTITY = SprayWheelOverlay.TRANSLATION_KEY_FACTORY.apply("attemptToSprayOnEntity");
        NOT_CONNECTED = SprayWheelOverlay.TRANSLATION_KEY_FACTORY.apply("notConnected");
        FULL_BLOCK = SprayWheelOverlay.TRANSLATION_KEY_FACTORY.apply("fullBlock");
        COOLDOWN = SprayWheelOverlay.TRANSLATION_KEY_FACTORY.apply("cooldown");
        DEFAULT_STYLE = ((StyleableBuilder<T, Style.Builder>)Style.builder()).color(NamedTextColor.RED).build();
    }
}
