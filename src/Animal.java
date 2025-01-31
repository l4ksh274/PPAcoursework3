import java.util.Random;

/**
 * Common elements of foxes and rabbits.
 *
 * @author David J. Barnes and Michael Kölling
 * @version 7.0
 */
public abstract class Animal
{
    // TODO Unlikely that this class needs updates for the sleeping functionality.

    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's position.
    private Location location;

    // The base hour that the animal will go to sleep at.
    protected int sleepHour;
    // The base hour that the animal wakes up at.
    protected int wakeHour;
    // The maximum deviation in hours for the sleeping parameters.
    protected int timeOffset;
    // Whether the animal is sleeping or not.
    protected boolean sleeping;
    // Random class
    protected static Random rand = Randomizer.getRandom();

    /**
     * Constructor for objects of class Animal. Randomly assigns sleeping parameters.
     * @param location The animal's location.
     */
    public Animal(Location location)
    {
        this.alive = true;
        this.location = location;
        this.sleepHour = rand.nextInt(24);
        this.wakeHour = rand.nextInt(24);
        this.timeOffset = rand.nextInt(5);
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
        this.sleepHour = sleepHour;
        this.wakeHour = wakeHour;
        this.timeOffset = timeOffset;
    }
    
    /**
     * Act.
     * @param currentField The current state of the field.
     * @param nextFieldState The new state being built.
     * @param day The day of the new state.
     * @param hour The hour of the day of the new state.
     */
    abstract public void act(Field currentField, Field nextFieldState, int day, int hour);
    
    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     */
    protected void setDead()
    {
        alive = false;
        location = null;
    }
    
    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    public Location getLocation()
    {
        return location;
    }
    
    /**
     * Set the animal's location.
     * @param location The new location.
     */
    protected void setLocation(Location location)
    {
        this.location = location;
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

}
