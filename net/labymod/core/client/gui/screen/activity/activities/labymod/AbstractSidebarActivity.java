// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.icon.Icon;
import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.HrWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Activity;

@Link("activity/sidebar-activity.lss")
public abstract class AbstractSidebarActivity extends Activity
{
    private final ListSession<Widget> listSession;
    protected final ScreenRendererWidget screenRendererWidget;
    protected final TextFieldWidget searchWidget;
    private final boolean withSearch;
    private VerticalListWidget<Widget> categoryList;
    
    protected AbstractSidebarActivity() {
        this(true);
    }
    
    protected AbstractSidebarActivity(final boolean withSearch) {
        this.listSession = new ListSession<Widget>();
        this.categoryList = new VerticalListWidget<Widget>();
        this.withSearch = withSearch;
        (this.screenRendererWidget = new ScreenRendererWidget().addId("screen-renderer")).addPostDisplayListener(instance -> this.recheckSidebarButtons(instance));
        (this.searchWidget = new TextFieldWidget().addId("search-input")).placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchWidget.maximalLength(128);
        this.searchWidget.updateListener(searchContent -> {
            searchContent = searchContent.trim();
            this.onSearchUpdateListener(searchContent);
        });
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final FlexibleContentWidget container = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)container).addId("container");
        final FlexibleContentWidget sidebarContainer = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)sidebarContainer).addId("sidebar-container");
        final VerticalListWidget<Widget> sidebar = new VerticalListWidget<Widget>(this.listSession);
        sidebar.addId("sidebar");
        if (this.withSearch) {
            sidebar.addChild(this.searchWidget);
        }
        sidebar.addChild(new HrWidget());
        (this.categoryList = new VerticalListWidget<Widget>()).addId("categories");
        this.onCategoryListInitialize(this.categoryList);
        this.recheckSidebarButtons();
        sidebar.addChild(this.categoryList);
        final ScrollWidget scrollWidget = new ScrollWidget(sidebar);
        final DivWidget scrollContainer = new DivWidget();
        scrollContainer.addId("scroll-container");
        ((AbstractWidget<ScrollWidget>)scrollContainer).addChild(scrollWidget);
        sidebarContainer.addFlexibleContent(scrollContainer);
        final DivWidget sidebarFooter = new DivWidget();
        sidebarFooter.addId("sidebar-footer");
        this.initializeSidebarFooter(sidebarFooter);
        if (!sidebarFooter.getChildren().isEmpty()) {
            sidebarContainer.addContent(sidebarFooter);
        }
        container.addContent(sidebarContainer);
        container.addFlexibleContent(this.screenRendererWidget);
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(container);
    }
    
    protected void initializeSidebarFooter(final DivWidget widget) {
    }
    
    public abstract void onCategoryListInitialize(final VerticalListWidget<Widget> p0);
    
    public abstract void onSearchUpdateListener(final String p0);
    
    protected void displayScreen(final Activity activity) {
        if (this.screenRendererWidget.getScreen() == activity) {
            return;
        }
        this.screenRendererWidget.displayScreen(activity);
    }
    
    protected final void recheckSidebarButtons() {
        this.recheckSidebarButtons(this.screenRendererWidget.getScreen());
    }
    
    protected void recheckSidebarButtons(final ScreenInstance screen) {
        this.recheckButtonsFor(this.categoryList, screen);
    }
    
    protected final void recheckButtonsFor(final Widget widget) {
        this.recheckButtonsFor(widget, this.screenRendererWidget.getScreen());
    }
    
    protected final void recheckButtonsFor(final Widget widget, final ScreenInstance screenInstance) {
        if (widget == null) {
            return;
        }
        for (Widget child : widget.getChildren()) {
            if (child instanceof final WrappedWidget wrappedWidget) {
                child = wrappedWidget.childWidget();
            }
            if (child instanceof final SidebarButtonWidget buttonWidget) {
                final boolean test = buttonWidget.predicate.test(screenInstance);
                buttonWidget.setEnabled(!test);
                buttonWidget.setActive(test);
            }
        }
    }
    
    @AutoWidget
    public static class SidebarButtonWidget extends ButtonWidget
    {
        private final Predicate<ScreenInstance> predicate;
        
        private SidebarButtonWidget(@Nullable final Component component, @Nullable final Icon icon, @NotNull final Predicate<ScreenInstance> predicate) {
            Objects.requireNonNull(predicate);
            if (component != null) {
                this.component = component;
            }
            if (icon != null) {
                this.icon().set(icon);
            }
            this.predicate = predicate;
        }
        
        public static SidebarButtonWidget of(@NotNull final Component component, @NotNull final Predicate<ScreenInstance> predicate) {
            Objects.requireNonNull(component);
            return new SidebarButtonWidget(component, null, predicate);
        }
        
        public static SidebarButtonWidget i18n(@NotNull final String translationKey, @NotNull final Predicate<ScreenInstance> predicate) {
            Objects.requireNonNull(translationKey);
            return new SidebarButtonWidget(Component.translatable(translationKey, new Component[0]), null, predicate);
        }
        
        public static SidebarButtonWidget i18n(@NotNull final String translationKey, @NotNull final Icon icon, @NotNull final Predicate<ScreenInstance> predicate) {
            Objects.requireNonNull(translationKey);
            return new SidebarButtonWidget(Component.translatable(translationKey, new Component[0]), icon, predicate);
        }
        
        public static SidebarButtonWidget icon(@NotNull final Icon icon, @NotNull final Predicate<ScreenInstance> predicate) {
            Objects.requireNonNull(icon);
            return new SidebarButtonWidget(null, icon, predicate);
        }
        
        public static SidebarButtonWidget text(@NotNull final String text, @NotNull final Predicate<ScreenInstance> predicate) {
            Objects.requireNonNull(text);
            return new SidebarButtonWidget(Component.text(text), null, predicate);
        }
        
        @NotNull
        public Predicate<ScreenInstance> getPredicate() {
            return this.predicate;
        }
    }
}
