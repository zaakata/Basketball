/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basket;

/**
 *
 * @author gugul
 */
import javax.swing.*;
import java.awt.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author gugul
 */
public class CourtPanel extends JPanel {
    private BufferedImage courtImage;
    private int eventX, eventY;
    private int width, height;

    /**
     *
     * @param width
     * @param height
     */
    public CourtPanel(int width, int height) {
        this.width = width;
        this.height = height;
        try {
            courtImage = ImageIO.read(getClass().getResource("map.jpg"));
            courtImage = resizeImage(courtImage, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(courtImage, 0, 0, this);
        g.setColor(Color.BLUE);
        g.fillOval(eventX - 5, eventY - 5, 10, 10);
    }

    /**
     *
     * @param x
     * @param y
     */
    public void setEventCoordinates(int x, int y) {
        eventX = x;
        eventY = y;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resizedImage;
    }
}

