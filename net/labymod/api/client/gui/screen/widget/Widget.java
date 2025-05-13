// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.widget.action.FilterAction;
import java.util.Collection;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.lss.style.Selector;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributePatch;
import net.labymod.api.client.gui.lss.style.modifier.attribute.StyleInstructions;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.animation.Animation;
import java.util.List;
import net.labymod.api.util.CharSequences;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.size.WidgetSizeList;
import net.labymod.api.client.gui.screen.widget.attributes.BoxSizing;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.util.Disposable;
import net.labymod.api.client.gui.element.CompositeElement;

public interface Widget extends CompositeElement<Widget>, Disposable, LssVariableHolder
{
    void reset();
    
    void applyStyleSheet(final StyleSheet p0);
    
    String getDefaultRendererName();
    
    default void reInitialize() {
        this.reInitialize(this.getParent());
    }
    
    void initializeLazy(final Parent p0);
    
    void reInitialize(final Parent p0);
    
    void postStyleSheetLoad();
    
    @Deprecated(forRemoval = true, since = "4.2.42")
    void render(final Stack p0, final MutableMouse p1, final float p2);
    
    void renderWidget(final ScreenContext p0);
    
    @Deprecated(forRemoval = true, since = "4.2.42")
    void renderWidget(final Stack p0, final MutableMouse p1, final float p2);
    
    void renderOverlay(final ScreenContext p0);
    
    void renderHoverComponent(final ScreenContext p0);
    
    boolean hasTabFocus();
    
    void setActionListener(final String p0, final Runnable p1);
    
    void setActionListener(final Runnable p0);
    
    void removeActionListener(final String p0);
    
    void unfocus();
    
    boolean hasParentStateAttributes();
    
    boolean isHovered();
    
    float getContentSize(final BoundsType p0, final WidgetSide p1);
    
    float getContentWidth(final BoundsType p0);
    
    float getContentHeight(final BoundsType p0);
    
    LssProperty<BoxSizing> boxSizing();
    
    WidgetSizeList sizes();
    
    void setSize(final SizeType p0, final WidgetSide p1, final WidgetSize p2);
    
    Float getSize(final SizeType p0, final WidgetSide p1);
    
    boolean hasSize(final SizeType p0, final WidgetSide p1, final WidgetSize.Type p2);
    
    boolean hasAnySize(final WidgetSide p0);
    
    default boolean hasSize(final WidgetSide side, final WidgetSize.Type sizeType) {
        for (final SizeType type : SizeType.values()) {
            if (this.hasSize(type, side, sizeType)) {
                return true;
            }
        }
        return false;
    }
    
    default boolean hasSize(final WidgetSide side) {
        for (final WidgetSize.Type sizeType : WidgetSize.Type.values()) {
            if (this.hasSize(side, sizeType)) {
                return true;
            }
        }
        return false;
    }
    
    default float getSizeOr(final SizeType type, final WidgetSide side, final float defaultValue) {
        final Float size = this.getSize(type, side);
        return (size != null) ? size : defaultValue;
    }
    
    boolean isPercentageSize(final SizeType p0, final WidgetSide p1);
    
    float getFontWeight();
    
    boolean isOutOfBounds();
    
    void setOutOfBounds(final boolean p0);
    
    LssProperty<WidgetAlignment> alignmentX();
    
    LssProperty<WidgetAlignment> alignmentY();
    
    boolean isFocused();
    
    void setFocused(final boolean p0);
    
    boolean onPress();
    
    LssProperty<PriorityLayer> priorityLayer();
    
     <W extends Widget> W addId(final String... p0);
    
    default <W extends Widget> W addId(final CharSequence sequence) {
        return this.addId(CharSequences.toString(sequence));
    }
    
     <W extends Widget> W addId(final String p0);
    
    boolean hasId(final CharSequence p0);
    
    void removeId(final String p0);
    
     <W extends Widget> W replaceId(final String p0, final String p1);
    
    String getUniqueId();
    
    List<CharSequence> getIds();
    
    String getTypeName();
    
    String getQualifiedName();
    
    Animation animation();
    
    void playAnimation(final String p0, final Runnable p1);
    
    void playAnimation(final String p0);
    
    LssProperty<ThemeRenderer> renderer();
    
    void handleAttributes();
    
    void updateBounds();
    
    List<StyleInstructions> getStyleInstructions();
    
    List<StyleInstructions> getSortedStyleInstructions();
    
    List<StyleInstructions> getDynamicStyleInstructions();
    
    @Deprecated
    List<StyleInstructions> getDynamicSortedStyleInstructions();
    
    void updateState(final boolean p0);
    
    void resetState();
    
    void patchAttributes();
    
    void patch(final AttributePatch p0);
    
    void addAttributePatch(final Selector p0, final AttributePatch p1, final int p2);
    
    void setPressable(final Pressable p0);
    
    void setPressListener(final BooleanSupplier p0);
    
    boolean hasHoverComponent();
    
    void setHoverComponent(final Component p0);
    
    void setHoverComponent(final Component p0, final float p1);
    
    void setHoverRenderableComponent(final RenderableComponent p0);
    
    float getTranslateX();
    
    void setTranslateX(final float p0);
    
    float getTranslateY();
    
    void setTranslateY(final float p0);
    
    float getScaleX();
    
    void setScaleX(final float p0);
    
    float getScaleY();
    
    void setScaleY(final float p0);
    
    void setScale(final float p0);
    
    float getEffectiveWidth();
    
    float getEffectiveHeight();
    
    boolean isSelected();
    
    void setSelected(final boolean p0);
    
    boolean isCancelParentHoverComponent();
    
    Rectangle childrenRegion();
    
    Widget actualWidget();
    
     <T extends Widget> void traverse(final Collection<T> p0, final FilterAction p1);
    
    default <T extends Widget> List<T> getChildrenAt(final Point point) {
        return this.getChildrenAt(point.getX(), point.getY());
    }
    
     <T extends Widget> List<T> getChildrenAt(final int p0, final int p1);
    
     <T extends Widget> void onAttachedTo(final AbstractWidget<T> p0);
    
    void destroy();
    
    boolean isDisposed();
    
    boolean isDestroyed();
    
    boolean isLazy();
    
    default int getSortingValue() {
        return 0;
    }
    
    Collection<AttributeState> getAttributeStates();
    
    long getAttributeStateToggleTimestamp(final AttributeState p0);
    
    boolean isAttributeStateEnabled(final AttributeState p0);
    
    void setAttributeState(final AttributeState p0, final boolean p1);
    
    void applyMediaRules(final boolean p0);
    
    Widget getHighestParentWidget();
    
    boolean shouldHandleEscape();
    
    @Nullable
    DirectPropertyValueAccessor getDirectPropertyValueAccessor();
    
    @ApiStatus.Experimental
    default void renderExtraDebugInformation() {
    }
}
