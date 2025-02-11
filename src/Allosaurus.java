/**
 * Implementation of the Allosaurus dinosaur with its unique characteristics
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Allosaurus extends Predator
{
    // Characteristics shared by all allosaurus'
    // The age at which an allosaurus can start to breed.
    private static final int BREEDING_AGE = 13;
    // The age of which an allosaurus can live
    private static final int MAX_AGE = 160;
    // The likelihood of an allosaurus breeding 
    private static final double BREEDING_PROBABILITY = 0.18;
    // The maximum number of births
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single ankylosaurus. In effect, this is the
    // number of steps a trex can go before it has to eat again.
    private static final int ANKYLOSAURUS_FOOD_VALUE = 9;
    
    public Allosaurus(boolean randomAge, Location location) {
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
        return new Allosaurus(false, loc);
    }
    
    @Override
    protected boolean isPrey(Animal animal) {
        return animal instanceof Ankylosaurus;
    }
}
