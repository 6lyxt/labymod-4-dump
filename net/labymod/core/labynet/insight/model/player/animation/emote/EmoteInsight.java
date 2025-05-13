// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.model.player.animation.emote;

import com.google.gson.JsonElement;
import net.labymod.api.util.math.vector.FloatVector3;
import java.util.Iterator;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import java.util.HashMap;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.entity.player.Player;
import com.google.gson.JsonObject;
import java.util.Map;
import net.labymod.core.labynet.insight.Insight;

public class EmoteInsight implements Insight
{
    private Map<String, EmotePartInsight> parts;
    
    public EmoteInsight(final JsonObject object) {
        this.fromJsonObject(object);
    }
    
    public EmoteInsight(final Player player) {
        final EmoteService emoteService = LabyMod.references().emoteService();
        final EmoteAnimationStorage animationStorage = emoteService.getAnimationStorage(player);
        if (animationStorage != null) {
            final AnimationController animationController = animationStorage.animationController();
            final ModelAnimation modelAnimation = animationController.getPlaying();
            if (modelAnimation != null) {
                final Map<String, EmotePartInsight> parts = new HashMap<String, EmotePartInsight>();
                for (final ModelAnimation.NamedModelPartAnimation entry : modelAnimation.getPartAnimations()) {
                    final String name = entry.getName();
                    final FloatVector3 position = animationController.getCurrentPosition(name);
                    final FloatVector3 rotation = animationController.getCurrentRotation(name);
                    final FloatVector3 scale = animationController.getCurrentScale(name);
                    final EmotePartInsight part = new EmotePartInsight(position, rotation, scale);
                    if (!part.isDefault()) {
                        parts.put(name, part);
                    }
                }
                this.parts = parts;
                return;
            }
        }
        this.parts = new HashMap<String, EmotePartInsight>();
    }
    
    public boolean isPlaying() {
        return !this.parts.isEmpty();
    }
    
    @Override
    public JsonObject toJsonObject() {
        final JsonObject parts = new JsonObject();
        for (final Map.Entry<String, EmotePartInsight> entry : this.parts.entrySet()) {
            parts.add((String)entry.getKey(), (JsonElement)entry.getValue().toJsonObject());
        }
        return parts;
    }
    
    @Override
    public void fromJsonObject(final JsonObject object) {
        final Map<String, EmotePartInsight> parts = new HashMap<String, EmotePartInsight>();
        for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
            parts.put(entry.getKey(), new EmotePartInsight(entry.getValue().getAsJsonObject()));
        }
        this.parts = parts;
    }
}
