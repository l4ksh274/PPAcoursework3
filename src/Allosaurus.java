/**
 * A simple model of an allosaurus.
 * Allosaurus' age, move, eat Ankylosaurus' and die
 *
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public class Allosaurus extends Predator
{
    // Characteristics shared by all allosaurus'
    // The age at which an allosaurus can start to breed.
    private static final int BREEDING_AGE = 6;
    // The age to which an allosaurus can live.
    private static final int MAX_AGE = 160;
    // The likelihood of an allosaurus breeding.
    private static final double BREEDING_PROBABILITY = 0.22;
    // The maximum number of births
    private static final int MAX_LITTER_SIZE = 2;
    // The food value of a single ankylosaurus. In effect, this is the
    // number of steps an allosaurus can go before it has to eat again.
    private static final int ANKYLOSAURUS_FOOD_VALUE = 25;
    
    /**
     * Create an allosaurus. An allosaurus can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the allosaurus will have random age and hunger level.
     * @param location The location within the field.
     */
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
        return new Allosaurus(false, loc);
    }
    
    @Override
    protected boolean isFood(Entity entity) {
        return entity instanceof Ankylosaurus;
    }
}
