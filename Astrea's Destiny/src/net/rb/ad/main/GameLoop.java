package net.rb.ad.main;

import org.lwjgl.opengl.Display;

import net.rb.ad.graphics.DisplayManager;
import net.rb.ad.graphics.RenderManager;
import net.rb.ad.graphics.model.Model;
import net.rb.ad.graphics.model.TexturedModel;
import net.rb.ad.graphics.shader.StaticShader;
import net.rb.ad.graphics.texture.ModelTexture;
import net.rb.ad.resource.ResourceManager;

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
		
		ResourceManager resourceManager = new ResourceManager();
		RenderManager renderManager = new RenderManager();
		StaticShader shader = new StaticShader();
		
		float[] vertices = {
			-0.5F, 0.5F, 0F,
			-0.5F, -0.5F, 0F,
			0.5F, -0.5F, 0F,
			0.5F, 0.5F, 0F,
		};
		
		float[] textureCoordinates = {
				0, 0,
				0, 1,
				1, 1,
				1, 0
			};
		
		int[] indices = {
			0, 1, 3,
			3, 1, 2
		};
		
		Model model = resourceManager.loadToVAO(vertices, textureCoordinates, indices);
		ModelTexture texture = new ModelTexture(resourceManager.loadTexture("iron_block"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		while (!Display.isCloseRequested()) {
			renderManager.init();
			shader.start();
			renderManager.render(texturedModel);
			shader.stop();
			DisplayManager.update();
		}
		
		shader.clean();
		resourceManager.clean();
		DisplayManager.close();
	}
}