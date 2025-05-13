// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.Textures;
import net.labymod.api.util.TimeUnit;
import net.labymod.api.util.time.TimeUtil;
import java.text.SimpleDateFormat;
import net.labymod.api.util.I18n;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import javax.inject.Inject;
import net.labymod.api.client.component.Component;
import net.labymod.api.labynet.models.NameHistory;
import java.util.List;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabActivity;

@Singleton
@Link("activity/chat/input/name-history.lss")
@AutoActivity
@Referenceable
public class NameHistoryActivity extends ChatInputTabActivity<FlexibleContentWidget>
{
    private static final String HIDDEN = "\uff0d";
    private static final String PREFIX = "labymod.chatInput.tab.namehistory.";
    private final LabyNetController labyNetController;
    private final TextFieldWidget searchWidget;
    private Result<List<NameHistory>> lastResult;
    private String lastQuery;
    
    @Inject
    public NameHistoryActivity(final LabyNetController labyNetController) {
        this.labyNetController = labyNetController;
        this.lastResult = Result.empty();
        (this.searchWidget = new TextFieldWidget()).addId("search");
        this.searchWidget.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchWidget.validator(this.searchValidator());
        this.searchWidget.submitHandler(this.searchSubmitHandler());
        this.searchWidget.maximalLength(19);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.searchWidget.setFocused(true);
        this.contentWidget = (T)new FlexibleContentWidget();
        ((AbstractWidget<Widget>)this.contentWidget).addId("content");
        final VerticalListWidget<Widget> header = new VerticalListWidget<Widget>();
        header.addId("header");
        final DivWidget titleWrapper = new DivWidget();
        titleWrapper.addId("title-wrapper");
        final ComponentWidget title = ComponentWidget.i18n("labymod.chatInput.tab.namehistory.name");
        title.addId("title");
        ((AbstractWidget<ComponentWidget>)titleWrapper).addChild(title);
        header.addChild(titleWrapper);
        final FlexibleContentWidget searchWrapper = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)searchWrapper).addId("search-wrapper");
        searchWrapper.addFlexibleContent(this.searchWidget);
        final ButtonWidget searchButton = ButtonWidget.i18n("labymod.ui.button.search");
        ((AbstractWidget<Widget>)searchButton).addId("search-submit");
        searchButton.setPressable(() -> this.searchSubmitHandler().accept(this.searchWidget.getText()));
        searchWrapper.addContent(searchButton);
        header.addChild(searchWrapper);
        ((FlexibleContentWidget)this.contentWidget).addContent(header);
        if (this.lastQuery == null) {
            ((Document)this.document).addChild(this.contentWidget);
            return;
        }
        ComponentWidget statusWidget = null;
        if (this.lastResult.isEmpty()) {
            statusWidget = ComponentWidget.i18n("labymod.misc.loading");
        }
        else if (this.lastResult.hasException()) {
            if (this.lastResult.exception().getCause() instanceof NullPointerException) {
                statusWidget = ComponentWidget.i18n("labymod.chatInput.tab.namehistory.search.noResult");
            }
            else {
                statusWidget = ComponentWidget.i18n("labymod.misc.errorWithArgs", this.lastResult.exception().getClass().getSimpleName());
            }
        }
        if (statusWidget != null) {
            statusWidget.addId("status");
            final DivWidget statusWrapper = new DivWidget();
            statusWrapper.addId("status-wrapper");
            ((AbstractWidget<ComponentWidget>)statusWrapper).addChild(statusWidget);
            ((FlexibleContentWidget)this.contentWidget).addContent(statusWrapper);
            ((Document)this.document).addChild(this.contentWidget);
            return;
        }
        final VerticalListWidget<Widget> results = new VerticalListWidget<Widget>();
        results.addId("results");
        final List<NameHistory> value = this.lastResult.get();
        for (int i = 0; i < value.size(); ++i) {
            final NameHistory nameHistory = value.get(i);
            final HorizontalListWidget result = new HorizontalListWidget();
            ((AbstractWidget<Widget>)result).addId("result");
            final String name = nameHistory.getName();
            Component text;
            if (name.equals("\uff0d")) {
                text = Component.translatable("labymod.chatInput.tab.namehistory.result.hidden", new Component[0]);
            }
            else {
                text = Component.text(name);
            }
            final ComponentWidget nameWidget = ComponentWidget.component(text);
            nameWidget.addId("name");
            result.addEntry(nameWidget);
            String date = null;
            if (nameHistory.getChangedAtString() != null) {
                final String format;
                if (nameHistory.getChangedAt() == 0L || (format = I18n.getTranslation("labymod.chatInput.tab.namehistory.result.timeFormat", new Object[0])) == null) {
                    date = nameHistory.getChangedAtString();
                }
                else {
                    date = new SimpleDateFormat(format).format(nameHistory.getChangedAt());
                    if (i != value.size() - 1) {
                        final long nextTime = (i == 0) ? TimeUtil.getCurrentTimeMillis() : value.get(i - 1).getChangedAt();
                        final String duration = TimeUnit.parseToList(nextTime - nameHistory.getChangedAt()).get(0);
                        final ComponentWidget durationWidget = ComponentWidget.text(duration);
                        durationWidget.addId("duration");
                        result.addEntry(durationWidget);
                    }
                }
            }
            if (i == 0) {
                nameWidget.addId("current-name");
            }
            else if (i == value.size() - 1) {
                nameWidget.addId("original-name");
                final String originalName = I18n.translate("labymod.chatInput.tab.namehistory.result.original", new Object[0]);
                if (date == null) {
                    date = originalName;
                }
                else {
                    date = date + " (" + originalName;
                }
            }
            else {
                nameWidget.addId("previous-name");
            }
            if (date == null) {
                date = I18n.translate("labymod.chatInput.tab.namehistory.result.unknown", new Object[0]);
            }
            final ComponentWidget dateWidget = ComponentWidget.text(date);
            dateWidget.addId("date");
            result.addEntry(dateWidget);
            if (!nameHistory.isAccurate()) {
                final IconWidget inaccurate = new IconWidget(Textures.SpriteCommon.CIRCLE_WARNING);
                inaccurate.addId("inaccurate");
                inaccurate.setHoverComponent(Component.translatable("labymod.chatInput.tab.namehistory.result.inaccurate", new Component[0]));
                result.addEntry(inaccurate);
            }
            results.addChild(result);
        }
        ((FlexibleContentWidget)this.contentWidget).addFlexibleContent(new ScrollWidget(results));
        ((Document)this.document).addChild(this.contentWidget);
    }
    
    public void scheduleQuery(final String query) {
        this.searchWidget.setText(query);
        this.searchWidget.setFocused(true);
        this.searchSubmitHandler().accept(query);
    }
    
    @Override
    public void onCloseScreen() {
        this.searchWidget.setFocused(false);
        super.onCloseScreen();
    }
    
    private Predicate<String> searchValidator() {
        return name -> true;
    }
    
    private Consumer<String> searchSubmitHandler() {
        return name -> {
            if (this.lastQuery == null || !this.lastQuery.equals(name)) {
                final String search = name.trim();
                if (search.isEmpty()) {
                    this.lastQuery = null;
                    this.lastResult = Result.empty();
                    this.searchWidget.setFocused(false);
                    this.searchWidget.setText("");
                    this.reload();
                }
                else {
                    this.lastQuery = search;
                    this.lastResult = Result.empty();
                    this.searchWidget.setFocused(false);
                    this.reload();
                    this.labyNetController.loadNameHistory(search, result -> {
                        if (this.lastQuery == null || this.lastQuery.equals(search)) {
                            this.lastResult = result;
                            this.labyAPI.minecraft().executeOnRenderThread(this::reload);
                        }
                    });
                }
            }
        };
    }
}
