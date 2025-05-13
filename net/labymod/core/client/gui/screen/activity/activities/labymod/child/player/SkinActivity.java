// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.I18n;
import net.labymod.api.client.session.MinecraftServices;
import java.util.concurrent.TimeUnit;
import net.labymod.core.main.LabyMod;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.client.session.model.MojangTextureChangedResponse;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.labynet.models.textures.TextureResult;
import net.labymod.core.labynet.DefaultLabyNetController;
import java.util.UUID;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.session.Session;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.entity.player.OptiFinePlayer;
import java.math.BigInteger;
import java.util.Random;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin.SkinLayersWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FoldingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.thirdparty.optifine.OptiFine;
import net.labymod.api.Textures;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.SettingLikeWidget;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.client.session.model.MojangTexture;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.Laby;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin.LastUsedSkinsContainerWidget;
import net.labymod.core.labyconnect.session.ApplyTextureController;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;

@AutoActivity
@Link("activity/player/skin.lss")
public class SkinActivity extends PlayerActivity.Child
{
    private static final MojangTextureEntry LOADING_CAPE;
    private static final ApplyTextureController APPLY_TEXTURE_CONTROLLER;
    private static final String IDENTIFIER = "skin";
    private static final long OPTIFINE_RELOAD_CAPE_DELAY;
    private final LastUsedSkinsContainerWidget lastUsedSkinsWidget;
    private final DropdownWidget<MojangTextureEntry> capeDropdownWidget;
    private SkinBrowseActivity skinBrowseActivity;
    
    public SkinActivity(final PlayerActivity playerActivity, final String translationKeyPrefix) {
        super(playerActivity, translationKeyPrefix + "skin.", "skin");
        this.lastUsedSkinsWidget = new LastUsedSkinsContainerWidget(this);
        final ResourceLocation loading = Skin.LOADING;
        (this.capeDropdownWidget = new DropdownWidget<MojangTextureEntry>()).setChangeListener(texture -> {
            if (texture != SkinActivity.LOADING_CAPE) {
                final String id = (texture == MojangTextureEntry.NONE) ? null : texture.texture().getId().toString();
                SkinActivity.APPLY_TEXTURE_CONTROLLER.setCapeAsync(id, response -> {
                    final MojangTexture activeCape = response.getActiveCape();
                    final MojangTextureService service = Laby.references().mojangTextureService();
                    final LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
                    if (session != null) {
                        session.sendTextureUpdated(response);
                    }
                    ThreadSafe.executeOnRenderThread(() -> service.applyTexture(this.uniqueId, MojangTextureType.CAPE, (activeCape == null) ? null : activeCape.getUrl()));
                });
            }
        });
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DropdownWidget<SkinType> skinTypeDropdown = new DropdownWidget<SkinType>();
        final VerticalListWidget<Widget> container = new VerticalListWidget<Widget>();
        container.addId("list");
        final HorizontalListWidget selectSkinContainer = new HorizontalListWidget();
        ((AbstractWidget<Widget>)selectSkinContainer).addId("select-skin-container");
        final ButtonWidget browseComputerButton = ButtonWidget.i18n(this.translationKeyPrefix + "browseLocalButton");
        browseComputerButton.setPressable(() -> {
            final SkinType selected = skinTypeDropdown.getSelected();
            SkinActivity.APPLY_TEXTURE_CONTROLLER.browseSkinFile(((selected == null) ? SkinType.AUTOMATIC : selected).skinVariant);
            return;
        });
        selectSkinContainer.addEntry(browseComputerButton);
        selectSkinContainer.addEntry(ComponentWidget.i18n(this.translationKeyPrefix + "browseButtonSplitter")).addId("button-splitter");
        final ButtonWidget browseLabyButton = ButtonWidget.i18n(this.translationKeyPrefix + "browseOnlineButton");
        ((AbstractWidget<Widget>)browseLabyButton).addId("accent-button");
        ((AbstractWidget<Widget>)browseLabyButton).addId("browse-online-button");
        browseLabyButton.removeId("primary-button");
        browseLabyButton.setPressable(() -> this.playerActivity.displayScreen(this.skinBrowseActivity()));
        selectSkinContainer.addEntry(browseLabyButton);
        container.addChild(selectSkinContainer);
        final String skinTypeTranslationKey = this.translationKeyPrefix + "settings.skinType.";
        skinTypeDropdown.addAll(SkinType.VALUES);
        skinTypeDropdown.setSelected(SkinType.AUTOMATIC);
        skinTypeDropdown.setTranslationKeyPrefix(skinTypeTranslationKey + "entries");
        container.addChild(new SettingLikeWidget(Component.translatable(skinTypeTranslationKey + "name", new Component[0]), skinTypeDropdown).addId("setting"));
        container.addChild(new SettingLikeWidget(Component.translatable(this.translationKeyPrefix + "settings.cape.name", new Component[0]), this.capeDropdownWidget).addId("setting"));
        if (MinecraftVersions.V1_9.orNewer()) {
            final MinecraftOptions options = this.labyAPI.minecraft().options();
            final String mainHandTranslationKey = this.translationKeyPrefix + "settings.mainHand.";
            final DropdownWidget<MainHand> mainHandDropdown = new DropdownWidget<MainHand>();
            mainHandDropdown.addAll(MainHand.VALUES);
            mainHandDropdown.setSelected(options.mainHand());
            mainHandDropdown.setTranslationKeyPrefix(mainHandTranslationKey + "entries");
            mainHandDropdown.setChangeListener(mainHand -> {
                options.setMainHand(mainHand);
                options.sendOptionsToServer();
                options.save();
                this.playerActivity.modelWidget().update();
                return;
            });
            container.addChild(new SettingLikeWidget(Textures.SpriteCustomization.HAND, Component.translatable(mainHandTranslationKey + "name", new Component[0]), mainHandDropdown).addId("setting"));
        }
        if (OptiFine.isPresent()) {
            this.initializeOptiFineFeatures(container);
        }
        final DivWidget displayNameWrapper = new DivWidget();
        displayNameWrapper.addId("display-name-wrapper");
        final ComponentWidget displayName = ComponentWidget.i18n(this.translationKeyPrefix + "settings.layers.name");
        displayName.addId("display-name");
        ((AbstractWidget<ComponentWidget>)displayNameWrapper).addChild(displayName);
        final FoldingWidget foldingWidget = new FoldingWidget(displayNameWrapper, new SkinLayersWidget(() -> this.playerActivity.modelWidget().update())).addId("setting");
        foldingWidget.addChild(new DivWidget().addId("status-indicator"));
        container.addChild(foldingWidget);
        container.addChild(this.lastUsedSkinsWidget);
        this.lastUsedSkinsWidget.setVisible(this.lastUsedSkinsWidget.hasValidTextures());
        ((Document)this.document).addChild(new ScrollWidget(container).addId("player-scroll"));
    }
    
    private void initializeOptiFineFeatures(final VerticalListWidget<Widget> container) {
        final Session session = this.labyAPI.minecraft().sessionAccessor().getSession();
        final ButtonWidget capeEditorButton = ButtonWidget.i18n(this.translationKeyPrefix + "settings.ofCapeEditor.text");
        capeEditorButton.setEnabled(session != null && session.isPremium());
        capeEditorButton.setPressable(() -> {
            final Session currentSession = this.labyAPI.minecraft().sessionAccessor().getSession();
            if (currentSession == null || !currentSession.isPremium()) {
                return;
            }
            else {
                capeEditorButton.setEnabled(false);
                final Random r1 = new Random();
                new Random(System.identityHashCode(new Object()));
                final Random random;
                final Random r2 = random;
                final BigInteger random1Bi = new BigInteger(128, r1);
                final BigInteger random2Bi = new BigInteger(128, r2);
                final BigInteger serverBi = random1Bi.xor(random2Bi);
                final String serverId = serverBi.toString(16);
                Laby.references().minecraftAuthenticator().joinServer(currentSession, serverId).exceptionally(throwable -> false).thenAccept(valid -> ThreadSafe.executeOnRenderThread(() -> this.openCapeEditor(session, serverId, capeEditorButton, valid)));
                return;
            }
        });
        container.addChild(new SettingLikeWidget(Component.translatable(this.translationKeyPrefix + "settings.ofCapeEditor.name", new Component[0]), capeEditorButton).addId("setting"));
        final ButtonWidget reloadCapeButton = ButtonWidget.i18n(this.translationKeyPrefix + "settings.ofCapeReload.text");
        final ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
        reloadCapeButton.setEnabled(player != null && session != null && session.isPremium());
        reloadCapeButton.setPressable(() -> {
            if (player instanceof final OptiFinePlayer optiFinePlayer) {
                optiFinePlayer.bridge$optifine$setReloadCapeTime(TimeUtil.getCurrentTimeMillis() + SkinActivity.OPTIFINE_RELOAD_CAPE_DELAY);
            }
            return;
        });
        container.addChild(new SettingLikeWidget(Component.translatable(this.translationKeyPrefix + "settings.ofCapeReload.name", new Component[0]), reloadCapeButton).addId("setting"));
    }
    
    @Override
    protected void onSessionUpdate(final PlayerActivity.UpdateContext context, final UUID uniqueId) {
        final LabyNetController labyNetController = Laby.references().labyNetController();
        ((DefaultLabyNetController)labyNetController).loadTexturesFromUser(TextureResult.Type.SKIN, uniqueId, result -> {
            if (!result.isPresent()) {
                this.lastUsedSkinsWidget.setVisible(false);
                return;
            }
            else {
                this.labyAPI.minecraft().executeOnRenderThread(() -> this.lastUsedSkinsWidget.setTextureResult((TextureResult)result.get()));
                return;
            }
        });
        this.capeDropdownWidget.clear();
        this.capeDropdownWidget.add(SkinActivity.LOADING_CAPE);
        this.capeDropdownWidget.setSelected(SkinActivity.LOADING_CAPE);
        SkinActivity.APPLY_TEXTURE_CONTROLLER.getProfileAsync(response -> {
            if (response != null) {
                this.capeDropdownWidget.clear();
                final DropdownWidget<MojangTextureEntry> capeDropdownWidget = this.capeDropdownWidget;
                MojangTextureEntry selected = MojangTextureEntry.NONE;
                final MojangTextureEntry entry2;
                capeDropdownWidget.add(entry2);
                final MojangTexture[] capes = response.getCapes();
                if (capes != null) {
                    final MojangTexture[] array;
                    int i = 0;
                    for (int length = array.length; i < length; ++i) {
                        final MojangTexture cape = array[i];
                        if (cape != null) {
                            final MojangTextureEntry entry = new MojangTextureEntry(cape);
                            this.capeDropdownWidget.add(entry);
                            if (cape.isActive()) {
                                selected = entry;
                            }
                        }
                    }
                }
                this.capeDropdownWidget.setSelected(selected, false);
            }
        });
    }
    
    private SkinBrowseActivity skinBrowseActivity() {
        if (this.skinBrowseActivity == null) {
            this.skinBrowseActivity = new SkinBrowseActivity(this);
        }
        return this.skinBrowseActivity;
    }
    
    private void openCapeEditor(final Session session, final String serverId, final ButtonWidget capeEditorButton, final boolean valid) {
        capeEditorButton.setEnabled(true);
        if (!valid) {
            return;
        }
        final String username = session.getUsername();
        final String uniqueId = session.getUniqueId().toString().replace("-", "");
        this.labyAPI.minecraft().chatExecutor().openUrl("https://optifine.net/capeChange?u=" + uniqueId + "&n=" + username + "&s=" + serverId);
    }
    
    public void setModelTexture(final Skin skin) {
        this.playerActivity.setSkinTexture(skin);
    }
    
    static {
        LOADING_CAPE = new MojangTextureEntry(null, "Loading...");
        APPLY_TEXTURE_CONTROLLER = LabyMod.references().applyTextureController();
        OPTIFINE_RELOAD_CAPE_DELAY = TimeUnit.SECONDS.toMillis(15L);
    }
    
    public enum SkinType
    {
        AUTOMATIC((MinecraftServices.SkinVariant)null), 
        CLASSIC(MinecraftServices.SkinVariant.CLASSIC), 
        SLIM(MinecraftServices.SkinVariant.SLIM);
        
        public static final SkinType[] VALUES;
        private final MinecraftServices.SkinVariant skinVariant;
        
        private SkinType(final MinecraftServices.SkinVariant skinVariant) {
            this.skinVariant = skinVariant;
        }
        
        public MinecraftServices.SkinVariant skinVariant() {
            return this.skinVariant;
        }
        
        static {
            VALUES = values();
        }
    }
    
    public static class MojangTextureEntry
    {
        public static final MojangTextureEntry NONE;
        private final MojangTexture texture;
        private final String name;
        
        public MojangTextureEntry(final MojangTexture texture) {
            this(texture, texture.getAlias());
        }
        
        public MojangTextureEntry(final MojangTexture texture, final String name) {
            this.texture = texture;
            this.name = name;
        }
        
        public MojangTexture texture() {
            return this.texture;
        }
        
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.getName();
        }
        
        static {
            NONE = new MojangTextureEntry(null, I18n.translate("labymod.activity.customization.textures.cape.noCape", new Object[0]));
        }
    }
}
