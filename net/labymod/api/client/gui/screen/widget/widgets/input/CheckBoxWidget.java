// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input;

import net.labymod.api.client.sound.SoundType;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class CheckBoxWidget extends AbstractWidget<Widget>
{
    private State state;
    
    public CheckBoxWidget() {
        this.state = State.UNCHECKED;
    }
    
    @Override
    public String getDefaultRendererName() {
        return "CheckBox";
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.isHovered()) {
            this.onPress();
            this.callActionListeners();
            return true;
        }
        return false;
    }
    
    @Override
    public boolean onPress() {
        this.state = this.state.toggle();
        Laby.references().soundService().play((this.state == State.CHECKED) ? SoundType.SWITCH_TOGGLE_ON : SoundType.SWITCH_TOGGLE_OFF);
        return super.onPress();
    }
    
    @Override
    public boolean isHoverComponentRendered() {
        return this.hasHoverComponent() ? super.isHoverComponentRendered() : this.isHovered();
    }
    
    public State state() {
        return this.state;
    }
    
    public void setState(final State checked) {
        this.state = checked;
    }
    
    public enum State
    {
        UNCHECKED, 
        PARTLY, 
        CHECKED;
        
        public State toggle() {
            return (this == State.UNCHECKED) ? State.CHECKED : State.UNCHECKED;
        }
    }
}
