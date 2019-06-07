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
 * Class for interacting with the JavaScript environment. This class is used by
 * the JavaScript objects, but should not need to be used directly for creating
 * or changing maps.
 *
 * @author Rob Terpilowski
 */
public class JavascriptRuntime implements IJavascriptRuntime {

    protected static IJavascriptRuntime runtime = null;

    public static IWebEngine engine;

    /**
     * Gets a singleton instance of this class, creating one if it doesn't yet
     * exist.
     *
     * @return A singleton instance
     */
    public static IJavascriptRuntime getInstance() {
        if (runtime == null) {
            runtime = new JavascriptRuntime();
        }
        return runtime;
    }

    /**
     * Set the WebEngine that this runtime should use.
     *
     * @param e The underlying WebEngine to use.
     */
    public static void setDefaultWebEngine(IWebEngine e) {
        engine = e;
    }

    /**
     * Execute the specified command returning a value (if any)
     *
     * @param command The JavaScript command to execute
     * @return The underlying JavaScript object that was returned by the script.
     */
    @Override
    public JSObject execute(String command) {
        Object returnValue = engine.executeScript(command);
        if (returnValue instanceof JSObject) {
            return (JSObject) returnValue;
        }

        return null;
    }

    /**
     * Gets a constructor as a string which then can be passed to the execute().
     *
     * @param javascriptObjectType The type of JavaScript object to create
     * @param args The args of the constructor
     * @return A string which can be passed to the JavaScript environment to
     * create a new object.
     */
    @Override
    public String getConstructor(String javascriptObjectType, Object... args) {
        return getFunction("new " + javascriptObjectType, args);
    }

    /**
     * Gets an array parameter constructor as a String, which then can be 
     * passed to the execute() method.
     *
     * @param javascriptObjectType type The type of JavaScript object array to create
     * @param ary The array elements
     * @return A string which can be passed to the JavaScript environment to
     * create a new array.
     */
    @Override
    public String getArrayConstructor(String javascriptObjectType, Object[] ary) {
        String fn = getArrayFunction("new " + javascriptObjectType, ary);
        return fn;
    }

    /**
     * Gets a function as a String, which then can be passed to the execute()
     * method.
     *
     * @param variable The variable to invoke the function on.
     * @param function The function to invoke
     * @param args Arguments the function requires
     * @return A string which can be passed to the JavaScript environment to
     * invoke the function
     */
    @Override
    public String getFunction(String variable, String function, Object... args) {
        return getFunction(variable + "." + function, args);
    }

    /**
     * Gets a function as a String, which then can be passed to the execute()
     * method.
     *
     * @param function The function to invoke
     * @param args Arguments the function requires
     * @return A string which can be passed to the JavaScript environment to
     * invoke the function
     */
    @Override
    public String getFunction(String function, Object... args) {
        if (args == null) {
            return function + "();";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(function).append("(");
        for (Object arg : args) {
            sb.append(getArgString(arg)).append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), ")");

        return sb.toString();
    }

    /**
     * Gets an array function as a String, which then can be passed to the
     * execute() method.
     *
     * @param function The function to invoke
     * @param ary The array of arguments to pass to the function.
     * @return A string which can be passed to the JavaScript environment to
     * invoke the function
     */
    @Override
    public String getArrayFunction(String function, Object[] ary) {
        if (ary == null || ary.length == 0) {
            return function + "([]);";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(function).append("([");
        for (Object arg : ary) {
            if (arg instanceof JavascriptObject) {
                sb.append(((JavascriptObject) arg).getVariableName()).append(",");
            } else {
                sb.append(getArgString(arg)).append(",");
            }
        }
        sb.replace(sb.length() - 1, sb.length(), "]").append(")");

        return sb.toString();
    }

    /**
     * Takes the specified object and converts the argument to a String.
     *
     * @param arg The object to convert
     * @return A String representation of the argument.
     */
    protected String getArgString(Object arg) {
        //if (arg instanceof LatLong) {
        //    return ((LatLong) arg).getVariableName();
        //} else 
        if (arg instanceof JavascriptObject) {
             return ((JavascriptObject) arg).getVariableName();
           // return ((JavascriptObject) arg).getPropertiesAsString();
        } else if( arg instanceof JavascriptEnum ) {
            return ((JavascriptEnum) arg).getEnumValue().toString();
        } else {
            return arg.toString();
        }
    }
}
