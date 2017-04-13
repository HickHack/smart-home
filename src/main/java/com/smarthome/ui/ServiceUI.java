package com.smarthome.ui.service;

import com.smarthome.services.service.Service;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import static com.smarthome.ui.UIConstants.UIWIDTH;


public class ServiceUI extends JFrame {

    private JPanel panel;
    private JTextArea outputArea;
    private JScrollPane outputScroll;

    public ServiceUI(Service service) {
        super(service.getName() + " - " + service.getType());
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                service.stop();
            }
        });

        setupPanel();
        setupOutputTextArea();
        setupScrollPane();
    }

    public void init() {
        this.setVisible(true);
    }

    public void clearArea() {
        outputArea.setText("");
    }

    public void updateArea(String message) {
        outputArea.append("\n" + message);
    }


    public Point setPosition(Component component) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - component.getWidth()) / 2);
        return new Point(x, 0);
    }

    private void setupPanel() {
        setResizable(false);
        pack();
        setSize(UIWIDTH, 300);
        setLocation(setPosition(this));
        panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);
        panel.setBounds(0, 0, UIWIDTH - 1, UIWIDTH - 1);
        update(this.getGraphics());
    }

    private void setupOutputTextArea() {
        outputArea = new JTextArea();
    }

    private void setupScrollPane() {
        outputScroll = new JScrollPane(outputArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        outputScroll.setBounds(5, 5, UIWIDTH - 10, 268);
        outputScroll.setViewportView(outputArea);
        panel.add(outputScroll);
    }
}
