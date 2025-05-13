// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.lss.style.function.parser;

import java.util.List;
import net.labymod.core.client.gui.lss.style.function.DefaultFunction;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParseException;
import net.labymod.core.client.gui.lss.style.function.parameter.FixedElement;
import java.util.ArrayList;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.function.parser.ElementParser;

public class ParsingFunction
{
    public static final char PARAMS_OPEN = '(';
    public static final char PARAMS_CLOSE = ')';
    public static final char PARAM_SPLIT = ',';
    public static final char PARAM_SPACE_SPLIT = ' ';
    private final ElementParser elementParser;
    private final StringBuilder text;
    private ParserState state;
    private int position;
    
    public ParsingFunction(final ElementParser elementParser) {
        this.text = new StringBuilder();
        this.state = ParserState.FUNCTION_NAME;
        this.position = 0;
        this.elementParser = elementParser;
    }
    
    private String selectState(final ParserState state) {
        this.state = state;
        return this.resetText();
    }
    
    private String resetText() {
        final String text = this.text.toString();
        this.text.setLength();
        return text;
    }
    
    public Element parseFunction(final String input) throws ElementParseException {
        this.state = ParserState.FUNCTION_NAME;
        String functionName = null;
        int functionLevel = 0;
        final List<Element> parameters = new ArrayList<Element>();
        final char[] charArray2;
        final char[] chars = charArray2 = input.toCharArray();
        for (final char c : charArray2) {
            ++this.position;
            Label_0200: {
                if (c == ' ' && this.state == ParserState.FUNCTION_NAME) {
                    if (!this.text.isEmpty()) {
                        return new FixedElement(this.resetText());
                    }
                }
                else {
                    if (c == '(') {
                        ++functionLevel;
                        if (this.state == ParserState.FUNCTION_NAME) {
                            functionName = this.selectState(ParserState.FUNCTION_PARAMS);
                            break Label_0200;
                        }
                    }
                    if ((c == ',' || c == ')') && functionLevel == 1) {
                        parameters.add(this.parseParameter(this.resetText()));
                        if (c == ')') {
                            --functionLevel;
                            break;
                        }
                    }
                    else {
                        if (c == ')') {
                            --functionLevel;
                        }
                        this.text.append(c);
                    }
                }
            }
        }
        if (functionName == null || functionName.trim().isEmpty()) {
            final String value = this.text.toString();
            this.text.setLength();
            return new FixedElement(value.trim());
        }
        if (functionLevel != 0) {
            throw new ElementParseException("Function \"" + functionName + "\" not closed (missing bracket): " + input);
        }
        if (!this.text.isEmpty()) {
            String text = this.resetText();
            final char[] charArray = text.toCharArray();
            boolean quoted = false;
            int level = 1;
            for (final char c2 : charArray) {
                if (c2 == '\"') {
                    quoted = !quoted;
                }
                if (!quoted) {
                    if (c2 == '(') {
                        ++level;
                    }
                    if (c2 == ')' && --level == 0) {
                        text = this.text.toString();
                        parameters.add(this.parseParameter(text.trim()));
                        this.text.setLength();
                        this.position -= charArray.length - text.length() - 1;
                        break;
                    }
                }
                this.text.append(c2);
            }
        }
        return new DefaultFunction(functionName.trim(), parameters.toArray(new Element[0]));
    }
    
    private Element parseParameter(String rawParam) {
        if (rawParam.indexOf(40) > 0 && rawParam.indexOf(41) != -1) {
            return this.elementParser.parseElement(rawParam);
        }
        rawParam = rawParam.trim();
        if (rawParam.indexOf(34) == 0 && rawParam.lastIndexOf(34) == rawParam.length() - 1) {
            rawParam = rawParam.substring(1, rawParam.length() - 1);
        }
        return new FixedElement(rawParam);
    }
    
    public int position() {
        return this.position;
    }
}
