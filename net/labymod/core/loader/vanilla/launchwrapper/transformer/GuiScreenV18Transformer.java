// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer;

import org.objectweb.asm.tree.ClassNode;
import java.util.Iterator;
import net.labymod.api.volt.asm.util.ASMHelper;
import org.objectweb.asm.tree.FieldNode;
import net.minecraft.launchwrapper.IClassTransformer;

public class GuiScreenV18Transformer implements IClassTransformer
{
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (!name.equals("net.minecraft.client.gui.GuiScreen")) {
            return classData;
        }
        return ASMHelper.transformClassData(classData, classNode -> {
            classNode.fields.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final FieldNode field = iterator.next();
                if (field.name.equals("mc") && field.desc.equalsIgnoreCase("Lnet/minecraft/client/Minecraft;")) {
                    field.access = 1;
                    break;
                }
            }
        }, false);
    }
}
