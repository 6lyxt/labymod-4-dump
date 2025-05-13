// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.localization;

import com.google.gson.JsonParser;
import net.labymod.api.util.io.web.exception.WebRequestException;
import java.io.IOException;
import java.nio.file.OpenOption;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import com.google.gson.JsonObject;
import java.nio.file.Path;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.WebResponse;

public class LocaleRemoteWebResponse implements WebResponse<JsonElement>
{
    private final Path translationPath;
    private JsonObject translationObject;
    
    public LocaleRemoteWebResponse(final Path translationPath) {
        this.translationPath = translationPath;
    }
    
    @Override
    public void success(final JsonElement result) {
        if (!result.isJsonObject()) {
            return;
        }
        this.translationObject = result.getAsJsonObject();
        try {
            Files.createFile(this.translationPath, (FileAttribute<?>[])new FileAttribute[0]);
            Files.write(this.translationPath, this.translationObject.toString().getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void failed(final WebRequestException exception) {
        try {
            this.translationObject = new JsonParser().parse(new String(Files.readAllBytes(this.translationPath))).getAsJsonObject();
        }
        catch (final IOException ioException) {
            ioException.printStackTrace();
        }
    }
    
    public JsonObject getTranslationObject() {
        return this.translationObject;
    }
}
