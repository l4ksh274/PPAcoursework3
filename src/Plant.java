/**
 * Abstract class representing common elements of all plants in this simulation.
 * Plants may be eaten and seeds allows the plants to regrow in the same position.
 * 
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */

public abstract class Plant extends Entity {

    // Location where the plant's seeds will attempt to sprout after it dies.
    protected Location seedSproutLocation;

    /**
     * Constructor to initialise a plant at a given location within the field
     * @param location The initial location of the plant.
     */
    public Plant(Location location) {
        super(location);
        this.seedSproutLocation = location;
    }

    /*
     * Defines the behaviour of a plant in each step of the stimulation:
     * increment age, have a chance of reproducing in the same spot if dies.
     * @param currentField The current state of the field.
     * @param nextFieldState The field state for the next simulation step.
     * @param day The current day in the simulation.
     * @param hour The current hour in the simulation.
     */
    public void act(Field currentField, Field nextFieldState, int day, int hour) {
        incrementAge();

        // If plant is alive, stays in the same location.
        if(isAlive()) {
            nextFieldState.placeEntity(this, this.getLocation());
        }
        else {
            // Seeds have a probability of sprouting after parent plant has died
            if (nextFieldState.getEntityAt(seedSproutLocation) == null) {
                if (rand.nextDouble() <= getSeedSproutProbability()) {
                    Plant young = createOffspring(seedSproutLocation);
                    nextFieldState.placeEntity(young, seedSproutLocation);
                }
            }
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                '}';
    }

    /**
     * @return The probability of a seed sprouting when a plant dies.
     */
    protected abstract double getSeedSproutProbability();

    /**
     * Creates a new instance of a plant at a given location.
     * @param seedSproutLocation The location where the offspring should grow.
     * @return A new instance of a plant.
     */
    protected abstract Plant createOffspring(Location seedSproutLocation);
}