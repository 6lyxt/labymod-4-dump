// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat;

import java.util.Objects;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.component.Component;

public class Title
{
    @Nullable
    private final Component title;
    @Nullable
    private final Component subTitle;
    private final int fadeInTicks;
    private final int stayTicks;
    private final int fadeOutTicks;
    
    public Title(@Nullable final Component title, @Nullable final Component subTitle, final int fadeInTicks, final int stayTicks, final int fadeOutTicks) {
        this.title = title;
        this.subTitle = subTitle;
        this.fadeInTicks = fadeInTicks;
        this.stayTicks = stayTicks;
        this.fadeOutTicks = fadeOutTicks;
    }
    
    @NotNull
    public static Builder builder() {
        return new Builder();
    }
    
    @Nullable
    public Component getTitle() {
        return this.title;
    }
    
    @Nullable
    public Component getSubTitle() {
        return this.subTitle;
    }
    
    public int getFadeInTicks() {
        return this.fadeInTicks;
    }
    
    public int getStayTicks() {
        return this.stayTicks;
    }
    
    public int getFadeOutTicks() {
        return this.fadeOutTicks;
    }
    
    public void show() {
        Laby.references().chatExecutor().showTitle(this);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Title title1 = (Title)o;
        return this.fadeInTicks == title1.fadeInTicks && this.stayTicks == title1.stayTicks && this.fadeOutTicks == title1.fadeOutTicks && Objects.equals(this.title, title1.title) && Objects.equals(this.subTitle, title1.subTitle);
    }
    
    @Override
    public int hashCode() {
        int result = (this.title != null) ? this.title.hashCode() : 0;
        result = 31 * result + ((this.subTitle != null) ? this.subTitle.hashCode() : 0);
        result = 31 * result + this.fadeInTicks;
        result = 31 * result + this.stayTicks;
        result = 31 * result + this.fadeOutTicks;
        return result;
    }
    
    public static class Builder
    {
        private Component title;
        private Component subTitle;
        private int fadeInTicks;
        private int stayTicks;
        private int fadeOutTicks;
        
        private Builder() {
            this.stayTicks = 20;
        }
        
        @NotNull
        public Builder title(@NotNull final Component title) {
            this.title = title;
            return this;
        }
        
        @NotNull
        public Builder subTitle(@NotNull final Component subTitle) {
            this.subTitle = subTitle;
            return this;
        }
        
        @NotNull
        public Builder fadeIn(final int fadeInTicks) {
            this.fadeInTicks = fadeInTicks;
            return this;
        }
        
        @NotNull
        public Builder stay(final int stayTicks) {
            this.stayTicks = stayTicks;
            return this;
        }
        
        @NotNull
        public Builder fadeOut(final int fadeOutTicks) {
            this.fadeOutTicks = fadeOutTicks;
            return this;
        }
        
        @NotNull
        public Builder timing(final int fadeInTicks, final int stayTicks, final int fadeOutTicks) {
            this.fadeInTicks = fadeInTicks;
            this.stayTicks = stayTicks;
            this.fadeOutTicks = fadeOutTicks;
            return this;
        }
        
        public Title build() {
            return new Title(this.title, this.subTitle, this.fadeInTicks, this.stayTicks, this.fadeOutTicks);
        }
        
        public void show() {
            this.build().show();
        }
    }
}
