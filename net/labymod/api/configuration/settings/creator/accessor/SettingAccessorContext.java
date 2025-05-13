// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings.creator.accessor;

import java.lang.reflect.Field;
import net.labymod.api.configuration.settings.creator.MemberInspector;

record SettingAccessorContext(MemberInspector element, Field field) {}
