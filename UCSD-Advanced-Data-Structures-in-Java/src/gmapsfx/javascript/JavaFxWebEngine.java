/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gmapsfx.javascript;

import javafx.concurrent.Worker;
import javafx.scene.web.WebEngine;

/**
 * This class provides an implementation of the IWebEngine interface utilizing 
 * a javafx.scene.web.WebEngine as the underlying engine.
 * 
 * @author Rob Terpilowski
 */
public class JavaFxWebEngine implements IWebEngine {
    
    
    protected WebEngine webEngine;
    
    /**
     * Builds a new engine utilizing the specified JavaFX WebEngine
     * @param engine The JavaFX WebEngine to use.
     */
    public JavaFxWebEngine( WebEngine engine ) {
        this.webEngine = engine;
    }

    
    /**
     * Executes the specified JavaScript Command
     * @param command The command to execute
     * @return The object returned by the script (if any).
     */
    @Override
    public Object executeScript(String command) {
        return webEngine.executeScript(command);
    }

    /**
     * Gets a worked which will be notified when a web page has finished loading.
     * @return The worker 
     */
    @Override
    public Worker<Void> getLoadWorker() {
        return webEngine.getLoadWorker();
    }

    /**
     * Loads the specified URL
     * @param url The URL to load in the engine.
     */
    @Override
    public void load(String url) {
        webEngine.load(url);
    }
    
    
    
    
    
}
