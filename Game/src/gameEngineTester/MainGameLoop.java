package gameEngineTester;
 
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
 
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
 
public class MainGameLoop {
 
    public static void main(String[] args) {
    	List<Entity> staticEntities = new ArrayList<Entity>();
    	List<Terrain> terrains = new ArrayList<Terrain>();
    	List<GuiTexture> guis = new ArrayList<GuiTexture>();
    	List<Light> lights = new ArrayList<Light>();
 
        DisplayManager.createDisplay();
        Loader loader = new Loader();
         
        //------- TERRAIN TEXTURE PACK STUFF -------------
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
        ModelData lampData = OBJFileLoader.loadOBJ("lamp");
        
        ModelData playerData = OBJFileLoader.loadOBJ("person");
        
        RawModel tree = loader.loadToVAO(treeData.getVertices(), treeData.getTextureCoords(),
        		treeData.getNormals(), treeData.getIndices());
        RawModel grass = loader.loadToVAO(grassData.getVertices(), grassData.getTextureCoords(),
        		grassData.getNormals(), grassData.getIndices());
        RawModel fern = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(),
        		fernData.getNormals(), fernData.getIndices());
        RawModel lowPolyTree = loader.loadToVAO(lowTreeData.getVertices(), lowTreeData.getTextureCoords(),
        		lowTreeData.getNormals(), lowTreeData.getIndices());
       
        RawModel lamp = loader.loadToVAO(lampData.getVertices(), lampData.getTextureCoords(),
        		lampData.getNormals(), lampData.getIndices());
        
        RawModel person = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(),
        		playerData.getNormals(), playerData.getIndices());
        
        ModelTexture fernModelTexture = new ModelTexture(loader.loadTexture("fern"));
        fernModelTexture.setNumberOfRows(2);
        
        ModelTexture lowTreeModelTexture = new ModelTexture(loader.loadTexture("treeAtlas"));
        lowTreeModelTexture.setNumberOfRows(2);
        lowTreeModelTexture.setHasTransparency(true);
        
        TexturedModel treeModel = new TexturedModel(tree, new ModelTexture(loader.loadTexture("tree")));
        TexturedModel grassModel = new TexturedModel(grass, new ModelTexture(loader.loadTexture("grassTexture")));
        TexturedModel fernModel = new TexturedModel(fern, fernModelTexture);
        TexturedModel lowTreeModel = new TexturedModel(lowPolyTree, lowTreeModelTexture);
        TexturedModel lampModel = new TexturedModel(lamp, new ModelTexture(loader.loadTexture("lamp")));
        
        TexturedModel playerModel = new TexturedModel(person, new ModelTexture(loader.loadTexture("playerTexture")));
        
        grassModel.getTexture().setHasTransparency(true);
        grassModel.getTexture().setUseFakeLighting(true);
        fernModel.getTexture().setHasTransparency(true);
        
        Random random = new Random();
        
        for (int i=0;i<500;i++) {
        	staticEntities.add(new Entity(treeModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        	staticEntities.add(new Entity(grassModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,1));
        	staticEntities.add(new Entity(fernModel, random.nextInt(4), new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,0.6f));
        	if (i % 2 == 0 | i % 3 == 0 | i % 5 == 0) {
        		staticEntities.add(new Entity(lowTreeModel, random.nextInt(3), new Vector3f(random.nextFloat()*800 - 400, 0, random.nextFloat() * -600),0,0,0,0.35f));
        	}
        }
        
        Player player = new Player(playerModel, new Vector3f(0,0,-10),0,0,0,0.4f);
        
       lights.add(new Light(new Vector3f(0, 20000, -7000), new Vector3f(0.6f,0.6f,0.6f)));
       lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
       lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
       lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2, 0), new Vector3f(1, 0.01f, 0.002f)));
       
       staticEntities.add(new Entity(lampModel, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
       staticEntities.add(new Entity(lampModel, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1));
       staticEntities.add(new Entity(lampModel, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1));
        
       terrains.add(new Terrain(0, -1, loader, texturePack, blendMap, "heightmap"));
       terrains.add(new Terrain(-1, -1, loader, texturePack, blendMap, "heightmap"));
       
       for (Entity entity : staticEntities) {
    	   entity.setCorrectPosition(terrains);
       }
        
        Camera camera = new Camera(player);
        
        /*guis.add(new GuiTexture(loader.loadTexture("guiTest"),
        		new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f)));*/
        
        MasterRenderer renderer = new MasterRenderer(loader);
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        
        while(!Display.isCloseRequested()){
        	player.move(terrains);
            camera.move();
            renderer.processEntity(player);
            
            for (Terrain terrain : terrains) {
            	renderer.processTerrain(terrain);
            }
            
            for (Entity entity : staticEntities) {
            	renderer.processEntity(entity);
            }
            
            renderer.render(lights, camera);
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}