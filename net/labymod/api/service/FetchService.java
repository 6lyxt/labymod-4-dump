// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.service;

import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.exception.WebRequestException;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.TypeTokenGsonRequest;
import net.labymod.api.util.io.web.request.AbstractRequest;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import net.labymod.api.util.io.web.WebResponse;

public abstract class FetchService<T> extends Service implements WebResponse<T>
{
    private final String url;
    private final TypeToken<T> typeToken;
    
    public FetchService(final String url, final Type type) {
        this(url, TypeToken.get(type));
    }
    
    public FetchService(final String url, final Class<T> type) {
        this(url, TypeToken.get((Class)type));
    }
    
    public FetchService(final String url, final TypeToken<T> typeToken) {
        this.url = url;
        this.typeToken = typeToken;
        this.prepareSynchronously();
    }
    
    @Override
    protected void prepare() {
        Request.ofGson(this.typeToken).url(this.url, new Object[0]).async().execute(response -> {
            if (response.hasException()) {
                this.failed(response.exception());
            }
            else {
                try {
                    this.success((T)response.get());
                }
                catch (final Exception exception) {
                    this.failed(new WebRequestException(exception));
                }
            }
        });
    }
}
