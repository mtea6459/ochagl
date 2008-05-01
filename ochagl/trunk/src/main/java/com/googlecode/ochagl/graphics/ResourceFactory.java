package com.googlecode.ochagl.graphics;

import com.googlecode.ochagl.graphics.jogl.LightJogl;
import com.googlecode.ochagl.graphics.jogl.Line3dJogl;
import com.googlecode.ochagl.graphics.jogl.MaterialJogl;
import com.googlecode.ochagl.graphics.jogl.MeshJogl;
import com.googlecode.ochagl.graphics.jogl.Object2dJogl;
import com.googlecode.ochagl.graphics.jogl.Object3dJogl;
import com.googlecode.ochagl.graphics.jogl.StringSpriteJogl;
import com.googlecode.ochagl.graphics.jogl.TextureLoaderJogl;
import com.googlecode.ochagl.graphics.jogl.View2dJogl;
import com.googlecode.ochagl.graphics.jogl.View3dJogl;


/**
 * JoglとJava2dの実装クラスを持つオブジェクトのファクトリ.
 */
public final class ResourceFactory  {
    
    public static final int JAVA2D = 0;
    public static final int JOGL = 1;
    
    private static int mode_ = 0;
    private ResourceFactory() {}
    
    public static TextureLoader createTextureLoader() {
        TextureLoader loader = null;
        switch (mode_) {
            case JAVA2D:
                loader = new TextureLoaderJogl();
                break;
            case JOGL:
                loader = new TextureLoaderJogl();
                break;
        }
        return loader;
    }

    public static Object2d createObject2d(final String name) {
        Object2d sprite = null;
        switch (mode_) {
            case JAVA2D:
                sprite = new Object2dJogl(name);
                break;
            case JOGL:
                sprite = new Object2dJogl(name);
                break;
        }
        return sprite;
    }

    public static StringSprite createStringSprite() {
        StringSprite string = null;
        switch (mode_) {
            case JAVA2D:
                string = new StringSpriteJogl();
                break;
            case JOGL:
                string = new StringSpriteJogl();
                break;
        }
        return string;
    }

//////////////////////////////////////////////////////////////////////////////////////    

    public static Object3d createObject3d(String name) {
        Object3d obj = null;
        switch (mode_) {
            case JAVA2D:
                obj = new Object3dJogl(name);
                break;
            case JOGL:
                obj = new Object3dJogl(name);
                break;
        }
        return obj;
    }

    public static Light createLight() {
        Light light = null;
        switch (mode_) {
            case JAVA2D:
                light = new LightJogl();
                break;
            case JOGL:
                light = new LightJogl();
                break;
        }
        return light;
    }

    public static View3d createView3d(int state) {
        View3d view = null;
        switch (mode_) {
            case JAVA2D:
                view = new View3dJogl(state);
                break;
            case JOGL:
                view = new View3dJogl(state);
                break;
        }
        return view;
    }

    public static View3d createView3d() {
        return createView3d(0);
    }

    public static View2d createView2d(int state) {
        View2d view = null;
        switch (mode_) {
            case JAVA2D:
                view = new View2dJogl(state);
                break;
            case JOGL:
                view = new View2dJogl(state);
                break;
        }
        return view;
    }

    public static View2d createView2d() {
        return createView2d(0);
    }

    public static Material createMaterial() {
        Material mat = null;
        switch (mode_) {
            case JAVA2D:
                mat = new MaterialJogl();
                break;
            case JOGL:
                mat = new MaterialJogl();
                break;
        }
        return mat;
    }

    public static Mesh createMesh() {
        Mesh mesh = null;
        switch (mode_) {
            case JAVA2D:
                mesh = new MeshJogl();
                break;
            case JOGL:
                mesh = new MeshJogl();
                break;
        }
        return mesh;
    }

    public static Line3d createLine3d() {
        Line3d mesh = null;
        switch (mode_) {
            case JAVA2D:
                mesh = new Line3dJogl();
                break;
            case JOGL:
                mesh = new Line3dJogl();
                break;
        }
        return mesh;
    }
}
