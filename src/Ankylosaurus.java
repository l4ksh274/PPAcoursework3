/**
 * A simple model of a ankylosaurus.
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
    // The age at which an ankylosaurus can start to breed.
    private static final int BREEDING_AGE = 3;
    // The age to which a ankylosaurus can live.
    private static final int MAX_AGE = 25;
    // The likelihood of a ankylosaurus breeding.
    private static final double BREEDING_PROBABILITY = 0.7;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // The food value of a single Berry. In effect, this is the
    // number of steps a trex can go before it has to eat again.
    private static final int BERRY_FOOD_VALUE = 12;
    
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
    
    @Override
    protected int getMaxAge(){
        return MAX_AGE;
    }

    @Override
    protected int getAge() {
        return age;
    }

    @Override
    protected int getFoodValue() {
        return BERRY_FOOD_VALUE;
    }
    
    @Override 
    protected double getBreedingProbability(){
        return BREEDING_PROBABILITY;
    }
    
    @Override
    protected int getMaxLitterSize(){
        return MAX_LITTER_SIZE;
    }
    
    @Override
    protected int getBreedingAge(){
        return BREEDING_AGE;
    }
    
    @Override
    protected Animal createOffspring(Location loc) {
        return new Ankylosaurus(false, loc);
    }

    @Override
    protected boolean isFood(Entity entity) {
        return (entity instanceof Berry berry) && berry.getAge() >= berry.getRipeAge();
    }
}
