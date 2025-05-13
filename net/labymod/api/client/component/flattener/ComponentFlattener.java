// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.flattener;

import java.util.function.Function;
import net.labymod.api.client.component.ScoreComponent;
import net.labymod.api.client.component.KeybindComponent;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.IconComponent;
import java.util.function.Consumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.builder.Buildable;

public interface ComponentFlattener extends Buildable<Builder>
{
    public static final String BASIC_FLATTENER_IDENTIFIER = "basic";
    public static final String BASIC_COMPLEX_FLATTENER_IDENTIFIER = "basic_complex";
    public static final String BASIC_UNKNOWN_FLATTENER_IDENTIFIER = "basic_unknown";
    public static final String ONLY_TEXT_FLATTENER_IDENTIFIER = "text_only";
    public static final ComponentFlattener BASIC = builder().withIdentifier("basic").mapper(TextComponent.class, TextComponent::getText).mapper(TranslatableComponent.class, TranslatableComponent::getKey).mapper(KeybindComponent.class, KeybindComponent::getKeybind).mapper(IconComponent.class, component -> "").mapper(ScoreComponent.class, ScoreComponent::getName).build();
    public static final ComponentFlattener TEXT_ONLY = builder().withIdentifier("text_only").mapper(TextComponent.class, TextComponent::getText).build();
    
    default Builder builder() {
        return new DefaultComponentFlattener.Builder();
    }
    
    void flatten(final Component p0, final FlattenerListener p1);
    
    String getIdentifier();
    
    default void flatten(final Component input, final Consumer<Component> componentConsumer, final Consumer<String> textConsumer) {
        this.flatten(input, new FlattenerListener(this) {
            @Override
            public void push(final Component source) {
                componentConsumer.accept(source);
            }
            
            @Override
            public void component(final String text) {
                textConsumer.accept(text);
            }
        });
    }
    
    public interface Builder
    {
        Builder withIdentifier(final String p0);
        
         <T extends Component> Builder mapper(final Class<T> p0, final Function<T, String> p1);
        
         <T extends Component> Builder complexMapper(final Class<T> p0, final ComplexMapper<T> p1);
        
        Builder unknownMapper(final Function<Component, String> p0);
        
        ComponentFlattener build();
    }
}
