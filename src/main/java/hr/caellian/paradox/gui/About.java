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

import hr.caellian.paradox.lib.Reference;
import hr.caellian.paradox.lib.Resources;
import hr.caellian.paradox.util.Pointer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Caellian on 1.7.2015., at 0:20.
 */
public class About extends JFrame {
    private static final String aboutText = "This program was created as an universal way of generating string compounds.\n" +
            "It is a free program and if you paid for it, you should require a refund (unless you donated money, of course).\n" +
            "This is an open source program and source code can be found in GitHub repository here: https://github.com/Caellian/Paradox_Generator\n" +
            "\n" +
            "This program is protected under MIT license which can be found here: https://github.com/Caellian/Paradox_Generator/master/LICENSE.txt";

    private JPanel mainPanel;
    private JFrame self;

    public About(Pointer<Boolean> aboutOpen) {
        super(Reference.PROGRAM_NAME + " - About");
        self = this;
        this.setSize(450, 400);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.initComponents();
        this.setContentPane(mainPanel);
        this.setIconImage(new ImageIcon(Resources.ICON).getImage());
        this.setResizable(false);
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                aboutOpen.to = true;
            }

            @Override
            public void windowClosing(WindowEvent e) {
                aboutOpen.to = false;
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel logoLabel = new JLabel(new ImageIcon(Resources.ICON));
        mainPanel.add(logoLabel, c);

        JTextPane aboutTextPane = new JTextPane();
        aboutTextPane.setEditable(false);
        aboutTextPane.setBackground(Style.NDark.STYLE_CONTROL);
        aboutTextPane.setText(aboutText);
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        mainPanel.add(aboutTextPane, c);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> self.dispatchEvent(new WindowEvent(self, WindowEvent.WINDOW_CLOSING)));
        c.gridy = 2;
        c.weighty = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(closeButton, c);
    }
}
