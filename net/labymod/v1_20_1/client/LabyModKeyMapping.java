// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client;

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

public class LabyModKeyMapping extends enl
{
    private static final Function<Hotkey, enl> KEY_MAPPINGS;
    private final KeyAccessor keyAccessor;
    
    private LabyModKeyMapping(final String name, final KeyAccessor accessor, final String category) {
        super(name, (accessor.get() instanceof MouseButton) ? ehe.b.c : ehe.b.a, accessor.get().getId(), category);
        this.keyAccessor = accessor;
    }
    
    public static enl create(final Hotkey hotkey) {
        return LabyModKeyMapping.KEY_MAPPINGS.apply(hotkey);
    }
    
    @NotNull
    public ehe.a i() {
        final Key key = this.keyAccessor.getDefault();
        final ehe.b type = (key instanceof MouseButton) ? ehe.b.c : ehe.b.a;
        return type.a(key.getId());
    }
    
    public void b(@NotNull final ehe.a key) {
        super.b(key);
        final Key mappedKey = (key.a() == ehe.b.a || key.a() == ehe.b.b) ? KeyMapper.getKey(key.b()) : KeyMapper.getMouseButton(key.b());
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
