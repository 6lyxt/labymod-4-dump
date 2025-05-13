// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.client.gui.screen.widget.widgets.MultilineTextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;

@AutoActivity
@Link("test/text-input.lss")
public class TextInputTestActivity extends TestActivity
{
    private static final TagInputWidget.TagCollection TAGS;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((AbstractWidget<MultilineTextFieldWidget>)this.document).addChild(new MultilineTextFieldWidget());
        ((AbstractWidget<TagInputWidget>)this.document).addChild(new TagInputWidget(TextInputTestActivity.TAGS));
    }
    
    static {
        (TAGS = new TagInputWidget.TagCollection()).add("test");
        TextInputTestActivity.TAGS.add("test2");
    }
}
