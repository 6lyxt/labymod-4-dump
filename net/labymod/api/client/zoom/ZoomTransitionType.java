// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.zoom;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;

public enum ZoomTransitionType
{
    NO_TRANSITION((CubicBezier)null), 
    LINEAR(CubicBezier.LINEAR), 
    EASE_IN(CubicBezier.EASE_IN), 
    EASE_OUT(CubicBezier.EASE_OUT), 
    EASE_IN_OUT(CubicBezier.EASE_IN_OUT), 
    BOUNCE(CubicBezier.BOUNCE);
    
    private final CubicBezier cubicBezier;
    
    private ZoomTransitionType(final CubicBezier cubicBezier) {
        this.cubicBezier = cubicBezier;
    }
    
    public CubicBezier getCubicBezier() {
        return this.cubicBezier;
    }
}
