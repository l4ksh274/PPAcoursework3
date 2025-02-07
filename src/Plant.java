public abstract class Plant extends Living {

    // Plant is eaten and leftover seeds may cause the plant to grow back again 
    protected Location seedSproutLocation;

    protected Field field;

    public Plant(Location location, Field field) {
        super(location, field);
        this.seedSproutLocation = location;
        this.field = field;
    }

    /*
     * Only original plant can breed
     */
    public void act(Field currentField, Field nextFieldState, int day, int hour) {
        incrementAge();

        // If plant is alive, stays in the same location
        if(isAlive()) {
            nextFieldState.placeLiving(this, this.getLocation());
        }
        else {
            // setDead();
            
            // Seeds have a probability of sprouting after parent plant has died
            if (nextFieldState.getAnimalAt(seedSproutLocation) == null) {
                if (rand.nextDouble() <= getSeedSproutProbability()) {
                    Plant young = createOffspring(seedSproutLocation);
                    nextFieldState.placeLiving(young, seedSproutLocation);
                    System.out.println("New seedling sprouted at " + seedSproutLocation);
                }
            }
        }
    }

    protected abstract double getSeedSproutProbability();

    protected abstract Plant createOffspring(Location seedSproutLocation);
}