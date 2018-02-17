package net.rb.asd.main;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import net.rb.asd.entity.Camera;
import net.rb.asd.entity.Entity;
import net.rb.asd.entity.Light;
import net.rb.asd.graphics.DisplayManager;
import net.rb.asd.graphics.RenderManager;
import net.rb.asd.graphics.model.Model;
import net.rb.asd.graphics.model.TexturedModel;
import net.rb.asd.graphics.shader.StaticShader;
import net.rb.asd.graphics.texture.ModelTexture;
import net.rb.asd.resource.ResourceLoader;

/**
 * (C)2018- Richard Bergqvist
 * Created 15 feb. 2018
 */
public class GameLoop {

	/**
	 * Runs the entire game.
	 */
	public static void main(String[] args) {
		DisplayManager.create();
		
		ResourceLoader loader = new ResourceLoader();
		StaticShader shader = new StaticShader();
		RenderManager renderManager = new RenderManager(shader);
		
		Model model = loader.loadOBJModel("dragon", loader);
		TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("materials/white")));
		ModelTexture texture = texturedModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -50), 0, 0, 0, 1);
		Camera camera = new Camera();
		Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));
		
		while (!Display.isCloseRequested()) {
			entity.rotate(0, 1, 0);
			camera.move();
			renderManager.init();
			shader.start();
			shader.loadViewMatrix(camera);
			shader.loadLight(light);
			renderManager.render(entity, shader);
			shader.stop();
			DisplayManager.update();
		}
		
		shader.clean();
		loader.clean();
		DisplayManager.close();
	}
}