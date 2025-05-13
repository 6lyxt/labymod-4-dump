// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.transformer;

import org.objectweb.asm.tree.AbstractInsnNode;
import java.util.Iterator;
import net.labymod.api.volt.asm.tree.InsnListBuilder;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import java.util.List;
import org.objectweb.asm.tree.ClassNode;
import net.labymod.api.volt.asm.util.ASMHelper;
import net.labymod.api.volt.asm.instruction.InstructionFinder;
import net.minecraft.launchwrapper.IClassTransformer;

@Deprecated(forRemoval = true, since = "4.1.12")
public class GameScreenTransformer implements IClassTransformer
{
    private static final String GAME_SCREEN = "net/labymod/api/client/gui/screen/game/GameScreen";
    private static final String TAGGED_OBJECT = "net/labymod/api/tag/TaggedObject";
    private static final String TAGGED_OBJECT_DESCRIPTOR = "Lnet/labymod/api/tag/TaggedObject;";
    private static final String TAGGED_OBJECT_METHOD_NAME = "taggedObject";
    private static final String TAGGED_OBJECT_METHOD_DESCRIPTOR = "()Lnet/labymod/api/tag/TaggedObject;";
    private static final String GENERATED_FIELD_NAME = "dynamic_generated_taggedObject";
    private final InstructionFinder instructionFinder;
    
    public GameScreenTransformer() {
        this.instructionFinder = new InstructionFinder();
    }
    
    public byte[] transform(final String name, final String transformedName, final byte... classData) {
        if (classData == null) {
            return null;
        }
        return ASMHelper.transformClassData(classData, this::patch, false);
    }
    
    private void patch(final ClassNode classNode) {
        if (!this.hasGameScreenInterface(classNode.interfaces)) {
            return;
        }
        boolean hasTaggedObjectMethod = false;
        for (final MethodNode method : classNode.methods) {
            if (method.name.equals("taggedObject") && method.desc.equals("()Lnet/labymod/api/tag/TaggedObject;")) {
                hasTaggedObjectMethod = true;
                break;
            }
        }
        if (hasTaggedObjectMethod) {
            return;
        }
        classNode.fields.add(new FieldNode(18, "dynamic_generated_taggedObject", "Lnet/labymod/api/tag/TaggedObject;", (String)null, (Object)null));
        for (final MethodNode method : classNode.methods) {
            if (method.name.equals("<init>")) {
                this.initializeTaggedObjectField(classNode, method);
            }
        }
        final MethodNode taggedObjectMethod = new MethodNode(1, "taggedObject", "()Lnet/labymod/api/tag/TaggedObject;", (String)null, (String[])null);
        final InsnListBuilder builder = InsnListBuilder.create();
        builder.addVar(25, 0);
        builder.addField(180, classNode.name, "dynamic_generated_taggedObject", "Lnet/labymod/api/tag/TaggedObject;");
        builder.addInstruction(176);
        taggedObjectMethod.instructions.add(builder.build());
        classNode.methods.add(taggedObjectMethod);
    }
    
    private void initializeTaggedObjectField(final ClassNode classNode, final MethodNode method) {
        final InsnListBuilder builder = InsnListBuilder.create();
        builder.addVar(25, 0);
        builder.addType(187, "net/labymod/api/tag/TaggedObject");
        builder.addInstruction(89);
        builder.addMethod(183, "net/labymod/api/tag/TaggedObject", "<init>", "()V");
        builder.addField(181, classNode.name, "dynamic_generated_taggedObject", "Lnet/labymod/api/tag/TaggedObject;");
        final AbstractInsnNode lastReturnInstruction = this.instructionFinder.findLastReturnInstruction(method);
        method.instructions.insertBefore(lastReturnInstruction, builder.build());
    }
    
    private boolean hasGameScreenInterface(final List<String> interfaces) {
        if (interfaces == null) {
            return false;
        }
        for (final String anInterface : interfaces) {
            if ("net/labymod/api/client/gui/screen/game/GameScreen".equals(anInterface)) {
                return true;
            }
        }
        return false;
    }
}
