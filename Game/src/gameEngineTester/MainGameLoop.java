package gameEngineTester;
 
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
 
public class MainGameLoop {
 
    public static void main(String[] args) {
    	List<Entity> entities = new ArrayList<Entity>();
    	List<Terrain> terrains = new ArrayList<Terrain>();
 
        DisplayManager.createDisplay();
        Loader loader = new Loader();
         
        //------- TERRAIN TEXTURE PACK StuFF -------------
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture,
        		rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        
        //------------------------------------------------
        
        ModelData treeData = OBJFileLoader.loadOBJ("tree");
        ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
        ModelData fernData = OBJFileLoader.loadOBJ("fern");
        ModelData lowTreeData = OBJFileLoader.loadOBJ("lowPolyTree");
        
        ModelData playerData = OBJFileLoader.loadOBJ("person");
        
        RawModel tree = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(),
        		treeData.getNormals(), treeData.getIndices());
        RawModel grass = loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(),
        		grassData.getNormals(), grassData.getIndices());
        RawModel fern = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(),
        		fernData.getNormals(), fernData.getIndices());
        RawModel lowPolyTree = loader.loadToVAO(lowTreeData.getVertices(), lowTreeData.getTextureCoords(),
        		lowTreeData.getNormals(), lowTreeData.getIndices());
        
        RawModel person = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(),
        		playerData.getNormals(), playerData.getIndices());
        
        TexturedModel treeModel = new TexturedModel(tree, new ModelTexture(loader.loadTexture("tree")));
        TexturedModel grassModel = new TexturedModel(grass, new ModelTexture(loader.loadTexture("grassTexture")));
        TexturedModel fernModel = new TexturedModel(fern, new ModelTexture(loader.loadTexture("fern")));
        TexturedModel lowTreeModel = new TexturedModel(lowPolyTree, new ModelTexture(loader.loadTexture("lowPolyTree")));
        
        TexturedModel playerModel = new TexturedModel(person, new ModelTexture(loader.loadTexture("playerTexture")));
        
        grassModel.getTexture().setHasTransparency(true);
        grassModel.getTexture().setUseFakeLighting(true);
        fernModel.getTexture().setHasTransparency(true);
        
        Random random = new Random();
        
        for (int i=0;i<500;i++) {
        	entities.add(new Entity(treeModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        	entities.add(new Entity(grassModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,1));
        	entities.add(new Entity(fernModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,0.6f));
        	if (i % 2 == 0 | i % 3 == 0) {
        		entities.add(new Entity(lowTreeModel, new Vector3f(random.nextFloat()*800 - 400, 0, random.nextFloat() * -600),0,0,0,0.35f));
        	}
        }
        
        Player player = new Player(playerModel, new Vector3f(0,0,-10),0,0,0,0.4f);
        
       Light light = new Light(new Vector3f(20000,20000,2000), new Vector3f(1,1,1));
        
       terrains.add(new Terrain(0, -1, loader, texturePack, blendMap));
       terrains.add(new Terrain(-1, -1, loader, texturePack, blendMap));
         
        
        
        Camera camera = new Camera(player);
        
        
        MasterRenderer renderer = new MasterRenderer();
        
        while(!Display.isCloseRequested()){
            camera.move();
            player.move();
            renderer.processEntity(player);
            
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