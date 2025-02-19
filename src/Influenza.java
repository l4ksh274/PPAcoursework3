/**
 * Specifies the parameters of the "Influzena" disease.
 *
 * @author Laksh Patel and Jiwei Cao
 * @version 1.0
 */

public class Influenza extends Disease{
    private static final int DURATION_IN_STEPS = 80;
    private static final String name = "Influenza";

    /**
     * Constructs an Influenza disease instance.
     * and adds three symptoms: {@code FEVER}, {@code COUGH}, and {@code DEATH}.
     */

    public Influenza() {
        super(name, DURATION_IN_STEPS);

        getSymptoms().add(Symptoms.FEVER);
        getSymptoms().add(Symptoms.COUGH);
        getSymptoms().add(Symptoms.DEATH);
    }
}
