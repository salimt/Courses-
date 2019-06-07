/*
 * Copyright 2014 Lynden, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gmapsfx.javascript;

import netscape.javascript.JSObject;

/**
 * An interface for interacting with a JavaScript environment which includes
 * methods for building strings that represent functions and constructors as
 * well as providing a means to execute commands.
 *
 * @author Rob Terpilowski
 */
public interface IJavascriptRuntime {

    /**
     * Execute the specified command returning a value (if any)
     *
     * @param command The JavaScript command to execute
     * @return The underlying JavaScript object that was returned by the script.
     */
    JSObject execute(String command);

    /**
     * Gets a constructor as a string which then can be passed to the execute().
     *
     * @param javascriptObjectType The type of JavaScript object to create
     * @param args The args of the constructor
     * @return A string which can be passed to the JavaScript environment to
     * create a new object.
     */
    String getConstructor(String javascriptObjectType, Object... args);

    /**
     * Gets an array parameter constructor as a String, which then can be 
     * passed to the execute() method. Note, this is where the parameter to the 
     * constructor is an array, rather than the varargs which are broken down 
     * into separate parameters.
     *
     * @param javascriptObjectType type The type of JavaScript object array to create
     * @param ary The array elements
     * @return A string which can be passed to the JavaScript environment to
     * create a new array.
     */
    String getArrayConstructor(String javascriptObjectType, Object[] ary);

    /**
     * Gets a function as a String, which then can be passed to the
     * execute() method.
     * 
     * @param variable The variable to invoke the function on.
     * @param function The function to invoke
     * @param args Arguments the function requires
     * @return A string which can be passed to the JavaScript environment to
     * invoke the function
     */
    String getFunction(String variable, String function, Object... args);
    
    
    /**
     * Gets a function as a String, which then can be passed to the
     * execute() method.
     * 
     * @param function The function to invoke
     * @param args Arguments the function requires
     * @return A string which can be passed to the JavaScript environment to
     * invoke the function
     */
    String getFunction(String function, Object... args);
    
    
    /**
     * Gets an array function as a String, which then can be passed to the
     * execute() method.
     * 
     * @param function The function to invoke
     * @param ary The array of arguments to pass to the function.
     * @return A string which can be passed to the JavaScript environment to
     * invoke the function
     */
    String getArrayFunction(String function, Object[] ary);

}
