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

import com.caellian.generator.lib.Reference;
import com.caellian.generator.resource.StoredData;
import com.caellian.generator.util.StringManagement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

/**
 * Created by Caellian on 30.6.2015., at 3:40.
 */
public class Generator extends JFrame
{
	private JPanel       mainPanel;
	private JScrollPane  scrollPane;
	private JTextArea    generatedTextArea;
	private JComboBox    generatorComboBox;
	private JSpinner     countSpinner;
	private JRadioButton listRadioButton;
	private JRadioButton sentenceRadioButton;
	private JButton      generateButton;

	private String generator = "";

	public Generator()
	{
		super(Reference.PROGRAM_NAME);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setContentPane(mainPanel);
		this.initComponents();
		this.pack();
		this.setVisible(true);
	}

	private void initComponents()
	{
		countSpinner.addChangeListener(e->{
			if ((int) countSpinner.getValue() < 1)
			{
				countSpinner.setValue(1);
			}
		});

		ButtonGroup format = new ButtonGroup();
		format.add(listRadioButton);
		format.add(sentenceRadioButton);

		generator = StoredData.sources.get(0);
		for (String source : StoredData.sources)
		{
			generatorComboBox.addItem(StoredData.strings.get(source));
		}

		generatorComboBox.addActionListener(e->StoredData.sources.stream().filter(source->StoredData.strings.get(source) == generatorComboBox.getSelectedItem()).forEach(source->generator = source));

		generateButton.addActionListener(new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new Thread(()->{
					generatedTextArea.setText("");
					for (int count = 0; count < (int) countSpinner.getValue(); count++)
					{
						if (listRadioButton.isSelected())
						{
							if (Objects.equals(generatedTextArea.getText(), ""))
							{
								generatedTextArea.setText(StringManagement.generate(StoredData.data.get(generator)));
							}
							else
							{
								generatedTextArea.setText(generatedTextArea.getText() + "\n" + StringManagement.generate(StoredData.data.get(generator)));
							}
						}
						else if (sentenceRadioButton.isSelected())
						{
							generatedTextArea.setLineWrap(true);
							if (Objects.equals(generatedTextArea.getText(), ""))
							{
								generatedTextArea.setText(StringManagement.generate(StoredData.data.get(generator)));
							}
							else
							{
								generatedTextArea.setText(generatedTextArea.getText() + " " + StringManagement.generate(StoredData.data.get(generator)));
							}
						}
					}
				}).run();
			}
		});
	}
}
