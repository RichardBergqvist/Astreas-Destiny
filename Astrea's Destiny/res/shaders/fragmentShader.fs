#version 400 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 lightVector;
in vec3 cameraVector;

out vec4 out_Color;

uniform sampler2D textureSampler;

uniform vec3 lightColor;

uniform float shineDamper;
uniform float reflectivity;

void main() {
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(lightVector);
	vec3 unitCameraVector = normalize(cameraVector);
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

	float nDot1 = dot(unitNormal, unitLightVector);
	float brightness = max(nDot1, 0.0);
	vec3 diffuse = brightness * lightColor;

	float specularFactor = dot(reflectedLightDirection, unitCameraVector);
	specularFactor = max(specularFactor, 0.0);

	float damper = pow(specularFactor, shineDamper);
	vec3 finalSpecular = damper * lightColor;

	out_Color = vec4(diffuse, 1.0) * texture(textureSampler, pass_textureCoordinates) + vec4(finalSpecular, 1.0);
}