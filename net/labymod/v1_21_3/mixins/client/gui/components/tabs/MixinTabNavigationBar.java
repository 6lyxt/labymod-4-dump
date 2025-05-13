// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.gui.components.tabs;

import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.Laby;
import net.labymod.api.volt.callback.InsertInfo;
import java.util.function.Function;
import java.util.HashMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import com.google.common.collect.ImmutableList;
import java.util.Map;
import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_3.client.gui.TabNavigationBarAccessor;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@Mixin({ fql.class })
public abstract class MixinTabNavigationBar<T extends AbstractWidget<?>> extends fqb implements ConvertableMinecraftWidget<T>, TabNavigationBarAccessor
{
    private final WidgetWatcher<T> watcher;
    private final Map<fqj, VersionedTab> versionedTabs;
    @Shadow
    @Final
    private ImmutableList<fqj> i;
    @Shadow
    @Final
    private fqk h;
    private fqj labyMod$currentTab;
    
    public MixinTabNavigationBar() {
        this.watcher = new WidgetWatcher<T>();
        this.versionedTabs = new HashMap<fqj, VersionedTab>();
    }
    
    public ImmutableList<fqj> getTabs() {
        return this.i;
    }
    
    public fqk getTabManager() {
        return this.h;
    }
    
    public WidgetWatcher<T> getWatcher() {
        return this.watcher;
    }
    
    public VersionedTab versionedTabOf(final fqj tab) {
        return this.versionedTabs.computeIfAbsent(tab, VersionedTab::new);
    }
    
    @Insert(method = { "render" }, at = @At("HEAD"), cancellable = true)
    public void render(final fns graphics, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        final fqj tab = this.h.a();
        if (this.labyMod$currentTab != tab) {
            final int previousIndex = this.i.indexOf((Object)this.labyMod$currentTab);
            final int newIndex = this.i.indexOf((Object)tab);
            final boolean transitionToRight = newIndex > previousIndex;
            this.versionedTabOf(this.labyMod$currentTab).setTransitionToRight(transitionToRight);
            this.versionedTabOf(tab).setTransitionToRight(transitionToRight);
        }
        this.labyMod$currentTab = tab;
        this.watcher.update(this, tab);
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final boolean rendered = this.watcher.render(((VanillaStackAccessor)graphics.c()).stack(), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
    
    public boolean a(final double mouseX, final double mouseY, final int button) {
        final AbstractWidget<?> widget = this.watcher.getWidget();
        if (widget == null || !widget.bounds().isInRectangle(BoundsType.BORDER, (float)mouseX, (float)mouseY)) {
            return super.a(mouseX, mouseY, button);
        }
        final AbstractWidgetConverter widgetConverter = this.watcher.getWidgetConverter();
        if (widgetConverter == null) {
            return super.a(mouseX, mouseY, button);
        }
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
            return mouseButton != null && widgetConverter.mouseClicked(widget, mouse, mouseButton);
        });
    }
}
