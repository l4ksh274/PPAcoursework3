import java.util.List;
import java.util.Iterator;

/**
 * Write a description of class Predator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Predator extends Animal
{   
    // The predator's food level, which is increased by eating their prey.
    protected int foodLevel;
    private static final double SLEEP_CHANGE_PROBABILITY = 0.05;

    public Predator(Location location) {
        super(location);
    }


    public Predator(Location location, int sleepHour, int wakeHour, int timeOffset){
        super(location, sleepHour, wakeHour, timeOffset);
    }

    /**
     * This is what the trex does most of the time: it hunts for
     * ankylosaurus'. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param currentField The current state of the field.
     * @param nextFieldState The new state being built.
     * @param day The day of the new state.
     * @param hour The hour of the day of the new state.
     */
    @Override
    public void act(Field currentField, Field nextFieldState, int day, int hour)
    {
        checkMortality(nextFieldState);
        updateSleeping(hour, SLEEP_CHANGE_PROBABILITY);
        if (!sleeping){
            incrementHunger();
            if(isAlive()) {
                if (rand.nextFloat() < moveProbability){
                    List<Location> freeLocations =
                            nextFieldState.getFreeAdjacentLocations(getLocation());
                    // There is a free location and the random number generator falls within the breeding chance.
                    if(!freeLocations.isEmpty() && rand.nextFloat() < getBreedingProbabilityMulitplier()) {
                        giveBirth(nextFieldState, freeLocations);
                    }
                    // Move towards a source of food if found.
                    Location nextLocation = findFood(currentField);
                    if(nextLocation == null && ! freeLocations.isEmpty()) {
                        // No food found - try to move to a free location.
                        nextLocation = freeLocations.remove(0);
                    }
                    // See if it was possible to move.
                    if(nextLocation != null) {
                        setLocation(nextLocation);
                        nextFieldState.placeAnimal(this, nextLocation);
                    }
                    else {
                        // Overcrowding.
                        setDead();
                    }
                }else{
                    nextFieldState.placeAnimal(this, getLocation());
                }

            }
        }else{
            nextFieldState.placeAnimal(this, getLocation());
        }

    }
    
    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                ", foodLevel=" + foodLevel +
                '}';
    }
    
    /**
     * Make this trex more hungry. This could result in the trex's death.
     */
    private void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for ankylosaurus' adjacent to the current location.
     * Only the first live ankylosaurus is eaten.
     * @param field The field currently occupied.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood(Field field)
    {
        List<Location> adjacent = field.getAdjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        Location foodLocation = null;
        while(foodLocation == null && it.hasNext()) {
            Location loc = it.next();
            Animal animal = field.getAnimalAt(loc);
            if(animal != null && isPrey(animal)) {
                if(animal.isAlive()) {
                    animal.setDead();
                    foodLevel = getFoodValue();
                    foodLocation = loc;
                }
            }
        }
        return foodLocation;
    }
    
    protected abstract int getFoodValue();
    
    protected abstract boolean isPrey(Animal animal);
}