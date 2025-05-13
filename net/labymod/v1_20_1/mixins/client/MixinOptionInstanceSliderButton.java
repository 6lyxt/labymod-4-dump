// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client;

import net.labymod.v1_20_1.client.gui.WrappedIntRangeBase;
import net.labymod.api.util.StringUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_1.mixins.client.gui.components.MixinAbstractSliderButton;

@Mixin({ enq.i.class })
public class MixinOptionInstanceSliderButton<T> extends MixinAbstractSliderButton
{
    @Nullable
    private enq.g labyMod$wrappedIntRangeBase;
    @Shadow
    @Final
    private enq.k<T> i;
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
    
    private enq.g labyMod$createWrappedIntRangeBase() {
        Class<?> cls = this.i.getClass();
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
        if (enq.g.class.isAssignableFrom(cls)) {
            return (enq.g)new WrappedIntRangeBase((enq.g)this.i);
        }
        return null;
    }
}
