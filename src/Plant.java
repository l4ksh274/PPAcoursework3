/**
 * Abstract class representing a plant in this simulation.
 * Plants may be eaten and seeds allows the plants to regrow in the same position.
 */

public abstract class Plant extends Entity {

    // Location where the plant's seeds will attempt to sprout after it dies.
    protected Location seedSproutLocation;
    // Reference to the field the plant exists in
    protected Field field;

    /**
     * Constructor to initialise a plant at a given location within the field
     * @param location The initial location of the plant.
     * @param field The field where the plant exists.
     */
    public Plant(Location location, Field field) {
        super(location, field);
        this.seedSproutLocation = location;
        this.field = field;
    }

    /*
     * Defines the behaviour of a plant in each step of the stimulation.
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
            if (nextFieldState.getEntityAt(seedSproutLocation) != null) {
                if (rand.nextDouble() <= getSeedSproutProbability()) {
                    Plant young = createOffspring(seedSproutLocation);
                    nextFieldState.placeEntity(young, seedSproutLocation);
                    System.out.println(young + " is respawning" + young.getLocation());
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