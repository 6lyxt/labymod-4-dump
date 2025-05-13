// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.camera.spline.position;

public enum PositionValueType
{
    X((PositionValueAdapter)new PositionValueAdapter() {
        @Override
        public double get(final Location position) {
            return position.getX();
        }
        
        @Override
        public void set(final Location position, final double value) {
            position.setX(value);
        }
    }), 
    Y((PositionValueAdapter)new PositionValueAdapter() {
        @Override
        public double get(final Location position) {
            return position.getY();
        }
        
        @Override
        public void set(final Location position, final double value) {
            position.setY(value);
        }
    }), 
    Z((PositionValueAdapter)new PositionValueAdapter() {
        @Override
        public double get(final Location position) {
            return position.getZ();
        }
        
        @Override
        public void set(final Location position, final double value) {
            position.setZ(value);
        }
    }), 
    YAW((PositionValueAdapter)new PositionValueAdapter() {
        @Override
        public double get(final Location position) {
            return position.getYaw();
        }
        
        @Override
        public void set(final Location position, final double value) {
            position.setYaw(value);
        }
    }), 
    PITCH((PositionValueAdapter)new PositionValueAdapter() {
        @Override
        public double get(final Location position) {
            return position.getPitch();
        }
        
        @Override
        public void set(final Location position, final double value) {
            position.setPitch(value);
        }
    }), 
    TILT((PositionValueAdapter)new PositionValueAdapter() {
        @Override
        public double get(final Location position) {
            return position.getRoll();
        }
        
        @Override
        public void set(final Location position, final double value) {
            position.setRoll(value);
        }
    });
    
    private final PositionValueAdapter adapter;
    
    private PositionValueType(final PositionValueAdapter adapter) {
        this.adapter = adapter;
    }
    
    public PositionValueAdapter getAdapter() {
        return this.adapter;
    }
    
    public double get(final Location position) {
        return this.adapter.get(position);
    }
}
