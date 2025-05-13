// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.lang.reflect.Type;
import net.labymod.api.util.GsonUtil;
import java.io.Reader;
import com.google.gson.stream.JsonReader;
import java.nio.file.Files;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.Constants;
import java.io.IOException;
import java.util.UUID;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.labyconnect.TokenStorage;

@Singleton
@Implements(TokenStorage.class)
public class DefaultTokenStorage implements TokenStorage
{
    private Storage storage;
    
    @Inject
    public DefaultTokenStorage() {
        this.storage = new Storage();
        try {
            this.load();
            this.storage.removeExpiredTokens();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Token getToken(final Purpose purpose, final UUID uniqueId) {
        return this.storage.getToken(purpose, uniqueId);
    }
    
    public void updateToken(final Purpose purpose, final UUID uniqueId, final Token token) {
        this.storage.updateToken(purpose, uniqueId, token);
        try {
            this.save();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    public void load() throws IOException {
        if (IOUtil.exists(Constants.Files.TOKENS)) {
            try (final BufferedReader reader = Files.newBufferedReader(Constants.Files.TOKENS);
                 final JsonReader jsonReader = new JsonReader((Reader)reader)) {
                final Storage storage = (Storage)GsonUtil.DEFAULT_GSON.fromJson(jsonReader, (Type)Storage.class);
                if (storage != null) {
                    this.storage = storage;
                }
            }
        }
    }
    
    public void save() throws IOException {
        GsonUtil.writeJson(Constants.Files.TOKENS, this.storage);
    }
    
    public static class Storage
    {
        private Map<UUID, Map<Purpose, Token>> tokens;
        
        public Storage() {
            this.tokens = new HashMap<UUID, Map<Purpose, Token>>();
        }
        
        public Token getToken(final Purpose purpose, final UUID uniqueId) {
            final Map<Purpose, Token> purposeTokenMap = this.tokens.get(uniqueId);
            return (purposeTokenMap != null) ? purposeTokenMap.get(purpose) : null;
        }
        
        public void updateToken(final Purpose purpose, final UUID uniqueId, final Token token) {
            final Map<Purpose, Token> purposeTokenMap = this.tokens.computeIfAbsent(uniqueId, k -> new HashMap());
            purposeTokenMap.put(purpose, token);
        }
        
        private void removeExpiredTokens() {
            this.tokens.forEach((uniqueId, purposeTokenMap) -> purposeTokenMap.entrySet().removeIf(entry -> entry.getValue().isExpired()));
            this.tokens.values().removeIf(Map::isEmpty);
        }
        
        public Map<UUID, Map<Purpose, Token>> getTokens() {
            return this.tokens;
        }
    }
}
