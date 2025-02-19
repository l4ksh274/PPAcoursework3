/**
 * A simple model of a conifer.
 * Conifers age / ripen and die.
 *
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */

public class Conifer extends Plant {

    // Characteristics shared by all conifers.
    // Conifer matures and can be eaten.
    private static final int RIPE_AGE = 20;
    // Conifer's chance of seeds sprouting when parent conifer dies
    private static final double SEED_SPROUT_PROBABILITY = 0.9;
    // The age to which a Conifer can live.
    private static final double GROWTH_PROBABILITY = 0.006;
    // The age to which a berry can live.
    private static final int MAX_AGE = 70;

    /**
     * Create a conifer. A conifer can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * @param randomAge If true, the berry will hvae random age and hunger level.
     * @param location The location within the field.
     */
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
