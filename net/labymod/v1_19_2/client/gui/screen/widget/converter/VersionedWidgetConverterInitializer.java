// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.gui.screen.widget.converter;

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
        registry.exclude(epj.class, eng.class, emv.class, enc.class, emo.class, ens.class, ekg.class, emm.class, emx.class, emb.class, emt.class, enn.class, eni.class);
        registry.exclude(ExclusionStrategy.widget(ekv.class, ehx.class));
        registry.register(new ButtonConverter(), ehp.class, ehv.class, ehy.class, eia.class);
        registry.register(new SliderConverter(), ehj.class, efx.i.class, ehm.class, eim.class);
        registry.register(new TextFieldConverter(), ehx.class);
    }
}
