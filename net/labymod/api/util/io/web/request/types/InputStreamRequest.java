// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.request.types;

import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.AbstractRequest;

public class InputStreamRequest extends AbstractRequest<WebInputStream, InputStreamRequest>
{
    protected InputStreamRequest() {
    }
    
    public static InputStreamRequest create() {
        return new InputStreamRequest();
    }
    
    @Override
    protected WebInputStream handle(final Response<WebInputStream> response, final WebInputStream inputStream) {
        return inputStream;
    }
    
    @Override
    protected InputStreamRequest prepareCopy() {
        return new InputStreamRequest();
    }
}
