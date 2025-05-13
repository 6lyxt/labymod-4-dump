// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity;

import net.labymod.api.client.gui.screen.ScreenInstance;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.Map;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.util.WidgetDataCollector;
import java.util.Locale;
import net.labymod.api.util.TextFormat;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import java.util.function.Function;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.element.CompositeElement;

public abstract class ElementActivity<D extends CompositeElement<?>> extends LabyScreen
{
    protected static final Logging LOGGER;
    private static final ModifyReason ACTIVITY_RESIZE;
    protected final LabyAPI labyAPI;
    protected final D document;
    private final String namespace;
    private String qualifiedName;
    private String name;
    private String identifier;
    
    public ElementActivity() {
        this.labyAPI = Laby.labyAPI();
        this.namespace = this.labyAPI.addonService().getAddon(this.getClass()).map((Function<? super LoadedAddon, ?>)LoadedAddon::info).map((Function<? super Object, ? extends String>)InstalledAddonInfo::getNamespace).orElse("labymod");
        WidgetDataCollector.collectData(this.getClass(), (qualifiedName, name) -> {
            this.qualifiedName = qualifiedName;
            this.name = name;
            this.identifier = "%s:%s".formatted(this.namespace, TextFormat.CAMEL_CASE.toSnakeCase(name).toLowerCase(Locale.ENGLISH));
            return;
        });
        this.document = this.createDocument();
    }
    
    protected abstract D createDocument();
    
    @Override
    public void initialize(final Parent parent) {
    }
    
    @Override
    protected void postInitialize() {
        this.document.initialize(this);
        this.document.postInitialize();
    }
    
    @Override
    public void render(final ScreenContext context) {
        this.document.render(context);
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float tickDelta) {
        final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        screenContext.runInContext(stack, mouse, tickDelta, this::render);
    }
    
    @Override
    public boolean renderBackground(final ScreenContext context) {
        return false;
    }
    
    @Override
    public void renderOverlay(final ScreenContext context) {
        this.document.renderOverlay(context);
    }
    
    @Override
    public void renderHoverComponent(final ScreenContext context) {
        this.document.renderHoverComponent(context);
    }
    
    @Override
    public void resize(final int width, final int height) {
        this.document.bounds().setSize((float)width, (float)height, ElementActivity.ACTIVITY_RESIZE);
    }
    
    @Override
    public void tick() {
        this.document.tick();
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        final boolean shouldHandle = this.shouldDocumentHandleKey(key, type);
        return ((key != Key.ESCAPE || shouldHandle) && this.document.keyPressed(key, type)) || (key == Key.ESCAPE && !shouldHandle && this.displayPreviousScreen());
    }
    
    public boolean shouldDocumentHandleKey(final Key key, final InputType type) {
        return true;
    }
    
    @Override
    public boolean keyReleased(final Key key, final InputType type) {
        return this.document.keyReleased(key, type);
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        return this.document.charTyped(key, character);
    }
    
    public boolean displayPreviousScreen() {
        if (this.previousScreen == null) {
            this.labyAPI.minecraft().minecraftWindow().displayScreenRaw(null);
            return true;
        }
        if (this.previousScreen == this) {
            return false;
        }
        this.displayScreenRaw(this.previousScreen);
        return true;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getQualifiedName() {
        return this.qualifiedName;
    }
    
    @Override
    public void updateKeyRepeatingMode(final boolean enabled) {
        this.labyAPI.minecraft().updateKeyRepeatingMode(enabled);
    }
    
    @Override
    public Parent getParent() {
        return this.parent;
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        return this.document.mouseReleased(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return this.document.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return this.document.mouseScrolled(mouse, scrollDelta);
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        return this.document.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    @Override
    public boolean fileDropped(final MutableMouse mouse, final List<Path> paths) {
        return this.document.fileDropped(mouse, paths);
    }
    
    @Override
    public void doScreenAction(final String action, final Map<String, Object> parameters) {
        this.document.doScreenAction(action, parameters);
    }
    
    public D document() {
        return this.document;
    }
    
    @Override
    public Bounds bounds() {
        return this.document.bounds();
    }
    
    @Override
    public Parent getRoot() {
        return this;
    }
    
    @NotNull
    @Override
    public Object mostInnerScreen() {
        return this;
    }
    
    @NotNull
    @Override
    public ScreenInstance mostInnerScreenInstance() {
        return this;
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public String getNamespace() {
        return this.namespace;
    }
    
    static {
        LOGGER = Logging.create(ElementActivity.class);
        ACTIVITY_RESIZE = ModifyReason.of("activityResize");
    }
}
