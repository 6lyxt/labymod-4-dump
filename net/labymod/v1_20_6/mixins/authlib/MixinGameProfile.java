// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.authlib;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.mojang.Property;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.v1_20_6.mojang.WrappedPropertyMap;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.metadata.Metadata;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import com.mojang.authlib.properties.PropertyMap;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.mojang.GameProfile;

@Mixin({ com.mojang.authlib.GameProfile.class })
public abstract class MixinGameProfile implements GameProfile
{
    @Mutable
    @Final
    @Shadow(remap = false)
    private PropertyMap properties;
    private transient Metadata labyMod$metadata;
    
    public MixinGameProfile() {
        this.properties = new PropertyMap();
    }
    
    @Inject(method = { "<init>*" }, at = { @At("TAIL") }, remap = false)
    private void labyMod$wrapProperties(final CallbackInfo ci) {
        this.properties = new WrappedPropertyMap();
    }
    
    @Shadow(remap = false)
    public abstract UUID getId();
    
    @Shadow(remap = false)
    public abstract String getName();
    
    @Override
    public UUID getUniqueId() {
        return this.getId();
    }
    
    @Override
    public String getUsername() {
        return this.getName();
    }
    
    @NotNull
    @Override
    public Map<String, Collection<Property>> getProperties() {
        return ((WrappedPropertyMap)this.properties).getProperties();
    }
    
    @Override
    public Metadata metadata() {
        if (this.labyMod$metadata == null) {
            this.labyMod$metadata = Metadata.create();
        }
        return this.labyMod$metadata;
    }
    
    @Override
    public void metadata(final Metadata metadata) {
        this.labyMod$metadata = metadata;
    }
}
