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

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by tinsv on 2017-04-09.
 */
public class ItemGenerator extends Item{
    // Localization -> (ID -> Text)
    public final HashMap<String, HashMap<String, String>> localizations = new HashMap<>();
    public final String source;


    public ItemGenerator(Node origin, String source){
        super(null, origin, new LinkedList<>());
        this.source = source;
        ItemManager.generators.add(this);
    }

    public String getText(String localization) {
        return getText(localization, this);
    }

    public String getText(String localization, ItemGenerator generator) {
        if (!this.localizations.get(localization).containsKey(getNestedID())) {
            return ItemManager.publicLocalizations.get(localization).getOrDefault(getNestedID(), getNestedID());
        } else {
            return this.localizations.get(localization).getOrDefault(getNestedID(), getNestedID());
        }
    }
}
