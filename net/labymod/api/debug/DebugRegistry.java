// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.debug;

import java.util.HashMap;
import net.labymod.api.user.GameUser;
import net.labymod.api.Laby;
import java.util.function.BooleanSupplier;
import java.util.Collections;
import java.util.Collection;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.key.Key;
import java.util.Map;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class DebugRegistry
{
    private static final Map<String, DebugFeature> STATES;
    public static final DebugFeature DEBUG_WINDOWS;
    public static final DebugFeature SURFACE_WIREFRAME;
    public static final DebugFeature PETS_AI;
    public static final DebugFeature PAYLOAD;
    
    private DebugRegistry() {
    }
    
    public static void toggleStates(final Key key) {
        for (final DebugFeature state : DebugRegistry.STATES.values()) {
            state.toggleState(key);
        }
    }
    
    public static Collection<DebugFeature> getDebugFeatures() {
        return Collections.unmodifiableCollection((Collection<? extends DebugFeature>)DebugRegistry.STATES.values());
    }
    
    public static DebugFeature register(final String name, final Key key) {
        final DebugFeature newDebugFeature = new DebugFeature(name, key);
        DebugRegistry.STATES.put(name, newDebugFeature);
        return newDebugFeature;
    }
    
    public static DebugFeature register(final String name, final Key key, final BooleanSupplier permissionCheck) {
        final DebugFeature newDebugFeature = new DebugFeature(name, key, permissionCheck);
        DebugRegistry.STATES.put(name, newDebugFeature);
        return newDebugFeature;
    }
    
    private static boolean isStaffOrCosmeticCreator() {
        final GameUser gameUser = Laby.references().gameUserService().clientGameUser();
        return gameUser != null && gameUser.visibleGroup().isStaffOrCosmeticCreator();
    }
    
    private static boolean isDevelopmentEnvironment() {
        return Laby.labyAPI().labyModLoader().isDevelopmentEnvironment();
    }
    
    static {
        STATES = new HashMap<String, DebugFeature>();
        DEBUG_WINDOWS = register("Debug Windows", Key.D, () -> Laby.labyAPI().labyModLoader().isDevelopmentEnvironment() || Laby.labyAPI().config().other().advanced().debugger());
        SURFACE_WIREFRAME = register("Surface Wireframe", Key.G, DebugRegistry::isStaffOrCosmeticCreator);
        PETS_AI = register("Pets AI", Key.U, DebugRegistry::isStaffOrCosmeticCreator);
        PAYLOAD = register("Custom Payload", Key.O, DebugRegistry::isDevelopmentEnvironment);
    }
}
