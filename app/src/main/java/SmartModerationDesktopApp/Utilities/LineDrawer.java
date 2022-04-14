package SmartModerationDesktopApp.Utilities;

import SmartModerationDesktopApp.MainWindow.MainWindow;
import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

public class LineDrawer {

    private final MainWindow mainWindow;
    private final Point startPoint;
    private final Point endPoint;
    private boolean isLineDrawn;
    private boolean hasLineDistance;

    public LineDrawer(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        isLineDrawn = false;
        hasLineDistance = false;
        startPoint = new Point();
        endPoint = new Point();
    }

    public void drawDottedLineBetween(ModerationCard movingCard, ModerationCard magneticCard) {
        if (movingCard.isDistancedMagnet() != hasLineDistance) {
            clearLine();
            hasLineDistance = movingCard.isDistancedMagnet();
        }
        Graphics2D g2d = (Graphics2D) mainWindow.getGraphics();
        float dashPhase = 0f;
        float dash[] = {5.0f, 5.0f};
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.5f, dash, dashPhase));
        setStartAndEndPoint(movingCard, magneticCard);
        g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        isLineDrawn = true;
    }

    public void setStartAndEndPoint(ModerationCard movingCard, ModerationCard magneticCard) {
        int distance = hasLineDistance ? magneticCard.getMAGNETRANGE() : 0;
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
                startPoint.setLocation(0, magneticCard.getBounds().y - distance);
                endPoint.setLocation(mainWindow.getContentPane().getMaximumSize().width, magneticCard.getBounds().y - distance);
                break;
            case NORTH:
                startPoint.setLocation(0, magneticCard.getBounds().y + magneticCard.getBounds().height + distance);
                endPoint.setLocation(mainWindow.getContentPane().getMaximumSize().width, magneticCard.getBounds().y + magneticCard.getBounds().height + distance);
                break;
        }
    }

    public void clearLine() {
        if (!isLineDrawn) {
            return;
        }
        Graphics2D g2d = (Graphics2D) mainWindow.getGraphics();
        g2d.clearRect(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        mainWindow.revalidate();
        mainWindow.repaint();
        isLineDrawn = false;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }
}
