package net.rb.asd.entity;

import org.lwjgl.util.vector.Vector3f;

import net.rb.asd.graphics.model.TexturedModel;

/**
 * (C)2018- Richard Bergqvist
 * Created 16 feb. 2018
 */
public class Entity {

	private TexturedModel model;
	private Vector3f position;
	private float rotationX, rotationY, rotationZ;
	private float scale;
	
	public Entity(TexturedModel model, Vector3f position, float rotationX, float rotationY, float rotationZ, float scale) {
		this.model = model;
		this.position = position;
		this.rotationX = rotationX;
		this.rotationY = rotationY;
		this.rotationZ = rotationZ;
		this.scale = scale;
	}
	
	public void move(float dx, float dy, float dz) {
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	
	public void rotate(float dx, float dy, float dz) {
		this.rotationX += dx;
		this.rotationY += dy;
		this.rotationZ += dz;
	}
	
	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setRotationX(float rotationX) {
		this.rotationX = rotationX;
	}
	
	public float getRotationX() {
		return rotationX;
	}
	
	public void setRotationY(float rotationY) {
		this.rotationY = rotationY;
	}

	public float getRotationY() {
		return rotationY;
	}
	
	public void setRotationZ(float rotationZ) {
		this.rotationZ = rotationZ;
	}

	public float getRotationZ() {
		return rotationZ;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getScale() {
		return scale;
	}
}