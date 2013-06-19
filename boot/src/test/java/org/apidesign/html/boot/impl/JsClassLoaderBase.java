/**
 * HTML via Java(tm) Language Bindings
 * Copyright (C) 2013 Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details. apidesign.org
 * designates this particular file as subject to the
 * "Classpath" exception as provided by apidesign.org
 * in the License file that accompanied this code.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://wiki.apidesign.org/wiki/GPLwithClassPathException
 */
package org.apidesign.html.boot.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jaroslav Tulach <jaroslav.tulach@apidesign.org>
 */
public class JsClassLoaderBase {
    protected static Class<?> methodClass;
    
    public JsClassLoaderBase() {
    }
    
    @BeforeMethod
    public void assertClassDefined() {
        assertNotNull(methodClass, "BeforeClass set up code should provide methodClass");
    }

    @Test public void noParamMethod() throws Throwable {
        Method plus = methodClass.getMethod("fortyTwo");
        try {
            final Object val = plus.invoke(null);
            assertTrue(val instanceof Number, "A number returned " + val);
            assertEquals(((Number)val).intValue(), 42);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }
    
    @Test public void testExecuteScript() throws Throwable {
        Method plus = methodClass.getMethod("plus", int.class, int.class);
        try {
            assertEquals(plus.invoke(null, 10, 20), 30);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }

    @Test public void overloadedMethod() throws Throwable {
        Method plus = methodClass.getMethod("plus", int.class);
        try {
            assertEquals(plus.invoke(null, 10), 10);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }
    
    @Test public void instanceMethod() throws Throwable {
        Method plus = methodClass.getMethod("plusInst", int.class);
        Object inst = methodClass.newInstance();
        try {
            assertEquals(plus.invoke(inst, 10), 10);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }
    
    @Test public void staticThis() throws Throwable {
        Method st = methodClass.getMethod("staticThis");
        try {
            assertNull(st.invoke(null));
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }

    @Test public void getThis() throws Throwable {
        Object th = methodClass.newInstance();
        Method st = methodClass.getMethod("getThis");
        try {
            assertEquals(st.invoke(th), th);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }
    
}