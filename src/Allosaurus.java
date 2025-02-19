/**
 * A simple model of an Allosaurus.
 * Allosaurus age, move, eat Ankylosaurus, and eventually die.
 *
 * This class extends the Predator class and defines specific behaviors
 * and characteristics of an Allosaurus, including its breeding age,
 * maximum age, breeding probability, food value, and litter size.
 *
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public class Allosaurus extends Predator {

    /** The age at which an Allosaurus can start to breed. */
    private static final int BREEDING_AGE = 6;

    /** The age to which an Allosaurus can live. */
    private static final int MAX_AGE = 160;

    /** The likelihood of an Allosaurus breeding. */
    private static final double BREEDING_PROBABILITY = 0.25;

    /** The maximum number of births per breeding event. */
    private static final int MAX_LITTER_SIZE = 4;

    /** The food value of a single Ankylosaurus. */
    private static final int ANKYLOSAURUS_FOOD_VALUE = 25;

    /**
     * Create an Allosaurus. An Allosaurus can be created as a newborn (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the Allosaurus will have a random age and hunger level.
     * @param location The location within the field where the Allosaurus is placed.
     */
    public Allosaurus(boolean randomAge, Location location) {
        super(location);
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
        } else {
            age = 0;
        }
        foodLevel = rand.nextInt(ANKYLOSAURUS_FOOD_VALUE);
    }

    /**
     * Get the maximum age an Allosaurus can live.
     *
     * @return The maximum age of the Allosaurus.
     */
    @Override
    protected int getMaxAge() {
        return MAX_AGE;
    }

    /**
     * Get the current age of the Allosaurus.
     *
     * @return The current age of the Allosaurus.
     */
    @Override
    protected int getAge() {
        return age;
    }

    /**
     * Get the food value of an Ankylosaurus.
     *
     * @return The food value of an Ankylosaurus.
     */
    @Override
    protected int getFoodValue() {
        return ANKYLOSAURUS_FOOD_VALUE;
    }

    /**
     * Get the breeding probability of an Allosaurus.
     *
     * @return The breeding probability of the Allosaurus.
     */
    @Override
    protected double getBreedingProbability() {
        return BREEDING_PROBABILITY;
    }

    /**
     * Get the maximum litter size of an Allosaurus.
     *
     * @return The maximum number of offspring in one birth.
     */
    @Override
    protected int getMaxLitterSize() {
        return MAX_LITTER_SIZE;
    }

    /**
     * Get the breeding age of an Allosaurus.
     *
     * @return The age at which the Allosaurus can start breeding.
     */
    @Override
    protected int getBreedingAge() {
        return BREEDING_AGE;
    }

    /**
     * Create an offspring Allosaurus at the specified location.
     *
     * @param loc The location where the new Allosaurus will be placed.
     * @return A new Allosaurus instance that starts at age 0
     */
    @Override
    protected Animal createOffspring(Location loc) {
        return new Allosaurus(false, loc);
    }

    /**
     * Determine whether a given entity is food for the Allosaurus.
     * An Allosaurus eats Ankylosaurus.
     *
     * @param entity The entity to check.
     * @return True if the entity is an Ankylosaurus, false otherwise.
     */
    @Override
    protected boolean isFood(Entity entity) {
        return entity instanceof Ankylosaurus;
    }
}