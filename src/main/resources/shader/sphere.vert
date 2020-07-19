#version 330 core
#define PI 3.141592


in vec3 position;
// in vec3 normal;

out vec3 color;

uniform mat4 model;
uniform float time;
uniform int nowL;
uniform int nowM;
uniform int nextL;
uniform int nextM;

float sf(int l, int m, float theta, float phi) {
    if (l == 0) {
        return sqrt(1.0 / (4.0 * PI));
    }
    if (l == 1) {
        if (m == 0) return sqrt(3.0 / (4.0 * PI)) * cos(theta);
        else return sqrt(3.0 / (4.0 * PI)) * sin(theta) * cos(phi);
    }
    if (l == 2) {
        if (m == 0) return sqrt(5.0 / (16.0 * PI)) * (3.0 * pow(cos(theta), 2.0) - 1.0);
        if (m == 1) return sqrt(15.0 / (4.0 * PI)) * sin(theta) * cos(theta) * cos(phi);
        else return sqrt(15.0 / (16.0 * PI)) * pow(sin(theta), 2.0) * pow(cos(phi), 2.0);
    }
    // if (l == 3) {
    //     if (m == 0) return sqrt(7.0 / (16.0))
    // }
    else {
        return 0.0;
    }
}

void main() {
    float phi = (position.x + 1.0) * PI;
    float theta = (position.y + 1.0) / 2.0 * PI;
    vec3 pos = vec3(sin(theta)*sin(phi), sin(theta)*cos(phi), cos(theta));

    // color = (pos + vec3(1.0)) / 2.0;
    // color = (position + 1.0) / 2.0;
    float nowR = sf(nowL, nowM, theta, phi);
    float nextR = sf(nextL, nextM, theta, phi);
    float t = clamp((mod(time, 300.0) / 300.0) * 4.0 - 3.0, 0.0, 1.0);
    // float t = (mod(time, 300.0) / 300.0);
    float r = t * nextR + (1.0-t) * nowR;

    pos = pos * abs(r);
    if (r > 0.0) {
        color = vec3(0.0, abs(r)*2.0, 0.0);
    } else {
        color = vec3(abs(r)*2.0, 0.0, 0.0);
    }
    // vec3 lightDir = normalize(vec3(-1.0, -1.0, -1.0));
    // color = dot(lightDir * pos);

    gl_Position = model *vec4(pos, 1.0);
}