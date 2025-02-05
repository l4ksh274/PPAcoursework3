import java.util.List;

/**
 * Write a description of class Prey here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Prey extends Animal
{   
    public Prey(Location location, Field field) {
        super(location, field);
    }

    public Prey(Location location, Field field, int sleepHour, int wakeHour, int timeOffset) {
        super(location, field, sleepHour, wakeHour, timeOffset);
    }

    /**
     * Move towards a source of food if found.
     */
    @Override 
    public Location findFood(Field currentField, List<Location> freeLocations) {
        Location nextLocation = null;
        if (isHungry()) {
            nextLocation = findPrey(currentField);
            if(nextLocation == null && ! freeLocations.isEmpty()) {
            // No food found - try to move to a free location.
            nextLocation = freeLocations.remove(0);
            }
        }
        // If animal is not hungry, move to any free location
        else {
            if (!freeLocations.isEmpty()) {
                nextLocation = freeLocations.remove(0);
            }
        }
        return nextLocation;
    }

    /**
     * @return true if animal's food level is less than 10
     */
    private boolean isHungry() {
        return foodLevel <= 10;
    }

    @Override
    public String toString() {
        return "Rabbit{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                '}';
    }
}
