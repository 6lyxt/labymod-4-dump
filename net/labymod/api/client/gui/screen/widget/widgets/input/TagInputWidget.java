// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.render.font.FontSize;
import java.lang.annotation.Annotation;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import java.util.function.BiConsumer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.Parent;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.WrappedListWidget;

@AutoWidget
@SettingWidget
@Link(value = "widget/tag-input.lss", priority = -64)
public class TagInputWidget extends WrappedListWidget<Widget>
{
    private final TagCollection tags;
    private TagTextFieldWidget pendingWidget;
    private final List<TagTextFieldWidget> deletionPending;
    private boolean falsifyHover;
    
    public TagInputWidget(final TagCollection tagCollection) {
        this.falsifyHover = false;
        this.tags = tagCollection;
        this.deletionPending = new ArrayList<TagTextFieldWidget>();
    }
    
    public TagInputWidget() {
        this(new TagCollection());
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        for (final TagCollection.Tag tag : this.tags.getTags()) {
            this.addChild(this.createInput(tag));
        }
        (this.pendingWidget = new TagTextFieldWidget(this.tags.unsavedTag, true)).addId("tag-text-field-widget", "unsubmitted");
        this.pendingWidget.submitHandler(value -> {
            if (value.trim().isEmpty()) {
                return;
            }
            else {
                final TagCollection.Tag tag2 = this.tags.add(value);
                this.tags.unsavedTag.setContent("");
                this.pendingWidget.updateInput = false;
                this.pendingWidget.setText("");
                this.pendingWidget.updateInput = true;
                int index = -1;
                int i = 0;
                while (i < this.children.size()) {
                    final Widget child = this.children.get(i);
                    if (child == this.pendingWidget) {
                        index = i;
                        break;
                    }
                    else {
                        ++i;
                    }
                }
                this.addChildInitialized(index, this.createInput(tag2));
                return;
            }
        });
        ((AbstractWidget<TagTextFieldWidget>)this).addChild(this.pendingWidget);
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        this.handlePending();
        super.renderWidget(context);
    }
    
    private void handlePending() {
        final TagTextFieldWidget widgetToDelete = (this.deletionPending == null || this.deletionPending.isEmpty()) ? null : this.deletionPending.get(0);
        if (widgetToDelete != null) {
            this.deletionPending.remove(widgetToDelete);
            if (widgetToDelete == this.pendingWidget) {
                widgetToDelete.submitHandler.accept(widgetToDelete.getText());
            }
            else {
                widgetToDelete.text = "";
                widgetToDelete.submitHandler.accept("");
            }
        }
    }
    
    @Override
    public void dispose() {
        this.handlePending();
        super.dispose();
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        this.falsifyHover = true;
        final boolean handled = super.mouseClicked(mouse, mouseButton);
        this.falsifyHover = false;
        if (handled) {
            return true;
        }
        for (final Widget child : this.children) {
            if (child.bounds().isInRectangle(BoundsType.OUTER, mouse)) {
                return false;
            }
        }
        this.pendingWidget.setFocused(true);
        return true;
    }
    
    @Override
    public boolean isHovered() {
        return !this.falsifyHover && super.isHovered();
    }
    
    @Override
    public boolean isHoverComponentRendered() {
        return this.hasHoverComponent() ? super.isHoverComponentRendered() : super.isHovered();
    }
    
    private Widget createInput(final TagCollection.Tag text) {
        return this.createInput(text, (widget, wrapper) -> {
            if (widget.getText().trim().isEmpty()) {
                this.tags.remove(widget.tag);
                final boolean removed = this.children.removeIf(child -> child == wrapper);
                if (removed) {
                    this.update();
                }
            }
            else {
                widget.setFocused(false);
            }
        });
    }
    
    private void update() {
        this.updateChildren();
        this.updateBounds();
        if (this.parent instanceof final ListWidget listWidget) {
            listWidget.updateBounds();
        }
        if (this.hasId("extended-input-widget")) {
            final Parent parent1 = this.parent.getParent();
            if (parent1 instanceof net.labymod.api.client.gui.screen.widget.widgets.activity.settings.SettingWidget) {
                ((AbstractWidget)parent1).updateBounds();
            }
        }
    }
    
    private Widget createInput(final TagCollection.Tag tag, final BiConsumer<TagTextFieldWidget, Widget> submitHandler) {
        final TagTextFieldWidget tagTextFieldWidget = new TagTextFieldWidget(tag, false);
        tagTextFieldWidget.addId("tag-text-field-widget", "submitted");
        final FlexibleContentWidget wrapper = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)wrapper).addId("tag-text-field-wrapper", "submitted");
        wrapper.addContent(tagTextFieldWidget);
        final DivWidget deleteWrapper = new DivWidget();
        deleteWrapper.addId("tag-text-field-delete-wrapper");
        deleteWrapper.setPressable(() -> {
            tagTextFieldWidget.text = "";
            tagTextFieldWidget.submitHandler.accept("");
            return;
        });
        final IconWidget iconWidget = new IconWidget(Textures.SpriteCommon.SMALL_X);
        iconWidget.addId("tag-text-field-delete-icon");
        ((AbstractWidget<IconWidget>)deleteWrapper).addChild(iconWidget);
        wrapper.addContent(deleteWrapper);
        if (submitHandler != null) {
            tagTextFieldWidget.submitHandler(value -> submitHandler.accept(tagTextFieldWidget, wrapper));
        }
        return wrapper;
    }
    
    public TagCollection tagCollection() {
        return this.tags;
    }
    
    public static class TagCollection
    {
        private final List<Tag> tags;
        private final transient Tag unsavedTag;
        
        public TagCollection() {
            this.tags = new ArrayList<Tag>();
            this.unsavedTag = new Tag("");
        }
        
        public List<Tag> getTags() {
            return this.tags;
        }
        
        public Tag add(final String text) {
            final Tag tag = new Tag(text);
            this.tags.add(tag);
            return tag;
        }
        
        public void add(final Tag tag) {
            this.tags.add(tag);
        }
        
        public boolean remove(final Tag tag) {
            return this.tags.remove(tag);
        }
        
        public boolean isEmpty() {
            return this.tags.isEmpty();
        }
        
        public static class Tag
        {
            private String content;
            
            public Tag(final String content) {
                this.content = content;
            }
            
            public String getContent() {
                return this.content;
            }
            
            public void setContent(final String content) {
                this.content = content;
            }
        }
    }
    
    @SettingFactory
    public static class Factory implements WidgetFactory<TagInputSetting, TagInputWidget>
    {
        @Override
        public TagInputWidget[] create(final Setting setting, final TagInputSetting annotation, final SettingAccessor accessor) {
            return new TagInputWidget[] { new TagInputWidget(accessor.get()) };
        }
        
        @Override
        public Class<?>[] types() {
            return new Class[] { TagCollection.class };
        }
    }
    
    @AutoWidget
    public class TagTextFieldWidget extends TextFieldWidget
    {
        private final TagCollection.Tag tag;
        private final boolean unsaved;
        private float width;
        private boolean updateInput;
        
        private TagTextFieldWidget(final TagCollection.Tag tag, final boolean unsaved) {
            this.width = -1.0f;
            this.updateInput = true;
            this.tag = tag;
            this.text = tag.getContent();
            this.unsaved = unsaved;
        }
        
        @Override
        public void postStyleSheetLoad() {
            super.postStyleSheetLoad();
            if (this.initialized) {
                this.updateBounds();
                TagInputWidget.this.update();
            }
        }
        
        @Override
        public float getContentWidth(final BoundsType type) {
            if (this.width == -1.0f) {
                this.width = this.labyAPI.renderPipeline().textRenderer().width(this.text) * this.fontSize().get().getFontSize();
            }
            return this.width + this.bounds().getHorizontalOffset(type);
        }
        
        @Override
        protected void applyText(final String text) {
            super.applyText(text);
            this.tag.setContent(text);
            this.width = -1.0f;
            if (this.initialized) {
                if (this.parent instanceof final FlexibleContentWidget flexibleContentWidget) {
                    flexibleContentWidget.updateBounds();
                }
                else {
                    this.updateBounds();
                }
                if (this.updateInput) {
                    TagInputWidget.this.update();
                }
            }
        }
        
        @Override
        public boolean keyPressed(final Key key, final InputType type) {
            if (this.unsaved && this.isFocused() && key == Key.TAB) {
                this.submitHandler.accept(this.text);
                return true;
            }
            return super.keyPressed(key, type);
        }
        
        @Override
        public boolean charTyped(final Key key, final char character) {
            return super.charTyped(key, character);
        }
        
        @Override
        public boolean isFocused() {
            if (this.parent instanceof final TagInputWidget tagInputWidget) {
                return tagInputWidget.isFocused();
            }
            if (this.parent instanceof final FlexibleContentWidget flexibleContentWidget) {
                return flexibleContentWidget.isFocused();
            }
            return super.isFocused();
        }
        
        @Override
        public void setFocused(final boolean focused) {
            if (!focused && this.text.trim().isEmpty() && this != TagInputWidget.this.pendingWidget) {
                TagInputWidget.this.deletionPending.add(this);
            }
            if (!focused && this.unsaved) {
                TagInputWidget.this.deletionPending.add(this);
            }
            if (this.parent instanceof final TagInputWidget tagInputWidget) {
                tagInputWidget.setFocused(focused);
                this.labyAPI.minecraft().updateKeyRepeatingMode(true);
                return;
            }
            if (this.parent instanceof final FlexibleContentWidget flexibleContentWidget) {
                flexibleContentWidget.setFocused(focused);
                this.labyAPI.minecraft().updateKeyRepeatingMode(true);
                return;
            }
            super.setFocused(focused);
        }
    }
    
    @SettingElement(extended = true)
    @Target({ ElementType.FIELD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface TagInputSetting {
    }
}
