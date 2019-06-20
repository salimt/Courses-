package test;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for Theatre class
 */

public class TheatreTest {

    private Theatre theatre;
    private List<StageLight> stageLights;
    private List<HouseLight> houseLights;
    private Curtain curtain;

    private static final int NUM_LIGHTS = 10;
    private static final int SHOW_TIME = 8;
    private static final int SCENE_1 = 1;

    @BeforeEach
    public void setUp() {
        this.houseLights = new ArrayList<>();
        this.stageLights = new ArrayList<>();

        for (int i = 0; i < NUM_LIGHTS; i++) {
            houseLights.add(new HouseLight());

            StageLight newStageLight = new StageLight();
            if (i % 2 == 0) {
                newStageLight.addSceneWhenOn(SCENE_1);
            }
            stageLights.add(newStageLight);
        }

        this.curtain = new Curtain();
        this.theatre = new Theatre(SHOW_TIME, stageLights, houseLights, curtain);
    }

    @Test
    public void letInAudience() {
        theatre.openHouse();
        assertTrue(curtain.isClosed());

        for (HouseLight houseLight : houseLights) {
            assertTrue(houseLight.isTurnedOn());
        }

        for (StageLight stageLight : stageLights) {
            assertFalse(stageLight.isTurnedOn());
        }
    }

    @Test
    public void startShowTooEarly() {
        int currTime = SHOW_TIME - 1;
        assertFalse(theatre.startShow(currTime));
    }

    @Test
    public void startShowRightOnTime() {
        assertTrue(theatre.startShow(SHOW_TIME));
        checkLightsAndCurtainAtShowTime();
    }

    @Test
    public void startShowLate() {
        assertTrue(theatre.startShow(SHOW_TIME));
        checkLightsAndCurtainAtShowTime();
    }

    private void checkLightsAndCurtainAtShowTime() {
        for (Light houseLight : houseLights) {
            assertFalse(houseLight.isTurnedOn());
        }
        for (int i = 0; i < NUM_LIGHTS; i++) {
            StageLight thisLight = stageLights.get(i);
            if (i % 2 == 0) {
                assertTrue(thisLight.isTurnedOn());
            } else {
                assertFalse(thisLight.isTurnedOn());
            }
        }
        assertFalse(curtain.isClosed());
    }
}
