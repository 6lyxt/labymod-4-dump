// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.activities;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.Laby;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import java.util.function.Consumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.TitledActivity;

@Link("activity/confirm.lss")
@AutoActivity
public class ConfirmActivity extends TitledActivity
{
    private final Component confirmText;
    private final Component cancelText;
    private final Consumer<Boolean> responseConsumer;
    private boolean fired;
    protected HorizontalListWidget buttonRow;
    
    public ConfirmActivity(final Component title, final Consumer<Boolean> responseConsumer) {
        this(title, Component.translatable("gui.yes", new Component[0]), Component.translatable("gui.no", new Component[0]), responseConsumer);
    }
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public ConfirmActivity(final Component title, final String confirmText, final String cancelText, final Consumer<Boolean> responseConsumer) {
        this(title, Component.text(confirmText), Component.text(cancelText), responseConsumer);
    }
    
    private ConfirmActivity(final Component title, final Component confirmText, final Component cancelText, final Consumer<Boolean> responseConsumer) {
        super(title);
        this.confirmText = confirmText;
        this.cancelText = cancelText;
        this.responseConsumer = responseConsumer;
    }
    
    public static void confirm(final Component title, final Consumer<Boolean> responseConsumer) {
        final ConfirmActivity activity = new ConfirmActivity(title, responseConsumer);
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
    }
    
    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static void confirm(final Component title, final String confirmText, final String cancelText, final Consumer<Boolean> responseConsumer) {
        final ConfirmActivity activity = new ConfirmActivity(title, confirmText, cancelText, responseConsumer);
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
    }
    
    public static void confirm(final Component title, final Component confirmText, final Component cancelText, final Consumer<Boolean> responseConsumer) {
        final ConfirmActivity activity = new ConfirmActivity(title, confirmText, cancelText, responseConsumer);
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        ((AbstractWidget<Widget>)(this.buttonRow = new HorizontalListWidget())).addId("button-row");
        this.buttonRow.addEntry(ButtonWidget.component(this.confirmText, () -> this.clicked(true)));
        this.buttonRow.addEntry(ButtonWidget.component(this.cancelText, () -> this.clicked(false)));
        ((AbstractWidget<HorizontalListWidget>)super.document).addChild(this.buttonRow);
    }
    
    private void clicked(final boolean accepted) {
        this.accept(accepted);
        super.displayPreviousScreen();
    }
    
    private void accept(final Boolean response) {
        if (!this.fired) {
            this.fired = true;
            this.responseConsumer.accept(response);
        }
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        this.accept(null);
    }
}
