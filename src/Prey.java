
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
    
    public double getSleepChangeProbability() {
        return SLEEP_CHANGE_PROBABILITY;
    }
}
