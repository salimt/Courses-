package model.weights;

public class WeightMachine extends Weight {

    private String muscleGroup;

    public WeightMachine(String muscleGroup, int pounds){
        super(pounds);
        this.muscleGroup = muscleGroup;
    }

    // getters
    public String getMuscleGroup() { return this.muscleGroup; }


}
