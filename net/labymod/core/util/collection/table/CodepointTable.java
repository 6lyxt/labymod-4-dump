// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.collection.table;

import java.util.Objects;
import java.util.function.ObjIntConsumer;
import java.util.Arrays;
import java.util.function.IntFunction;

public class CodepointTable<T>
{
    private static final int BLOCK_BITS = 8;
    private static final int BLOCK_MASK = 255;
    private static final int BLOCK_SIZE = 256;
    private static final int BLOCK_COUNT = 4352;
    private final T[] emptyBlock;
    private final T[][] blockMap;
    private final IntFunction<T[]> blockConstructor;
    
    public CodepointTable(final IntFunction<T[]> blockConstructor, final IntFunction<T[][]> blockMapConstructor) {
        this.blockConstructor = blockConstructor;
        this.emptyBlock = blockConstructor.apply(256);
        Arrays.fill(this.blockMap = blockMapConstructor.apply(4352), this.emptyBlock);
    }
    
    public T get(final int index) {
        final int blockIndex = index >> 8;
        final int blockPosition = index & 0xFF;
        return this.blockMap[blockIndex][blockPosition];
    }
    
    public void set(final int index, final T value) {
        final int blockIndex = index >> 8;
        final int blockPosition = index & 0xFF;
        T[] blockData = this.blockMap[blockIndex];
        if (blockData == this.emptyBlock) {
            blockData = this.blockConstructor.apply(256);
            this.blockMap[blockIndex] = blockData;
        }
        blockData[blockPosition] = value;
    }
    
    public void clear() {
        Arrays.fill(this.blockMap, this.emptyBlock);
    }
    
    public T computeIfAbsent(final int index, final IntFunction<T> function) {
        T value = this.get(index);
        if (value != null) {
            return value;
        }
        value = function.apply(index);
        this.set(index, value);
        return value;
    }
    
    public void forEach(final ObjIntConsumer<T> consumer) {
        Objects.requireNonNull(consumer, "consumer must not be null");
        for (int blockIndex = 0; blockIndex < this.blockMap.length; ++blockIndex) {
            final T[] blockData = this.blockMap[blockIndex];
            if (blockData != this.emptyBlock) {
                for (int index = 0; index < blockData.length; ++index) {
                    final T value = blockData[index];
                    if (value != null) {
                        consumer.accept(value, blockIndex << 8 | index);
                    }
                }
            }
        }
    }
}
