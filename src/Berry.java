/**
 * A simple model of a berry.
 * Berries age / ripen and die.
 *
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */

public class Berry extends Plant {
    // Characteristics shared by all berries.
    // Berry matures and can be eaten.
    private static final int RIPE_AGE = 15;
    // Berry's chance of seeds sprouting when parent berry dies
    private static final double SEED_SPROUT_PROBABILITY = 0.9;
    // The Berry's chance for growing more plants
    private static final double GROWTH_PROBABILITY = 0.004;
    // The age a Berry can live for
    private static final int MAX_AGE = 70;

    /**
     * Create a berry. A berry can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * @param randomAge If true, the berry will have random age and hunger level.
     * @param location The Location within the field.
     */
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