/*
 * Paradox Generator, generator for everything.
 * Copyright (C) 2015 Caellian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.caellian.generator;

import com.caellian.generator.configuration.Settings;
import com.caellian.generator.gui.Generator;
import com.caellian.generator.gui.Style;
import com.caellian.generator.lib.Reference;
import com.caellian.generator.lib.Resources;
import com.caellian.generator.resource.StoredData;
import com.caellyan.core.GUI.SplashScreen;
import com.caellyan.core.configuration.Configuration;

import java.util.Calendar;

public class ParadoxGenerator
{
	public static Configuration configuration;

	public static void main(String[] args)
	{
		System.out.println(Reference.PROGRAM_NAME + ", Copyright (C) " + Calendar.getInstance().get(Calendar.YEAR) + " " + Reference.PROGRAM_AUTHOR);

		SplashScreen splashScreen = new SplashScreen(Resources.splash, Resources.icon);
		splashScreen.showSplash();
		initConfiguration();
		StoredData.initData();
		Style.loadUIStyles();
		splashScreen.hideSplash();

		Generator generatorWindow = new Generator();
	}

	private static void initConfiguration()
	{
		configuration = new Configuration(new Settings());
		configuration.loadConfig();
	}
}
