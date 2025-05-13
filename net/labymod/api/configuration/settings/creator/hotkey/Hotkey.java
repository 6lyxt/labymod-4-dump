// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.hotkey;

import java.util.function.BooleanSupplier;
import net.labymod.api.client.gui.screen.key.KeyAccessor;

record Hotkey(String category, String translationKey, KeyAccessor accessor, BooleanSupplier visibility) {}
