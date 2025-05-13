// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labynet.insight.controller;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.core.labynet.insight.model.ScreenshotInsight;
import com.google.gson.JsonSyntaxException;
import net.labymod.api.util.GsonUtil;
import com.google.gson.JsonObject;
import net.labymod.api.client.screenshot.ScreenshotUtil;
import net.labymod.core.labynet.insight.util.ImageCodec;
import java.io.DataOutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.Laby;
import net.labymod.core.labynet.exception.ScreenshotException;
import net.labymod.core.client.screenshot.meta.ScreenshotMeta;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import java.util.function.Consumer;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class InsightUploader
{
    private static final Logging LOGGER;
    private static final String[] FORMATS;
    private final ExecutorService executor;
    
    public InsightUploader() {
        this.executor = Executors.newSingleThreadExecutor();
    }
    
    public void uploadAsync(final Path file, final Consumer<String> successCallback, final Consumer<Exception> errorCallback) {
        PopupWidget.builder().title(Component.translatable("labymod.activity.screenshotBrowser.viewer.upload.warning", new Component[0])).confirmCallback(() -> this.executor.execute(() -> {
            try {
                final String url = this.upload(file);
                successCallback.accept(url);
            }
            catch (final Exception e) {
                errorCallback.accept(e);
            }
        })).cancelCallback(() -> errorCallback.accept(null)).build().displayInOverlay();
    }
    
    private String upload(final Path file) throws Exception {
        final ScreenshotMeta meta = new ScreenshotMeta(file);
        if (!meta.hasInsight()) {
            throw new ScreenshotException("Not an insight screenshot");
        }
        final ScreenshotInsight insight = meta.getInsight();
        if (insight == null) {
            throw new ScreenshotException("Insight is corrupted");
        }
        final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            throw new ScreenshotException("Not connected to LabyConnect");
        }
        final TokenStorage.Token token = session.tokenStorage().getToken(TokenStorage.Purpose.CLIENT, session.self().getUniqueId());
        if (token == null || token.isExpired()) {
            throw new ScreenshotException("Access token expired");
        }
        final HttpURLConnection connection = (HttpURLConnection)new URL("https://laby.net/api/v3/post").openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Client " + token.getToken());
        try (final DataOutputStream dos = new DataOutputStream(connection.getOutputStream())) {
            try {
                final ImageCodec codec = new ImageCodec(file);
                codec.setImagePostProcessor(image -> ScreenshotUtil.maxSize(image, 1920, 1080));
                final String format = ImageCodec.getAvailableFormat(InsightUploader.FORMATS);
                final byte[] imageBytes = codec.compile(format);
                dos.writeInt(imageBytes.length);
                dos.write(imageBytes);
                final JsonObject object = insight.toJsonObject();
                object.addProperty("format", format);
                final String json = object.toString();
                final int length = json.length();
                dos.writeInt(length);
                for (int i = 0; i < length; ++i) {
                    final char character = json.charAt(i);
                    dos.writeByte((character > '\u007f') ? 63 : ((byte)character));
                }
            }
            catch (final Throwable e) {
                throw new ScreenshotException("Failed to encode image: " + e.getMessage());
            }
        }
        final StringBuilder jsonResponse = new StringBuilder();
        if (connection.getResponseCode() / 100 != 2) {
            try (final InputStream inputStream = connection.getErrorStream()) {
                int read;
                while ((read = inputStream.read()) != -1) {
                    jsonResponse.append((char)read);
                }
            }
            try {
                final String json2 = jsonResponse.toString();
                final JsonObject response = (JsonObject)GsonUtil.DEFAULT_GSON.fromJson(json2, (Class)JsonObject.class);
                if (response.has("error")) {
                    throw new ScreenshotException(response.get("error").getAsString());
                }
                throw new ScreenshotException(json2);
            }
            catch (final JsonSyntaxException ignored) {
                InsightUploader.LOGGER.info("Failed to parse error response: " + String.valueOf(jsonResponse), new Object[0]);
                throw new ScreenshotException("Unknown error", connection.getResponseCode());
            }
        }
        try (final InputStream inputStream = connection.getInputStream()) {
            int j;
            while (inputStream != null && (j = inputStream.read()) != -1) {
                jsonResponse.append((char)j);
            }
        }
        try {
            final JsonObject response2 = (JsonObject)GsonUtil.DEFAULT_GSON.fromJson(jsonResponse.toString(), (Class)JsonObject.class);
            if (!response2.has("url")) {
                throw new ScreenshotException("No url given in response");
            }
            return response2.get("url").getAsString();
        }
        catch (final Exception e2) {
            throw new ScreenshotException("Invalid response: " + String.valueOf(jsonResponse));
        }
    }
    
    static {
        LOGGER = Logging.create(InsightUploader.class);
        FORMATS = new String[] { "avif", "webp", "png", "jpeg" };
    }
}
