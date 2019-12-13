package entities;
 
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
 
public class Camera {
     
    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;
    
    private static final float FACTOR_TO_MOVE = 0.125f;
     
    public Camera(){}
     
    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
        	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
        		position.z-=FACTOR_TO_MOVE;
        	}
            position.z-=FACTOR_TO_MOVE;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
        	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
        		position.z+=FACTOR_TO_MOVE;
        	}
        	position.z+=FACTOR_TO_MOVE;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
        	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
        		position.x+=FACTOR_TO_MOVE;
        	}
            position.x+=FACTOR_TO_MOVE;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
        	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
        		position.x-=FACTOR_TO_MOVE;
        	}
            position.x-=FACTOR_TO_MOVE;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
        	position.y+=FACTOR_TO_MOVE;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
        	position.y-=FACTOR_TO_MOVE;
        }
    }
 
    public Vector3f getPosition() {
        return position;
    }
 
    public float getPitch() {
        return pitch;
    }
 
    public float getYaw() {
        return yaw;
    }
 
    public float getRoll() {
        return roll;
    }
     
     
 
}