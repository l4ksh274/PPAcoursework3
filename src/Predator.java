
/**
 * Abstract class representing predators in this simulation.
 * Predators are animals that hunt other animals for food to survive.
 * 
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public abstract class Predator extends Animal
{   
    /*
     The probability that the predator changes sleeping parameters
     to something else within the valid range.
     */
    private static final double SLEEP_CHANGE_PROBABILITY = 0.05;

    /**
     * Constructs a Predator at the given location.
     *
     * @param location The location of the predator within the simulation grid.
     */
    public Predator(Location location) {
        super(location);
    }

    /**
     * Retrieves the probability of a predator's sleep state changing.
     *
     * @return the probability of sleep change as a double.
     */
    public double getSleepChangeProbability() {
        return SLEEP_CHANGE_PROBABILITY;
    }
}