package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.MainWindow;
import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

public class LineDrawer {

    private final MainWindow mainWindow;
    private Point startPoint;
    private Point endPoint;

    public LineDrawer(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void drawDottedLineBetween(ModerationCard movingCard, ModerationCard magneticCard) {
        if (movingCard.isDistancedMagnet() != mainWindow.isHasLineDistance()) {
            clearBackground();
            mainWindow.setHasLineDistance(movingCard.isDistancedMagnet());
        }

        Graphics2D g2d = (Graphics2D) mainWindow.getGraphics();
        float dashPhase = 0f;
        float dash[] = {5.0f, 5.0f};
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.5f, dash, dashPhase));
        int distance = mainWindow.isHasLineDistance() ? magneticCard.getMAGNETRANGE() : 0;
        switch (movingCard.getSnapDirection()) {
            case EAST:
                startPoint.setLocation(magneticCard.getBounds().x - distance, 0);
                endPoint.setLocation(magneticCard.getBounds().x - distance, mainWindow.getContentPane().getMaximumSize().height);
                g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                break;
            case WEST:
                g2d.drawLine(magneticCard.getBounds().x + magneticCard.getBounds().width + distance, 0, magneticCard.getBounds().x + magneticCard.getBounds().width + distance, mainWindow.getContentPane().getMaximumSize().height);
                break;
            case NORTH:
                g2d.drawLine(0, magneticCard.getY() - distance, mainWindow.getContentPane().getMaximumSize().width, magneticCard.getY() - distance);
                break;
            case SOUTH:
                g2d.drawLine(0, magneticCard.getY() + magneticCard.getBounds().height + distance, mainWindow.getContentPane().getMaximumSize().width, magneticCard.getY() + magneticCard.getBounds().height + distance);
                break;
        }
        mainWindow.setIsLineDrawn(true);
    }

    //TODO: clear only drawn line
    public void clearBackground() {
        if (!mainWindow.isIsLineDrawn()) {
            return;
        }
        Graphics2D g2d = (Graphics2D) mainWindow.getGraphics();
        g2d.clearRect(0, 0, mainWindow.getHeight(), mainWindow.getWidth());
        mainWindow.revalidate();
        mainWindow.repaint();
        mainWindow.setIsLineDrawn(false);
    }
}
