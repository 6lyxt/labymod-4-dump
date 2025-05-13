// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.io.web;

import net.labymod.api.util.io.web.exception.WebRequestException;

public interface WebResponse<T>
{
    void success(final T p0) throws Exception;
    
    void failed(final WebRequestException p0);
}
