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

package hr.caellian.paradox.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created on 11.3.2015. at 20:25 (CET).
 */
public class Style {
    public static void loadUIStyles() {
        initDarkStyle();
    }

    public static void initDarkStyle() {
        UIManager.put("control", NDark.STYLE_CONTROL);
        UIManager.put("text", NDark.STYLE_TEXT);
        UIManager.put("nimbusBase", NDark.STYLE_NIMBUS_BASE);
        UIManager.put("nimbusFocus", NDark.STYLE_NIMBUS_FOCUS);
        UIManager.put("nimbusBorder", NDark.STYLE_NIMBUS_BORDER);
        UIManager.put("nimbusLightBackground", NDark.STYLE_NIMBUS_LIGHT_BACKGROUND);
        UIManager.put("info", NDark.STYLE_INFO);
        UIManager.put("nimbusSelectionBackground", NDark.STYLE_INFO);
        UIManager.put("nimbusOrange", NDark.STYLE_NIMBUS_FOCUS);
        initNimbus();
    }

    private static void initNimbus() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ignored) {
            }
        }
    }

    public static class NDark {
        public static Color STYLE_CONTROL = new Color(40, 40, 40);
        public static Color STYLE_TEXT = new Color(255, 255, 255);
        public static Color STYLE_NIMBUS_BASE = new Color(0, 0, 0);
        public static Color STYLE_NIMBUS_FOCUS = new Color(0, 79, 214);
        public static Color STYLE_NIMBUS_BORDER = new Color(0, 28, 55);
        public static Color STYLE_NIMBUS_LIGHT_BACKGROUND = new Color(50, 50, 50);
        public static Color STYLE_INFO = new Color(80, 80, 80);
    }
}
