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

package hr.caellian.core.versionControl;

import hr.caellian.core.util.IterableNodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;

/**
 * This is implementation of version handler. It takes a long time to read version files and it
 * is suggested that a {@code com.caellian.core.SplashScreen} is displayed during initialization period of this
 * object.
 * <p>
 *
 * @author Caellian
 */

public class VersionManager {
    public VersionHistory versions = new VersionHistory();
    public Version currentVersion = null;

    public VersionManager(URL versionFile) {
        this.initVersionData(versionFile);
    }

    private void initVersionData(URL versionFile) {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        InputStream inputStream = null;


        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            inputStream = versionFile.openStream();
            Document document = documentBuilder.parse(inputStream);

            document.normalize();

            Node root = document.getElementsByTagName("versions").item(0);

            for (Node source : new IterableNodeList(root.getChildNodes())) {
                if (Objects.equals(source.getNodeName(), "version")) {
                    Version version = null;
                    String name = null;
                    String changelog = null;
                    URL download = null;

                    for (Node dataNode : new IterableNodeList(source.getChildNodes())) {
                        switch (dataNode.getNodeName()) {
                            case "version":
                                String[] versionStrings = dataNode.getTextContent().split("\\.");
                                version = new Version(Short.parseShort(versionStrings[0]), Short.parseShort(versionStrings[1]), Short.parseShort(versionStrings[2]));
                                break;
                            case "name":
                                String possibleName = dataNode.getTextContent();
                                name = !Objects.equals(possibleName, "") ? possibleName : null;
                                break;
                            case "changelog":
                                String possibleChangelog = dataNode.getTextContent();
                                changelog = !Objects.equals(possibleChangelog, "") ? possibleChangelog : null;
                                break;
                            case "download":
                                String possibleDownload = dataNode.getTextContent();
                                if (!Objects.equals(possibleDownload, "")) {
                                    download = new URL(dataNode.getTextContent());
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    versions.add(new VersionData(version, name != null ? Optional.of(name) : Optional.empty(), changelog != null ? Optional.of(changelog) : Optional.empty(), download != null ? Optional.of(download) : Optional.empty()));
                }
            }
        } catch (Exception e) {
            System.err.println("Unable to import sources from internet.");
            e.printStackTrace();
        } finally {
            try {
                assert inputStream != null : "Input Stream must not be null!";
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return true if there is newer version available.
     */
    public boolean updateAvailable() {
        return this.getLatestVersion().version.compareTo(currentVersion) > 0;
    }

    public VersionData getLatestVersion() {
        return versions.first();
    }
}
