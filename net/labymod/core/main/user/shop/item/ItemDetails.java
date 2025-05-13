// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item;

import net.labymod.core.main.user.shop.item.model.PetData;
import net.labymod.core.main.user.shop.item.model.AttachmentPosition;
import net.labymod.core.main.user.shop.item.model.MirrorType;
import net.labymod.core.main.user.shop.item.model.type.TextureType;
import net.labymod.core.main.user.shop.item.model.AttachmentPoint;
import net.labymod.core.main.user.shop.item.model.type.MoveType;
import net.labymod.core.main.user.shop.item.model.type.ItemType;
import net.labymod.core.main.user.shop.item.texture.Ratio;
import com.google.gson.annotations.SerializedName;

public class ItemDetails
{
    @SerializedName("id")
    private int identifier;
    private String name;
    private String[] options;
    private double scale;
    private boolean draft;
    @SerializedName("default_data")
    private String[] defaultData;
    @SerializedName("option_list")
    private String[] optionList;
    @SerializedName("frame_aspect_ratio")
    private Ratio ratio;
    @SerializedName("frame_animation_delay")
    private long animationDelay;
    @SerializedName("type")
    private ItemType type;
    @SerializedName("move_type")
    private MoveType moveType;
    @SerializedName("attached_to")
    private AttachmentPoint attachmentPoint;
    @SerializedName("texture_type")
    private TextureType textureType;
    @SerializedName("texture_directory")
    private String textureDirectory;
    @SerializedName("mirror")
    private boolean mirror;
    @SerializedName("mirror_type")
    private MirrorType mirrorType;
    @SerializedName("nametag_offset")
    private float nameTagOffset;
    @SerializedName("hide_cape")
    private boolean hideCape;
    @SerializedName("hidden_while_wearing_elytra")
    private boolean hiddenWhileWearingElytra;
    @SerializedName("category")
    private String category;
    @SerializedName("position")
    private AttachmentPosition position;
    @SerializedName(value = "petData", alternate = { "pet_data" })
    private PetData petData;
    
    public ItemDetails() {
        this.scale = 1.0;
        this.draft = true;
        this.ratio = null;
        this.animationDelay = 50L;
        this.type = ItemType.COSMETIC;
        this.moveType = MoveType.BOTH;
        this.attachmentPoint = AttachmentPoint.BODY;
        this.textureType = TextureType.TYPE_BOUND;
        this.mirrorType = MirrorType.MIRROR;
        this.petData = new PetData();
    }
    
    public int getIdentifier() {
        return this.identifier;
    }
    
    public void setIdentifier(final int identifier) {
        this.identifier = identifier;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String[] getOptions() {
        return this.options;
    }
    
    public void setOptions(final String[] options) {
        this.options = options;
    }
    
    public String[] getDefaultData() {
        return this.defaultData;
    }
    
    public void setDefaultData(final String[] defaultData) {
        this.defaultData = defaultData;
    }
    
    public double getScale() {
        return this.scale;
    }
    
    public void setScale(final double scale) {
        this.scale = scale;
    }
    
    public boolean isDraft() {
        return this.draft;
    }
    
    public void setDraft(final boolean draft) {
        this.draft = draft;
    }
    
    public Ratio getRatio() {
        return this.ratio;
    }
    
    public void setRatio(final Ratio ratio) {
        this.ratio = ratio;
    }
    
    public long getAnimationDelay() {
        return this.animationDelay;
    }
    
    public void setAnimationDelay(final long animationDelay) {
        this.animationDelay = animationDelay;
    }
    
    public ItemType getType() {
        return this.type;
    }
    
    public void setType(final ItemType type) {
        this.type = type;
    }
    
    public MoveType getMoveType() {
        return this.moveType;
    }
    
    public void setMoveType(final MoveType moveType) {
        this.moveType = moveType;
    }
    
    public AttachmentPoint getAttachmentPoint() {
        return this.attachmentPoint;
    }
    
    public void setAttachmentPoint(final AttachmentPoint attachmentPoint) {
        this.attachmentPoint = attachmentPoint;
    }
    
    public TextureType getTextureType() {
        return this.textureType;
    }
    
    public void setTextureType(final TextureType textureType) {
        this.textureType = textureType;
    }
    
    public String getTextureDirectory() {
        return this.textureDirectory;
    }
    
    public void setTextureDirectory(final String textureDirectory) {
        this.textureDirectory = textureDirectory;
    }
    
    public boolean isMirror() {
        return this.mirror;
    }
    
    public void setMirror(final boolean mirror) {
        this.mirror = mirror;
    }
    
    public MirrorType getMirrorType() {
        return this.mirrorType;
    }
    
    public void setMirrorType(final MirrorType mirrorType) {
        this.mirrorType = mirrorType;
    }
    
    public float getNameTagOffset() {
        return this.nameTagOffset;
    }
    
    public void setNameTagOffset(final float nameTagOffset) {
        this.nameTagOffset = nameTagOffset;
    }
    
    public boolean isHideCape() {
        return this.hideCape;
    }
    
    public void setHideCape(final boolean hideCape) {
        this.hideCape = hideCape;
    }
    
    public boolean isHiddenWhileWearingElytra() {
        return this.hiddenWhileWearingElytra;
    }
    
    public void setHiddenWhileWearingElytra(final boolean hiddenWhileWearingElytra) {
        this.hiddenWhileWearingElytra = hiddenWhileWearingElytra;
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(final String category) {
        this.category = category;
    }
    
    public AttachmentPosition getPosition() {
        return this.position;
    }
    
    public void setPosition(final AttachmentPosition position) {
        this.position = position;
    }
    
    public String[] getOptionList() {
        return this.optionList;
    }
    
    public void setOptionList(final String[] optionList) {
        this.optionList = optionList;
    }
    
    public PetData getPetData() {
        return this.petData;
    }
    
    @Override
    public String toString() {
        return "ItemDetails[id=" + this.getIdentifier() + ", name=" + this.getName();
    }
}
