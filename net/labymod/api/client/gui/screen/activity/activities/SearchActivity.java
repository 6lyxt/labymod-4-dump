// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.activities;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.function.Consumers;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Activity;

@Link("activity/search-activity.lss")
@AutoActivity
public class SearchActivity extends Activity
{
    private final Consumer<String> updateListener;
    private final Consumer<TextFieldWidget> widgetConsumer;
    private TextFieldWidget textFieldWidget;
    private boolean skipNextCharacter;
    
    public SearchActivity(final Consumer<String> updateListener) {
        this(updateListener, null);
    }
    
    public SearchActivity(final Consumer<String> updateListener, final Consumer<TextFieldWidget> widgetConsumer) {
        this.updateListener = Objects.requireNonNull(updateListener);
        this.widgetConsumer = widgetConsumer;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        (this.textFieldWidget = new TextFieldWidget().blockFirstKeyPress(true).addId("search-input")).updateListener(this.updateListener);
        ((AbstractWidget<TextFieldWidget>)this.document()).addChild(this.textFieldWidget);
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        if (this.skipNextCharacter) {
            return this.skipNextCharacter = false;
        }
        return super.charTyped(key, character);
    }
    
    @Override
    protected void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        Consumers.accept(this.widgetConsumer, this.textFieldWidget);
    }
    
    public void skipNextCharacter() {
        this.skipNextCharacter = true;
    }
    
    @Override
    public boolean allowCustomFont() {
        return false;
    }
}
