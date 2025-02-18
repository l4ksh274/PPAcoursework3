public class Berry extends Plant {
    
    // Berry matures and can be eaten
    private static final int RIPE_AGE = 20;
    // Berry's chance of seeds sprouting when parent berry dies
    private static final double SEED_SPROUT_PROBABILITY = 0.9;
    // The Berry's chance for growing more plants
    private static final double GROWTH_PROBABILITY = 0.1;

    private static final int MAX_AGE = 700;

    public Berry(Boolean randomAge, Location location) {
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
        return new Berry(false, seedSproutLocation);
    }
}