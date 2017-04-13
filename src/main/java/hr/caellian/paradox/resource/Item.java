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

import hr.caellian.paradox.util.Pointer;
import org.w3c.dom.Node;

import java.util.*;

/**
 * Created by tinsv on 2017-04-09.
 */
public class Item {
    private static Random random = new Random();

    protected final Item parent;
    protected final Node origin;
    public final LinkedList<Item> children = new LinkedList<>();
    public final LinkedHashMap<Item, Float> childWeight = new LinkedHashMap<>();

    protected String customLocalization = null;

    public Item(Item parent, Node origin, LinkedList<Item> data) {
        this.parent = parent;
        this.origin = origin;
        children.addAll(data);
        data.forEach(a -> childWeight.put(a, 1f));
        if(origin.hasAttributes()){
            if(origin.getAttributes().getNamedItem("loc") != null) {
                customLocalization = origin.getAttributes().getNamedItem("loc").getNodeValue();
            }
            if(origin.getAttributes().getNamedItem("name") != null) {
                ItemManager.staticNames.put(getNestedID(), origin.getAttributes().getNamedItem("name").getNodeValue());
            }
        }

    }

    public Item(Item parent, Node origin, ArrayList<Item> data) {
        this.parent = parent;
        this.origin = origin;
        children.addAll(data);
        data.forEach(a -> childWeight.put(a, 1f));
        if(origin.hasAttributes()){
            if(origin.getAttributes().getNamedItem("loc") != null) {
                customLocalization = origin.getAttributes().getNamedItem("loc").getNodeValue();
            }
            if(origin.getAttributes().getNamedItem("name") != null) {
                ItemManager.staticNames.put(getNestedID(), origin.getAttributes().getNamedItem("name").getNodeValue());
            }
        }
    }

    public Item(Item parent, Node origin, LinkedHashMap<Item, Float> data) {
        this.parent = parent;
        this.origin = origin;
        data.forEach((a, b) -> children.add(a));
        childWeight.putAll(data);
        if(origin.hasAttributes()){
            if(origin.getAttributes().getNamedItem("loc") != null) {
                customLocalization = origin.getAttributes().getNamedItem("loc").getNodeValue();
            }
            if(origin.getAttributes().getNamedItem("name") != null) {
                ItemManager.staticNames.put(getNestedID(), origin.getAttributes().getNamedItem("name").getNodeValue());
            }
        }
    }

    public Item(Item parent, Node origin, HashMap<Item, Float> data) {
        this.parent = parent;
        this.origin = origin;
        data.forEach((a, b) -> children.add(a));
        childWeight.putAll(data);
        if(origin.hasAttributes()){
            if(origin.getAttributes().getNamedItem("loc") != null) {
                customLocalization = origin.getAttributes().getNamedItem("loc").getNodeValue();
            }
            if(origin.getAttributes().getNamedItem("name") != null) {
                ItemManager.staticNames.put(getNestedID(), origin.getAttributes().getNamedItem("name").getNodeValue());
            }
        }
    }

    public Item getChild() {
        if (childWeight.size() == 0) return null;
        Pointer<Float> random = new Pointer<>(0f);
        childWeight.forEach((a, b) -> random.to += b);

        random.to = Item.random.nextFloat() * random.to;

        Item result = children.get(0);
        for (int i = 0; i < childWeight.size(); i++) {
            random.to -= (Float) childWeight.values().toArray()[i];
            if (random.to <= 0.0) {
                result = children.get(i);
                break;
            }
        }

        return result;
    }

    public String getNestedID() {
        if (parent != null && !(parent instanceof ItemGenerator)) {
            return parent.getNestedID() + "." + (origin.hasAttributes() &&
                    origin.getAttributes().getNamedItem("id") != null ?
                    origin.getAttributes().getNamedItem("id").getNodeValue() : origin.getNodeName());
        } else {
            return origin.hasAttributes() && origin.getAttributes().getNamedItem("id") != null ? origin.getAttributes().getNamedItem("id").getNodeValue() : origin.getNodeName();
        }
    }

    public String getText(String localization, ItemGenerator generator) {
        String id = origin.getAttributes().getNamedItem("id").getNodeValue();
        String nestedID = getNestedID();

        if (customLocalization != null) {
            nestedID = customLocalization;
        }
        if (generator == null || !generator.localizations.get(localization).containsKey(nestedID)) {
            if(ItemManager.publicLocalizations.get(localization).containsKey(nestedID)){
                return ItemManager.process(ItemManager.publicLocalizations.get(localization).get(nestedID), localization, generator) + (!children.isEmpty() ? getChild().getText(localization, generator) : "");
            } else {
                return ItemManager.process(ItemManager.staticNames.getOrDefault(nestedID, id), localization, generator) + (!children.isEmpty() ? getChild().getText(localization, generator) : "");
            }
        } else {
            return ItemManager.process(generator.localizations.get(localization).getOrDefault(nestedID, id), localization, generator) + (!children.isEmpty() ? getChild().getText(localization, generator) : "");
        }
    }
}
