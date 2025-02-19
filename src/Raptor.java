/**
 * A simple model of a raptor
 *
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public class Raptor extends Predator
{
    // Characteristics shared by all raptors (class variables)
    // The age to which a raptor can start to breed
    private static final int BREEDING_AGE = 4;
    // The age to which a raptor can live
    private static final int MAX_AGE = 120;
    // The likelihood of a raptor breeding
    private static final double BREEDING_PROBABILITY = 0.44;
    // The maximum number of births
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single dodo
    private static final int DODO_FOOD_VALUE = 40;
    
    /**
     * Create a raptor. A raptor can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * @param randomAge If true, the raptor will have random age and hunger level.
     * @param location The location within the field.
     */
    public Raptor(boolean randomAge, Location location){
        super(location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
        foodLevel = rand.nextInt(DODO_FOOD_VALUE);
    }
    
    /**
     * Gets the maximum age that the raptor can reach.
     *
     * @return the maximum age of the raptor as an integer.
     */
    @Override
    protected int getMaxAge(){
        return MAX_AGE;
    }

    /**
     * Gets the current age of the raptor.
     *
     * @return the age of the raptor as an integer.
     */
    @Override
    protected int getAge() {
        return age;
    }
    
    /**
     * Gets the food value of a specific prey item, in this case, a dodo.
     *
     * @return the food value of a dodo as an integer.
     */
    @Override
    protected int getFoodValue(){
        return DODO_FOOD_VALUE;
    }
    
    /**
     * Gets the breeding probability for the raptor.
     *
     * @return the likelihood of the raptor breeding as a double.
     */
    @Override 
    protected double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }
    
    /**
     * Gets the maximum number of offspring a Raptor can have in one litter.
     *
     * @return the maximum litter size as an integer.
     */
    @Override
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Gets the breeding age of the raptor.
     *
     * @return the minimum age at which the raptor can start breeding.
     */
    @Override
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }
    
    /**
     * Creates an offspring of the current raptor at the specified location.
     *
     * @param loc The location where the offspring will be created.
     * @return A new instance of a Raptor at the specified location.
     */
    @Override
    protected Animal createOffspring(Location loc) {
        return new Raptor(false, loc);
    }
    
    /**
     * Determines if the given entity is considered food for the current entity.
     *
     * @param entity The entity to check.
     * @return True if the entity is an instance of Dodo, false otherwise.
     */
    @Override
    protected boolean isFood(Entity entity) {
        return entity instanceof Dodo;
    }
}
