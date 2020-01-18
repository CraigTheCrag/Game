package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class StaticEntity extends Entity {

	public StaticEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale, boolean useFakeLighting, boolean hasTransparency) {
		super(model, position, rotX, rotY, rotZ, scale, useFakeLighting, hasTransparency);
	}
	
	public StaticEntity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
			float scale) {
		super(model, position, rotX, rotY, rotZ, scale, false, false);
	}
	
	public StaticEntity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ,
            float scale, boolean useFakeLighting, boolean hasTransparency) {
    	super(model, textureIndex, position, rotX, rotY, rotZ, scale, useFakeLighting, hasTransparency);
    }
	
	public StaticEntity(TexturedModel model, int numberOfRows, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ,
            float scale, boolean useFakeLighting, boolean hasTransparency) {
    	super(model, textureIndex, position, rotX, rotY, rotZ, scale, useFakeLighting, hasTransparency);
	}
	
	public StaticEntity(TexturedModel model, int numberOfRows, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ,
            float scale) {
    	super(model, textureIndex, position, rotX, rotY, rotZ, scale, false, false);
	}
	
}
