// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.emote;

import net.labymod.api.util.io.web.request.AbstractRequest;
import com.google.gson.reflect.TypeToken;
import net.labymod.api.util.io.web.request.types.StringRequest;
import com.google.gson.JsonObject;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.GsonUtil;
import java.util.Collections;
import java.util.Collection;
import net.labymod.api.util.StringUtil;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import java.io.InputStream;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.core.main.user.shop.item.texture.Ratio;
import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.AnimationContainer;
import java.util.Map;
import java.util.Iterator;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.HashMap;
import net.labymod.api.util.ThreadSafe;
import java.io.IOException;
import net.labymod.core.main.user.shop.emote.exception.EmoteException;
import java.net.URL;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Locale;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationMeta;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import javax.inject.Inject;
import net.labymod.api.Constants;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.request.Request;
import java.util.concurrent.Future;
import java.util.List;
import java.util.concurrent.ExecutorService;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.render.model.ModelService;
import java.lang.reflect.Type;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class EmoteLoader
{
    private static final Type EMOTE_MAP_TYPE;
    private static final String EMOTES_KEY = "emotes";
    private static final String EMOTE_TEXTURE_TEMPLATE = "emotes/%s/texture";
    private final ModelService modelService;
    private final Resources resources;
    private final Logging logging;
    private final ExecutorService executorService;
    private final List<Future<?>> tasks;
    private final Request<JsonElement> emoteDataRequest;
    
    @Inject
    public EmoteLoader(final ModelService modelService, final Resources resources) {
        this.modelService = modelService;
        this.resources = resources;
        this.logging = Logging.create(this.getClass());
        this.executorService = Executors.newWorkStealingPool(Math.max(1, 4));
        this.tasks = new ArrayList<Future<?>>();
        this.emoteDataRequest = Request.ofGson(JsonElement.class).url(Constants.Urls.EMOTE_DATA, new Object[0]);
    }
    
    public void loadEmotes(final Int2ObjectOpenHashMap<EmoteItem> emotes) {
        final List<EmoteItem> items = this.loadEmoteIndex();
        for (EmoteItem emote : items) {
            final int emoteId = emote.getId();
            this.tasks.add(this.executorService.submit(() -> {
                final AnimationContainer animationContainer = this.loadAnimations(emoteId);
                if (animationContainer.hasTriggerAnimations()) {
                    animationContainer.getAnimations().iterator();
                    final Iterator iterator3;
                    while (iterator3.hasNext()) {
                        final ModelAnimation animation = iterator3.next();
                        animation.addMeta(EmoteAnimationMeta.TRIGGER_EMOTE, true);
                    }
                }
                animationContainer.getTriggerAnimations(AnimationTrigger.NONE).iterator();
                final Iterator iterator4;
                while (iterator4.hasNext()) {
                    final ModelAnimation animation2 = iterator4.next();
                    animation2.addMeta(EmoteAnimationMeta.START_ANIMATION, true);
                }
                emote.setAnimationContainer(animationContainer);
                if (emote.hasProps()) {
                    final Model propsModel = this.loadPropsModel(emoteId);
                    emote.setPropsModel(propsModel);
                    final String textureUrl = String.format(Locale.ROOT, Constants.Urls.EMOTE_TEXTURE, emoteId);
                    final String texturePath = String.format(Locale.ROOT, "emotes/%s/texture", emoteId);
                    final Ratio ratio = emote.getTextureRatio();
                    if (ratio == null) {
                        final ResourceLocation location = ResourceLocation.create("labymod", texturePath + ".png");
                        final CompletableResourceLocation completableLocation = this.resources.textureRepository().getOrRegisterTexture(location, textureUrl);
                        completableLocation.addCompletableListener(() -> emote.setPropsTextureLocation(completableLocation.getCompleted()));
                    }
                    else {
                        try {
                            final InputStream spriteImageStream = new URL(textureUrl).openStream();
                            final AnimatedResourceLocation animatedLocation = this.resources.resourceLocationFactory().createAnimated("labymod", texturePath, spriteImageStream, ratio.getWidth(), ratio.getHeight(), emote.getTextureAnimationDelay());
                            emote.setAnimatedPropsTextureLocation(animatedLocation);
                        }
                        catch (final IOException exception2) {
                            throw new EmoteException(emoteId, "Failed to load animated texture for emote " + emoteId, (Throwable)exception2);
                        }
                    }
                }
                if (emote.hasPlayerModel()) {
                    emote.setSteveModel(this.loadPlayerModel(emoteId, false));
                    emote.setAlexModel(this.loadPlayerModel(emoteId, true));
                }
                ThreadSafe.executeOnRenderThread(() -> emotes.put(emote.getId(), (Object)emote));
                return;
            }));
        }
        final Map<String, IntList> errorMap = new HashMap<String, IntList>();
        final Iterator<Future<?>> iterator = this.tasks.iterator();
        while (iterator.hasNext()) {
            final Future<?> task = iterator.next();
            if (task.isDone()) {
                iterator.remove();
            }
            else {
                try {
                    task.get();
                }
                catch (final Exception exception) {
                    Throwable cause;
                    for (cause = exception; cause.getCause() != null; cause = cause.getCause()) {}
                    if (cause instanceof final EmoteException ex) {
                        final String reason = ex.getReason();
                        final IntList list = errorMap.computeIfAbsent(reason, s -> new IntArrayList());
                        list.add(((EmoteException)cause).getEmoteId());
                    }
                    else {
                        this.logging.warn("Unable to load emote. Cause: " + cause.getMessage(), new Object[0]);
                    }
                }
                iterator.remove();
            }
        }
        this.printErrors(errorMap);
    }
    
    private void printErrors(final Map<String, IntList> errorMap) {
        for (final Map.Entry<String, IntList> entry : errorMap.entrySet()) {
            final IntList ids = entry.getValue();
            final boolean emotes = ids.size() > 1;
            this.logging.warn("Unable to load {}. (Cause: {}, {}: {})", emotes ? "some emotes" : "emote", entry.getKey(), emotes ? "Emotes" : "Emote", StringUtil.join((Collection<?>)ids, ", "));
        }
        errorMap.clear();
    }
    
    private List<EmoteItem> loadEmoteIndex() {
        final Response<JsonElement> response = this.emoteDataRequest.executeSync();
        final JsonElement result = response.getNullable();
        if (result == null) {
            return Collections.emptyList();
        }
        if (!result.isJsonObject()) {
            return Collections.emptyList();
        }
        final JsonObject emoteContainer = result.getAsJsonObject();
        final Map<String, EmoteItem> emoteIndex = (Map<String, EmoteItem>)GsonUtil.DEFAULT_GSON.fromJson(emoteContainer.get("emotes"), EmoteLoader.EMOTE_MAP_TYPE);
        return new ArrayList<EmoteItem>(emoteIndex.values());
    }
    
    private AnimationContainer loadAnimations(final int emoteId) {
        final Response<String> response = ((AbstractRequest<String, R>)((AbstractRequest<T, StringRequest>)Request.ofString()).url(Constants.Urls.EMOTE_ANIMATION, new Object[] { emoteId })).executeSync();
        if (!response.isPresent()) {
            final int statusCode = response.getStatusCode();
            throw new EmoteException(emoteId, "Failed to load animation of emote with id " + emoteId + " (" + statusCode, "Response Code: " + statusCode);
        }
        Collection<ModelAnimation> animations;
        try {
            animations = this.modelService.loadBlockBenchAnimations(response.get(), EmoteAnimationMeta.withDefaults());
        }
        catch (final NullPointerException exception) {
            throw new EmoteException(emoteId, "Animations for emote " + emoteId + " could not be loaded", exception.getMessage());
        }
        if (animations == null || animations.isEmpty()) {
            throw new EmoteException(emoteId, "Animation file for emote " + emoteId + " does not contain animations", "No animations");
        }
        return new AnimationContainer(animations);
    }
    
    private Model loadPropsModel(final int emoteId) {
        final Response<String> response = ((AbstractRequest<String, R>)((AbstractRequest<T, StringRequest>)Request.ofString()).url(Constants.Urls.EMOTE_GEOMETRY, new Object[] { emoteId })).executeSync();
        if (!response.isPresent()) {
            final int statusCode = response.getStatusCode();
            throw new EmoteException(emoteId, "Failed to load props of emote with id " + emoteId + " (" + statusCode, "Response Code: " + statusCode + " (Props)");
        }
        return this.modelService.loadBlockBenchModel(response.get());
    }
    
    private Model loadPlayerModel(final int emoteId, final boolean slim) {
        final Response<String> response = ((AbstractRequest<String, R>)((AbstractRequest<T, StringRequest>)Request.ofString()).url(slim ? Constants.Urls.EMOTE_PLAYER_MODEL_ALEX : Constants.Urls.EMOTE_PLAYER_MODEL_STEVE, new Object[] { emoteId })).executeSync();
        if (!response.isPresent()) {
            final int statusCode = response.getStatusCode();
            throw new EmoteException(emoteId, "Failed to load player model of emote with id " + emoteId + " (" + statusCode, "Response Code: " + statusCode + " (Player Model)");
        }
        return this.modelService.loadBlockBenchModel(response.get());
    }
    
    static {
        EMOTE_MAP_TYPE = TypeToken.getParameterized((Type)Map.class, new Type[] { Integer.class, EmoteItem.class }).getType();
    }
}
