public abstract class Plant extends Living {

    protected double height;
    protected double growthRate;

    public Plant(Location location, Field field) {
        super(location, field);
    }

    public void grow() {
        height *= growthRate;
    }
}