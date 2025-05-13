// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget;

import net.labymod.api.notification.Notification;
import net.labymod.api.event.client.gui.hud.HudWidgetUpdateRequestEvent;
import net.labymod.api.util.ide.IdeUtil;
import org.jetbrains.annotations.Nullable;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.revision.Revision;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.position.HudWidgetAnchor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.configuration.settings.Setting;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.LabyAPI;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.service.Identifiable;

public abstract class HudWidget<T extends HudWidgetConfig> implements Identifiable
{
    private static final Logging LOGGER;
    private static final String REASON_CONFIG_RELOAD = "config_reload";
    protected final LabyAPI labyAPI;
    protected final String id;
    protected final Class<T> configClass;
    protected final String namespace;
    protected final Component displayName;
    protected T config;
    protected List<Setting> settings;
    protected Icon icon;
    protected HudWidget<?> parent;
    protected HudWidget<?> child;
    protected HudWidgetAnchor anchor;
    private HudWidgetCategory category;
    private HudWidgetDropzone[] dropzones;
    private Revision revision;
    
    protected HudWidget(final String id, final Class<T> configClass) {
        this.anchor = HudWidgetAnchor.LEFT_TOP;
        this.category = HudWidgetCategory.MISCELLANEOUS;
        this.dropzones = new HudWidgetDropzone[0];
        this.revision = null;
        this.id = id;
        this.labyAPI = Laby.labyAPI();
        this.configClass = configClass;
        this.namespace = this.labyAPI.getNamespace(this.getClass());
        final String translationKey = String.format(Locale.ROOT, "%s.hudWidget.%s.name", this.namespace, id);
        this.displayName = Component.translatable(translationKey, new Component[0]);
    }
    
    public final void tick(final boolean isEditorContext) {
        try {
            this.onTick(isEditorContext);
        }
        catch (final Throwable throwable) {
            HudWidget.LOGGER.error("Failed to tick hud widget {}", this.id, throwable);
            this.onException(throwable);
        }
    }
    
    @Deprecated(forRemoval = true, since = "4.1.4")
    public void onTick() {
    }
    
    public void onTick(final boolean isEditorContext) {
        this.onTick();
    }
    
    public void initialize(final HudWidgetWidget widget) {
        this.initialize((AbstractWidget<Widget>)widget);
        this.updateSize(widget, widget.accessor().isEditor(), widget.size());
    }
    
    @Deprecated
    public void initialize(final AbstractWidget<Widget> widget) {
    }
    
    public void postInitialize(final HudWidgetWidget widget) {
        this.postInitialize((AbstractWidget<Widget>)widget);
    }
    
    @Deprecated
    public void postInitialize(final AbstractWidget<Widget> widget) {
    }
    
    public void onBoundsChanged(final HudWidgetWidget widget, final Rectangle previousRect, final Rectangle newRect) {
        this.onBoundsChanged((AbstractWidget<Widget>)widget, previousRect, newRect);
    }
    
    @Deprecated
    public void onBoundsChanged(final AbstractWidget<Widget> widget, final Rectangle previousRect, final Rectangle newRect) {
    }
    
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return false;
    }
    
    public void updateSize(final HudWidgetWidget widget, final boolean isEditorContext, final HudSize size) {
        this.updateSize(widget, size);
    }
    
    public void onUpdate() {
    }
    
    public void updateAnchor(final HudWidgetAnchor anchor) {
        final HudWidgetAnchor prevAnchor = this.anchor;
        this.update(this.anchor = anchor);
        if (prevAnchor != anchor) {
            this.onUpdate();
        }
    }
    
    public HudWidgetAnchor anchor() {
        return this.anchor;
    }
    
    public final void renderWidget(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        try {
            this.render(stack, mouse, partialTicks, isEditorContext, size);
        }
        catch (final Throwable throwable) {
            HudWidget.LOGGER.error("Failed to render hud widget {}", this.id, throwable);
            this.onException(throwable);
        }
    }
    
    public abstract void render(final Stack p0, final MutableMouse p1, final float p2, final boolean p3, final HudSize p4);
    
    public void renderPost(final Stack stack, final MutableMouse mouse, final float partialTicks, final HudWidgetWidget widget) {
        this.renderPost(stack, mouse, partialTicks, (AbstractWidget<Widget>)widget);
        this.render(stack, mouse, partialTicks, false);
        this.render(stack, mouse, partialTicks, true, widget.size());
    }
    
    public void load(final T config) {
        this.config = config;
        if (this.settings == null) {
            this.settings = config.toSettings();
        }
    }
    
    public void reloadConfig() {
        this.load(this.config);
        this.tick(true);
        this.requestUpdate("config_reload");
    }
    
    @Override
    public String getId() {
        return this.id;
    }
    
    public Class<T> getConfigClass() {
        return this.configClass;
    }
    
    public T getConfig() {
        return this.config;
    }
    
    public List<Setting> getSettings() {
        return this.settings;
    }
    
    @Deprecated
    public HudSize size(final HudWidgetWidget widget) {
        return widget.size();
    }
    
    @NotNull
    public HudWidget<?> firstWidget() {
        return (this.parent == null) ? this : this.parent.getFirstWidget();
    }
    
    @NotNull
    public HudWidget<?> lastWidget() {
        return (this.child == null) ? this : this.child.getLastWidget();
    }
    
    public boolean contains(@NotNull final HudWidget<?> widget) {
        if (this.equals(widget)) {
            return true;
        }
        for (HudWidget<?> parent = this.parent; parent != null; parent = parent.parent) {
            if (parent.equals(widget)) {
                return true;
            }
        }
        for (HudWidget<?> child = this.child; child != null; child = child.child) {
            if (child.equals(widget)) {
                return true;
            }
        }
        return false;
    }
    
    public void forEach(final Consumer<HudWidget<?>> action) {
        for (HudWidget<?> widget = this.getFirstWidget(); widget != null; widget = widget.child) {
            action.accept(widget);
        }
    }
    
    @Nullable
    public HudWidget<?> getParent() {
        return this.parent;
    }
    
    public void updateParent(@Nullable final HudWidget<?> parent) {
        if (parent != null && this.contains(parent)) {
            return;
        }
        final HudWidget<?> prevParent = this.parent;
        if (prevParent != null) {
            prevParent.setChildInternal(null);
        }
        if (parent == null) {
            this.setParentInternal(null);
        }
        else {
            this.setParentInternal(parent);
            final HudWidget<?> child = parent.getChild();
            if (child != null) {
                child.updateParent(this.lastWidget());
            }
            parent.setChildInternal(this);
        }
    }
    
    private void setParentInternal(@Nullable final HudWidget<?> parent) {
        this.config.setParentId((parent == null) ? null : parent.getId());
        this.parent = parent;
    }
    
    @Nullable
    public HudWidget<?> getChild() {
        return this.child;
    }
    
    public void updateChild(@Nullable final HudWidget<?> child) {
        if (child != null && this.contains(child)) {
            return;
        }
        if (child == null) {
            final HudWidget<?> prevChild = this.getChild();
            if (prevChild != null) {
                prevChild.updateParent(null);
            }
            this.setChildInternal(null);
        }
        else {
            child.updateParent(this);
        }
    }
    
    private void setChildInternal(@Nullable final HudWidget<?> child) {
        if (child != null) {
            ((HudWidgetConfig)child.getConfig()).setParentId(this.id);
        }
        this.child = child;
    }
    
    public Icon getIcon() {
        return this.icon;
    }
    
    public void setIcon(final Icon icon) {
        this.icon = icon;
        IdeUtil.dumpSpriteIcons(this.namespace + ".hud_widget." + this.getId(), icon);
    }
    
    public abstract boolean isVisibleInGame();
    
    public boolean isEnabled() {
        return this.config.isEnabled();
    }
    
    public HudWidget<?> getNextVisibleChild(final boolean isEditorContext) {
        HudWidget<?> hudWidget;
        for (hudWidget = this.getChild(); hudWidget != null && (!hudWidget.isHolderEnabled() || (!hudWidget.isVisibleInGame() && !isEditorContext)); hudWidget = hudWidget.getChild()) {}
        return hudWidget;
    }
    
    @NotNull
    public Component displayName() {
        return this.displayName;
    }
    
    @Deprecated
    public Component getDisplayName() {
        return this.displayName;
    }
    
    public HudWidgetDropzone getAttachedDropzone() {
        if (this.dropzones.length == 0 || this.config.getDropzoneId() == null) {
            return null;
        }
        for (final HudWidgetDropzone dropzone : this.dropzones) {
            if (dropzone.getId().equals(this.config.getDropzoneId())) {
                return dropzone;
            }
        }
        return null;
    }
    
    public HudWidgetDropzone[] getDropzones() {
        return this.dropzones;
    }
    
    protected final void bindCategory(final HudWidgetCategory category) {
        this.category = ((category == null) ? HudWidgetCategory.MISCELLANEOUS : category);
    }
    
    protected final void bindDropzones(final HudWidgetDropzone... dropzones) {
        this.dropzones = new HudWidgetDropzone[dropzones.length];
        for (int i = 0; i < dropzones.length; ++i) {
            this.dropzones[i] = dropzones[i].copy();
        }
    }
    
    public void initializePreConfigured(final T config) {
    }
    
    public final HudWidgetCategory category() {
        return this.category;
    }
    
    public boolean isHolderEnabled() {
        return Laby.labyAPI().addonService().isEnabled(this.namespace);
    }
    
    public boolean renderInDebug() {
        return false;
    }
    
    public boolean canPreInitialize() {
        return true;
    }
    
    public boolean isResizeable() {
        return this instanceof ResizeableHudWidget;
    }
    
    public void requestUpdate(@NotNull final String reason) throws IllegalStateException {
        if (!this.isEnabled()) {
            throw new IllegalStateException("Refused request because the widget is disabled");
        }
        Laby.fireEvent(new HudWidgetUpdateRequestEvent(this, reason));
    }
    
    public boolean handlesScaling() {
        return false;
    }
    
    public boolean fitsInDropzone(final HudWidgetDropzone zone) {
        for (final HudWidgetDropzone dropzone : this.dropzones) {
            if (dropzone.equals(zone)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasRevision() {
        return this.revision != null;
    }
    
    @Nullable
    public Revision getRevision() {
        return this.revision;
    }
    
    public void setRevision(final Revision revision) {
        this.revision = revision;
    }
    
    @Deprecated
    public void renderPost(final Stack stack, final MutableMouse mouse, final float partialTicks, final AbstractWidget<Widget> widget) {
    }
    
    @Deprecated
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext) {
    }
    
    @Deprecated
    @NotNull
    public HudWidget<?> getFirstWidget() {
        return this.firstWidget();
    }
    
    @Deprecated
    @NotNull
    public HudWidget<?> getLastWidget() {
        return (this.child == null) ? this : this.child.getLastWidget();
    }
    
    @Deprecated
    public void updateSize(final HudWidgetWidget widget, final HudSize size) {
        throw new AbstractMethodError("HudWidget#updateSize(HudWidgetWidget, Boolean, HudSize) is not implemented");
    }
    
    @Deprecated
    public void update(final HudWidgetAnchor anchor) {
    }
    
    @Deprecated
    public boolean isAvailable() {
        return this.isHolderEnabled();
    }
    
    @Deprecated
    public boolean isAlwaysAvailable() {
        return false;
    }
    
    @Deprecated
    public void requestUpdate() throws IllegalStateException {
        this.requestUpdate("unknown");
    }
    
    private void onException(final Throwable throwable) {
        Notification.builder().title(Component.text("LabyMod")).text(Component.translatable("labymod.hudWidget.crash_notification.text", this.displayName())).type(Notification.Type.SYSTEM).buildAndPush();
        if (this.config == null) {
            return;
        }
        this.config.setEnabled(false);
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
    
    public interface Updatable
    {
        void update(final String p0);
    }
}
