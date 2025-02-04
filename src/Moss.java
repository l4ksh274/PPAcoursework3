public class Moss extends Plant {
    
    private static final int MAX_AGE = 300;

    public Moss(Location location, Field field) {
        super(location, field);
        age = 0;
    }

    @Override 
    protected int getMaxAge() {
        return MAX_AGE;
    }
}
