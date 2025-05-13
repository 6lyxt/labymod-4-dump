// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.debug;

import net.labymod.api.client.component.BaseComponent;
import java.util.HashMap;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.user.group.Group;
import java.util.UUID;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import net.labymod.api.Laby;
import net.labymod.api.user.GameUser;
import java.util.Map;
import java.util.function.Supplier;
import net.labymod.api.util.logging.Logging;

public final class InvalidCosmeticDataDebugger
{
    private static final String PREFIX = "[INVALID COSMETIC DATA] ";
    private static final Logging LOGGER;
    private static final Supplier<String> THREAD_NAME;
    private static final Map<String, GameUser> USERS;
    
    public static void start(final GameUser user) {
        if (!isStaffOrCosmeticCreator()) {
            return;
        }
        final String name = InvalidCosmeticDataDebugger.THREAD_NAME.get();
        executeOnRenderThread(() -> addUser(name, user));
    }
    
    public static void log(final String message, final Object... args) {
        if (!isStaffOrCosmeticCreator()) {
            return;
        }
        final String name = InvalidCosmeticDataDebugger.THREAD_NAME.get();
        executeOnRenderThread(() -> {
            final GameUser gameUser = InvalidCosmeticDataDebugger.USERS.get(name);
            if (gameUser == null) {
                InvalidCosmeticDataDebugger.LOGGER.error("[INVALID COSMETIC DATA] Hmm, that should not happen. " + message, args);
            }
            else {
                final UUID uniqueId = gameUser.getUniqueId();
                InvalidCosmeticDataDebugger.LOGGER.error("[INVALID COSMETIC DATA] [User " + String.valueOf(uniqueId) + "]" + message, args);
                Laby.labyAPI().labyNetController().loadNameByUniqueId(uniqueId, result -> {
                    final String shortUuid = uniqueId.toString().replace("-", "").substring(0, 16);
                    Laby.labyAPI().notificationController().push(Notification.builder().title(Component.text("Invalid Cosmetic Data")).text(((BaseComponent<Component>)Component.text("User \"" + (String)result.getOrDefault(shortUuid) + "\" has an invalid cosmetic.").append(Component.newline())).append(Component.text((args.length >= 1) ? String.valueOf(args[0]) : "Unknown Cosmetic"))).build());
                });
            }
        });
    }
    
    public static void end() {
        if (!isStaffOrCosmeticCreator()) {
            return;
        }
        final String name = InvalidCosmeticDataDebugger.THREAD_NAME.get();
        executeOnRenderThread(() -> removeUser(name));
    }
    
    private static void addUser(final String name, final GameUser user) {
        InvalidCosmeticDataDebugger.USERS.put(name, user);
    }
    
    private static void removeUser(final String name) {
        InvalidCosmeticDataDebugger.USERS.remove(name);
    }
    
    private static void executeOnRenderThread(final Runnable runnable) {
        Laby.labyAPI().minecraft().executeOnRenderThread(runnable);
    }
    
    private static boolean isStaffOrCosmeticCreator() {
        final GameUser gameUser = Laby.labyAPI().gameUserService().clientGameUser();
        if (gameUser == null) {
            return false;
        }
        final Group visibleGroup = gameUser.visibleGroup();
        return visibleGroup != null && visibleGroup.isStaffOrCosmeticCreator();
    }
    
    static {
        LOGGER = Logging.create(InvalidCosmeticDataDebugger.class);
        THREAD_NAME = (() -> Thread.currentThread().getName());
        USERS = new HashMap<String, GameUser>();
    }
}
