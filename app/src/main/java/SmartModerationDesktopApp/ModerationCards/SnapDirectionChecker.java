package SmartModerationDesktopApp.ModerationCards;

import static SmartModerationDesktopApp.ModerationCards.SnapDirections.*;

public class SnapDirectionChecker {

    public void setSnapDirectionBetween(ModerationCard movingCard, ModerationCard magneticCard) {
        int magnetRange = movingCard.getMAGNETRANGE();
        boolean distancedMagnet = movingCard.isDistancedMagnet();
        ModerationCard returnModerationCard = movingCard.getMagneticCard();
        SnapDirections snapDirection = movingCard.getSnapDirection();
        if (!isOver(movingCard, magneticCard) && !isUnder(movingCard, magneticCard)) {
            if (isAtRightBorderOfWithinRangeOf(movingCard, magneticCard, magnetRange, -magnetRange)) {
                returnModerationCard = magneticCard;
                distancedMagnet = false;
                snapDirection = WEST;
            } else if (isAtRightBorderOfWithinRangeOf(movingCard, magneticCard, 2 * magnetRange, magnetRange)) {
                returnModerationCard = magneticCard;
                distancedMagnet = true;
                snapDirection = WEST;
            } else if (isAtLeftBorderOfWithinRangeOf(movingCard, magneticCard, -magnetRange, magnetRange)) {
                returnModerationCard = magneticCard;
                distancedMagnet = false;
                snapDirection = EAST;
            } else if (isAtLeftBorderOfWithinRangeOf(movingCard, magneticCard, magnetRange, 2 * magnetRange)) {
                returnModerationCard = magneticCard;
                distancedMagnet = true;
                snapDirection = EAST;
            }
        }
        if (!isLeftOf(movingCard, magneticCard) && !isRightOf(movingCard, magneticCard)) {
            if (isToTheTopBorderWithinMagnetrange(movingCard, magneticCard, magnetRange, -magnetRange)) {
                returnModerationCard = magneticCard;
                distancedMagnet = false;
                snapDirection = NORTH;
            } else if (isToTheTopBorderWithinMagnetrange(movingCard, magneticCard, 2 * magnetRange, magnetRange)) {
                returnModerationCard = magneticCard;
                distancedMagnet = true;
                snapDirection = NORTH;
            } else if (isToTheBottomBorderWithinMagnetrange(movingCard, magneticCard, -magnetRange, magnetRange)) {
                returnModerationCard = magneticCard;
                distancedMagnet = false;
                snapDirection = SOUTH;
            } else if (isToTheBottomBorderWithinMagnetrange(movingCard, magneticCard, magnetRange, 2 * magnetRange)) {
                returnModerationCard = magneticCard;
                distancedMagnet = true;
                snapDirection = SOUTH;
            }
        }
        movingCard.setMagneticCard(returnModerationCard);
        movingCard.setDistancedMagnet(distancedMagnet);
        movingCard.setSnapDirection(snapDirection);
    }

    private boolean isToTheRightBorderRightOfMagnetrange(ModerationCard movingCard, ModerationCard magneticCard, int magnetRange) {
        return magneticCard.getBounds().x + magneticCard.getBounds().width + magnetRange > movingCard.getBounds().x;
    }

    private boolean isToTheRightBorderLeftOfMagnetrangeOf(ModerationCard movingCard, ModerationCard magneticCard, int magnetRange) {
        return magneticCard.getBounds().x + magneticCard.getBounds().width + magnetRange <= movingCard.getBounds().x;
    }

    private boolean isAtRightBorderOfWithinRangeOf(ModerationCard movingCard, ModerationCard magneticCard, int magnetStart, int magnetEnd) {
        return isToTheRightBorderRightOfMagnetrange(movingCard, magneticCard, magnetStart) && isToTheRightBorderLeftOfMagnetrangeOf(movingCard, magneticCard, magnetEnd);
    }

    private boolean isToTheLeftBorderLeftOfMagnetrange(ModerationCard movingCard, ModerationCard magneticCard, int magnetRange) {
        return movingCard.getBounds().x + movingCard.getBounds().width + magnetRange < magneticCard.getBounds().x;
    }

    private boolean isToTheLeftBorderRightOfMagnetrange(ModerationCard movingCard, ModerationCard magneticCard, int magnetRange) {
        return movingCard.getBounds().x + movingCard.getBounds().width + magnetRange > magneticCard.getBounds().x;
    }

    private boolean isAtLeftBorderOfWithinRangeOf(ModerationCard movingCard, ModerationCard magneticCard, int magnetStart, int magnetEnd) {
        return isToTheLeftBorderLeftOfMagnetrange(movingCard, magneticCard, magnetStart) && isToTheLeftBorderRightOfMagnetrange(movingCard, magneticCard, magnetEnd);
    }

    private boolean isToTheTopBorderBelowMagnetrange(ModerationCard movingCard, ModerationCard magneticCard, int magnetRange) {
        return movingCard.getBounds().y < magneticCard.getBounds().y + magneticCard.getBounds().height + magnetRange;
    }

    private boolean isToTheTopBorderAboveMagnetrange(ModerationCard movingCard, ModerationCard magneticCard, int magnetRange) {
        return movingCard.getBounds().y >= magneticCard.getBounds().y + magneticCard.getBounds().height + magnetRange;
    }

    boolean isToTheTopBorderWithinMagnetrange(ModerationCard movingCard, ModerationCard magneticCard, int magnetStart, int magnetEnd) {
        return isToTheTopBorderBelowMagnetrange(movingCard, magneticCard, magnetStart) && isToTheTopBorderAboveMagnetrange(movingCard, magneticCard, magnetEnd);
    }

    private boolean isToTheBottomBorderBelowMagnetrange(ModerationCard movingCard, ModerationCard magneticCard, int magnetRange) {
        return movingCard.getBounds().y + magneticCard.getBounds().height + magnetRange < magneticCard.getBounds().y;
    }

    private boolean isToTheBottomBorderAboveMagnetrange(ModerationCard movingCard, ModerationCard magneticCard, int magnetRange) {
        return movingCard.getBounds().y + magneticCard.getBounds().height + magnetRange > magneticCard.getBounds().y;
    }

    private boolean isToTheBottomBorderWithinMagnetrange(ModerationCard movingCard, ModerationCard magneticCard, int magnetStart, int magnetEnd) {
        return isToTheBottomBorderBelowMagnetrange(movingCard, magneticCard, magnetStart) && isToTheBottomBorderAboveMagnetrange(movingCard, magneticCard, magnetEnd);
    }

    public boolean isRightOf(ModerationCard movingCard, ModerationCard magneticCard) {
        return movingCard.getBounds().x > magneticCard.getBounds().x + magneticCard.getBounds().width;
    }

    public boolean isLeftOf(ModerationCard movingCard, ModerationCard magneticCard) {
        return movingCard.getBounds().x + movingCard.getBounds().width < magneticCard.getBounds().x;
    }

    public boolean isOver(ModerationCard movingCard, ModerationCard magneticCard) {
        return movingCard.getBounds().y + movingCard.getBounds().height < magneticCard.getBounds().y;
    }

    public boolean isUnder(ModerationCard movingCard, ModerationCard magneticCard) {
        return movingCard.getBounds().y > magneticCard.getBounds().y + magneticCard.getBounds().height;
    }
}
