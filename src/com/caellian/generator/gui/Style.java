/*
 * Paradox Generator, generator for everything
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.caellian.generator.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created on 11.3.2015. at 20:25 (CET).
 */
public class Style
{
	public static void loadUIStyles()
	{
		initDarkStyle();
	}

	public static void initDarkStyle()
	{
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

	private static void initNimbus()
	{
		try
		{
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e)
		{
			try
			{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception ignored)
			{
			}
		}
	}

	public static class NDark
	{
		public static Color STYLE_CONTROL                 = new Color(40, 40, 40);
		public static Color STYLE_TEXT                    = new Color(255, 255, 255);
		public static Color STYLE_NIMBUS_BASE             = new Color(0, 0, 0);
		public static Color STYLE_NIMBUS_FOCUS            = new Color(214, 100, 0);
		public static Color STYLE_NIMBUS_BORDER           = new Color(55, 26, 0);
		public static Color STYLE_NIMBUS_LIGHT_BACKGROUND = new Color(50, 50, 50);
		public static Color STYLE_INFO                    = new Color(80, 80, 80);
	}
}
