public class Berry extends Plant {
    
    // Berry matures and can be eaten
    private static final int RIPE_AGE = 50;
    // Berry's chance of seeds sprouting when parent berry dies
    private static final double SEED_SPROUT_PROBABILITY = 1;

    private static final int MAX_AGE = 700;

    public Berry(Boolean randomAge, Location location, Field field) {
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
        return new Berry(false, seedSproutLocation, field);
    }
}