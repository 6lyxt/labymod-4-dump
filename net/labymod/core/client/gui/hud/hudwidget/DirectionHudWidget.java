// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.Iterator;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.world.object.WorldObject;
import java.util.Map;
import net.labymod.core.client.world.object.WorldObjectOverlay;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.gfx.pipeline.util.Scissor;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.util.Color;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.binding.dropzone.HudWidgetDropzone;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.client.gui.hud.hudwidget.ResizeableHudWidget;

@IntroducedIn("4.1.0")
@SpriteSlot(x = 2, y = 4)
public class DirectionHudWidget extends ResizeableHudWidget<DirectionHudWidgetConfig>
{
    public DirectionHudWidget() {
        super("direction", DirectionHudWidgetConfig.class);
        this.bindCategory(HudWidgetCategory.INGAME);
        this.bindDropzones(NamedHudWidgetDropzones.DIRECTION);
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final float width, final float height) {
        final Entity entity = this.labyAPI.minecraft().getCameraEntity();
        float degree = (entity == null) ? 35.0f : MathHelper.wrapDegrees(entity.getRotationYaw());
        if (degree <= 0.0f) {
            degree += 360.0f;
        }
        if (stack != null) {
            switch (((DirectionHudWidgetConfig)this.config).design().get().ordinal()) {
                case 0: {
                    this.renderMinecraft(stack, degree);
                    break;
                }
                case 1: {
                    this.renderPUPG(stack, degree);
                    break;
                }
                case 2: {
                    this.renderCOD(stack, degree);
                    break;
                }
                case 3: {
                    this.renderCSGO(stack, degree);
                    break;
                }
                case 4: {
                    this.renderGeo(stack, degree);
                    break;
                }
            }
        }
    }
    
    private void renderMinecraft(final Stack stack, final float degree) {
        final Scissor scissor = this.labyAPI.gfxRenderPipeline().scissor();
        try {
            scissor.push(stack, 0.0f, 0.0f, this.width, this.height);
            final RenderPipeline pipeline = this.labyAPI.renderPipeline();
            final float centerX = this.width / 2.0f;
            final float spacing = ((DirectionHudWidgetConfig)this.config).spacing().get();
            final int accentColor = ((DirectionHudWidgetConfig)this.config).accentColor().get().get();
            final int cutThreshold = 10;
            for (int phase = 0; phase < 2; ++phase) {
                BatchRectangleRenderer rectangleRenderer = null;
                if (phase == 0) {
                    rectangleRenderer = pipeline.rectangleRenderer().beginBatch(stack);
                    rectangleRenderer.pos(0.0f, 0.0f).size(this.width, this.height).color(-14606047).build();
                    for (int i = 0; i < this.height / 4.0f; ++i) {
                        rectangleRenderer.pos(centerX - 0.5f, this.height - i * 4 - 3.0f).size(1.0f, 3.0f).color(accentColor).build();
                    }
                }
                for (int layer = 0; layer < 2; ++layer) {
                    for (float rot = 0.0f; rot < 360.0f; rot += 11.25) {
                        final float offset = (rot - degree + layer * 360 * ((degree < 180.0f) ? -1 : 1)) * spacing;
                        final float posX = centerX + offset;
                        if (posX >= -cutThreshold) {
                            if (posX <= this.width + cutThreshold) {
                                final boolean isCardinalPoint = rot % 45.0f == 0.0f;
                                final boolean isMiddleOfCardinal = rot % 22.5 == 0.0;
                                if (phase == 1 && isCardinalPoint) {
                                    this.renderText(stack, this.toCardinal(rot), posX + 1.0f, this.height / 2.0f - 1.0f, 1.0f, -1, HorizontalAlignment.CENTER);
                                }
                                if (phase == 0) {
                                    rectangleRenderer.pos(posX - 0.5f, 0.0f).size(1.0f, isCardinalPoint ? 4.0f : (isMiddleOfCardinal ? 2.0f : 1.0f)).color(-3750202).build();
                                }
                            }
                        }
                    }
                }
                if (phase == 0) {
                    rectangleRenderer.upload();
                    this.renderWorldObjects(stack, degree, false, 0.0f, 0);
                }
            }
        }
        finally {
            scissor.pop();
        }
    }
    
    private void renderPUPG(final Stack stack, final float degree) {
        final RenderPipeline pipeline = this.labyAPI.renderPipeline();
        final float centerX = this.width / 2.0f;
        final float spacing = ((DirectionHudWidgetConfig)this.config).spacing().get();
        final int accentColor = ((DirectionHudWidgetConfig)this.config).accentColor().get().get();
        final int backgroundColor = ((DirectionHudWidgetConfig)this.config).primaryBackgroundColor().get().get();
        for (int phase = 0; phase < 2; ++phase) {
            BatchRectangleRenderer builder = null;
            if (phase == 0) {
                builder = pipeline.rectangleRenderer().beginBatch(stack);
                final float fadeWidth = this.width / 8.0f;
                builder.pos(0.0f, 0.0f).size(fadeWidth, this.height).gradientHorizontal(backgroundColor & 0xFFFFFF, backgroundColor).build();
                builder.pos(fadeWidth, 0.0f).size(this.width - fadeWidth * 2.0f, this.height).color(backgroundColor).build();
                builder.pos(this.width - fadeWidth, 0.0f).size(fadeWidth, this.height).gradientHorizontal(backgroundColor, backgroundColor & 0xFFFFFF).build();
            }
            for (int layer = 0; layer < 2; ++layer) {
                for (int rot = 0; rot < 360; ++rot) {
                    if (rot % 5 == 0) {
                        final float offset = (rot - degree + layer * 360 * ((degree < 180.0f) ? -1 : 1)) * spacing;
                        final float posX = centerX + offset;
                        if (posX >= 0.0f) {
                            if (posX <= this.width) {
                                final boolean isCardinalPoint = rot % 45 == 0;
                                int alphaMask = this.computeAlphaMask(this.width, offset, 100);
                                if (Math.abs(offset) < 30.0f) {
                                    float opacity = (alphaMask >>> 24) / 255.0f;
                                    opacity *= MathHelper.square(offset / 30.0f);
                                    alphaMask = ((int)(opacity * 255.0f) << 24 | 0xFFFFFF);
                                }
                                if (phase == 1) {
                                    this.renderText(stack, isCardinalPoint ? this.toCardinal((float)rot) : String.valueOf(rot), posX + (isCardinalPoint ? 0.5f : 0.25f), this.height / 2.0f + 1.0f, isCardinalPoint ? 1.0f : 0.5f, alphaMask, HorizontalAlignment.CENTER);
                                }
                                if (phase == 0) {
                                    builder.pos(posX - (isCardinalPoint ? 0.5f : 0.25f), this.height / 2.0f - (isCardinalPoint ? 5 : 3) / 2.0f - 2.0f).size(isCardinalPoint ? 1.0f : 0.5f, isCardinalPoint ? 5.0f : 3.0f).color((isCardinalPoint ? -1 : 1157627903) & alphaMask).build();
                                }
                            }
                        }
                    }
                }
            }
            if (phase == 0) {
                builder.upload();
                this.renderWorldObjects(stack, degree, true, this.width, 100);
            }
        }
        this.renderText(stack, String.valueOf((int)degree), centerX + 1.0f, this.height / 2.0f + 1.0f, 1.0f, -1, HorizontalAlignment.CENTER);
        pipeline.triangleRenderer().render(stack, centerX + 2.0f, 1.0f, centerX - 2.0f, 1.0f, centerX, 5.0f, accentColor, true);
    }
    
    private void renderCOD(final Stack stack, final float degree) {
        final RectangleRenderer rectangleRenderer = this.labyAPI.renderPipeline().rectangleRenderer();
        final float centerX = this.width / 2.0f;
        final float fadeWidth = this.width / 8.0f;
        final int primaryBackgroundColor = ((DirectionHudWidgetConfig)this.config).primaryBackgroundColor().get().get();
        final int secondaryBackgroundColor = ((DirectionHudWidgetConfig)this.config).secondaryBackgroundColor().get().get();
        final int accentColor = ((DirectionHudWidgetConfig)this.config).accentColor().get().get();
        final float spacing = ((DirectionHudWidgetConfig)this.config).spacing().get();
        for (int phase = 0; phase < 2; ++phase) {
            BatchRectangleRenderer builder = null;
            if (phase == 0) {
                builder = rectangleRenderer.beginBatch(stack);
                builder.pos(fadeWidth, 0.0f).size(this.width - fadeWidth * 2.0f, this.height - 8.0f).gradientVertical(secondaryBackgroundColor, primaryBackgroundColor).build();
                builder.pos(centerX - 0.5f, 0.0f).size(1.0f, this.height).gradientVertical(accentColor & 0x8FFFFFF, accentColor).build();
                final int shades = 40;
                final float shadeWidth = fadeWidth / shades;
                final int alpha = primaryBackgroundColor >>> 24;
                final int rgb = primaryBackgroundColor & 0xFFFFFF;
                for (int i = 0; i < shades; ++i) {
                    final float opacity = i / (float)shades;
                    final int color = (int)(opacity * alpha) << 24 | rgb;
                    builder.pos(i * shadeWidth, 0.0f).size(shadeWidth, this.height - 8.0f).gradientVertical(secondaryBackgroundColor, color).build();
                    builder.pos(this.width - (i + 1) * shadeWidth, 0.0f).size(shadeWidth, this.height - 8.0f).gradientVertical(secondaryBackgroundColor, color).build();
                }
            }
            for (int layer = 0; layer < 2; ++layer) {
                for (int rot = 0; rot < 360; ++rot) {
                    final float layerShift = (float)(layer * 360 * ((degree < 180.0f) ? -1 : 1));
                    final float offset = (rot - degree + layerShift) * spacing;
                    final float posX = centerX + offset;
                    if (posX >= 0.0f) {
                        if (posX <= this.width) {
                            final boolean isCardinalPoint = rot % 45 == 0;
                            final boolean isLabeled = rot % 15 == 0;
                            final int alphaMask = this.computeAlphaMask(this.width, offset, 100);
                            if (phase == 0) {
                                builder.pos(posX - 0.5f, this.height - 8.0f - (isLabeled ? 3 : 1)).size(1.0f, isLabeled ? 3.0f : 1.0f).color(alphaMask & 0xFF777777).build();
                            }
                            if (phase == 1 && isLabeled) {
                                this.renderText(stack, isCardinalPoint ? this.toCardinal((float)rot) : String.valueOf(rot), posX - 0.5f + (isCardinalPoint ? 0.7f : 1.0f), this.height - 15.0f - (isCardinalPoint ? 2 : 1), isCardinalPoint ? 0.7f : 0.5f, alphaMask, HorizontalAlignment.CENTER);
                            }
                        }
                    }
                }
            }
            if (phase == 0) {
                builder.upload();
                this.renderWorldObjects(stack, degree, true, this.width, 100);
            }
        }
        this.renderText(stack, this.toCardinal(degree), centerX - 1.0f, this.height - 7.0f, 1.0f, accentColor, HorizontalAlignment.RIGHT);
        this.renderText(stack, String.valueOf((int)degree), centerX + 2.0f, this.height - 7.0f, 1.0f, accentColor, HorizontalAlignment.LEFT);
    }
    
    private void renderCSGO(final Stack stack, final float degree) {
        final RenderPipeline pipeline = this.labyAPI.renderPipeline();
        final float centerX = this.width / 2.0f;
        final int primaryBackgroundColor = ((DirectionHudWidgetConfig)this.config).primaryBackgroundColor().get().get();
        final int secondaryBackgroundColor = ((DirectionHudWidgetConfig)this.config).secondaryBackgroundColor().get().get();
        final int accentColor = ((DirectionHudWidgetConfig)this.config).accentColor().get().get();
        final float spacing = ((DirectionHudWidgetConfig)this.config).spacing().get();
        for (int phase = 0; phase < 2; ++phase) {
            BatchRectangleRenderer builder = null;
            if (phase == 0) {
                builder = pipeline.rectangleRenderer().beginBatch(stack);
                builder.pos(0.0f, 0.0f).size(this.width / 2.0f, this.height - 10.0f).gradientHorizontal(secondaryBackgroundColor, primaryBackgroundColor).build();
                builder.pos(this.width / 2.0f, 0.0f).size(this.width / 2.0f, this.height - 10.0f).gradientHorizontal(primaryBackgroundColor, secondaryBackgroundColor).build();
                builder.pos(0.0f, this.height - 10.0f).size(this.width / 2.0f, 0.5f).gradientHorizontal(secondaryBackgroundColor, 1157627903).build();
                builder.pos(this.width / 2.0f, this.height - 10.0f).size(this.width / 2.0f, 0.5f).gradientHorizontal(1157627903, secondaryBackgroundColor).build();
                builder.pos(centerX - 0.25f, 0.0f).size(0.5f, this.height).gradientVertical(accentColor & 0x8FFFFFF, accentColor).build();
            }
            for (int layer = 0; layer < 2; ++layer) {
                for (int rot = 0; rot < 360; ++rot) {
                    final float layerShift = (float)(layer * 360 * ((degree < 180.0f) ? -1 : 1));
                    final float offset = (rot - degree + layerShift) * spacing;
                    final float posX = centerX + offset;
                    if (posX >= 0.0f) {
                        if (posX <= this.width) {
                            final boolean isCardinalPoint = rot % 45 == 0;
                            final boolean isLabeled = rot % 10 == 0;
                            final boolean isTick = isLabeled || isCardinalPoint;
                            final boolean isCompact = this.height < 20.0f;
                            final int alphaMask = this.computeAlphaMask(this.width, offset, 100);
                            if (phase == 0) {
                                final float tickHeight = (float)(isLabeled ? (isCardinalPoint ? 2 : 3) : 1);
                                builder.pos(posX - 0.25f, this.height - 10.0f - tickHeight * (isCompact ? 0 : 1)).size(0.5f, tickHeight).color(0xFFBBBBBB & alphaMask).build();
                            }
                            if (phase == 1 && isTick) {
                                this.renderText(stack, String.valueOf(rot), posX + 0.25f, this.height - 18.0f + (isCardinalPoint ? 2 : 0) + (isCompact ? 13 : 0), 0.5f, 0xFFEEEEEE & alphaMask, HorizontalAlignment.CENTER);
                                if (isCardinalPoint) {
                                    this.renderText(stack, this.toCardinal((float)rot), posX + 0.25f, this.height - 20.0f + (isCompact ? 13 : 0), 0.5f, 0xFFEEEEEE & alphaMask, HorizontalAlignment.CENTER);
                                }
                            }
                        }
                    }
                }
            }
            if (phase == 0) {
                builder.upload();
                this.renderWorldObjects(stack, degree, true, this.width, 100);
            }
        }
        this.renderText(stack, "" + (int)degree, centerX - 3.0f, this.height - 7.0f, 0.75f, accentColor, HorizontalAlignment.RIGHT);
        this.renderText(stack, this.toCardinal(degree), centerX + 3.0f, this.height - 7.0f, 0.75f, accentColor, HorizontalAlignment.LEFT);
    }
    
    private void renderGeo(final Stack stack, final float degree) {
        final RenderPipeline pipeline = this.labyAPI.renderPipeline();
        final float centerX = this.width / 2.0f;
        final float spacing = ((DirectionHudWidgetConfig)this.config).spacing().get();
        final int accentColor = ((DirectionHudWidgetConfig)this.config).accentColor().get().get();
        final int backgroundColorSide = ((DirectionHudWidgetConfig)this.config).primaryBackgroundColor().get().get();
        int backgroundColorCenter = ((DirectionHudWidgetConfig)this.config).secondaryBackgroundColor().get().get();
        final int sideAlpha = backgroundColorSide >> 24 & 0xFF;
        final int centerAlpha = backgroundColorCenter >> 24 & 0xFF;
        final int alpha = MathHelper.clamp(centerAlpha, sideAlpha / 2, 255);
        backgroundColorCenter &= 0xFFFFFF;
        backgroundColorCenter |= (alpha << 24 & 0xFF000000);
        this.labyAPI.renderPipeline().rectangleRenderer().pos(0.0f, 0.0f, this.width / 2.0f, this.height).gradientHorizontal(backgroundColorSide, backgroundColorCenter).rounded(5.0f, 0.0f, 5.0f, 0.0f).lowerEdgeSoftness(-0.125f).upperEdgeSoftness(0.5f).render(stack);
        this.labyAPI.renderPipeline().rectangleRenderer().pos(this.width / 2.0f, 0.0f, this.width, this.height).gradientHorizontal(backgroundColorCenter, backgroundColorSide).rounded(0.0f, 5.0f, 0.0f, 5.0f).lowerEdgeSoftness(-0.125f).upperEdgeSoftness(0.5f).render(stack);
        for (int phase = 0; phase < 2; ++phase) {
            BatchRectangleRenderer builder = null;
            if (phase == 0) {
                builder = pipeline.rectangleRenderer().beginBatch(stack);
            }
            for (int layer = 0; layer < 2; ++layer) {
                for (int rot = 0; rot < 360; ++rot) {
                    if (rot % 5 == 0) {
                        final float offset = (rot - degree + layer * 360 * ((degree < 180.0f) ? -1 : 1)) * spacing;
                        final float posX = centerX + offset;
                        if (posX >= 0.0f) {
                            if (posX <= this.width) {
                                final boolean isCardinalPoint = rot % 45 == 0;
                                final int alphaMask = this.computeAlphaMask(this.width - 15.0f, offset, 4);
                                if (isCardinalPoint) {
                                    if (phase == 1) {
                                        this.renderText(stack, this.toCardinal((float)rot), posX + 0.5f, this.height / 2.0f - 3.0f, 0.8f, accentColor & alphaMask, HorizontalAlignment.CENTER);
                                    }
                                }
                                else {
                                    final boolean isNextToCardinal = (rot - 5) % 45 != 0 && (rot + 5) % 45 != 0;
                                    if (phase == 0 && (isNextToCardinal || spacing >= 2.0f)) {
                                        builder.pos(posX - 0.5f, this.height / 4.0f).size(1.0f, this.height / 2.0f).color(0x44FFFFFF & alphaMask).build();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (phase == 0) {
                builder.upload();
                this.renderWorldObjects(stack, degree, true, this.width - 15.0f, 4);
            }
        }
        pipeline.triangleRenderer().render(stack, centerX + 1.0f, 0.0f, centerX - 1.0f, 0.0f, centerX, 4.0f, 1157627903, true);
        pipeline.triangleRenderer().render(stack, centerX - 1.0f, this.height, centerX + 1.0f, this.height, centerX, this.height - 4.0f, 1157627903, true);
    }
    
    private void renderText(final Stack stack, final String text, final float x, final float y, final float scale, final int color, final HorizontalAlignment alignment) {
        final TextRenderer textRenderer = this.labyAPI.renderPipeline().textRenderer();
        final float textWidth = (alignment == HorizontalAlignment.LEFT) ? 0.0f : (textRenderer.width(text) * scale);
        textRenderer.pos(x - ((alignment == HorizontalAlignment.RIGHT) ? textWidth : ((alignment == HorizontalAlignment.CENTER) ? (textWidth / 2.0f) : 0.0f)), y).text(text).shadow(false).useFloatingPointPosition(true).color(color, false).scale(scale).render(stack);
    }
    
    private void renderWorldObjects(final Stack stack, final float degree, final boolean hasOpacity, final float opacityWidth, final int opacityTransitionRadius) {
        if (!((DirectionHudWidgetConfig)this.config).showWorldObjectIndicators().get()) {
            return;
        }
        final IngameOverlayActivity overlay = this.labyAPI.ingameOverlay().getActivity(WorldObjectOverlay.class).orElse(null);
        if (!(overlay instanceof WorldObjectOverlay)) {
            return;
        }
        final WorldObjectOverlay worldObjectOverlay = (WorldObjectOverlay)overlay;
        final Minecraft minecraft = this.labyAPI.minecraft();
        final Entity cam = minecraft.getCameraEntity();
        if (cam == null) {
            return;
        }
        final float centerX = this.width / 2.0f;
        final float spacing = ((DirectionHudWidgetConfig)this.config).spacing().get();
        for (final Map.Entry<WorldObject, Widget> entry : worldObjectOverlay.getObjects().entrySet()) {
            final WorldObject object = entry.getKey();
            final Widget widget = entry.getValue();
            if (!object.shouldRender()) {
                continue;
            }
            final float yaw = MathHelper.convertDegrees(degree, worldObjectOverlay.getYaw(cam, object));
            final float offset = (yaw - degree) * spacing;
            final float posX = centerX + offset;
            if (posX < 0.0f) {
                continue;
            }
            if (posX > this.width) {
                continue;
            }
            stack.push();
            final float prevOpacity = this.labyAPI.renderPipeline().getAlpha();
            final boolean prevVisible = widget.isVisible();
            final Bounds bounds = widget.bounds();
            stack.translate(-bounds.getX(BoundsType.OUTER) + posX - bounds.getWidth(BoundsType.OUTER) / 2.0f, -bounds.getY(BoundsType.OUTER) + 3.0f, 0.0f);
            this.labyAPI.renderPipeline().setAlpha(hasOpacity ? ((this.computeAlphaMask(opacityWidth, offset, opacityTransitionRadius) >>> 24) / 255.0f) : 1.0f);
            widget.setVisible(true);
            widget.renderWidget(stack, minecraft.mouse(), minecraft.getPartialTicks());
            this.labyAPI.renderPipeline().setAlpha(prevOpacity);
            widget.setVisible(prevVisible);
            stack.pop();
        }
    }
    
    private String toCardinal(final float degree) {
        String text = null;
        switch (Math.round(degree / 45.0f)) {
            default: {
                text = "S";
                break;
            }
            case 1: {
                text = "SW";
                break;
            }
            case 2: {
                text = "W";
                break;
            }
            case 3: {
                text = "NW";
                break;
            }
            case 4: {
                text = "N";
                break;
            }
            case 5: {
                text = "NE";
                break;
            }
            case 6: {
                text = "E";
                break;
            }
            case 7: {
                text = "SE";
                break;
            }
        }
        return text;
    }
    
    private int computeAlphaMask(final float width, final float offset, final int transitionRadius) {
        final float length = width / 2.0f;
        final float opacity = (-MathHelper.square(offset) + MathHelper.square(length)) / (transitionRadius * length);
        return (int)(MathHelper.clamp(opacity, 0.0f, 1.0f) * 255.0f) << 24 | 0xFFFFFF;
    }
    
    @Override
    public boolean isVisibleInGame() {
        return true;
    }
    
    public enum DesignType
    {
        MINECRAFT, 
        PUPG, 
        COD, 
        CSGO, 
        GEO;
    }
    
    public static class DirectionHudWidgetConfig extends ResizeableHudWidgetConfig
    {
        @DropdownWidget.DropdownSetting
        private final ConfigProperty<DesignType> design;
        @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
        private final ConfigProperty<Color> accentColor;
        @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
        private final ConfigProperty<Color> primaryBackgroundColor;
        @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
        private final ConfigProperty<Color> secondaryBackgroundColor;
        @SliderWidget.SliderSetting(min = 1.0f, max = 5.0f, steps = 0.1f)
        private final ConfigProperty<Float> spacing;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showWorldObjectIndicators;
        
        public DirectionHudWidgetConfig() {
            super(50.0f, 200.0f, 400.0f, 12.0f, 20.0f, 25.0f);
            this.design = new ConfigProperty<DesignType>(DesignType.PUPG);
            this.accentColor = new ConfigProperty<Color>(Color.of(-1));
            this.primaryBackgroundColor = new ConfigProperty<Color>(Color.of(Integer.MIN_VALUE));
            this.secondaryBackgroundColor = new ConfigProperty<Color>(Color.of(0));
            this.spacing = new ConfigProperty<Float>(5.0f);
            this.showWorldObjectIndicators = new ConfigProperty<Boolean>(true);
        }
        
        public ConfigProperty<DesignType> design() {
            return this.design;
        }
        
        public ConfigProperty<Color> accentColor() {
            return this.accentColor;
        }
        
        public ConfigProperty<Color> primaryBackgroundColor() {
            return this.primaryBackgroundColor;
        }
        
        public ConfigProperty<Color> secondaryBackgroundColor() {
            return this.secondaryBackgroundColor;
        }
        
        public ConfigProperty<Float> spacing() {
            return this.spacing;
        }
        
        public ConfigProperty<Boolean> showWorldObjectIndicators() {
            return this.showWorldObjectIndicators;
        }
    }
}
