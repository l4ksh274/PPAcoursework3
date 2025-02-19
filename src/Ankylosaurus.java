/**
 * A simple model of an ankylosaurus.
 * Ankylosaurus' age, move, eat berries and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 7.1
 *
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public class Ankylosaurus extends Prey
{
    // Characteristics shared by all ankylosaurus' (class variables).
    // The age at which a ankylosaurus can start to breed.
    private static final int BREEDING_AGE = 5;
    // The age to which a ankylosaurus can live.
    private static final int MAX_AGE = 90;
    // The likelihood of a ankylosaurus breeding.
    private static final double BREEDING_PROBABILITY = 0.85;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 8;
    // The food value of a single Berry. In effect, this is the
    // number of steps a trex can go before it has to eat again.
    private static final int BERRY_FOOD_VALUE = 40;
    
    // Individual characteristics (instance fields).
    
    /**
     * Create a new ankylosaurus. A ankylosaurus can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the ankylosaurus will have a random age.
     * @param location The location within the field.
     */
    public Ankylosaurus(boolean randomAge, Location location)
    {
        super(location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
        foodLevel = rand.nextInt(BERRY_FOOD_VALUE);
    }
    
    /**
     * Gets the maximum age a specific ankylosaurus can reach.
     *
     * @return The maximum age of the ankylosaurus.
     */
    @Override
    protected int getMaxAge(){
        return MAX_AGE;
    }

    /**
     * Returns the current age of the ankylosaurus.
     *
     * @return The age of the ankylosaurus.
     */
    @Override
    protected int getAge() {
        return age;
    }

    /**
     * Gets the food value provided by a single berry when consumed by the ankylosaurus.
     * This determines how many steps the ankylosaurus can go without eating again after consuming a berry.
     *
     * @return The food value of a single berry.
     */
    @Override
    protected int getFoodValue() {
        return BERRY_FOOD_VALUE;
    }
    
    /**
     * Returns the breeding probability of the ankylosaurus. Breeding probability
     * determines the likelihood of the ankylosaurus producing offspring during the simulation.
     *
     * @return The breeding probability as a double value.
     */
    @Override 
    protected double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Get the maximum number of births (litter size) for an Ankylosaurus.
     *
     * @return The maximum number of offspring this Ankylosaurus can produce in one breeding session.
     */
    @Override
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Get the breeding age for this ankylosaurus.
     * The breeding age determines the age at which the animal can start breeding.
     *
     * @return The breeding age of the ankylosaurus.
     */
    @Override
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }
    
    /**
     * Create and return an offspring of the Ankylosaurus at a specified location.
     *
     * @param loc The location where the offspring will be created.
     * @return A new Ankylosaurus instance located at the specified location.
     */
    @Override
    protected Animal createOffspring(Location loc) {
        return new Ankylosaurus(false, loc);
    }

    /**
     * Determines whether the given entity is food for this animal.
     *
     * @param entity The entity to be checked.
     * @return True if the entity is a ripe berry, otherwise false.
     */
    @Override
    protected boolean isFood(Entity entity) {
        return (entity instanceof Berry berry) && berry.getAge() >= berry.getRipeAge();
    }
}
