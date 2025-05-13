// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.hud.hudwidget;

import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.resources.ResourceLocation;
import java.text.DecimalFormat;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.serverapi.core.model.display.EconomyDisplay;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.event.labymod.serverapi.EconomyUpdateEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import java.util.Iterator;
import net.labymod.api.util.Color;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import java.util.HashMap;
import net.labymod.api.client.render.font.ComponentRenderer;
import java.util.Map;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;

public class EconomyDisplayHudWidget extends SimpleHudWidget<EconomyDisplayHudWidgetConfig>
{
    private static final Icon DEFAULT_BANK_ICON;
    private static final Icon DEFAULT_CASH_ICON;
    private static final Map<String, EconomyDisplayWrapper> DUMMY_ECONOMIES;
    private final Map<String, EconomyDisplayWrapper> economies;
    private final ComponentRenderer componentRenderer;
    
    public EconomyDisplayHudWidget() {
        super("economy_display", EconomyDisplayHudWidgetConfig.class);
        this.economies = new HashMap<String, EconomyDisplayWrapper>();
        this.bindCategory(HudWidgetCategory.INGAME);
        this.componentRenderer = this.labyAPI.renderPipeline().componentRenderer();
    }
    
    @Override
    public void render(final Stack stack, final MutableMouse mouse, final float partialTicks, final boolean isEditorContext, final HudSize size) {
        final Map<String, EconomyDisplayWrapper> economies = (isEditorContext && !this.isVisibleInGame()) ? EconomyDisplayHudWidget.DUMMY_ECONOMIES : this.economies;
        float y = 2.0f;
        float widgetWidth = size.getActualWidth();
        final boolean interpolate = ((EconomyDisplayHudWidgetConfig)this.config).interpolateBalance.get();
        for (final EconomyDisplayWrapper display : economies.values()) {
            if (!display.economy.isVisible()) {
                continue;
            }
            ConfigProperty<Color> colorProperty = ((EconomyDisplayHudWidgetConfig)this.config).defaultColor;
            final String key = display.economy.getKey();
            if (key.equals("bank") && ((EconomyDisplayHudWidgetConfig)this.config).bankUseOwnColor.get()) {
                colorProperty = ((EconomyDisplayHudWidgetConfig)this.config).bankColor;
            }
            else if (key.equals("cash") && ((EconomyDisplayHudWidgetConfig)this.config).cashUseOwnColor.get()) {
                colorProperty = ((EconomyDisplayHudWidgetConfig)this.config).cashColor;
            }
            final RenderableComponent text = RenderableComponent.of(display.component(interpolate));
            final float iconSize = (text.getHeight() - 1.0f) * 2.0f;
            final float textWidth = text.getWidth() * 2.0f;
            final boolean right = this.anchor().isRight();
            final float iconX = right ? (widgetWidth - 1.0f - iconSize) : 1.0f;
            final float textX = right ? (widgetWidth - 1.0f - iconSize - textWidth - 4.0f) : (iconSize + 4.0f + 1.0f);
            final Icon icon = display.icon();
            if (stack != null) {
                icon.render(stack, iconX, y, iconSize);
            }
            if (stack != null) {
                this.componentRenderer.builder().scale(2.0f).text(text).pos(textX, y).color(colorProperty.get().get()).render(stack);
            }
            final float width = iconSize + textWidth + 4.0f;
            widgetWidth = Math.max(widgetWidth, width);
            y += text.getHeight() * 2.0f;
            y += 2.0f;
        }
        y -= 3.0f;
        size.setHeight(y);
        size.setWidth(widgetWidth);
    }
    
    @Override
    public boolean isVisibleInGame() {
        for (final EconomyDisplayWrapper value : this.economies.values()) {
            if (value.economy.isVisible()) {
                return true;
            }
        }
        return false;
    }
    
    @Subscribe
    public void onServerLeave(final ServerDisconnectEvent event) {
        this.economies.clear();
    }
    
    @Subscribe(126)
    public void onEconomyUpdate(final EconomyUpdateEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final EconomyDisplay economy = event.economy();
        if (ThreadSafe.isRenderThread()) {
            this.applyEconomyUpdate(economy);
        }
        else {
            ThreadSafe.executeOnRenderThread(() -> this.applyEconomyUpdate(economy));
        }
    }
    
    private void applyEconomyUpdate(final EconomyDisplay economy) {
        final EconomyDisplayWrapper display = this.economies.computeIfAbsent(economy.getKey(), key -> new EconomyDisplayWrapper(economy));
        display.economy = economy;
        boolean changed = false;
        final double balance = economy.getBalance();
        if (display.balance != balance) {
            changed = true;
            display.balance = balance;
            display.balanceUpdatedAt = TimeUtil.getCurrentTimeMillis();
        }
        String format = null;
        double divisor = Double.NaN;
        final EconomyDisplay.DecimalFormat decimalFormat = economy.getDecimalFormat();
        if (decimalFormat != null) {
            format = decimalFormat.format();
            divisor = decimalFormat.divisor();
        }
        if (format != display.format || divisor != display.divisor) {
            changed = true;
            display.format = format;
            display.divisor = divisor;
            display.decimalFormat = ((format == null) ? null : new DecimalFormat(format));
        }
        final String iconUrl = economy.getIconUrl();
        if (iconUrl != null && !iconUrl.equals(display.iconUrl)) {
            display.iconUrl = iconUrl;
            display.icon = null;
        }
        else if (iconUrl == null && display.iconUrl != null) {
            display.iconUrl = null;
            display.icon = null;
        }
        if (changed) {
            display.component = null;
        }
    }
    
    static {
        DEFAULT_BANK_ICON = Icon.texture(ResourceLocation.create("labymod", "textures/hudwidgets/economy/bank.png"));
        DEFAULT_CASH_ICON = Icon.texture(ResourceLocation.create("labymod", "textures/hudwidgets/economy/cash.png"));
        (DUMMY_ECONOMIES = new HashMap<String, EconomyDisplayWrapper>()).put("bank", new EconomyDisplayWrapper(new EconomyDisplay("bank", true, 6459.0, (String)null, (EconomyDisplay.DecimalFormat)null)));
        EconomyDisplayHudWidget.DUMMY_ECONOMIES.put("cash", new EconomyDisplayWrapper(new EconomyDisplay("cash", true, 2369.0, (String)null, (EconomyDisplay.DecimalFormat)null)));
    }
    
    private static class EconomyDisplayWrapper
    {
        private static final long INTERPOLATION_DURATION = 1000L;
        private EconomyDisplay economy;
        private Icon icon;
        private String iconUrl;
        private Component component;
        private double balance;
        private double prevBalance;
        private long balanceUpdatedAt;
        private DecimalFormat decimalFormat;
        private String format;
        private double divisor;
        
        public EconomyDisplayWrapper(final EconomyDisplay economy) {
            this.economy = economy;
            this.balance = economy.getBalance();
            this.balanceUpdatedAt = TimeUtil.getCurrentTimeMillis();
            this.iconUrl = economy.getIconUrl();
            final EconomyDisplay.DecimalFormat decimalFormat = economy.getDecimalFormat();
            if (decimalFormat != null) {
                this.format = decimalFormat.format();
                this.divisor = decimalFormat.divisor();
                this.decimalFormat = ((this.format == null) ? null : new DecimalFormat(this.format));
            }
        }
        
        public Icon icon() {
            if (this.icon == null) {
                if (this.iconUrl == null) {
                    if (this.economy.getKey().equals("bank")) {
                        this.icon = EconomyDisplayHudWidget.DEFAULT_BANK_ICON;
                    }
                    else {
                        this.icon = EconomyDisplayHudWidget.DEFAULT_CASH_ICON;
                    }
                }
                else {
                    this.icon = Icon.url(this.iconUrl);
                }
            }
            return this.icon;
        }
        
        private double getInterpolatedBalance() {
            final long timePassed = TimeUtil.getCurrentTimeMillis() - this.balanceUpdatedAt;
            if (timePassed > 1000L) {
                return this.balance;
            }
            final double input = Math.min(1000L, timePassed) / 1000.0 * 4.0;
            final double sigmoid = 1.0 / (1.0 + Math.exp(-input * 2.0 + 4.0));
            final double economyDifference = this.prevBalance - this.balance;
            return (double)Math.round(this.prevBalance - economyDifference * sigmoid);
        }
        
        public Component component(final boolean interpolated) {
            double balance = this.balance;
            if (interpolated) {
                final double interpolatedBalance = this.getInterpolatedBalance();
                if (interpolatedBalance != this.prevBalance) {
                    this.component = null;
                    balance = interpolatedBalance;
                }
            }
            if (this.component == null) {
                String value;
                if (this.decimalFormat == null || this.divisor <= 0.0) {
                    if (balance == (int)balance) {
                        value = String.valueOf((int)balance);
                    }
                    else {
                        value = String.valueOf(balance);
                    }
                }
                else {
                    final double dividedBalance = balance / this.divisor;
                    value = this.decimalFormat.format(dividedBalance);
                }
                this.prevBalance = balance;
                this.component = Component.text(value);
            }
            return this.component;
        }
    }
    
    public static class EconomyDisplayHudWidgetConfig extends HudWidgetConfig
    {
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> interpolateBalance;
        @ColorPickerWidget.ColorPickerSetting
        private final ConfigProperty<Color> defaultColor;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> bankUseOwnColor;
        @ColorPickerWidget.ColorPickerSetting
        @SettingRequires("bankUseOwnColor")
        private final ConfigProperty<Color> bankColor;
        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> cashUseOwnColor;
        @ColorPickerWidget.ColorPickerSetting
        @SettingRequires("cashUseOwnColor")
        private final ConfigProperty<Color> cashColor;
        
        public EconomyDisplayHudWidgetConfig() {
            this.interpolateBalance = new ConfigProperty<Boolean>(true);
            this.defaultColor = new ConfigProperty<Color>(Color.WHITE);
            this.bankUseOwnColor = new ConfigProperty<Boolean>(false);
            this.bankColor = new ConfigProperty<Color>(Color.WHITE);
            this.cashUseOwnColor = new ConfigProperty<Boolean>(false);
            this.cashColor = new ConfigProperty<Color>(Color.WHITE);
        }
    }
}
