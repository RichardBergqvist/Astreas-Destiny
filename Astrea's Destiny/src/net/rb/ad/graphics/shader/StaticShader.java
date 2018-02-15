package net.rb.ad.graphics.shader;

/**
 * (C)2018- Richard Bergqvist
 * Created 15 feb. 2018
 */
public class StaticShader extends Shader {

	public StaticShader() {
		super("vertexShader", "fragmentShader");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
	}
}