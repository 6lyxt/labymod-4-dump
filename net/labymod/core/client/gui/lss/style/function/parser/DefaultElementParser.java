// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.function.parser;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParseException;
import java.util.List;
import net.labymod.core.client.gui.lss.style.function.DefaultElementArray;
import java.nio.CharBuffer;
import java.util.ArrayList;
import net.labymod.api.client.gui.lss.style.function.Element;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParser;

@Singleton
@Implements(ElementParser.class)
public class DefaultElementParser implements ElementParser
{
    @Nullable
    @Override
    public Element parseElement(@NotNull final String function) throws ElementParseException {
        final List<Element> elements = new ArrayList<Element>();
        final ParsingFunction parser = new ParsingFunction(this);
        do {
            elements.add(parser.parseFunction(CharBuffer.wrap(function, parser.position(), function.length()).toString()));
        } while (parser.position() != function.length());
        return (elements.size() == 1) ? elements.get(0) : new DefaultElementArray(elements);
    }
}
