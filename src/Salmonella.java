/**
 * Specifies the parameters of the "Salmonella" disease.
 *
 * @author Laksh Patel and Jiwei Cao
 * @version 1.0
 */

public class Salmonella extends Disease{
    private static final String name = "Salmonella";
    private static final int durationInSteps = 80;

    /**
     * Constructs an Salmonella disease instance.
     * and adds three symptoms: {@code ACHES}, {@code VOMITING}, and {@code FEVER}.
     */
    public Salmonella(){
        super(name, durationInSteps);

        getSymptoms().add(Symptoms.ACHES);
        getSymptoms().add(Symptoms.VOMITING);
        getSymptoms().add(Symptoms.FEVER);
    }


}
