
/**
 * Abstract class representing prey in this simulation.
 * Prey are animals that are hunted by predators, but also eat plants.
 *
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public abstract class Prey extends Animal {

    private static final double SLEEP_CHANGE_PROBABILITY = 0.9;

    /**
     * Constructor for prey class.
     * Initialises the prey at a specific location.
     * @param location The initial location of the prey.
     */
    public Prey(Location location) {
        super(location, 20, 4, 1);
    }
    
    public double getSleepChangeProbability() {
        return SLEEP_CHANGE_PROBABILITY;
    }
}
