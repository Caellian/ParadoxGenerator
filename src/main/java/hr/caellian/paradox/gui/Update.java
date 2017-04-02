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

import hr.caellian.core.versionControl.VersionData;
import hr.caellian.paradox.lib.Reference;
import hr.caellian.paradox.lib.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by tinsv on 2017-04-01.
 */
public class Update extends JFrame {

    private final VersionData newVersion;
    private JPanel mainPanel = new JPanel(new GridBagLayout());

    public Update(VersionData newVersion) {
        super(Reference.PROGRAM_NAME + " - Update");
        this.newVersion = newVersion;

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.initComponents();
        this.setContentPane(mainPanel);
        this.setIconImage(new ImageIcon(Resources.ICON).getImage());
        this.setResizable(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initComponents() {
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        String title = "[" + newVersion.version.toString() + "]";
        if (newVersion.name.isPresent()) title += " - " + newVersion.name.get();
        JLabel updateTitle = new JLabel(title);
        mainPanel.add(updateTitle, c);

        if (newVersion.changelog.isPresent()) {
            JTextArea updateChangelog = new JTextArea(newVersion.changelog.get());
            updateChangelog.setEditable(false);
            c.gridy = 1;
            c.weighty = 1;
            c.fill = GridBagConstraints.BOTH;
            mainPanel.add(new JScrollPane(updateChangelog), c);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weighty = 0;
        }
        if (newVersion.downloadLink.isPresent() && Desktop.isDesktopSupported()) {
            JButton buttonDownload = new JButton("Download");
            buttonDownload.addActionListener(e -> {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(newVersion.downloadLink.get().toURI());
                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            c.gridy = 2;
            mainPanel.add(buttonDownload, c);
        }

    }
}
