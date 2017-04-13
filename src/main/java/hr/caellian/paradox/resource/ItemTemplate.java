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

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * Created by tinsv on 2017-04-09.
 */
public class ItemTemplate extends Item {
    private static final String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()";
    private static final String lower = "abcdefghijklmnopqrstuvwxyz";
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String symbols = "!@#$%^&*()";

    Random random = new Random();
    ArrayList<String> sourceData = null;

    public ItemTemplate(Item parent, Node origin, LinkedList<Item> data) {
        super(parent, origin, data);
        ItemManager.templates.put(getNestedID(), this);
    }

    public ItemTemplate(Item parent, Node origin, ArrayList<Item> data) {
        super(parent, origin, data);
        ItemManager.templates.put(getNestedID(), this);
    }

    public ItemTemplate(Item parent, Node origin, LinkedHashMap<Item, Float> data) {
        super(parent, origin, data);
        ItemManager.templates.put(getNestedID(), this);
    }

    public ItemTemplate(Item parent, Node origin, HashMap<Item, Float> data) {
        super(parent, origin, data);
        ItemManager.templates.put(getNestedID(), this);
    }

    public String getText(String localization, ItemGenerator generator) {
        StringBuilder result = new StringBuilder();

        NamedNodeMap nnm = origin.getAttributes();
        if (nnm.getNamedItem("function") != null) {
            String function = nnm.getNamedItem("function").getNodeValue();

            switch (function) {
                case "append":
                    String separator = nnm.getNamedItem("separator") != null ? ItemManager.process(nnm.getNamedItem("separator").getNodeValue(), localization, generator) : " ";
                    int cnt = 0;
                    for (Item it : children) {
                        result.append(it.getText(localization, generator)).append(++cnt != children.size() ? separator : "");
                    }
                    break;
                case "random":
                    String type = nnm.getNamedItem("type").getNodeValue();
                    switch (type) {
                        case "enum":
                            if (nnm.getNamedItem("source") != null) {
                                if (sourceData == null) {
                                    sourceData = new ArrayList<>();

                                    String source = ItemManager.process(nnm.getNamedItem("source").getNodeValue(), localization, generator);
                                    try {
                                        URI sourceURI = new URI(source);

                                        try {
                                            InputStream stream = sourceURI.toURL().openStream();
                                            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                                            String line;
                                            while ((line = reader.readLine()) != null){
                                                sourceData.add(line);
                                            }

                                        } catch (IOException e1) {
                                            System.err.println("Couldn't stream data from URL: " + source);
                                            break;
                                        }
                                    } catch (URISyntaxException e) {
                                        try {
                                            URL sourceURL = new URL(source);

                                            try {
                                                InputStream stream = sourceURL.openStream();
                                                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                                                String line;
                                                while ((line = reader.readLine()) != null){
                                                    sourceData.add(line);
                                                }

                                            } catch (IOException e1) {
                                                System.err.println("Couldn't stream data from URL: " + source);
                                                break;
                                            }
                                        } catch (MalformedURLException e1) {
                                            File sourceFile = new File(source);
                                            if (!sourceFile.exists() || !sourceFile.isFile()) {
                                                System.err.println("Source for item " + origin.getNodeName() + " is neither a valid URI/URL or a file: " + source);
                                                break;
                                            }

                                            try {
                                                InputStream stream = sourceFile.toURI().toURL().openStream();
                                                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                                                String line;
                                                while ((line = reader.readLine()) != null){
                                                    sourceData.add(line);
                                                }
                                            } catch (IOException e2) {
                                                System.err.println("Couldn't stream data from file: " + source);
                                                break;
                                            }
                                        }
                                    }
                                }
                                result.append(sourceData.get(random.nextInt(sourceData.size())));
                            } else {
                                result.append(getChild().getText(localization, generator));
                            }
                            break;
                        case "int":
                            int mini = Integer.parseInt(ItemManager.process(nnm.getNamedItem("min").getNodeValue(), localization, generator));
                            int maxi = Integer.parseInt(ItemManager.process(nnm.getNamedItem("max").getNodeValue(), localization, generator));
                            result.append(mini + random.nextInt(maxi - mini));
                            break;
                        case "float":
                            float minf = Integer.parseInt(ItemManager.process(nnm.getNamedItem("min").getNodeValue(), localization, generator));
                            float maxf = Integer.parseInt(ItemManager.process(nnm.getNamedItem("max").getNodeValue(), localization, generator));
                            result.append(minf + (maxf - minf) * random.nextFloat());
                            break;
                        case "double":
                            float mind = Integer.parseInt(ItemManager.process(nnm.getNamedItem("min").getNodeValue(), localization, generator));
                            float maxd = Integer.parseInt(ItemManager.process(nnm.getNamedItem("max").getNodeValue(), localization, generator));
                            result.append(mind + (maxd - mind) * random.nextDouble());
                            break;
                        case "boolean":
                            result.append(random.nextBoolean());
                            break;
                        case "uuid":
                            result.append(UUID.randomUUID().toString());
                            break;
                        case "dice":
                            int roll = 0;
                            String[] dice = ItemManager.process(nnm.getNamedItem("roll").getNodeValue(), localization, generator).split("\\+");
                            for (String one : dice) {
                                String[] num = one.split("d");
                                int count = Integer.valueOf(num[0].equals("") ? "1" : num[0]);
                                int high = Integer.valueOf(num[1]);

                                for (int i = 0; i < count; i++) {
                                    roll += 1 + random.nextInt(high);
                                }
                            }
                            result.append(roll);
                            break;
                        case "year":
                            int miny = Integer.parseInt(ItemManager.process(nnm.getNamedItem("min").getNodeValue(), localization, generator));
                            int maxy = Integer.parseInt(ItemManager.process(nnm.getNamedItem("max").getNodeValue(), localization, generator));
                            int year = miny + random.nextInt(maxy - miny);
                            if (year < 0) {
                                result.append(-year).append(" BC");
                            } else {
                                result.append(year);
                            }
                            break;
                        case "character":
                            String pool = letters;

                            if(nnm.getNamedItem("pool") != null){
                                pool = ItemManager.process(nnm.getNamedItem("pool").getNodeValue(), localization, generator);
                            }

                            if(nnm.getNamedItem("alpha") != null){
                                boolean alphaFlag = Boolean.parseBoolean(ItemManager.process(nnm.getNamedItem("alpha").getNodeValue(), localization, generator));
                                if (alphaFlag) {
                                    StringBuilder resPool = new StringBuilder();
                                    for (char c : pool.toCharArray()) {
                                        if (alpha.contains(c + "")) {
                                            resPool.append(c);
                                        }
                                    }
                                    pool = resPool.toString();
                                }
                            }

                            if(nnm.getNamedItem("symbols") != null){
                                boolean symbolsFlag = Boolean.parseBoolean(ItemManager.process(nnm.getNamedItem("symbols").getNodeValue(), localization, generator));
                                if (symbolsFlag) {
                                    StringBuilder resPool = new StringBuilder();
                                    for (char c : pool.toCharArray()) {
                                        if (symbols.contains(c + "")) {
                                            resPool.append(c);
                                        }
                                    }
                                    pool = resPool.toString();
                                }
                            }

                            if(nnm.getNamedItem("alpha") != null){
                                String caseFlag = ItemManager.process(nnm.getNamedItem("case").getNodeValue(), localization, generator);
                                if (caseFlag.toLowerCase().equals("lower")) {
                                    StringBuilder resPool = new StringBuilder();
                                    for (char c : pool.toCharArray()) {
                                        if (lower.contains(c + "")) {
                                            resPool.append(c);
                                        }
                                    }
                                    pool = resPool.toString();
                                } else if (caseFlag.toUpperCase().equals("UPPER")) {
                                    StringBuilder resPool = new StringBuilder();
                                    for (char c : pool.toCharArray()) {
                                        if (upper.contains(c + "")) {
                                            resPool.append(c);
                                        }
                                    }
                                    pool = resPool.toString();
                                }
                            }
                            result.append(pool.charAt(random.nextInt(pool.length())));
                            break;
                        default:
                            break;
                    }
                    break;
                case "link":
                    switch (getNestedID()) {
                        case "year.now":
                            result.append(Calendar.getInstance().get(Calendar.YEAR));
                            break;
                        case "month.now":
                            result.append(Calendar.getInstance().get(Calendar.MONTH));
                            break;
                        case "day.now":
                            result.append(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                            break;
                        case "time.now":
                            result.append(Calendar.getInstance().get(Calendar.HOUR)).append(":");
                            result.append(Calendar.getInstance().get(Calendar.MINUTE)).append(" ");
                            result.append((Calendar.getInstance().get(Calendar.AM_PM)) == 0 ? "AM" : "PM");
                            break;
                        case "time.now.long":
                            result.append(Calendar.getInstance().get(Calendar.HOUR)).append(":");
                            result.append(Calendar.getInstance().get(Calendar.MINUTE)).append(":");
                            result.append(Calendar.getInstance().get(Calendar.SECOND)).append(" ");
                            result.append((Calendar.getInstance().get(Calendar.AM_PM)) == 0 ? "AM" : "PM");
                            break;
                        default:
                            break;
                    }
                default:
                    break;
            }
        }
        return result.toString();
    }
}
