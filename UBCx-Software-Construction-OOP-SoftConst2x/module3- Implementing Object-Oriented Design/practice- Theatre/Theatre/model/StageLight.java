package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A light that shines on the theatre stage.
 */
public class StageLight extends Light {

    private List<Integer> scenesWhenOn;

    public StageLight() {
        this.scenesWhenOn = new ArrayList<>();
    }

    public boolean isPartOfScene(int sceneNumber) {
        return scenesWhenOn.contains(sceneNumber);
    }

    public void addSceneWhenOn(int sceneNumber) {
        scenesWhenOn.add(sceneNumber);
    }

}
