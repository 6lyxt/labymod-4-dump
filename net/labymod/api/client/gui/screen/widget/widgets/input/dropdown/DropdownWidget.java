// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.dropdown;

import net.labymod.api.configuration.settings.switchable.StringSwitchableHandler;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.property.Property;
import java.lang.annotation.Annotation;
import java.util.Objects;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.Arrays;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import java.util.Iterator;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.popup.DropdownPopupWidget;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.style.modifier.attribute.AttributeState;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.DefaultEntryRenderer;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.renderer.EntryRenderer;
import java.util.List;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
@SettingWidget
@Link("activity/overlay/dropdown/dropdown.lss")
public class DropdownWidget<T> extends AbstractWidget<Widget>
{
    private static final ModifyReason DROPDOWN_POSITION;
    protected final LssProperty<Float> wrapperPadding;
    private final LssProperty<Float> arrowWidth;
    protected final List<T> entries;
    protected EntryRenderer<T> entryRenderer;
    protected boolean open;
    protected boolean dropUp;
    protected long lastTimeOpenChanged;
    protected WidgetReference reference;
    private T selected;
    private ChangeListener<T> changeListener;
    
    public DropdownWidget() {
        this.wrapperPadding = new LssProperty<Float>(1.0f);
        this.arrowWidth = new LssProperty<Float>(30.0f);
        this.entries = new ArrayList<T>();
        this.entryRenderer = new DefaultEntryRenderer<T>();
        this.setAttributeState(AttributeState.ENABLED, true);
        this.setPressable(() -> this.toggleOpen());
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.selected != null) {
            final Widget widget = this.entryRenderer.createSelectedWidget(this.selected);
            widget.addId("selected");
            this.addChild(widget);
        }
    }
    
    private void toggleOpen() {
        if (this.open) {
            this.close();
        }
        else {
            this.open();
        }
    }
    
    public void openAt(final Rectangle attachTo, final List<StyleSheet> styleSheets) {
        if (!this.hasEntries()) {
            return;
        }
        final DropdownPopupWidget<T> popup = new DropdownPopupWidget<T>(this);
        final Window window = this.labyAPI.minecraft().minecraftWindow();
        this.dropUp = (attachTo.getY() > window.getScaledHeight() / 2.0f);
        if (this.dropUp) {
            popup.addId("drop-up");
        }
        for (CharSequence id : this.getIds()) {
            popup.addId(String.valueOf(id) + "-popup");
        }
        (this.reference = this.displayInOverlay(styleSheets, popup)).boundsUpdater((ref, bounds) -> {
            final boolean dropUp = this.isDropUp();
            final float width = attachTo.getWidth() - this.wrapperPadding().get() * 2.0f;
            final float height = ref.widget().getEffectiveHeight();
            bounds.setSize(width, height, DropdownWidget.DROPDOWN_POSITION);
            final float x = attachTo.getX() + this.wrapperPadding().get();
            final float y = attachTo.getY() + (dropUp ? (-height) : attachTo.getHeight());
            bounds.setPosition(x, y, DropdownWidget.DROPDOWN_POSITION);
            return;
        });
        this.reference.destroyHandlers().add(this::close);
        this.open = true;
        this.lastTimeOpenChanged = TimeUtil.getMillis();
    }
    
    public void openAt(final Rectangle attachTo) {
        final StyleSheet styleSheet = new LinkReference("labymod", "lss/activity/overlay/dropdown/dropdown.lss").loadStyleSheet();
        this.openAt(attachTo, Arrays.asList(styleSheet));
    }
    
    public void open() {
        this.openAt(Bounds.absoluteBounds(this), this.getStyleSheets());
    }
    
    public void close() {
        this.close(true);
    }
    
    public void close(final boolean playSound) {
        this.open = false;
        this.lastTimeOpenChanged = TimeUtil.getMillis();
        if (this.reference != null && this.reference.isAlive()) {
            this.reference.remove();
        }
        if (!this.labyAPI.minecraft().mouse().isInside(Bounds.absoluteBounds(this))) {
            return;
        }
        if (playSound) {
            Laby.references().soundService().play(SoundType.BUTTON_CLICK);
        }
    }
    
    protected void update() {
        this.setAttributeState(AttributeState.ENABLED, this.hasEntries());
    }
    
    public void add(final T entry) {
        this.entries.add(entry);
        this.update();
    }
    
    public void addAll(final Collection<T> entries) {
        this.entries.addAll((Collection<? extends T>)entries);
        this.update();
    }
    
    @Deprecated
    public void addAll(final List<T> entries) {
        this.addAll((Collection<T>)entries);
    }
    
    public void addAll(final T[] entries) {
        this.entries.addAll((Collection<? extends T>)Arrays.asList(entries));
        this.update();
    }
    
    public void remove(final T entry) {
        this.entries.remove(entry);
        this.update();
    }
    
    public void clear() {
        this.entries.clear();
        this.update();
    }
    
    public void setSelected(final T entry) {
        this.setSelected(entry, true);
    }
    
    public void setSelected(final T entry, final boolean notify) {
        this.selected = entry;
        if (this.isInitialized()) {
            this.reInitialize();
        }
        if (notify && this.changeListener != null) {
            this.changeListener.onChange(entry);
        }
        this.update();
    }
    
    public T getSelected() {
        return this.selected;
    }
    
    public void setChangeListener(final ChangeListener<T> changeListener) {
        this.changeListener = changeListener;
    }
    
    public void setEntryRenderer(final EntryRenderer<T> entryRenderer) {
        this.entryRenderer = entryRenderer;
    }
    
    public List<T> entries() {
        return this.entries;
    }
    
    public float getWidth() {
        return this.bounds().getWidth();
    }
    
    public void onSelect(final T entry) {
        this.setSelected(entry);
    }
    
    public EntryRenderer<T> entryRenderer() {
        return this.entryRenderer;
    }
    
    @Nullable
    public WidgetReference getReference() {
        return this.reference;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public boolean isDropUp() {
        return this.dropUp;
    }
    
    public boolean hasEntries() {
        return !this.entries.isEmpty();
    }
    
    public long getLastTimeOpenChanged() {
        return this.lastTimeOpenChanged;
    }
    
    public float getAnimationProgress() {
        if (this.reference == null) {
            return 0.0f;
        }
        final long animationDuration = TimeUtil.getMillis() - this.lastTimeOpenChanged;
        final float progress = (float)CubicBezier.EASE_OUT.curve(Math.min(1.0f, animationDuration / 100.0f));
        return this.open ? progress : (1.0f - progress);
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        final boolean isFitContent = this.hasSize(SizeType.MAX, WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT);
        final float maxWidth = isFitContent ? Float.MAX_VALUE : this.getSizeOr(SizeType.MAX, WidgetSide.WIDTH, Float.MAX_VALUE);
        float width = (this.selected == null) ? 0.0f : this.entryRenderer.getWidth(this.selected, maxWidth);
        for (final T entry : this.entries) {
            width = Math.max(width, this.entryRenderer.getWidth(entry, maxWidth));
        }
        return width + this.arrowWidth().get() + this.wrapperPadding().get() * 2.0f;
    }
    
    @Override
    public String getDefaultRendererName() {
        return "ButtonDropdown";
    }
    
    @Override
    public boolean isHoverComponentRendered() {
        return this.hasHoverComponent() ? super.isHoverComponentRendered() : this.isHovered();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        this.close(false);
        this.lastTimeOpenChanged = 0L;
    }
    
    public LssProperty<Float> arrowWidth() {
        return this.arrowWidth;
    }
    
    public LssProperty<Float> wrapperPadding() {
        return this.wrapperPadding;
    }
    
    @Deprecated
    public void translationKeyPrefix(final String key) {
        this.setTranslationKeyPrefix(key);
    }
    
    public void setTranslationKeyPrefix(String key) {
        if (!(this.entryRenderer instanceof DefaultEntryRenderer)) {
            throw new IllegalStateException("Cannot set translation key prefix for custom entry renderer");
        }
        if (key.endsWith(".")) {
            key = key.substring(0, key.length() - 1);
        }
        ((DefaultEntryRenderer)this.entryRenderer).setTranslationKeyPrefix(key);
    }
    
    @Deprecated
    public static <T> DropdownWidget<T> create(final T selected, final ChangeListener<T> changeListener) {
        final DropdownWidget<T> widget = new DropdownWidget<T>();
        widget.setSelected(selected);
        widget.setChangeListener(changeListener);
        return widget;
    }
    
    @Deprecated
    public static <T> DropdownWidget<T> create(final ChangeListener<T> changeListener) {
        final DropdownWidget<T> widget = new DropdownWidget<T>();
        widget.setChangeListener(changeListener);
        return widget;
    }
    
    static {
        DROPDOWN_POSITION = ModifyReason.of("dropdownPosition");
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<DropdownSetting, DropdownWidget<?>>
    {
        @Override
        public DropdownWidget<?>[] create(final Setting setting, final DropdownSetting annotation, final SettingAccessor accessor) {
            final Class<?> type = accessor.getType();
            final DropdownWidget<Object> widget = new DropdownWidget<Object>();
            widget.setSelected(accessor.get());
            final DropdownWidget<Object> dropdownWidget = widget;
            Objects.requireNonNull(accessor);
            dropdownWidget.setChangeListener(accessor::set);
            final DropdownEntryTranslationPrefix prefixAnnotation = accessor.getField().getAnnotation(DropdownEntryTranslationPrefix.class);
            final DefaultEntryRenderer<Object> entryRenderer = (DefaultEntryRenderer)widget.entryRenderer();
            if (prefixAnnotation == null) {
                entryRenderer.setTranslationKeyPrefix(accessor.setting().getTranslationKey() + ".entries");
            }
            else {
                entryRenderer.setTranslationKeyPrefix(prefixAnnotation.value());
            }
            if (type.isEnum()) {
                for (final Enum<?> enumConstant : (Enum[])type.getEnumConstants()) {
                    widget.add(enumConstant);
                }
            }
            accessor.property().addChangeListener((t, oldValue, newValue) -> widget.setSelected(newValue));
            return new DropdownWidget[] { widget };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[0];
        }
    }
    
    public interface ChangeListener<T>
    {
        void onChange(final T p0);
    }
    
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DropdownEntryTranslationPrefix {
        String value();
    }
    
    @SettingElement(switchable = StringSwitchableHandler.class)
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DropdownSetting {
    }
}
