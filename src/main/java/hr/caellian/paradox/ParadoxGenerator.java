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

package hr.caellian.paradox;

import hr.caellian.paradox.gui.Splash;
import hr.caellian.core.configuration.Configuration;
import hr.caellian.core.versionControl.VersionManager;
import hr.caellian.paradox.configuration.Settings;
import hr.caellian.paradox.gui.Generator;
import hr.caellian.paradox.gui.Style;
import hr.caellian.paradox.gui.Update;
import hr.caellian.paradox.lib.Reference;
import hr.caellian.paradox.lib.Resources;
import hr.caellian.paradox.resource.ItemManager;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class ParadoxGenerator {
    public static Configuration configuration;

    public static void main(String[] args) {
        System.out.println(Reference.PROGRAM_NAME + ", Copyright (C) 2017. " + Reference.PROGRAM_AUTHOR);

        Style.loadUIStyles();

        Splash splash = new Splash() {
            float percentOld = 0f;

            @Override
            public void setupGraphics(Graphics2D g) {
                g.setBackground(new Color(30, 30, 30));
                g.setPaintMode();
                g.setColor(Style.NDark.STYLE_NIMBUS_FOCUS);
            }

            @Override
            public void renderState(Graphics2D g, Object... state) {
                float percentDone = (float) state[0];
                String message = (String) state[1];

                g.clearRect(25, 543, 350, 12);
                g.drawString(message, 25, 553);

                for (float i=0.0f; i<=1; i += 0.2f) {
                    g.fill(new Rectangle2D.Double(25, 560, (int) (350 * (percentOld + (percentDone - percentOld) * i)), 5));
                    splashScreen.update();
                }
                percentOld = percentDone;
            }
        };

        splash.renderState(0.1f, "Loading configuration...");
        handleConfiguration();
        if (Settings.CHECK_FOR_UPDATES) {
            splash.renderState(0.3f, "Checking for updates...");
            VersionManager versionManager = new VersionManager(Resources.VERSIONS_FILE);
            versionManager.currentVersion = Reference.PROGRAM_VERSION;
            if (versionManager.updateAvailable()) {
                Update updateNotifier = new Update(versionManager.getLatestVersion());
            }
        }
        splash.renderState(0.6f, "Refreshing data...");
        ItemManager.refreshData();

        splash.renderState(1f, "Opening GUI...");
        Generator generatorWindow = new Generator();
        splash.close();
    }

    private static void handleConfiguration() {
        configuration = new Configuration(new Settings());
        configuration.loadConfig();
    }
}
