// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mojang.model;

import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface MojangModelService
{
    Model getPlayerModel(final MinecraftServices.SkinVariant p0, final ResourceLocation p1);
    
    Model createModel(final ResourceLocation p0, final ResourceLocation p1);
}
