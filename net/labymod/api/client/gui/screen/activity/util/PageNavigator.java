// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.util;

public class PageNavigator
{
    private int previousPage;
    private int currentPage;
    private int minimumPage;
    private int maximumPage;
    private boolean specialPage;
    private final Runnable onPageSwitch;
    
    public PageNavigator(final Runnable onPageSwitch) {
        this.onPageSwitch = onPageSwitch;
    }
    
    public void switchPage(final boolean left) {
        this.previousPage = this.currentPage;
        this.currentPage += (left ? -1 : 1);
        if (this.onPageSwitch != null) {
            this.onPageSwitch.run();
        }
    }
    
    public int getPreviousPage() {
        return this.previousPage;
    }
    
    public void setPreviousPage(final int previousPage) {
        this.previousPage = previousPage;
    }
    
    public int getCurrentPage() {
        return this.currentPage;
    }
    
    public void setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
    }
    
    public int getMinimumPage() {
        return this.minimumPage;
    }
    
    public void setMinimumPage(final int minimumPage) {
        this.minimumPage = minimumPage;
    }
    
    public int getMaximumPage() {
        return this.maximumPage;
    }
    
    public void setMaximumPage(final int maximumPage) {
        this.maximumPage = maximumPage;
    }
    
    public boolean isSpecialPage() {
        return this.specialPage;
    }
    
    public void setSpecialPage(final boolean specialPage) {
        this.specialPage = specialPage;
    }
    
    public void setPreviousPageToCurrentPage() {
        this.previousPage = this.currentPage;
    }
    
    public void reset() {
        this.currentPage = 0;
        this.previousPage = 0;
        this.minimumPage = 0;
        this.maximumPage = 0;
        this.specialPage = false;
    }
}
