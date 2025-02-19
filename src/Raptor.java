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
        return DODO_FOOD_VALUE;
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
        return new Raptor(false, loc);
    }
    
    @Override
    protected boolean isFood(Entity entity) {
        return entity instanceof Dodo;
    }
}
