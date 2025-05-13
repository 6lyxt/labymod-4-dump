// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.Locale;
import java.util.Objects;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorData;
import java.util.regex.Pattern;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;

@AutoWidget
public class ColorPickerInputWidget extends FlexibleContentWidget
{
    private static final Pattern HEX_PATTERN;
    private final ColorData colorData;
    private ColorPickerInputWrapperWidget<TextFieldWidget> hexInputWidget;
    private ColorPickerInputWrapperWidget<CheckBoxWidget> chromaInputWidget;
    private ColorPickerInputWrapperWidget<SliderWidget> chromaSpeedInputWidget;
    private Integer lastHexValue;
    
    protected ColorPickerInputWidget(final ColorData colorData) {
        (this.colorData = colorData).addUpdateListener(this, this::update);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final boolean chroma = this.colorData.enabledChroma();
        final boolean chromaSpeed = this.colorData.enabledChromaSpeed();
        FlexibleContentWidget contentWidget = this;
        if (chroma && chromaSpeed) {
            contentWidget = new FlexibleContentWidget();
            ((AbstractWidget<Widget>)contentWidget).addId("input-wrapper");
            this.addContent(contentWidget);
        }
        else {
            ((AbstractWidget<Widget>)this).addId("standalone");
        }
        contentWidget.addFlexibleContent(this.hexInputWidget = this.hexWidget());
        if (chroma) {
            contentWidget.addContent(this.chromaInputWidget = this.chromaWidget());
            if (chromaSpeed) {
                this.addContent(this.chromaSpeedInputWidget = this.chromaSpeedInputWidget());
            }
        }
    }
    
    @Override
    public void tick() {
        super.tick();
        if (this.chromaSpeedInputWidget == null) {}
    }
    
    private ColorPickerInputWrapperWidget<TextFieldWidget> hexWidget() {
        return new ColorPickerInputWrapperWidget<TextFieldWidget>("hex", () -> {
            final TextFieldWidget textFieldWidget = new TextFieldWidget();
            textFieldWidget.validator(this::validateHex);
            this.updateHex(textFieldWidget);
            return textFieldWidget;
        });
    }
    
    private ColorPickerInputWrapperWidget<CheckBoxWidget> chromaWidget() {
        return new ColorPickerInputWrapperWidget<CheckBoxWidget>("rgb", () -> {
            final CheckBoxWidget checkBoxWidget = new CheckBoxWidget();
            this.updateChroma(checkBoxWidget);
            checkBoxWidget.setPressable(() -> this.colorData.setChroma(checkBoxWidget.state() == CheckBoxWidget.State.CHECKED));
            return checkBoxWidget;
        });
    }
    
    private ColorPickerInputWrapperWidget<SliderWidget> chromaSpeedInputWidget() {
        return new ColorPickerInputWrapperWidget<SliderWidget>("speed", () -> {
            new(net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.class)();
            final ColorData colorData = this.colorData;
            Objects.requireNonNull(colorData);
            final float steps;
            new SliderWidget(steps, colorData::setChromaSpeed);
            final SliderWidget sliderWidget2;
            final SliderWidget sliderWidget = sliderWidget2.range(0.25f, 5.0f);
            sliderWidget.setValue(this.colorData.getActualColor().getChromaSpeed());
            return sliderWidget;
        });
    }
    
    private void update() {
        if (this.hexInputWidget == null || this.hexInputWidget.getWidget() == null) {
            return;
        }
        this.updateHex(this.hexInputWidget.getWidget());
        if (this.chromaInputWidget != null) {
            this.updateChroma(this.chromaInputWidget.getWidget());
        }
    }
    
    private void updateHex(final TextFieldWidget widget) {
        final int value = 0xFFFFFF & this.colorData.getColor().get();
        if (this.lastHexValue != null && value == this.lastHexValue) {
            return;
        }
        this.lastHexValue = value;
        widget.setText(String.format(Locale.ROOT, "#%06X", value), true);
        this.setHexState(true);
    }
    
    private void updateChroma(final CheckBoxWidget widget) {
        widget.setState(this.colorData.getActualColor().isChroma() ? CheckBoxWidget.State.CHECKED : CheckBoxWidget.State.UNCHECKED);
    }
    
    private boolean validateHex(String content) {
        if (this.hexInputWidget == null || this.hexInputWidget.getWidget() == null) {
            return false;
        }
        final StringBuilder accepted = new StringBuilder();
        for (int i = 0; i < content.length(); ++i) {
            final char c = content.charAt(i);
            if (c == '#') {
                if (i != 0) {
                    return false;
                }
                accepted.append(c);
            }
            else {
                if (!ColorPickerInputWidget.HEX_PATTERN.matcher(String.valueOf(c)).matches()) {
                    return false;
                }
                accepted.append(c);
            }
        }
        content = accepted.toString();
        if (content.length() > 0 && content.toCharArray()[0] != '#') {
            content = "#" + content;
        }
        boolean valid = false;
        if (content.length() == 7) {
            try {
                final int newValue = Integer.decode(content);
                this.lastHexValue = newValue;
                this.colorData.setValue(newValue);
                valid = true;
            }
            catch (final NumberFormatException ex) {}
        }
        this.setHexState(valid);
        return content.length() == 0 || content.length() <= ((accepted.toString().toCharArray()[0] == '#') ? 7 : 6);
    }
    
    private void setHexState(final boolean valid) {
        if (valid) {
            this.hexInputWidget.addId("valid");
            this.hexInputWidget.removeId("invalid");
        }
        else {
            this.hexInputWidget.addId("invalid");
            this.hexInputWidget.removeId("valid");
        }
    }
    
    static {
        HEX_PATTERN = Pattern.compile("[0-9A-Fa-f]");
    }
}
