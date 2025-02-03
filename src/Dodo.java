/**
 * Write a description of class Triceratops here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Dodo extends Prey
{
    // Characteristics shared by all triceratops' 
    // The age at which a triceratops can starto to breed
    private static final int BREEDING_AGE = 5;
    // The age to which a ankylosaurus can live
    private static final int MAX_AGE = 15;
    // The likelihood of a dodo breeding
    private static final double BREEDING_PROBABILITY = 0.85;
    // The maximum number of births
    private static final int MAX_LITTER_SIZE = 6;
    
    /**
     * 
     */
    public Dodo(boolean randomAge, Location location, Field field) {
        super(location, field);
        age = 0;
        if (randomAge) {
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
        return new Dodo(false, loc, field);
    }
}
