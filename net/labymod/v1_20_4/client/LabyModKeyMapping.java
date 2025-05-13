// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client;

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

public class LabyModKeyMapping extends evg
{
    private static final Function<Hotkey, evg> KEY_MAPPINGS;
    private final KeyAccessor keyAccessor;
    
    private LabyModKeyMapping(final String name, final KeyAccessor accessor, final String category) {
        super(name, (accessor.get() instanceof MouseButton) ? eow.b.c : eow.b.a, accessor.get().getId(), category);
        this.keyAccessor = accessor;
    }
    
    public static evg create(final Hotkey hotkey) {
        return LabyModKeyMapping.KEY_MAPPINGS.apply(hotkey);
    }
    
    @NotNull
    public eow.a i() {
        final Key key = this.keyAccessor.getDefault();
        final eow.b type = (key instanceof MouseButton) ? eow.b.c : eow.b.a;
        return type.a(key.getId());
    }
    
    public void b(@NotNull final eow.a key) {
        super.b(key);
        final Key mappedKey = (key.a() == eow.b.a || key.a() == eow.b.b) ? KeyMapper.getKey(key.b()) : KeyMapper.getMouseButton(key.b());
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
