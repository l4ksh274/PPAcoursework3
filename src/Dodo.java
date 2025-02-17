/**
 * A simple model of a dodo.
 * Dodos age, move, eat conifers and die.
 *
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public class Dodo extends Prey
{
    // Characteristics shared by all dodos.
    // The age at which a dodo can start to to breed.
    private static final int BREEDING_AGE = 4;
    // The age to which a dodo can live.
    private static final int MAX_AGE = 40;
    // The likelihood of a dodo breeding.
    private static final double BREEDING_PROBABILITY = 0.7;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    // The food value of a single Conifer. In effect, this is the
    // number of steps a dodo can go before it has to eat again.
    private static final int CONIFER_FOOD_VALUE = 11;
    
    /**
     * Create a dodo. A dodo can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the dodo will have a random age.
     * @param location The location within the field.
     */
    public Dodo(boolean randomAge, Location location) {
        super(location);
        if (randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
        foodLevel = rand.nextInt(CONIFER_FOOD_VALUE);
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
        return CONIFER_FOOD_VALUE;
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
        return new Dodo(false, loc);
    }

    @Override
    protected boolean isFood(Entity entity) {
        return (entity instanceof Conifer conifer) && conifer.getAge() >= conifer.getRipeAge();
    }
}
