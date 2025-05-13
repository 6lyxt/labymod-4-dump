// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.message;

import com.google.gson.JsonObject;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.time.TimeUtil;
import com.google.gson.JsonElement;
import net.labymod.core.labyconnect.session.ApplyTextureController;

public class SkinApplyMessageListener implements MessageListener
{
    private final ApplyTextureController applyTextureController;
    private long timeLastSkinApply;
    
    public SkinApplyMessageListener(final ApplyTextureController applyTextureController) {
        this.applyTextureController = applyTextureController;
    }
    
    @Override
    public void listen(final String message) {
        final JsonElement element = (JsonElement)SkinApplyMessageListener.GSON.fromJson(message, (Class)JsonElement.class);
        if (element.isJsonObject()) {
            final JsonObject object = element.getAsJsonObject();
            final String variant = object.get("variant").getAsString();
            final String downloadUrl = object.get("downloadUrl").getAsString();
            final String previewUrl = object.get("previewUrl").getAsString();
            final long timePassedSinceLastApply = TimeUtil.getCurrentTimeMillis() - this.timeLastSkinApply;
            if (timePassedSinceLastApply > 300000L) {
                this.applyTextureController.requestUploadSkin(Icon.url(previewUrl), () -> {
                    this.timeLastSkinApply = TimeUtil.getCurrentTimeMillis();
                    this.applyTextureController.uploadSkinAsync(MinecraftServices.SkinVariant.of(variant), new MinecraftServices.SkinPayload(downloadUrl));
                });
            }
            else {
                this.timeLastSkinApply = TimeUtil.getCurrentTimeMillis();
                this.applyTextureController.uploadSkinAsync(MinecraftServices.SkinVariant.of(variant), new MinecraftServices.SkinPayload(downloadUrl));
            }
        }
    }
}
