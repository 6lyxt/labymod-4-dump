// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.spray;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.event.Subscribe;
import net.labymod.api.Laby;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectSprayEvent;
import java.util.Iterator;
import java.util.List;
import net.labymod.core.main.user.shop.spray.model.SprayPack;
import java.util.ArrayList;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.Constants;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import net.labymod.api.event.EventBus;
import net.labymod.core.main.user.shop.spray.model.Spray;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.core.main.user.shop.spray.model.SprayStorage;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;
import net.labymod.api.service.Service;

@Singleton
@Referenceable
public class SprayService extends Service
{
    private static final SprayStorage EMPTY_STORAGE;
    private final Request<JsonElement> sprayRequest;
    private final Short2ObjectMap<Spray> sprays;
    private final SprayRegistry sprayRegistry;
    private SprayStorage sprayStorage;
    
    public SprayService(final EventBus eventBus, final SprayRegistry sprayRegistry) {
        this.sprays = (Short2ObjectMap<Spray>)new Short2ObjectOpenHashMap();
        this.sprayRegistry = sprayRegistry;
        this.sprayRequest = Request.ofGson(JsonElement.class).url(Constants.LegacyUrls.STICKERS, new Object[0]).async(false);
        eventBus.registerListener(this);
    }
    
    @Override
    protected void prepare() {
        this.sprayRequest.execute(this::readData);
    }
    
    private void readData(final Response<JsonElement> response) {
        final JsonElement element = response.getNullable();
        if (element == null || !element.isJsonObject()) {
            SprayService.LOGGER.warn("Failed to retrieve spray data", new Object[0]);
            return;
        }
        this.sprayStorage = (SprayStorage)GsonUtil.DEFAULT_GSON.fromJson(element, (Class)SprayStorage.class);
        final List<SprayPack> packs = this.sprayStorage().getPacks();
        final List<String> names = new ArrayList<String>();
        for (SprayPack pack : packs) {
            names.add(pack.getId() + ":" + pack.getName() + "(Size: " + pack.getSprays().size());
            for (final Spray spray : pack.getSprays()) {
                this.sprays.put((short)spray.getId(), (Object)spray);
            }
        }
        SprayService.LOGGER.debug("Spray Packs: " + String.join(", ", names), new Object[0]);
    }
    
    @Subscribe
    public void onSpray(final LabyConnectSprayEvent event) {
        final Spray spray = this.findSpray(event.getSprayId());
        if (spray == null) {
            return;
        }
        this.sprayRegistry.sprayServer(Laby.references().gameUserService().gameUser(event.getUniqueId()), event.getSprayId(), event.getX(), event.getY(), event.getZ(), event.direction(), event.getRotation());
    }
    
    @Nullable
    public SprayPack findPack(final short id) {
        for (final SprayPack pack : this.sprayStorage().getPacks()) {
            if (pack.getId() == id) {
                return pack;
            }
        }
        return null;
    }
    
    public Short2ObjectMap<Spray> getSprays() {
        return this.sprays;
    }
    
    @Nullable
    public Spray findSpray(final short id) {
        return (Spray)this.sprays.get(id);
    }
    
    public SprayStorage sprayStorage() {
        return (this.sprayStorage == null) ? SprayService.EMPTY_STORAGE : this.sprayStorage;
    }
    
    static {
        EMPTY_STORAGE = new SprayStorage();
    }
}
