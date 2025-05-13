// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.mixins.client;

import net.labymod.v1_19_4.client.gui.WrappedIntRangeBase;
import net.labymod.api.util.StringUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_4.mixins.client.gui.components.MixinAbstractSliderButton;

@Mixin({ emk.i.class })
public class MixinOptionInstanceSliderButton<T> extends MixinAbstractSliderButton
{
    @Nullable
    private emk.g labyMod$wrappedIntRangeBase;
    @Shadow
    @Final
    private emk.k<T> m;
    private boolean labyMod$integer;
    
    @Override
    public float getMinValue() {
        if (this.labyMod$wrappedIntRangeBase == null) {
            this.labyMod$wrappedIntRangeBase = this.labyMod$createWrappedIntRangeBase();
        }
        if (this.labyMod$wrappedIntRangeBase != null) {
            this.labyMod$integer = true;
            return (float)this.labyMod$wrappedIntRangeBase.d();
        }
        return 0.0f;
    }
    
    @Override
    public float getMaxValue() {
        if (this.labyMod$wrappedIntRangeBase == null) {
            this.labyMod$wrappedIntRangeBase = this.labyMod$createWrappedIntRangeBase();
        }
        if (this.labyMod$wrappedIntRangeBase != null) {
            this.labyMod$integer = true;
            return (float)this.labyMod$wrappedIntRangeBase.b();
        }
        return 1.0f;
    }
    
    @Override
    public float getSteps() {
        return this.labyMod$integer ? 1.0f : 0.0f;
    }
    
    private emk.g labyMod$createWrappedIntRangeBase() {
        Class<?> cls = this.m.getClass();
        if (cls.isAnonymousClass()) {
            String name = cls.getName();
            final int lastIndex = name.lastIndexOf(36);
            if (lastIndex != -1) {
                name = name.substring(lastIndex + 1);
                if (StringUtil.isNumeric(name)) {
                    cls = cls.getEnclosingClass();
                }
            }
        }
        if (emk.g.class.isAssignableFrom(cls)) {
            return (emk.g)new WrappedIntRangeBase((emk.g)this.m);
        }
        return null;
    }
}
