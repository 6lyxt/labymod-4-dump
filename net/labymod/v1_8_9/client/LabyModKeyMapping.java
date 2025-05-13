// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client;

import net.labymod.api.Laby;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.KeyAccessor;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import java.util.function.Function;

public class LabyModKeyMapping extends avb
{
    private static final Function<Hotkey, avb> KEY_MAPPINGS;
    private final KeyAccessor keyAccessor;
    
    private LabyModKeyMapping(final String name, final KeyAccessor accessor, final String category) {
        super(name, accessor.get().getId(), category);
        this.keyAccessor = accessor;
    }
    
    public static avb create(final Hotkey hotkey) {
        return LabyModKeyMapping.KEY_MAPPINGS.apply(hotkey);
    }
    
    public int h() {
        final Key defaultKey = this.keyAccessor.getDefault();
        if (defaultKey instanceof MouseButton) {
            return defaultKey.getId() - 100;
        }
        return defaultKey.getId();
    }
    
    public int i() {
        final Key key = this.keyAccessor.get();
        if (key instanceof MouseButton) {
            return key.getId() - 100;
        }
        return key.getId();
    }
    
    public void b(int key) {
        super.b(key);
        if (key < 0) {
            key += 100;
        }
        this.keyAccessor.set(KeyMapper.getKey(key));
    }
    
    static {
        KEY_MAPPINGS = Laby.references().functionMemoizeStorage().memoize(hotkey -> {
            final String category = hotkey.category();
            final LabyModKeyMapping mapping = new LabyModKeyMapping(I18n.translate(hotkey.translationKey(), new Object[0]), hotkey.accessor(), category);
            ((MinecraftInputMapping)mapping).addCategory(category);
            return mapping;
        });
    }
}
