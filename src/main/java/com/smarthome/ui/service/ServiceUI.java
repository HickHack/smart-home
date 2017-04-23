package com.smarthome.ui.service;

import com.smarthome.services.service.Service;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import static com.smarthome.ui.service.ServiceUIDimensions.*;


public class ServiceUI extends JFrame {

    private JPanel panel;
    private JTextArea outputArea;
    private JTextArea statusArea;
    private Service service;

    public ServiceUI(Service service) {
        super(service.getName() + " - " + service.getType());
        this.service = service;

        setupPanel();
        setupStatusTextArea();
        setupOutputTextArea();
        setupScrollPane();
        addWindowCloseListener();
    }

    public void init() {
        this.setVisible(true);
    }

    public synchronized void updateOutput(String text) {
        Runnable runnable = new Runnable() {
            public void run(){
                if (!text.equals("")) {
                    outputArea.append(new Date().toString() + " - " + text);
                    outputArea.append("\n");
                }

                outputArea.update(outputArea.getGraphics());
            }
        };

        SwingUtilities.invokeLater(runnable);
    }

    public synchronized void updateStatusAttributes(Map<Object, Object> valuesMap) {
        Runnable runnable = new Runnable() {
            public void run(){
                String text = "";
                int count = 0;

                for (Map.Entry<Object, Object> entry : valuesMap.entrySet()) {
                    String pair = entry.getKey() + ": " + entry.getValue() + "\t";

                    if (count % 2 != 0 && count != 0) {
                        text = text + pair + "\n";
                    } else {
                        text = text + pair;
                    }

                    count ++;
                }

                statusArea.setText(text);
            }
        };

        SwingUtilities.invokeLater(runnable);
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

    private void setupStatusTextArea() {
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