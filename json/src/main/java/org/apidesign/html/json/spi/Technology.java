/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2013-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Oracle. Portions Copyright 2013-2013 Oracle. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.apidesign.html.json.spi;

import net.java.html.json.Model;
import net.java.html.json.Models;

/** An implementation of a binding between model classes (see {@link Model})
 * and particular technology like <a href="http://knockoutjs.com">knockout.js</a>
 * in a browser window, etc.
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
public interface Technology<Data> {
    /** Creates an object to wrap the provided model object. The model
     * has previously been generated by annotation processor associated 
     * with {@link Model} annotation.
     * 
     * @param model the model generated from {@link Model}
     * @return internal object representing the model
     */
    public Data wrapModel(Object model);
    
    /** Converts an element potentially representing a model into the model.
     * @param modelClass expected class to convert the data to
     * @param data the current data provided from the browser
     * @return the instance of modelClass somehow extracted from the data, may return <code>null</code>
     */
    public <M> M toModel(Class<M> modelClass, Object data);
    
    /** Binds a property between the model and the data as used by the technology.
     * 
     * @param b the description of the requested binding
     * @param model the original instance of the model
     * @param data the data to bind with the model
     */
    public void bind(PropertyBinding b, Object model, Data data);

    /** Model for given data has changed its value. The technology is
     * supposed to update its state (for example DOM nodes associated
     * with the model). The update usually happens asynchronously.
     * 
     * @param data technology's own representation of the model
     * @param propertyName name of the model property that changed
     */
    public void valueHasMutated(Data data, String propertyName);

    public void expose(FunctionBinding fb, Object model, Data d);
    
    /** Applies given data to current context (usually an HTML page).
     * @param data the data to apply
     */
    public void applyBindings(Data data);
    
    /**
     * Some technologies may require wrapping a Java array into a special
     * object. In such case they may return it from this method.
     *
     * @param arr original array
     * @return wrapped array
     */
    public Object wrapArray(Object[] arr);
    
    /** 
     * Run given runnable in a safe mode. If the runnable can be executed
     * immediately, do it. If we need to switch to some other thread, do it
     * and invoke r asynchronously immediately returning from the call to
     * runSafe method.
     * 
     * @param r the runnable to execute
     */
    public void runSafe(Runnable r);

    /** For certain rendering technologies it may be more efficient to register
     * property and function bindings for one instance of the model at once, 
     * rather then doing it incrementally via 
     * {@link Technology#expose(org.apidesign.html.json.spi.FunctionBinding, java.lang.Object, java.lang.Object) }
     * and 
     * {@link Technology#bind(org.apidesign.html.json.spi.PropertyBinding, java.lang.Object, java.lang.Object) }.
     * In such case implement the {@link #wrapModel(java.lang.Object, org.apidesign.html.json.spi.PropertyBinding[], org.apidesign.html.json.spi.FunctionBinding[]) }
     * method of this interface and it will be called instead of the 
     * previous two ones.
     * 
     * @since 0.6
     */
    public static interface BatchInit<D> extends Technology<D> {
        /** Wrap the given model into redering technology appropriate object 
         * <code>D</code> and expose given properties and functions on it.
         * 
         * @param model the {@link Models#isModel(java.lang.Class) model} in Java
         * @param propArr array of property bindings to expose
         * @param funcArr array of functions to expose
         * @return appropriate wrapper around the model
         */
        public D wrapModel(Object model, PropertyBinding[] propArr, FunctionBinding[] funcArr);
    }
}
