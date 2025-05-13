// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.resources;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Event;

record ReleaseTextureEvent(ResourceLocation location) implements Event {}
