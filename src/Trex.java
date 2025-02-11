/**
 * A simple model of a trex.
 * Trexes age, move, eat Trexs, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 7.1
 */
public class Trex extends Predator
{
    // Characteristics shared by all trexes (class variables).
    // The age at which a trex can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a trex can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a trex breeding.
    private static final double BREEDING_PROBABILITY = 0.20;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single ankylosaurus. In effect, this is the
    // number of steps a trex can go before it has to eat again.
    private static final int ANKYLOSAURUS_FOOD_VALUE = 9;
    
    // Individual characteristics (instance fields).
    
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
    protected boolean isPrey(Animal animal) {
        return animal instanceof Ankylosaurus;
    }
}
