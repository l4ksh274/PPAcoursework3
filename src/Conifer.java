public class Conifer extends Plant {
    
    private static final int MAX_AGE = 200;

    public Conifer(Location location, Field field, boolean originalPlant) {
        super(location, field, originalPlant);
        age = 0;
    }
    @Override 
    protected int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected Plant createOffspring(Location loc, boolean isOriginal) {
        return new Conifer(loc, field, isOriginal);
    }
}
