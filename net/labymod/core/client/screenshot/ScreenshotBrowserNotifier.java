// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.screenshot;

public interface ScreenshotBrowserNotifier
{
    void onIndexProgress(final IndexState p0, final float p1);
    
    void onSectionAdded(final ScreenshotSection p0);
    
    void onSectionChanged(final ScreenshotSection p0);
    
    void onSectionRemoved(final ScreenshotSection p0);
    
    public enum IndexState
    {
        NOT_INITIALIZED, 
        PREPARING, 
        INDEXING, 
        FINALIZING, 
        IDLE;
    }
}
