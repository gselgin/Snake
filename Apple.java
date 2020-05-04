// Greg Elgin
// Last Updated: 05/04/20
// Apple object generated at random grid point, used in Snake game

import java.util.Random;


class Apple {
    private Coordinates appleCoords;
    private String type;


    // Construct an apple object containing a random set of coordinates
    Apple(String appleOrMouse) {
        type = appleOrMouse;
        Random rand = new Random();
        int x = rand.nextInt(15);
        int y = rand.nextInt(13) + 2;
        appleCoords = new Coordinates(x, y);
    }

    Apple(String appleOrMouse, int x, int y) {
        type = appleOrMouse;
        appleCoords = new Coordinates(x, y);
    }

    Coordinates getAppleCoords() {
        return appleCoords;
    }

    String getType() {
        return type;
    }

    void setAppleCoords(float x, float y) {
        appleCoords = new Coordinates(x, y);
    }

    // Move the apple 1 unit at a time in random direction
    void move() {
        float newX;
        float newY;

        Random rand = new Random();
        int xyChoice = rand.nextInt(2);
        int directionChoice = rand.nextInt((2));
        if (xyChoice == 0) {
            if (directionChoice == 0) {
                newX = this.getAppleCoords().getX() + 1;
                if (newX == 15) {
                    newX -= 2;
                    this.getAppleCoords().setX(newX);
                }
                else {
                    this.getAppleCoords().setX(newX);
                }
            }
            else {
                newX = this.getAppleCoords().getX() - 1;
                if (newX == -1) {
                    newX += 2;
                    this.getAppleCoords().setX(newX);
                }
                else {
                    this.getAppleCoords().setX(newX);
                }
            }
        }
        else {
            if (directionChoice == 0) {
                newY = this.getAppleCoords().getY() + 1;
                if (newY == 15) {
                    newY -= 2;
                    this.getAppleCoords().setY(newY);
                }
                else {
                    this.getAppleCoords().setY(newY);
                }
            }
            else {
                newY = this.getAppleCoords().getY() - 1;
                if (newY == 1) {
                    newY += 2;
                    this.getAppleCoords().setY(newY);
                }
                else {
                    this.getAppleCoords().setY(newY);
                }
            }
        }
    }
}
