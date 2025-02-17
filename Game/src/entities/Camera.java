package entities;
 
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
 
public class Camera {
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private static final float CHEST_HEIGHT = 3;
     
    private Vector3f position = new Vector3f(0,5,0);
    private float pitch = 20;
    private float yaw = 0;
    private float roll;
    
    private Player player;
     
    public Camera(Player player){
    	this.player = player;
    }
     
    public void move(){
        calculateZoom();
        calculatePitch();
        //calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
        calculateCameraPosition(horizontalDistance, verticalDistance);
        this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
    }
    
    public void invertPitch() {
    	this.pitch *= -1;
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
    
    private void calculateCameraPosition(float hDistance, float vDistance) {
    	float theta = player.getRotY() + angleAroundPlayer;
    	float offsetX = (float) (hDistance * Math.sin(Math.toRadians(theta)));
    	float offsetZ = (float) (hDistance * Math.cos(Math.toRadians(theta)));
    	position.x = player.getPosition().x - offsetX;
    	position.z = player.getPosition().z - offsetZ;
    	position.y = CHEST_HEIGHT + player.getPosition().y + vDistance;
    	
    }
    
    private float calculateHorizontalDistance() {
    	float hD = (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    	if (hD < 0) {
    		hD = 0;
    	}
    	return hD;
    }
    
    private float calculateVerticalDistance() {
    	float vD = (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    	if (vD < 0) {
    		vD = 0;
    	}
    	return vD;
    }
    
    private void calculateZoom() {
    	float zoomLevel = Mouse.getDWheel() * 0.1f;
    	distanceFromPlayer -= zoomLevel;
    	if (distanceFromPlayer < CHEST_HEIGHT) {
    		distanceFromPlayer = CHEST_HEIGHT;
    	}
    }
    
    private void calculatePitch() {
    	if(Mouse.isButtonDown(1)) {
    		float pitchChange = Mouse.getDY() * 0.25f;
    		float angleChange = Mouse.getDX() * 0.25f;
    		pitch -= pitchChange;
    		angleAroundPlayer -= angleChange;
    		if (pitch < 0) {
    			pitch = 0;
    		} else if (pitch > 90){
    			pitch = 90;
    		}
    	}
    }
}