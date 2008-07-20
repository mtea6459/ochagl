package com.googlecode.ochagl.graphics;

import com.googlecode.ochagl.math.Rad;


/**
 * Object3dにぶら下がる基本的な図形を生成するファクトリ.
 */
public final class Primitive3dFactory {

    private Primitive3dFactory() {
    }

    public static Object3d createLine(float size) {
        return createLine(size, 1, 0, 0);
    }

    public static Object3d createLine(float size, float r, float g, float b) {
        Object3d obj = ResourceFactory.createObject3d("LINE");
        Line3d primLine = null;
        Vertex vs = null;

        primLine = ResourceFactory.createLine3d();
        vs = new Vertex();
        vs.create(2,0,0);
        vs.setVertex(0, 0, 0, 0);
        vs.setVertex(1, 0, 0, size);
        primLine.setVertex(vs);
        primLine.setColor(r, g, b);
        obj.addMesh(primLine);
        return obj;
    }

    // TODO ビルボードにして2軸にする
    // 色を指定できるように
    public static Object3d createCrossLine(float size) {

        Object3d obj = ResourceFactory.createObject3d("CROSSLINE");
        Line3d line = null;
        Vertex vs = null;
        float sz2 = size / 2.0f;

        line = ResourceFactory.createLine3d();
        vs = new Vertex();
        vs.create(2,0,0);
        vs.setVertex(0,  sz2, 0, 0);
        vs.setVertex(1, -sz2, 0, 0);
        line.setVertex(vs);
        line.setColor(1.0f, 0, 0);
        obj.addMesh(line);

        line = ResourceFactory.createLine3d();
        vs = new Vertex();
        vs.create(2,0,0);
        vs.setVertex(0, 0, sz2, 0);
        vs.setVertex(1, 0,-sz2, 0);
        line.setVertex(vs);
        line.setColor(1.0f, 0, 0);
        obj.addMesh(line);

        line = ResourceFactory.createLine3d();
        vs = new Vertex();
        vs.create(2,0,0);
        vs.setVertex(0, 0, 0,  sz2);
        vs.setVertex(1, 0, 0, -sz2);
        line.setVertex(vs);
        line.setColor(1.0f, 0, 0);
        obj.addMesh(line);

        return obj;
    }

    public static Object3d createAxis(float size) {

        Object3d obj = ResourceFactory.createObject3d("AXIS");
        Line3d line = null;
        Vertex vs = null;

        line = ResourceFactory.createLine3d();
        vs = new Vertex();
        vs.create(2,0,0);
        vs.setVertex(0, 0, 0, 0);
        vs.setVertex(0, size, 0, 0);
        line.setVertex(vs);
        line.setColor(1.0f, 0, 0);
        obj.addMesh(line);

        line = ResourceFactory.createLine3d();
        vs = new Vertex();
        vs.create(2,0,0);
        vs.setVertex(0, 0, 0, 0);
        vs.setVertex(0, 0, size, 0);
        line.setVertex(vs);
        line.setColor(0, 1.0f, 0);
        obj.addMesh(line);

        line = ResourceFactory.createLine3d();
        vs = new Vertex();
        vs.create(2,0,0);
        vs.setVertex(0, 0, 0, 0);
        vs.setVertex(0, 0, 0, size);
        line.setVertex(vs);
        line.setColor(0, 0, 1.0f);
        obj.addMesh(line);
        
        return obj;
    }

    // 平面枠を備えた線分
    public static Object3d createLineSquare(float size) {

        Object3d obj = ResourceFactory.createObject3d("LineSquare");
        Line3d line = null;
        Vertex vs = null;

        float len = size / 2;

        ///////////////////////////////
        // 線分を作る
        ///////////////////////////////
        line = ResourceFactory.createLine3d();
        vs = new Vertex();
        vs.create(2,0,0);
        vs.setVertex(0, 0,  len, 0);
        vs.setVertex(1, 0, -len, 0);
        line.setVertex(vs);
        line.setColor(1.0f, 1.0f, 0.0f);
        obj.addMesh(line);

        ///////////////////////////////
        // 枠を作る
        // 0  3
        // 1  2
        ///////////////////////////////
        line = ResourceFactory.createLine3d();
        vs = new Vertex();
        vs.create(4,0,0);
        vs.setVertex(0, -len,  len, 0);
        vs.setVertex(1, -len, -len, 0);
        vs.setVertex(2,  len, -len, 0);
        vs.setVertex(3,  len,  len, 0);
        line.setLoop(true);
        line.setVertex(vs);
        line.setColor(1.0f, 1.0f, 1.0f);
        obj.addMesh(line);

        return obj;
    }

    public static Object3d createCircle(float size, float r, float g, float b) {

        int num = 16;
        Vertex vs = new Vertex();
        vs.create(num,0,0);
        float radius = size;
        float radStep = Rad.PI2 / num;
        float rad = 0;
        for (int i = 0; i < num; i++) {
            float x = Rad.cos(rad) * radius;
            float y = Rad.sin(rad) * radius;
            rad += radStep;
            vs.setVertex(i, x, y, 0);
        }
        
        Line3d line = ResourceFactory.createLine3d();
        line.setVertex(vs);
        line.setLoop(true);
        line.setColor(r, g, b);

        Object3d obj = ResourceFactory.createObject3d("CIRCLE");
        obj.addMesh(line);
        
        return obj;
    }

    public static Object3d createLineSphere(float size, float r, float g, float b) {
        Object3d obj = ResourceFactory.createObject3d("LINESPHERE");
        Object3d obj1 = createCircle(size, r, g, b);
        Object3d obj2 = createCircle(size, r, g, b);
        obj1.show(true);
        obj2.show(true);
        obj2.rotateX(Rad.toRad(90.0f));
        obj.add(obj1);
        obj.add(obj2);
        return obj;
    }
    
    public static Object3d createFloor(float x, float y, Texture texture) {
        Material material = ResourceFactory.createMaterial();
        material.init();
        Mesh mesh = ResourceFactory.createMesh();
        mesh.setVertex(new Vertex().createPoly4(x, y));
        mesh.setTexture(texture);
        mesh.setMaterial(material);
        Object3d obj = ResourceFactory.createObject3d("FLOOR");
        obj.addMesh(mesh);
        obj.rotateX(Rad.toRad(-90.0f));
        return obj;
    }

    public static Object3d createSprite3d(float w, float h, Texture texture) {
        Mesh mesh = ResourceFactory.createMesh();
        mesh.setVertex(new Vertex().createPoly4(w, h));
        mesh.setTexture(texture);
        mesh.setMaterial(null);
        mesh.unsetState(Mesh.CULL_FACE|Mesh.LIGHTING);
        Object3d obj = ResourceFactory.createObject3d("SPRITE3D");
        obj.addMesh(mesh);
        return obj;
    }

    public static Object3d createPoly3(float x, float y, Texture texture) {
        Material material = ResourceFactory.createMaterial();
        material.init();
        Mesh mesh = ResourceFactory.createMesh();
        mesh.setVertex(new Vertex().createPoly3(x, y));
        mesh.setTexture(texture);
        //mesh.setMaterial(material);
        mesh.setMaterial(null);
        Object3d obj = ResourceFactory.createObject3d("POLY3");
        obj.addMesh(mesh);
        obj.rotateX(Rad.toRad(-90.0f));
        return obj;
    }

    public static Object3d createCube(float x, float y, float z) {
        Material material = ResourceFactory.createMaterial();
        material.init();
        Mesh mesh = ResourceFactory.createMesh();
        mesh.setVertex(new Vertex().createCube(x, y, z));
        mesh.setMaterial(material);
        Object3d obj = ResourceFactory.createObject3d("CUBE");
        obj.addMesh(mesh);
        return obj;
    }
    
    public static Object3d createSphere(float r, int div, Texture texture) {
        Material material = ResourceFactory.createMaterial();
        material.init();
        Mesh mesh = ResourceFactory.createMesh();
        mesh.setVertex(new Vertex().createSphere(r, div));
        mesh.setTexture(texture);
        mesh.setMaterial(material);
        Object3d obj = ResourceFactory.createObject3d("SPHERE");
        obj.addMesh(mesh);
        return obj;
    }

    public static Object3d createCylinder(float r, float h, int div, Texture texture) {
        Material material = ResourceFactory.createMaterial();
        material.init();
        Mesh mesh = ResourceFactory.createMesh();
        mesh.setVertex(new Vertex().createCylinder(r, h, div));
        mesh.setTexture(texture);
        mesh.setMaterial(material);
        Object3d obj = ResourceFactory.createObject3d("CYLINDER");
        obj.addMesh(mesh);
        return obj;
    }
}