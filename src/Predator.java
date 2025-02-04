import java.util.List;

/**
 * Write a description of class Predator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Predator extends Animal
{   

    public Predator(Location location, Field field) {
        super(location, field);
    }


    public Predator(Location location, Field field, int sleepHour, int wakeHour, int timeOffset){
        super(location, field, sleepHour, wakeHour, timeOffset);
    }

    /**
     * Move towards a source of food if found.
     */
    @Override 
    public Location findFood(Field currentField, List<Location> freeLocations) {
        Location nextLocation = findPrey(currentField);
        if(nextLocation == null && ! freeLocations.isEmpty()) {
            // No food found - try to move to a free location.
            nextLocation = freeLocations.remove(0);
        }
        return nextLocation;
    }
    

    @Override
    public String toString() {
        return "Trex{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                ", foodLevel=" + foodLevel +
                '}';
    }
}