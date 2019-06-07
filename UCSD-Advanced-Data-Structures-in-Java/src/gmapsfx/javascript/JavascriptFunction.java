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

import java.util.List;

/**
 *
 * @author robt
 */
public class JavascriptFunction extends JavascriptObject {

    /**
     * Attempt to create a function as an object
     *
     * var myCallback = function( argX ) { someVariable.someMethod( argX );
     * someOtherVariable.someOtherMethod(); }
     */
    protected String functionName;
    protected List<String> args;
    protected List<JavascriptFunctionLine> functionLines;

    public JavascriptFunction(String functionName, List<String> args, List<JavascriptFunctionLine> functionLines) {
        this.functionName = functionName;
        this.args = args;
        this.functionLines = functionLines;
    }

    public String getFunctionAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append(functionName).append("(");
        if (args.isEmpty()) {
            sb.append(")");
        } else {
            for (String arg : args) {
                sb.append(arg).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
        }
        sb.append("{\n");
        for( JavascriptFunctionLine line : functionLines ) {
            sb.append( line.getFunctionLine() );
        }
        sb.append("}");
        
        return sb.toString();
    }

}
