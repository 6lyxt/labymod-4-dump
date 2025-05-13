// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.entity;

import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.component.Component;
import java.util.Objects;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.entity.player.tag.event.NameTagBackgroundRenderEvent;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.v1_8_9.client.renderer.EntityRendererAccessor;
import org.lwjgl.opengl.GL11;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.TagType;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.labymod.api.util.math.MathHelper;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.entity.EntityRenderEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ biv.class })
public abstract class MixinRender
{
    @Shadow
    @Final
    protected biu b;
    
    @Shadow
    public abstract biu d();
    
    @Shadow
    public abstract avn c();
    
    @Inject(method = { "doRender" }, at = { @At("HEAD") })
    private void labyMod$fireEntityRenderPre(final pk p_doRender_1_, final double p_doRender_2_, final double p_doRender_4_, final double p_doRender_6_, final float p_doRender_8_, final float p_doRender_9_, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity)p_doRender_1_, Phase.PRE));
    }
    
    @Inject(method = { "doRender" }, at = { @At("HEAD") })
    private void labyMod$fireEntityRenderPost(final pk p_doRender_1_, final double p_doRender_2_, final double p_doRender_4_, final double p_doRender_6_, final float p_doRender_8_, final float p_doRender_9_, final CallbackInfo ci) {
        Laby.fireEvent(new EntityRenderEvent((Entity)p_doRender_1_, Phase.POST));
    }
    
    @ModifyConstant(method = { "renderLivingLabel" }, constant = { @Constant(intValue = 8) })
    private int labyMod$modifyNameTagBackgroundHeight(final int nameTagHeight) {
        return MathHelper.ceil(Laby.labyAPI().renderPipeline().textRenderer().height());
    }
    
    @Overwrite
    protected void a(final pk entity, String name, final double x, final double y, final double z, final int distance) {
        final double distanceToEntity = entity.h(this.b.c);
        if (distanceToEntity <= distance * distance) {
            final TagType type = ((Entity)entity).nameTagType();
            if (type == TagType.MAIN_TAG && entity instanceof Player) {
                name = labyMod$firePlayerNameTagRenderEvent((Player)entity, name, type);
            }
            if (name == null) {
                return;
            }
            final avn font = this.c();
            final float height = 1.6f;
            final float scale = 0.016666668f * height;
            bfl.E();
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            bfl.b(x, y + entity.K + 0.5, z);
            final EntityRendererAccessor accessor = (EntityRendererAccessor)ave.A().o;
            final boolean thirdPersonFront = this.b.g.aB == 2;
            final float viewY = thirdPersonFront ? (accessor.getCameraYaw() + 180.0f) : accessor.getCameraYaw();
            final float viewX = thirdPersonFront ? (-accessor.getCameraPitch()) : accessor.getCameraPitch();
            bfl.b(-viewY, 0.0f, 1.0f, 0.0f);
            bfl.b(viewX, 1.0f, 0.0f, 0.0f);
            bfl.a(-scale, -scale, scale);
            final boolean sneaking = entity.av();
            if (sneaking) {
                bfl.b(0.0f, 9.374999f, 0.0f);
            }
            bfl.f();
            bfl.a(false);
            if (!sneaking) {
                bfl.i();
            }
            int nameWidth = font.a(name);
            final Stack defaultStack = VersionedStackProvider.DEFAULT_STACK;
            Laby.references().renderEnvironmentContext().setPackedLight(MinecraftUtil.getPackedLight(entity));
            final LabyAPI labyAPI = Laby.labyAPI();
            if (!(entity instanceof um)) {
                final GFXBridge gfx = labyAPI.gfxRenderPipeline().gfx();
                gfx.storeBlaze3DStates();
                labyAPI.tagRegistry().render(defaultStack, (Entity)entity, nameWidth + 2.0f, type);
                gfx.restoreBlaze3DStates();
            }
            bfl.l();
            bfl.a(770, 771, 1, 0);
            final bfx tessellator = bfx.a();
            final bfd bufferBuilder = tessellator.c();
            int nameHeight = 0;
            if (name.equals("deadmau5")) {
                nameHeight = -10;
            }
            bfl.x();
            final NameTagBackgroundRenderEvent bgEvent = Laby.fireEvent(NameTagBackgroundRenderEvent.singleton());
            nameWidth /= (int)2.0f;
            if (!bgEvent.isCancelled()) {
                bufferBuilder.a(7, bms.f);
                final int backgroundColor = bgEvent.getColor();
                final ColorFormat colorFormat = ColorFormat.ARGB32;
                final float red = colorFormat.normalizedRed(backgroundColor);
                final float green = colorFormat.normalizedGreen(backgroundColor);
                final float blue = colorFormat.normalizedBlue(backgroundColor);
                bufferBuilder.b((double)(-nameWidth - 1), (double)(-1 + nameHeight), 0.006).a(red, green, blue, 0.25f).d();
                bufferBuilder.b((double)(-nameWidth - 1), (double)(8 + nameHeight), 0.006).a(red, green, blue, 0.25f).d();
                bufferBuilder.b((double)(nameWidth + 1), (double)(8 + nameHeight), 0.006).a(red, green, blue, 0.25f).d();
                bufferBuilder.b((double)(nameWidth + 1), (double)(-1 + nameHeight), 0.006).a(red, green, blue, 0.25f).d();
                tessellator.b();
            }
            bfl.w();
            final int sneakColor = 553648127;
            if (!sneaking) {
                font.a(name, -font.a(name) / 2, nameHeight, sneakColor);
                bfl.j();
            }
            bfl.a(true);
            font.a(name, -font.a(name) / 2, nameHeight, sneaking ? sneakColor : -1);
            bfl.e();
            bfl.k();
            bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
            bfl.F();
        }
    }
    
    private static String labyMod$firePlayerNameTagRenderEvent(final Player entity, String name, final TagType type) {
        final ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        final Component preEventNameTag = componentMapper.fromMinecraftComponent(new fa(name));
        final PlayerNameTagRenderEvent event = new PlayerNameTagRenderEvent(PlayerNameTagRenderEvent.Context.PLAYER_RENDER, entity.networkPlayerInfo(), preEventNameTag, type);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            entity.setNameTagType(null);
            return null;
        }
        Component postEventNameTag = event.nameTag();
        if (!Objects.equals(postEventNameTag, preEventNameTag)) {
            postEventNameTag = postEventNameTag.append(PlayerNameTagRenderEvent.EDITED_COMPONENT);
            final eu customNameTag = (eu)componentMapper.toMinecraftComponent(postEventNameTag);
            name = customNameTag.d();
        }
        return name;
    }
}
