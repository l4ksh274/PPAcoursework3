import java.util.*;

/**
 * A base class representing common elements of all animals in the simulation.
 * Such things include aging, moving and breeding for example.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 7.0
 *
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public abstract class Animal extends Entity
{
    // The diseases the animal is infected with
    private Set<Disease> diseases;
    // Probability of the affected entity dying.
    private float mortalityProbability;
    // Probability multiplier of the animal breeding.
    private float breedingProbabilityMultiplier;


    // The animal's food level, which is increased by eating their respective food source.
    protected int foodLevel;
    // The animal's gender.
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
     * @param location The animal's initial location.
     */
    public Animal(Location location)
    {
        super(location);
        this.gender = Gender.randomGender();
        this.sleeping = false;
        this.sleepHour = rand.nextInt(24);
        this.wakeHour = rand.nextInt(24);
        this.timeOffset = rand.nextInt(5);
        diseases = new HashSet<>();
        moveProbability = 1f;
        mortalityProbability = 0;
        breedingProbabilityMultiplier = 1f;
    }

    /**
     * Constructor for objects of class Animal with specific sleep parameters.
     * @param location The animal's location.
     * @param sleepHour The animal's sleeping hour
     * @param wakeHour The animal's waking hour
     * @param timeOffset The animal's deviation from their sleeping parameters
     */
    public Animal(Location location, int sleepHour, int wakeHour, int timeOffset)
    {
        this(location);
        this.sleepHour = sleepHour;
        this.wakeHour = wakeHour;
        this.timeOffset = timeOffset;
    }

    /**
     * Allows the animal to perform its actions such as moving, breeding, and hunting for food
     * based on the current conditions of the field and its state. Also handles sleeping, 
     * aging, hunger, and potential mortality.
     *
     * @param currentField The current state of the field where the animal exists.
     * @param nextFieldState The next state of the field being constructed after this turn.
     * @param day The current day in the simulation.
     * @param hour The current hour in the simulation.
     */
    public void act(Field currentField, Field nextFieldState, int day, int hour)
    {
        checkMortality(nextFieldState);
        updateSleeping(hour, getSleepChangeProbability());
        incrementAge();
        
        if (!sleeping) {
            incrementHunger();
            if(isAlive()) {
                boolean noFogAndCanMove = nextFieldState.getCurrentWeather() != Weather.FOG && rand.nextFloat() < moveProbability;
                boolean fogAndCanMove = nextFieldState.getCurrentWeather() == Weather.FOG && rand.nextFloat() < moveProbability * nextFieldState.getVisibility();

                if (noFogAndCanMove || fogAndCanMove) {
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
     * If a mate is found, attempt to give birth into adjacent free squares.
     * New births will be made into free adjacent locations.
     * @param currentField The current field.
     * @param nextFieldState The state of the next field.
     * @param adjacentLocations The adjacent locations of the animal.
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
     * @param field The current field.
     * @param breedingLocations A list of locations of all animals adjacent to the current animal.
     * @return true if a mate is found.
     */
    protected boolean foundMate(Field field, List<Location> breedingLocations) {
        for (Location location : breedingLocations) {
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
     * Looks for a valid food entity in the adjacent squares.
     * If found, the food is consumed and hunger is replenished.
     * @param field The field currently occupied.
     * @param adjacentLocations A list of adjacent locations to the animal.
     * @return The location of the food found, or null if not found.
     */
    protected Location findFood(Field field, List<Location> adjacentLocations)
    {
        for (Location loc : adjacentLocations) {
            Entity entity = field.getEntityAt(loc);
            if(entity != null && isFood(entity) && entity.isAlive()) {
                entity.setDead();
                foodLevel = getFoodValue();
                return loc;
            }
        }
        return null;
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
     * @return Probability that this animal can breed successfully.
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

    /**
     * Gets the set of all diseases currently affecting the animal.
     *
     * @return A set of diseases that the animal is currently infected with.
     */
    public Set<Disease> getDiseases() {
        return diseases;
    }

    /**
     * Infects the animal with a specified disease by adding it to the set of diseases.
     *
     * @param disease The disease to infect the animal with.
     */
    public void infect(Disease disease) {
        diseases.add(disease);
    }

    /**
     * Increases the mortality probability of the animal by the specified value.
     *
     * @param mortalityRateIncrease The amount to increase the mortality probability.
     */
    public void increaseMortalityRate(float mortalityRateIncrease) {
        this.mortalityProbability += mortalityRateIncrease;
    }

    /**
     * Checks the mortality of the animal based on its mortality probability and the effects 
     * of any diseases it may have. If the animal should die, it is marked as dead. 
     * Additionally, updates the state of any diseases the animal is afflicted with and 
     * applies their effects. Increments the animal's age if it survives.
     *
     * @param nextFieldState The next state of the field being constructed.
     */
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

    /**
     * Adjusts the breeding probability multiplier of the animal by a specific factor.
     * The multiplier is updated by multiplying it with the provided change factor.
     *
     * @param changeProbability A decimal value by which the breeding probability multiplier is adjusted. 
     *                          Values greater than 1 increase the probability, while values less than 1 decrease it.
     */
    public void changeBreedingProbability(float changeProbability) {
        breedingProbabilityMultiplier *= changeProbability;
    }

    /**
     * Gets the breeding probability multiplier of the animal.
     * This multiplier influences the likelihood of successful reproduction.
     *
     * @return The breeding probability multiplier as a float value.
     */
    public float getBreedingProbabilityMultiplier() {
        return breedingProbabilityMultiplier;
    }

    /**
     * Returns the probability that the animal's sleep state will change 
     * based on its sleep and wake parameters.
     *
     * @return A probability value (as a double) representing the likelihood of 
     *         the animal transitioning between sleeping and waking states.
     */
    public abstract double getSleepChangeProbability();
}
