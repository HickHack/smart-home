package com.smarthome.ui.client;

import com.smarthome.services.LaunchControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.smarthome.ui.client.ClientUIDimensions.*;


public class ClientUI extends JFrame {

    private LaunchControl launchControl;
    private JPanel panel;
    private JButton launchButton;
    private JButton jacuzziButton;
    private JButton lightingButton;

    public ClientUI(LaunchControl launchControl, String title) {
        super("Launch Control - " + title);
        this.launchControl = launchControl;

        setupPanel();
        setupLaunchButton();
        setupJacuzziButton();
        setupLightingButton();
        addWindowCloseListener();
    }

    public void init() {
        this.setVisible(true);
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

        addPaneTitle();
    }

    private void setupLaunchButton() {
        launchButton = new JButton("Launch Services");
        launchButton.setBounds(BUTTON_CENTER_X, LAUNCH_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        panel.add(launchButton);

        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                launchControl.launchServices();
            }
        });
    }

    private void setupJacuzziButton() {
        jacuzziButton = new JButton("Jacuzzi");
        jacuzziButton.setBounds(BUTTON_CENTER_X, JACUZZI_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        panel.add(jacuzziButton);

        jacuzziButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void setupLightingButton() {
        lightingButton = new JButton("Lighting");
        lightingButton.setBounds(BUTTON_CENTER_X, LIGHTING_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        panel.add(lightingButton);

        lightingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void setupLightingButton() {
        lightingButton = new JButton("Lighting");
        lightingButton.setBounds(BUTTON_CENTER_X, LIGHTING_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        panel.add(lightingButton);

        lightingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void addPaneTitle() {
        JLabel titleLabel = new JLabel("Control Panel", SwingConstants.CENTER);
        titleLabel.setBounds(STANDARD_X, STANDARD_Y, TITLE_WIDTH, TITLE_HEIGHT);
        titleLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(titleLabel);
    }

    private void addWindowCloseListener() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                launchControl.shutdown();
                System.exit(0);
            }
        });
    }
}