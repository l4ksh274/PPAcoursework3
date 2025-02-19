import java.util.ArrayList;
import java.util.List;

/**
 *  Represents a disease with a specified name and duration, tracked in steps (steps)
 *
 * @author Laksh Patel and Jiwei Cao
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

    /**
     * Constructs a Disease with a specified name and duration, tracked in steps.
     *
     * @param name The name of the disease.
     * @param durationInSteps The duration of the disease in simulation steps.
     */
    public Disease(String name, int durationInSteps){
        this.name = name;
        this.durationInSteps = durationInSteps;
        stepsElapsed = 0;
        symptoms = new ArrayList<>();
    }

    /**
     * Gets the list of symptoms associated with the disease.
     *
     * @return a list of symptoms representing the effects caused by the disease.
     */
    public List<Symptoms> getSymptoms() {
        return symptoms;
    }

    /**
     * Gets the number of steps that have elapsed for this disease.
     *
     * @return The number of steps elapsed as an integer.
     */
    public int getStepsElapsed() {
        return stepsElapsed;
    }

    /**
     * Increments the number of steps elapsed for the disease by one.
     *
     * This method modifies the internal state of the disease by increasing
     * its `stepsElapsed` counter, which tracks how many simulation steps
     * the disease has persisted. Used to update the progression of the disease
     * over time, typically during a simulation process.
     */
    public void incrementStepsElapsed() {
        this.stepsElapsed++;
    }

    /**
     * Gets the maximum duration of the disease in terms of steps.
     *
     * @return The maximum duration of the disease in simulation steps.
     */
    public int getDurationInSteps() {
        return durationInSteps;
    }
}
