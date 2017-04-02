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

package hr.caellian.core.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created on 21.4.2015. at 15:23.
 */
public class SplashScreen extends JFrame {
    //TODO:Add support for transparent pictures.

    public SplashScreen(URL image) {
        this(image, null);
    }

    public SplashScreen(URL image, URL icon) throws HeadlessException {
        try {
            this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(image))));
        } catch (IOException e) {
            System.err.println("Unable to set SPLASH screen image!");
            e.printStackTrace();
        }
        if (icon != null) {
            this.setIconImage(new ImageIcon(icon).getImage());
        }
        this.setResizable(false);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("SplashScreen");
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void showSplash() {
        this.setOpacity(0);
        this.setVisible(true);
        for (float transparency = 0; transparency < 1; transparency += 0.0001) {
            this.setOpacity(transparency);
        }
    }

    public void hideSplash() {
        for (float transparency = 1; transparency > 0; transparency -= 0.01) {
            this.setOpacity(transparency);
        }
        this.setVisible(false);
    }
}
