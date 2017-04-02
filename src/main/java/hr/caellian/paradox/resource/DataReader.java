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
import hr.caellian.paradox.configuration.Settings;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Caellian on 30.6.2015., at 0:45.
 */
public class DataReader {
    public static void importSources() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        InputStream inputStream = null;

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            inputStream = new URL(Settings.GENERATOR_SOURCE).openStream();
            Document document = documentBuilder.parse(inputStream);

            document.normalize();

            Node root = document.getElementsByTagName("generator").item(0);

            for (Node source : new IterableNodeList(root.getChildNodes())) {
                if (Objects.equals(source.getNodeName(), "strings")) {
                    StoredData.strings.putAll(getStringData(source));
                } else if (source.getChildNodes().getLength() >= 1) {
                    StoredData.sources.add(source.getNodeName());
                    StoredData.data.putAll(getChildData(source));
                }
            }
        } catch (Exception e) {
            System.err.println("Unable to import sources from internet.");
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

    private static HashMap<String, ArrayList<String>> getChildData(Node source) {
        HashMap<String, ArrayList<String>> result = new HashMap<>();
        if (source.getChildNodes().getLength() >= 1) {
            new IterableNodeList(source.getChildNodes()).stream().filter(nextSource -> !Objects.equals(source.getNodeName(), "#text") && !Objects.equals(nextSource.getNodeName(), "#text")).forEach(nextSource -> {
                result.putIfAbsent(source.getNodeName(), new ArrayList<>());
                result.get(source.getNodeName()).add(nextSource.getNodeName());
                result.putAll(getChildData(nextSource));
            });
        } else {
            if (!Objects.equals(source.getParentNode().getNodeName(), "#text") && !Objects.equals(source.getNodeName(), "#text")) {
                result.putIfAbsent(source.getParentNode().getNodeName(), new ArrayList<>());
                result.get(source.getParentNode().getNodeName()).add(source.getNodeName());
            }
        }
        return result;
    }

    private static HashMap<String, String> getStringData(Node source) {
        HashMap<String, String> result = new HashMap<>();
        if (source.getChildNodes().getLength() >= 1) {
            new IterableNodeList(source.getChildNodes()).stream().filter(node -> !Objects.equals(node.getNodeName(), "#text")).forEach(node -> result.put(node.getNodeName(), node.getTextContent()));
        }
        return result;
    }
}
