public class Reformatory {

    private int wNum;   //the total number of times a weight has been measured.

    public Reformatory() { this.wNum = 0; }

    //return the given persons weight
    public int weight(Person person) { wNum += 1; return person.getWeight(); }

    //increases the persons weight by +1
    public void feed(Person person){ person.setWeight(person.getWeight()+1); }

    //
    public int totalWeightsMeasured(){
        return wNum;
    }

}
