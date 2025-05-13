// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.converter;

import java.nio.file.Path;
import net.labymod.core.main.account.AccountManagerController;
import net.labymod.core.main.LabyMod;
import java.nio.file.OpenOption;
import java.nio.charset.StandardCharsets;
import com.google.gson.JsonElement;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import net.labymod.api.Constants;
import com.google.gson.JsonObject;
import net.labymod.api.configuration.converter.LegacyConverter;

public class LegacyAccountConverter extends LegacyConverter<JsonObject>
{
    public LegacyAccountConverter() {
        super("accounts.json", JsonObject.class);
    }
    
    @Override
    protected void convert(final JsonObject jsonObject) throws Exception {
        final Path file = Constants.Files.ACCOUNTS;
        Files.createDirectories(file.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
        Files.write(file, this.gson.toJson((JsonElement)jsonObject).getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
        LabyMod.getInstance().getAccountManager().load(AccountManagerController.AZURE);
    }
    
    @Override
    public boolean hasStuffToConvert() {
        return this.getValue() != null;
    }
}
