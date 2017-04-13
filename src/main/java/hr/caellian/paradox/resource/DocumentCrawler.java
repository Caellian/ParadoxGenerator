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

import hr.caellian.core.util.IterableNodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Created by tinsv on 2017-04-09.
 */
public class DocumentCrawler {
    private static final List<String> ignored = Arrays.asList("#text", "#comment");

    public static void parseDocument(URL source) {
        System.out.println("Parsing document '" + source.toString() + "'...");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        InputStream inputStream = null;

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            inputStream = source.openStream();
            Document document = documentBuilder.parse(inputStream);
            document.normalize();

            Node root = document.getElementsByTagName("paradox").item(0);

            for (Node node : new IterableNodeList(root.getChildNodes())) {
                switch (node.getNodeName()) {
                    case "generator":
                        ItemGenerator generator = ItemManager.getGenerator(node, source.toString());
                        generator.children.addAll(getChildren(generator, node, generator).keySet());
                        generator.childWeight.putAll(getChildren(generator, node, generator));
                        break;
                    case "template":
                        construct(null, node, null);
                        break;
                    case "localization":
                        String to = node.getAttributes().getNamedItem("to").getNodeValue();
                        LinkedHashMap<String, String> localization = new LinkedHashMap<>();
                        for (Node child: new IterableNodeList(node.getChildNodes())) {
                            if (!ignored.contains(child.getNodeName())) localization.put(child.getNodeName(), child.getTextContent());
                        }
                        ItemManager.publicLocalizations.putIfAbsent(to, new HashMap<>());
                        ItemManager.publicLocalizations.get(to).putAll(localization);
                        break;
                    case "item":
                    case "function":
                        System.err.println("'" + node.getNodeName() + "' in root not supported!");
                        break;
                    default:
                        if (!ignored.contains(node.getNodeName())) System.err.println("Can't handle unsupported type: " + node.getNodeName());
                }
            }
        } catch (Exception e) {
            System.err.println("Unable to import generator from source: " + source.toString());
            e.printStackTrace();
        } finally {
            try {
                assert inputStream != null : "Input Stream mustn't be null!";
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Item construct(Item parent, Node node, ItemGenerator generator) {
        Item result;
        LinkedHashMap<Item, Float> children;
        switch (node.getNodeName()) {
            case "item":
                result = new Item(parent, node, new ArrayList<>());
                children = getChildren(result, node, generator);
                result.children.addAll(children.keySet());
                result.childWeight.putAll(children);
                break;
            case "generator":
                result = null;
                System.err.println("Nested generators are not supported!");
                break;
            case "template":
                result = new ItemTemplate(parent, node, new ArrayList<>());
                children = getChildren(result, node, generator);
                result.children.addAll(children.keySet());
                result.childWeight.putAll(children);
                break;
            case "localization":
                String to = node.getAttributes().getNamedItem("to") != null ?
                        node.getAttributes().getNamedItem("to").getNodeValue() : "en";
                String name = node.getAttributes().getNamedItem("name") != null ?
                        node.getAttributes().getNamedItem("name").getNodeValue() : generator.getNestedID();
                LinkedHashMap<String, String> localization = new LinkedHashMap<>();
                for (Node child: new IterableNodeList(node.getChildNodes())) {
                    if (!ignored.contains(child.getNodeName())) localization.put(child.getNodeName(), child.getTextContent());
                }
                ItemManager.publicLocalizations.putIfAbsent(to, new HashMap<>());
                ItemManager.publicLocalizations.get(to).put(generator.getNestedID(), name);
                generator.localizations.putIfAbsent(to, new HashMap<>());
                generator.localizations.get(to).putAll(localization);
                generator.localizations.get(to).put(generator.getNestedID(), name);
                result = null;
                break;
            case "function":
                result = new ItemFunction(parent, node, new LinkedList<>());
                children = getChildren(result, node, generator);
                result.children.addAll(children.keySet());
                result.childWeight.putAll(children);
                break;
            default:
                result = null;
                if (!ignored.contains(node.getNodeName())) System.err.println("Can't handle unsupported type: " + node.getNodeName());
        }

        return result;
    }

    private static LinkedHashMap<Item, Float> getChildren(Item parent, Node node, ItemGenerator generator) {
        LinkedHashMap<Item, Float> result = new LinkedHashMap<>();

        for (Node child: new IterableNodeList(node.getChildNodes())) {
            if (ignored.contains(child.getNodeName())) continue;

            if (Objects.equals(child.getNodeName(), "localization")) {
                construct(parent, child, generator);
            } else {
                result.put(construct(parent, child, generator),
                        child.hasAttributes() && child.getAttributes().getNamedItem("weight") != null ?
                                Float.valueOf(child.getAttributes().getNamedItem("weight").getNodeValue()) : 1f);
            }
        }

        return result;
    }
}
