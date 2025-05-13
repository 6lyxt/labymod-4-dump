// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.texture.listener;

public interface ItemTextureListener
{
    public static final ItemTextureListener NOP_LISTENER = new ItemTextureListener() {
        @Override
        public void onBegin() {
        }
        
        @Override
        public void onSuccess() {
        }
        
        @Override
        public void onError() {
        }
    };
    
    void onBegin();
    
    void onSuccess();
    
    void onError();
}
