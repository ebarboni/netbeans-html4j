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

package net.java.html.js.tests;

import java.util.concurrent.Callable;
import net.java.html.js.JavaScriptBody;

/**
 *
 * @author Jaroslav Tulach <jtulach@netbeans.org>
 */
final class Bodies {
    @JavaScriptBody(args = { "a", "b" }, body = "return a + b;")
    public static native int sum(int a, int b);
    
    @JavaScriptBody(args = {"r"}, javacall = true, body = "r.@java.lang.Runnable::run()();")
    static native void callback(Runnable r);

    @JavaScriptBody(args = {"c"}, javacall = true, body = "return c.@java.util.concurrent.Callable::call()();")
    static native Object callback(Callable<Boolean> c);
    
    @JavaScriptBody(args = { "v" }, body = "return v;")
    public static native Object id(Object v);
    
    @JavaScriptBody(args = { "v" }, body = "return { 'x' : v };")
    public static native Object instance(int v);
    
    @JavaScriptBody(args = "o", body = "o.x++;")
    public static native void incrementX(Object o);

    @JavaScriptBody(args = "o", body = "return o.x;")
    public static native int readX(Object o);

    @JavaScriptBody(args = { "c" }, javacall = true, body = 
        "return c.@net.java.html.js.tests.Sum::sum(II)(40, 2);"
    )
    public static native int sumIndirect(Sum c);
    
    @JavaScriptBody(args = { "arr", "index" }, body = "return arr[index];")
    public static native Object select(Object[] arr, int index);

    @JavaScriptBody(args = { "arr" }, body = "return arr.length;")
    public static native int length(Object[] arr);

    @JavaScriptBody(args = { "arr", "i", "value" }, body = "arr[i] = value;")
    public static native void modify(String[] arr, int i, String value);
    
    @JavaScriptBody(args = {}, body = "return true;")
    public static native boolean truth();
}
