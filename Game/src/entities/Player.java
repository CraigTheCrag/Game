package entities;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends NonStaticEntity {

	private static final float RUN_SPEED = 12.5f;
	private static final float TURN_SPEED = 160;
	private static final float JUMP_POWER = 24;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private Vector3f previousPosition;
	
	private boolean isInAir = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move(List<Terrain> terrains) {
		previousPosition = super.getPosition();
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight = Terrain.getTerrainHeight(super.getPosition(), terrains);
		if (super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			super.getPosition().y = terrainHeight;
			isInAir = false;
		}
	}
	
	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public float getUpwardsSpeed() {
		return upwardsSpeed;
	}

	private void jump() {
		this.upwardsSpeed = JUMP_POWER;
	}
	
	private void checkInputs() {
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				this.currentSpeed = 2*RUN_SPEED;
			} else {
				this.currentSpeed = RUN_SPEED;
			}
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				this.currentSpeed = -2*RUN_SPEED;
			} else {
				this.currentSpeed = -RUN_SPEED;
			}
		} else {
			this.currentSpeed = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		} else {
			this.currentTurnSpeed = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (!isInAir) {
				jump();
				isInAir = true;
			}
		}
	}
	
	public Vector3f getPreviousPosition() {
		return previousPosition;
	}

}
