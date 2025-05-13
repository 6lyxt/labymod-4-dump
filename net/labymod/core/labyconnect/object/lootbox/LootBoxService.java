// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.lootbox;

import net.labymod.api.service.Registry;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.util.math.vector.DoubleVector3;
import java.util.Optional;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.Minecraft;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.entity.player.Player;
import net.labymod.core.labyconnect.object.lootbox.content.LootBoxContent;
import java.util.UUID;
import net.labymod.core.labyconnect.session.DefaultLabyConnectSession;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Iterator;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.core.labyconnect.object.lootbox.remote.Incentive;
import net.labymod.api.BuildData;
import net.labymod.api.Constants;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.core.labyconnect.object.lootbox.remote.IncentivesIndex;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.Laby;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import net.labymod.api.LabyAPI;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;
import net.labymod.api.service.Service;

@Singleton
@Referenceable
public class LootBoxService extends Service
{
    private final LabyAPI api;
    private final List<LootBoxInventoryItem> inventoryItems;
    private final Int2ObjectMap<LootBox> lootBoxes;
    private int amountAvailable;
    private int amountOpened;
    private boolean featureAvailable;
    private boolean initialized;
    
    public LootBoxService() {
        this.inventoryItems = new ArrayList<LootBoxInventoryItem>();
        this.lootBoxes = (Int2ObjectMap<LootBox>)new Int2ObjectOpenHashMap();
        this.amountAvailable = 0;
        this.amountOpened = 0;
        this.featureAvailable = false;
        this.initialized = false;
        this.api = Laby.labyAPI();
    }
    
    @Override
    protected void prepare() {
        super.prepare();
        final Response<IncentivesIndex> response = Request.ofGson(IncentivesIndex.class).url(Constants.Urls.INCENTIVES_DATA, new Object[0]).userAgent(BuildData.getUserAgent()).executeSync();
        if (!response.isPresent()) {
            LootBoxService.LOGGER.error("Failed to load incentives index!", new Object[0]);
            return;
        }
        final IncentivesIndex incentivesIndex = response.get();
        final List<Incentive> incentives = incentivesIndex.getIncentives();
        for (final Incentive incentive : incentives) {
            try {
                this.registerIncentive(incentive);
            }
            catch (final LootBoxException exception) {
                LootBoxService.LOGGER.error("Failed to register incentive {}", incentive.getName(), exception);
            }
        }
        this.initialized = !this.lootBoxes.isEmpty();
        LootBoxService.LOGGER.info("Service initialized with {} incentives. ({})", this.lootBoxes.size(), String.join(", ", this.getLootBoxIds()));
    }
    
    private List<String> getLootBoxIds() {
        final List<String> list = new ArrayList<String>();
        for (final LootBox entry : this.lootBoxes.values()) {
            list.add(entry.getId());
        }
        return list;
    }
    
    private void registerIncentive(final Incentive incentive) {
        final LootBox lootBox = new LootBox(incentive.getId(), incentive.getName());
        lootBox.loadModel();
        this.registerLootBox(lootBox);
    }
    
    public void tick() {
        final List<LootBoxInventoryItem> inventoryItems = this.getInventoryItems();
        for (final LootBoxInventoryItem inventoryItem : inventoryItems) {
            final LootBox lootBox = inventoryItem.getLootBox();
            if (lootBox == null) {
                continue;
            }
            lootBox.tick();
        }
    }
    
    public void load() {
        for (final LootBox lootBox : this.lootBoxes.values()) {
            lootBox.loadModel();
        }
    }
    
    public void requestIncentive(final int typeId) {
        final DefaultLabyConnectSession session = (DefaultLabyConnectSession)this.api.labyConnect().getSession();
        if (session != null) {
            session.requestIncentive(typeId);
        }
    }
    
    public void spawnLootBox(final UUID playerId, final LootBoxContent content, final int lootBoxType) {
        if (!Laby.labyAPI().config().ingame().lootBoxes().get()) {
            return;
        }
        final Minecraft minecraft = this.api.minecraft();
        final MinecraftCamera camera = minecraft.getCamera();
        final Optional<Player> optional = minecraft.clientWorld().getPlayer(playerId);
        if (optional.isEmpty() || camera == null) {
            return;
        }
        final Player player = optional.get();
        final DoubleVector3 position = player.position().toDoubleVector3();
        final float yaw = player.getRotationYaw();
        final float rotation = MathHelper.toRadiansFloat(yaw + 90.0f);
        final float cos = MathHelper.cos(rotation);
        final float sin = MathHelper.sin(rotation);
        final float distance = 3.0f;
        position.add(cos * distance, 0.0, sin * distance);
        final ClientWorld world = minecraft.clientWorld();
        final int y2 = world.getTopBlockYOf(MathHelper.floor(position.getX()), MathHelper.floor(position.getY()), MathHelper.floor(position.getZ()));
        position.setY(y2 + ((y2 >= 1) ? 1 : 0));
        if (player == minecraft.getClientPlayer()) {
            Laby.labyAPI().minecraft().sounds().playSound(Constants.Resources.SOUND_LOOTBOX_OPEN, 0.1f, 1.0f, position);
        }
        ((Registry<LootBoxObject>)Laby.references().worldObjectRegistry()).register(new LootBoxObject(player, position, yaw, content, lootBoxType));
    }
    
    public void openLootBox(final int type) {
        synchronized (this) {
            --this.amountAvailable;
            ++this.amountOpened;
            for (final LootBoxInventoryItem inventoryItem : this.inventoryItems) {
                if (inventoryItem.getType() == type && inventoryItem.isAvailable()) {
                    inventoryItem.setAvailable(false);
                    break;
                }
            }
        }
    }
    
    public void updateInventory(final int amountAvailable, final int amountOpened, final List<LootBoxInventoryItem> items) {
        synchronized (this) {
            this.amountAvailable = amountAvailable;
            this.amountOpened = amountOpened;
            this.inventoryItems.clear();
            this.inventoryItems.addAll(items);
            this.featureAvailable = true;
            LootBoxService.LOGGER.info("Updated inventory with {} items.", this.inventoryItems.size());
        }
    }
    
    @Nullable
    public LootBox byId(final int id) {
        return (LootBox)this.lootBoxes.get(id);
    }
    
    public int getAmountAvailable() {
        synchronized (this) {
            return this.amountAvailable;
        }
    }
    
    public int getAmountOpened() {
        synchronized (this) {
            return this.amountOpened;
        }
    }
    
    public List<LootBoxInventoryItem> getInventoryItems() {
        return this.inventoryItems;
    }
    
    public boolean isFeatureAvailable() {
        return this.initialized && this.featureAvailable;
    }
    
    private void registerLootBox(final LootBox lootBox) {
        this.lootBoxes.put(lootBox.getType(), (Object)lootBox);
    }
}
