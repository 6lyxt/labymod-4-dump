// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.world.phys.hit;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.Laby;
import net.labymod.api.client.world.DynamicBossBarProgressHandler;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.world.BossBarProgressHandler;
import net.labymod.api.client.world.BossBarOverlay;
import net.labymod.api.client.world.BossBarColor;
import java.util.UUID;
import net.labymod.api.client.world.BossBar;

public class VersionedBossBar implements BossBar
{
    private static final UUID WITHER;
    private static final UUID ENDER_DRAGON;
    private static final UUID OTHER;
    private uc lastDisplayData;
    private UUID uuid;
    private final BossBarColor bossBarColor;
    private final BossBarOverlay bossBarOverlay;
    private final BossBarProgressHandler progressHandler;
    private Component displayName;
    
    public VersionedBossBar(final uc displayData) {
        this.setIdentifier(displayData);
        this.updateDisplayName();
        this.bossBarColor = BossBarColor.PINK;
        this.bossBarOverlay = BossBarOverlay.PROGRESS;
        this.progressHandler = new DynamicBossBarProgressHandler(t -> {}, () -> {
            final uc currentDisplayData = this.lastDisplayData;
            if (currentDisplayData == null) {
                return 1.0f;
            }
            else {
                return currentDisplayData.bn() / currentDisplayData.bu();
            }
        });
    }
    
    public void updateDisplayName() {
        this.displayName = Laby.references().componentMapper().fromMinecraftComponent((this.lastDisplayData == null) ? Component.text("Dummy") : this.lastDisplayData.f_());
    }
    
    @NotNull
    @Override
    public UUID getIdentifier() {
        return this.uuid;
    }
    
    @Override
    public Component displayName() {
        return this.displayName;
    }
    
    @Override
    public BossBarColor bossBarColor() {
        return this.bossBarColor;
    }
    
    @Override
    public BossBarOverlay bossBarOverlay() {
        return this.bossBarOverlay;
    }
    
    @Override
    public BossBarProgressHandler progressHandler() {
        return this.progressHandler;
    }
    
    public void setIdentifier(final uc displayData) {
        this.lastDisplayData = displayData;
        if (displayData instanceof ug) {
            this.uuid = VersionedBossBar.ENDER_DRAGON;
        }
        else if (displayData instanceof uk) {
            this.uuid = VersionedBossBar.WITHER;
        }
        else {
            this.uuid = VersionedBossBar.OTHER;
        }
        this.updateDisplayName();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final VersionedBossBar that = (VersionedBossBar)o;
        if (this.lastDisplayData == null || that.lastDisplayData == null) {
            return this.lastDisplayData == null && that.lastDisplayData == null;
        }
        return Objects.equals(this.lastDisplayData.f_(), that.lastDisplayData.f_()) && Objects.equals(this.lastDisplayData.bn(), that.lastDisplayData.bn()) && Objects.equals(this.lastDisplayData.bu(), that.lastDisplayData.bu());
    }
    
    public uc getLastDisplayData() {
        return this.lastDisplayData;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.lastDisplayData);
    }
    
    static {
        WITHER = UUID.fromString("8156883e-756e-4912-9063-8a871738ed3e");
        ENDER_DRAGON = UUID.fromString("d34aa2b8-31da-4d26-9655-e33c143f096c");
        OTHER = UUID.fromString("34e57efa-5783-46c7-a9fc-890296aaba1f");
    }
}
