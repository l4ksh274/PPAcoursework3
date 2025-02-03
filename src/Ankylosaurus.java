/**
 * A simple model of a ankylosaurus.
 * Ankylosaurus' age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 7.1
 */
public class Ankylosaurus extends Prey
{
    // Characteristics shared by all ankylosaurus' (class variables).
    // The age at which a ankylosaurus can start to breed.
    private static final int BREEDING_AGE = 8;
    // The age to which a ankylosaurus can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a ankylosaurus breeding.
    private static final double BREEDING_PROBABILITY = 0.75;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    
    // Individual characteristics (instance fields).
    
    /**
     * Create a new ankylosaurus. A ankylosaurus may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the ankylosaurus will have a random age.
     * @param location The location within the field.
     */
    public Ankylosaurus(boolean randomAge, Location location, Field field)
    {
        super(location, field);
        age = 0;
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
    }
    
    @Override
    protected int getMaxAge(){
        return MAX_AGE;
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
        return new Ankylosaurus(false, loc, field);
    }
}
