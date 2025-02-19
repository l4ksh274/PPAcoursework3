/**
 * A simple model of a dodo.
 * Dodos age, move, eat conifers and die.
 *
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public class Dodo extends Prey
{
    // Characteristics shared by all dodos
    // The age at which a dodo can start to breed
    private static final int BREEDING_AGE = 3;
    // The age to which a dodo can live
    private static final int MAX_AGE = 80;
    // The likelihood of a dodo breeding
    private static final double BREEDING_PROBABILITY = 0.9;
    // The maximum number of births
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single Conifer. In effect, this is the
    // number of steps a dodo can go before it has to eat again.
    private static final int CONIFER_FOOD_VALUE = 30;
    
    /**
     * Create a dodo. A dodo can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     *
     * @param randomAge If true, the dodo will have a random age.
     * @param location The location within the field.
     */
    public Dodo(boolean randomAge, Location location) {
        super(location);
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
        foodLevel = rand.nextInt(CONIFER_FOOD_VALUE);
    }
    
    /**
     * Returns the maximum age that a dodo can live.
     *
     * @return The maximum age of the dodo.
     */
    @Override
    protected int getMaxAge(){
        return MAX_AGE;
    }

    /**
     * Gets the current age of the dodo.
     *
     * @return the age of the dodo.
     */
    @Override
    protected int getAge() {
        return age;
    }

    /**
     * Get the food value associated with consuming a conifer.
     * This determines how many steps a dodo can survive without
     * eating again after consuming a conifer.
     *
     * @return The food value of a single conifer.
     */
    @Override
    protected int getFoodValue() {
        return CONIFER_FOOD_VALUE;
    }
    
    /**
     * Gets the breeding probability for the Dodo.
     *
     * @return The probability that a Dodo will successfully breed.
     */
    @Override 
    protected double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Get the maximum number of offspring a dodo can have in a single litter.
     *
     * @return The maximum litter size for a dodo.
     */
    @Override
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Gets the age at which this dodo can start to breed.
     *
     * @return The minimum age required for breeding.
     */
    @Override
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }
    
    /**
     * Create a new offspring of the dodo species at the specified location.
     *
     * @param loc The location where the offspring will be created.
     * @return A new Dodo instance representing the offspring.
     */
    @Override
    protected Animal createOffspring(Location loc) {
        return new Dodo(false, loc);
    }

    /**
     * Determines if the given entity is considered food for the dodo.
     * For an entity to be food, it must be a {@link Conifer} and must have
     * reached or surpassed its ripe age.
     *
     * @param entity The entity to evaluate as potential food.
     * @return true if the entity is a ripe conifer, false otherwise.
     */
    @Override
    protected boolean isFood(Entity entity) {
        return (entity instanceof Conifer conifer) && conifer.getAge() >= conifer.getRipeAge();
    }
}
