// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.options;

public interface MinecraftOptions
{
    public static final int INVALID_SIMULATION_DISTANCE = -1;
    
    int getFrameLimit();
    
    int getChatBackgroundColor();
    
    void setModelParts(final int p0);
    
    int getModelParts();
    
    int getBackgroundColorWithOpacity(final int p0);
    
    ChatVisibility chatVisibility();
    
    MainHand mainHand();
    
    void setMainHand(final MainHand p0);
    
    void sendOptionsToServer();
    
    float getChatWidth();
    
    default float getChatHeight(final boolean open) {
        return open ? this.getChatHeightOpen() : this.getChatHeightClosed();
    }
    
    float getChatHeightOpen();
    
    float getChatHeightClosed();
    
    double getChatScale();
    
    double getChatTextOpacity();
    
    double getTextBackgroundOpacity();
    
    double getChatLineSpacing();
    
    boolean isChatColorsEnabled();
    
    boolean isChatLinksEnabled();
    
    boolean isChatLinkConfirmationEnabled();
    
    boolean isSmoothCamera();
    
    void setSmoothCamera(final boolean p0);
    
    String getCurrentLanguage();
    
    String getLastKnownServer();
    
    void setLastKnownServer(final String p0);
    
    Perspective perspective();
    
    boolean isEyeProtectionActive();
    
    void setEyeProtectionActive(final boolean p0);
    
    MinecraftInputMapping getInputMapping(final String p0);
    
    MinecraftInputMapping attackInput();
    
    MinecraftInputMapping useItemInput();
    
    MinecraftInputMapping sprintInput();
    
    MinecraftInputMapping sneakInput();
    
    boolean isFullscreen();
    
    void setFullscreen(final boolean p0);
    
    void setPerspective(final Perspective p0);
    
    boolean isBobbing();
    
    void setBobbing(final boolean p0);
    
    double getFov();
    
    boolean isHideGUI();
    
    boolean isDebugEnabled();
    
    void save();
    
    AttackIndicatorPosition attackIndicatorPosition();
    
    boolean isBackgroundForChatOnly();
    
    default boolean isHideSplashTexts() {
        return false;
    }
    
    int getRenderDistance();
    
    int getActualRenderDistance();
    
    default int getSimulationDistance() {
        return -1;
    }
}
