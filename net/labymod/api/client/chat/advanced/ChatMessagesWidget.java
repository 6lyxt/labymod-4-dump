// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.advanced;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.client.chat.prefix.ChatPrefix;
import net.labymod.api.util.KeyValue;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gfx.GFXBridge;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.Laby;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.render.font.ComponentRenderMeta;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.chat.prefix.ChatPrefixRegistry;
import net.labymod.api.client.gui.screen.activity.activities.ingame.chat.WindowAccessor;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.util.Lazy;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class ChatMessagesWidget extends AbstractWidget<AbstractWidget<?>>
{
    private static final ModifyReason UPDATE_BUTTON;
    private static final Lazy<AdvancedChatConfig> SETTINGS;
    private static final int SCROLLBAR_WIDTH = 4;
    private static final int MULTI_LINE_INDENT = 4;
    private final IngameChatTab tab;
    private final WindowAccessor window;
    private final ChatPrefixRegistry prefixRegistry;
    private ButtonWidget menuButtonWidget;
    private ComponentRenderMeta lastHoveredComponentMeta;
    private int lineOffset;
    private int counterBeforeScroll;
    private int lastTotalLines;
    
    public ChatMessagesWidget(final IngameChatTab tab, final WindowAccessor window) {
        this.lastTotalLines = 0;
        this.tab = tab;
        this.window = window;
        this.prefixRegistry = window.chat().getProvider().prefixRegistry();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (ChatMessagesWidget.SETTINGS.get().showMenuButton().get()) {
            ((AbstractWidget<Widget>)(this.menuButtonWidget = ButtonWidget.icon(Textures.SpriteCommon.SMALL_BURGER))).addId("context");
            this.menuButtonWidget.setPressable(() -> ((AbstractWidget)this.window).openContextMenu());
            ((AbstractWidget<ButtonWidget>)this).addChild(this.menuButtonWidget);
        }
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        if (this.tab.getMessages().isEmpty() && !this.window.chat().isChatOpen()) {
            return;
        }
        this.keepScrollPosition();
        this.lastHoveredComponentMeta = null;
        this.renderMessages(context);
        this.renderScrollbar(context);
        super.renderWidget(context);
    }
    
    @Override
    public void renderHoverComponent(final ScreenContext context) {
        super.renderHoverComponent(context);
        if (this.lastHoveredComponentMeta != null) {
            this.lastHoveredComponentMeta.renderHover(context.stack(), context.mouse());
        }
    }
    
    private void renderMessages(final ScreenContext context) {
        final int lines = Math.min(this.getMaxLinesVisible(), this.getTotalLines());
        final Stack stack = context.stack();
        final MutableMouse mouse = context.mouse();
        for (int phase = 0; phase < 2; ++phase) {
            stack.push();
            int lineIndex = 0;
            final BatchRectangleRenderer rectangleRenderer = (phase == 0) ? this.labyAPI.renderPipeline().rectangleRenderer().beginBatch(stack) : null;
            final List<AdvancedChatMessage> messages = this.tab.getMessages();
            int lineCount = 0;
            for (final AdvancedChatMessage message : messages) {
                if (message != null) {
                    if (!message.isVisible()) {
                        continue;
                    }
                    stack.push();
                    stack.translate(0.0f, 0.0f, (lines - lineCount) * 0.75f);
                    lineIndex += this.renderMessage(stack, mouse, message, lineIndex, rectangleRenderer, phase);
                    stack.pop();
                    ++lineCount;
                }
            }
            if (rectangleRenderer != null) {
                final GFXBridge gfx = Laby.gfx();
                gfx.storeBlaze3DStates();
                gfx.disableDepth();
                rectangleRenderer.upload();
                gfx.restoreBlaze3DStates();
            }
            stack.pop();
            if (lineIndex == 0) {
                break;
            }
        }
        final int chatHeight = lines * this.getLineHeight(this.labyAPI.minecraft().options());
        this.updateMenuButton(chatHeight);
        if (this.window.hasTitleBar() && this.window.chat().isChatOpen()) {
            this.renderBackgroundGap(stack, chatHeight);
        }
    }
    
    private int renderMessage(final Stack stack, final MutableMouse mouse, final AdvancedChatMessage message, final int lineIndex, final BatchRectangleRenderer rectangleRenderer, final int phase) {
        final MinecraftOptions options = this.labyAPI.minecraft().options();
        final AdvancedChatConfig advancedChat = this.labyAPI.config().ingame().advancedChat();
        final boolean legacyMessageOffset = advancedChat.legacyMessageOffset().get();
        final Bounds bounds = this.bounds();
        final double scale = options.getChatScale();
        final int prefixWidth = this.getPrefixWidth(message, scale);
        final int textWidth = this.getLineWidth(prefixWidth, scale);
        final int lineHeight = this.getLineHeight(options);
        final boolean hasPrefix = prefixWidth != 4;
        final RenderableComponent[] renderableComponents = message.getRenderableComponents(textWidth);
        int totalLines = 0;
        for (final RenderableComponent renderableComponent : renderableComponents) {
            totalLines += renderableComponent.getLines();
        }
        final int chatBottom = (int)bounds.getBottom();
        final int chatHeight = (int)bounds.getHeight();
        final int messageX = (int)bounds.getX();
        final int messageY = chatBottom + (this.lineOffset - totalLines - lineIndex) * lineHeight;
        final float textOffset = (float)MathHelper.ceil(lineHeight / 2.0f - 4.0 * scale);
        final long duration = TimeUtil.getMillis() - message.timestamp();
        final boolean fadeInMessages = advancedChat.fadeInMessages().get() && !this.isScrolled();
        final float fadeInFactor = fadeInMessages ? ((float)CubicBezier.EASE_OUT.curve(Math.min(1.0f, duration / 100.0f))) : 1.0f;
        stack.translate(0.0f, lineHeight * (1.0f - fadeInFactor), 0.0f);
        final boolean chatOpen = this.window.chat().isChatOpen();
        final int counter = (int)TimeUtil.convertMillisecondsToTicks(duration);
        final double opacity = MathHelper.clamp((1.0 - counter / 200.0f) * 10.0, 0.0, 1.0);
        final int alpha = (int)((chatOpen ? 255.0 : Math.floor(255.0 * opacity * opacity)) * fadeInFactor);
        final double backgroundOpacity = this.getBackgroundOpacity(options);
        final int backgroundColor = this.getBackgroundColor(message, alpha, backgroundOpacity);
        final double textOpacity = options.getChatTextOpacity();
        final int textAlpha = (int)(alpha * textOpacity);
        final int textColor = (textAlpha & 0xFF) << 24 | 0xFFFFFF;
        int handledLines = 0;
        for (int mainLine = 0; mainLine < renderableComponents.length; ++mainLine) {
            final RenderableComponent renderableComponent2 = renderableComponents[mainLine];
            int renderedSubLines = 0;
            final int subLines = renderableComponent2.getLines();
            final int subLineTextOffset = (int)Math.floor((subLines * lineHeight - lineHeight) / 2.0f);
            for (int subLine = 0; subLine < subLines; ++subLine) {
                final int lineY = messageY + lineHeight * (mainLine + subLine);
                ++handledLines;
                if (chatOpen || lineY >= chatBottom - options.getChatHeightClosed()) {
                    if (lineY >= chatBottom - chatHeight) {
                        if (lineY + lineHeight <= chatBottom) {
                            if (counter < 200 || chatOpen) {
                                if (phase == 0) {
                                    this.renderLineBackground(rectangleRenderer, messageX, lineY, lineHeight, backgroundColor);
                                }
                                if (phase == 1) {
                                    for (final KeyValue<ChatPrefix> entry : this.prefixRegistry.getElements()) {
                                        final ChatPrefix prefix = entry.getValue();
                                        if (!prefix.isVisible(this.tab, message)) {
                                            continue;
                                        }
                                        prefix.render(stack, (float)messageX, (float)lineY, message, renderableComponents, mainLine, subLine, lineHeight, textOffset + subLineTextOffset, scale, alpha);
                                    }
                                }
                                ++renderedSubLines;
                            }
                        }
                    }
                }
            }
            final boolean allSubLinesVisible = renderedSubLines == subLines;
            if (allSubLinesVisible && phase == 1) {
                final boolean firstLine = mainLine == 0;
                final int lineY2 = messageY + lineHeight * mainLine;
                stack.push();
                stack.translate(0.0f, 0.0f, mainLine * 0.25f);
                this.renderLineText(stack, mouse, renderableComponent2, (float)(messageX + prefixWidth + (legacyMessageOffset ? 1 : 2) - ((firstLine || hasPrefix) ? 4 : 0)), lineY2 + textOffset, scale, textColor);
                stack.pop();
            }
        }
        return handledLines;
    }
    
    private void renderLineBackground(final BatchRectangleRenderer rectangleRenderer, final int x, final int y, final int lineHeight, final int backgroundColor) {
        rectangleRenderer.pos((float)x, (float)y).size((float)((int)this.bounds().getRight() - x), (float)lineHeight).color(backgroundColor).build();
    }
    
    private void renderLineText(final Stack stack, final MutableMouse mouse, final RenderableComponent renderableComponent, final float x, final float y, final double scale, final int textColor) {
        final ComponentRenderMeta meta = this.labyAPI.renderPipeline().componentRenderer().builder().pos(x, y).text(renderableComponent).scale((float)scale).shadow(this.tab.config().shadow().get()).useFloatingPointPosition(false).color(textColor, false).render(stack);
        if (mouse != null && meta.isInRectangle(mouse)) {
            this.lastHoveredComponentMeta = meta;
        }
    }
    
    private void renderBackgroundGap(final Stack stack, final int chatHeight) {
        final MinecraftOptions options = this.labyAPI.minecraft().options();
        final double backgroundOpacity = this.getBackgroundOpacity(options);
        final Bounds bounds = this.bounds();
        final GFXBridge gfx = Laby.gfx();
        gfx.storeBlaze3DStates();
        gfx.disableDepth();
        this.labyAPI.renderPipeline().rectangleRenderer().renderRectangle(stack, (float)(int)bounds.getLeft(), (float)(int)bounds.getTop(), (float)(int)bounds.getRight(), (float)((int)bounds.getBottom() - chatHeight), ColorFormat.ARGB32.pack(0, (int)(255.0 * backgroundOpacity)));
        gfx.restoreBlaze3DStates();
    }
    
    private void updateMenuButton(final int chatHeight) {
        if (this.menuButtonWidget == null) {
            return;
        }
        final boolean hasMessages = chatHeight > 0;
        final boolean chatOpen = this.window.chat().isChatOpen();
        final boolean visible = chatOpen && !this.window.hasTitleBar() && hasMessages;
        this.menuButtonWidget.setVisible(visible);
        if (hasMessages) {
            final Bounds buttonBounds = this.menuButtonWidget.bounds();
            buttonBounds.setBottomY(this.bounds().getBottom() - chatHeight, BoundsType.OUTER, ChatMessagesWidget.UPDATE_BUTTON);
        }
    }
    
    private void renderScrollbar(final ScreenContext context) {
        final int totalLines = this.getTotalLines();
        final int visibleLines = this.getMaxLinesVisible();
        if (this.window.chat().isChatOpen() && totalLines > visibleLines) {
            final MinecraftOptions options = this.labyAPI.minecraft().options();
            final int lineHeight = this.getLineHeight(options);
            final int invisibleLines = totalLines - visibleLines;
            final int visibleHeight = visibleLines * lineHeight;
            final int scrollBarHeight = (int)(visibleHeight * (visibleLines / (double)totalLines));
            final double percentage = this.lineOffset / (double)invisibleLines;
            final double scrollBarOffset = percentage * (visibleHeight - scrollBarHeight);
            final int scrollBarX = (int)this.bounds().getRight() - 4;
            final int scrollBarY = (int)(this.bounds().getBottom() - scrollBarOffset) - scrollBarHeight;
            final boolean isScrolled = this.isScrolled();
            final boolean newMessagesSinceScroll = this.counterBeforeScroll != this.tab.getCounter() && isScrolled;
            final int opacity = isScrolled ? 170 : 96;
            final int color = newMessagesSinceScroll ? 13382451 : 3355562;
            final BatchRectangleRenderer renderer = this.labyAPI.renderPipeline().rectangleRenderer().beginBatch(context.stack());
            final boolean legacyScrollbar = this.labyAPI.config().ingame().advancedChat().legacyScrollbar().get();
            if (!legacyScrollbar) {
                renderer.pos((float)scrollBarX, (float)scrollBarY).size(2.0f, (float)scrollBarHeight).color(color + (opacity << 24)).build();
            }
            renderer.pos((float)(scrollBarX + (legacyScrollbar ? 3 : 1)), (float)scrollBarY).size(1.0f, (float)scrollBarHeight).color(legacyScrollbar ? -1 : (13421772 + (opacity << 24))).build();
            renderer.upload();
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        return (this.lastHoveredComponentMeta != null && this.lastHoveredComponentMeta.interact()) || super.mouseClicked(mouse, mouseButton);
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        if (!this.isScrolled()) {
            this.counterBeforeScroll = this.tab.getCounter();
        }
        final boolean holdingShift = this.labyAPI.minecraft().isKeyPressed(Key.L_SHIFT);
        this.lineOffset += ((scrollDelta > 0.0) ? 1 : -1) * (holdingShift ? 1 : 7);
        this.clampLineOffset();
        return super.mouseScrolled(mouse, scrollDelta);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        final MinecraftOptions options = this.labyAPI.minecraft().options();
        return (float)(Math.min(this.getTotalLines(), this.getMaxLinesVisible()) * this.getLineHeight(options));
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.clampLineOffset();
    }
    
    private void clampLineOffset() {
        final int totalLines = this.getTotalLines();
        final int maxLinesVisible = this.getMaxLinesVisible();
        this.lineOffset = MathHelper.clamp(this.lineOffset, 0, Math.max(totalLines - maxLinesVisible, 0));
    }
    
    private void keepScrollPosition() {
        final int totalLines = this.getTotalLines();
        if (this.lastTotalLines != totalLines) {
            if (this.lineOffset != 0) {
                this.lineOffset += totalLines - this.lastTotalLines;
                this.clampLineOffset();
            }
            this.lastTotalLines = totalLines;
        }
    }
    
    private int getTotalLines() {
        final double scale = this.labyAPI.minecraft().options().getChatScale();
        int totalLines = 0;
        for (final AdvancedChatMessage message : this.tab.getMessages()) {
            if (!message.isVisible()) {
                continue;
            }
            final int width = this.getLineWidth(message, scale);
            final RenderableComponent[] renderableComponents2;
            final RenderableComponent[] renderableComponents = renderableComponents2 = message.getRenderableComponents(width);
            for (final RenderableComponent renderableComponent : renderableComponents2) {
                totalLines += renderableComponent.getLines();
            }
        }
        return totalLines;
    }
    
    private int getMaxLinesVisible() {
        final MinecraftOptions options = this.labyAPI.minecraft().options();
        final int lineHeight = this.getLineHeight(options);
        return (lineHeight == 0) ? 0 : ((int)this.bounds().getHeight() / lineHeight);
    }
    
    private int getPrefixWidth(final AdvancedChatMessage message, final double scale) {
        int width = 0;
        for (final KeyValue<ChatPrefix> entry : this.prefixRegistry.getElements()) {
            final ChatPrefix prefix = entry.getValue();
            if (!prefix.isVisible(this.tab, message)) {
                continue;
            }
            width += prefix.getWidth(message, scale);
        }
        return width + 4;
    }
    
    private int getLineWidth(final int prefixWidth, final double scale) {
        final float width = this.bounds().getWidth() - prefixWidth - 4.0f;
        return MathHelper.ceil(width / scale);
    }
    
    private int getLineWidth(final AdvancedChatMessage message, final double scale) {
        return this.getLineWidth(this.getPrefixWidth(message, scale), scale);
    }
    
    private int getLineHeight(final MinecraftOptions options) {
        final float height = Laby.references().textRendererProvider().getRenderer().height();
        final int lineHeight = (int)(height * (options.getChatLineSpacing() + 1.0));
        return MathHelper.ceil(lineHeight * options.getChatScale());
    }
    
    private int getBackgroundColor(final AdvancedChatMessage message, final int alpha, double backgroundOpacity) {
        final Object value = message.metadata().get("custom_background");
        final boolean hasBackgroundColor = value instanceof Integer;
        final boolean isBackgroundDisabled = backgroundOpacity == 0.0;
        final int color = (int)(hasBackgroundColor ? value : 0);
        if (hasBackgroundColor && isBackgroundDisabled) {
            backgroundOpacity = 0.5;
        }
        final int backgroundAlpha = (int)(alpha * backgroundOpacity);
        return (backgroundAlpha & 0xFF) << 24 | (color & 0xFFFFFF);
    }
    
    private double getBackgroundOpacity(final MinecraftOptions options) {
        final boolean backgroundVisible = this.tab.config().background().get();
        return backgroundVisible ? options.getTextBackgroundOpacity() : 0.0;
    }
    
    private boolean isScrolled() {
        return this.lineOffset != 0;
    }
    
    static {
        UPDATE_BUTTON = ModifyReason.of("updateButton");
        SETTINGS = Lazy.of(() -> Laby.labyAPI().config().ingame().advancedChat());
    }
}
