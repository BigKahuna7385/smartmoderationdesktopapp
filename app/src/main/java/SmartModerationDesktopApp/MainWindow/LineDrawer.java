package SmartModerationDesktopApp.MainWindow;

import SmartModerationDesktopApp.ModerationCards.ModerationCard;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

public class LineDrawer {

    private final Point startPoint;
    private final Point endPoint;
    private boolean isLineDrawn;
    private boolean hasLineDistance;

    public LineDrawer() {
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
        Graphics2D g2d = (Graphics2D) MainWindow.getInstance().getGraphics();
        float dashPhase = 0f;
        float dash[] = {5.0f, 5.0f};
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.5f, dash, dashPhase));
        setStartAndEndPoint(movingCard, magneticCard);
        g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        isLineDrawn = true;
    }

    public void setStartAndEndPoint(ModerationCard movingCard, ModerationCard magneticCard) {
        int distance = (hasLineDistance ? magneticCard.getMAGNETRANGE() : 0);
        switch (movingCard.getSnapDirection()) {
            case EAST:
                startPoint.setLocation(magneticCard.getX() - distance, 0);
                endPoint.setLocation(magneticCard.getX() - distance, MainWindow.getInstance().getContentPane().getMaximumSize().height);
                break;
            case WEST:
                startPoint.setLocation(magneticCard.getX() + magneticCard.getWidth() + distance, 0);
                endPoint.setLocation(magneticCard.getX() + magneticCard.getWidth() + distance, MainWindow.getInstance().getContentPane().getMaximumSize().height);
                break;
            case SOUTH:
                startPoint.setLocation(0, magneticCard.getY() - distance - MainWindow.getInstance().getLocation().y);
                endPoint.setLocation(MainWindow.getInstance().getContentPane().getMaximumSize().width, magneticCard.getY() - distance - MainWindow.getInstance().getY());
                break;
            case NORTH:
                startPoint.setLocation(0, magneticCard.getY() + magneticCard.getHeight() + distance - MainWindow.getInstance().getLocation().y);
                endPoint.setLocation(MainWindow.getInstance().getContentPane().getMaximumSize().width, magneticCard.getY() + magneticCard.getHeight() + distance - MainWindow.getInstance().getY());
                break;
        }
    }

    public void clearLine() {
        if (!isLineDrawn) {
            return;
        }
        Graphics2D g2d = (Graphics2D) MainWindow.getInstance().getGraphics();
        g2d.clearRect(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
        MainWindow.getInstance().revalidate();
        MainWindow.getInstance().repaint();
        isLineDrawn = false;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }
}
