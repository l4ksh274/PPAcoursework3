import java.util.List;
import java.util.Iterator;

/**
 * Common elements of foxes and rabbits.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 7.0
 */
public abstract class Animal extends Living
{
    // The predator's food level, which is increased by eating their prey.
    protected int foodLevel;
    // The animal's gender
    protected Gender gender;

    protected Field field;

    // The base hour that the animal will go to sleep at.
    protected int sleepHour;
    // The base hour that the animal wakes up at.
    protected int wakeHour;
    // The maximum deviation in hours for the sleeping parameters.
    protected int timeOffset;
    // Whether the animal is sleeping or not.
    protected boolean sleeping;

    /**
     * Constructor for objects of class Animal. Randomly assigns sleeping parameters.
     * @param location The animal's location.
     */
    public Animal(Location location, Field field)
    {
        super(location, field);
        this.gender = Gender.randomGender();
        this.field = field;
        this.sleepHour = rand.nextInt(24);
        this.wakeHour = rand.nextInt(24);
        this.timeOffset = rand.nextInt(5);
    }

    /**
     * Constructor for objects of class Animal.
     * @param location The animal's location.
     * @param sleepHour The animal's sleeping hour
     * @param wakeHour The animal's waking hour
     * @param timeOffset The animal's deviation from their sleeping parameters
     */
    public Animal(Location location, Field field, int sleepHour, int wakeHour, int timeOffset)
    {
        this(location, field);
        this.sleepHour = sleepHour;
        this.wakeHour = wakeHour;
        this.timeOffset = timeOffset;
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
    public void act(Field currentField, Field nextFieldState, int day, int hour)
    {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            List<Location> freeLocations =
                    nextFieldState.getFreeAdjacentLocations(getLocation());
            if(! freeLocations.isEmpty()) {
                giveBirth(nextFieldState, freeLocations);
            }
            
            // Move towards a source of food if found.
            Location nextLocation = findFood(currentField, freeLocations);

            // See if it was possible to move.
            if(nextLocation != null) {
                setLocation(nextLocation);
                nextFieldState.placeLiving(this, nextLocation);
            }
            else {
                // Overcrowding.
                setDead();
            }
        }
    }

    /**
     * Look for ankylosaurus' adjacent to the current location.
     * Only the first live ankylosaurus is eaten.
     * @param field The field currently occupied.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findPrey(Field field)
    {
        List<Location> adjacent = field.getAdjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        Location foodLocation = null;
        while(foodLocation == null && it.hasNext()) {
            Location loc = it.next();
            Living living = field.getAnimalAt(loc);
            if(living != null && isFood(living)) {
                if(living.isAlive()) {
                    living.setDead();
                    foodLevel = getFoodValue();
                    foodLocation = loc;
                }
            }
        }
        return foodLocation;
    }

    /**
     * Make this trex more hungry. This could result in the trex's death.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Check whether this trex is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param freeLocations The locations that are free in the current field.
     */
    protected void giveBirth(Field nextFieldState, List<Location> freeLocations)
    {
        // New trexes are born into adjacent locations.
        // Get a list of adjacent free locations.
        if (!foundMate(freeLocations)) {
            return;
        }

        int births = breed();
        if(births > 0) {
            for (int b = 0; b < births && ! freeLocations.isEmpty(); b++) {
                Location loc = freeLocations.remove(0);
                Animal young = createOffspring(loc);
                nextFieldState.placeLiving(young, loc);
            }
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        else {
            births = 0;
        }
        return births;
    }

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
    }

    /**
     * Checks all freeLocations to see if there are any animals of the opposite gender and removes animals of the same gender
     * @return
     */
    protected boolean foundMate(List<Location> freeLocations) {
        Iterator<Location> iterator = freeLocations.iterator();

        while (iterator.hasNext()){
            Location location = iterator.next();
            Living being = field.getAnimalAt(location);

            if (being == null) {
                iterator.remove();
                continue;
            }

            if (being instanceof Animal) {
                Animal animal = (Animal) being;
                
                if (gender == Gender.MALE && animal.getGender() == Gender.FEMALE) {
                    continue;
                } 
                else {
                    iterator.remove();
                }
            }
        }
        if (freeLocations.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }

    protected abstract double getBreedingProbability();

    protected abstract int getMaxLitterSize();

    protected abstract int getBreedingAge();

    protected abstract Animal createOffspring(Location loc);

    protected abstract int getFoodValue();
    
    protected abstract boolean isFood(Living living);

    protected Gender getGender() {
        return gender;
    }
    
    protected abstract Location findFood(Field currentField, List<Location> freeLocations);

    /**
     * Gets the base sleep hour of the animal.
     * @return  the base sleep hour
     */

    public int getSleepHour() {
        return sleepHour;
    }

    /**
     * Gets the base wake up hour of the animal.
     * @return  the base wake up hour
     */

    public int getWakeHour() {
        return wakeHour;
    }

    /**
     * Checks whether the animal is sleeping or not.
     * @return  Whether the animal is sleeping.
     */

    public boolean isSleeping() {
        return sleeping;
    }

    /**
     * If the hour is on the sleeping or waking boundary, the animal with wake or sleep 100% of the time.
     * Otherwise, if the hour is within an allowed range to trigger an event,
     * there is a possibility that the event is triggered, specified by the SLEEP_CHANGE_PROBABILITY.
     * @param hour The hour of the field
     * @param SLEEP_CHANGE_PROBABILITY Decimal probability of the animal's sleep parameters changing within the allowed range.
     */

    protected void updateSleeping(int hour, double SLEEP_CHANGE_PROBABILITY){
        // If the sleep parameters should change
        boolean sleepDeviation = rand.nextDouble(1) < SLEEP_CHANGE_PROBABILITY;

        // If the hour is the same as the high end of the allowed sleep boundary.
        if ((this.sleepHour + this.timeOffset) == hour){
            this.sleeping = true;
        }
        // Else if hour is in the allowed sleeping range and a 25% probability roll is achieved.
        else if (sleepDeviation && (sleepHour + this.timeOffset) >= hour && (sleepHour -  this.timeOffset) <= hour){
            this.sleeping = true;
        }
        // If the hour is the same as the high end of the allowed wakeup boundary.
        if (this.wakeHour + this.timeOffset == hour){
            this.sleeping = false;
        }
        // Else if hour is in the allowed wakeup range and a 25% probability roll is achieved.
        else if (sleepDeviation && (wakeHour + this.timeOffset) >= hour && (wakeHour -  this.timeOffset) <= hour){
            this.sleeping = false;
        }
    }

}
