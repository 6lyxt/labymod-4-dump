// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.command;

import net.labymod.api.client.component.BaseComponent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.I18n;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.TranslatableComponent;
import java.util.Collections;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.component.Component;
import java.util.List;
import net.labymod.api.LabyAPI;

public abstract class Command
{
    protected final LabyAPI labyAPI;
    protected final String prefix;
    protected final String[] aliases;
    private final List<SubCommand> subCommands;
    protected String translationKeyPrefix;
    protected Component messagePrefix;
    private List<SubCommand> unmodifiableSubCommands;
    
    protected Command(@NotNull final String prefix, @NotNull final String... aliases) {
        this.labyAPI = Laby.labyAPI();
        Objects.requireNonNull(prefix, "prefix");
        Objects.requireNonNull(aliases, "aliases");
        this.prefix = prefix.toLowerCase(Locale.ROOT);
        this.aliases = new String[aliases.length];
        for (int i = 0; i < aliases.length; ++i) {
            this.aliases[i] = aliases[i].toLowerCase(Locale.ROOT);
        }
        this.subCommands = new ArrayList<SubCommand>();
    }
    
    public abstract boolean execute(final String p0, final String[] p1);
    
    public List<String> complete(final String[] arguments) {
        if (arguments.length == 1) {
            final List<String> tabCompletions = new ArrayList<String>();
            tabCompletions.add(this.prefix);
            tabCompletions.addAll(Arrays.asList(this.aliases));
            return tabCompletions;
        }
        return Collections.emptyList();
    }
    
    protected final void sendMessage(final String message) {
        this.labyAPI.minecraft().chatExecutor().chat(message);
    }
    
    protected final void displayMessage(final String message) {
        this.displayMessage(Component.text(message));
    }
    
    protected final void displaySyntax() {
        Objects.requireNonNull(this.translationKeyPrefix, "TranslationKey cannot be null");
        this.displaySyntaxTranslatable(this.getTranslationKey("syntax"));
    }
    
    protected final void displaySyntax(final String subCommand) {
        Objects.requireNonNull(this.translationKeyPrefix, "TranslationKey cannot be null");
        this.displaySyntaxTranslatable(this.getTranslationKey(subCommand + ".syntax"));
    }
    
    private void displaySyntaxTranslatable(final String translationKey) {
        this.displayMessage(((BaseComponent<Component>)Component.translatable("labymod.command.usage", new Component[0]).args(((BaseComponent<Component>)Component.text("/").append(Component.text(this.prefix)).append(Component.text(" ")).append(Component.translatable(translationKey, new Component[0])).color(NamedTextColor.GRAY)).decoration(TextDecoration.BOLD, false)).color(NamedTextColor.YELLOW)).decorate(TextDecoration.BOLD));
    }
    
    protected final void displayMessage(final Component message) {
        Component component = Component.empty();
        if (this.messagePrefix != null) {
            component = component.append(this.messagePrefix).append(Component.text(" "));
        }
        this.labyAPI.minecraft().chatExecutor().displayClientMessage(component.append(message));
    }
    
    protected final void displayTranslatable(final String key, final TextColor textColor, final Object... arguments) {
        String translationKey = key;
        if (this.translationKeyPrefix != null) {
            translationKey = this.translationKeyPrefix + "." + key;
        }
        final String message = I18n.translate(translationKey, arguments);
        this.displayMessage(Component.text(message, textColor));
    }
    
    protected final void displayTranslatable(final String key, final Style style, final Component... arguments) {
        String translationKey = key;
        if (this.translationKeyPrefix != null) {
            translationKey = this.translationKeyPrefix + "." + key;
        }
        this.displayMessage(((BaseComponent<Component>)Component.translatable(translationKey, arguments)).style(style));
    }
    
    protected final void displayTranslatable(final String key, final TextColor textColor, final Component... arguments) {
        String translationKey = key;
        if (this.translationKeyPrefix != null) {
            translationKey = this.translationKeyPrefix + "." + key;
        }
        this.displayMessage(((BaseComponent<Component>)Component.translatable(translationKey, arguments)).style(Style.empty().color(textColor)));
    }
    
    protected final String getTranslationKey() {
        return this.translationKeyPrefix;
    }
    
    protected String getTranslationKey(final String key) {
        Objects.requireNonNull(this.translationKeyPrefix, "TranslationKey cannot be null");
        return String.format(Locale.ROOT, "%s.%s", this.translationKeyPrefix, key);
    }
    
    protected void openActivity(final ScreenInstance screenInstance) {
        this.labyAPI.minecraft().executeNextTick(() -> this.labyAPI.minecraft().minecraftWindow().displayScreen(screenInstance));
    }
    
    public final String getPrefix() {
        return this.prefix;
    }
    
    public final String[] getAliases() {
        return this.aliases;
    }
    
    public final List<SubCommand> getSubCommands() {
        if (this.unmodifiableSubCommands == null) {
            this.unmodifiableSubCommands = Collections.unmodifiableList((List<? extends SubCommand>)this.subCommands);
        }
        return this.unmodifiableSubCommands;
    }
    
    public final <T extends Command> T messagePrefix(final Component messagePrefix) {
        this.messagePrefix = messagePrefix;
        return (T)this;
    }
    
    public final <T extends Command> T translationKey(final String key) {
        this.translationKeyPrefix = key;
        return (T)this;
    }
    
    protected final <T extends Command> T withSubCommand(@NotNull final SubCommand subCommand) {
        Objects.requireNonNull(subCommand, "subCommand");
        subCommand.withParent(this);
        this.subCommands.add(subCommand);
        return (T)this;
    }
}
