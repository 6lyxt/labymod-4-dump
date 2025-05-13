// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.debug;

import net.labymod.api.client.gfx.imgui.control.ControlEntryRegistry;
import net.labymod.api.event.Event;

record ImGuiInitializeEvent(ControlEntryRegistry registry) implements Event {}
