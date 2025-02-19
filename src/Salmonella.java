public class Salmonella extends Disease{
    private static final String name = "Salmonella";
    private static final int durationInSteps = 80;

    public Salmonella(){
        super(name, durationInSteps);

        getSymptoms().add(Symptoms.ACHES);
        getSymptoms().add(Symptoms.VOMITING);
        getSymptoms().add(Symptoms.FEVER);
    }


}
