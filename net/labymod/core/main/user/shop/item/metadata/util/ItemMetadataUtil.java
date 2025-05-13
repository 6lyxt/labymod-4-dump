// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.metadata.util;

import net.labymod.core.main.user.shop.item.metadata.serialization.DefaultItemMetadataSerializer;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;
import net.labymod.core.main.user.shop.item.model.TextureDetails;
import java.util.Objects;
import net.labymod.core.main.user.shop.item.texture.ItemTexture;
import net.labymod.core.main.user.GameUserItemStorage;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.AbstractItem;
import java.util.List;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.shop.item.ItemService;
import java.util.Collection;
import net.labymod.api.util.CollectionHelper;
import net.labymod.core.main.user.shop.item.model.type.TextureType;
import java.util.Comparator;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.GameUserService;
import java.util.UUID;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.main.user.shop.item.metadata.serialization.ItemMetadataSerializer;
import net.labymod.api.util.logging.Logging;

public final class ItemMetadataUtil
{
    private static final Logging LOGGER;
    private static final ItemMetadataSerializer SERIALIZER;
    
    public static void updateGameUser(final Cosmetic cosmetic, @Nullable final ItemTextureListener listener) {
        final LabyConnect labyConnect = Laby.references().labyConnect();
        final LabyConnectSession session = labyConnect.getSession();
        if (session == null) {
            return;
        }
        final UUID uniqueId = session.self().getUniqueId();
        final TokenStorage tokenStorage = session.tokenStorage();
        if (!tokenStorage.hasValidToken(TokenStorage.Purpose.CLIENT, uniqueId)) {
            return;
        }
        updateGameUser(uniqueId, cosmetic, listener);
    }
    
    public static void updateGameUser(final UUID uniqueId, final Cosmetic cosmetic, @Nullable final ItemTextureListener listener) {
        final GameUserService gameUserService = Laby.references().gameUserService();
        final GameUser gameUser = gameUserService.gameUser(uniqueId);
        updateGameUser(cosmetic, gameUser, listener);
    }
    
    public static void updateGameUser(final Cosmetic cosmetic, final GameUser user, @Nullable final ItemTextureListener listener) {
        if (user instanceof final DefaultGameUser defaultGameUser) {
            final GameUserService gameUserService = Laby.references().gameUserService();
            final ItemService itemService = ((DefaultGameUserService)gameUserService).itemService();
            final GameUserData userData = defaultGameUser.getUserData();
            final boolean hasItem = userData.hasItem(cosmetic.getItemId());
            final List<GameUserItem> items = userData.getItems();
            if (cosmetic.isEnabled()) {
                if (hasItem) {
                    updateMetadata(cosmetic, itemService, defaultGameUser);
                }
                else {
                    AbstractItem item = itemService.getItemById(cosmetic.getItemId());
                    if (item == null) {
                        ItemMetadataUtil.LOGGER.error("No Cosmetic with ID \"{}\" could be found.", cosmetic.getItemId());
                        return;
                    }
                    item = item.copy();
                    final ItemMetadata itemMetadata = new ItemMetadata(item.itemDetails());
                    try {
                        serialize(itemMetadata, cosmetic.getData());
                    }
                    catch (final MetadataException exception) {
                        final ItemDetails details = item.itemDetails();
                        ItemMetadataUtil.LOGGER.error("Item options for \"{}\" could not be serialized because \"{}\"", details.getIdentifier() + "/" + details.getName(), exception.getMessage());
                    }
                    items.add(new GameUserItem(item, itemMetadata));
                    items.sort(Comparator.comparingInt(value -> value.item().getIdentifier()));
                    final GameUserItemStorage storage = defaultGameUser.getUserItemStorage();
                    storage.prepare(defaultGameUser, userData);
                    final ItemTexture texture = defaultGameUser.getUserItemStorage().getTexture(item);
                    if (texture != null) {
                        if (itemMetadata.getDetails().getTextureType() == TextureType.USER_BOUND) {
                            texture.invalidate();
                        }
                        texture.setItemTextureListener(listener);
                    }
                }
            }
            else {
                final AbstractItem item;
                CollectionHelper.removeIf(items, item -> {
                    if (item.item().getIdentifier() == cosmetic.getItemId()) {
                        final ItemTexture texture2 = defaultGameUser.getUserItemStorage().getTexture(item.item());
                        if (texture2 != null) {
                            texture2.invalidate();
                        }
                        return true;
                    }
                    else {
                        return false;
                    }
                });
            }
        }
    }
    
    public static void serialize(final ItemMetadata metadata, final String[] options) throws MetadataException {
        serialize(metadata, metadata.getDetails(), options);
    }
    
    public static void serialize(final ItemMetadata metadata, final ItemDetails details, String[] options) throws MetadataException {
        options = convertOptions(details.getIdentifier(), options);
        ItemMetadataUtil.SERIALIZER.serialize(metadata, details, options);
    }
    
    public static List<Object> deserialize(final ItemMetadata metadata) throws MetadataException {
        return ItemMetadataUtil.SERIALIZER.deserialize(metadata);
    }
    
    private static void updateMetadata(final Cosmetic cosmetic, final ItemService itemService, final DefaultGameUser gameUser) {
        final GameUserItem userItem = gameUser.getUserData().getItem(cosmetic.getItemId());
        if (userItem != null) {
            final AbstractItem item = itemService.getItemById(cosmetic.getItemId());
            final ItemDetails details = item.itemDetails();
            try {
                final ItemMetadata itemMetadata = userItem.itemMetadata();
                final TextureDetails previousTexture = itemMetadata.getTextureDetails();
                itemMetadata.invalidate();
                serialize(itemMetadata, details, cosmetic.getData());
                item.itemMetadata(itemMetadata);
                final TextureDetails newTexture = itemMetadata.getTextureDetails();
                invalidateItemTexture(gameUser, item, () -> itemMetadata.getDetails().getTextureType() != TextureType.USER_BOUND && Objects.equals(previousTexture, newTexture));
            }
            catch (final Exception exception) {
                ItemMetadataUtil.LOGGER.error("Item options for \"{}\" could not be serialized because \"{}\"", details.getIdentifier() + "/" + details.getName(), exception.getMessage());
            }
        }
    }
    
    private static void invalidateItemTexture(final DefaultGameUser gameUser, final AbstractItem item, final BooleanSupplier condition) {
        if (condition.getAsBoolean()) {
            return;
        }
        final ItemTexture texture = gameUser.getUserItemStorage().getTexture(item);
        if (texture == null) {
            return;
        }
        texture.invalidate();
    }
    
    private static String[] convertOptions(final int identifier, final String[] options) {
        if (identifier != 36 && identifier != 32) {
            return options;
        }
        if (options.length < 4) {
            return options;
        }
        final String xOption = options[0];
        final String yOption = options[1];
        final String widthOption = options[2];
        final String heightOption = options[3];
        final List<String> mappedOptions = new ArrayList<String>();
        mappedOptions.add(xOption + ";" + yOption + ";" + widthOption + ";" + heightOption);
        for (int i = 4; i < options.length; ++i) {
            mappedOptions.add(options[i]);
        }
        return mappedOptions.toArray(new String[0]);
    }
    
    static {
        LOGGER = Logging.create(ItemMetadataUtil.class);
        SERIALIZER = new DefaultItemMetadataSerializer();
    }
}
