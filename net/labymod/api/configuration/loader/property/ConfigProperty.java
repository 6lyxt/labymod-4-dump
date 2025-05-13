// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.loader.property;

import net.labymod.api.Laby;
import net.labymod.api.event.labymod.config.SettingResetEvent;
import java.util.Iterator;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.api.util.function.ChangeListener;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.ApiStatus;
import java.util.function.Consumer;
import net.labymod.api.property.PropertyConvention;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.configuration.settings.SettingHandler;
import net.labymod.api.configuration.settings.type.SettingElement;
import java.lang.reflect.Type;
import net.labymod.api.property.Property;

public class ConfigProperty<T> extends Property<T>
{
    private final Class<T> type;
    private Type genericType;
    private SettingElement setting;
    private SettingHandler handler;
    private boolean withPseudoValue;
    private T pseudoValue;
    @Nullable
    private CustomRequires<T> customRequires;
    private BooleanSupplier visibilitySupplier;
    
    public ConfigProperty(final T value) {
        super(value);
        Objects.requireNonNull(value, "Null provided for ConfigProperty value");
        this.type = (Class<T>)value.getClass();
    }
    
    public ConfigProperty(final T value, final PropertyConvention<T> propertyConvention) {
        super(value, propertyConvention);
        Objects.requireNonNull(value, "Null provided for ConfigProperty value");
        this.type = (Class<T>)value.getClass();
    }
    
    public static <E extends Enum<E>> ConfigProperty<E> createEnum(final E value) {
        return new ConfigProperty<E>(value, new DefaultEnumPropertyConvention<E>(value));
    }
    
    public static <T> ConfigProperty<T> create(final T value, final Consumer<T> consumer) {
        consumer.accept(value);
        return new ConfigProperty<T>(value);
    }
    
    public static <T> ConfigProperty<T> create(final T value) {
        return new ConfigProperty<T>(value);
    }
    
    @ApiStatus.Internal
    public final void withSettings(final SettingElement setting) {
        (this.setting = setting).setHandler(this.handler);
    }
    
    public ConfigProperty<T> withHandler(final SettingHandler handler) {
        this.handler = handler;
        return this;
    }
    
    @Override
    public T get() {
        return this.withPseudoValue ? this.pseudoValue : this.value;
    }
    
    @Override
    public T getOrDefault(final T defaultValue) {
        final T value = this.withPseudoValue ? this.pseudoValue : this.value;
        return (value == null) ? defaultValue : value;
    }
    
    @Override
    public boolean isDefaultValue() {
        return this.isDefaultValue(this.withPseudoValue ? this.pseudoValue : this.value);
    }
    
    @Override
    public void set(T value) {
        if (Objects.equals(this.value, value)) {
            return;
        }
        final boolean initialized = this.setting != null && this.setting.isInitialized() && !this.withPseudoValue;
        if (initialized) {
            final SettingUpdateEvent event = this.fireEvent(Phase.PRE, value);
            value = event.getValue();
        }
        final T prevValue = this.value;
        final boolean changed = !Objects.equals(prevValue, value);
        if (changed) {
            value = this.applyConvention(value);
        }
        this.value = value;
        if (changed && !this.withPseudoValue) {
            for (final ChangeListener<Property<T>, T> listener : this.changeListeners) {
                listener.changed(this, prevValue, value);
            }
        }
        if (initialized) {
            this.fireEvent(Phase.POST, value);
        }
    }
    
    @Override
    public void reset() {
        final boolean initialized = this.setting != null && this.setting.isInitialized();
        if (initialized) {
            Laby.fireEvent(new SettingResetEvent(Phase.PRE, this.setting));
        }
        super.reset();
        if (initialized) {
            Laby.fireEvent(new SettingResetEvent(Phase.POST, this.setting));
        }
    }
    
    public Class<T> getType() {
        return this.type;
    }
    
    @Nullable
    public CustomRequires<T> getCustomRequires() {
        return this.customRequires;
    }
    
    public ConfigProperty<T> customRequires(@Nullable final CustomRequires<T> customRequires) {
        this.customRequires = customRequires;
        return this;
    }
    
    @Nullable
    public BooleanSupplier getVisibilitySupplier() {
        return this.visibilitySupplier;
    }
    
    public ConfigProperty<T> visibilitySupplier(@Nullable final BooleanSupplier visibilitySupplier) {
        this.visibilitySupplier = visibilitySupplier;
        return this;
    }
    
    public ConfigProperty<T> visibilitySupplier(final ConfigProperty<Boolean> property) {
        Objects.requireNonNull(property);
        return this.visibilitySupplier(property::get);
    }
    
    @Nullable
    public Type getGenericType() {
        return this.genericType;
    }
    
    @ApiStatus.Internal
    public void setGenericType(final Type genericType) {
        this.genericType = genericType;
    }
    
    @ApiStatus.Internal
    public void withPseudoValue(final T pseudoValue) {
        this.withPseudoValue = true;
        this.setPseudoValue(pseudoValue);
    }
    
    @ApiStatus.Internal
    public boolean hasPseudoValue() {
        return this.withPseudoValue;
    }
    
    @ApiStatus.Internal
    public T getActualValue() {
        return super.get();
    }
    
    @ApiStatus.Internal
    public void withoutPseudoValue() {
        this.withPseudoValue = false;
        this.setPseudoValue(null);
    }
    
    private SettingUpdateEvent fireEvent(final Phase phase, final T value) {
        return Laby.fireEvent(new SettingUpdateEvent(phase, this.setting, value));
    }
    
    private void setPseudoValue(final T pseudoValue) {
        T value = this.withPseudoValue ? pseudoValue : this.value;
        final T prevValue = this.withPseudoValue ? this.value : this.pseudoValue;
        if (Objects.equals(prevValue, value)) {
            this.pseudoValue = pseudoValue;
            return;
        }
        final boolean initialized = this.setting != null && this.setting.isInitialized();
        if (initialized) {
            final SettingUpdateEvent event = this.fireEvent(Phase.PRE, value);
            value = event.getValue();
        }
        this.pseudoValue = pseudoValue;
        for (final ChangeListener<Property<T>, T> listener : this.changeListeners) {
            listener.changed(this, prevValue, value);
        }
        if (initialized) {
            this.fireEvent(Phase.POST, value);
        }
    }
}
