// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier.attribute;

import net.labymod.api.client.gui.lss.LssPropertyException;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.client.gui.lss.LssStyleException;
import net.labymod.api.client.gui.lss.style.function.Function;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.reader.SingleInstruction;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.util.function.ThrowableSupplier;
import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.client.gui.lss.style.modifier.Forwarder;
import net.labymod.api.util.logging.Logging;

public class PropertyAttributePatch extends AttributePatch
{
    private static final Logging LOGGER;
    private final Forwarder forwarder;
    private final Class<?> type;
    private final Element element;
    private final ThrowableSupplier<ProcessedObject[], Exception> objectSupplier;
    private ProcessedObject[] objects;
    
    public PropertyAttributePatch(final Forwarder forwarder, final Class<?> type, final SingleInstruction instruction, final Element element, final ThrowableSupplier<ProcessedObject[], Exception> objectSupplier) {
        super(instruction, element.getRawValue());
        this.forwarder = forwarder;
        this.type = type;
        this.element = element;
        this.objectSupplier = objectSupplier;
    }
    
    public Forwarder forwarder() {
        return this.forwarder;
    }
    
    public Class<?> getType() {
        return this.type;
    }
    
    public Element element() {
        return this.element;
    }
    
    public ProcessedObject[] objects() {
        return this.objects;
    }
    
    public Map<String, String> collectVariables(final Widget widget) {
        final Map<String, String> variables = new HashMap<String, String>();
        this.element.forEach(child -> {
            if (child instanceof final Function function) {
                if (function.getName().equals("var") && function.parameters().length == 1) {
                    try {
                        final ProcessedObject[] variable = function.computeValue(widget, this.getKey(), String.class);
                        if (variable.length == 1) {
                            variables.put(function.parameters()[0].getRawValue(), variable[0].rawValue());
                        }
                    }
                    catch (final Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            return;
        });
        return variables;
    }
    
    private void updateObject() throws LssStyleException {
        try {
            this.objects = this.objectSupplier.get();
        }
        catch (final Exception exception) {
            throw new LssStyleException(this.instruction().styleSheet(), this.instruction(), exception.getMessage());
        }
    }
    
    @Override
    public void init() throws LssStyleException {
        this.updateObject();
    }
    
    @Override
    public void patch(final Widget widget) {
        try {
            this.updateObject();
        }
        catch (final Exception exception) {
            exception.printStackTrace();
            return;
        }
        this.patch(widget, this.objects);
    }
    
    private void patch(final Widget widget, final ProcessedObject... processedObject) {
        try {
            this.forwarder.forward(widget, this.getKey(), processedObject);
            if (widget instanceof final WrappedWidget wrappedWidget) {
                this.patch(wrappedWidget.childWidget());
            }
        }
        catch (final LssPropertyException exception) {
            PropertyAttributePatch.LOGGER.error("Failed to apply style instruction from {}:{} to widget {}: {}", this.instruction().styleSheet().file(), this.instruction().getLineNumber(), widget.getIds(), exception.getMessage());
        }
        catch (final Exception exception2) {
            exception2.printStackTrace();
        }
    }
    
    @Override
    public void unpatch(final Widget widget) {
        this.forwarder.reset(widget, this.getKey());
        if (widget instanceof final WrappedWidget wrappedWidget) {
            this.unpatch(wrappedWidget.childWidget());
        }
    }
    
    static {
        LOGGER = Logging.create(PropertyAttributePatch.class);
    }
}
