import java.util.List;
import java.util.ArrayList;

public abstract class Plant extends Living {

    // Tracks the original plant
    private Plant originalPlant;

    // Stores all plants connected to the original plant
    private ArrayList<Plant> offspring;

    protected Field field;

    public Plant(Location location, Field field, boolean isOriginal) {
        super(location, field);

        if (isOriginal) {
            this.originalPlant = this;
        }

        this.offspring = new ArrayList<>();
    }

    /*
     * Only original plant can breed
     */
    public void act(Field currentField, Field nextFieldState, int day, int hour) {
        incrementAge();
        
        if (this == originalPlant) {
            if (!this.isAlive()) {
                for (Plant child : offspring) {
                    child.setDead();
                    
                }
                offspring.clear();
            }
            else {
                List<Location> freeLocations = getFreeLocations(nextFieldState);
                if (!freeLocations.isEmpty()) {  
                    spread(nextFieldState, freeLocations);
                } 
            }
        }
        else {
            if (originalPlant.isAlive()) {
                nextFieldState.placeLiving(this, this.getLocation());
            }
        }
    }

    public boolean isOriginalPlantAlive() {
        return originalPlant != null && originalPlant.isAlive();
    }

    public List<Location> getFreeLocations(Field nextFieldState) {
        return nextFieldState.getFreeAdjacentLocations(originalPlant.getLocation());
    }

    protected void spread(Field nextFieldState, List<Location> freeLocations) {
        for (Location loc : freeLocations) {
            Plant young = createOffspring(loc, false);
            young.setOriginalPlant(this.originalPlant);
            this.originalPlant.offspring.add(young);
            nextFieldState.placeLiving(young, loc);
        }
    }

    public void setOriginalPlant(Plant original) {
        this.originalPlant = original;
    }

    protected abstract Plant createOffspring(Location loc, boolean isOriginal);
}