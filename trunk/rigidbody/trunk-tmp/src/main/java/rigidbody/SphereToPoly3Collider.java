/*
 * Created on 2005/01/08
 * 
 * TODO To change the template for this generated file go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
package rigidbody;

import com.googlecode.ochagl.math.Line;
import com.googlecode.ochagl.math.Mat4;
import com.googlecode.ochagl.math.Plane;
import com.googlecode.ochagl.math.Vec3;

// TODO 接触点を正確な接触位置、推し戻し位置、接触面への垂線の3種類持つようにする

public class SphereToPoly3Collider {

    private Plane plane = new Plane();

    private Vec3 edges[] = new Vec3[3];

    private float edgesl[] = new float[3];

    public SphereToPoly3Collider() {
        for (int i = 0; i < 3; i++) {
            edges[i] = new Vec3();
        }
    }

    /**
     * 球と3角形ポリゴンとの衝突を検査する.
     * 
     * @param ci 衝突情報
     * @return
     */
    public int checkCollision(CollisionInfo ci)
    {
        Vec3 v = new Vec3().sub(ci.sp.pos, ci.lastPos);
        int res = 0;

        if (!plane.set(ci.vs))
            return 0;

        // 平面から球の中心までの距離
        float len = v.norm();
        //if (len <= ci.sp.r) {
        if (true) {
            res = checkCollision1(ci);
        } else {
            Line l = new Line();
            l.set(ci.lastPos, v);
            res = checkCollision2(l, ci);
        }
        return res;
    }

    /**
     * 点と球の半径分膨らました3角形ポリゴンとの衝突を検査する.<br>
     *  <li>平面とのチェック</li>
     *  <li>辺とのチェック</li>
     *  <li>頂点とのチェック</li>
     * @param ci
     * @return
     */
    private int checkCollision1(CollisionInfo ci)
    {
        Vec3 tp = new Vec3();
        Vec3 v1 = new Vec3();
        Vec3 v2 = new Vec3();
        Vec3 v3 = new Vec3();

        float len = 0;

        ////////////////////////////
        // 1. 平面とのチェック
        ////////////////////////////
        if (!plane.set(ci.vs)) {
            // 平面で無い
            return -1;
        }

//        if (!plane.isFront(ci.sp.pos)) {
        if (!plane.isFront(ci.sp.pos) && !plane.isFront(ci.lastPos) ) {
            // 平面の裏
            return -2;
        }

        // 平面を半径分だけ押し出す
        plane.Q.add(new Vec3().scale(ci.sp.r, plane.N));
        plane.recalc();
        if (plane.isFront(ci.sp.pos)) {
            // 平面に接触していない
            return -3;
        }

        
        v1.sub(ci.sp.pos, ci.vs[0]);
        tp.add(ci.sp.pos, v2.scale(ci.sp.r - v1.dot(plane.N), plane.N));

        // 3角形の内外判定
        // 左回りでこちらが表
        //  0
        // 1 2
        int ct = 0;
        for (int i = 0; i < 3; i++) {

            // 辺の方向ベクトル
            edges[i].sub(ci.vs[(i + 1) % 3], ci.vs[i]);

            // 三角柱の側面と球原点の表裏判定
            // (内側が表)
            v1.cross(plane.N, edges[i]);
            v1.normalize();
            v2.sub(ci.sp.pos, ci.vs[i]);
            if (v1.dot(v2) > 0.0f) {
                // 平面をなす3角形の内側
                ct++;
            }

            edgesl[i] = edges[i].normalize();
        }
        if (ct == 3) {
            // 平面上にあった
            ci.outs[0].set(tp);
            return 1;
        }

        ///////////////////////////////////////////////
        // 2. 辺とのチェック
        //    辺を軸とした円柱の間にあるかチェック
        ///////////////////////////////////////////////

        for (int i = 0; i < 3; i++) {

            v1.sub(ci.sp.pos, ci.vs[i]);
            len = v1.dot(edges[i]);
            if (len <= 0)
                continue;

            if (len <= edgesl[i]) {
                v1.scale(len, edges[i]);
                v2.add(ci.vs[i], v1);
                v3.sub(ci.sp.pos, v2);
                if (v3.len2() <= ci.sp.r * ci.sp.r) {
                    v3.normalize();
                    ci.outs[0].add(v2, v1.scale(ci.sp.r, v3));
                    return 2;
                }
            }
        }

        ///////////////////////////
        // 3. 頂点とのチェック
        ///////////////////////////

        for (int i = 0; i < 3; i++) {
            v1.sub(ci.sp.pos, ci.vs[i]);
            if (v1.len2() <= ci.sp.r * ci.sp.r) {
                v1.normalize();
                ci.outs[0].add(ci.vs[i], v2.scale(ci.sp.r, v1));
                return 3;
            }
        }

        return 0;
    }

    /**
     * 「線分」と球の半径分膨らました3角形ポリゴンとの衝突を検査する.<br>
     * <li>平面とのチェック</li>
     * <li>円柱(辺)とのチェック</li>
     * <li>球(頂点)とのチェック</li>
     */
    private int checkCollision2(Line line, CollisionInfo ci)
    {

        float len = 0;
        int res = 0;
        float[] t = new float[2];
        Line l = new Line(line);
        Vec3 tp = new Vec3();
        Vec3 v1 = new Vec3();
        Vec3 v2 = new Vec3();
        Vec3 v3 = new Vec3();

        ////////////////////////////
        // 1. 平面とのチェック
        ////////////////////////////
        if (!plane.set(ci.vs))
            return 0;

        // 平面を半径分だけ押し出す
        plane.Q.add(new Vec3().scale(ci.sp.r, plane.N));
        plane.recalc();

        if (plane.isFront(ci.sp.pos))
            return 0;

        if (plane.isFront(l.Q)) {
            // 交差点を求める
            res = Intersect.plane(l, plane, t);
            //TODO tの範囲
            //if (res == 0 || (t[0] < 0 || t[0] > 1))//ono
            if (res == 0)//ono
                return 0;
            tp = l.getP(t[0]);
        } else {
            v1.sub(ci.sp.pos, ci.vs[0]);
            v2.scale(ci.sp.r - v1.dot(plane.N), plane.N);
            tp.add(ci.sp.pos, v2);
        }

        //  0
        // 2 1
        boolean isHit = true;
        for (int i = 0; i < 3; i++) {

            // 辺の方向ベクトル
            edges[i].sub(ci.vs[(i + 1) % 3], ci.vs[i]);

            // 三角柱の側面と球原点の表裏判定
            // (外側が表)
            v1.cross(edges[i], plane.N);
            v1.normalize();
            v2.sub(tp, ci.vs[i]);
            if (v1.dot(v2) >= 0.0f) {
                isHit = false;
            }

            edgesl[i] = edges[i].normalize();
            if (edgesl[i] == 0) {
                return 0;
            }
        }
        if (isHit) {
            ci.outs[0].set(tp);
            return 1;
        }

        ///////////////////////////////////////////////
        // 2. 辺とのチェック
        //    辺を軸とした円柱の間にあるかチェック
        ///////////////////////////////////////////////

        plane.set(ci.vs);
        if (!plane.isFront(ci.sp.pos))
            return 0;

        Mat4 tm = new Mat4();
        Mat4 m = new Mat4();
        for (int i = 0; i < 3; i++) {
            int j = (i + 1) % 3;

            float d1 = v1.sub(ci.sp.pos, ci.vs[i]).dot(edges[i]);
            tp.add(ci.vs[i], v3.scale(d1, edges[i]));
            float r1 = v1.sub(ci.sp.pos, tp).normalize();

            if (r1 <= ci.sp.r) {

                // 接触する辺を特定する
                float d2 = v3.sub(ci.sp.pos, ci.vs[j]).dot(edges[i]);
                if (d1 * d2 <= 0) {
                    ci.outs[0].add(tp, v2.scale(ci.sp.r, v1));
                    return 2;
                }

            } else {

                l.set(line);
                v1.cross(plane.N, edges[i]);
                m.set(plane.N, edges[i], v1, ci.vs[i]);
                //ono
                ci.cm.set(m);
                tm.invert(m);
                tm.transform(l);
                //System.out.println("Q:" + l.Q.toString());
                //System.out.println("V:" + l.V.toString());

                Vec3[] outs = new Vec3[2];
                outs[0] = new Vec3();
                outs[1] = new Vec3();
                res = Intersect.cylinder(l, ci.sp.r, edgesl[i], outs);
                if ((res & (Intersect.RES1|Intersect.RES2)) > 0) {
                    v1.sub(ci.sp.pos, tp).normalize();
                    outs[0].add(tp, v2.scale(ci.sp.r, v1));
                    ci.outs[0].set(outs[0]);
                    return 2;
                }

            }

        }
//
//        ///////////////////////////
//        // 3. 頂点とのチェック
//        ///////////////////////////
//
//        l.set(line);
//        for (int i = 0 ; i < 3 ; i++) {
//            v1.sub(ci.sp.pos, ci.vs[i]);
//            float r = v1.normalize();
//            if(r <= ci.sp.r) {
//                ci.outs[0].add(ci.vs[i], v2.scale(ci.sp.r, v1));
//                return 3;
//            } else {
//                v1.sub(ci.vs[i], l.Q);
//                float d = v1.dot(l.V);
//                v2.normalize(l.V);
//                v3.add(l.Q, v1.scale(d, v2));
//                v1.sub(v3, ci.vs[i]);
//                r = v1.normalize();
//                if (r <= ci.sp.r) {
//                    ci.outs[0].add(ci.vs[i], v2.scale(ci.sp.r, v1));
//                    return 3;
//                }
//            }
//        }

        return 0;
    }
}