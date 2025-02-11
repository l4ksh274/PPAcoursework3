import java.util.ArrayList;
import java.util.List;

/**
 *  Represents a disease with a specified name and duration, tracked in steps (steps)
 *
 * @author Laksh Patel
 * @version 1.0
 */
public abstract class Disease {

    // Name of the disease
    private String name;
    // The longest number of steps the disease can be com
    private final int durationInSteps;
    // The number of steps elapsed by the disease.
    private int stepsElapsed;
    // The Symptoms caused by the disease.
    protected List<Symptoms> symptoms;

    public Disease(String name, int durationInSteps){
        this.name = name;
        this.durationInSteps = durationInSteps;
        stepsElapsed = 0;
        symptoms = new ArrayList<>();
    }

    public List<Symptoms> getSymptoms() {
        return symptoms;
    }

    public int getStepsElapsed() {
        return stepsElapsed;
    }

    public void incrementStepsElapsed() {
        this.stepsElapsed++;
    }

    public int getDurationInSteps() {
        return durationInSteps;
    }
}
