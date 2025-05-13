// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.hud.hudwidget.item;

import net.labymod.api.Textures;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.hud.HudWidgetWidget;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;

public abstract class ItemHudWidget<T extends HudWidgetConfig> extends HudWidget<T>
{
    public static final int ITEM_SIZE = 16;
    private static final String REASON_NAME_WIDTH = "item_name_width";
    private Icon placeHolderIcon;
    private ItemStack itemStack;
    private Component itemName;
    private Component editorItemName;
    private RenderableComponent renderableItemName;
    private RenderableComponent renderableEditorItemName;
    
    protected ItemHudWidget(final String id) {
        this(id, TextHudWidgetConfig.class);
    }
    
    protected ItemHudWidget(final String id, final Class<T> configClass) {
        super(id, configClass);
        this.bindCategory(HudWidgetCategory.ITEM);
        this.bindDropzones(NamedHudWidgetDropzones.ITEMS);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        if (this.itemStack != null && !this.itemStack.isAir() && !isEditorContext) {
            this.labyAPI.minecraft().itemStackRenderer().renderItemStack(stack, this.itemStack, 0, 0, this.decorate());
        }
        else {
            if (this.placeHolderIcon == null) {
                this.placeHolderIcon = this.createPlaceholderIcon();
            }
            this.placeHolderIcon.render(stack, 0.0f, 0.0f, 16.0f, 16.0f);
            if (this.decorate()) {
                this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(stack, 1.0f, 15.0f, 15.0f, 16.0f, -1);
            }
        }
        final RenderableComponent renderableComponent = this.getRenderableItemName(isEditorContext);
        if (renderableComponent != null) {
            final float fontHeight = Laby.references().textRendererProvider().getRenderer().height();
            final float x = this.anchor.isRight() ? (-renderableComponent.getWidth() - 2.0f) : 18.0f;
            this.labyAPI.renderPipeline().componentRenderer().builder().pos(x, 8.0f - fontHeight / 2.0f + 1.5f).text(renderableComponent).render(stack);
        }
    }
    
    public RenderableComponent getRenderableItemName(final boolean isEditorContext) {
        return isEditorContext ? this.renderableEditorItemName : this.renderableItemName;
    }
    
    @Override
    public void updateSize(final HudWidgetWidget widget, final boolean editorContext, final HudSize size) {
        size.set(16, 16);
    }
    
    @Override
    public boolean isVisibleInGame() {
        return this.itemStack != null && !this.itemStack.isAir();
    }
    
    public void updateItemStack(final ItemStack itemStack, final boolean isEditorContext) {
        if (!Objects.equals(this.itemStack, itemStack)) {
            this.itemStack = itemStack;
            this.updateName(isEditorContext);
        }
    }
    
    public void updateItemName(final Component itemName, final boolean isEditorContext) {
        if (Objects.equals(isEditorContext ? this.editorItemName : this.itemName, itemName)) {
            return;
        }
        if (isEditorContext) {
            this.editorItemName = itemName;
        }
        else {
            this.itemName = itemName;
        }
        this.updateName(isEditorContext);
    }
    
    private void updateName(final boolean isEditorContext) {
        if (isEditorContext) {
            this.renderableEditorItemName = ((this.editorItemName == null) ? null : RenderableComponent.of(this.editorItemName));
        }
        else {
            this.renderableItemName = ((this.itemName == null) ? null : RenderableComponent.of(this.itemName));
        }
        if (this.isEnabled()) {
            this.requestUpdate("item_name_width");
        }
    }
    
    public Icon createPlaceholderIcon() {
        return Textures.SpriteCommon.QUESTION_MARK;
    }
    
    protected boolean decorate() {
        return false;
    }
}
