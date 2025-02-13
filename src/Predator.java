
/**
 * Write a description of class Predator here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class Predator extends Animal
{   
    private static final double SLEEP_CHANGE_PROBABILITY = 0.05;

    public Predator(Location location) {
        super(location);
    }

    public double getSleepChangeProbability() {
        return SLEEP_CHANGE_PROBABILITY;
    }
}