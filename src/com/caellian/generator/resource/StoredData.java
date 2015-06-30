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

package com.caellian.generator.resource;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Caellian on 30.6.2015., at 0:53.
 */
public class StoredData
{
	public static final ArrayList<String>            sources = Lists.newArrayList();
	public static final HashMultimap<String, String> data    = HashMultimap.create();
	public static final HashMap<String, String>      strings = Maps.newHashMap();

	public static void initData()
	{
		DataReader.importSources();
	}
}
