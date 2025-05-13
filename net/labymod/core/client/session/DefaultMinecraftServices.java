// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.session;

import java.lang.reflect.Type;
import net.labymod.api.util.gson.UUIDTypeAdapter;
import java.util.UUID;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.List;
import net.labymod.api.client.resources.texture.GameImage;
import com.google.gson.JsonObject;
import net.labymod.api.util.io.web.request.FormData;
import java.util.ArrayList;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.util.Locale;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.api.util.io.web.request.Response;
import javax.inject.Inject;
import net.labymod.api.client.session.SessionAccessor;
import com.google.gson.Gson;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.session.MinecraftServices;

@Singleton
@Implements(MinecraftServices.class)
public class DefaultMinecraftServices implements MinecraftServices
{
    private static final String URL_PROFILE = "https://api.minecraftservices.com/minecraft/profile%s";
    private static final Gson GSON;
    private final SessionAccessor sessionAccessor;
    
    @Inject
    public DefaultMinecraftServices(final SessionAccessor sessionAccessor) {
        this.sessionAccessor = sessionAccessor;
    }
    
    @Override
    public Response<MojangTextureChangedResponse> getProfile() {
        final String accessToken = this.sessionAccessor.getSession().getAccessToken();
        return Request.ofGson(MojangTextureChangedResponse.class, () -> DefaultMinecraftServices.GSON).url(String.format(Locale.ROOT, "https://api.minecraftservices.com/minecraft/profile%s", ""), new Object[0]).authorization("Bearer", accessToken).executeSync();
    }
    
    @Override
    public Response<MojangTextureChangedResponse> changeSkin(final SkinVariant variant, final SkinPayload payload) throws IOException {
        final String accessToken = this.sessionAccessor.getSession().getAccessToken();
        final boolean hasGameImage = payload.hasGameImage();
        final Request<MojangTextureChangedResponse> request = Request.ofGson(MojangTextureChangedResponse.class, () -> DefaultMinecraftServices.GSON).url(String.format(Locale.ROOT, "https://api.minecraftservices.com/minecraft/profile%s", "/skins"), new Object[0]).method(Request.Method.POST).authorization("Bearer", accessToken);
        if (hasGameImage) {
            final GameImage image = payload.getGameImage();
            assert image != null;
            final ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.write("png", stream);
            final byte[] bytes = stream.toByteArray();
            final List<FormData> form = new ArrayList<FormData>();
            form.add(FormData.builder().name("variant").value(variant.getId()).build());
            form.add(FormData.builder().name("file").fileName("skin.png").contentType("image/png").value(bytes).build());
            request.form(form);
        }
        else {
            final JsonObject body = new JsonObject();
            body.addProperty("variant", variant.getId());
            body.addProperty("url", payload.getUrl());
            request.json(body);
        }
        return request.executeSync();
    }
    
    @Override
    public Response<MojangTextureChangedResponse> hideCape() {
        final String accessToken = this.sessionAccessor.getSession().getAccessToken();
        return Request.ofGson(MojangTextureChangedResponse.class, () -> DefaultMinecraftServices.GSON).url(String.format(Locale.ROOT, "https://api.minecraftservices.com/minecraft/profile%s", "/capes/active"), new Object[0]).method(Request.Method.DELETE).authorization("Bearer", accessToken).executeSync();
    }
    
    @Override
    public Response<MojangTextureChangedResponse> showCape(final String capeId) {
        final String accessToken = this.sessionAccessor.getSession().getAccessToken();
        final JsonObject body = new JsonObject();
        body.addProperty("capeId", capeId);
        return Request.ofGson(MojangTextureChangedResponse.class, () -> DefaultMinecraftServices.GSON).url(String.format(Locale.ROOT, "https://api.minecraftservices.com/minecraft/profile%s", "/capes/active"), new Object[0]).method(Request.Method.PUT).authorization("Bearer", accessToken).json((Object)body).executeSync();
    }
    
    static {
        GSON = new GsonBuilder().registerTypeAdapter((Type)UUID.class, (Object)new UUIDTypeAdapter()).create();
    }
}
