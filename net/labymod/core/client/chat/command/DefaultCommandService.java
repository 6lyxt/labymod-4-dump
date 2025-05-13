// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.chat.command;

import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.util.I18n;
import java.util.Locale;
import java.util.Iterator;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.HashMap;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.chat.command.InjectedSubCommand;
import java.util.Set;
import net.labymod.api.client.chat.command.Command;
import java.util.Map;
import net.labymod.api.LabyAPI;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.addon.DefaultAddonService;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.chat.command.CommandService;

@Singleton
@Implements(CommandService.class)
public class DefaultCommandService implements CommandService
{
    private static final DefaultAddonService ADDON_SERVICE;
    private final Logging logging;
    private final LabyAPI labyAPI;
    private final Map<Class<? extends Command>, Command> commands;
    private final Set<InjectedSubCommand> injectedCommands;
    
    @Inject
    public DefaultCommandService(final LabyAPI labyAPI, final EventBus eventBus) {
        this.logging = Logging.create(CommandService.class);
        this.labyAPI = labyAPI;
        this.commands = new HashMap<Class<? extends Command>, Command>();
        this.injectedCommands = new HashSet<InjectedSubCommand>();
        eventBus.registerListener(new CommandSendListener(this));
    }
    
    @Override
    public <T extends Command> T register(final T command) {
        if (command instanceof final InjectedSubCommand injectedSubCommand) {
            this.injectedCommands.add(injectedSubCommand);
            return command;
        }
        final Command previousValue = this.commands.putIfAbsent(command.getClass(), command);
        return (T)((previousValue == null) ? command : previousValue);
    }
    
    @Override
    public void unregister(final Class<? extends Command> commandClass) {
        final Iterator<Class<? extends Command>> iterator = this.commands.keySet().iterator();
        Class<? extends Command> command = null;
        while (iterator.hasNext()) {
            command = iterator.next();
            if (command == commandClass) {
                this.commands.remove(command);
                return;
            }
        }
        this.injectedCommands.removeIf(command -> command.getClass() == commandClass);
    }
    
    @Override
    public boolean fireCommand(final String prefix, final String[] arguments) {
        final String lowercasePrefix = prefix.toLowerCase(Locale.ROOT);
        for (final Command command : this.commands.values()) {
            if (this.commandMatches(command, lowercasePrefix) && this.fireCommandInternal(command, prefix, arguments)) {
                return true;
            }
        }
        if (this.injectedCommands.isEmpty()) {
            return false;
        }
        final String message = prefix + " " + String.join(" ", (CharSequence[])arguments);
        final String lowerCaseMessage = message.toLowerCase(Locale.ROOT);
        for (InjectedSubCommand command2 : this.injectedCommands) {
            if (lowerCaseMessage.startsWith(command2.getInjectionPrefix())) {
                if (!DefaultCommandService.ADDON_SERVICE.isEnabled(this.labyAPI.getNamespace(command2))) {
                    continue;
                }
                final String subMessage = message.substring(command2.getInjectionPrefix().length() + 1);
                if (subMessage.length() == 0) {
                    continue;
                }
                final String[] messageSplit = subMessage.split(" ");
                final String subPrefix = messageSplit[0];
                final String lowercaseSubPrefix = subPrefix.toLowerCase(Locale.ROOT);
                if (!command2.getPrefix().equals(lowercaseSubPrefix)) {
                    boolean found = false;
                    for (final String alias : command2.getAliases()) {
                        if (alias.equals(lowercaseSubPrefix)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        continue;
                    }
                }
                final String[] subArguments = new String[messageSplit.length - 1];
                System.arraycopy(messageSplit, 1, subArguments, 0, subArguments.length);
                if (this.executeCommand(command2.getClass(), command2, subPrefix, subArguments)) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    @Override
    public boolean fireCommand(final Class<? extends Command> commandClass, final String usedPrefix, final String[] arguments) {
        final Command command = this.commands.get(commandClass);
        if (command == null) {
            throw new UnsupportedOperationException("The Command \"" + commandClass.getSimpleName() + "\" was not registered as an command.");
        }
        return this.fireCommandInternal(commandClass, command, usedPrefix, arguments);
    }
    
    private boolean fireCommandInternal(final Command command, final String usedPrefix, final String[] arguments) {
        return this.fireCommandInternal(command.getClass(), command, usedPrefix, arguments);
    }
    
    private boolean fireCommandInternal(final Class<? extends Command> commandClass, final Command command, final String usedPrefix, final String[] arguments) {
        final String namespace = this.labyAPI.getNamespace(commandClass);
        if (!DefaultCommandService.ADDON_SERVICE.isEnabled(namespace)) {
            String translatedNamespace = I18n.getTranslation(namespace + ".settings.name", new Object[0]);
            if (translatedNamespace == null) {
                translatedNamespace = namespace;
            }
            final TextComponent component = Component.text(I18n.translate("labymod.command.disabled", usedPrefix, translatedNamespace, Key.L_SHIFT.getName()), NamedTextColor.RED);
            this.labyAPI.minecraft().chatExecutor().displayClientMessage(component);
            return true;
        }
        return this.executeCommand(commandClass, command, usedPrefix, arguments);
    }
    
    private boolean executeCommand(final Class<? extends Command> commandClass, final Command command, final String prefix, final String[] arguments) {
        if (arguments.length > 0) {
            final String subCommandPrefix = arguments[0];
            String[] subCommandArguments = null;
            for (final SubCommand subCommand : command.getSubCommands()) {
                if (!this.commandMatches(subCommand, subCommandPrefix)) {
                    continue;
                }
                if (subCommandArguments == null) {
                    subCommandArguments = new String[arguments.length - 1];
                    System.arraycopy(arguments, 1, subCommandArguments, 0, subCommandArguments.length);
                }
                if (this.executeCommand(subCommand.getClass(), subCommand, subCommandPrefix, subCommandArguments)) {
                    return true;
                }
            }
        }
        try {
            return command.execute(prefix, arguments);
        }
        catch (final Exception e) {
            final TextComponent component = Component.text(I18n.translate("labymod.command.exception", commandClass.getSimpleName(), e.getClass().getSimpleName()), NamedTextColor.RED);
            this.labyAPI.minecraft().chatExecutor().displayClientMessage(component);
            this.logging.error("An exception occurred while executing the command " + commandClass.getSimpleName(), (Throwable)e);
            return true;
        }
    }
    
    private boolean commandMatches(final Command command, final String prefix) {
        if (command.getPrefix().equals(prefix)) {
            return true;
        }
        for (final String alias : command.getAliases()) {
            if (alias.equals(prefix)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        ADDON_SERVICE = DefaultAddonService.getInstance();
    }
}
