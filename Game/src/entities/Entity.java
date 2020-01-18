package entities;
 
import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import terrains.Terrain;
import textures.ModelTexture;
 
public class Entity {
 
	private String OBJFile;
	private String textureFile;
	private int numberOfRows = 1;
	
	private Loader loader;
	
	public static final float GRAVITY = -50;
	
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;
    
    private Terrain currentTerrain;
    
    private int textureIndex = 0;
 
    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ,
            float scale, boolean useFakeLighting, boolean hasTransparency) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }
    
    public Entity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ,
            float scale, boolean useFakeLighting, boolean hasTransparency) {
    	this.textureIndex = textureIndex;
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }
    
    public Entity(TexturedModel model, int numberOfRows, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ,
            float scale, boolean useFakeLighting, boolean hasTransparency) {
    	this.numberOfRows = numberOfRows;
    	this.textureIndex = textureIndex;
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }
    
    public Entity(Loader loader, String OBJFile, String textureFile, int numberOfRows, int textureIndex, Vector3f position,
    		float rotX, float rotY, float rotZ, float scale, boolean useFakeLighting, boolean hasTransparency) {
    	this.loader = loader;
    	this.OBJFile = OBJFile;
    	this.textureFile = textureFile;
    	this.numberOfRows = numberOfRows;
    	this.textureIndex = textureIndex;
    	
    	if (this.textureIndex >= (this.numberOfRows*this.numberOfRows)) {
    		this.textureIndex = this.numberOfRows * this.numberOfRows - 1;
    	}
    	
    	this.position = position;
    	this.rotX = rotX;
    	this.rotY = rotY;
    	this.rotZ = rotZ;
    	this.scale = scale;
    	this.loadModel();
    	
    	this.model.getTexture().setHasTransparency(hasTransparency);
    	this.model.getTexture().setUseFakeLighting(useFakeLighting);
    }
    
    public Entity(Loader loader, String OBJFile, String textureFile, Vector3f position, float rotX, float rotY, float rotZ,
    		float scale, boolean useFakeLighting, boolean hasTransparency) {
    	this.loader = loader;
    	this.OBJFile = OBJFile;
    	this.textureFile = textureFile;
    	this.numberOfRows = 1;
    	this.textureIndex = 0;
    	this.position = position;
    	this.rotX = rotX;
    	this.rotY = rotY;
    	this.rotZ = rotZ;
    	this.scale = scale;
    	this.loadModel();
    	
    	this.model.getTexture().setHasTransparency(hasTransparency);
    	this.model.getTexture().setUseFakeLighting(useFakeLighting);
    }
    
    private void loadModel() {
    	ModelData data = OBJFileLoader.loadOBJ(OBJFile);
		RawModel rawModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(),
        		data.getNormals(), data.getIndices());
		ModelTexture modelTexture = new ModelTexture(loader.loadGameTexture(textureFile));
		modelTexture.setNumberOfRows(numberOfRows);
		this.model = new TexturedModel(rawModel, modelTexture);
    }
    
    public float getTextureXOffset() {
    	int column = textureIndex % model.getTexture().getNumberOfRows();
    	return (float)column / (float)model.getTexture().getNumberOfRows();
    }
    
    public float getTextureYOffset() {
    	int row = textureIndex / model.getTexture().getNumberOfRows();
    	return (float)row / (float)model.getTexture().getNumberOfRows();
    }
 
    public TexturedModel getModel() {
        return model;
    }
 
    public void setModel(TexturedModel model) {
        this.model = model;
    }
 
    public Vector3f getPosition() {
        return position;
    }
 
    public void setPosition(Vector3f position) {
        this.position = position;
    }
 
    public float getRotX() {
        return rotX;
    }
 
    public void setRotX(float rotX) {
        this.rotX = rotX;
    }
 
    public float getRotY() {
        return rotY;
    }
 
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }
 
    public float getRotZ() {
        return rotZ;
    }
 
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }
 
    public float getScale() {
        return scale;
    }
 
    public void setScale(float scale) {
        this.scale = scale;
    }
 
}