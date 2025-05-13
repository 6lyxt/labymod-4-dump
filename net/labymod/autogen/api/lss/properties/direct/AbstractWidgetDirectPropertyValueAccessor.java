// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.AbstractWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundDirtBrightnessPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetForceVanillaFontPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetClearDepthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetHoverBoxDelayPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetFilterPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAnimationTimingFunctionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetFontWeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundDirtTypePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundDirtShiftPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundAlwaysDirtPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundColorTransitionDurationPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBackgroundColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetRenderChildrenPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetCancelParentHoverComponentPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetDistinctPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetFitOuterPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetDestroyDelayPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetInteractableOutsidePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAlwaysFocusedPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetUseFloatingPointPositionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetWriteToStencilBufferPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetStencilTranslationPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetStencilYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetStencilXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetMouseRenderDistancePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetOpacityPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetScaleYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetScaleXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetZIndexPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetTranslateYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetTranslateXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAlignmentYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAlignmentXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPriorityLayerPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetHeightPrecisionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetWidthPrecisionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPaddingBottomPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPaddingRightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPaddingTopPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPaddingLeftPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetMarginBottomPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetMarginRightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetMarginTopPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetMarginLeftPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBottomPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetRightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetTopPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetLeftPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetVisiblePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetPressablePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetInteractablePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetDraggablePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetAnimationDurationPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetBoxSizingPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.AbstractWidgetRendererPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class AbstractWidgetDirectPropertyValueAccessor extends StyledWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> renderer;
    protected PropertyValueAccessor<?, ?, ?> boxSizing;
    protected PropertyValueAccessor<?, ?, ?> animationDuration;
    protected PropertyValueAccessor<?, ?, ?> draggable;
    protected PropertyValueAccessor<?, ?, ?> interactable;
    protected PropertyValueAccessor<?, ?, ?> pressable;
    protected PropertyValueAccessor<?, ?, ?> visible;
    protected PropertyValueAccessor<?, ?, ?> left;
    protected PropertyValueAccessor<?, ?, ?> top;
    protected PropertyValueAccessor<?, ?, ?> right;
    protected PropertyValueAccessor<?, ?, ?> bottom;
    protected PropertyValueAccessor<?, ?, ?> marginLeft;
    protected PropertyValueAccessor<?, ?, ?> marginTop;
    protected PropertyValueAccessor<?, ?, ?> marginRight;
    protected PropertyValueAccessor<?, ?, ?> marginBottom;
    protected PropertyValueAccessor<?, ?, ?> paddingLeft;
    protected PropertyValueAccessor<?, ?, ?> paddingTop;
    protected PropertyValueAccessor<?, ?, ?> paddingRight;
    protected PropertyValueAccessor<?, ?, ?> paddingBottom;
    protected PropertyValueAccessor<?, ?, ?> widthPrecision;
    protected PropertyValueAccessor<?, ?, ?> heightPrecision;
    protected PropertyValueAccessor<?, ?, ?> priorityLayer;
    protected PropertyValueAccessor<?, ?, ?> alignmentX;
    protected PropertyValueAccessor<?, ?, ?> alignmentY;
    protected PropertyValueAccessor<?, ?, ?> translateX;
    protected PropertyValueAccessor<?, ?, ?> translateY;
    protected PropertyValueAccessor<?, ?, ?> zIndex;
    protected PropertyValueAccessor<?, ?, ?> scaleX;
    protected PropertyValueAccessor<?, ?, ?> scaleY;
    protected PropertyValueAccessor<?, ?, ?> opacity;
    protected PropertyValueAccessor<?, ?, ?> mouseRenderDistance;
    protected PropertyValueAccessor<?, ?, ?> stencilX;
    protected PropertyValueAccessor<?, ?, ?> stencilY;
    protected PropertyValueAccessor<?, ?, ?> stencilTranslation;
    protected PropertyValueAccessor<?, ?, ?> writeToStencilBuffer;
    protected PropertyValueAccessor<?, ?, ?> useFloatingPointPosition;
    protected PropertyValueAccessor<?, ?, ?> alwaysFocused;
    protected PropertyValueAccessor<?, ?, ?> interactableOutside;
    protected PropertyValueAccessor<?, ?, ?> destroyDelay;
    protected PropertyValueAccessor<?, ?, ?> fitOuter;
    protected PropertyValueAccessor<?, ?, ?> distinct;
    protected PropertyValueAccessor<?, ?, ?> cancelParentHoverComponent;
    protected PropertyValueAccessor<?, ?, ?> renderChildren;
    protected PropertyValueAccessor<?, ?, ?> backgroundColor;
    protected PropertyValueAccessor<?, ?, ?> backgroundColorTransitionDuration;
    protected PropertyValueAccessor<?, ?, ?> backgroundAlwaysDirt;
    protected PropertyValueAccessor<?, ?, ?> backgroundDirtShift;
    protected PropertyValueAccessor<?, ?, ?> backgroundDirtType;
    protected PropertyValueAccessor<?, ?, ?> fontWeight;
    protected PropertyValueAccessor<?, ?, ?> animationTimingFunction;
    protected PropertyValueAccessor<?, ?, ?> filter;
    protected PropertyValueAccessor<?, ?, ?> hoverBoxDelay;
    protected PropertyValueAccessor<?, ?, ?> clearDepth;
    protected PropertyValueAccessor<?, ?, ?> forceVanillaFont;
    protected PropertyValueAccessor<?, ?, ?> backgroundDirtBrightness;
    LssPropertyResetter AbstractWidgetResetter;
    
    public AbstractWidgetDirectPropertyValueAccessor() {
        this.renderer = new AbstractWidgetRendererPropertyValueAccessor();
        this.boxSizing = new AbstractWidgetBoxSizingPropertyValueAccessor();
        this.animationDuration = new AbstractWidgetAnimationDurationPropertyValueAccessor();
        this.draggable = new AbstractWidgetDraggablePropertyValueAccessor();
        this.interactable = new AbstractWidgetInteractablePropertyValueAccessor();
        this.pressable = new AbstractWidgetPressablePropertyValueAccessor();
        this.visible = new AbstractWidgetVisiblePropertyValueAccessor();
        this.left = new AbstractWidgetLeftPropertyValueAccessor();
        this.top = new AbstractWidgetTopPropertyValueAccessor();
        this.right = new AbstractWidgetRightPropertyValueAccessor();
        this.bottom = new AbstractWidgetBottomPropertyValueAccessor();
        this.marginLeft = new AbstractWidgetMarginLeftPropertyValueAccessor();
        this.marginTop = new AbstractWidgetMarginTopPropertyValueAccessor();
        this.marginRight = new AbstractWidgetMarginRightPropertyValueAccessor();
        this.marginBottom = new AbstractWidgetMarginBottomPropertyValueAccessor();
        this.paddingLeft = new AbstractWidgetPaddingLeftPropertyValueAccessor();
        this.paddingTop = new AbstractWidgetPaddingTopPropertyValueAccessor();
        this.paddingRight = new AbstractWidgetPaddingRightPropertyValueAccessor();
        this.paddingBottom = new AbstractWidgetPaddingBottomPropertyValueAccessor();
        this.widthPrecision = new AbstractWidgetWidthPrecisionPropertyValueAccessor();
        this.heightPrecision = new AbstractWidgetHeightPrecisionPropertyValueAccessor();
        this.priorityLayer = new AbstractWidgetPriorityLayerPropertyValueAccessor();
        this.alignmentX = new AbstractWidgetAlignmentXPropertyValueAccessor();
        this.alignmentY = new AbstractWidgetAlignmentYPropertyValueAccessor();
        this.translateX = new AbstractWidgetTranslateXPropertyValueAccessor();
        this.translateY = new AbstractWidgetTranslateYPropertyValueAccessor();
        this.zIndex = new AbstractWidgetZIndexPropertyValueAccessor();
        this.scaleX = new AbstractWidgetScaleXPropertyValueAccessor();
        this.scaleY = new AbstractWidgetScaleYPropertyValueAccessor();
        this.opacity = new AbstractWidgetOpacityPropertyValueAccessor();
        this.mouseRenderDistance = new AbstractWidgetMouseRenderDistancePropertyValueAccessor();
        this.stencilX = new AbstractWidgetStencilXPropertyValueAccessor();
        this.stencilY = new AbstractWidgetStencilYPropertyValueAccessor();
        this.stencilTranslation = new AbstractWidgetStencilTranslationPropertyValueAccessor();
        this.writeToStencilBuffer = new AbstractWidgetWriteToStencilBufferPropertyValueAccessor();
        this.useFloatingPointPosition = new AbstractWidgetUseFloatingPointPositionPropertyValueAccessor();
        this.alwaysFocused = new AbstractWidgetAlwaysFocusedPropertyValueAccessor();
        this.interactableOutside = new AbstractWidgetInteractableOutsidePropertyValueAccessor();
        this.destroyDelay = new AbstractWidgetDestroyDelayPropertyValueAccessor();
        this.fitOuter = new AbstractWidgetFitOuterPropertyValueAccessor();
        this.distinct = new AbstractWidgetDistinctPropertyValueAccessor();
        this.cancelParentHoverComponent = new AbstractWidgetCancelParentHoverComponentPropertyValueAccessor();
        this.renderChildren = new AbstractWidgetRenderChildrenPropertyValueAccessor();
        this.backgroundColor = new AbstractWidgetBackgroundColorPropertyValueAccessor();
        this.backgroundColorTransitionDuration = new AbstractWidgetBackgroundColorTransitionDurationPropertyValueAccessor();
        this.backgroundAlwaysDirt = new AbstractWidgetBackgroundAlwaysDirtPropertyValueAccessor();
        this.backgroundDirtShift = new AbstractWidgetBackgroundDirtShiftPropertyValueAccessor();
        this.backgroundDirtType = new AbstractWidgetBackgroundDirtTypePropertyValueAccessor();
        this.fontWeight = new AbstractWidgetFontWeightPropertyValueAccessor();
        this.animationTimingFunction = new AbstractWidgetAnimationTimingFunctionPropertyValueAccessor();
        this.filter = new AbstractWidgetFilterPropertyValueAccessor();
        this.hoverBoxDelay = new AbstractWidgetHoverBoxDelayPropertyValueAccessor();
        this.clearDepth = new AbstractWidgetClearDepthPropertyValueAccessor();
        this.forceVanillaFont = new AbstractWidgetForceVanillaFontPropertyValueAccessor();
        this.backgroundDirtBrightness = new AbstractWidgetBackgroundDirtBrightnessPropertyValueAccessor();
        this.AbstractWidgetResetter = new AbstractWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "renderer": {
                return this.renderer;
            }
            case "boxSizing": {
                return this.boxSizing;
            }
            case "animationDuration": {
                return this.animationDuration;
            }
            case "draggable": {
                return this.draggable;
            }
            case "interactable": {
                return this.interactable;
            }
            case "pressable": {
                return this.pressable;
            }
            case "visible": {
                return this.visible;
            }
            case "left": {
                return this.left;
            }
            case "top": {
                return this.top;
            }
            case "right": {
                return this.right;
            }
            case "bottom": {
                return this.bottom;
            }
            case "marginLeft": {
                return this.marginLeft;
            }
            case "marginTop": {
                return this.marginTop;
            }
            case "marginRight": {
                return this.marginRight;
            }
            case "marginBottom": {
                return this.marginBottom;
            }
            case "paddingLeft": {
                return this.paddingLeft;
            }
            case "paddingTop": {
                return this.paddingTop;
            }
            case "paddingRight": {
                return this.paddingRight;
            }
            case "paddingBottom": {
                return this.paddingBottom;
            }
            case "widthPrecision": {
                return this.widthPrecision;
            }
            case "heightPrecision": {
                return this.heightPrecision;
            }
            case "priorityLayer": {
                return this.priorityLayer;
            }
            case "alignmentX": {
                return this.alignmentX;
            }
            case "alignmentY": {
                return this.alignmentY;
            }
            case "translateX": {
                return this.translateX;
            }
            case "translateY": {
                return this.translateY;
            }
            case "zIndex": {
                return this.zIndex;
            }
            case "scaleX": {
                return this.scaleX;
            }
            case "scaleY": {
                return this.scaleY;
            }
            case "opacity": {
                return this.opacity;
            }
            case "mouseRenderDistance": {
                return this.mouseRenderDistance;
            }
            case "stencilX": {
                return this.stencilX;
            }
            case "stencilY": {
                return this.stencilY;
            }
            case "stencilTranslation": {
                return this.stencilTranslation;
            }
            case "writeToStencilBuffer": {
                return this.writeToStencilBuffer;
            }
            case "useFloatingPointPosition": {
                return this.useFloatingPointPosition;
            }
            case "alwaysFocused": {
                return this.alwaysFocused;
            }
            case "interactableOutside": {
                return this.interactableOutside;
            }
            case "destroyDelay": {
                return this.destroyDelay;
            }
            case "fitOuter": {
                return this.fitOuter;
            }
            case "distinct": {
                return this.distinct;
            }
            case "cancelParentHoverComponent": {
                return this.cancelParentHoverComponent;
            }
            case "renderChildren": {
                return this.renderChildren;
            }
            case "backgroundColor": {
                return this.backgroundColor;
            }
            case "backgroundColorTransitionDuration": {
                return this.backgroundColorTransitionDuration;
            }
            case "backgroundAlwaysDirt": {
                return this.backgroundAlwaysDirt;
            }
            case "backgroundDirtShift": {
                return this.backgroundDirtShift;
            }
            case "backgroundDirtType": {
                return this.backgroundDirtType;
            }
            case "fontWeight": {
                return this.fontWeight;
            }
            case "animationTimingFunction": {
                return this.animationTimingFunction;
            }
            case "filter": {
                return this.filter;
            }
            case "hoverBoxDelay": {
                return this.hoverBoxDelay;
            }
            case "clearDepth": {
                return this.clearDepth;
            }
            case "forceVanillaFont": {
                return this.forceVanillaFont;
            }
            case "backgroundDirtBrightness": {
                return this.backgroundDirtBrightness;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "renderer": {
                return true;
            }
            case "boxSizing": {
                return true;
            }
            case "animationDuration": {
                return true;
            }
            case "draggable": {
                return true;
            }
            case "interactable": {
                return true;
            }
            case "pressable": {
                return true;
            }
            case "visible": {
                return true;
            }
            case "left": {
                return true;
            }
            case "top": {
                return true;
            }
            case "right": {
                return true;
            }
            case "bottom": {
                return true;
            }
            case "marginLeft": {
                return true;
            }
            case "marginTop": {
                return true;
            }
            case "marginRight": {
                return true;
            }
            case "marginBottom": {
                return true;
            }
            case "paddingLeft": {
                return true;
            }
            case "paddingTop": {
                return true;
            }
            case "paddingRight": {
                return true;
            }
            case "paddingBottom": {
                return true;
            }
            case "widthPrecision": {
                return true;
            }
            case "heightPrecision": {
                return true;
            }
            case "priorityLayer": {
                return true;
            }
            case "alignmentX": {
                return true;
            }
            case "alignmentY": {
                return true;
            }
            case "translateX": {
                return true;
            }
            case "translateY": {
                return true;
            }
            case "zIndex": {
                return true;
            }
            case "scaleX": {
                return true;
            }
            case "scaleY": {
                return true;
            }
            case "opacity": {
                return true;
            }
            case "mouseRenderDistance": {
                return true;
            }
            case "stencilX": {
                return true;
            }
            case "stencilY": {
                return true;
            }
            case "stencilTranslation": {
                return true;
            }
            case "writeToStencilBuffer": {
                return true;
            }
            case "useFloatingPointPosition": {
                return true;
            }
            case "alwaysFocused": {
                return true;
            }
            case "interactableOutside": {
                return true;
            }
            case "destroyDelay": {
                return true;
            }
            case "fitOuter": {
                return true;
            }
            case "distinct": {
                return true;
            }
            case "cancelParentHoverComponent": {
                return true;
            }
            case "renderChildren": {
                return true;
            }
            case "backgroundColor": {
                return true;
            }
            case "backgroundColorTransitionDuration": {
                return true;
            }
            case "backgroundAlwaysDirt": {
                return true;
            }
            case "backgroundDirtShift": {
                return true;
            }
            case "backgroundDirtType": {
                return true;
            }
            case "fontWeight": {
                return true;
            }
            case "animationTimingFunction": {
                return true;
            }
            case "filter": {
                return true;
            }
            case "hoverBoxDelay": {
                return true;
            }
            case "clearDepth": {
                return true;
            }
            case "forceVanillaFont": {
                return true;
            }
            case "backgroundDirtBrightness": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.AbstractWidgetResetter;
    }
}
