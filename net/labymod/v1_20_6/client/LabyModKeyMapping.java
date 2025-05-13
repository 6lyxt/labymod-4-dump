// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.client;

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

public class LabyModKeyMapping extends fff
{
    private static final Function<Hotkey, fff> KEY_MAPPINGS;
    private final KeyAccessor keyAccessor;
    
    private LabyModKeyMapping(final String name, final KeyAccessor accessor, final String category) {
        super(name, (accessor.get() instanceof MouseButton) ? eyv.b.c : eyv.b.a, accessor.get().getId(), category);
        this.keyAccessor = accessor;
    }
    
    public static fff create(final Hotkey hotkey) {
        return LabyModKeyMapping.KEY_MAPPINGS.apply(hotkey);
    }
    
    @NotNull
    public eyv.a i() {
        final Key key = this.keyAccessor.getDefault();
        final eyv.b type = (key instanceof MouseButton) ? eyv.b.c : eyv.b.a;
        return type.a(key.getId());
    }
    
    public void b(@NotNull final eyv.a key) {
        super.b(key);
        final Key mappedKey = (key.a() == eyv.b.a || key.a() == eyv.b.b) ? KeyMapper.getKey(key.b()) : KeyMapper.getMouseButton(key.b());
        this.keyAccessor.set(mappedKey);
    }
    
    public boolean l() {
        return ((MinecraftInputMapping)this).getKeyCode() == this.i().b();
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
