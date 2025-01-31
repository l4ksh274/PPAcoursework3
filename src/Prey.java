public abstract class Prey extends Animal{
    public Prey(Location location) {
        super(location);
    }
    
    /**
     * Prey can flee
     * @param currentField
     * @param nextFieldState
     */
    protected abstract void flee(Field currentField, Field nextFieldState);
}
