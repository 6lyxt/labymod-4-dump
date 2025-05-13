// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin;

import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import java.util.Locale;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.widget.widgets.input.SimpleButtonWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.SkinActivity;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class SkinPreviewWidget extends AbstractWidget<Widget>
{
    private final SkinActivity skinActivity;
    private final Skin skin;
    
    public SkinPreviewWidget(final SkinActivity skinActivity, final Skin skin) {
        this.skinActivity = skinActivity;
        this.skin = skin;
        this.lazy = true;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final IconWidget iconWidget = new IconWidget(this.skin.previewIcon());
        iconWidget.setCleanupOnDispose(true);
        ((AbstractWidget<IconWidget>)this).addChild(iconWidget);
        final SimpleButtonWidget applyButton = new SimpleButtonWidget(Component.translatable(this.skinActivity.getTranslationKeyPrefix() + "skin.preview", new Component[0]));
        applyButton.addId("button", "apply-button");
        applyButton.setAttributeState(AttributeState.ENABLED, true);
        applyButton.setPressable(() -> this.skinActivity.setModelTexture(this.skin));
        ((AbstractWidget<SimpleButtonWidget>)this).addChild(applyButton);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (mouseButton == MouseButton.RIGHT && !this.hasContextMenu()) {
            final ContextMenu contextMenu = this.createContextMenu();
            contextMenu.addEntry(ContextMenuEntry.builder().text(Component.translatable(this.skinActivity.getTranslationKeyPrefix() + "skin.open", new Component[0])).clickHandler(entry -> {
                this.labyAPI.minecraft().chatExecutor().openUrl(String.format(Locale.ROOT, "https://laby.net/skin/%s", this.skin.getImageHash()));
                return true;
            }).build());
        }
        return super.mouseClicked(mouse, mouseButton);
    }
}
