// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web.request.types;

import java.io.IOException;
import net.labymod.api.util.io.web.WebInputStream;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.AbstractRequest;

public class StringRequest extends AbstractRequest<String, StringRequest>
{
    protected StringRequest() {
    }
    
    public static StringRequest create() {
        return new StringRequest();
    }
    
    @Override
    protected String handle(final Response<String> response, final WebInputStream inputStream) throws IOException {
        return this.readString(inputStream);
    }
    
    @Override
    protected StringRequest prepareCopy() {
        return new StringRequest();
    }
}
