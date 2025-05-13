// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.session.message;

import net.labymod.api.client.session.model.MojangTexture;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.core.labyconnect.session.ApplyTextureController;

public class TexturesUpdatedMessageListener implements MessageListener
{
    private final ApplyTextureController applyTextureController;
    
    public TexturesUpdatedMessageListener(final ApplyTextureController applyTextureController) {
        this.applyTextureController = applyTextureController;
    }
    
    @Override
    public void listen(final String message) {
        final MojangTextureChangedResponse response = (MojangTextureChangedResponse)TexturesUpdatedMessageListener.GSON.fromJson(message, (Class)MojangTextureChangedResponse.class);
        final MojangTexture[] skins = response.getSkins();
        if (skins.length > 0) {
            this.applyTextureController.applySkinTexture(response.getUniqueId(), skins[0]);
        }
        final MojangTexture[] capes = response.getCapes();
        this.applyTextureController.applyCapeTexture(response.getUniqueId(), (capes.length > 0) ? capes[0] : null);
    }
}
