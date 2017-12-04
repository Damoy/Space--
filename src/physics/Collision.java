package physics;

/**
 * Simple AABB collision detection class
 * @author Damoy
 */
public final class Collision {


    /**
     * Check collision between 2 AABB
     * @param one,two 2 boxes
     * @return if there is a collision between both boxes
     */
    public static boolean boxCollide(AABB one, AABB two){

        return (one.getX() < two.getX() + two.getWidth() &&
                one.getX() + one.getWidth() > two.getX() &&
                one.getY() < two.getY() + two.getHeight() &&
                one.getY() + one.getHeight() > two.getY());
    }

    /**
     * With width1 = height1, width2 = height2
     */
    public static boolean boxCollide(float x1, float y1, int width1,
                                     float x2, float y2, int width2){
        return boxCollide(x1, y1, width1, width1,
                x2, y2, width2, width2);
    }

    /**
     * Same with heights in parameter
     */
    public static boolean boxCollide(float x1, float y1, int width1, int height1,
                                     float x2, float y2, int width2, int height2){
        return (x1 < x2 + width2 &&
                x1 + width1 > x2 &&
                y1 < y2 + height2 &&
                y1 + height1 > y2);
    }
    
	public static boolean boxCollideSides(float x1, int width1, float x2, int width2){
		return (x1 < x2 + width2 && x1 + width1 > x2);
	}
	
	public static boolean boxCollideSides(AABB box, AABB other){
		return boxCollideSides(box.getX(), box.getWidth(), other.getX(), other.getWidth());
	}

    /**
     * Between one AABB and parameters
     */
    public static boolean boxCollide(AABB box, float x, float y, int width, int height){
        return boxCollide(box.getX(), box.getY(), box.getWidth(), box.getHeight(),
                x, y, width, height);
    }

    /**
     * Check if the point is in the box
     * @param x the abscissa of the point
     * @param y the ordinate of the point
     * @param box the box
     * @return
     */
    public static boolean collisionPointAABB(float x, float y, AABB box){
    	return (x >= box.getX()
                && x < box.getX() + box.getWidth()
                && y >= box.getY()
                && y < box.getY() + box.getHeight());
    }

}
