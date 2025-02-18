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
    private static final double BREEDING_PROBABILITY = 0.25;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single ankylosaurus. In effect, this is the
    // number of steps a trex can go before it has to eat again.
    private static final int ANKYLOSAURUS_FOOD_VALUE = 20;
    
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
    
    @Override
    protected int getMaxAge(){
        return MAX_AGE;
    }

    @Override
    protected int getAge() {
        return age;
    }
    
    @Override
    protected int getFoodValue(){
        return ANKYLOSAURUS_FOOD_VALUE;
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
        return new Trex(false, loc);
    }
    
    @Override
    protected boolean isFood(Entity entity) {
        return entity instanceof Ankylosaurus;
    }
}
