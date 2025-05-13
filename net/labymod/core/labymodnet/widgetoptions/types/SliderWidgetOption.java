// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labymodnet.widgetoptions.types;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.function.Consumer;
import net.labymod.core.labymodnet.widgetoptions.WidgetOption;

public class SliderWidgetOption extends WidgetOption
{
    private static final String DEBOUNCE_ID = "slider-debounce";
    private final float[] offsetCoordinates;
    private final String[] sliders;
    
    public SliderWidgetOption(final String optionName, final int optionIndex) {
        super(optionName, optionIndex);
        this.offsetCoordinates = new float[3];
        this.sliders = optionName.substring(6).replaceAll("[\\[\\]]", "").split(",");
    }
    
    @Override
    protected void create(final String data, final Consumer<Widget> consumer) {
        for (final String slider : this.sliders) {
            final SliderWidget sliderWidget = this.createSlider(slider, data);
            if (sliderWidget != null) {
                consumer.accept(sliderWidget);
            }
        }
    }
    
    private SliderWidget createSlider(final String slider, final String data) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "="
        //     3: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //     6: astore_3        /* sliderData */
        //     7: aload_3         /* sliderData */
        //     8: iconst_0       
        //     9: aaload         
        //    10: astore          sliderKey
        //    12: aload_2         /* data */
        //    13: ldc             ";"
        //    15: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //    18: astore          coordinates
        //    20: iconst_0       
        //    21: istore          i
        //    23: iload           i
        //    25: iconst_3       
        //    26: if_icmpge       50
        //    29: aload_0         /* this */
        //    30: getfield        net/labymod/core/labymodnet/widgetoptions/types/SliderWidgetOption.offsetCoordinates:[F
        //    33: iload           i
        //    35: aload           coordinates
        //    37: iload           i
        //    39: aaload         
        //    40: invokestatic    java/lang/Float.parseFloat:(Ljava/lang/String;)F
        //    43: fastore        
        //    44: iinc            i, 1
        //    47: goto            23
        //    50: aload           sliderKey
        //    52: astore          7
        //    54: iconst_m1      
        //    55: istore          8
        //    57: aload           7
        //    59: invokevirtual   java/lang/String.hashCode:()I
        //    62: tableswitch {
        //              240: 88
        //              241: 104
        //              242: 120
        //          default: 133
        //        }
        //    88: aload           7
        //    90: ldc             "x"
        //    92: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    95: ifeq            133
        //    98: iconst_0       
        //    99: istore          8
        //   101: goto            133
        //   104: aload           7
        //   106: ldc             "y"
        //   108: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   111: ifeq            133
        //   114: iconst_1       
        //   115: istore          8
        //   117: goto            133
        //   120: aload           7
        //   122: ldc             "z"
        //   124: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   127: ifeq            133
        //   130: iconst_2       
        //   131: istore          8
        //   133: iload           8
        //   135: tableswitch {
        //                0: 160
        //                1: 171
        //                2: 182
        //          default: 193
        //        }
        //   160: aload_0         /* this */
        //   161: getfield        net/labymod/core/labymodnet/widgetoptions/types/SliderWidgetOption.offsetCoordinates:[F
        //   164: iconst_0       
        //   165: faload         
        //   166: fstore          currentValue
        //   168: goto            195
        //   171: aload_0         /* this */
        //   172: getfield        net/labymod/core/labymodnet/widgetoptions/types/SliderWidgetOption.offsetCoordinates:[F
        //   175: iconst_1       
        //   176: faload         
        //   177: fstore          currentValue
        //   179: goto            195
        //   182: aload_0         /* this */
        //   183: getfield        net/labymod/core/labymodnet/widgetoptions/types/SliderWidgetOption.offsetCoordinates:[F
        //   186: iconst_2       
        //   187: faload         
        //   188: fstore          currentValue
        //   190: goto            195
        //   193: aconst_null    
        //   194: areturn        
        //   195: goto            220
        //   198: astore          e
        //   200: getstatic       net/labymod/core/labymodnet/widgetoptions/types/SliderWidgetOption.LOGGER:Lnet/labymod/api/util/logging/Logging;
        //   203: aload_2         /* data */
        //   204: invokedynamic   BootstrapMethod #0, makeConcatWithConstants:(Ljava/lang/String;)Ljava/lang/String;
        //   209: iconst_0       
        //   210: anewarray       Ljava/lang/Object;
        //   213: invokeinterface net/labymod/api/util/logging/Logging.info:(Ljava/lang/CharSequence;[Ljava/lang/Object;)V
        //   218: aconst_null    
        //   219: areturn        
        //   220: aload_3         /* sliderData */
        //   221: iconst_1       
        //   222: aaload         
        //   223: ldc             "<"
        //   225: invokevirtual   java/lang/String.split:(Ljava/lang/String;)[Ljava/lang/String;
        //   228: astore          sliderValues
        //   230: aload           sliderValues
        //   232: iconst_0       
        //   233: aaload         
        //   234: invokestatic    java/lang/Float.parseFloat:(Ljava/lang/String;)F
        //   237: fstore          sliderMin
        //   239: aload           sliderValues
        //   241: iconst_1       
        //   242: aaload         
        //   243: invokestatic    java/lang/Float.parseFloat:(Ljava/lang/String;)F
        //   246: fstore          sliderMax
        //   248: new             Lnet/labymod/api/client/gui/screen/widget/widgets/input/SliderWidget;
        //   251: dup            
        //   252: fconst_1       
        //   253: aload_0         /* this */
        //   254: aload           sliderKey
        //   256: invokedynamic   BootstrapMethod #1, updateValue:(Lnet/labymod/core/labymodnet/widgetoptions/types/SliderWidgetOption;Ljava/lang/String;)Lnet/labymod/api/client/gui/screen/widget/action/SliderInteraction;
        //   261: invokespecial   net/labymod/api/client/gui/screen/widget/widgets/input/SliderWidget.<init>:(FLnet/labymod/api/client/gui/screen/widget/action/SliderInteraction;)V
        //   264: astore          sliderWidget
        //   266: aload           sliderWidget
        //   268: fload           sliderMin
        //   270: fload           sliderMax
        //   272: invokevirtual   net/labymod/api/client/gui/screen/widget/widgets/input/SliderWidget.range:(FF)Lnet/labymod/api/client/gui/screen/widget/widgets/input/SliderWidget;
        //   275: pop            
        //   276: aload           sliderWidget
        //   278: fload           currentValue
        //   280: f2d            
        //   281: iconst_0       
        //   282: invokevirtual   net/labymod/api/client/gui/screen/widget/widgets/input/SliderWidget.setValue:(DZ)V
        //   285: aload           sliderWidget
        //   287: areturn        
        //    StackMapTable: 00 0D FF 00 17 00 08 07 00 08 07 00 0E 07 00 0E 07 00 8B 07 00 0E 00 07 00 8B 01 00 00 FA 00 1A FD 00 25 07 00 0E 01 0F 0F 0C 1A 0A 0A 0A FF 00 01 00 06 07 00 08 07 00 0E 07 00 0E 07 00 8B 07 00 0E 02 00 00 FF 00 02 00 05 07 00 08 07 00 0E 07 00 0E 07 00 8B 07 00 0E 00 01 07 00 47 FC 00 15 02
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                      
        //  -----  -----  -----  -----  ------------------------------------------
        //  12     194    198    220    Ljava/lang/ArrayIndexOutOfBoundsException;
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
