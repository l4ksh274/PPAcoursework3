public class Conifer extends Plant {
    
    private static final int MAX_AGE = 200;

    public Conifer(Location location, Field field) {
        super(location, field);
        age = 0;
    }

    @Override 
    protected int getMaxAge() {
        return MAX_AGE;
    }
}
