// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.component.mapper;

import net.labymod.api.loader.LabyModLoader;
import net.labymod.core.loader.DefaultLabyModLoader;
import java.util.regex.Matcher;
import java.util.List;
import net.labymod.api.Laby;
import java.util.function.Consumer;
import net.labymod.api.adventure.ComponentUtils;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.Component;
import java.util.regex.Pattern;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.flattener.ComplexMapper;

public class TranslatableComponentMapper implements ComplexMapper<TranslatableComponent>
{
    private static final Logging LOGGER;
    private static final Pattern FORMAT_PATTERN;
    
    private Component mergeStyleRecursive(final Component component, final Style style) {
        return ComponentUtils.mergeStyleRecursive(component, style, Style.Merge.Strategy.IF_ABSENT_ON_TARGET);
    }
    
    @Override
    public void map(final TranslatableComponent component, final Consumer<Component> consumer) {
        final String translation = Laby.labyAPI().minecraft().getTranslationOrDefault(component.getKey(), component.getFallback());
        final List<Component> args = component.getArguments();
        if (args.isEmpty()) {
            consumer.accept(Component.text(translation.replace("%%", "%"), component.style()));
            return;
        }
        final Matcher matcher = TranslatableComponentMapper.FORMAT_PATTERN.matcher(translation);
        final Style style = component.style();
        int incrementalArgIndex = 0;
        int currentIndex;
        int end;
        for (currentIndex = 0; matcher.find(currentIndex); currentIndex = end) {
            final int start = matcher.start();
            end = matcher.end();
            if (start > currentIndex) {
                final String text = translation.substring(currentIndex, start);
                if (text.indexOf(37) != -1) {
                    throw new IllegalArgumentException();
                }
                consumer.accept(Component.text(text, style));
            }
            final String text = matcher.group(2);
            final String matchedString = translation.substring(start, end);
            if ("%".equals(text) && "%%".equals(matchedString)) {
                consumer.accept(Component.text("%", style));
            }
            else {
                if (!"s".equals(text) && !"d".equals(text)) {
                    throw new IllegalArgumentException("Unsupported format: '" + matchedString);
                }
                final String customIndex = matcher.group(1);
                final int index = (customIndex != null) ? (Integer.parseInt(customIndex) - 1) : incrementalArgIndex++;
                if (index < args.size()) {
                    final Component arg = args.get(index);
                    consumer.accept(this.mergeStyleRecursive(arg, arg.style().merge(component.style(), Style.Merge.Strategy.IF_ABSENT_ON_TARGET)));
                }
            }
        }
        if (currentIndex < translation.length()) {
            final String var10 = translation.substring(currentIndex);
            if (var10.indexOf(37) != -1) {
                throw new IllegalArgumentException();
            }
            consumer.accept(Component.text(var10, style));
        }
    }
    
    @Override
    public void exception(final TranslatableComponent value, final Consumer<Component> consumer, final Exception e) {
        TranslatableComponentMapper.LOGGER.error(e.getMessage(), new Object[0]);
        consumer.accept(Component.text(value.getKey(), value.style()));
    }
    
    static {
        LOGGER = Logging.create(TranslatableComponentMapper.class, () -> {
            final LabyModLoader loader = DefaultLabyModLoader.getInstance();
            return loader.isLabyModDevelopmentEnvironment() || loader.isAddonDevelopmentEnvironment();
        });
        FORMAT_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
    }
}
