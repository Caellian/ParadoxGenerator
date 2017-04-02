/*
 * The MIT License (MIT)
 * Paradox Generator, random sentence generator.
 * Copyright (c) 2017 Tin Švagelj <tin.svagelj@live.com> a.k.a. Caellian
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package hr.caellian.paradox.lib;

import hr.caellian.paradox.ParadoxGenerator;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Caellian on 3.7.2015., at 1:51.
 */
public class Resources {
    public static final URL SPLASH = ParadoxGenerator.class.getResource("/splash/splash.png");
    public static final URL ICON = ParadoxGenerator.class.getResource("/icon/64.png");

    public static final URL VERSIONS_FILE = getVersionsFile();

    private static URL getVersionsFile() {
        try {
            return new URL("https://raw.githubusercontent.com/Caellian/Paradox_Generator/master/versions.xml");
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
