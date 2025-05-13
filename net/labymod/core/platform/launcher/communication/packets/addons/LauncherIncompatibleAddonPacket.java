// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.platform.launcher.communication.packets.addons;

import java.util.Iterator;
import net.labymod.serverapi.api.payload.io.PayloadWriter;
import java.util.ArrayList;
import net.labymod.serverapi.api.payload.io.PayloadReader;
import java.util.List;
import net.labymod.core.platform.launcher.communication.LauncherPacket;

public class LauncherIncompatibleAddonPacket implements LauncherPacket
{
    private List<IncompatibleAddon> incompatibleAddonList;
    
    public LauncherIncompatibleAddonPacket() {
    }
    
    public LauncherIncompatibleAddonPacket(final List<IncompatibleAddon> incompatibleAddonList) {
        this.incompatibleAddonList = incompatibleAddonList;
    }
    
    @Override
    public void read(final PayloadReader reader) {
        this.incompatibleAddonList = new ArrayList<IncompatibleAddon>();
        for (int decisions = reader.readVarInt(), i = 0; i < decisions; ++i) {
            final String namespace = reader.readString();
            final boolean disableIncompatible = reader.readBoolean();
            this.incompatibleAddonList.add(new IncompatibleAddon(namespace, disableIncompatible));
        }
    }
    
    @Override
    public void write(final PayloadWriter writer) {
        writer.writeVarInt(this.incompatibleAddonList.size());
        for (final IncompatibleAddon incompatibleAddon : this.incompatibleAddonList) {
            writer.writeString(incompatibleAddon.getNamespace());
            writer.writeVarInt(incompatibleAddon.dependencies.length);
            for (final String dependency : incompatibleAddon.dependencies) {
                writer.writeString(dependency);
            }
            writer.writeVarInt(incompatibleAddon.incompatibleNamespaces.length);
            for (final String incompatible : incompatibleAddon.incompatibleNamespaces) {
                writer.writeString(incompatible);
            }
        }
    }
    
    public List<IncompatibleAddon> getIncompatibleAddons() {
        return this.incompatibleAddonList;
    }
    
    public static class IncompatibleAddon
    {
        private final String namespace;
        private final String[] dependencies;
        private final String[] incompatibleNamespaces;
        private boolean disableIncompatible;
        
        public IncompatibleAddon(final String namespace, final String[] dependencies, final String[] incompatibleNamespaces) {
            this.namespace = namespace;
            this.dependencies = dependencies;
            this.incompatibleNamespaces = incompatibleNamespaces;
            this.disableIncompatible = false;
        }
        
        public IncompatibleAddon(final String namespace, final boolean disableIncompatible) {
            this(namespace, null, null);
            this.disableIncompatible = disableIncompatible;
        }
        
        public String getNamespace() {
            return this.namespace;
        }
        
        public String[] getIncompatibleNamespaces() {
            return this.incompatibleNamespaces;
        }
        
        public boolean disableIncompatible() {
            return this.disableIncompatible;
        }
    }
}
