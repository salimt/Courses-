package model.weights;

public class WeightMachine extends Weight {

    private String muscleGroup;

    //REQUIRES: pounds >= 0
    public WeightMachine(String muscleGroup, int pounds){
        super(pounds);
        this.muscleGroup = muscleGroup;
    }

    //EFFECTS: returns the muscle group this machine targets
    public String getMuscleGroup(){
        return this.muscleGroup;
    }

}
