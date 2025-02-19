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

    /**
     * Gets the age at which a berry is considered ripe.
     *
     * @return The age at which the berry reaches ripeness.
     */
    protected int getRipeAge() {
        return RIPE_AGE;
    }

    /**
     * Gets the current age of the berry.
     *
     * @return The age of the berry as an integer.
     */
    @Override
    protected int getAge() {
        return age;
    }

    /**
     * Gets the maximum age of the berry.
     * The maximum age determines the lifespan of the berry.
     *
     * @return The maximum age the berry can reach before it stops being alive.
     */
    @Override
    protected int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Gets the probability that a seed will sprout after the parent plant dies.
     *
     * @return The probability of a seed sprouting as a double value within the range [0, 1].
     */
    @Override
    protected double getSeedSproutProbability() {
        return SEED_SPROUT_PROBABILITY;
    }

    /**
     * Gets the probability of the plant creating offspring.
     * This is used to determine the likelihood of a plant
     * spreading its growth to adjacent locations under suitable conditions.
     *
     * @return The probability of the plant creating offspring.
     */
    @Override
    protected double getGrowthProbability() {
        return GROWTH_PROBABILITY;
    }

    /**
     * Creates a new instance of a Berry at the specified location.
     * This method is used to generate offspring for the plant.
     *
     * @param seedSproutLocation The location where the new Berry instance should grow.
     * @return A new instance of Berry at the given location.
     */
    @Override
    protected Plant createOffspring(Location seedSproutLocation) {
        return new Conifer(false, seedSproutLocation);
    }
}
