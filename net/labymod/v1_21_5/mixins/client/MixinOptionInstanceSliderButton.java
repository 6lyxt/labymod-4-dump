// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client;

import net.labymod.v1_21_5.client.gui.WrappedIntRangeBase;
import net.labymod.api.util.StringUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_5.mixins.client.gui.components.MixinAbstractSliderButton;

@Mixin({ fqt.i.class })
public abstract class MixinOptionInstanceSliderButton<T> extends MixinAbstractSliderButton
{
    @Nullable
    private fqt.g labyMod$wrappedIntRangeBase;
    @Shadow
    @Final
    private fqt.k<T> e;
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
    
    private fqt.g labyMod$createWrappedIntRangeBase() {
        Class<?> cls = this.e.getClass();
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
        if (fqt.g.class.isAssignableFrom(cls)) {
            return (fqt.g)new WrappedIntRangeBase((fqt.g)this.e);
        }
        return null;
    }
}
