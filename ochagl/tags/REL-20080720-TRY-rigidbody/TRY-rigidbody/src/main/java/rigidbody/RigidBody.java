package rigidbody;

import com.googlecode.ochagl.app.DebugFont;
import com.googlecode.ochagl.graphics.Object3d;
import com.googlecode.ochagl.graphics.StringSprite;
import com.googlecode.ochagl.math.Mat3;
import com.googlecode.ochagl.math.Mat4;
import com.googlecode.ochagl.math.Quat;
import com.googlecode.ochagl.math.Rad;
import com.googlecode.ochagl.math.Vec3;

/*

斜面での加速が少ない
スリップしたりしなかったり
高速回転させるとバウンドする
なんか違う
回転だけさせたいのに移動してしまう
地面を突き抜ける

動量Ｌと角速度ωの関係は、質量ｍと慣性テンソル［Ｉ］を使って次の通り： 

Ｐ ＝ ｍｖ 
Ｌ ＝ ［Ｉ］ω 

外力が加わったときの運動量・角運動量の変化は、外力の力積Ｆと
重心から作用点へのベクトルｒを使って 

ΔＰ ＝ Ｆ 
ΔＬ ＝ ｒ＊Ｆ 

と表される。 

求まった速度と角速度によって、重心の位置ｘと姿勢[Ｒ]を変化させればＯＫ。 

ｘ’＝ｖ
［Ｒ］’＝［ω＊］［Ｒ］ 

////////////////////////////////////////////////////////////
 L = |I|*ω
 ω(t) = |I|(t)^-1 * L(t) 


慣性テンソルの求め方
 I(t) = R(t) * I0 * R(t)^t;
 I(t)^-1 = R(t) * I0^-1 * R(t)^t;
この I(t)^-1 とL(T)で角速度を求む

回転すると姿勢が変わり
姿勢が変わると慣性テンソルが変わり
慣性テンソルが変わると速度が変わる
 
外力を与えると運動量が変化する（内力では変化しない）


//////////////////////////////////////////////////////////
■前提

質点の運動方程式は

d(mv)/dt = F

であるが、これを回転に当てはめると
右辺をトルクとする為に両辺にrを掛け

r x d(mv)/dt = r x F

微分の積の公式を使って左辺を変形すると

d(r x mv)/dt = r x F
 
mv は 運動量だから
 
d(r x P)/dt = r x F

r x F はトルクとし N であらわし、
r x P は角運動量とし、Lであらわす

結局、回転の運動方程式は 

dL/dt = N

となる

--------------------------- 
トルクN は力の作用点までの位置ベクトルRと力との外戚で求む
 
N = r x F

角運動量は作用点までの位置ベクトルrと作用点の運動量との外積である
L = r x mv
L = r x P

回転の運動方程式から角運動量の微分はトルクなので
dL/dt = N
dL/dt = r x F
となり、角運動量の微小変化は
dL = r x F * dt
となる

以上より、外力を与えたときは
 r x F * dt

を用いて角運動量を変化させる
 

*/
public abstract class RigidBody {

    /**
     * 重力.
     */
    static protected final Vec3 G = new Vec3(0.0f, -9.8f, 0.0f);

    static protected final float MUE0 = 3.0f;     // 最大摩擦係数
    static protected final float MUE = 0.9f;      // 摩擦係数

    public Vec3 touchPos = new Vec3(0, 0, 0);

    /**
     * 質量.
     */
    protected float mass_ = 0.0f;

    /**
     * ワールド位置.
     */
    protected Vec3 X_ = new Vec3(0, 0, 0);

    /**
     * 1フレーム前のワールド位置.
     */
    protected Vec3 lastX_ = new Vec3(0, 0, 0);

    /**
     * ワールド速度.
     */
    protected Vec3 V_ = new Vec3(0, 0, 0);

    /**
     * 力の総計.
     */
    protected Vec3 F_ = new Vec3(0, 0, 0);

    /**
     * 運動量.
     */
    protected Vec3 P_ = new Vec3(0, 0, 0);

    /**
     * ワールド角速度.
     */
    protected Vec3 W_ = new Vec3(0, 0, 0);

    /**
     * トルク.
     */
    protected Vec3 T_ = new Vec3(0, 0, 0);

    /**
     * 角運動量.
     */
    protected Vec3 L_ = new Vec3(0, 0, 0);

    /**
     * 初期慣性テンソル.
     */
    protected Mat3 I0_ = new Mat3();

    /**
     * 初期慣性テンソル逆数.
     */
    protected Mat3 I0inv_ = new Mat3();
    
    /**
     * 慣性テンソル逆数.
     */
    protected Mat3 Iinv_ = new Mat3();
    
    /**
     * 姿勢.
     */
    protected Quat Q_ = new Quat();
    
    /**
     * メッシュオブジェクト.
     */
    protected Object3d obj_ = null;

    protected String szFriction_ = "";
    protected float impuls_ = 0.0f;
    protected float mue0_ = 0.0f;
    protected float muef_ = 0.0f;
    protected float mued_ = 0.0f;

    // 計算用
    protected Mat3 tmpMat1 = new Mat3();
    protected Mat3 tmpMat2 = new Mat3();
    protected Vec3 tv1 = new Vec3(0, 0, 0);
    protected Vec3 tv2 = new Vec3(0, 0, 0);
    protected Vec3 tv3 = new Vec3(0, 0, 0);
    protected Vec3 tv4 = new Vec3(0, 0, 0);
    protected Vec3 tv5 = new Vec3(0, 0, 0);
    protected Quat tmpQ1 = new Quat();
    protected Quat tmpQ2 = new Quat();

    protected static final boolean DEBUG = true;
    protected StringSprite[] strings_ = new StringSprite[7];

    /**
     * コンストラクタ.<br>
     * @param mass 質量
     * @param obj メッシュオブジェクト
     */
    public RigidBody(float mass, Object3d obj) {
        mass_ = mass;
        obj_ = obj;
        reset(0, 0, 0);
    }

    /**
     * 剛体の属性を初期化する.<br>
     */
    public void reset(float x, float y, float z)
    {
        Mat4 rm = obj_.getLocalMatrix();
        rm.setIdentity();
        Q_.set(rm);
        V_.set(0,0,0);
        W_.set(0,0,0);
        P_.set(0,0,0);
        L_.set(0,0,0);
        X_.set(x,y,z);
        lastX_.set(X_);
        resetCalc();
        calcInertia();
        updateObj();
    }

    /**
     * 力をリセットする.<br>
     */
    public void resetCalc() {
        F_.set(0,0,0);
        T_.set(0,0,0);
    }

    /**
     * 剛体位置を取得する.<br>
     * @return 剛体位置
     */
    public Vec3 getPosition() {
        return X_;
    }

    /**
     * 剛体位置を取得する.<br>
     * @return 剛体位置
     */
    public void setPosition(Vec3 v) {
        X_.set(v);
    }

    /**
     * 剛体位置を取得する.<br>
     * @return 剛体位置
     */
    public void setLastPosition(Vec3 v) {
        lastX_.set(v);
    }

    /**
     * 位置フレーム前の剛体位置を取得する.<br>
     * @return 位置フレーム前の剛体位置
     */
    public Vec3 getLastPosition() {
        return lastX_;
    }

    /**
     * 剛体の速度を取得する.<br>
     * @return 剛体速度
     */
    public Vec3 getVelocity() {
        return V_;
    }

    /**
     * 位置と角度を更新する.<br>
     * @param dtime 時間
     */
    public void update(float dtime)
    {

        float dt = dtime;      //
        Quat q1 = tmpQ1;  // 
        Quat q2 = tmpQ2;  // 
        Mat3 rm = tmpMat1;
        Vec3 w = tv1;  //
        Vec3 fdt = tv1;  // 加速度
        Vec3 vdt = tv1;  // 速度 * dt

        lastX_.set(X_);

        fdt.set(F_);
        fdt.scale(dt);
        P_.add(fdt);

        //fdt.add(T_);
        fdt.set(T_);
        fdt.scale(dt);
        L_.add(fdt);
        
        calcVelocity(dt);
        calcInertia();
        
        ///////////////
        // 位置更新
        ///////////////

        // 位置
        vdt.set(V_);
        vdt.scale(dt);
        X_.add(vdt);
        
        ///////////////
        // 姿勢更新
        ///////////////

        // クオータニオンをdtで微分
        // Q += (1/2)Q * Q(ω, 0)dt;
        w.set(W_);
        rm.set(obj_.getWorldMatrix()); 
        rm.transpose();
        rm.transform(w);
        q1.set(Q_);
        q1.scale(0.5f);
        q2.set(w.x, w.y, w.z, 0);
        q1.mul(q2);
        q1.scale(dt);
        Q_.add(q1);
        Q_.normalize();
        
        /////////////////////
        // メッシュへ反映
        /////////////////////
        updateObj();
    }

    /**
     * メッシュの位置と姿勢を更新.<br> 
     */
    private void updateObj()
    {
        obj_.getLocalMatrix().set(Q_);
        obj_.setPosition(X_);
        obj_.getLocalMatrix().normalize();
    }

    /**
     * 各種の力を計算する.<br> 
     */
    public void calcForce(float dtime)
    {
        // 重力
        addForce(X_, tv1.scale(mass_, G));
        
        float c = 3.0f * Rad.PI2 * 2 * 0.0001f;
        // 空気抵抗(速度)
        tv1.scale(c, V_);
        P_.sub(tv1);

        // 空気抵抗(角速度)
        tv1.scale(c, W_);
        L_.sub(tv1);
    }

    /**
     * 慣性テンソルを初期化する.<br>
     */
    public abstract void initInertia();

    
    /**
     * 現在の姿勢の逆慣性テンソルを求める.<br>    
     * R * I^-1 * R^t;
     * @param dtime 時間
     */
    public void calcInertia() {
        Mat3 rm = tmpMat1;
        Mat3 tm = tmpMat2;
        rm.set(Q_);
        tm.set(rm);
        tm.transpose();
        rm.mul(I0inv_);
        Iinv_.mul(rm, tm);
    }

    /**
     * 運動量から速度と角速度を計算.<br>
     * ω(t) = |I|(t)^-1 * L(t) 
     * @param dtime 時間
     */
    public void calcVelocity(float dtime) {    
        V_.scale(1.0f/mass_, P_);
        Iinv_.transform(L_, W_);
        //V_.turncate(0.9f * 60.0f);
    }
    
    /**
     * 衝突応答.<br>
     * @param dt 時間
     * @param ap 剛体上の作用点(action point)
     * @param tp 静止物体上の接触位置(touch point)
     * @param nv 接触位置から剛体位置への法線
     * @param depth　めり込み量
     * @param num 接触数
     */
    public void collisionResponse(final float dt,
                                  final Vec3 ap,
                                  final Vec3 nv,
                                  final float depth,
                                  final float num)
    {
        Vec3 F  = new Vec3();
        Vec3 dV = new Vec3(V_);
        Vec3 FV = new Vec3();               // 接触面の速度
        Vec3 DN = new Vec3().scale(-1, nv); // めり込み方向(depth normal)
        Vec3 R  = new Vec3().sub(ap, X_);   // 作用点へのベクトル 

        final float dvel = dV.dot(DN);     // 接触法線の相対速度量 

        // ペナルティ法による撃力
        impuls_ = -400 * depth - 10 * dvel;
        //impuls_ = -400 * depth - 15 * dvel;
        //impuls_ = -100 * depth - 10 * dvel;
        addForce(ap, tv1.scale(impuls_, DN));
        //addImpuls(ap, tv1.scale(impuls_, DN), dt);
        
        // 接触面に平行な力を取り出す
        F.scale(1.0f/mass_, P_);
        F.sub(tv1.scale(F.dot(DN), DN));

        szFriction_ = "";
        mue0_ = MUE0 * Math.abs(impuls_);  // 最大静止摩擦力
        mued_ = MUE  * Math.abs(impuls_);  // 動摩擦力
        muef_ = F.norm();                  // 摩擦力
        if (muef_ <= mue0_) {

            // 静止摩擦力(グリップしてる！)
            szFriction_ = "GRIP";

            // 接触点を中心とした角速度から速度を求める
            // 求めた速度から力を算出
            dV.add(V_, tv1.cross(W_, R));
            dV.scale(dt);
            F.scale(mass_/dt/num, dV);
            F.sub(tv1.scale(F.dot(DN), DN));
            addForce(X_, F.scale(-1));

            // トルク
            addForce(ap, F);

        } else if (muef_ > 0.00001f) {
        
            // 動摩擦力(滑ってる！)
            szFriction_ = "SLIP";

            F.sub(tv1.scale(F.dot(DN), DN));
            F.normalize();
            F.scale(-mued_);
            addForce(X_, F);
        }
    }

    /**
     * デバッグ情報の表示.
     */
    public void dispInfo() {
        if (!DEBUG)
            return;
        int x = 10;
        int y = 11;
        DebugFont.print(x, y++, "V:" + V_.norm() * (1.0f/60.0f));
        DebugFont.print(x, y++, "W:" + W_.norm() * (1.0f/60.0f));
        DebugFont.print(x, y++, szFriction_);
        DebugFont.print(x, y++, "mue0:" + mue0_);
        DebugFont.print(x, y++, "muef:" + muef_);
        DebugFont.print(x, y++, "mued:" + mued_);
        DebugFont.print(x, y++, "impuls_:" + impuls_);
        DebugFont.print(x, y++, "X    :" + X_.toString());
        DebugFont.print(x, y++, "lastX:" + lastX_.toString());
    }

    /**
     * 力を加える.
     * @param pos 力の作用点
     * @param force 力
     */
    public void addForce(Vec3 pos, Vec3 force) {
//        T_.add(new Vec3().cross(new Vec3().sub(pos, X_), force));
//        F_.add(force);
        addImpuls(pos, force, 1.0f/60);
    }

    /**
     * 力を加える.
     * @param pos 力の作用点
     * @param force 力
     * @param dt 刻み時間
     */
    public void addImpuls(Vec3 pos, Vec3 force, float dt) {
        Vec3 r = new Vec3().sub(pos, X_);
        Vec3 f = new Vec3().scale(dt, force);
        L_.add(new Vec3().cross(r, f));
        P_.add(f);
    }

    /**
     * 力を加える.
     * @param pos 力の作用点
     * @param force 力
     */
    public void addTorque(Vec3 pos, Vec3 force) {
        T_.add(new Vec3().cross(new Vec3().sub(pos, X_), force));
    }

}
