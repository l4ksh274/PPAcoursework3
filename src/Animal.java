import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Common elements of all animals in the simulation
 *
 * @author David J. Barnes and Michael Kölling
 * @version 7.0
 */
public abstract class Animal extends Entity
{
    // The diseases the animal is infected with
    private List<Disease> diseases;
    // Probability of the affected entity dying.
    private float mortalityProbability;
    // Probability of the entity breeding.
    private float breedingProbabilityMultiplier;


    // The predator's food level, which is increased by eating their prey.
    protected int foodLevel;
    // The animal's gender
    protected Gender gender;

    // The base hour that the animal will go to sleep at.
    protected int sleepHour;
    // The base hour that the animal wakes up at.
    protected int wakeHour;
    // The maximum deviation in hours for the sleeping parameters.
    protected int timeOffset;
    // Whether the animal is sleeping or not.
    protected boolean sleeping;
    // The probability that the animal will move
    protected float moveProbability;

    /**
     * Constructor for objects of class Animal. Randomly assigns sleeping parameters.
     * @param location The animal's location.
     */
    public Animal(Location location)
    {
        super(location);
        this.gender = Gender.randomGender();
        this.sleeping = false;
        this.sleepHour = rand.nextInt(24);
        this.wakeHour = rand.nextInt(24);
        this.timeOffset = rand.nextInt(5);
        diseases = new ArrayList<>();
        moveProbability = 1f;
        mortalityProbability = 0;
        breedingProbabilityMultiplier = 1f;
    }

    /**
     * Constructor for objects of class Animal.
     * @param location The animal's location.
     * @param sleepHour The animal's sleeping hour
     * @param wakeHour The animal's waking hour
     * @param timeOffset The animal's deviation from their sleeping parameters
     */
    public Animal(Location location, int sleepHour, int wakeHour, int timeOffset)
    {
        this(location);
        this.gender = Gender.randomGender();
        this.sleepHour = sleepHour;
        this.wakeHour = wakeHour;
        this.timeOffset = timeOffset;
    }

    /**
     * Perform this animal's actions for one step:
     * increment age / hunger, possibly breed, move or die of overcrowding
     * @param currentField The current state of the field.
     * @param nextFieldState The new state being built.
     * @param day The day of the new state.
     * @param hour The hour of the day of the new state.
     */
    public void act(Field currentField, Field nextFieldState, int day, int hour)
    {
        checkMortality(nextFieldState);
        updateSleeping(hour, getSleepChangeProbability());
        incrementAge();
        
        if (!sleeping) {
            incrementHunger();
            if(isAlive()) {
                if (rand.nextFloat() < moveProbability) {
                    List<Location> adjacentLocations = nextFieldState.getAdjacentLocations(getLocation());
                    List<Location> freeLocations = nextFieldState.getFreeAdjacentLocations(getLocation());

                    // There is a free location and the random number generator falls within the breeding chance.
                    if (!freeLocations.isEmpty() && rand.nextFloat() < getBreedingProbabilityMultiplier()) {
                        giveBirth(currentField, nextFieldState, adjacentLocations, freeLocations);
                    }
            
                    // Move towards a source of food if found.
                    Location nextLocation = findFood(currentField, adjacentLocations);
                    if (nextLocation == null && !freeLocations.isEmpty()) {
                        // No food found - try to move to a free location.
                        nextLocation = freeLocations.remove(0);
                    }
            
                    // See if it was possible to move.
                    if(nextLocation != null) {
                        setLocation(nextLocation);
                        nextFieldState.placeEntity(this, nextLocation);
                    }
                    else {
                        // Overcrowding.
                        setDead();
                    }
                }
                else {
                    nextFieldState.placeEntity(this, getLocation());
                }
            }
        }   else {
            nextFieldState.placeEntity(this, getLocation());
        }
    }

    /**
     * Increment hunger by 1. The animal may die if their hunger falls below 0.
     */
    protected void incrementHunger()
    {
        foodLevel--;
        if(foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * If a mate is found, attempt to give birth into adjacent free squares
     * New births will be made into free adjacent locations.
     * @param freeLocations The locations that are free in the current field.
     */
    protected void giveBirth(Field currentField, Field nextFieldState, List<Location> adjacentLocations, List<Location> freeLocations)
    {
        List<Location> breedingLocations = new ArrayList<>();
        for (Location breedingLocation : adjacentLocations) {
            if (nextFieldState.getEntityAt(breedingLocation) != null) {
                breedingLocations.add(breedingLocation);
            }
        }

        if (!foundMate(currentField, breedingLocations)) {
            return;
        }

        int births = breed();
        if(births > 0) {
            for (int b = 0; b < births && !freeLocations.isEmpty(); b++){
                Location loc = freeLocations.remove(0);
                Animal young = createOffspring(loc);
                nextFieldState.placeEntity(young, loc);
            }
        }
    }

    /**
     * Check if there are any animals of the opposite gender amongst the adjacent squares.
     * If not, the animal can't breed.
     * @return
     */
    protected boolean foundMate(Field field, List<Location> breedingLocations) {
        Iterator<Location> iterator = breedingLocations.iterator();

        while (iterator.hasNext()){
            Location location = iterator.next();
            Entity entity = field.getEntityAt(location);

            if (entity instanceof Animal other) {

                if (other.isAlive() && this.getClass() == entity.getClass() && this.gender != other.getGender()) {
                    return true;
                } 
            }
        }
        return false;
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed()
    {
        int births;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        else {
            births = 0;
        }
        return births;
    }

    /**
     * Check if this animal is old enough to breed.
     * @return true if the rabbit can breed, false otherwise.
     */
    protected boolean canBreed()
    {
        return age >= getBreedingAge();
    }

    /**
     * Look for a valid food entity in the adjacent squares. (prey or plant)
     * If found, eat animal by setting it to dead and update the animal's food level.
     * @param field The field currently occupied.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood(Field field, List<Location> adjacentLocations)
    {
        Iterator<Location> it = adjacentLocations.iterator();
        Location foodLocation = null;
        while(foodLocation == null && it.hasNext()) {
            Location loc = it.next();
            Entity entity = field.getEntityAt(loc);
            if(entity != null && isFood(entity)) {
                if(entity.isAlive()) {
                    entity.setDead();
                    foodLevel = getFoodValue();
                    foodLocation = loc;
                }
            }
        }
        return foodLocation;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "age=" + age +
                ", alive=" + isAlive() +
                ", location=" + getLocation() +
                ", foodLevel=" + foodLevel +
                '}';
    }

    /**
     * @return Proability that this animal can breed successfully.
     */
    protected abstract double getBreedingProbability();

    /**
     * @return Maximum number of offspring in a single birth event.
     */
    protected abstract int getMaxLitterSize();

    /**
     * @return Minumum age for animal to start breeding.
     */
    protected abstract int getBreedingAge();

    /**
     * @param loc The location for the offspring to be born at.
     * @return A new instance of the animal as an offspring at the given location.
     */
    protected abstract Animal createOffspring(Location loc);

    /**
     * @return How much hunger is restored when this animal eats its food.
     */
    protected abstract int getFoodValue();
    
    /**
     * @return true if the given entity is valid food for this animal, else false.
     */
    protected abstract boolean isFood(Entity entity);

    /**
     * @return The Gender (MALE/FEMALE) of this animal.
     */
    protected Gender getGender() {
        return gender;
    }

    /**
     * Gets the base sleep hour of the animal.
     * @return  the base sleep hour
     */

    public int getSleepHour() {
        return sleepHour;
    }

    /**
     * Gets the base wake up hour of the animal.
     * @return  the base wake up hour
     */

    public int getWakeHour() {
        return wakeHour;
    }

    /**
     * Checks whether the animal is sleeping or not.
     * @return  Whether the animal is sleeping.
     */

    public boolean isSleeping() {
        return sleeping;
    }

    /**
     * If the hour is on the sleeping or waking boundary, the animal with wake or sleep 100% of the time.
     * Otherwise, if the hour is within an allowed range to trigger an event,
     * there is a possibility that the event is triggered, specified by the SLEEP_CHANGE_PROBABILITY.
     * @param hour The hour of the field
     * @param SLEEP_CHANGE_PROBABILITY Decimal probability of the animal's sleep parameters changing within the allowed range.
     */

    protected void updateSleeping(int hour, double SLEEP_CHANGE_PROBABILITY){

        // If the sleep parameters should change
        boolean sleepDeviation = rand.nextDouble(1) < SLEEP_CHANGE_PROBABILITY;

        // If the hour is the same as the high end of the allowed sleep boundary.
        if ((this.sleepHour + this.timeOffset) == hour){
            this.sleeping = true;
        }
        // Else if hour is in the allowed sleeping range and a 25% probability roll is achieved.
        else if (sleepDeviation && (sleepHour + this.timeOffset) >= hour && (sleepHour -  this.timeOffset) <= hour){
            this.sleeping = true;
        }
        // If the hour is the same as the high end of the allowed wakeup boundary.
        if (this.wakeHour + this.timeOffset == hour){
            this.sleeping = false;
        }
        // Else if hour is in the allowed wakeup range and a 25% probability roll is achieved.
        else if (sleepDeviation && (wakeHour + this.timeOffset) >= hour && (wakeHour -  this.timeOffset) <= hour){
            this.sleeping = false;
        }
    }

    /**
     * Multiplies the move probability by a decimal
     * @param moveProbability the decimal you want to multiply the move probability by.
     */

    public void modifyMoveProbability(float moveProbability) {
        this.moveProbability *= moveProbability;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void infect(Disease disease) {
        if (!diseases.contains(disease)){
            diseases.add(disease);
        }
    }

    public float getMortalityProbability() {
        return mortalityProbability;
    }

    public void increaseMortalityRate(float mortalityRateIncrease) {
        this.mortalityProbability += mortalityRateIncrease;
    }

    protected void checkMortality(Field nextFieldState){
        if (rand.nextFloat() < mortalityProbability){
            setDead();
            return;
        }
        Iterator<Disease> diseaseIterator = getDiseases().iterator();
        while (diseaseIterator.hasNext()){
            Disease disease = diseaseIterator.next();
            disease.incrementStepsElapsed();
            if (disease.getStepsElapsed() == disease.getDurationInSteps()){
                diseaseIterator.remove();
            }else{
                for (Symptoms symptom : disease.getSymptoms()){
                    symptom.applyAffect(this, nextFieldState);
                }
            }
        }
        incrementAge();
    }

    public void changeBreedingProbability(float changeProbability) {
        breedingProbabilityMultiplier *= changeProbability;
    }

    public float getBreedingProbabilityMultiplier() {
        return breedingProbabilityMultiplier;
    }

    public abstract double getSleepChangeProbability();
}
