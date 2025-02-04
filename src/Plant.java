public abstract class Plant extends Living {

    public Plant(Location location, Field field) {
        super(location, field);
    }

    public void act() {
        incrementAge();
    }
}