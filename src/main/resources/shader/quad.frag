#version 330 core

in vec2 uv;

void main() {
    gl_FragColor = vec4(uv, 1.0, 1.0);
}