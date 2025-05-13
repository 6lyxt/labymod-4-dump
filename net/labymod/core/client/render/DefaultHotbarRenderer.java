// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render;

import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.entity.Entity;
import java.util.function.Function;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.player.Player;
import java.util.Objects;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.AtlasRenderer;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.render.RenderMode;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.resources.texture.MinecraftTextures;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.Minecraft;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.HotbarRenderer;

@Singleton
@Implements(HotbarRenderer.class)
public class DefaultHotbarRenderer implements HotbarRenderer
{
    private static boolean GUI_ATLAS;
    private static final ResourceLocation HOTBAR;
    private static final ResourceLocation HOTBAR_SELECTION;
    private static final ResourceLocation HOTBAR_OFFHAND_LEFT;
    private static final ResourceLocation HOTBAR_OFFHAND_RIGHT;
    private static final float HOTBAR_WIDTH = 182.0f;
    private static final float HOTBAR_HEIGHT = 22.0f;
    private final LabyAPI labyAPI;
    private final Minecraft minecraft;
    private final ResourceRenderContext resourceRenderContext;
    private final MinecraftTextures minecraftTextures;
    private final ItemStack[] items;
    private float x;
    private float y;
    private RenderMode renderMode;
    private Layout layout;
    
    @Inject
    public DefaultHotbarRenderer(final LabyAPI labyAPI, final ItemStackFactory itemStackFactory) {
        this.renderMode = RenderMode.REAL;
        this.layout = Layout.HORIZONTAL;
        this.labyAPI = labyAPI;
        this.minecraft = this.labyAPI.minecraft();
        this.resourceRenderContext = this.labyAPI.renderPipeline().renderContexts().resourceRenderContext();
        this.minecraftTextures = this.labyAPI.minecraft().textures();
        (this.items = new ItemStack[9])[0] = itemStackFactory.create("minecraft", "diamond_sword");
        this.items[1] = itemStackFactory.create("minecraft", "bow");
        this.items[8] = itemStackFactory.create("minecraft", "arrow", 42);
    }
    
    @Override
    public HotbarRenderer mode(final RenderMode renderMode) {
        this.renderMode = renderMode;
        return this;
    }
    
    @Override
    public HotbarRenderer pos(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    @Override
    public HotbarRenderer layout(final Layout layout) {
        this.layout = layout;
        return this;
    }
    
    @Override
    public void render(final Stack stack) {
        switch (this.renderMode) {
            case DUMMY: {
                this.renderDummy(stack);
                break;
            }
            case REAL: {
                this.renderReal(stack);
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.renderMode));
            }
        }
    }
    
    private void renderDummy(final Stack stack) {
        final float x = this.x;
        final float selectedPosition = 60.0f;
        final float xPosition = (this.layout == Layout.HORIZONTAL) ? selectedPosition : 0.0f;
        final float yPosition = (this.layout == Layout.VERTICAL) ? selectedPosition : 0.0f;
        final ResourceLocation widgetsTexture = this.minecraftTextures.widgetsTexture();
        final TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(widgetsTexture);
        final AtlasRenderer renderer = ResourceRenderContext.ATLAS_RENDERER;
        this.resourceRenderContext.begin(stack, widgetsTexture);
        this.renderHotbar(renderer, atlas, x, xPosition, yPosition);
        this.resourceRenderContext.uploadToBuffer();
        this.renderHotbarItems(stack, x, this.y, slot -> this.items[slot]);
    }
    
    private void renderReal(final Stack stack) {
        final Player player = this.cameraPlayer();
        if (player == null) {
            return;
        }
        float x = this.x;
        final ItemStack offHandItemStack = player.getOffHandItemStack();
        final MainHand offHand = this.labyAPI.minecraft().options().mainHand().opposite();
        if (offHand == MainHand.LEFT) {
            x += 29.0f;
        }
        final ClientPlayer clientPlayer = (ClientPlayer)player;
        final Inventory inventory = clientPlayer.inventory();
        final float selectedPosition = inventory.getSelectedIndex() * 20.0f;
        final float xPosition = (this.layout == Layout.HORIZONTAL) ? selectedPosition : 0.0f;
        final float yPosition = (this.layout == Layout.VERTICAL) ? selectedPosition : 0.0f;
        final ResourceLocation widgetsTexture = this.minecraftTextures.widgetsTexture();
        final TextureAtlas atlas = Laby.references().atlasRegistry().getAtlas(widgetsTexture);
        final AtlasRenderer renderer = ResourceRenderContext.ATLAS_RENDERER;
        this.resourceRenderContext.begin(stack, widgetsTexture);
        this.renderHotbar(renderer, atlas, x, xPosition, yPosition);
        if (!offHandItemStack.isAir()) {
            if (offHand == MainHand.LEFT) {
                renderer.blitSprite(this.resourceRenderContext, atlas, DefaultHotbarRenderer.HOTBAR_OFFHAND_LEFT, (int)(x - 29.0f), (int)(this.y - 1.0f), 29, 24, -1);
            }
            else {
                renderer.blitSprite(this.resourceRenderContext, atlas, DefaultHotbarRenderer.HOTBAR_OFFHAND_RIGHT, (int)(x + 182.0f), (int)(this.y - 1.0f), 29, 24, -1);
            }
        }
        this.resourceRenderContext.uploadToBuffer();
        final float x2 = x;
        final float y = this.y;
        final Inventory obj = inventory;
        Objects.requireNonNull(obj);
        this.renderHotbarItems(stack, x2, y, obj::itemStackAt);
        if (!offHandItemStack.isAir()) {
            if (offHand == MainHand.LEFT) {
                this.labyAPI.minecraft().itemStackRenderer().renderItemStack(stack, offHandItemStack, (int)(x - 26.0f), (int)(this.y + 3.0f));
            }
            else {
                this.labyAPI.minecraft().itemStackRenderer().renderItemStack(stack, offHandItemStack, (int)(x + 182.0f + 10.0f), (int)(this.y + 3.0f));
            }
        }
    }
    
    @Override
    public float getWidth() {
        return 182.0f;
    }
    
    @Override
    public float getHeight() {
        return 22.0f;
    }
    
    private void renderHotbarItems(final Stack stack, final float x, final float y, final Function<Integer, ItemStack> itemFunction) {
        Objects.requireNonNull(itemFunction, "itemFunction must be not null");
        for (int slot = 0; slot < 9; ++slot) {
            final ItemStack item = itemFunction.apply(slot);
            if (item != null) {
                final int itemX = (int)(x + (slot * 20 + 3));
                final int itemY = (int)(y + 3.0f);
                this.labyAPI.minecraft().itemStackRenderer().renderItemStack(stack, item, itemX, itemY);
            }
        }
    }
    
    private Player cameraPlayer() {
        final Entity cameraEntity = this.minecraft.getCameraEntity();
        return (cameraEntity instanceof Player) ? ((Player)cameraEntity) : null;
    }
    
    private void renderHotbar(final AtlasRenderer renderer, final TextureAtlas atlas, final float x, final float xPosition, final float yPosition) {
        renderer.blitSprite(this.resourceRenderContext, atlas, DefaultHotbarRenderer.HOTBAR, (int)x, (int)this.y, 182, 22, -1);
        renderer.blitSprite(this.resourceRenderContext, atlas, DefaultHotbarRenderer.HOTBAR_SELECTION, (int)(x - 1.0f + xPosition), (int)(this.y - 1.0f + yPosition), 24, DefaultHotbarRenderer.GUI_ATLAS ? 23 : 24, -1);
    }
    
    static {
        DefaultHotbarRenderer.GUI_ATLAS = MinecraftVersions.V23w31a.orNewer();
        HOTBAR = ResourceLocation.create("minecraft", "hud/hotbar");
        HOTBAR_SELECTION = ResourceLocation.create("minecraft", "hud/hotbar_selection");
        HOTBAR_OFFHAND_LEFT = ResourceLocation.create("minecraft", "hud/hotbar_offhand_left");
        HOTBAR_OFFHAND_RIGHT = ResourceLocation.create("minecraft", "hud/hotbar_offhand_right");
    }
}
