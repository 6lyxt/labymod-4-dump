// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets;

import net.labymod.api.client.component.BaseComponent;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import java.util.Locale;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;
import net.labymod.api.util.markdown.MarkdownEmptyLineComponent;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.Component;
import net.labymod.api.util.markdown.MarkdownHeaderComponent;
import net.labymod.api.util.markdown.MarkdownRawComponent;
import net.labymod.api.util.markdown.MarkdownComponent;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.markdown.MarkdownDocument;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
@Link("widget/markdown.lss")
public class MarkdownWidget extends VerticalListWidget<Widget>
{
    private final MarkdownDocument document;
    
    public MarkdownWidget(final MarkdownDocument document) {
        this.document = document;
        this.addId("markdown-container");
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        WrappedListWidget<Widget> wrappedListWidget = null;
        final List<MarkdownComponent> components = this.document.getComponents();
        for (int i = 0; i < components.size(); ++i) {
            final MarkdownComponent component = components.get(i);
            if (!(component instanceof MarkdownRawComponent) && wrappedListWidget != null) {
                ((AbstractWidget<WrappedListWidget<Widget>>)this).addChild(wrappedListWidget);
                wrappedListWidget = null;
            }
            final MarkdownComponent previous = (i == 0) ? null : components.get(i - 1);
            if (component instanceof final MarkdownHeaderComponent header) {
                final ComponentWidget widget = ComponentWidget.component(((BaseComponent<Component>)Component.text(header.getText())).decorate(TextDecoration.BOLD));
                widget.addId("markdown-header", "markdown-header-" + header.getLevel());
                if (previous instanceof MarkdownRawComponent) {
                    widget.addId("markdown-header-offset");
                }
                ((AbstractWidget<ComponentWidget>)this).addChild(widget);
            }
            else if (component instanceof MarkdownEmptyLineComponent) {
                final DivWidget widget2 = new DivWidget();
                widget2.addId("markdown-empty-line");
                ((AbstractWidget<DivWidget>)this).addChild(widget2);
            }
            else {
                if (!(component instanceof MarkdownRawComponent)) {
                    throw new UnsupportedOperationException("Unsupported markdown component: " + component.getClass().getSimpleName());
                }
                final MarkdownRawComponent raw = (MarkdownRawComponent)component;
                AbstractWidget<Widget> container = (AbstractWidget<Widget>)((wrappedListWidget == null) ? this : wrappedListWidget);
                if (wrappedListWidget == null && i + 1 < components.size() && components.get(i + 1) instanceof MarkdownRawComponent) {
                    wrappedListWidget = new WrappedListWidget<Widget>();
                    wrappedListWidget.addId("markdown-raw-container");
                    container = wrappedListWidget;
                }
                TextComponent text = Component.text(raw.getText());
                if (raw.hasFormatting(MarkdownRawComponent.Formatting.ITALIC)) {
                    text = text.decorate(TextDecoration.ITALIC);
                }
                final ComponentWidget widget3 = ComponentWidget.component(text);
                widget3.addId("markdown-text");
                for (MarkdownRawComponent.Formatting formatting : raw.getFormatting()) {
                    widget3.addId("markdown-formatting-" + formatting.name().toLowerCase(Locale.ROOT));
                }
                if (previous instanceof MarkdownHeaderComponent) {
                    if (wrappedListWidget != null) {
                        wrappedListWidget.addId("markdown-raw-offset");
                    }
                    else {
                        widget3.addId("markdown-raw-offset");
                    }
                }
                container.addChild(widget3);
            }
        }
        if (wrappedListWidget != null) {
            ((AbstractWidget<WrappedListWidget<Widget>>)this).addChild(wrappedListWidget);
        }
    }
}
