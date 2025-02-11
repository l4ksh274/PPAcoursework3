public class Chlamydia extends Disease{
    private static final String name = "Chlamydia";
    private static final int durationInSteps = 30;

    public Chlamydia() {
        super(name, durationInSteps);
        getSymptoms().add(Symptoms.ACHES);
        getSymptoms().add(Symptoms.ANTIBREEDING);
        getSymptoms().add(Symptoms.VOMITING);
    }
}
