// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.widget.action.FilterAction;
import java.util.Collection;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSizeList;
import net.labymod.api.client.gui.screen.widget.attributes.BoxSizing;
import net.labymod.api.metadata.Metadata;
import java.util.Map;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.animation.Animation;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.util.WidgetDataCollector;
import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@Deprecated
@AutoWidget
public class WrappedWidget extends StyledWidget
{
    @NotNull
    protected final Widget childWidget;
    private String qualifiedName;
    private DirectPropertyValueAccessor directPropertyValueAccessor;
    
    protected WrappedWidget(@NotNull final Widget childWidget) {
        this.childWidget = childWidget;
        if (childWidget instanceof StyledWidget) {
            Widget current = childWidget;
            do {
                ((StyledWidget)current).actualWidget = this.actualWidget;
            } while (current instanceof WrappedWidget && (current = ((WrappedWidget)current).childWidget()) instanceof StyledWidget);
        }
        WidgetDataCollector.collectData(this.getClass(), (qualifiedName, name, directPropertyValueAccessor) -> {
            this.qualifiedName = qualifiedName;
            this.directPropertyValueAccessor = directPropertyValueAccessor;
        });
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o) || this.childWidget == o;
    }
    
    @Override
    public void unfocus() {
        this.childWidget.unfocus();
    }
    
    @Override
    public void reset() {
        this.childWidget.reset();
    }
    
    @Override
    public void initialize(final Parent parent) {
        this.childWidget.initialize(parent);
    }
    
    @Override
    public void initializeLazy(final Parent parent) {
        this.childWidget.initializeLazy(parent);
    }
    
    @Override
    public void postInitialize() {
        this.childWidget.postInitialize();
    }
    
    @Override
    public void applyStyleSheet(final StyleSheet styleSheet) {
        this.childWidget.applyStyleSheet(styleSheet);
        super.applyStyleSheet(styleSheet);
    }
    
    @Override
    public void reInitialize(final Parent parent) {
        this.childWidget.reInitialize(parent);
    }
    
    @Override
    public void postStyleSheetLoad() {
        this.childWidget.postStyleSheetLoad();
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float tickDelta) {
        super.render(stack, mouse, tickDelta);
        this.childWidget.render(stack, mouse, tickDelta);
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        this.childWidget.render(context);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        this.childWidget.renderWidget(context);
    }
    
    @Override
    public void renderWidget(final Stack stack, final MutableMouse mouse, final float partialTicks) {
        this.childWidget.renderWidget(stack, mouse, partialTicks);
    }
    
    @Override
    public void renderOverlay(final ScreenContext context) {
        this.childWidget.renderOverlay(context);
    }
    
    @Override
    public void renderHoverComponent(final ScreenContext context) {
        this.childWidget.renderHoverComponent(context);
    }
    
    @Override
    public String getDefaultRendererName() {
        return this.childWidget.getDefaultRendererName();
    }
    
    @Override
    public void tick() {
        this.childWidget.tick();
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return this.childWidget.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        return this.childWidget.mouseReleased(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return this.childWidget.mouseScrolled(mouse, scrollDelta);
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        return this.childWidget.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    @Override
    public boolean fileDropped(final MutableMouse mouse, final List<Path> paths) {
        return this.childWidget.fileDropped(mouse, paths);
    }
    
    @Override
    public List<? extends Widget> getChildren() {
        return this.childWidget.getChildren();
    }
    
    @Override
    public boolean onPress() {
        return this.childWidget.onPress();
    }
    
    @Override
    public boolean hasParentStateAttributes() {
        return this.childWidget.hasParentStateAttributes();
    }
    
    @Override
    public boolean isHovered() {
        return this.childWidget.isHovered();
    }
    
    @Override
    public boolean isTicking() {
        return this.childWidget.isTicking();
    }
    
    @Override
    public float getContentSize(final BoundsType type, final WidgetSide side) {
        return this.childWidget.getContentSize(type, side);
    }
    
    @Override
    public LssProperty<PriorityLayer> priorityLayer() {
        return this.childWidget.priorityLayer();
    }
    
    @Override
    public LssProperty<WidgetAlignment> alignmentX() {
        return this.childWidget.alignmentX();
    }
    
    @Override
    public LssProperty<WidgetAlignment> alignmentY() {
        return this.childWidget.alignmentY();
    }
    
    @Override
    public boolean hasTabFocus() {
        return this.childWidget.hasTabFocus();
    }
    
    @Override
    public boolean isFocused() {
        return this.childWidget.isFocused();
    }
    
    @Override
    public void setFocused(final boolean focused) {
        this.childWidget.setFocused(focused);
    }
    
    @Override
    public void setActionListener(final Runnable listener) {
        this.childWidget.setActionListener(listener);
    }
    
    @Override
    public void setActionListener(final String id, final Runnable listener) {
        this.childWidget.setActionListener(id, listener);
    }
    
    @Override
    public void removeActionListener(final String id) {
        this.childWidget.removeActionListener(id);
    }
    
    @Override
    public void setPressable(final Pressable pressable) {
        this.childWidget.setPressable(pressable);
    }
    
    @Override
    public void setPressListener(final BooleanSupplier pressable) {
        this.childWidget.setPressListener(pressable);
    }
    
    @Override
    public boolean isVisible() {
        return this.childWidget.isVisible();
    }
    
    @Override
    public void setVisible(final boolean visible) {
        this.childWidget.setVisible(visible);
    }
    
    @Override
    public <W extends Widget> W addId(final String... ids) {
        return this.childWidget.addId(ids);
    }
    
    @Override
    public <W extends Widget> W addId(final String id) {
        return this.childWidget.addId(id);
    }
    
    @Override
    protected void reloadStyleSheets() {
        if (this.childWidget instanceof final StyledWidget styledWidget) {
            styledWidget.reloadStyleSheets();
        }
    }
    
    @Override
    public boolean hasId(final CharSequence id) {
        return this.childWidget.hasId(id);
    }
    
    @Override
    public void removeId(final String id) {
        this.childWidget.removeId(id);
    }
    
    @Override
    public <W extends Widget> W replaceId(final String currentId, final String newId) {
        return this.childWidget.replaceId(currentId, newId);
    }
    
    @Override
    public String getUniqueId() {
        return this.childWidget.getUniqueId();
    }
    
    @Override
    public List<CharSequence> getIds() {
        return this.childWidget.getIds();
    }
    
    @Override
    public String getTypeName() {
        return this.childWidget.getTypeName();
    }
    
    @Override
    public String getQualifiedName() {
        return this.qualifiedName;
    }
    
    @Override
    public Animation animation() {
        return this.childWidget.animation();
    }
    
    @Override
    public void playAnimation(final String id, final Runnable finishHandler) {
        this.childWidget.playAnimation(id, finishHandler);
    }
    
    @Override
    public void playAnimation(final String id) {
        this.playAnimation(id, null);
    }
    
    @Override
    public LssProperty<ThemeRenderer> renderer() {
        return this.childWidget.renderer();
    }
    
    @Override
    public Bounds bounds() {
        return this.childWidget.bounds();
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        return this.childWidget.keyPressed(key, type);
    }
    
    @Override
    public boolean keyReleased(final Key key, final InputType type) {
        return this.childWidget.keyReleased(key, type);
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        return this.childWidget.charTyped(key, character);
    }
    
    @Override
    public void doScreenAction(final String action, final Map<String, Object> parameters) {
        this.childWidget.doScreenAction(action, parameters);
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.childWidget.metadata(metadata);
    }
    
    @Override
    public Metadata metadata() {
        return this.childWidget.metadata();
    }
    
    @Override
    public boolean isDragging() {
        return this.childWidget.isDragging();
    }
    
    @Override
    public boolean isDraggable() {
        return this.childWidget.isDraggable();
    }
    
    @Override
    public void setDragging(final boolean dragging) {
        this.childWidget.setDragging(dragging);
    }
    
    @Override
    public boolean isInteractable() {
        return this.childWidget.isInteractable();
    }
    
    @NotNull
    public Widget childWidget() {
        return this.childWidget;
    }
    
    @Override
    public Parent getParent() {
        return this.childWidget.getParent();
    }
    
    @Override
    public void handleAttributes() {
        this.childWidget.handleAttributes();
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return this.childWidget.getContentWidth(type);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        return this.childWidget.getContentHeight(type);
    }
    
    @Override
    public LssProperty<BoxSizing> boxSizing() {
        return this.childWidget.boxSizing();
    }
    
    @Override
    public WidgetSizeList sizes() {
        return this.childWidget.sizes();
    }
    
    @Override
    public void setSize(final SizeType type, final WidgetSide side, final WidgetSize size) {
        this.childWidget.setSize(type, side, size);
    }
    
    @Override
    public Float getSize(final SizeType type, final WidgetSide side) {
        return this.childWidget.getSize(type, side);
    }
    
    @Override
    public boolean hasSize(final SizeType type, final WidgetSide side, final WidgetSize.Type sizeType) {
        return this.childWidget.hasSize(type, side, sizeType);
    }
    
    @Override
    public boolean hasAnySize(final WidgetSide side) {
        return this.childWidget.hasAnySize(side);
    }
    
    @Override
    public boolean isPercentageSize(final SizeType type, final WidgetSide side) {
        return this.childWidget.isPercentageSize(type, side);
    }
    
    @Override
    public float getFontWeight() {
        return this.childWidget.getFontWeight();
    }
    
    @Override
    public boolean isOutOfBounds() {
        return this.childWidget.isOutOfBounds();
    }
    
    @Override
    public void setOutOfBounds(final boolean outOfBounds) {
        this.childWidget.setOutOfBounds(outOfBounds);
    }
    
    @Override
    public List<StyleInstructions> getStyleInstructions() {
        return this.childWidget.getStyleInstructions();
    }
    
    @Override
    public List<StyleInstructions> getSortedStyleInstructions() {
        return this.childWidget.getSortedStyleInstructions();
    }
    
    @Override
    public List<StyleInstructions> getDynamicStyleInstructions() {
        return this.childWidget.getDynamicStyleInstructions();
    }
    
    @Override
    public List<StyleInstructions> getDynamicSortedStyleInstructions() {
        return this.childWidget.getDynamicSortedStyleInstructions();
    }
    
    @Override
    public void addAttributePatch(final Selector selector, final AttributePatch patch, final int skipDepth) {
        super.addAttributePatch(selector, patch, skipDepth);
        this.childWidget.addAttributePatch(selector, patch, skipDepth);
    }
    
    @Override
    public void patch(final AttributePatch patch) {
        super.patch(patch);
        this.childWidget.patch(patch);
    }
    
    @Override
    public void patchAttributes() {
        super.patchAttributes();
        this.childWidget.patchAttributes();
    }
    
    @Override
    public void updateState(final boolean force) {
        super.updateState(force);
        this.childWidget.updateState(force);
    }
    
    @Override
    public void resetState() {
        this.childWidget.resetState();
        super.resetState();
    }
    
    @Override
    public Parent getRoot() {
        return this.childWidget.getRoot();
    }
    
    @Override
    public String toString() {
        return this.childWidget.getTypeName();
    }
    
    @Override
    public boolean isActive() {
        return this.childWidget.isActive();
    }
    
    @Override
    public void setActive(final boolean active) {
        this.childWidget.setActive(active);
    }
    
    @Override
    public boolean hasHoverComponent() {
        return this.childWidget.hasHoverComponent();
    }
    
    @Override
    public void setHoverComponent(final Component component) {
        this.childWidget.setHoverComponent(component);
    }
    
    @Override
    public void setHoverComponent(final Component component, final float maxWidth) {
        this.childWidget.setHoverComponent(component, maxWidth);
    }
    
    @Override
    public void setHoverRenderableComponent(final RenderableComponent renderableComponent) {
        this.childWidget.setHoverRenderableComponent(renderableComponent);
    }
    
    @Override
    public float getTranslateX() {
        return this.childWidget.getTranslateX();
    }
    
    @Override
    public void setTranslateX(final float translateX) {
        this.childWidget.setTranslateX(translateX);
    }
    
    @Override
    public float getTranslateY() {
        return this.childWidget.getTranslateY();
    }
    
    @Override
    public void setTranslateY(final float translateY) {
        this.childWidget.setTranslateY(translateY);
    }
    
    @Override
    public float getScaleX() {
        return this.childWidget.getScaleX();
    }
    
    @Override
    public void setScaleX(final float scaleX) {
        this.childWidget.setScaleX(scaleX);
    }
    
    @Override
    public float getScaleY() {
        return this.childWidget.getScaleY();
    }
    
    @Override
    public void setScaleY(final float scaleY) {
        this.childWidget.setScaleY(scaleY);
    }
    
    @Override
    public void setScale(final float scale) {
        this.childWidget.setScale(scale);
    }
    
    @Override
    public float getEffectiveWidth() {
        return this.childWidget.getEffectiveWidth();
    }
    
    @Override
    public float getEffectiveHeight() {
        return this.childWidget.getEffectiveHeight();
    }
    
    @Override
    public boolean isSelected() {
        return this.childWidget.isSelected();
    }
    
    @Override
    public void setSelected(final boolean selected) {
        this.childWidget.setSelected(selected);
    }
    
    @Override
    public boolean isCancelParentHoverComponent() {
        return this.childWidget.isCancelParentHoverComponent();
    }
    
    @Override
    public Rectangle childrenRegion() {
        return this.childWidget.childrenRegion();
    }
    
    @Override
    public <K extends Widget> void traverse(final Collection<K> output, final FilterAction filterAction) {
        this.childWidget.traverse(output, filterAction);
    }
    
    @Override
    public <T extends Widget> List<T> getChildrenAt(final int x, final int y) {
        return this.childWidget.getChildrenAt(x, y);
    }
    
    @Override
    public <T extends Widget> void onAttachedTo(final AbstractWidget<T> parent) {
        this.childWidget.onAttachedTo(parent);
    }
    
    @Override
    public void updateBounds() {
        this.childWidget.updateBounds();
    }
    
    @Override
    public void destroy() {
        this.childWidget.destroy();
    }
    
    @Override
    public boolean isDisposed() {
        return this.childWidget.isDisposed();
    }
    
    @Override
    public boolean isDestroyed() {
        return this.childWidget.isDestroyed();
    }
    
    @Override
    public boolean isLazy() {
        return this.childWidget.isLazy();
    }
    
    @Override
    public Collection<AttributeState> getAttributeStates() {
        return this.childWidget.getAttributeStates();
    }
    
    @Override
    public long getAttributeStateToggleTimestamp(final AttributeState state) {
        return this.childWidget.getAttributeStateToggleTimestamp(state);
    }
    
    @Override
    public boolean isAttributeStateEnabled(final AttributeState state) {
        return this.childWidget.isAttributeStateEnabled(state);
    }
    
    @Override
    public boolean isInteractableOutside() {
        return this.childWidget.isInteractableOutside();
    }
    
    @Override
    public void setAttributeState(final AttributeState state, final boolean enabled) {
        this.childWidget.setAttributeState(state, enabled);
    }
    
    @Override
    public boolean transformMouse(final MutableMouse mouse, final BooleanSupplier handler) {
        return this.childWidget.transformMouse(mouse, handler);
    }
    
    @Override
    public Widget getHighestParentWidget() {
        return this.childWidget.getHighestParentWidget();
    }
    
    @Override
    public void dispose() {
        this.childWidget.dispose();
    }
    
    @Override
    public LssVariableHolder getParentVariableHolder() {
        return this.childWidget.getParentVariableHolder();
    }
    
    @Override
    public Map<String, LssVariable> getLssVariables() {
        return this.childWidget.getLssVariables();
    }
    
    @Override
    public void updateLssVariable(final LssVariable variable) {
        super.updateLssVariable(variable);
        this.childWidget.updateLssVariable(variable);
    }
    
    @Override
    public void forceUpdateLssVariable(final LssVariable variable) {
        super.updateLssVariable(variable);
        this.childWidget.updateLssVariable(variable);
    }
    
    @Override
    public LssVariable getVariable(@NotNull final String key) {
        return this.childWidget.getVariable(key);
    }
    
    @Override
    public void setVariable(@NotNull final String key, @NotNull final String value) {
        this.childWidget.setVariable(key, value);
    }
    
    @Override
    public void clearVariable(@NotNull final String key) {
        this.childWidget.clearVariable(key);
    }
    
    @Override
    public boolean shouldHandleEscape() {
        return this.childWidget.shouldHandleEscape();
    }
    
    @Nullable
    @Override
    public DirectPropertyValueAccessor getDirectPropertyValueAccessor() {
        return this.directPropertyValueAccessor;
    }
    
    @Override
    public void renderExtraDebugInformation() {
        this.childWidget.renderExtraDebugInformation();
    }
}
