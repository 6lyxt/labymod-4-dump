// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.widgetoptions;

import java.util.Collections;
import net.labymod.core.labymodnet.models.CosmeticOption;
import net.labymod.core.labymodnet.models.CosmeticOptions;
import net.labymod.core.labymodnet.widgetoptions.types.CheckBoxWidgetOption;
import net.labymod.core.labymodnet.widgetoptions.types.DropdownWidgetOption;
import net.labymod.core.labymodnet.widgetoptions.types.SliderWidgetOption;
import net.labymod.core.labymodnet.widgetoptions.types.ColorPickerWidgetOption;
import java.util.ArrayList;
import net.labymod.core.labymodnet.models.Cosmetic;
import java.util.List;

public class WidgetOptionService
{
    private static final List<WidgetOption> EMPTY;
    private static final String COLOR_IDENTIFIER = "rgb";
    private static final String OFFSET_IDENTIFIER = "offset";
    private final OptionProvider provider;
    
    public WidgetOptionService(final OptionProvider provider) {
        this.provider = provider;
    }
    
    public List<WidgetOption> getOptions(final Cosmetic cosmetic, final Runnable errorCallback) {
        final String[] data = cosmetic.getData();
        final String[] options = cosmetic.getOptions();
        if (data == null || data.length == 0 || options.length != data.length) {
            if (errorCallback != null) {
                errorCallback.run();
            }
            return WidgetOptionService.EMPTY;
        }
        List<WidgetOption> list = null;
        for (int i = 0; i < options.length; ++i) {
            final WidgetOption option = this.createOption(i, options[i]);
            if (option != null) {
                if (list == null) {
                    list = new ArrayList<WidgetOption>();
                }
                list.add(option);
            }
        }
        return (list == null) ? WidgetOptionService.EMPTY : list;
    }
    
    private WidgetOption createOption(final int index, final String optionName) {
        if (optionName.equals("rgb")) {
            return new ColorPickerWidgetOption(optionName, index);
        }
        if (optionName.startsWith("offset")) {
            return new SliderWidgetOption(optionName, index);
        }
        final CosmeticOptions cosmeticOptions = this.provider.getCosmeticOptions();
        if (cosmeticOptions == null) {
            return null;
        }
        final CosmeticOption cosmeticOption = cosmeticOptions.getOptions(optionName);
        if (cosmeticOption == null) {
            return null;
        }
        switch (cosmeticOption.type()) {
            case DROPDOWN: {
                return new DropdownWidgetOption(cosmeticOption, optionName, index);
            }
            case CHECKBOX: {
                return new CheckBoxWidgetOption(cosmeticOption, optionName, index);
            }
            default: {
                return null;
            }
        }
    }
    
    static {
        EMPTY = Collections.emptyList();
    }
}
