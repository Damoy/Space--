package physics;

import entities.Entity;
import toolbox.data.GameMemory;
import toolbox.interfaces.Labelable;

/**
 * The axis align bounding box,
 * used to detect collision between entities
 */
public class AABB implements Labelable{

    // the position
    private float x, y;
    // the sizes
    private int width, height;
    // 
    private String label;


    public AABB(Entity entity){
    	this(entity.getX(), entity.getY(),
    			entity.getTexture().getWidth(), entity.getTexture().getHeight());
    }

    /**
     * Generates a new AABB with the position
     * and the sizes given
     * @param x,y the position
     * @param height,width the sizes
     */
    public AABB(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public AABB(){
        this(0, 0, 0, 0);
    }

    /**
     * Set the box position only
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public void update(float x, float y, int width, int height, boolean updateSizes){
    	updateX(x);
    	updateY(y);
    	if(updateSizes){
    		updateWidth(width);
    		updateHeight(height);
    	}
    }
    
    public void updateX(float x){
    	if(this.x == x) return;
    	this.x = x;
    }
    
    public void updateY(float y){
    	if(this.y == y) return;
    	this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void updateWidth(int w){
    	if(this.width == w) return;
    	this.width = w;
    }
    
    public void updateHeight(int h){
    	if(this.width == h) return;
    	this.height = h;
    }
    
    @Override
    public void setLabel(String label){
    	if(label == null) return;
    	if(label.equals(this.label)) return;
    	this.label = label;
    }
    
    public String toString(){
    	StringBuffer b = GameMemory.OUTPUT_STRING_BUFFER;
    	if(label == null) b.append("AABB");
    	else b.append(label);
    	b.append(": [x:");
    	b.append(x);
    	b.append(", y:");
    	b.append(y);
    	b.append(", w:");
    	b.append(width);
    	b.append(", h:");
    	b.append(height);
    	b.append("]");
    	return GameMemory.getOutputBufferContentAndReset();
    }
}
