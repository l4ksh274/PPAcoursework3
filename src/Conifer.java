public class Conifer extends Plant {
    
    // Conifer matures and can be eaten
    private static final int RIPE_AGE = 10;
    // Conifer's chance of seeds sprouting when parent conifer dies
    private static final double SEED_SPROUT_PROBABILITY = 1;

    private static final int MAX_AGE = 200;

    public Conifer(Boolean randomAge, Location location, Field field) {
        super(location, field);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
    }

    protected int getRipeAge() {
        return RIPE_AGE;
    }

    @Override
    protected int getAge() {
        return age;
    }

    @Override 
    protected int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected double getSeedSproutProbability() {
        return SEED_SPROUT_PROBABILITY;
    }

    @Override
    protected Plant createOffspring(Location seedSproutLocation) {
        System.out.println("New baby conifer");
        return new Conifer(false, seedSproutLocation, field);
    }
}
