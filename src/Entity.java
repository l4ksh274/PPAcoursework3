import java.util.Random;

/**
 * Abstract class representing common elements of all entities in the simulation.
 * This serves as a base class for all living entities, such as animals and plants.
 * 
 * @author Jiwei Cao and Laksh Patel
 * @version 1.0
 */
public abstract class Entity {
    
    // A flag indicating whether the entity is alive.
    protected boolean alive;
    // The current location of the entity.
    protected Location location;
    // The age of the entity.
    protected int age;
    // Shared random instance to generate random values.
    protected static Random rand = Randomizer.getRandom();

    /**
     * Constructor for creating an entity at a specific location.
     * The entity is intialised to be alive.
     * @param location
     */
    public Entity(Location location) {
        this.alive = true;
        this.location = location;
    }

    /**
     * Check whether the thing is alive or not.
     * @return True if the entity is still alive.
     */
    public boolean isAlive()
    {
        return alive;
    }

    /**
     * Get the current location of the entity.
     * @return The entity's location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Set the entity's location.
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
     * Indicate that the entity is no longer alive.
     */
    protected void setDead()
    {
        alive = false;
        location = null;
    }

    /**
     * Get the maximum age of an entity before it dies.
     * This is an abstract method which must be implemented by its subclasses.
     * @return The max age of the entity.
     */
    protected abstract int getMaxAge();

    /**
     * Get the current age of the entity.
     * This is an abstract method which must be implemented by its subclasses.
     * @return The current age of the entity.
     */
    protected abstract int getAge();

    /**
     * Defines the behaviour of this entity for one simulation step.
     * This is an abstract method, which must be implemented by its subclasses.
     * @param currentField The current state of the field.
     * @param nextFieldState The updated state of the field.
     * @param day The current day of the simulation.
     * @param hour The current hour in the simulation.
     */
    protected abstract void act(Field currentField, Field nextFieldState, int day, int hour);
}