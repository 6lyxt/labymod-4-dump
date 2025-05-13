// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.embed;

import net.labymod.core.client.gui.embed.content.DefaultFormEmbeddedContentBuilder;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContentBuilder;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.embed.EmbedFactory;

@Singleton
@Implements(EmbedFactory.class)
public class DefaultEmbedFactory implements EmbedFactory
{
    @Override
    public FormEmbeddedContentBuilder form() {
        return new DefaultFormEmbeddedContentBuilder();
    }
}
