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

package com.caellian.generator.resource;

import com.caellian.generator.configuration.Settings;
import com.caellyan.core.util.IterableNodeList;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Caellian on 30.6.2015., at 0:45.
 */
public class DataReader
{
	public static void importSources()
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		InputStream inputStream = null;

		try
		{
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			inputStream = new URL(Settings.GENERATOR_SOURCE).openStream();
			Document document = documentBuilder.parse(inputStream);

			document.normalize();

			Node root = document.getElementsByTagName("generator").item(0);

			for (Node source : new IterableNodeList(root.getChildNodes()))
			{
				if (Objects.equals(source.getNodeName(), "strings"))
				{
					StoredData.strings.putAll(getStringData(source));
				}
				else if (source.getChildNodes().getLength() >= 1)
				{
					StoredData.sources.add(source.getNodeName());
					StoredData.data.putAll(getChildMultidata(source));
				}
			}
		} catch (Exception e)
		{
			System.err.println("Unable to import sources from internet.");
			e.printStackTrace();
		} finally
		{
			try
			{
				assert inputStream != null : "Input Stream mustn't be null!";
				inputStream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static HashMultimap<String, String> getChildMultidata(Node source)
	{
		HashMultimap<String, String> result = HashMultimap.create();
		if (source.getChildNodes().getLength() >= 1)
		{
			new IterableNodeList(source.getChildNodes()).stream().filter(nextSource->!Objects.equals(source.getNodeName(), "#text") && !Objects.equals(nextSource.getNodeName(), "#text")).forEach(nextSource->{
				result.put(source.getNodeName(), nextSource.getNodeName());
				result.putAll(getChildMultidata(nextSource));
			});
		}
		else
		{
			if (!Objects.equals(source.getParentNode().getNodeName(), "#text") && !Objects.equals(source.getNodeName(), "#text"))
			{
				result.put(source.getParentNode().getNodeName(), source.getNodeName());
			}
		}
		return result;
	}

	private static HashMap<String, String> getStringData(Node source)
	{
		HashMap<String, String> result = Maps.newHashMap();
		if (source.getChildNodes().getLength() >= 1)
		{
			new IterableNodeList(source.getChildNodes()).stream().filter(node->!Objects.equals(node.getNodeName(), "#text")).forEach(node->result.put(node.getNodeName(), node.getTextContent()));
		}
		return result;
	}
}
