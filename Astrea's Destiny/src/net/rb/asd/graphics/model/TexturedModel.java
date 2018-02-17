package net.rb.asd.graphics.model;

import net.rb.asd.graphics.texture.ModelTexture;

/**
 * (C)2018- Richard Bergqvist
 * Created 15 feb. 2018
 */
public class TexturedModel {

	private Model model;
	private ModelTexture texture;
	
	public TexturedModel(Model model, ModelTexture texture) {
		this.model = model;
		this.texture = texture;
	}
	
	public Model getModel() {
		return model;
	}
	
	public ModelTexture getTexture() {
		return texture;
	}
}