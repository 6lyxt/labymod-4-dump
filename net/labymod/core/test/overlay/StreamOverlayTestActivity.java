// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.test.overlay;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.io.ByteArrayInputStream;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.entity.player.ClientPlayer;
import java.util.Iterator;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.RenderPipeline;
import java.util.Locale;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.DataInputStream;
import java.net.Socket;
import java.util.concurrent.Executors;
import net.labymod.api.util.time.TimeUtil;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.resources.texture.GameImageTexture;
import java.net.ServerSocket;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.test.TestActivity;

@AutoActivity
public class StreamOverlayTestActivity extends TestActivity
{
    private static StreamOverlay overlay;
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (StreamOverlayTestActivity.overlay == null) {
            Laby.labyAPI().eventBus().registerListener(StreamOverlayTestActivity.overlay = new StreamOverlay());
        }
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        final float centerX = this.bounds().getCenterX();
        final float centerY = this.bounds().getCenterY();
        if (StreamOverlayTestActivity.overlay == null) {
            this.labyAPI.renderPipeline().textRenderer().pos(centerX, centerY).centered(true).text("Stream Overlay not enabled").render(context.stack());
        }
        else {
            this.labyAPI.renderPipeline().textRenderer().pos(centerX, centerY / 2.0f).centered(true).text(StreamOverlayTestActivity.overlay.getState()).render(context.stack());
            StreamOverlayTestActivity.overlay.renderStream(context.stack(), centerX - 100.0f, centerY / 2.0f + 10.0f, 200.0f, 112.5f);
        }
    }
    
    public static class StreamOverlay
    {
        private final ResourceLocation resourceLocation;
        private ServerSocket serverSocket;
        private byte[] frame;
        private GameImageTexture gameImageTexture;
        private String state;
        private final Map<Integer, Float> modifiers;
        private int hoverModifierIndex;
        private int draggingModifierIndex;
        private float modifierWindowX;
        private float modifierWindowY;
        private long timeLastRender;
        
        public StreamOverlay() {
            this.frame = new byte[0];
            this.state = "Initializing...";
            this.modifiers = new HashMap<Integer, Float>();
            this.hoverModifierIndex = -1;
            this.draggingModifierIndex = -1;
            this.timeLastRender = TimeUtil.getMillis();
            try {
                this.serverSocket = new ServerSocket(8085);
                Executors.newSingleThreadExecutor().execute(() -> {
                    try {
                        while (!this.serverSocket.isClosed()) {
                            try {
                                this.state = "Waiting for connection...";
                                final Socket socket = this.serverSocket.accept();
                                this.connection(socket);
                            }
                            catch (final Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                        this.state = "Closed";
                    }
                    catch (final Exception e3) {
                        e3.printStackTrace();
                        this.state = "Closed: " + e3.getMessage();
                    }
                    return;
                });
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
            this.resourceLocation = Laby.references().resourceLocationFactory().create("labymod", "test");
        }
        
        private void connection(final Socket socket) throws IOException {
            final InputStream inputStream = socket.getInputStream();
            final DataInputStream dataInputStream = new DataInputStream(inputStream);
            this.state = "Waiting for image...";
            while (!socket.isClosed()) {
                this.handle(dataInputStream);
            }
        }
        
        private void handle(final DataInputStream dataInputStream) throws IOException {
            final int length = dataInputStream.readInt();
            this.state = "Streaming...";
            final byte[] buffer = new byte[length];
            dataInputStream.readFully(buffer);
            final byte[] frame = decompress(buffer);
            synchronized (this) {
                this.frame = frame;
            }
            this.toResourceLocation(frame);
        }
        
        @Subscribe
        public void onRender(final IngameOverlayRenderEvent event) {
            if (event.phase() != Phase.POST) {
                return;
            }
            final Stack stack = event.stack();
            final Bounds bounds = Laby.labyAPI().minecraft().minecraftWindow().bounds();
            final float width = bounds.getWidth();
            final float height = bounds.getHeight();
            this.renderStreamOverlay(stack, 0.0f, 0.0f, width, height);
            this.renderModifiers(stack, this.modifierWindowX, this.modifierWindowY, 200.0f, height);
        }
        
        @Subscribe
        public void onMouseClick(final MouseButtonEvent event) {
            final MouseButtonEvent.Action action = event.action();
            if (action == MouseButtonEvent.Action.CLICK) {
                if (event.button() == MouseButton.LEFT) {
                    if (this.hoverModifierIndex == -2) {
                        this.modifiers.put(this.modifiers.size(), 0.0f);
                    }
                    else if (this.hoverModifierIndex >= 0) {
                        this.draggingModifierIndex = this.hoverModifierIndex;
                    }
                }
                if (event.button() == MouseButton.RIGHT) {
                    if (this.hoverModifierIndex == -2) {
                        this.modifiers.clear();
                    }
                    else if (this.hoverModifierIndex >= 0) {
                        this.modifiers.put(this.hoverModifierIndex, 0.0f);
                    }
                }
                if (event.button() == MouseButton.MIDDLE) {
                    this.draggingModifierIndex = -3;
                    if (KeyHandler.isControlDown()) {
                        System.out.println("stack.translate(" + getDisplay(0) + "F * UNIT, " + getDisplay(1) + "F * UNIT, " + getDisplay(2) + "F * UNIT);");
                        System.out.println("stack.rotate(" + getDisplay(3) + "F, 1, 0, 0);\nstack.rotate(" + getDisplay(4) + "F, 0, 1, 0);\nstack.rotate(" + getDisplay(5) + "F, 0, 0, 1);");
                    }
                }
            }
            if (action == MouseButtonEvent.Action.RELEASE) {
                this.draggingModifierIndex = -1;
            }
        }
        
        private void renderModifiers(final Stack stack, final float x, float y, final float width, final float height) {
            final RenderPipeline pipeline = Laby.labyAPI().renderPipeline();
            final MutableMouse mouse = Laby.labyAPI().minecraft().mouse();
            final int mouseX = mouse.getX();
            final int mouseY = mouse.getY();
            if (this.draggingModifierIndex == -3) {
                this.modifierWindowX += mouseX - this.modifierWindowX - width / 2.0f;
                this.modifierWindowY += mouseY - this.modifierWindowY - 20.0f;
            }
            this.hoverModifierIndex = -1;
            int minIndex = Integer.MAX_VALUE;
            int maxIndex = Integer.MIN_VALUE;
            for (final Map.Entry<Integer, Float> entry : this.modifiers.entrySet()) {
                minIndex = Math.min(minIndex, entry.getKey());
                maxIndex = Math.max(maxIndex, entry.getKey());
            }
            for (int index = minIndex; index <= maxIndex; ++index) {
                final Float modifier = this.modifiers.get(index);
                if (modifier != null) {
                    pipeline.rectangleRenderer().renderRectangle(stack, x + 1.0f, y + 1.0f, x + width, y + 8.0f, Integer.MIN_VALUE);
                    final int min = -8;
                    final int max = 8;
                    float progress = (modifier - min) / (max - min);
                    final boolean hover = mouse.isInside(x + 1.0f, y + 1.0f, width - 2.0f, 8.0);
                    if (hover) {
                        this.hoverModifierIndex = index;
                    }
                    if (this.draggingModifierIndex == index) {
                        progress = (mouseX - x - 1.0f) / (width - 2.0f);
                        progress = Math.max(0.0f, Math.min(1.0f, progress));
                        this.modifiers.put(this.draggingModifierIndex, min + progress * (max - min));
                    }
                    pipeline.rectangleRenderer().renderRectangle(stack, x + 1.0f + progress * (width - 2.0f), y + 1.0f, x + 1.0f + progress * (width - 2.0f) + 1.0f, y + 8.0f, hover ? Integer.MAX_VALUE : Integer.MIN_VALUE);
                    pipeline.textRenderer().pos(x + 2.0f + width, y + 2.5f).scale(0.67f).text(String.format(Locale.US, "%.2f", modifier)).centered(false).shadow(false).render(stack);
                    pipeline.textRenderer().pos(x - 5.0f, y + 2.5f).scale(0.67f).text("" + index).centered(false).shadow(false).render(stack);
                    y += 8.0f;
                }
            }
            final boolean hoverAdd = mouse.isInside(x + 1.0f, y + 1.0f, 8.0, 8.0);
            if (hoverAdd) {
                this.hoverModifierIndex = -2;
            }
            pipeline.rectangleRenderer().renderRectangle(stack, x + 1.0f, y + 1.0f, x + 8.0f, y + 8.0f, hoverAdd ? Integer.MAX_VALUE : Integer.MIN_VALUE);
            pipeline.textRenderer().pos(x + 2.0f, y + 1.0f).text("+").color(hoverAdd ? -16777216 : -1).centered(false).shadow(false).render(stack);
        }
        
        private void renderStreamOverlay(final Stack stack, final float x, final float y, final float width, final float height) {
            float opacity = 0.0f;
            final ClientPlayer clientPlayer = Laby.labyAPI().minecraft().getClientPlayer();
            if (clientPlayer != null && !KeyHandler.isShiftDown()) {
                final float z = (float)clientPlayer.position().getZ();
                final float min = 277.0f;
                final float max = 285.0f;
                if (z >= min && z <= max) {
                    opacity = 1.0f - (z - min) / (max - min);
                }
                else if (z < min) {
                    opacity = 1.0f;
                }
                if (opacity <= 0.0f || opacity >= 1.0f) {
                    opacity = 0.5f;
                }
            }
            opacity = Math.min(1.0f, opacity);
            stack.push();
            final RenderPipeline pipeline = Laby.labyAPI().renderPipeline();
            pipeline.setAlpha(opacity);
            this.renderStream(stack, x, y, width, height);
            pipeline.setAlpha(1.0f);
            stack.pop();
        }
        
        public void renderStream(final Stack stack, final float x, final float y, final float width, final float height) {
            this.timeLastRender = TimeUtil.getMillis();
            final byte[] frame;
            synchronized (this) {
                frame = this.frame;
            }
            if (frame == null || frame.length == 0) {
                return;
            }
            try {
                final ResourceLocation resourceLocation = this.resourceLocation;
                if (resourceLocation == null) {
                    return;
                }
                Laby.labyAPI().renderPipeline().resourceRenderer().texture(resourceLocation).pos(x, y).resolution(width, height).sprite(0.0f, 0.0f, width, height).size(width, height).render(stack);
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
        }
        
        private ResourceLocation toResourceLocation(final byte[] buffer) {
            final int width = (buffer[0] & 0xFF) << 24 | (buffer[1] & 0xFF) << 16 | (buffer[2] & 0xFF) << 8 | (buffer[3] & 0xFF);
            final int height = (buffer[4] & 0xFF) << 24 | (buffer[5] & 0xFF) << 16 | (buffer[6] & 0xFF) << 8 | (buffer[7] & 0xFF);
            if (width <= 0 || height <= 0) {
                return null;
            }
            final GameImageTexture.Factory factory = Laby.references().gameImageTextureFactory();
            final TextureRepository repository = Laby.references().textureRepository();
            final GameImageProvider provider = Laby.references().gameImageProvider();
            if (this.gameImageTexture == null || this.gameImageTexture.getImage().getWidth() != width || this.gameImageTexture.getImage().getHeight() != height) {
                final GameImage gameImage = provider.createImage(width, height);
                this.gameImageTexture = factory.create(this.resourceLocation, gameImage);
            }
            final boolean swap = MinecraftVersions.V1_12_2.orOlder();
            final int a = swap ? 16 : 0;
            final int b = swap ? 0 : 16;
            final GameImage image = this.gameImageTexture.getImage();
            for (int index = 0; index < width * height; ++index) {
                image.setARGB(index % width, index / width, 0xFF000000 | (buffer[8 + index * 3] & 0xFF) << a | (buffer[8 + index * 3 + 1] & 0xFF) << 8 | (buffer[8 + index * 3 + 2] & 0xFF) << b);
            }
            final long timeSinceLastRender = TimeUtil.getMillis() - this.timeLastRender;
            if (timeSinceLastRender < 1000L) {
                repository.register(this.resourceLocation, this.gameImageTexture);
            }
            return this.resourceLocation;
        }
        
        public static byte[] decompress(final byte[] input) {
            try {
                final byte[] buffer = new byte[1024];
                final ByteArrayInputStream bis = new ByteArrayInputStream(input);
                final GZIPInputStream gis = new GZIPInputStream(bis);
                final ByteArrayOutputStream out = new ByteArrayOutputStream();
                int len;
                while ((len = gis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                gis.close();
                out.close();
                return out.toByteArray();
            }
            catch (final IOException e) {
                e.printStackTrace();
                return input;
            }
        }
        
        public String getState() {
            return this.state;
        }
        
        public float getModifierInternal(final int index) {
            return this.modifiers.computeIfAbsent(index, i -> 0.0f);
        }
        
        public static float getModifier(final int index) {
            return (StreamOverlayTestActivity.overlay == null) ? 0.0f : StreamOverlayTestActivity.overlay.getModifierInternal(index);
        }
        
        public static void applyModifierTranslate(final Stack stack) {
            final float unit = 0.0625f;
            stack.translate(getModifier(0) * unit, getModifier(1) * unit, getModifier(2) * unit);
        }
        
        public static void applyModifierRotate(final Stack stack) {
            stack.rotate(getModifier(3), 1.0f, 0.0f, 0.0f);
            stack.rotate(getModifier(4), 0.0f, 1.0f, 0.0f);
            stack.rotate(getModifier(5), 0.0f, 0.0f, 1.0f);
        }
        
        private static String getDisplay(final int index) {
            return String.format(Locale.US, "%.2f", getModifier(index));
        }
    }
}
