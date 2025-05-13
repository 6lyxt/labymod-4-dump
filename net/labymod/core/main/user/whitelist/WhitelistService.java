// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.whitelist;

import net.labymod.api.util.io.web.request.AbstractRequest;
import java.util.UUID;
import net.labymod.core.main.user.DefaultGameUser;
import java.util.function.Consumer;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.io.web.request.Response;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.zip.Inflater;
import java.util.Objects;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.Constants;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import java.util.ArrayList;
import java.util.List;
import it.unimi.dsi.fastutil.longs.LongList;
import net.labymod.api.service.Service;

public class WhitelistService extends Service
{
    private final LongList whitelistUsers;
    private final List<Runnable> tasks;
    private boolean loaded;
    
    public WhitelistService() {
        this.tasks = new ArrayList<Runnable>();
        this.whitelistUsers = (LongList)new LongArrayList();
        this.loaded = false;
    }
    
    public void prepare() {
        final Response<WebInputStream> response = ((AbstractRequest<WebInputStream, R>)((AbstractRequest<T, InputStreamRequest>)((AbstractRequest<T, InputStreamRequest>)Request.ofInputStream()).url(Constants.LegacyUrls.WHITELIST, new Object[0])).async(false)).executeSync();
        final WebInputStream stream = response.getNullable();
        try {
            if (response.hasException()) {
                WhitelistService.LOGGER.error("Failed to load whitelist", response.exception());
                return;
            }
            Objects.requireNonNull(stream);
            final Inflater inflater = new Inflater();
            inflater.setInput(stream.readAllBytes());
            try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                final byte[] buffer = new byte[1024];
                while (!inflater.finished()) {
                    final int inflate = inflater.inflate(buffer);
                    if (inflate == 0) {
                        throw new IOException("The requested whitelist is corrupted");
                    }
                    output.write(buffer, 0, inflate);
                }
                final byte[] decompressedData = output.toByteArray();
                final LongList list = (LongList)new LongArrayList();
                for (int i = 0; i < decompressedData.length; i += 8) {
                    long uniqueIdPart = 0L;
                    for (int j = 0; j < 8; ++j) {
                        uniqueIdPart += ((long)decompressedData[i + j] & 0xFFL) << 8 * j;
                    }
                    list.add(uniqueIdPart);
                }
                this.load(list);
            }
        }
        catch (final Exception exception) {
            WhitelistService.LOGGER.error("Couldn't load whitelist", exception);
        }
    }
    
    @Override
    public void onServiceUnload() {
        this.whitelistUsers.clear();
        this.loaded = false;
    }
    
    public void load(final LongList whitelistUsers) {
        this.whitelistUsers.addAll(whitelistUsers);
        this.loaded = true;
        this.tasks.forEach(Runnable::run);
        this.tasks.clear();
    }
    
    public void fetch(final GameUser user, final Consumer<Boolean> callback) {
        final GameUser.WhitelistState state = user.whitelistState();
        if (state.isLoaded()) {
            callback.accept(state.isWhitelisted());
            return;
        }
        if (state == GameUser.WhitelistState.UNKNOWN) {
            this.fetch(user.getUniqueId(), contains -> {
                ((DefaultGameUser)user).setWhitelistState(GameUser.WhitelistState.of(contains));
                callback.accept(contains);
            });
        }
    }
    
    private void fetch(final UUID uniqueId, final Consumer<Boolean> callback) {
        if (!this.loaded) {
            this.tasks.add(() -> this.fetch(uniqueId, callback));
            return;
        }
        callback.accept(this.contains(uniqueId));
    }
    
    private boolean contains(final UUID uniqueId) {
        final long uuidPart = uniqueId.getMostSignificantBits() >> 32 & 0xFFFFFFFFL;
        return this.whitelistUsers.contains(uuidPart);
    }
    
    public void reset() {
        this.loaded = false;
        this.whitelistUsers.clear();
        this.tasks.clear();
    }
}
