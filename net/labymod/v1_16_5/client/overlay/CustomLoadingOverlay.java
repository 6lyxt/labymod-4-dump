// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.overlay;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.core.main.LabyMod;
import net.labymod.core.client.gui.background.DynamicBackgroundController;
import net.labymod.core.client.gui.background.bootlogo.AbstractBootLogoRenderer;
import java.util.Optional;
import java.util.function.Consumer;

public class CustomLoadingOverlay extends doh
{
    private final djz minecraft;
    private final ace reload;
    private final Consumer<Optional<Throwable>> onFinish;
    private final AbstractBootLogoRenderer loadingRenderer;
    private final DynamicBackgroundController caveRenderer;
    
    public CustomLoadingOverlay(final djz minecraft, final ace reload, final Consumer<Optional<Throwable>> onFinish, final boolean fadeIn) {
        super(minecraft, reload, (Consumer)onFinish, fadeIn);
        this.minecraft = minecraft;
        this.reload = reload;
        this.onFinish = onFinish;
        this.loadingRenderer = LabyMod.references().bootLogoController().renderer();
        this.caveRenderer = LabyMod.references().dynamicBackgroundController();
        this.loadingRenderer.initialize();
        this.loadingRenderer.preloadResources();
    }
    
    public void a(final dfm stack, final int mouseX, final int mouseY, final float partialTicks) {
        final dez window = this.minecraft.aD();
        final int width = window.o();
        final int height = window.p();
        final float progress = this.reload.b();
        this.loadingRenderer.updateProgress(progress, true);
        final Stack apiStack = ((VanillaStackAccessor)stack).stack();
        this.loadingRenderer.render(apiStack, 0.0f, 0.0f, (float)width, (float)height, partialTicks);
        if (this.reload.d()) {
            this.minecraft.a((don)null);
            try {
                this.reload.e();
                this.onFinish.accept(Optional.empty());
            }
            catch (final Throwable throwable) {
                this.onFinish.accept(Optional.of(throwable));
            }
            if (this.minecraft.y != null) {
                this.minecraft.y.b(this.minecraft, width, height);
            }
            this.caveRenderer.playOpening();
        }
    }
}
