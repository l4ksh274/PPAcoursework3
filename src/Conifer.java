public class Conifer extends Plant {
    
    // Conifer matures and can be eaten
    private static final int RIPE_AGE = 20;
    // Conifer's chance of seeds sprouting when parent conifer dies
    private static final double SEED_SPROUT_PROBABILITY = 0.9;
    private static final double GROWTH_PROBABILITY = 0.006;

    private static final int MAX_AGE = 70;

    public Conifer(Boolean randomAge, Location location) {
        super(location);
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
    protected double getGrowthProbability() {
        return GROWTH_PROBABILITY;
    }

    @Override
    protected Plant createOffspring(Location seedSproutLocation) {
        return new Conifer(false, seedSproutLocation);
    }
}
