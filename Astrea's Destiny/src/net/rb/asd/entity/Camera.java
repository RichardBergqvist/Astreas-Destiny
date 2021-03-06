package net.rb.asd.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * (C)2018- Richard Bergqvist
 * Created 16 feb. 2018
 */
public class Camera {

	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch;
	private float yaw;
	
	public Camera() {
		
	}
	
	public void move() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			position.z -= 0.02F;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			position.z += 0.02F;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			position.x += 0.02F;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			position.x -= 0.02F;
		}
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}
}