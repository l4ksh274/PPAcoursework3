
/**
 * Abstract class representing predators in this simulation.
 * Predators are animals that hunt other animals for food to survive.
 * 
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public abstract class Predator extends Animal
{   
    private static final double SLEEP_CHANGE_PROBABILITY = 0.05;

    /**
     * Constructor for predator class.
     * Initialises the predator at a specific location.
     * @param location The initial location of the predator.
     */
    public Predator(Location location) {
        super(location);
    }

    public double getSleepChangeProbability() {
        return SLEEP_CHANGE_PROBABILITY;
    }
}