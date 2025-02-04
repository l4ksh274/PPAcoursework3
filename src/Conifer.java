public class Conifer extends Plant {
    
    private static final int MAX_AGE = 200;

    public Conifer(Location location, Field field, Plant originalPlant) {
        super(location, field, originalPlant);
        age = 0;
    }
    @Override 
    protected int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected Plant createOffspring(Location loc, Plant originalPlant) {
        return new Conifer(loc, field, originalPlant);
    }
}
