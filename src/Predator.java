public abstract class Predator extends Animal {
    public Predator(Location location) {
        super(location);
    }
    
    /**
     * Predators can hunt
     * @param currentField
     * @param nextFieldState
     */
    protected abstract void hunt(Field currentField, Field nextFieldState);
}
