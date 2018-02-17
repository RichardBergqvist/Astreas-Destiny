package net.rb.asd.graphics.texture;

/**
 * (C)2018- Richard Bergqvist
 * Created 15 feb. 2018
 */
public class ModelTexture {

	private int textureID;
	
	private float shineDamper = 1;
	private float reflectivity;
	
	public ModelTexture(int textureID) {
		this.textureID = textureID;
	}
	
	public int getTextureID() {
		return textureID;
	}
	
	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}
	
	public float getShineDamper() {
		return shineDamper;
	}
	
	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
	
	public float getReflectivity() {
		return reflectivity;
	}
}