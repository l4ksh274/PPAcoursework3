
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
}
