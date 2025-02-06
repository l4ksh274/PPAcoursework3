/**
 * Write a description of class Raptor here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Raptor extends Predator
{
    // Characteristics shared by all raptors (class variables)
    // The age to which a raptor can start to breed
    private static final int BREEDING_AGE = 25;
    // The age to which a raptor can live
    private static final int MAX_AGE = 50;
    // The likelihood of a raptor breeding
    private static final double BREEDING_PROBABILITY = 0.20;
    // The maximum number of births
    private static final int MAX_LITTER_SIZE = 3;
    // The food value of a single dodo
    private static final int DODO_FOOD_VALUE = 10;
    
    public Raptor(boolean randomAge, Location location, Field field){
        super(location, field);
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
        return new Raptor(false, loc, field);
    }
    
    @Override
    protected boolean isFood(Living living) {
        if(living instanceof Dodo) {
            return true;
        }
        else {
            return false;
        }
    }
}
