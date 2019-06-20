package model.gym;

public class DeluxeGym extends Gym {

    private boolean hasSauna;
    private boolean hasTowelService;

    //REQUIRES: locnName != null
    public DeluxeGym(String locnName){
        super (locnName);
        setHasPool(true);
        setHasChildCare(true);
    }

    public boolean hasSauna(){
        return hasSauna;
    }

    public boolean hasTowelService(){
        return hasTowelService;
    }
}
