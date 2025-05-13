// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client;

import net.labymod.api.Laby;
import net.labymod.api.util.I18n;
import net.labymod.api.client.options.MinecraftInputMapping;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.KeyAccessor;
import net.labymod.api.configuration.settings.creator.hotkey.Hotkey;
import java.util.function.Function;

public class LabyModKeyMapping extends dyo
{
    private static final Function<Hotkey, dyo> KEY_MAPPINGS;
    private final KeyAccessor keyAccessor;
    
    private LabyModKeyMapping(final String name, final KeyAccessor accessor, final String category) {
        super(name, (accessor.get() instanceof MouseButton) ? dsh.b.c : dsh.b.a, accessor.get().getId(), category);
        this.keyAccessor = accessor;
    }
    
    public static dyo create(final Hotkey hotkey) {
        return LabyModKeyMapping.KEY_MAPPINGS.apply(hotkey);
    }
    
    @NotNull
    public dsh.a h() {
        final Key key = this.keyAccessor.getDefault();
        final dsh.b type = (key instanceof MouseButton) ? dsh.b.c : dsh.b.a;
        return type.a(key.getId());
    }
    
    public void b(@NotNull final dsh.a key) {
        super.b(key);
        final Key mappedKey = (key.a() == dsh.b.a || key.a() == dsh.b.b) ? KeyMapper.getKey(key.b()) : KeyMapper.getMouseButton(key.b());
        this.keyAccessor.set(mappedKey);
    }
    
    public boolean k() {
        return ((MinecraftInputMapping)this).getKeyCode() == this.h().b();
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
