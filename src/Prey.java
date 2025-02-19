
/**
 * Abstract class representing prey in this simulation.
 * Prey are animals that are hunted by predators, but also eat plants.
 *
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public abstract class Prey extends Animal {

    /*
     The probability that the Prey  changes sleeping parameters
     to something else within the valid range.
     */
    private static final double SLEEP_CHANGE_PROBABILITY = 0.9;

    /**
     * Constructs a Predator at the given location.
     *
     * @param location The location of the predator within the simulation grid.
     */
    public Prey(Location location) {
        super(location, 20, 4, 1);
    }
    
    public double getSleepChangeProbability() {
        return SLEEP_CHANGE_PROBABILITY;
    }
}
