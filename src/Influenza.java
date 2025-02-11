public class Influenza extends Disease{
    private static final int DURATION_IN_STEPS = 20;
    private static final String name = "Influenza";

    public Influenza() {
        super(name, DURATION_IN_STEPS);

        getSymptoms().add(Symptoms.FEVER);
        getSymptoms().add(Symptoms.COUGH);
        getSymptoms().add(Symptoms.DEATH);
    }
}
