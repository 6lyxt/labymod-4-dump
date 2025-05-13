// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test;

import net.labymod.api.client.gui.embed.content.field.FormField;
import net.labymod.api.client.gui.embed.content.FormEmbeddedContent;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;

@AutoActivity
@Link("test/test-menu.lss")
public class FormTestActivity extends TestActivity
{
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FormEmbeddedContent form = Laby.references().embedFactory().form().title(Component.text("LabyStudio has invited you")).subTitle(Component.text("to their LAN world")).icon(Textures.SpriteLabyMod.DEFAULT_WOLF_SHARP).build();
        form.addField("accept").title(Component.text("Join")).makeButton().onSubmit(submitted -> System.out.println("Join"));
        form.addField("decline").title(Component.text("Ignore")).makeButton().onSubmit(submitted -> System.out.println("Ignore"));
        ((Document)this.document).addChild(form.createWidget()).addId("test-form");
    }
}
