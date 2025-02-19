/**
 * Defines the symptom behaviors that can affect animals in a simulation.
 * Implementations of this interface specify how symptoms modify animal behavior or state
 * within a given field context.
 */
public interface ISymptom {

    /**
     * Applies the symptom's effect to an animal within a simulation field.
     *
     * @param animal The animal being affected by the symptom
     * @param nextFieldState The field context where the effect occurs, typically representing
     *                       the next state of the simulation field
     */
    void applyAffect(Animal animal, Field nextFieldState);

}
