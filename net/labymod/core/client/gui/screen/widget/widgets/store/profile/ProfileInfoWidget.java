// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.store.profile;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.io.web.result.Result;
import java.util.Locale;
import java.util.Iterator;
import java.util.Optional;
import java.util.List;
import net.labymod.api.util.markdown.MarkdownDocument;
import java.util.concurrent.TimeUnit;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.HashMap;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import java.util.Map;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

@AutoWidget
@Link("activity/flint/profile-info.lss")
public class ProfileInfoWidget extends VerticalListWidget<Widget>
{
    private final Map<State, ButtonWidget> buttons;
    private final FlintModification modification;
    private State state;
    private long loadingStart;
    private long lastLoadingStart;
    
    public ProfileInfoWidget(final FlintModification modification) {
        this.modification = modification;
        this.buttons = new HashMap<State, ButtonWidget>();
        this.state = State.DESCRIPTION;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.buttons.clear();
        final HorizontalListWidget buttons = new HorizontalListWidget();
        ((AbstractWidget<Widget>)buttons).addId("info-button-container");
        buttons.addEntry(this.createInfoButton(State.DESCRIPTION, "description"));
        buttons.addEntry(this.createInfoButton(State.CHANGELOG, "changelog"));
        buttons.addEntry(this.createInfoButton(State.REVIEWS, "reviews"));
        ((AbstractWidget<HorizontalListWidget>)this).addChild(buttons);
        Widget infoWidget = null;
        switch (this.state.ordinal()) {
            case 0: {
                final DescriptionWidget descriptionWidget = this.descriptionWidget();
                if (descriptionWidget != null) {
                    infoWidget = descriptionWidget;
                    break;
                }
                if (this.loadingStart == 0L) {
                    this.loadingStart = TimeUtil.getMillis() + 1000L;
                    break;
                }
                break;
            }
            case 1: {
                final VerticalListWidget<ChangelogWidget> changelogWidget = this.changelogWidget();
                if (changelogWidget != null && !changelogWidget.getChildren().isEmpty()) {
                    infoWidget = changelogWidget;
                    break;
                }
                if (this.loadingStart == 0L) {
                    this.loadingStart = TimeUtil.getMillis() + 1000L;
                    break;
                }
                break;
            }
            case 2: {
                final VerticalListWidget<ReviewWidget> reviewsWidget = this.reviewWidget();
                if (reviewsWidget == null) {
                    if (this.loadingStart == 0L) {
                        this.loadingStart = TimeUtil.getMillis() + 1000L;
                        break;
                    }
                    break;
                }
                else {
                    if (reviewsWidget.getChildren().isEmpty()) {
                        infoWidget = ComponentWidget.i18n(this.getTranslationKey(this.state, "noReviews"));
                        break;
                    }
                    infoWidget = reviewsWidget;
                    break;
                }
                break;
            }
        }
        if (infoWidget == null) {
            infoWidget = this.loadingInfoWidget(ComponentWidget.i18n(this.getTranslationKey(this.state, "loading")));
            if (infoWidget == null) {
                return;
            }
        }
        if (infoWidget instanceof final ComponentWidget componentWidget) {
            if (componentWidget.component() instanceof TranslatableComponent) {
                infoWidget.addId("info-information");
            }
        }
        infoWidget.addId("info-content");
        this.addChild(infoWidget);
    }
    
    private Widget loadingInfoWidget(final Widget widget) {
        if (this.loadingStart < TimeUtil.getMillis()) {
            return widget;
        }
        if (this.lastLoadingStart == this.loadingStart) {
            return null;
        }
        this.lastLoadingStart = this.loadingStart;
        Task.builder(() -> {
            if (this.loadingStart == 0L) {
                return;
            }
            else {
                this.loadingStart = TimeUtil.getMillis();
                this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
                return;
            }
        }).delay(500L, TimeUnit.MILLISECONDS).build().execute();
        return null;
    }
    
    private DescriptionWidget descriptionWidget() {
        final MarkdownDocument description = this.modification.getOrLoadDescription(result -> {
            if (result.isPresent()) {
                this.loadingStart = 0L;
                this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
            }
            return;
        });
        if (description == null) {
            return null;
        }
        return new DescriptionWidget(this.modification, description);
    }
    
    private VerticalListWidget<ReviewWidget> reviewWidget() {
        final Optional<List<FlintModification.Review>> reviews = this.modification.getOrLoadReviews(result -> {
            if (result.isPresent()) {
                this.loadingStart = 0L;
                this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
            }
            return;
        });
        if (!reviews.isPresent()) {
            return null;
        }
        final VerticalListWidget<ReviewWidget> reviewsWidget = new VerticalListWidget<ReviewWidget>();
        for (final FlintModification.Review review : reviews.get()) {
            reviewsWidget.addChild(new ReviewWidget(review));
        }
        return reviewsWidget;
    }
    
    private VerticalListWidget<ChangelogWidget> changelogWidget() {
        final Optional<List<FlintModification.Changelog>> changelogs = this.modification.getOrLoadChangelog(result -> {
            if (result.isPresent()) {
                this.loadingStart = 0L;
                this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
            }
            return;
        });
        if (!changelogs.isPresent()) {
            return null;
        }
        final VerticalListWidget<ChangelogWidget> changelogWidget = new VerticalListWidget<ChangelogWidget>();
        for (final FlintModification.Changelog changelog : changelogs.get()) {
            changelogWidget.addChild(new ChangelogWidget(this.modification, changelog));
        }
        return changelogWidget;
    }
    
    private String getTranslationKey(final State state, final String key) {
        return "labymod.addons.store.profile.info." + state.name().toLowerCase(Locale.ROOT) + "." + key;
    }
    
    private ButtonWidget createInfoButton(final State state, final String name) {
        final ButtonWidget button = ButtonWidget.i18n(this.getTranslationKey(state, "name"), () -> {
            if (this.state == state) {
                return;
            }
            else {
                this.state = state;
                this.loadingStart = 0L;
                this.reInitialize();
                this.buttons.entrySet().iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final Map.Entry<State, ButtonWidget> entry = iterator.next();
                    entry.getValue().setEnabled(entry.getKey() != state);
                    entry.getValue().setActive(entry.getKey() == state);
                }
                return;
            }
        });
        ((AbstractWidget<Widget>)button).addId("info-button");
        button.setEnabled(this.state != state);
        button.setActive(this.state == state);
        this.buttons.put(state, button);
        return button;
    }
    
    private enum State
    {
        DESCRIPTION, 
        CHANGELOG, 
        REVIEWS;
    }
}
