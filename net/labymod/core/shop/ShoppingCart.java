// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop;

import org.jetbrains.annotations.NotNull;
import java.util.HashSet;
import java.util.Set;
import net.labymod.api.util.io.web.result.Result;
import java.util.Locale;
import java.util.function.Supplier;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.BuildData;
import net.labymod.api.util.io.web.request.AbstractRequest;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.core.shop.models.cart.CreateCartResponse;
import net.labymod.core.shop.models.cart.AddToCartResponse;
import net.labymod.api.util.io.web.request.FormData;
import com.google.gson.JsonObject;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.core.main.user.GameUserItem;
import java.util.Iterator;
import net.labymod.core.shop.models.ShopItem;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.core.shop.event.ShopCartUpdateEvent;
import java.util.UUID;
import net.labymod.core.shop.models.cart.PromoCodeResponse;

public class ShoppingCart
{
    private final CartStorage storage;
    private final ShopController shopController;
    private PromoCodeResponse.Code promoCode;
    private UUID cartId;
    private UUID userId;
    
    public ShoppingCart(final ShopController shopController) {
        this.storage = new CartStorage();
        this.shopController = shopController;
    }
    
    public int size() {
        return this.storage.size();
    }
    
    public boolean isEmpty() {
        return this.storage.isEmpty();
    }
    
    public boolean clear() {
        this.cartId = null;
        this.userId = null;
        this.promoCode = null;
        if (this.isEmpty()) {
            return false;
        }
        this.storage.clear();
        Laby.fireEvent(ShopCartUpdateEvent.INSTANCE);
        return true;
    }
    
    public void forEachShopItem(final Consumer<ShopItem> consumer) {
        for (final CartItem item : this.storage) {
            consumer.accept(item.item);
        }
    }
    
    public void forEachGameItem(final Consumer<GameUserItem> consumer) {
        this.forEachShopItem(shopItem -> {
            final GameUserItem gameUserItem = shopItem.asGameUserItem();
            if (gameUserItem != null) {
                consumer.accept(gameUserItem);
            }
        });
    }
    
    public boolean has(final ShopItem item) {
        final int id = item.getId();
        for (final CartItem cartItem : this.storage) {
            if (cartItem.item.getId() == id) {
                return true;
            }
        }
        return false;
    }
    
    public PromoCodeResponse.Code getPromoCode() {
        return this.promoCode;
    }
    
    public void getInfo(final ResultCallback<JsonObject> callback) {
    }
    
    public void applyPromoCode(final String code, final ResultCallback<PromoCodeResponse.Code> callback) {
        this.cartRequest(PromoCodeResponse.class, "add-promocode", callback, response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
            }
            else {
                final PromoCodeResponse promoCodeResponse = (PromoCodeResponse)response.get();
                final PromoCodeResponse.Code promoCode = promoCodeResponse.getCode();
                if (promoCode == null) {
                    callback.acceptException(new ShoppingCartKeywordException(promoCodeResponse.getMessage()));
                }
                else {
                    if (promoCode instanceof final PromoCodeResponse.RefCode refCode) {
                        refCode.setCode(code);
                    }
                    this.promoCode = promoCode;
                    Laby.fireEvent(ShopCartUpdateEvent.INSTANCE);
                    callback.acceptRaw(promoCode);
                }
            }
        }, FormData.of("code", code));
    }
    
    public void removePromoCode(final ResultCallback<JsonObject> callback) {
        this.cartRequest(JsonObject.class, "remove-promocode", callback, response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
            }
            else {
                this.promoCode = null;
                Laby.fireEvent(ShopCartUpdateEvent.INSTANCE);
                callback.acceptRaw(response.get());
            }
        }, new FormData[0]);
    }
    
    public void removeItem(final ShopItem item, final ResultCallback<CartItem> callback) {
        final int id = item.getId();
        for (final CartItem cartItem : this.storage) {
            if (cartItem.item.getId() == id) {
                this.removeItem(cartItem, callback);
                break;
            }
        }
    }
    
    public void removeItem(final CartItem item, final ResultCallback<CartItem> callback) {
        this.cartRequest(JsonObject.class, "remove-item", callback, response -> {
            this.storage.removeItem(item);
            Laby.fireEvent(ShopCartUpdateEvent.INSTANCE);
            callback.acceptRaw(item);
        }, FormData.of("cart_item_id", item.cartId));
    }
    
    public void addItem(final ShopItem item, final ResultCallback<CartItem> callback) {
        this.addItem(new CartItem(item, CartDuration.LIFETIME), callback);
    }
    
    public void addItem(final CartItem item, final ResultCallback<CartItem> callback) {
        this.cartRequest(AddToCartResponse.class, "add-item", callback, response -> {
            final AddToCartResponse addToCartResponse = (AddToCartResponse)response.get();
            if (!addToCartResponse.isSuccessful() || addToCartResponse.getItem() == null) {
                callback.acceptException(new IllegalStateException("Failed to add item to cart"));
            }
            else {
                final String cartItemId = addToCartResponse.getItem().getCartItemId();
                if (cartItemId == null) {
                    callback.acceptException(new IllegalStateException("Failed to add item to cart"));
                }
                else {
                    item.cartId = Integer.parseInt(cartItemId);
                    this.storage.addItem(item);
                    Laby.fireEvent(ShopCartUpdateEvent.INSTANCE);
                    callback.acceptRaw(item);
                }
            }
        }, () -> {
            if (item.receiver == null) {
                item.receiver = this.userId;
            }
            return new FormData[] { FormData.of("item_receiver", item.receiver), FormData.of("shop_item_id", item.item.getId()), FormData.of("item_value", item.duration.identifier) };
        });
    }
    
    public void createCart(final ResultCallback<UUID> callback) {
        if (this.cartId != null) {
            callback.acceptRaw(this.cartId);
            return;
        }
        final Request<CreateCartResponse> request = this.createRequest(CreateCartResponse.class, callback);
        if (request == null) {
            return;
        }
        request.url("https://www.labymod.net/api/shop/create-cart", new Object[0]).connectTimeout(5000).readTimeout(5000).execute(response -> {
            if (response.hasException()) {
                callback.acceptException(response.exception());
            }
            else {
                callback.acceptRaw(this.cartId = ((CreateCartResponse)response.get()).getCartId());
            }
        });
    }
    
    private <T> Request<T> createRequest(final Class<T> clazz, final ResultCallback<?> callback) {
        final LabyConnectSession session = Laby.references().labyConnect().getSession();
        if (session == null) {
            callback.acceptException(new IllegalStateException("LabyConnect is not connected"));
            return null;
        }
        final UUID uniqueId = (this.userId == null) ? session.self().getUniqueId() : this.userId;
        final TokenStorage tokenStorage = session.tokenStorage();
        if (!tokenStorage.hasValidToken(TokenStorage.Purpose.CLIENT, uniqueId)) {
            callback.acceptException(new IllegalAccessException("No valid token"));
            return null;
        }
        if (this.userId == null) {
            this.userId = uniqueId;
        }
        final String token = tokenStorage.getToken(TokenStorage.Purpose.CLIENT, uniqueId).getToken();
        final GsonRequest<T> request = (GsonRequest<T>)Request.ofGson(clazz).method(Request.Method.POST).connectTimeout(5000).readTimeout(5000).authorization("Client", token).userAgent("LabyMod " + BuildData.getVersion() + " " + BuildData.releaseType() + "/" + BuildData.commitReference());
        if (ThreadSafe.isRenderThread()) {
            request.async();
        }
        return (Request<T>)request;
    }
    
    private <T> void cartRequest(final Class<T> responseClass, final String action, final ResultCallback<?> callback, final Consumer<Response<T>> response, final Supplier<FormData[]> formDataSupplier) {
        this.createCart(result -> {
            if (result.hasException()) {
                callback.acceptException(result.exception());
            }
            else {
                final Request<Object> request = this.createRequest((Class<Object>)responseClass, callback);
                if (request != null) {
                    final FormData[] formData = formDataSupplier.get();
                    final FormData[] actualFormData = new FormData[formData.length + 1];
                    actualFormData[0] = FormData.builder().name("action").value(action).build();
                    System.arraycopy(formData, 0, actualFormData, 1, formData.length);
                    request.url(String.format(Locale.ROOT, "https://www.labymod.net/api/shop/cart?cart_uuid=%s", result.get()), new Object[0]).form(actualFormData).connectTimeout(5000).readTimeout(5000).execute(requestResponse -> {
                        if (requestResponse.hasException()) {
                            callback.acceptException(requestResponse.exception());
                        }
                        else {
                            response.accept(requestResponse);
                        }
                    });
                }
            }
        });
    }
    
    private <T> void cartRequest(final Class<T> responseClass, final String action, final ResultCallback<?> callback, final Consumer<Response<T>> response, final FormData... formData) {
        this.cartRequest(responseClass, action, callback, response, () -> formData);
    }
    
    public CartStorage storage() {
        return this.storage;
    }
    
    public UUID getCartId() {
        return this.cartId;
    }
    
    public enum CartDuration
    {
        LIFETIME(0), 
        ONE_MONTH(1), 
        THREE_MONTHS(3), 
        SIX_MONTHS(6), 
        TWELVE_MONTHS(12);
        
        private final int identifier;
        
        private CartDuration(final int identifier) {
            this.identifier = identifier;
        }
    }
    
    public static class CartItem
    {
        private final ShopItem item;
        private final CartDuration duration;
        private UUID receiver;
        private int cartId;
        
        public CartItem(final ShopItem item, final CartDuration duration, final UUID receiver) {
            this.item = item;
            this.duration = duration;
            this.receiver = receiver;
        }
        
        public CartItem(final ShopItem item, final CartDuration duration) {
            this.item = item;
            this.duration = duration;
        }
    }
    
    public static class CartStorage implements Iterable<CartItem>
    {
        private final Set<CartItem> items;
        private ChangeListener listener;
        
        public CartStorage() {
            this.items = new HashSet<CartItem>();
        }
        
        public void addItem(final CartItem item) {
            if (item == null) {
                return;
            }
            this.items.add(item);
            this.onChange(ChangeListener.ChangeType.ADD, item.item.getItemId());
        }
        
        public void removeItem(final CartItem item) {
            if (item == null) {
                return;
            }
            this.items.remove(item);
            this.onChange(ChangeListener.ChangeType.REMOVE, item.item.getItemId());
        }
        
        public void clear() {
            this.items.clear();
            this.onChange(ChangeListener.ChangeType.CLEAR, -1);
        }
        
        public int size() {
            return this.items.size();
        }
        
        public boolean isEmpty() {
            return this.items.isEmpty();
        }
        
        @NotNull
        @Override
        public Iterator<CartItem> iterator() {
            return this.items.iterator();
        }
        
        public void setChangeListener(final ChangeListener listener) {
            this.listener = listener;
        }
        
        private void onChange(final ChangeListener.ChangeType type, final int cosmeticId) {
            if (this.listener == null) {
                return;
            }
            this.listener.onChange(type, cosmeticId);
        }
        
        public interface ChangeListener
        {
            void onChange(final ChangeType p0, final int p1);
            
            public enum ChangeType
            {
                ADD, 
                REMOVE, 
                CLEAR;
            }
        }
    }
    
    public static class ShoppingCartKeywordException extends RuntimeException
    {
        public ShoppingCartKeywordException(final String message) {
            super(message);
        }
    }
}
