// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.camera.spline;

import net.labymod.core.util.camera.spline.position.PositionValueAdapter;
import java.util.ArrayList;
import net.labymod.core.util.camera.spline.position.PositionValueType;
import java.util.Collections;
import java.util.List;
import net.labymod.core.util.camera.spline.position.Location;
import java.util.Vector;

public class Spline
{
    private final Vector<Location> points;
    private List<Vector<Cubic>> cubics;
    private int cubicEntrySize;
    
    public Spline() {
        this.points = new Vector<Location>();
        this.cubics = Collections.emptyList();
    }
    
    public boolean isValid() {
        return this.points.size() >= 2 && !this.cubics.isEmpty();
    }
    
    public void reset() {
        this.points.clear();
        this.cubics.clear();
        this.cubicEntrySize = 0;
    }
    
    public void add(final Location point) {
        if (this.points.isEmpty()) {
            point.setYaw((point.getYaw() + 180.0) % 360.0 - 180.0);
            point.setRoll(point.getRoll() % 360.0);
        }
        else {
            final Location last = this.points.get(this.points.size() - 1);
            point.setYaw(this.fixEulerRotation(last.getYaw(), point.getYaw(), 180));
            point.setRoll(this.fixEulerRotation(last.getRoll(), point.getRoll(), 0));
        }
        this.points.add(point);
    }
    
    public Location get(final float progress) {
        final float progressAtCubics = progress * this.cubicEntrySize;
        final int cubicIndex = (int)Math.min((float)(this.cubicEntrySize - 1), progressAtCubics);
        final float cubicProgress = progressAtCubics - cubicIndex;
        final Location position = new Location();
        for (final PositionValueType valueType : PositionValueType.values()) {
            final double value = this.cubics.get(valueType.ordinal()).get(cubicIndex).eval(cubicProgress);
            valueType.getAdapter().set(position, value);
        }
        return position;
    }
    
    public void calculate() {
        this.cubics = new ArrayList<Vector<Cubic>>(PositionValueType.values().length);
        for (final PositionValueType type : PositionValueType.values()) {
            final Vector<Cubic> vector = new Vector<Cubic>();
            try {
                this.calculateNaturalCubic(type, this.points, vector);
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
            this.cubicEntrySize = vector.size();
            this.cubics.add(vector);
        }
    }
    
    private void calculateNaturalCubic(final PositionValueType type, final Vector<Location> points, final Vector<Cubic> cubics) {
        final PositionValueAdapter adapter = type.getAdapter();
        final int num = points.size() - 1;
        final double[] gamma = new double[num + 1];
        final double[] delta = new double[num + 1];
        final double[] D = new double[num + 1];
        gamma[0] = 0.5;
        for (int i = 1; i < num; ++i) {
            gamma[i] = 1.0 / (4.0 - gamma[i - 1]);
        }
        gamma[num] = 1.0 / (2.0 - gamma[num - 1]);
        double p0 = adapter.get(points.get(0));
        double p2 = adapter.get(points.get(1));
        delta[0] = 3.0 * (p2 - p0) * gamma[0];
        for (int i = 1; i < num; ++i) {
            p0 = adapter.get(points.get(i - 1));
            p2 = adapter.get(points.get(i + 1));
            delta[i] = (3.0 * (p2 - p0) - delta[i - 1]) * gamma[i];
        }
        p0 = adapter.get(points.get(num - 1));
        p2 = adapter.get(points.get(num));
        D[num] = (delta[num] = (3.0 * (p2 - p0) - delta[num - 1]) * gamma[num]);
        for (int i = num - 1; i >= 0; --i) {
            D[i] = delta[i] - gamma[i] * D[i + 1];
        }
        cubics.clear();
        for (int i = 0; i < num; ++i) {
            p0 = adapter.get(points.get(i));
            p2 = adapter.get(points.get(i + 1));
            cubics.add(new Cubic(p0, D[i], 3.0 * (p2 - p0) - 2.0 * D[i] - D[i + 1], 2.0 * (p0 - p2) + D[i] + D[i + 1]));
        }
    }
    
    private double fixEulerRotation(final double first, final double second, final int eulerBreak) {
        if (first == second) {
            return first;
        }
        final double normalizedFirst = (first + eulerBreak) % 360.0;
        final double normalizedSecond = (second + eulerBreak) % 360.0;
        double pathDifference = Math.abs(normalizedSecond - normalizedFirst);
        final int factor = (normalizedSecond > normalizedFirst) ? 1 : -1;
        if (pathDifference > 180.0) {
            pathDifference = -1.0 * (360.0 - pathDifference);
        }
        return first + factor * pathDifference;
    }
}
