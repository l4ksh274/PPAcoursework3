/**
 * Specifies the parameters of the "Chlamydia" disease.
 *
 * @author Laksh Patel and Jiwei Cao
 * @version 1.0
 */

public class Chlamydia extends Disease{
    private static final String name = "Chlamydia";
    private static final int durationInSteps = 100;

    /**
     * Constructs a Chlamydia disease instance.
     * and adds three symptoms: {@code ACHES}, {@code ANTIBREEDING}, and {@code VOMITING}.
     */

    public Chlamydia() {
        super(name, durationInSteps);
        getSymptoms().add(Symptoms.ACHES);
        getSymptoms().add(Symptoms.ANTIBREEDING);
        getSymptoms().add(Symptoms.VOMITING);
    }
}
