package com.googlecode.ochagl.graphics.task;
import com.googlecode.ochagl.graphics.Light;
import com.googlecode.ochagl.graphics.ResourceFactory;

public class GenericLightTask extends Generic3dTask {

    protected Light light_ = null;
    
    public GenericLightTask(String name, int priority) {
        super(name, priority);
        light_ = ResourceFactory.createLight();
        light_.show(true);
        light_.setDiffuse(0.8f, 0.8f, 0.8f);
        light_.setAmbinet(0.2f, 0.2f, 0.2f);
        light_.setSpecular(0.5f, 0.5f, 0.5f);
        light_.setEmissive(0.0f, 0.0f, 0.0f);
        light_.setShininess(100);
        light_.setPosition(-100.0f, 10.0f, 0.0f);
    }

    public Light getLight() {
        return light_;
    }

    public void execute() {

    }
}
