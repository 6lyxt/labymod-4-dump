// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.uri.loader;

import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.component.Component;
import java.net.URLEncoder;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.Laby;
import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import net.labymod.core.labyconnect.protocol.model.chat.attachment.embed.link.LinkAttachment;
import net.labymod.api.uri.loader.AttachmentLoader;

public class LinkAttachmentLoader implements AttachmentLoader<LinkAttachment>
{
    private final ExecutorService executorService;
    
    public LinkAttachmentLoader() {
        this.executorService = Executors.newSingleThreadExecutor();
    }
    
    @Override
    public LinkAttachment create(final URI uri) {
        final LinkAttachment attachment = new LinkAttachment(uri);
        this.executorService.execute(() -> this.loadAttachment(attachment));
        return attachment;
    }
    
    private void loadAttachment(final LinkAttachment attachment) {
        final LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        final LabyConnectSession session = labyConnect.getSession();
        if (session == null) {
            return;
        }
        final TokenStorage.Token token = session.tokenStorage().getToken(TokenStorage.Purpose.JWT, session.self().getUniqueId());
        if (token == null || token.isExpired()) {
            return;
        }
        final URI uri = attachment.getURI();
        try {
            final String url = uri.toURL().toString();
            final Response<LinkPreviewResponse> response = Request.ofGson(LinkPreviewResponse.class).url("https://link-preview.laby.net/request-preview?url=" + URLEncoder.encode(url, "UTF-8"), new Object[0]).authorization("Bearer", token.getToken()).executeSync();
            if (response.getStatusCode() < 400) {
                final LinkPreviewResponse preview = response.get();
                boolean update = false;
                if (preview.getTitle() != null) {
                    attachment.setTitle(Component.text(preview.getTitle()));
                    update = true;
                }
                if (preview.getDescription() != null) {
                    attachment.setDescription(Component.text(preview.getDescription()));
                    update = true;
                }
                if (preview.getImageUrl() != null) {
                    attachment.setIcon(Icon.url(preview.getImageUrl()));
                    update = true;
                }
                if (update) {
                    attachment.update();
                }
            }
        }
        catch (final Throwable e) {
            e.printStackTrace();
        }
    }
    
    public static class LinkPreviewResponse
    {
        private String imageUrl;
        private String title;
        private String description;
        private String size;
        private String type;
        private String themeColor;
        
        public String getImageUrl() {
            return this.imageUrl;
        }
        
        public String getTitle() {
            return this.title;
        }
        
        public String getDescription() {
            return this.description;
        }
        
        public String getSize() {
            return this.size;
        }
        
        public String getType() {
            return this.type;
        }
        
        public String getThemeColor() {
            return this.themeColor;
        }
    }
}
