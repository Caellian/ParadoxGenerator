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

package com.caellian.generator.util;

import com.caellian.generator.resource.StoredData;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/**
 * Created by Caellian on 30.6.2015., at 1:56.
 */
public class StringManagement
{
	public static String generate(Set<String> values)
	{
		return idToText(getRandom(values, "").split(" "));
	}

	public static String getRandom(Set<String> values, String built)
	{
		if (values.size() >= 1)
		{
			Random random = new Random();
			int rndNumber = random.nextInt(values.size());
			return getRandom(StoredData.data.get((String) values.toArray()[rndNumber]), built + values.toArray()[rndNumber] + " ");
		}
		else
		{
			if (Objects.equals(built, ""))
			{
				return "";
			}
			else
			{
				return built.substring(0, built.length() - 1);
			}
		}
	}

	public static String idToText(String[] generated)
	{
		String result = "";
		for (String key : generated)
		{
			HashMap<String, String> strings = Maps.newHashMap(StoredData.strings);
			result += strings.get(key) + " ";
		}
		return result.substring(0, result.length() - 1);
	}
}
