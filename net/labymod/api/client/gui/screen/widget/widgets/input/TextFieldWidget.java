// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.switchable.StringSwitchableHandler;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.property.Property;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.lang.annotation.Annotation;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.math.MathHelper;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.OffsetSide;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import java.util.function.Predicate;
import net.labymod.api.client.resources.texture.GameImage;
import java.util.function.Consumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
@SettingWidget
public class TextFieldWidget extends SimpleWidget
{
    private static final Logging LOGGER;
    private static final ModifyReason MODIFY_REASON;
    private static final String COOLDOWN_ID = "cooldown";
    protected static final char PARAGRAPH = '§';
    private final LssProperty<WidgetAlignment> textAlignmentX;
    private final LssProperty<WidgetAlignment> textAlignmentY;
    private final LssProperty<Type> type;
    private final LssProperty<Integer> placeHolderColor;
    private final LssProperty<Boolean> textShadow;
    private final LssProperty<Integer> textColor;
    private final LssProperty<Integer> markTextColor;
    private final LssProperty<Integer> markColor;
    private final LssProperty<Integer> cursorColor;
    private final LssProperty<Boolean> clearButton;
    private final LssProperty<Boolean> submitButton;
    private final LssProperty<FontSize> fontSize;
    protected String text;
    private String previousText;
    protected Component placeholder;
    protected String placeholderTranslatable;
    protected int cursorIndex;
    protected int viewIndex;
    protected long timeLastActivity;
    protected boolean marked;
    protected int markerIndex;
    protected boolean editable;
    protected boolean password;
    protected Consumer<String> updateListener;
    protected Consumer<String> submitHandler;
    protected Consumer<GameImage> imagePasteHandler;
    protected boolean handleCharacterKeyPress;
    private int maximalLength;
    private boolean blockFirstKeyPress;
    private long cooldownMillis;
    private long currentCooldownEnd;
    private Predicate<String> validator;
    protected IconWidget clearButtonWidget;
    protected IconWidget submitButtonWidget;
    
    public TextFieldWidget() {
        this.textAlignmentX = new LssProperty<WidgetAlignment>(WidgetAlignment.LEFT);
        this.textAlignmentY = new LssProperty<WidgetAlignment>(WidgetAlignment.TOP);
        this.type = new LssProperty<Type>(Type.DEFAULT);
        this.placeHolderColor = new LssProperty<Integer>(ColorFormat.ARGB32.pack(NamedTextColor.DARK_GRAY.getValue(), 255));
        this.textShadow = new LssProperty<Boolean>(true);
        this.textColor = new LssProperty<Integer>(ColorFormat.ARGB32.pack(NamedTextColor.WHITE.getValue(), 255));
        this.markTextColor = new LssProperty<Integer>(ColorFormat.ARGB32.pack(2105514, 255));
        this.markColor = new LssProperty<Integer>(-1);
        this.cursorColor = new LssProperty<Integer>(ColorFormat.ARGB32.pack(NamedTextColor.WHITE.getValue(), 255));
        this.clearButton = new LssProperty<Boolean>(false);
        this.submitButton = new LssProperty<Boolean>(false);
        this.fontSize = new LssProperty<FontSize>(FontSize.predefined(FontSize.PredefinedFontSize.MEDIUM));
        this.text = "";
        this.previousText = null;
        this.marked = false;
        this.editable = true;
        this.maximalLength = 32767;
        this.cooldownMillis = 0L;
        this.currentCooldownEnd = -1L;
        this.validator = (content -> true);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.placeholderTranslatable != null) {
            final String placeholderTranslation = I18n.getTranslation(this.placeholderTranslatable, new Object[0]);
            if (placeholderTranslation != null) {
                this.placeholder = Component.text(placeholderTranslation);
            }
        }
        this.setAttributeState(AttributeState.ENABLED, this.editable);
    }
    
    @Override
    public void postStyleSheetLoad() {
        if (this.clearButton.get()) {
            (this.clearButtonWidget = new IconWidget(Textures.SpriteCommon.SMALL_X)).addId("clear", "action-button");
            this.clearButtonWidget.setPressable(() -> this.setText(""));
            ((AbstractWidget<IconWidget>)this).addChildInitialized(this.clearButtonWidget);
        }
        if (this.submitButton.get()) {
            (this.submitButtonWidget = new IconWidget(Textures.SpriteCommon.SUBMIT)).addId("submit", "action-button");
            this.submitButtonWidget.setPressable(() -> {
                if (this.submitHandler != null) {
                    this.submit();
                }
                return;
            });
            ((AbstractWidget<IconWidget>)this).addChildInitialized(this.submitButtonWidget);
        }
        super.postStyleSheetLoad();
        this.updateActionButtons();
    }
    
    private void updateActionButtons() {
        if (this.clearButtonWidget != null) {
            this.clearButtonWidget.setVisible(!this.text.isEmpty() && this.clearButton.get());
        }
        if (this.submitButtonWidget != null) {
            this.submitButtonWidget.setVisible(this.submitButton.get());
        }
    }
    
    @Override
    protected void updateContentBounds() {
        super.updateContentBounds();
        final Bounds bounds = this.bounds();
        float offsetRight = 0.0f;
        if (this.submitButtonWidget != null && this.submitButtonWidget.visible().get()) {
            final ReasonableMutableRectangle submitBounds = this.submitButtonWidget.bounds().rectangle(BoundsType.OUTER);
            final float width = submitBounds.getWidth();
            submitBounds.setX(bounds.getRight() - width, TextFieldWidget.MODIFY_REASON);
            offsetRight = width;
        }
        if (this.clearButtonWidget != null && this.clearButtonWidget.visible().get()) {
            final ReasonableMutableRectangle clearBounds = this.clearButtonWidget.bounds().rectangle(BoundsType.OUTER);
            final float width = clearBounds.getWidth();
            clearBounds.setX(bounds.getRight() - offsetRight - width, TextFieldWidget.MODIFY_REASON);
        }
        super.updateContentBounds();
    }
    
    @Override
    public String getDefaultRendererName() {
        return "TextField";
    }
    
    @Override
    public boolean hasTabFocus() {
        return true;
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        this.updateCooldownId();
        super.renderWidget(context);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (!this.isFocused() || !this.editable) {
            return false;
        }
        if (this.blockFirstKeyPress) {
            return this.blockFirstKeyPress = false;
        }
        boolean handled = false;
        if (this.submitHandler != null && KeyHandler.isEnter(key)) {
            this.submit();
            handled = true;
        }
        if (KeyHandler.isSelectAll(key)) {
            this.markAll();
            return true;
        }
        if (KeyHandler.isSelectLeft(key)) {
            this.markLeft();
            return true;
        }
        if (KeyHandler.isSelectRight(key)) {
            this.markRight();
            return true;
        }
        if (KeyHandler.isPaste(key)) {
            final String data = this.labyAPI.minecraft().getClipboard();
            if (data.isEmpty() && this.imagePasteHandler != null) {
                try {
                    final GameImage image = this.labyAPI.operatingSystemAccessor().getImageInClipboard();
                    if (image != null) {
                        this.imagePasteHandler.accept(image);
                        return true;
                    }
                }
                catch (final UnsupportedOperationException exception) {
                    TextFieldWidget.LOGGER.error("Failed to paste image from clipboard", exception);
                }
            }
            this.insertText(data);
            this.marked = false;
            handled = true;
        }
        if (KeyHandler.isCopy(key) && this.marked) {
            this.labyAPI.minecraft().setClipboard(this.getMarkedText());
            return true;
        }
        if (KeyHandler.isCut(key) && this.marked) {
            this.labyAPI.minecraft().setClipboard(this.getMarkedText());
            handled = true;
            this.insertText("");
            this.marked = false;
        }
        if ((key == Key.BACK || (this.marked && (key == Key.DELETE || type == InputType.CHARACTER))) && this.cursorIndex > 0) {
            this.deleteText(-1);
            handled = (type != InputType.CHARACTER);
        }
        if (key == Key.HOME) {
            this.setCursorAtStart();
            this.marked = false;
        }
        if (key == Key.END) {
            this.setCursorAtEnd();
            this.marked = false;
        }
        if (key == Key.ARROW_LEFT) {
            if (KeyHandler.isShiftDown()) {
                this.handleMarked();
            }
            else {
                this.marked = false;
            }
            final int current = this.marked ? this.cursorIndex : Math.min(this.cursorIndex, this.markerIndex);
            this.moveCursorIndex(KeyHandler.isControlDown() ? this.wordIndex(-1) : Math.max(current - 1, 0));
            handled = true;
        }
        if (key == Key.ARROW_RIGHT) {
            if (KeyHandler.isShiftDown()) {
                this.handleMarked();
            }
            else {
                this.marked = false;
            }
            final int current = this.marked ? this.cursorIndex : Math.max(this.cursorIndex, this.markerIndex);
            this.moveCursorIndex(KeyHandler.isControlDown() ? this.wordIndex(1) : Math.min(current + 1, this.getMaxCursorIndex()));
            handled = true;
        }
        if ((key == Key.DELETE && this.cursorIndex < this.getMaxCursorIndex()) || (this.marked && key == Key.BACK && this.cursorIndex < 1)) {
            this.deleteText(1);
            handled = true;
        }
        if ((key == Key.DELETE || key == Key.BACK) && !handled) {
            handled = true;
        }
        if (this.handleCharacterKeyPress && type == InputType.CHARACTER) {
            handled = true;
        }
        if (handled) {
            this.timeLastActivity = TimeUtil.getMillis();
        }
        this.updateViewIndex();
        return handled;
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        if (!this.isFocused() || !this.editable) {
            return false;
        }
        this.insertText(Character.toString(character));
        this.marked = false;
        this.updateViewIndex();
        return true;
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (!this.isHovered() || !this.editable || mouseButton != MouseButton.LEFT) {
            return this.clearButtonWidget != null && this.clearButtonWidget.isHovered() && this.clearButtonWidget.mouseClicked(mouse, mouseButton);
        }
        if (this.text.isEmpty()) {
            this.moveCursorIndex(0);
            super.mouseClicked(mouse, mouseButton);
            return true;
        }
        final boolean shiftPressed = KeyHandler.isShiftDown();
        if (!shiftPressed) {
            this.marked = false;
        }
        mouse.transform(this.bounds().rectangle(BoundsType.INNER), () -> {
            int index = -1;
            float currentX = 0.0f;
            final String visibleText = this.getVisibleText();
            int i = 0;
            while (i < visibleText.length()) {
                final float charWidth = this.labyAPI.renderPipeline().textRenderer().width(visibleText.charAt(i)) * this.fontSize.get().getFontSize();
                if (currentX + charWidth / 2.0f > mouse.getX()) {
                    index = i;
                    break;
                }
                else {
                    currentX += charWidth;
                    ++i;
                }
            }
            final int maxVisibleIndex = this.text.indexOf(visibleText) + visibleText.length();
            if (index == -1) {
                index = maxVisibleIndex;
            }
            final int index2 = index + this.viewIndex;
            if (!this.lastMouseClick.isDoubleClick()) {
                this.moveCursorIndex(index2);
                return;
            }
            else {
                int startIndex = (index2 == 0) ? 0 : -1;
                int endIndex = (index2 == maxVisibleIndex) ? index2 : -1;
                if (startIndex == -1) {
                    int j = (index2 == maxVisibleIndex) ? (maxVisibleIndex - 1) : index2;
                    while (j >= 0) {
                        if (this.text.charAt(j) == ' ') {
                            startIndex = j;
                            break;
                        }
                        else {
                            --j;
                        }
                    }
                    if (startIndex == -1) {
                        startIndex = 0;
                    }
                }
                if (endIndex == -1) {
                    int k = index2;
                    while (k < maxVisibleIndex) {
                        if (this.text.charAt(k) == ' ') {
                            endIndex = k;
                            break;
                        }
                        else {
                            ++k;
                        }
                    }
                    if (endIndex == -1) {
                        endIndex = maxVisibleIndex;
                    }
                }
                this.moveCursorIndex(startIndex);
                this.handleMarked();
                this.moveCursorIndex(endIndex);
                return;
            }
        });
        super.mouseClicked(mouse, mouseButton);
        return true;
    }
    
    @Override
    public void unfocus() {
        super.unfocus();
        this.marked = false;
    }
    
    public void insertText(String text) {
        final int minPosition = this.marked ? Math.min(this.cursorIndex, this.markerIndex) : this.cursorIndex;
        final int maxPosition = this.marked ? Math.max(this.cursorIndex, this.markerIndex) : this.cursorIndex;
        final int newPosition = this.maximalLength - this.text.length() - (minPosition - maxPosition);
        int contentLength = text.length();
        if (newPosition < contentLength) {
            text = text.substring(0, newPosition);
            contentLength = newPosition;
        }
        text = text.replace(Character.toString('§'), "").replace("\n", "").replace("\r", "");
        final String newText = new StringBuilder(this.text).replace(minPosition, maxPosition, text).toString();
        if (this.validator.test(newText)) {
            this.applyText(newText);
            this.moveCursorIndex(minPosition + contentLength);
        }
    }
    
    public void setCursorAtStart() {
        this.cursorIndex = 0;
    }
    
    public void setCursorAtEnd() {
        this.cursorIndex = this.text.length();
    }
    
    public float getTextWidthOfRange(final int start, final int end) {
        return (start >= end) ? 0.0f : (this.labyAPI.renderPipeline().textRenderer().width(this.getFormattedText().substring(Math.max(start, 0), Math.min(end, this.text.length()))) * this.fontSize.get().getFontSize());
    }
    
    public String getVisibleText() {
        final String formattedText = this.getFormattedText();
        final int end = Math.min(this.viewIndex + this.getVisibleLength(), formattedText.length());
        return formattedText.substring(Math.min(this.viewIndex, end), end);
    }
    
    public int getVisibleLength() {
        final String formattedText = this.getFormattedText();
        if (formattedText.length() == 0) {
            return 0;
        }
        final String text = (formattedText.length() > this.viewIndex) ? formattedText.substring(this.viewIndex) : formattedText;
        final TextRenderer textRenderer = this.labyAPI.renderPipeline().textRenderer();
        for (int i = 0; i < text.length(); ++i) {
            if (textRenderer.width(text.substring(0, i)) * this.fontSize.get().getFontSize() >= this.getFieldWidth()) {
                return i;
            }
        }
        return text.length();
    }
    
    protected float getFieldWidth() {
        final Bounds bounds = this.bounds();
        float width;
        final float originalWidth = width = bounds.getWidth(BoundsType.INNER);
        if (this.clearButtonWidget != null && this.clearButtonWidget.visible().get()) {
            width -= this.clearButtonWidget.bounds().getWidth(BoundsType.OUTER);
        }
        if (this.submitButtonWidget != null && this.submitButtonWidget.visible().get()) {
            width -= this.submitButtonWidget.bounds().getWidth(BoundsType.OUTER);
        }
        if (width != originalWidth) {
            width -= bounds.getOffset(BoundsType.MIDDLE, OffsetSide.RIGHT);
        }
        return width;
    }
    
    public int getMarkerStartIndex() {
        return Math.min(this.cursorIndex, this.markerIndex);
    }
    
    public int getMarkerEndIndex() {
        return Math.max(this.cursorIndex, this.markerIndex);
    }
    
    public String getMarkedText() {
        return this.getText(this.getMarkerStartIndex(), this.getMarkerEndIndex());
    }
    
    public String getText(final int start, int end) {
        final String formattedText = this.getFormattedText();
        try {
            return formattedText.substring(start, end);
        }
        catch (final Exception exception) {
            TextFieldWidget.LOGGER.error("Failed to parse text (Start: {}, End: {} <> MarkerStart: {}, MarkerEnd: {}, Text: {})", start, end, this.getMarkerStartIndex(), this.getMarkerEndIndex(), this.getText(), exception);
            end = Math.min(end, formattedText.length());
            return formattedText.substring(Math.min(start, end), end);
        }
    }
    
    public float getOffsetX(final int index) {
        return this.getTextWidthOfRange(this.viewIndex, this.getVisibleIndex(index));
    }
    
    public int getVisibleIndex(int index) {
        final int maxLength = this.getVisibleLength();
        if (index - this.viewIndex > maxLength) {
            index = this.viewIndex + maxLength;
        }
        return Math.max(index, this.viewIndex);
    }
    
    public float getMarkerStartOffsetX() {
        return this.getOffsetX(this.getMarkerStartIndex());
    }
    
    public float getMarkerEndOffsetX() {
        return this.getOffsetX(this.getMarkerEndIndex());
    }
    
    public float getCursorOffsetX() {
        return this.getTextWidthOfRange(this.viewIndex, this.cursorIndex);
    }
    
    public boolean isCursorVisible() {
        final int maxLength = this.getVisibleLength();
        return this.cursorIndex - this.viewIndex <= maxLength && this.cursorIndex >= this.viewIndex;
    }
    
    public void setText(final String text, final boolean skipValidator) {
        if (Objects.equals(this.text, text)) {
            return;
        }
        if (skipValidator || this.validator.test(text)) {
            if (text.length() > this.maximalLength) {
                this.applyText(text.substring(0, this.maximalLength));
            }
            else {
                this.applyText(text);
            }
            this.updateViewIndex();
            this.setCursorAtStart();
        }
    }
    
    @Override
    public void setFocused(final boolean focused) {
        super.setFocused(focused);
        this.labyAPI.minecraft().updateKeyRepeatingMode(true);
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.setText(text, false);
    }
    
    public int getCursorIndex() {
        return this.cursorIndex;
    }
    
    public void setCursorIndex(final int index) {
        this.cursorIndex = MathHelper.clamp(index, 0, this.text.length());
        if (this.getFormattedText().length() <= this.getVisibleLength()) {
            this.viewIndex = 0;
        }
    }
    
    public int getViewIndex() {
        return this.viewIndex;
    }
    
    public int getMaxCursorIndex() {
        return this.text.length();
    }
    
    public boolean isCursorAtEnd() {
        return this.cursorIndex == this.getMaxCursorIndex();
    }
    
    public long getTimeLastActivity() {
        return this.timeLastActivity;
    }
    
    public boolean isEditable() {
        return this.editable;
    }
    
    public Component placeholder() {
        return this.placeholder;
    }
    
    public TextFieldWidget placeholder(final Component placeHolder) {
        this.placeholder = placeHolder;
        return this;
    }
    
    public boolean isPassword() {
        return this.password;
    }
    
    public TextFieldWidget password(final boolean isPassword) {
        this.password = isPassword;
        return this;
    }
    
    public boolean hasMarked() {
        return this.marked;
    }
    
    public boolean shouldDisplayPlaceHolder() {
        return this.text.isEmpty() && !this.isFocused();
    }
    
    public TextFieldWidget updateListener(final Consumer<String> updateListener) {
        this.updateListener = updateListener;
        return this;
    }
    
    public TextFieldWidget validator(final Predicate<String> validator) {
        this.validator = validator;
        return this;
    }
    
    public TextFieldWidget submitHandler(final Consumer<String> submitHandler) {
        this.submitHandler = submitHandler;
        return this;
    }
    
    public TextFieldWidget imagePasteHandler(final Consumer<GameImage> imagePasteHandler) {
        this.imagePasteHandler = imagePasteHandler;
        return this;
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return Math.max(75.0f, super.getContentWidth(type));
    }
    
    public void setEditable(final boolean editable) {
        this.setAttributeState(AttributeState.ENABLED, editable);
        this.editable = editable;
    }
    
    public int maximalLength() {
        return this.maximalLength;
    }
    
    public void maximalLength(final int maximalLength) {
        this.maximalLength = maximalLength;
    }
    
    public LssProperty<Type> type() {
        return this.type;
    }
    
    public LssProperty<WidgetAlignment> textAlignmentX() {
        return this.textAlignmentX;
    }
    
    public LssProperty<WidgetAlignment> textAlignmentY() {
        return this.textAlignmentY;
    }
    
    public LssProperty<Integer> placeHolderColor() {
        return this.placeHolderColor;
    }
    
    public LssProperty<Integer> textColor() {
        return this.textColor;
    }
    
    public LssProperty<Integer> markTextColor() {
        return this.markTextColor;
    }
    
    public LssProperty<Integer> markColor() {
        return this.markColor;
    }
    
    public LssProperty<Integer> cursorColor() {
        return this.cursorColor;
    }
    
    public LssProperty<Boolean> clearButton() {
        return this.clearButton;
    }
    
    public LssProperty<Boolean> submitButton() {
        return this.submitButton;
    }
    
    public LssProperty<FontSize> fontSize() {
        return this.fontSize;
    }
    
    public LssProperty<Boolean> textShadow() {
        return this.textShadow;
    }
    
    public TextFieldWidget blockFirstKeyPress(final boolean shouldBlock) {
        this.blockFirstKeyPress = shouldBlock;
        return this;
    }
    
    public void setCooldownMillis(final long cooldownMillis) {
        this.cooldownMillis = cooldownMillis;
    }
    
    public long getCurrentCooldownEnd() {
        return this.currentCooldownEnd;
    }
    
    public boolean isCooledDown() {
        return this.currentCooldownEnd != -1L && this.currentCooldownEnd >= TimeUtil.getMillis();
    }
    
    private void submit() {
        if (this.isCooledDown()) {
            return;
        }
        if (this.cooldownMillis > 0L) {
            this.currentCooldownEnd = TimeUtil.getMillis() + this.cooldownMillis;
        }
        this.submitHandler.accept(this.text);
    }
    
    private void updateCooldownId() {
        if (this.cooldownMillis <= 0L) {
            return;
        }
        final boolean hasId = this.hasId("cooldown");
        final boolean cooledDown = this.isCooledDown();
        if (cooledDown && !hasId) {
            this.addId("cooldown");
        }
        else if (!cooledDown && hasId) {
            this.removeId("cooldown");
        }
    }
    
    private void updateViewIndex() {
        final int viewStartIndex = this.viewIndex;
        final int viewEndIndex = viewStartIndex + this.getVisibleLength();
        if (this.viewIndex > 0 && this.text.length() <= this.getVisibleLength()) {
            this.viewIndex = 0;
        }
        if (this.cursorIndex < viewStartIndex && this.viewIndex > 0) {
            this.viewIndex = Math.max(this.viewIndex - this.getVisibleLength(), 0);
        }
        if (this.cursorIndex > viewEndIndex && this.viewIndex < this.text.length() - this.getVisibleLength() + 1) {
            this.viewIndex = this.text.length() - this.getVisibleLength() + 1;
        }
        if (this.updateListener != null && !Objects.equals(this.previousText, this.text)) {
            this.updateListener.accept(this.text);
            this.previousText = this.text;
        }
        this.callActionListeners();
        this.updateActionButtons();
    }
    
    protected String getFormattedText() {
        return this.password ? this.encrypt(this.text) : this.text;
    }
    
    private String encrypt(final String text) {
        final StringBuilder string = new StringBuilder();
        for (int i = 0; i < text.length(); ++i) {
            string.append("*");
        }
        return string.toString();
    }
    
    private void handleMarked() {
        if (this.marked) {
            return;
        }
        this.marked = true;
        this.markerIndex = this.cursorIndex;
    }
    
    private void markAll() {
        this.marked = true;
        this.markerIndex = 0;
        this.viewIndex = 0;
        this.setCursorAtEnd();
    }
    
    private void markLeft() {
        this.marked = true;
        this.markerIndex = 0;
    }
    
    private void markRight() {
        this.marked = true;
        this.markerIndex = this.text.length();
    }
    
    private void deleteText(final int index) {
        if (KeyHandler.isControlDown()) {
            this.deleteWords(index);
        }
        else {
            this.deleteCharacters(index);
        }
    }
    
    private void deleteWords(final int index) {
        if (this.text.isEmpty()) {
            return;
        }
        if (this.markerIndex != this.cursorIndex) {
            this.insertText("");
            return;
        }
        this.deleteCharacters(this.wordIndex(index) - this.cursorIndex);
    }
    
    private int wordIndex(final int index) {
        return this.wordIndex(index, this.cursorIndex);
    }
    
    private int wordIndex(final int index, final int cursorIndex) {
        int cursor = cursorIndex;
        final boolean lowerIndex = index < 0;
        for (int absIndex = Math.abs(index), i = 0; i < absIndex; ++i) {
            if (!lowerIndex) {
                final int contentLength = this.text.length();
                cursor = this.text.indexOf(32, cursor);
                if (cursor == -1) {
                    cursor = contentLength;
                }
                else {
                    while (cursor < contentLength && this.text.charAt(cursor) == ' ') {
                        ++cursor;
                    }
                }
            }
            else {
                while (cursor > 0 && this.text.charAt(cursor - 1) == ' ') {
                    --cursor;
                }
                while (cursor > 0 && this.text.charAt(cursor - 1) != ' ') {
                    --cursor;
                }
            }
        }
        return cursor;
    }
    
    public boolean isHandlingCharacterKeyPress() {
        return this.handleCharacterKeyPress;
    }
    
    public TextFieldWidget handleCharacterKeyPress(final boolean handleCharacterKeyPress) {
        this.handleCharacterKeyPress = handleCharacterKeyPress;
        return this;
    }
    
    public TextFieldWidget handleCharacterKeyPress() {
        return this.handleCharacterKeyPress(true);
    }
    
    private void deleteCharacters(final int index) {
        if (this.text.isEmpty()) {
            return;
        }
        if (this.marked && this.markerIndex != this.cursorIndex) {
            this.insertText("");
            this.marked = false;
            return;
        }
        final int newCursorIndex = this.cursorIndex + index;
        final int minIndex = Math.min(newCursorIndex, this.cursorIndex);
        final int maxIndex = Math.max(newCursorIndex, this.cursorIndex);
        if (minIndex != maxIndex) {
            final String newText = new StringBuilder(this.text).delete(minIndex, maxIndex).toString();
            if (this.validator.test(newText)) {
                this.applyText(newText);
                this.moveCursorIndex(minIndex);
            }
        }
    }
    
    protected void applyText(final String text) {
        this.text = text;
    }
    
    private void moveCursorIndex(final int index) {
        this.setCursorIndex(index);
        if (!this.marked) {
            this.markerIndex = this.cursorIndex;
        }
    }
    
    @Override
    public boolean isHoverComponentRendered() {
        return this.hasHoverComponent() ? super.isHoverComponentRendered() : super.isHovered();
    }
    
    static {
        LOGGER = Logging.getLogger();
        MODIFY_REASON = ModifyReason.of("textFieldActionBounds");
    }
    
    public enum Type
    {
        DEFAULT, 
        VANILLA_WINDOW;
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<TextFieldSetting, TextFieldWidget>
    {
        @Override
        public TextFieldWidget[] create(final Setting setting, final TextFieldSetting annotation, final SettingAccessor accessor) {
            final Object data = accessor.get();
            final boolean isFloat = data instanceof Float;
            final boolean isInteger = data instanceof Integer;
            final TextFieldWidget widget = new TextFieldWidget();
            widget.setText(String.valueOf(data));
            widget.updateListener(value -> {
                if (isFloat) {
                    if (!value.isEmpty() && !value.startsWith(".") && !value.endsWith(".")) {
                        accessor.set(Float.parseFloat(value));
                    }
                }
                else if (isInteger) {
                    if (!value.isEmpty()) {
                        accessor.set(Integer.parseInt(value));
                    }
                }
                else {
                    accessor.set(value);
                }
                return;
            });
            widget.placeholderTranslatable = setting.getTranslationKey() + ".placeholder";
            if (annotation.maxLength() >= 0) {
                widget.maximalLength(annotation.maxLength());
            }
            accessor.property().addChangeListener((t, oldValue, newValue) -> widget.setText(String.valueOf(newValue)));
            return new TextFieldWidget[] { widget };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[] { String.class, Float.class, Integer.class };
        }
    }
    
    @SettingElement(switchable = StringSwitchableHandler.class)
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TextFieldSetting {
        int maxLength() default -1;
    }
}
