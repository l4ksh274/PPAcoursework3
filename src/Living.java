public abstract class Living {
    
    // Whether the thing is alive or not.
    protected boolean alive;
    // The living thing's location.
    protected Location location;
    // The living thing's age.
    protected int age;

    public Living(Location location, Field field) {
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

    protected abstract int getMaxAge();
}