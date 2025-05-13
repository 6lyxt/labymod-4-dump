// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.function;

import java.util.regex.Pattern;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

public final class NumberPredicate implements Predicate<String>
{
    private static final String NUMBER_PATTERN = "[0-9]";
    private final int maximum;
    private final IntConsumer numberConsumer;
    
    public NumberPredicate(final int maximum, final IntConsumer numberConsumer) {
        this.maximum = maximum;
        this.numberConsumer = numberConsumer;
    }
    
    @Override
    public boolean test(String content) {
        if (content == null) {
            this.acceptNumber(0);
            return true;
        }
        final StringBuilder builder = new StringBuilder();
        final char[] chars = content.toCharArray();
        boolean isZero = false;
        for (int i = 0; i < chars.length; ++i) {
            final char character = chars[i];
            if (!Pattern.matches("[0-9]", String.valueOf(character))) {
                return false;
            }
            if (isZero && character == '0') {
                return false;
            }
            if (i == 0 && character == '0') {
                isZero = true;
            }
            builder.append(character);
        }
        content = builder.toString();
        final int number = content.isEmpty() ? 0 : Integer.parseInt(content);
        if (number < 0 || number > this.maximum) {
            return false;
        }
        this.acceptNumber(number);
        return true;
    }
    
    private void acceptNumber(final int number) {
        if (this.numberConsumer == null) {
            return;
        }
        this.numberConsumer.accept(number);
    }
}
