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

import hr.caellian.core.versionControl.VersionManager;
import hr.caellian.paradox.ParadoxGenerator;
import hr.caellian.paradox.configuration.Settings;
import hr.caellian.paradox.lib.Reference;
import hr.caellian.paradox.lib.Resources;
import hr.caellian.paradox.resource.ItemGenerator;
import hr.caellian.paradox.resource.ItemManager;
import hr.caellian.paradox.util.Pointer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

/**
 * Created by Caellian on 30.6.2015., at 3:40.
 */
public class Generator extends JFrame {
    private JPanel mainPanel = new JPanel(new GridBagLayout());
    private JLabel logoLabel = new JLabel(new ImageIcon(Resources.ICON));

    private JMenuBar mainMenu = new JMenuBar();
    private JMenu file = new JMenu("File");
    private JMenuItem openFile = new JMenuItem("Open...");
    private JMenuItem openURL = new JMenuItem("Open URL...");
    private JMenuItem refresh = new JMenuItem("Refresh");
    private JMenuItem settings = new JMenuItem("Settings...");
    private JMenuItem exit = new JMenuItem("Exit");

    private JMenu help = new JMenu("Help");
    private JMenuItem documentation = new JMenuItem("Documentation");
    private JMenuItem update = new JMenuItem("Check for Updates...");
    private JMenuItem about = new JMenuItem("About");

    private JLabel generatorLabel = new JLabel("Generator:");
    private JComboBox<String> generatorComboBox = new JComboBox<>();

    private SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 1000, 1);
    private JSpinner countSpinner = new JSpinner(model);
    private JRadioButton listRadioButton = new JRadioButton("List");
    private JRadioButton sentenceRadioButton = new JRadioButton("Sentence");
    private JButton generateButton = new JButton("Generate");

    private JTextArea generatedTextArea = new JTextArea();
    private JScrollPane generatedScrollPane = new JScrollPane(generatedTextArea);

    private ItemGenerator generator;

    private Generator self;
    private Pointer<Boolean> aboutOpen = new Pointer<>(false);
    private Pointer<Boolean> settingsOpen = new Pointer<>(false);

    public Generator() {
        super(Reference.PROGRAM_NAME);
        this.self = this;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setJMenuBar(mainMenu);
        this.initComponents();
        this.setIconImage(new ImageIcon(Resources.ICON).getImage());
        this.setMinimumSize(new Dimension(500, 500));
        this.pack();
        this.setVisible(true);
    }

    private void initComponents() {
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        file.setMnemonic('F');

        openFile.setMnemonic('O');
        openFile.addActionListener(e -> {
            FileDialog fileDialog = new FileDialog(self,"Open Source", FileDialog.LOAD);
            fileDialog.setFilenameFilter((dir, name) -> name.endsWith(".xml"));
            fileDialog.setVisible(true);

            String old = Settings.GENERATOR_SOURCE;
            try {
                Settings.GENERATOR_SOURCE = new File(fileDialog.getDirectory(), fileDialog.getFile()).toURI().toURL().toString();
            } catch (MalformedURLException urle) {
                Settings.GENERATOR_SOURCE = old;
                JOptionPane.showMessageDialog(self, "Paradox Generator received a malformed URL!", "Bad File URL", JOptionPane.ERROR_MESSAGE);
            } finally {
                ParadoxGenerator.configuration.updateConfigFile();
                ItemManager.refreshData();

                generatorComboBox.removeAllItems();
                for (ItemGenerator generator : ItemManager.generators) {
                    generatorComboBox.addItem(generator.getText(Settings.LOCALIZATION));
                }
                if(generatorComboBox.getItemCount() > 0) {
                    generator = ItemManager.generators.get(0);
                    generatorComboBox.setSelectedIndex(0);
                }
            }
        });

        file.add(openFile);

        openURL.addActionListener(l -> {
            String old = Settings.GENERATOR_SOURCE;
            String source = JOptionPane.showInputDialog(self, "Please enter URL of new source:", "New Source", JOptionPane.QUESTION_MESSAGE);
            if (source != null && !source.isEmpty()) {
                try {
                    Settings.GENERATOR_SOURCE = new URL(source).toString();
                } catch (MalformedURLException e1) {
                    Settings.GENERATOR_SOURCE = old;
                    JOptionPane.showMessageDialog(self, "You entered malformed URL!", "Bad URL", JOptionPane.ERROR_MESSAGE);
                } finally {
                    ParadoxGenerator.configuration.updateConfigFile();
                    ItemManager.refreshData();

                    generatorComboBox.removeAllItems();
                    for (ItemGenerator generator : ItemManager.generators) {
                        generatorComboBox.addItem(generator.getText(Settings.LOCALIZATION));
                    }
                    if(generatorComboBox.getItemCount() > 0) {
                        generator = ItemManager.generators.get(0);
                        generatorComboBox.setSelectedIndex(0);
                    }
                }
            }
        });
        file.add(openURL);

        refresh.setMnemonic('R');
        refresh.addActionListener(l -> {
            ItemManager.refreshData();

            generatorComboBox.removeAllItems();
            for (ItemGenerator generator : ItemManager.generators) {
                generatorComboBox.addItem(generator.getText(Settings.LOCALIZATION));
            }
            if(generatorComboBox.getItemCount() > 0) {
                generator = ItemManager.generators.get(0);
                generatorComboBox.setSelectedIndex(0);
            }
        });
        file.add(refresh);

        file.addSeparator();

        settings.setMnemonic('S');
        settings.addActionListener(l -> {
            if (!settingsOpen.to) new Preferences(settingsOpen);
        });
//        file.add(settings); TODO: Add Settings GUI

        file.addSeparator();

        exit.setMnemonic('x');
        exit.addActionListener(l -> self.dispatchEvent(new WindowEvent(self, WindowEvent.WINDOW_CLOSING)));
        file.add(exit);

        mainMenu.add(file);

        help.setMnemonic('H');

        documentation.setMnemonic('D');
        documentation.addActionListener(l -> {
            if(Desktop.isDesktopSupported())
            {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/Caellian/ParadoxGenerator/wiki"));
                } catch (IOException | URISyntaxException ignore) {}
            } else {
                JOptionPane.showMessageDialog(self, "https://github.com/Caellian/ParadoxGenerator/wiki", "Documentation URL", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        help.add(documentation);

        help.addSeparator();

        update.setMnemonic('U');
        update.addActionListener(l -> {
            VersionManager versionManager = new VersionManager(Resources.VERSIONS_FILE);
            versionManager.currentVersion = Reference.PROGRAM_VERSION;
            if (versionManager.updateAvailable()) {
                Update updateNotifier = new Update(versionManager.getLatestVersion());
            } else {
                JOptionPane.showMessageDialog(self, "Paradox Generator is up to date!", "Update Not Available", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        help.add(update);

        about.setMnemonic('A');
        about.addActionListener(l -> {
            if (!aboutOpen.to) new About(aboutOpen);
        });
        help.add(about);

        mainMenu.add(help);

        GridBagConstraints c = new GridBagConstraints();

        c.ipadx = 10;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
//        mainPanel.add(logoLabel, c);

        for (ItemGenerator generator : ItemManager.generators) {
            generatorComboBox.addItem(generator.getText(Settings.LOCALIZATION));
        }
        if(generatorComboBox.getItemCount() > 0) {
            generator = ItemManager.generators.get(0);
            generatorComboBox.setSelectedIndex(0);
        }

        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0;
        mainPanel.add(generatorLabel, c);

        generatorComboBox.addActionListener(l ->
                ItemManager.generators.stream()
                        .filter(source -> Objects.equals(source.getText(Settings.LOCALIZATION), generatorComboBox.getSelectedItem()))
                        .forEach(source -> generator = source));

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
        generatedTextArea.setLineWrap(true);
        generatedTextArea.setWrapStyleWord(true);
        mainPanel.add(generatedScrollPane, c);

        generateButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    generatedTextArea.setText("");
                    for (int count = 0, length = 0; count < (int) countSpinner.getValue(); count++) {
                        String insterted = generator.getChild().getText(Settings.LOCALIZATION, generator) + (listRadioButton.isSelected() ? "\n" : " ");
                        try {
                            Document doc = generatedTextArea.getDocument();
                            doc.insertString(length, insterted, null);
                        } catch (BadLocationException ignored) {} finally {
                            length += insterted.length();
                        }
                    }
                }).start();
            }
        });
        c.gridy = 4;
        c.weighty = 0;
        mainPanel.add(generateButton, c);
    }
}
