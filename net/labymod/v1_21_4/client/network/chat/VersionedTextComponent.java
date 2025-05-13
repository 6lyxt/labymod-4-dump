// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.network.chat;

import net.labymod.api.client.component.Component;
import java.util.Objects;
import net.labymod.api.client.component.ComponentService;
import net.labymod.v1_21_4.client.network.chat.contents.LiteralContentsAccessor;
import net.labymod.api.client.component.TextComponent;

public class VersionedTextComponent extends VersionedBaseComponent<TextComponent, wq> implements TextComponent
{
    private static final String EMPTY = "";
    private boolean empty;
    
    public VersionedTextComponent(final xd holder, final boolean empty) {
        super(holder);
        this.empty = empty;
    }
    
    @Override
    public String getText() {
        if (this.empty) {
            return "";
        }
        final wq contents = ((VersionedBaseComponent<T, wq>)this).getContents();
        if (contents instanceof final xw.a literal) {
            return literal.b();
        }
        return contents.toString();
    }
    
    @Override
    public TextComponent text(final String text) {
        if (this.empty) {
            ((MutableComponentAccessor)this.holder).setContents((wq)new xw.a(text));
            this.empty = false;
            return this;
        }
        final LiteralContentsAccessor contents = ((VersionedBaseComponent<T, LiteralContentsAccessor>)this).getContents();
        if (contents instanceof LiteralContentsAccessor) {
            final LiteralContentsAccessor literal = contents;
            literal.setText(text);
            return this;
        }
        return this;
    }
    
    @Override
    public TextComponent plainCopy() {
        return this.empty ? ComponentService.empty() : ComponentService.textComponent(this.getText());
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof VersionedBaseComponent)) {
            return obj instanceof xd && this.holder.equals(obj);
        }
        if (obj instanceof final VersionedTextComponent textComponent) {
            return (Objects.equals(this.getText(), textComponent.getText()) || this.holder.b().equals((Object)textComponent.holder.b())) && this.holder.a().equals((Object)textComponent.holder.a()) && this.holder.c().equals(textComponent.holder.c());
        }
        return ((VersionedBaseComponent)obj).holder.equals((Object)this.holder);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.getText(), this.holder.a(), this.holder.c());
    }
}
