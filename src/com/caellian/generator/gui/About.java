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

package com.caellian.generator.gui;

import com.caellian.generator.lib.Reference;
import com.caellian.generator.lib.Resources;

import javax.swing.*;

/**
 * Created by Caellian on 1.7.2015., at 0:20.
 */
public class About extends JFrame
{
	private JPanel    mainPanel;
	private JTextPane thisProgramWasCreatedTextPane;
	private JLabel    logoLabel;

	public About()
	{
		super(Reference.PROGRAM_NAME + " - About");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(mainPanel);
		this.setIconImage(new ImageIcon(Resources.icon).getImage());
		this.pack();
		this.setVisible(true);
	}

	private void createUIComponents()
	{
		logoLabel = new JLabel(new ImageIcon(Resources.icon));
	}
}
