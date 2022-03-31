package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.MainWindow;
import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

public class LineDrawer {

    private final MainWindow mainWindow;
    private final Point startPoint;
    private final Point endPoint;

    public LineDrawer(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        startPoint = new Point();
        endPoint = new Point();
    }

    public void drawDottedLineBetween(ModerationCard movingCard, ModerationCard magneticCard) {
        if (movingCard.isDistancedMagnet() != mainWindow.isHasLineDistance()) {
            clearLine();
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
                break;
            case WEST:
                startPoint.setLocation(magneticCard.getBounds().x + magneticCard.getBounds().width + distance, 0);
                endPoint.setLocation(magneticCard.getBounds().x + magneticCard.getBounds().width + distance, mainWindow.getContentPane().getMaximumSize().height);
                break;
            case SOUTH:
                startPoint.setLocation(0, magneticCard.getY() - distance);
                endPoint.setLocation(mainWindow.getContentPane().getMaximumSize().width, magneticCard.getY() - distance);
                break;
            case NORTH:
                startPoint.setLocation(0, magneticCard.getY() + magneticCard.getBounds().height + distance);
                endPoint.setLocation(mainWindow.getContentPane().getMaximumSize().width, magneticCard.getY() + magneticCard.getBounds().height + distance);
                break;
        }
        g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        mainWindow.setIsLineDrawn(true);
    }

    public void clearLine() {
        if (!mainWindow.isIsLineDrawn()) {
            return;
        }
        Graphics2D g2d = (Graphics2D) mainWindow.getGraphics();
        g2d.clearRect(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        mainWindow.revalidate();
        mainWindow.repaint();
        mainWindow.setIsLineDrawn(false);
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }
    
    
}
