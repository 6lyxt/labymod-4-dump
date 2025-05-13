// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component;

import java.util.Objects;
import net.labymod.api.client.scoreboard.ScoreboardObjective;

public interface ScoreComponent extends BaseComponent<ScoreComponent>
{
    default Builder builder() {
        return new Builder();
    }
    
    String getName();
    
    String getObjective();
    
    ScoreComponent plainCopy();
    
    ScoreComponent name(final String p0);
    
    ScoreComponent objective(final String p0);
    
    Component value();
    
    ScoreboardObjective getScoreboardObjective();
    
    default Builder toBuilder() {
        return new Builder(this);
    }
    
    public static class Builder extends Component.Builder<ScoreComponent, Builder>
    {
        protected String name;
        protected String objective;
        
        protected Builder() {
            this.name = "";
            this.objective = "";
        }
        
        protected Builder(final ScoreComponent component) {
            super(component);
            this.name = "";
            this.objective = "";
            this.name = component.getName();
            this.objective = component.getObjective();
        }
        
        public Builder name(final String name) {
            this.name = name;
            return this;
        }
        
        public Builder objective(final String objective) {
            this.objective = objective;
            return this;
        }
        
        @Override
        public ScoreComponent build() {
            Objects.requireNonNull(this.name, "Name cannot be null!");
            Objects.requireNonNull(this.objective, "Objective cannot be null!");
            return ComponentService.scoreComponent(this.name, this.objective, this.isEmpty() ? null : this.buildStyle(), this.children);
        }
    }
}
