package com.smarthome.ui.client;

import com.smarthome.services.LaunchControl;
import com.smarthome.services.service.ServiceType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static com.smarthome.ui.client.ClientUIDimensions.*;

/**
 * @author Graham Murray
 */
public class ClientUI extends JFrame {

    private LaunchControl launchControl;
    private Timer timer;
    private JPanel panel;
    private JButton launchButton;
    private JButton jacuzziButton;
    private JButton lightingButton;
    private JButton televisionButton;
    private JButton mediaPlayerButton;

    public ClientUI(LaunchControl launchControl, String title) {
        super(title);
        this.launchControl = launchControl;

        init();
    }

    public void init() {
        setupPanel();
        setupLaunchButton();
        setupJacuzziButton();
        setupLightingButton();
        setupTelevisionButton();
        setupMediaPlayerButton();
        setupTimer();
        addWindowCloseListener();

        setVisible(true);
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
                launchControl.triggerJacuzziService();
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

    private void setupTelevisionButton() {
        televisionButton = new JButton("Television");
        televisionButton.setBounds(BUTTON_CENTER_X, TELEVISION_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        panel.add(televisionButton);

        televisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void setupMediaPlayerButton() {
        mediaPlayerButton = new JButton("Media Player");
        mediaPlayerButton.setBounds(BUTTON_CENTER_X, MEDIA_PLAYER_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        panel.add(mediaPlayerButton);

        mediaPlayerButton.addActionListener(new ActionListener() {
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
                System.exit(0);
            }
        });
    }

    private void setupTimer() {
        timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jacuzziButton.setEnabled(launchControl.isServiceAvailable(ServiceType.TCP_JACUZZI));
                lightingButton.setEnabled(launchControl.isServiceAvailable(ServiceType.TCP_LIGHTING));
                televisionButton.setEnabled(launchControl.isServiceAvailable(ServiceType.TCP_TELEVISION));
            }
        });

        timer.setRepeats(true);
        timer.start();
    }
}