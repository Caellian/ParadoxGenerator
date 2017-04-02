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

package hr.caellian.paradox.gui;

import hr.caellian.paradox.ParadoxGenerator;
import hr.caellian.paradox.configuration.Settings;
import hr.caellian.paradox.lib.Reference;
import hr.caellian.paradox.lib.Resources;
import hr.caellian.paradox.resource.StoredData;
import hr.caellian.paradox.util.Pointer;
import hr.caellian.paradox.util.StringManagement;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Created by Caellian on 30.6.2015., at 3:40.
 */
public class Generator extends JFrame {
    private JPanel mainPanel = new JPanel(new GridBagLayout());
    private JLabel logoLabel = new JLabel(new ImageIcon(Resources.ICON));

    private JLabel generatorLabel = new JLabel("Generator:");
    private JComboBox<String> generatorComboBox = new JComboBox<>();

    private SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 1000, 1);
    private JSpinner countSpinner = new JSpinner(model);
    private JRadioButton listRadioButton = new JRadioButton("List");
    private JRadioButton sentenceRadioButton = new JRadioButton("Sentence");
    private JButton generateButton = new JButton("Generate");

    private JTextArea generatedTextArea = new JTextArea();
    private JScrollPane generatedScrollPane = new JScrollPane(generatedTextArea);

    private String generator = StoredData.sources.get(0);
    private Generator self;
    private Pointer<Boolean> aboutOpen = new Pointer<>(false);

    public Generator() {
        super(Reference.PROGRAM_NAME);
        this.self = this;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.initComponents();
        this.setContentPane(mainPanel);
        this.setIconImage(new ImageIcon(Resources.ICON).getImage());
        this.pack();
        this.setVisible(true);
    }

    private void initComponents() {
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 10;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        logoLabel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!aboutOpen.to) new About(aboutOpen);
            }
        });
        mainPanel.add(logoLabel, c);

        for (String source : StoredData.sources) {
            generatorComboBox.addItem(StoredData.strings.get(source));
        }
        generatorComboBox.setSelectedIndex(0);

        generatorLabel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String source = JOptionPane.showInputDialog(self, "Please enter URL of new source:", "New Source", JOptionPane.QUESTION_MESSAGE);
                if (source != null && !source.isEmpty()) {
                    try {
                        Settings.GENERATOR_SOURCE = new URL(source).toString();
                    } catch (MalformedURLException e1) {
                        JOptionPane.showMessageDialog(self, "You entered malformed URL!", "Bad URL", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        ParadoxGenerator.configuration.updateConfigFile();
                    }
                }
            }
        });
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        mainPanel.add(generatorLabel, c);

        generatorComboBox.addActionListener(e -> StoredData.sources.stream().filter(source -> StoredData.strings.get(source) == generatorComboBox.getSelectedItem()).forEach(source -> generator = source));

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(generatorComboBox, c);
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;

        JPanel generatorSettings = new JPanel(new GridBagLayout());

        TitledBorder border = BorderFactory.createTitledBorder(new LineBorder(Style.NDark.STYLE_NIMBUS_FOCUS), "Generator settings");
        border.setTitleColor(Style.NDark.STYLE_NIMBUS_FOCUS);
        border.setTitlePosition(TitledBorder.TOP);
        generatorSettings.setBorder(border);

        JLabel genenerateCount = new JLabel("Generate Count:");
        c.gridx = 0;
        c.gridy = 0;
        generatorSettings.add(genenerateCount, c);

        c.gridx = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        generatorSettings.add(countSpinner, c);
        c.fill = GridBagConstraints.NONE;

        JLabel generateFormat = new JLabel("Generate Format:");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        generatorSettings.add(generateFormat, c);

        ButtonGroup formatGroup = new ButtonGroup();
        formatGroup.add(listRadioButton);
        formatGroup.add(sentenceRadioButton);

        listRadioButton.setSelected(true);
        c.gridx = 1;
        c.weightx = 1;
        generatorSettings.add(listRadioButton, c);
        c.gridx = 2;
        generatorSettings.add(sentenceRadioButton, c);
        c.weightx = 0;

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(generatorSettings, c);

        c.gridy = 3;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        generatedTextArea.setWrapStyleWord(true);
        mainPanel.add(generatedScrollPane, c);

        generateButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    generatedTextArea.setText("");
                    for (int count = 0; count < (int) countSpinner.getValue(); count++) {
                        if (listRadioButton.isSelected()) {
                            if (Objects.equals(generatedTextArea.getText(), "")) {
                                generatedTextArea.setText(StringManagement.generate(StoredData.data.get(generator)));
                            } else {
                                generatedTextArea.setText(generatedTextArea.getText() + "\n" + StringManagement.generate(StoredData.data.get(generator)));
                            }
                        } else if (sentenceRadioButton.isSelected()) {
                            generatedTextArea.setLineWrap(true);
                            if (Objects.equals(generatedTextArea.getText(), "")) {
                                generatedTextArea.setText(StringManagement.generate(StoredData.data.get(generator)));
                            } else {
                                generatedTextArea.setText(generatedTextArea.getText() + " " + StringManagement.generate(StoredData.data.get(generator)));
                            }
                        }
                    }
                }).run();
            }
        });
        c.gridy = 4;
        c.weighty = 0;
        mainPanel.add(generateButton, c);
    }
}
