// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.session;

import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.HttpAuthenticationService;
import com.google.gson.JsonParseException;
import java.io.IOException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.exceptions.UserMigratedException;
import org.apache.commons.lang3.StringUtils;
import com.mojang.authlib.yggdrasil.response.Response;
import com.mojang.authlib.yggdrasil.request.JoinMinecraftServerRequest;
import com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
import com.mojang.util.UUIDTypeAdapter;
import java.util.UUID;
import com.mojang.authlib.properties.PropertyMap;
import java.lang.reflect.Type;
import com.mojang.authlib.GameProfile;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import com.mojang.authlib.EnvironmentParser;
import com.mojang.authlib.Environment;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.google.gson.Gson;
import java.net.URL;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;

public class LabyMinecraftSessionService extends YggdrasilMinecraftSessionService
{
    private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";
    private static final URL JOIN_URL;
    private final Gson gson;
    
    public LabyMinecraftSessionService(final YggdrasilAuthenticationService authenticationService) {
        super(authenticationService, (Environment)EnvironmentParser.getEnvironmentFromProperties().orElse(YggdrasilEnvironment.PROD.getEnvironment()));
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter((Type)GameProfile.class, (Object)new GameProfileSerializer());
        builder.registerTypeAdapter((Type)PropertyMap.class, (Object)new PropertyMap.Serializer());
        builder.registerTypeAdapter((Type)UUID.class, (Object)new UUIDTypeAdapter());
        builder.registerTypeAdapter((Type)ProfileSearchResultsResponse.class, (Object)new ProfileSearchResultsResponse.Serializer());
        this.gson = builder.create();
    }
    
    public void joinServer(final GameProfile profile, final String authenticationToken, final String serverId) throws AuthenticationException {
        final JoinMinecraftServerRequest request = new JoinMinecraftServerRequest();
        request.accessToken = authenticationToken;
        request.selectedProfile = profile.getId();
        request.serverId = serverId;
        try {
            final String jsonResult = this.getAuthenticationService().performPostRequest(LabyMinecraftSessionService.JOIN_URL, this.gson.toJson((Object)request), "application/json");
            final Response result = (Response)this.gson.fromJson(jsonResult, (Class)Response.class);
            if (result == null) {
                return;
            }
            if (StringUtils.isNotBlank((CharSequence)result.getError())) {
                if ("UserMigratedException".equals(result.getCause())) {
                    throw new UserMigratedException(result.getErrorMessage());
                }
                if (result.getError().equals("ForbiddenOperationException")) {
                    throw new InvalidCredentialsException(result.getErrorMessage());
                }
                throw new AuthenticationException(result.getErrorMessage());
            }
        }
        catch (final IOException | JsonParseException | IllegalStateException e) {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", (Throwable)e);
        }
    }
    
    static {
        JOIN_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/join");
    }
    
    private static class GameProfileSerializer implements JsonSerializer<GameProfile>, JsonDeserializer<GameProfile>
    {
        public GameProfile deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            final JsonObject object = (JsonObject)json;
            final UUID id = object.has("id") ? ((UUID)context.deserialize(object.get("id"), (Type)UUID.class)) : null;
            final String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
            return new GameProfile(id, name);
        }
        
        public JsonElement serialize(final GameProfile src, final Type typeOfSrc, final JsonSerializationContext context) {
            final JsonObject result = new JsonObject();
            if (src.getId() != null) {
                result.add("id", context.serialize((Object)src.getId()));
            }
            if (src.getName() != null) {
                result.addProperty("name", src.getName());
            }
            return (JsonElement)result;
        }
    }
}
