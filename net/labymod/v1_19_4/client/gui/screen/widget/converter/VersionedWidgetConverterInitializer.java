// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.AbstractWidgetConverter;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;

@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer
{
    @Override
    public void initialize(final WidgetConverterRegistry registry) {
        registry.exclude(exh.class, eva.class, eun.class, euv.class, eug.class, evn.class, erw.class, eue.class, eup.class, ets.class, eul.class, evi.class, evc.class);
        registry.exclude(ExclusionStrategy.widget(esm.class, eol.class));
        registry.register(new ButtonConverter(), eoc.class, eoj.class, eom.class, eop.class);
        registry.register(new SliderConverter(), enx.class, enu.class, emk.i.class);
        registry.register(new TextFieldConverter(), eol.class);
        registry.register(new TabLayoutConverter(), ept.class);
        registry.register(new StringConverter(), epc.class);
    }
}
