import java.util.List;

/**
 * Write a description of class Prey here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Prey extends Animal {

    private static final double SLEEP_CHANGE_PROBABILITY = 0.9;

    public Prey(Location location) {
        super(location, 20, 4, 1);
    }

    public Prey(Location location, int sleepHour, int wakeHour, int timeOffset) {
        super(location, sleepHour, wakeHour, timeOffset);
    }
    
    /**
     * This is what the prey does most of the time - it runs
     * around. Sometimes it will breed or die of old age.
     * @param currentField The field occupied.
     * @param nextFieldState The updated field.
     * @param day The day of the new state.
     * @param hour The hour of the day of the new state.
     */
    @Override
    public void act(Field currentField, Field nextFieldState, int day, int hour)
    {
        incrementAge();
        updateSleeping(hour, SLEEP_CHANGE_PROBABILITY);

        if(!sleeping){
            if(isAlive()) {
                List<Location> freeLocations =
                        nextFieldState.getFreeAdjacentLocations(getLocation());
                if(!freeLocations.isEmpty()) {
                    giveBirth(nextFieldState, freeLocations);
                }
                // Try to move into a free location.
                if(! freeLocations.isEmpty()) {
                    Location nextLocation = freeLocations.get(0);
                    setLocation(nextLocation);
                    nextFieldState.placeAnimal(this, nextLocation);
                }
                else {
                    // Overcrowding.
                    setDead();
                }
            }
        }else{
            nextFieldState.placeAnimal(this, getLocation());
        }

    }

    @Override
    public String toString() {
        return getClass() + "{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                '}';
    }
}
