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
import netscape.javascript.JSObject;

/**
 *
 * @author robt
 */
public class JavascriptFunctionLine {
    
    protected JSObject jsObject;
    protected JavascriptObject object;
    protected String method;
    protected List<Object> args;

    public JavascriptFunctionLine(JavascriptObject object, String method, List<Object> args) {
        this.object = object;
        this.method = method;
        this.args = args;
    }
    
    
    public String getFunctionLine() {
        StringBuilder sb = new StringBuilder();
        sb.append( object.getVariableName() ).append(".").append(method);
        sb.append("(");
        for( Object arg : args ) {
            if( arg instanceof JavascriptObject) {
                sb.append( ((JavascriptObject) arg).getVariableName() );
            } else {
                sb.append( arg.toString() );
            }
            sb.append(",");
        }
        sb.deleteCharAt( sb.length()-1);
        sb.append(");\n");
        
        return sb.toString();
    }
    
}
