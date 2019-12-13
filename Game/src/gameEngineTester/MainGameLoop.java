package gameEngineTester;
 
import models.RawModel;
import models.TexturedModel;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
 
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import entities.Camera;
import entities.Entity;
import entities.Light;
 
public class MainGameLoop {
 
    public static void main(String[] args) {
    	List<Entity> entities = new ArrayList<Entity>();
    	List<Terrain> terrains = new ArrayList<Terrain>();
 
        DisplayManager.createDisplay();
        Loader loader = new Loader();
         
        RawModel model = OBJLoader.loadOBJModel("dragon", loader);
         
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("dragon")));
        ModelTexture texture = staticModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);
         
        entities.add(new Entity(staticModel, new Vector3f(0,0,-50),0,0,0,1));
        
        Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
        
        terrains.add(new Terrain(0,0,loader, new ModelTexture(loader.loadTexture("grassTexture"))));
        terrains.add(new Terrain(1,0,loader, new ModelTexture(loader.loadTexture("grassTexture"))));
         
        
        
        Camera camera = new Camera();
        
        
        MasterRenderer renderer = new MasterRenderer();
        
        while(!Display.isCloseRequested()){
            camera.move();
            
            for (Terrain terrain : terrains) {
            	renderer.processTerrain(terrain);
            }
            
            for (Entity entity : entities) {
            	renderer.processEntity(entity);
            }
            
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }
 
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}