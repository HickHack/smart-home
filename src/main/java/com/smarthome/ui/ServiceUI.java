package com.smarthome.ui;

import com.smarthome.services.service.Service;
import com.smarthome.services.service.ServiceType;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import static com.smarthome.ui.UIConstants.*;


public class ServiceUI extends JFrame {

    private JPanel panel;
    private JTextArea outputArea;
    private JTextArea statusArea;
    private Service service;

    public ServiceUI(Service service) {
        super(service.getName() + " - " + service.getType());
        this.service = service;

        setupPanel();
        setupStatusPanel();
        setupOutputTextArea();
        setupScrollPane();
        addWindowCloseListener();
    }

    public void init() {
        this.setVisible(true);
    }

    public void updateOutput(String message) {
        outputArea.append("\n" + new Date().toString() + " - " + message);
    }

    public void updateStatusAttributes(Map<Object, Object> valuesMap) {
        String text = "";
        int count = 0;

        for (Map.Entry<Object, Object> e : valuesMap.entrySet()) {
            String pair =  e.getKey() + ": " + e.getValue() + "\t";

            if (count % 2 != 0 && count != 0) {
                text = text + pair + "\n";
            } else {
                text = text + pair;
            }

            count ++;
        }

        statusArea.setText(text);
    }

    public Point setPosition(Component component) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - component.getWidth()) / 2);
        return new Point(x, 0);
    }

    private void setupPanel() {
        setResizable(false);
        pack();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocation(setPosition(this));
        panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(null);
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_WIDTH);
        update(this.getGraphics());
    }

    private void setupOutputTextArea() {
        outputArea = new JTextArea();
        outputArea.setLineWrap(true);
        outputArea.setEditable(false);
    }

    private void setupScrollPane() {
        JScrollPane outputScroll = new JScrollPane(outputArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        outputScroll.setBounds(SCROLLPANE_X, SCROLLPANE_Y, SCROLLPANE_WIDTH, SCROLLPANE_HEIGHT);
        outputScroll.setViewportView(outputArea);
        outputScroll.setBorder(getCompoundBorder());
        panel.add(outputScroll);
    }

    private void setupStatusPanel() {
        statusArea  = new JTextArea();
        statusArea.setLineWrap(true);
        statusArea.setEditable(false);
        statusArea.setBorder(getCompoundBorder());
        statusArea.setBounds(STANDARD_X, STANDARD_Y, STATUS_AREA_WIDTH, STATUS_AREA_HEIGHT);
        panel.add(statusArea);
    }

    private CompoundBorder getCompoundBorder() {
        Border border = BorderFactory.createLineBorder(Color.GRAY);
        return BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(
                BORDER_DIMENSION, BORDER_DIMENSION, BORDER_DIMENSION, BORDER_DIMENSION));
    }

    private void addWindowCloseListener() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                service.stop();
            }
        });
    }
}
