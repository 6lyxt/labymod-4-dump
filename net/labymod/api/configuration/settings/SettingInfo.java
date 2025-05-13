// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings;

import net.labymod.api.configuration.loader.Config;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

public class SettingInfo<M extends Member & AnnotatedElement>
{
    private final Config config;
    private final M member;
    
    public SettingInfo(final Config config, final M member) {
        this.config = config;
        this.member = member;
    }
    
    public Config config() {
        return this.config;
    }
    
    public M getMember() {
        return this.member;
    }
}
