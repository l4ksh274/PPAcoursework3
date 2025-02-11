
/**
 * Write a description of class Predator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Predator extends Animal
{   
    private static final double SLEEP_CHANGE_PROBABILITY = 0.05;

    public Predator(Location location, Field field) {
        super(location, field);
    }

    public Predator(Location location, Field field, int sleepHour, int wakeHour, int timeOffset){
        super(location, field, sleepHour, wakeHour, timeOffset);
    }

    public double getSleepChangeProbability() {
        return SLEEP_CHANGE_PROBABILITY;
    }
}