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

package com.caellian.generator.lib;

import com.caellian.generator.ParadoxGenerator;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Caellian on 3.7.2015., at 1:51.
 */
public class Resources
{
	public static final URL splash = ParadoxGenerator.class.getResource("/splash/splash.png");
	public static final URL icon   = ParadoxGenerator.class.getResource("/icon/64.png");

	public static final URL versionsFile = getVersionsFile();

	private static URL getVersionsFile()
	{
		try
		{
			return new URL("https://raw.githubusercontent.com/Caellian/Paradox_Generator/master/versions.xml");
		} catch (MalformedURLException e)
		{
			return null;
		}
	}
}
