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

package hr.caellian.paradox.resource;

import hr.caellian.paradox.configuration.Settings;
import hr.caellian.paradox.util.Pointer;
import org.w3c.dom.Node;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ItemManager {
    public static final ArrayList<ItemGenerator> generators = new ArrayList<>();
    public static final HashMap<String, ItemTemplate> templates = new HashMap<>();
    public static final HashMap<String, HashMap<String, String>> publicLocalizations = new HashMap<>();
    public static final HashMap<String, String> staticNames = new HashMap<>();

    public static void refreshData() {
        generators.clear();
        templates.clear();
        publicLocalizations.clear();
        staticNames.clear();
        try {
            DocumentCrawler.parseDocument(new URL(Settings.GENERATOR_SOURCE));
        } catch (Exception e) {
            System.err.println("Couldn't load generator (" + Settings.GENERATOR_SOURCE + ") source!");
            e.printStackTrace();
        }
        for (String library : Settings.LIBRARIES.split(";")) {
            try {
                DocumentCrawler.parseDocument(new URL(library));
            } catch (Exception e) {
                File file = new File(library);

                if (file.exists()) {
                    try {
                        DocumentCrawler.parseDocument(file.toURI().toURL());
                    } catch (MalformedURLException e1) {
                        System.err.println("Couldn't load library (" + library + ") source!");
                    }
                }
            }
        }
    }

    public static String process(String in, String localization, ItemGenerator generator) {
        String result = in;

        String[] symbol = in.split("[^\\\\](\\$\\{)");
        for (int i = 1; i < symbol.length; i++) {
            symbol[i] = symbol[i].split("}")[0];

            if (templates.containsKey(symbol[i])) {
                result = result.replaceFirst("\\$\\{" + symbol[i] + "}",templates.get(symbol[i]).getText(localization, generator));
            } else {
                System.err.println("Couldn't find template: " + symbol[i]);
            }
        }

        return result.replace("\\${", "${");
    }

    public static ItemGenerator getGenerator(Node origin, String source) {
        Pointer<ItemGenerator> result = new Pointer<>(null);
        generators.stream().filter(it -> origin.hasAttributes() && Objects.equals(it.getNestedID(), origin.getAttributes().getNamedItem("id").getNodeValue())).forEach(it -> result.to = it);
        return result.isNull() ? new ItemGenerator(origin, source) : result.to;
    }
}
