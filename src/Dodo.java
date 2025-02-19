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
    private static final int BREEDING_AGE = 3;
    // The age to which a ankylosaurus can live
    private static final int MAX_AGE = 80;
    // The likelihood of a dodo breeding
    private static final double BREEDING_PROBABILITY = 0.9;
    // The maximum number of births
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single Conifer. In effect, this is the
    // number of steps a dodo can go before it has to eat again.
    private static final int CONIFER_FOOD_VALUE = 30;
    
    /**
     * 
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
