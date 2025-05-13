// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.renderer;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.property.Property;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.Resources;
import net.labymod.api.client.resources.texture.Texture;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;

@AutoWidget
public class VariableIconWidget extends IconWidget
{
    private final LssProperty<Float> iconHeight;
    private final LssProperty<Float> iconWidth;
    private final ResourceLocation fallbackLocation;
    private final VariableUrlFunction urlSupplier;
    private final String baseUrl;
    private boolean recalculate;
    private int appliedHeight;
    private int appliedWidth;
    
    public VariableIconWidget(final ResourceLocation fallbackLocation, final String baseUrl, final VariableUrlFunction urlSupplier) {
        super((fallbackLocation == null) ? null : Icon.texture(fallbackLocation));
        this.iconHeight = new LssProperty<Float>(0.0f);
        this.iconWidth = new LssProperty<Float>(0.0f);
        this.appliedHeight = -1;
        this.appliedWidth = -1;
        this.fallbackLocation = fallbackLocation;
        this.urlSupplier = urlSupplier;
        this.baseUrl = baseUrl;
        this.iconWidth.addChangeListener((p, o, n) -> this.recalculate = true);
        this.iconHeight.addChangeListener((p, o, n) -> this.recalculate = true);
    }
    
    public VariableIconWidget(final String baseUrl, final VariableUrlFunction urlSupplier) {
        this(null, baseUrl, urlSupplier);
    }
    
    @Override
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        if (!this.isVisible()) {
            return;
        }
        if (this.urlSupplier != null && this.baseUrl != null) {
            this.recalculate = false;
            final float scale = this.labyAPI.minecraft().minecraftWindow().getScale();
            final int height = (int)(this.iconHeight.get() * scale);
            final int width = (int)(this.iconWidth.get() * scale);
            boolean sizeChanged = false;
            if (height != this.appliedHeight) {
                this.appliedHeight = height;
                sizeChanged = true;
            }
            if (width != this.appliedWidth) {
                this.appliedWidth = width;
                sizeChanged = true;
            }
            if (sizeChanged) {
                final String url = this.urlSupplier.get(this.baseUrl, width, height);
                if (url != null) {
                    final Icon icon = this.icon().get();
                    if (icon != null) {
                        final Resources resources = Laby.labyAPI().renderPipeline().resources();
                        final TextureRepository repository = resources.textureRepository();
                        final ResourceLocation resourceLocation = resources.resourceLocationFactory().createLabyMod("icon/" + url.hashCode());
                        icon.setResourceLocationSupplier(() -> repository.getOrRegisterTexture(resourceLocation, this.fallbackLocation, url, null).getCompleted());
                    }
                    else {
                        this.icon().set(Icon.url(url, this.fallbackLocation));
                    }
                }
            }
        }
    }
    
    public LssProperty<Float> iconWidth() {
        return this.iconWidth;
    }
    
    public LssProperty<Float> iconHeight() {
        return this.iconHeight;
    }
    
    @FunctionalInterface
    public interface VariableUrlFunction
    {
        @Nullable
        String get(final String p0, final int p1, final int p2);
    }
}
