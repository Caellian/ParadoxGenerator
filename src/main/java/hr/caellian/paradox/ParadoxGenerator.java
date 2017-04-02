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

import hr.caellian.core.GUI.SplashScreen;
import hr.caellian.core.configuration.Configuration;
import hr.caellian.core.versionControl.VersionManager;
import hr.caellian.paradox.configuration.Settings;
import hr.caellian.paradox.gui.Generator;
import hr.caellian.paradox.gui.Style;
import hr.caellian.paradox.gui.Update;
import hr.caellian.paradox.lib.Reference;
import hr.caellian.paradox.lib.Resources;
import hr.caellian.paradox.resource.StoredData;

public class ParadoxGenerator {
    public static Configuration configuration;

    public static void main(String[] args) {
        System.out.println(Reference.PROGRAM_NAME + ", Copyright (C) 2017. " + Reference.PROGRAM_AUTHOR);

        SplashScreen splashScreen = new SplashScreen(Resources.SPLASH, Resources.ICON);
        splashScreen.showSplash();
        Style.loadUIStyles();

        initConfiguration();
        if (Settings.CHECK_FOR_UPDATES) {
            VersionManager versionManager = new VersionManager(Resources.VERSIONS_FILE);
            versionManager.currentVersion = Reference.PROGRAM_VERSION;
            if (versionManager.updateAvailable()) {
                Update updateNotifier = new Update(versionManager.getLatestVersion());
            }
        }
        StoredData.initData();

        Generator generatorWindow = new Generator();
        splashScreen.hideSplash();
    }

    private static void initConfiguration() {
        configuration = new Configuration(new Settings());
        configuration.loadConfig();
    }
}
