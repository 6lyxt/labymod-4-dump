// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.geometry.animation;

import java.util.Collections;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonArray;
import net.labymod.api.client.render.model.animation.ModelPartTransformation;
import net.labymod.api.client.render.model.animation.ModelPartAnimation;
import java.util.Iterator;
import com.google.gson.JsonElement;
import java.util.Collection;
import net.labymod.api.client.render.model.animation.meta.AnimationMeta;
import java.util.ArrayList;
import net.labymod.api.util.io.IOUtil;
import java.io.File;
import java.io.Reader;
import java.io.InputStreamReader;
import net.labymod.api.util.GsonUtil;
import java.io.InputStream;
import java.io.IOException;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.HashMap;
import java.util.Objects;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import java.util.Map;
import com.google.gson.JsonObject;

public class AnimationLoader
{
    private final JsonObject tree;
    private final Map<String, ModelAnimation> animations;
    
    public AnimationLoader(final JsonObject tree) {
        Objects.requireNonNull(tree, "Json tree cannot be null");
        this.tree = tree;
        this.animations = new HashMap<String, ModelAnimation>();
    }
    
    public AnimationLoader(final ResourceLocation resourceLocation) throws IOException {
        this(resourceLocation.openStream());
    }
    
    public AnimationLoader(final InputStream inputStream) throws IOException {
        this((JsonObject)GsonUtil.DEFAULT_GSON.fromJson((Reader)new InputStreamReader(inputStream), (Class)JsonObject.class));
        inputStream.close();
    }
    
    public AnimationLoader(final String json) {
        this((JsonObject)GsonUtil.DEFAULT_GSON.fromJson(json, (Class)JsonObject.class));
    }
    
    public AnimationLoader(final File file) throws IOException {
        this(IOUtil.newInputStream(file.toPath()));
    }
    
    public AnimationLoader load() {
        return this.load(new ArrayList<AnimationMeta<?>>());
    }
    
    public AnimationLoader load(final Collection<AnimationMeta<?>> supportedAnimationMeta) {
        if (!this.tree.has("animations") || !this.tree.get("animations").isJsonObject()) {
            return this;
        }
        final JsonObject animations = this.tree.get("animations").getAsJsonObject();
        for (final Map.Entry<String, JsonElement> animationEntry : animations.entrySet()) {
            final String animationName = animationEntry.getKey();
            final JsonObject animationObject = animationEntry.getValue().getAsJsonObject();
            if (animationObject.has("bones")) {
                final JsonObject bones = animationObject.get("bones").getAsJsonObject();
                ModelAnimation animation;
                if (animationObject.has("animation_length")) {
                    final float animationLengthSeconds = animationObject.get("animation_length").getAsFloat();
                    animation = new ModelAnimation(animationName, (long)(animationLengthSeconds * 1000.0f));
                }
                else {
                    animation = new ModelAnimation(animationName);
                }
                if (animationObject.has("anim_time_update")) {
                    final String meta = animationObject.get("anim_time_update").getAsString();
                    if (!meta.isEmpty()) {
                        final String[] args = meta.split(" ");
                        AnimationMeta<?> currentMeta = null;
                        for (final String arg : args) {
                            if (currentMeta != null) {
                                animation.parseAndAddMeta(currentMeta, arg);
                                currentMeta = null;
                            }
                            else {
                                final String metaName = arg.replace("-", "");
                                for (final AnimationMeta<?> animationMeta : supportedAnimationMeta) {
                                    if (animationMeta.getKey().equals(metaName) || animationMeta.getShortcut().equals(metaName)) {
                                        currentMeta = animationMeta;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    for (final AnimationMeta<?> animationMeta2 : supportedAnimationMeta) {
                        if (animationObject.has(animationMeta2.getKey())) {
                            animation.parseAndAddMeta(animationMeta2, animationObject.get(animationMeta2.getKey()).getAsString());
                        }
                    }
                }
                for (final Map.Entry<String, JsonElement> boneEntry : bones.entrySet()) {
                    final String boneName = boneEntry.getKey();
                    final JsonObject bone = boneEntry.getValue().getAsJsonObject();
                    final ModelPartAnimation boneAnimation = animation.getPartAnimation(boneName);
                    this.extractKeyframes(boneAnimation.rotation(), bone, "rotation");
                    this.extractKeyframes(boneAnimation.position(), bone, "position");
                    this.extractKeyframes(boneAnimation.scale(), bone, "scale");
                }
                this.animations.put(animationName, animation);
            }
        }
        return this;
    }
    
    private void extractKeyframes(final ModelPartTransformation transformation, final JsonObject bone, final String key) {
        if (bone.has(key)) {
            final JsonElement type = bone.get(key);
            if (type.isJsonArray()) {
                this.pushVector(transformation, 0L, type.getAsJsonArray(), false, key);
            }
            else if (type.isJsonObject()) {
                final JsonObject object = type.getAsJsonObject();
                if (object.has("post")) {
                    final boolean smooth = object.has("lerp_mode") && object.get("lerp_mode").getAsString().equals("catmullrom");
                    this.pushVector(transformation, 0L, object.get("post").getAsJsonArray(), smooth, key);
                }
                else {
                    for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                        final long offset = (long)(Double.parseDouble(entry.getKey()) * 1000.0);
                        final JsonElement value = entry.getValue();
                        boolean smooth2 = false;
                        JsonArray array;
                        if (value.isJsonArray()) {
                            array = value.getAsJsonArray();
                        }
                        else {
                            final JsonObject entryObject = value.getAsJsonObject();
                            array = entryObject.get("post").getAsJsonArray();
                            smooth2 = (entryObject.has("lerp_mode") && entryObject.get("lerp_mode").getAsString().equals("catmullrom"));
                        }
                        this.pushVector(transformation, offset, array, smooth2, key);
                    }
                }
            }
            else if (type.isJsonPrimitive() && type.getAsJsonPrimitive().isNumber()) {
                final float value2 = type.getAsFloat();
                final JsonArray array2 = new JsonArray();
                array2.add((JsonElement)new JsonPrimitive((Number)value2));
                array2.add((JsonElement)new JsonPrimitive((Number)value2));
                array2.add((JsonElement)new JsonPrimitive((Number)value2));
                this.pushVector(transformation, 0L, array2, false, key);
            }
        }
    }
    
    private void pushVector(final ModelPartTransformation storage, final long offset, final JsonArray arrayVector, final boolean smooth, final String key) {
        float x = arrayVector.get(0).getAsFloat();
        float y = arrayVector.get(1).getAsFloat();
        float z = arrayVector.get(2).getAsFloat();
        if (key.equals("rotation")) {
            x = (float)Math.toRadians(x);
            y = (float)Math.toRadians(y);
            z = (float)Math.toRadians(z);
        }
        else if (key.equals("position")) {
            y = -y;
        }
        storage.addKeyframe(new ModelPartTransformation.Keyframe(offset, smooth, x, y, z));
    }
    
    public ModelAnimation getAnimation(final String name) {
        return this.animations.get(name);
    }
    
    public Collection<ModelAnimation> getAnimations() {
        return Collections.unmodifiableCollection((Collection<? extends ModelAnimation>)this.animations.values());
    }
}
