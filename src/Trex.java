/**
 * A simple model of a trex.
 * Trexes age, move, eat Ankylosaurus' and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 7.1
 * 
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public class Trex extends Predator
{
    // Characteristics shared by all trexes (class variables).
    // The age at which a trex can start to breed.
    private static final int BREEDING_AGE = 9;
    // The age to which a trex can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a trex breeding.
    private static final double BREEDING_PROBABILITY = 0.56;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single ankylosaurus. In effect, this is the
    // number of steps a trex can go before it has to eat again.
    private static final int ANKYLOSAURUS_FOOD_VALUE = 25;
    
    /**
     * Create a trex. A trex can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the trex will have random age and hunger level.
     * @param location The location within the field.
     */
    public Trex(boolean randomAge, Location location)
    {
        super(location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
        foodLevel = rand.nextInt(ANKYLOSAURUS_FOOD_VALUE);
    }
    
    /**
     * Gets the maximum possible age a Trex can reach.
     *
     * @return the maximum age as an integer.
     */
    @Override
    protected int getMaxAge(){
        return MAX_AGE;
    }

    /**
     * Gets the age of the current instance.
     *
     * @return the age of the instance as an integer.
     */
    @Override
    protected int getAge() {
        return age;
    }
    
    /**
     * Gets the food value of an Ankylosaurus, which determines the number of steps
     * a Trex can survive without eating after consuming an Ankylosaurus.
     *
     * @return the food value of an Ankylosaurus as an integer.
     */
    @Override
    protected int getFoodValue(){
        return ANKYLOSAURUS_FOOD_VALUE;
    }
    
    /**
     * Gets the probability that the trex will breed.
     *
     * @return the breeding probability as a double.
     */
    @Override 
    protected double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }   
    
    /**
     * Gets the maximum litter size for the predator.
     *
     * @return the maximum number of offspring a predator can produce in a single breeding event.
     */
    @Override
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }
    
    /**
     * Returns the breeding age of the Trex. This is the age at which
     * a Trex can start to breed.
     *
     * @return the minimum age required for the Trex to breed.
     */
    @Override
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }
    
    /**
     * Creates an offspring Trex at the specified location.
     *
     * @param loc The location where the offspring Trex will be created.
     * @return A new Trex instance initialized as an offspring.
     */
    @Override
    protected Animal createOffspring(Location loc) {
        return new Trex(false, loc);
    }
    
    /**
     * Determines whether a given entity is considered food for the predator.
     *
     * @param entity The entity to check.
     * @return true if the specified entity is an Ankylosaurus, false otherwise.
     */
    @Override
    protected boolean isFood(Entity entity) {
        return entity instanceof Ankylosaurus;
    }
}
