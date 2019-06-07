/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmapsfx.javascript;

import javafx.concurrent.Worker;

/**
 * Provides an abstraction to the web engine, so that a mock engine can be utilized in unit tests, or a different web engine
 * could be utilized in future versions of this framework.
 * 
 * @author Rob Terpilowski
 */
public interface IWebEngine {

    /**
     * Executes the specified JavaScript Command
     *
     * @param command The command to execute
     * @return The object returned by the script (if any).
     */
    public Object executeScript(String command);

    /**
     * Gets a worked which will be notified when a web page has finished
     * loading.
     *
     * @return The worker
     */
    public Worker<Void> getLoadWorker();

    /**
     * Loads the specified URL
     *
     * @param url The URL to load in the engine.
     */
    public void load(String url);

}
