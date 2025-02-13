import java.util.Random;

public abstract class Entity {
    
    // Whether the thing is alive or not.
    protected boolean alive;
    // The living thing's location.
    protected Location location;
    // The living thing's age.
    protected int age;
    // Random class
    protected static Random rand = Randomizer.getRandom();

    public Entity(Location location) {
        this.alive = true;
        this.location = location;
    }

    /**
     * Check whether the thing is alive or not.
     * @return true if the thing is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Return the thing's location.
     * @return The thing's location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Set the thing's location.
     * @param location The new location.
     */
    protected void setLocation(Location location)
    {
        this.location = location;
    }

    /**
     * Increase the age.
     * This could result in the animal's death.
     */
    protected void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
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
     * @return The max age of the entity.
     */
    protected abstract int getMaxAge();

    /**
     * @return The current age of the entity.
     */
    protected abstract int getAge();

    /**
     * Defines the behaviour of this entity for one simulation step.
     * @param currentField The current state of the field.
     * @param nextFieldState The updated state of the field.
     * @param day The current day of the simulation.
     * @param hour The current hour in the simulation.
     */
    protected abstract void act(Field currentField, Field nextFieldState, int day, int hour);
}