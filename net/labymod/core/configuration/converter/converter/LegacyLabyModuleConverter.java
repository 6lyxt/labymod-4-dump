// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.converter.converter;

import net.labymod.core.client.gui.hud.hudwidget.text.AfkTimerHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ClickTestHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.ClockHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.CoordinateHudWidget;
import java.util.Objects;
import java.util.Arrays;
import net.labymod.core.client.gui.hud.hudwidget.text.DateHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.EntityCountHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.FDirectionHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.MemoryHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.text.PingHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.PotionEffectHudWidget;
import com.google.gson.JsonArray;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.labymod.core.client.gui.hud.hudwidget.text.SpeedHudWidget;
import net.labymod.core.client.gui.hud.hudwidget.item.ArrowHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.item.EquipmentWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import java.util.Map;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.gui.hud.GlobalHudWidgetConfig;
import net.labymod.api.util.Color;
import net.labymod.api.Laby;
import com.google.gson.JsonObject;
import net.labymod.api.configuration.converter.LegacyModuleConverter;

public class LegacyLabyModuleConverter extends LegacyModuleConverter
{
    public LegacyLabyModuleConverter() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokespecial   net/labymod/api/configuration/converter/LegacyModuleConverter.<init>:()V
        //     4: aload_0         /* this */
        //     5: ldc             "AfkTimerModule"
        //     7: ldc             "afk_timer"
        //     9: invokedynamic   BootstrapMethod #0, accept:()Ljava/util/function/BiConsumer;
        //    14: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //    17: aload_0         /* this */
        //    18: ldc             "BiomeModule"
        //    20: ldc             "biome"
        //    22: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;)V
        //    25: aload_0         /* this */
        //    26: ldc             "ClickTestModule"
        //    28: ldc             "click_test"
        //    30: invokedynamic   BootstrapMethod #1, accept:()Ljava/util/function/BiConsumer;
        //    35: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //    38: aload_0         /* this */
        //    39: ldc             "ClockModule"
        //    41: ldc             "clock"
        //    43: invokedynamic   BootstrapMethod #2, accept:()Ljava/util/function/BiConsumer;
        //    48: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //    51: aload_0         /* this */
        //    52: ldc             "ComboModule"
        //    54: ldc             "combo"
        //    56: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;)V
        //    59: aload_0         /* this */
        //    60: ldc             "CoordinatesModule"
        //    62: ldc             "coordinates"
        //    64: invokedynamic   BootstrapMethod #3, accept:()Ljava/util/function/BiConsumer;
        //    69: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //    72: aload_0         /* this */
        //    73: ldc             "DateModule"
        //    75: ldc             "date"
        //    77: invokedynamic   BootstrapMethod #4, accept:()Ljava/util/function/BiConsumer;
        //    82: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //    85: aload_0         /* this */
        //    86: ldc             "EntityCountModule"
        //    88: ldc             "entity_count"
        //    90: invokedynamic   BootstrapMethod #5, accept:()Ljava/util/function/BiConsumer;
        //    95: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //    98: aload_0         /* this */
        //    99: ldc             "FModule"
        //   101: ldc             "fdirection"
        //   103: invokedynamic   BootstrapMethod #6, accept:()Ljava/util/function/BiConsumer;
        //   108: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //   111: aload_0         /* this */
        //   112: ldc             "FPSModule"
        //   114: ldc             "fps"
        //   116: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;)V
        //   119: aload_0         /* this */
        //   120: ldc             "MemoryModule"
        //   122: ldc             "memory"
        //   124: invokedynamic   BootstrapMethod #7, accept:()Ljava/util/function/BiConsumer;
        //   129: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //   132: aload_0         /* this */
        //   133: ldc             "OnlinePlayersModule"
        //   135: ldc             "player_count"
        //   137: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;)V
        //   140: aload_0         /* this */
        //   141: ldc             "PingModule"
        //   143: ldc             "ping"
        //   145: invokedynamic   BootstrapMethod #8, accept:()Ljava/util/function/BiConsumer;
        //   150: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //   153: aload_0         /* this */
        //   154: ldc             "PotionEffectsModule"
        //   156: ldc             "potion"
        //   158: invokedynamic   BootstrapMethod #9, accept:()Ljava/util/function/BiConsumer;
        //   163: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //   166: aload_0         /* this */
        //   167: ldc             "RangeModule"
        //   169: ldc             "range"
        //   171: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;)V
        //   174: aload_0         /* this */
        //   175: ldc             "ScoreboardModule"
        //   177: ldc             "scoreboard"
        //   179: invokedynamic   BootstrapMethod #10, accept:()Ljava/util/function/BiConsumer;
        //   184: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //   187: aload_0         /* this */
        //   188: ldc             "ServerAddressModule"
        //   190: ldc             "server_address"
        //   192: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;)V
        //   195: aload_0         /* this */
        //   196: ldc             "ServerInfoModule"
        //   198: invokedynamic   BootstrapMethod #11, accept:()Ljava/util/function/BiConsumer;
        //   203: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //   206: aload_0         /* this */
        //   207: ldc             "SpeedModule"
        //   209: ldc             "speed"
        //   211: invokedynamic   BootstrapMethod #12, accept:()Ljava/util/function/BiConsumer;
        //   216: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //   219: aload_0         /* this */
        //   220: ldc             "YouTubeRealTimeModule"
        //   222: ldc             "service_youtube"
        //   224: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;)V
        //   227: aload_0         /* this */
        //   228: ldc             "BootsModule"
        //   230: ldc             "feet"
        //   232: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerEquipmentModule:(Ljava/lang/String;Ljava/lang/String;)V
        //   235: aload_0         /* this */
        //   236: ldc             "LeggingsModule"
        //   238: ldc             "legs"
        //   240: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerEquipmentModule:(Ljava/lang/String;Ljava/lang/String;)V
        //   243: aload_0         /* this */
        //   244: ldc             "ChestplateModule"
        //   246: ldc             "chest"
        //   248: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerEquipmentModule:(Ljava/lang/String;Ljava/lang/String;)V
        //   251: aload_0         /* this */
        //   252: ldc             "HelmetModule"
        //   254: ldc             "helmet"
        //   256: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerEquipmentModule:(Ljava/lang/String;Ljava/lang/String;)V
        //   259: aload_0         /* this */
        //   260: ldc             "HeldItemModule"
        //   262: ldc             "main_hand"
        //   264: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerEquipmentModule:(Ljava/lang/String;Ljava/lang/String;)V
        //   267: aload_0         /* this */
        //   268: ldc             "ArrowAmountModule"
        //   270: ldc             "arrow"
        //   272: invokedynamic   BootstrapMethod #13, accept:()Ljava/util/function/BiConsumer;
        //   277: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //   280: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException: Cannot invoke "com.strobel.assembler.metadata.TypeReference.getSimpleType()" because the return value of "com.strobel.decompiler.ast.Variable.getType()" is null
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:252)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:185)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.nameVariables(AstMethodBodyBuilder.java:1482)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.populateVariables(AstMethodBodyBuilder.java:1411)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:799)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:635)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:129)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    protected void convert(final JsonObject jsonObject) throws Exception {
        super.convert(jsonObject);
        final GlobalHudWidgetConfig globalHudWidgetConfig = Laby.labyAPI().hudWidgetRegistry().globalHudWidgetConfig();
        globalHudWidgetConfig.labelColor().set(Color.of(jsonObject.get("prefixColor").getAsInt()));
        globalHudWidgetConfig.bracketColor().set(Color.of(jsonObject.get("bracketsColor").getAsInt()));
        globalHudWidgetConfig.valueColor().set(Color.of(jsonObject.get("valuesColor").getAsInt()));
        globalHudWidgetConfig.itemGravity().set(jsonObject.get("itemSlotGravity").getAsBoolean());
        final BackgroundConfig background = globalHudWidgetConfig.background();
        background.enabled().set(jsonObject.get("backgroundVisible").getAsBoolean());
        final int color = jsonObject.get("backgroundColor").getAsInt();
        final int alpha = jsonObject.get("backgroundTransparency").getAsInt();
        background.color().set(Color.of(color, alpha));
        background.padding().set(Float.valueOf(jsonObject.get("padding").getAsInt()));
    }
    
    private void registerEquipmentModule(final String legacyModuleName, final String hudWidgetId) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1         /* legacyModuleName */
        //     2: aload_2         /* hudWidgetId */
        //     3: invokedynamic   BootstrapMethod #14, accept:()Ljava/util/function/BiConsumer;
        //     8: invokevirtual   net/labymod/core/configuration/converter/converter/LegacyLabyModuleConverter.registerModule:(Ljava/lang/String;Ljava/lang/String;Ljava/util/function/BiConsumer;)V
        //    11: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException: Cannot invoke "com.strobel.assembler.metadata.TypeReference.getSimpleType()" because the return value of "com.strobel.decompiler.ast.Variable.getType()" is null
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:252)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:185)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.nameVariables(AstMethodBodyBuilder.java:1482)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.populateVariables(AstMethodBodyBuilder.java:1411)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:129)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
