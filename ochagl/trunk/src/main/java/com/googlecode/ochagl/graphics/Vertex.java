package com.googlecode.ochagl.graphics;

import com.googlecode.ochagl.math.Rad;
import com.googlecode.ochagl.math.TexCoord2;
import com.googlecode.ochagl.math.Vec3;


/**
 * 頂点管理クラス.可視性，公開メソッド，仕組みそのものについて見直す必要あり！
 */
public class Vertex {

    /**
     * フラグ.
     */
    private int flag_;

    /**
     * 頂点数.
     */
    private int vertexNum_;

    /**
     * インデクス数.
     */
    private int indexNum_;

    /**
     * 3角形ポリゴン数.
     */
    private int polyNum_;

    /**
     * 頂点配列.
     */
    private Vec3[] vertexTop_;

    /**
     * 面法線配列.
     */
    private Vec3[] fnormalTop_;

    /**
     * 頂点法線配列.
     */
    private Vec3[] vnormalTop_;

    /**
     * 頂点色配列.
     */
    private int[] vcolorTop_;

    /**
     * UV 配列.
     */
    private TexCoord2[] uvTop_;

    /**
     * インデクス配列.
     */
    private int[] indexTop_;

    /**
     * フラットポリのインデクス配列へのポインタ.
     */
    private int[] pFT3I_;

    /**
     * グーロポリのインデクス配列へのポインタ.
     */
    private int[] pGT3I_;

    /**
     * フラットポリンゴン数．
     */
    private int nFT3I_;

    /**
     * グーロポリンゴン数．
     */
    private int nGT3I_;

    /**
     * コンストラクタ
     */
    public Vertex() {
        flag_ = 0;
        vertexNum_ = 0;
        indexNum_ = 0;
        polyNum_ = 0;

        vertexTop_ = null;
        fnormalTop_ = null;
        vnormalTop_ = null;
        vcolorTop_ = null;
        uvTop_ = null;
        indexTop_ = null;

        pFT3I_ = null;
        pGT3I_ = null;
        nFT3I_ = 0;
        nGT3I_ = 0;
    }

    /**
     * フラグを取得する．
     *
     * @return フラグ
     */
    public int getFlag() {

        return flag_;
    }

    /**
     * 頂点数を取得する．
     *
     * @return 頂点数
     */
    public int getVertexNum() {

        return vertexNum_;
    }

    /**
     * インデックス数を取得する．
     *
     * @return インデックス数
     */
    public int getIndexNum() {

        return indexNum_;
    }

    /**
     * 頂点配列への参照を取得する．
     *
     * @return 頂点配列への参照
     */
    public Vec3[] getVertexTop() {

        return vertexTop_;
    }

    /**
     * 面法線配列への参照を取得する．
     *
     * @return 面法線配列への参照
     */
    public Vec3[] getNormalTop() {

        return fnormalTop_;
    }

    /**
     * 頂点法線配列への参照を取得する．
     *
     * @return 頂点法線配列への参照
     */
    public Vec3[] getVnormalTop() {

        return vnormalTop_;
    }

    /**
     * 頂点カラー配列への参照を取得する．
     *
     * @return 頂点カラー配列への参照
     */
    public int[] getVcolorTop() {

        return vcolorTop_;
    }

    /**
     * UV配列への参照を取得する．
     *
     * @return UV配列への参照
     */
    public TexCoord2[] getUvTop() {

        return uvTop_;
    }

    /**
     * インデックス配列への参照を取得する．
     *
     * @return インデックスカラー配列への参照
     */
    public int[] getIndexTop() {

        return indexTop_;
    }

    /**
     * フラットポリゴンのインデックス配列を設定する．
     *
     * @param p フラットポリゴンのインデックス配列
     */
    public void setFT3I(final int[] p) {
        pFT3I_ = p;
    }

    /**
     * グーロポリゴンのインデックス配列を設定する．
     *
     * @param p グーロポリゴンのインデックス配列
     */
    public void setGT3I(final int[] p) {
        pGT3I_ = p;
    }

    /**
     * フラットポリゴンのインデックス数を設定する．
     *
     * @param n フラットポリゴンのインデックス数
     */
    public void setFT3INum(final int n) {
        nFT3I_ = n;
    }

    /**
     * グーロポリゴンのインデックス数を設定する．
     *
     * @param n グーロポリゴンのインデックス数
     */
    public void setGT3INum(final int n) {
        nGT3I_ = n;
    }

    /**
     * フラットポリゴンのインデックス配列を取得する．
     *
     * @return フラットポリゴンのインデックス配列
     */
    public int[] getFT3I() {

        return pFT3I_;
    }

    /**
     * グーロポリゴンのインデックス配列を取得する．
     *
     * @return グーロポリゴンのインデックス配列
     */
    public int[] getGT3I() {

        return pGT3I_;
    }

    /**
     * フラットポリゴンのインデックス数を取得する．
     *
     * @return フラットポリゴンのインデックス数
     */
    public int getnFT3I() {

        return nFT3I_;
    }

    /**
     * グーロポリゴンのインデックス数を取得する．
     *
     * @return グーロポリゴンのインデックス数
     */
    public int getnGT3I() {

        return nGT3I_;
    }

    /**
     * 頂点バッファを生成する．
     *
     * @param nvertex 頂点数
     * @param nindex インデックス数
     * @param flag フラグ
     *
     * @return 生成した頂点バッファ
     */
    public Vertex create(
        final int nvertex,
        final int nindex,
        final int flag) {
        vertexNum_ = nvertex;
        indexNum_ = nindex;
        polyNum_ = nindex / 3;
        flag_ = flag;

        vertexTop_ = new Vec3[vertexNum_];
        vnormalTop_ = new Vec3[vertexNum_];
        fnormalTop_ = new Vec3[polyNum_];
        uvTop_ = new TexCoord2[vertexNum_];
        indexTop_ = new int[indexNum_];

        for (int i = 0; i < vertexTop_.length; i++) {
            vertexTop_[i] = new Vec3();
        }

        for (int i = 0; i < vnormalTop_.length; i++) {
            vnormalTop_[i] = new Vec3();
        }

        for (int i = 0; i < fnormalTop_.length; i++) {
            fnormalTop_[i] = new Vec3();
        }

        for (int i = 0; i < uvTop_.length; i++) {
            uvTop_[i] = new TexCoord2();
        }

        vcolorTop_ = new int[vertexNum_];

        // qqq
        for (int i = 0; i < vcolorTop_.length; i++) {
            vcolorTop_[i] = 0xffffffff;
        }

        return this;
    }

    /**
     * 頂点を設定する．
     *
     * @param idx インデックス
     * @param x Ｘ座標
     * @param y Ｙ座標
     * @param z Ｚ座標
     * @param nx Ｘ頂点法線
     * @param ny Ｙ頂点法線
     * @param nz Ｚ頂点法線
     * @param u Ｕ座標
     * @param v Ｖ座標
     */
    public final void setVertexNUV(
        final int idx,
        final float x,
        final float y,
        final float z,
        final float nx,
        final float ny,
        final float nz,
        final float u,
        final float v) {
        vertexTop_[idx].set(x, y, z);
        vnormalTop_[idx].set(nx, ny, nz);
        uvTop_[idx].set(u, v);
    }

    /**
     * 頂点を設定する．
     *
     * @param idx インデックス
     * @param x Ｘ座標
     * @param y Ｙ座標
     * @param z Ｚ座標
     */
    public void setVertex(
        final int idx,
        final float x,
        final float y,
        final float z) {
        vertexTop_[idx].set(x, y, z);
    }

    /**
     * 面法線，頂点法線を生成する．
     */
    public void createNormal() {

        Vec3[] vbuf = vertexTop_;
        int[] ibuf = indexTop_;
        Vec3[] fnbuf = fnormalTop_;
        Vec3[] vnbuf = vnormalTop_;
        Vec3 v0 = new Vec3();
        Vec3 v1 = new Vec3();
        Vec3 v2 = new Vec3();
        Vec3 v3 = new Vec3();
        int i;
        int j;
        int i0;
        int i1;
        int i2;

        //////////////////
        // 面法線求める
        //////////////////
        // 右手系なので左回りにかける
        //  0 2
        //  1
        for (i = 0; i < ibuf.length; i += 3) {
            i0 = ibuf[i + 0];
            i1 = ibuf[i + 1];
            i2 = ibuf[i + 2];

            v0.set(vbuf[i0].x, vbuf[i0].y, vbuf[i0].z);
            v1.set(vbuf[i1].x, vbuf[i1].y, vbuf[i1].z);
            v2.set(vbuf[i2].x, vbuf[i2].y, vbuf[i2].z);
            v1.sub(v0);
            v2.sub(v0);
            v3.cross(v1, v2);

            //			v3.normalize();
            fnbuf[i / 3].set(v3);

            //System.out.println("v3:" + v3.toString());
            //System.out.println("v3len:" + v3.length());
            //System.out.println("i0:" + i0);
            //System.out.println("i1:" + i1);
            //System.out.println("i2:" + i2);
            //System.out.println("fn:" + fnbuf[i/3].toString());
        }

        //////////////////
        // 頂点法線求める
        //////////////////
        // 初期化
        for (i = 0; i < vertexNum_; i++) {
            vnbuf[i].set(0, 0, 0);
        }

        // 頂点を共有する、正規化前の面法線を足しこむ
        int[] vnums = new int[vertexNum_];

        for (i = 0; i < polyNum_; i++) {

            for (j = 0; j < 3; j++) {

                if (fnbuf[i].len() > 0) {
                    vnbuf[ibuf[(i * 3) + j]].add(fnbuf[i]);
                    vnums[ibuf[(i * 3) + j]]++;
                }
            }
        }

        // 足しこんだ頂点法線を、正規化
        for (i = 0; i < vertexNum_; i++) {

            //vnbuf[i].scale(1.0/vnums[i]);
            vnbuf[i].normalize();
        }

        // 面法線を正規化
        for (i = 0; i < polyNum_; i++) {

            if (fnbuf[i].len() > 0) {
                fnbuf[i].normalize();
            }
        }

        /*
         * for (i = 0; i < fnbuf.length; i++) System.out.println("fnbuf[" + i +
         * "].length():" + fnbuf[i].length());
         *
         * for (i = 0; i < vnums.length; i++) System.out.println("vnums[" + i +
         * "]:" + vnums[i]);
         */
    }

    /**
     * ポリゴン(3頂点)の頂点を生成する．
     *
     * @param sizew ポリゴンの幅
     * @param sizeh ポリゴンの高さ
     *
     * @return 頂点
     */
    public Vertex createPoly3(
        final float sizew,
        final float sizeh) {
        create(3, 3, 0);
        pGT3I_ = indexTop_;
        nGT3I_ = indexNum_ / 3;

        /////////////////////////
        // 頂点を作る
        /////////////////////////
        float x = sizew / 2;
        float y = sizeh / 2;

        // xyz, nrm, uv
        setVertexNUV(0, -x, +y, 0, 0, 0, 1, 0, 0); // 0
        setVertexNUV(1, -x, -y, 0, 0, 0, 1, 0, 1); // 1
        setVertexNUV(2, +x, -y, 0, 0, 0, 1, 1, 1); // 2

        /////////////////////////
        // インデックス
        /////////////////////////
        // 頂点並び
        // 0
        // 1 2
        int[] indices = {
                0, 1,
                2,
            };

        for (int i = 0; i < indices.length; i++) {
            indexTop_[i] = indices[i];
        }

        return this;
    }

    /**
     * ポリゴン(4頂点)の頂点を生成する．
     *
     * @param sizew ポリゴンの幅
     * @param sizeh ポリゴンの高さ
     *
     * @return 頂点
     */
    public Vertex createPoly4(
        final float sizew,
        final float sizeh) {
        create(4, 6, 0);
        pGT3I_ = indexTop_;
        nGT3I_ = indexNum_ / 3;

        /////////////////////////
        // 頂点を作る
        /////////////////////////
        float x = sizew / 2;
        float y = sizeh / 2;

        // xyz, nrm, uv
        //		setVertexNUV(0, -x,+y,0, 0,0,1, 0,0); // 0
        //		setVertexNUV(1, -x,-y,0, 0,0,1, 0,1); // 1
        //		setVertexNUV(2, +x,-y,0, 0,0,1, 1,1); // 2
        //		setVertexNUV(3, +x,+y,0, 0,0,1, 1,0); // 3
        setVertexNUV(0, -x, +y, 0, 0, 0, 1, 0, 0); // 0
        setVertexNUV(1, -x, -y, 0, 0, 0, 1, 0, 1); // 1
        setVertexNUV(2, +x, -y, 0, 0, 0, 1, 1, 1); // 2
        setVertexNUV(3, +x, +y, 0, 0, 0, 1, 1, 0); // 3

        /////////////////////////
        // インデックス
        /////////////////////////
        //	頂点並び
        //	0 3
        //	1 2
        int[] indices = {
                0, 1,
                2, 2,
                3, 0,
            };

        for (int i = 0; i < indices.length; i++) {
            indexTop_[i] = indices[i];
        }

        return this;
    }

    /**
     * 箱の頂点を生成する．
     *
     * @param sizex Ｘ方向のサイズ
     * @param sizey Ｙ方向のサイズ
     * @param sizez Ｚ方向のサイズ
     *
     * @return 頂点
     */
    public Vertex createCube(
        final float sizex,
        final float sizey,
        final float sizez) {
        create(4 * 6, 6 * 2 * 3, 0);
        pGT3I_ = indexTop_;
        nGT3I_ = indexNum_ / 3;

        /////////////////////////
        // 頂点を作る
        /////////////////////////

        /*
         * 頂点並び 2 1
         *
         * 3 0
         */
        int i = 0;
        float x = sizex / 2;
        float y = sizey / 2;
        float z = sizez / 2;

        // up
        // xyz, nrm, uv
        setVertexNUV(i++, +x, +y, +z, 0, +1, 0, 0, 0); // 0
        setVertexNUV(i++, +x, +y, -z, 0, +1, 0, 1, 0); // 1
        setVertexNUV(i++, -x, +y, -z, 0, +1, 0, 1, 1); // 2
        setVertexNUV(i++, -x, +y, +z, 0, +1, 0, 0, 1); // 3

        // bottom
        setVertexNUV(i++, +x, -y, -z, 0, -1, 0, 0, 0); // 4
        setVertexNUV(i++, +x, -y, +z, 0, -1, 0, 1, 0); // 5
        setVertexNUV(i++, -x, -y, +z, 0, -1, 0, 1, 1); // 6
        setVertexNUV(i++, -x, -y, -z, 0, -1, 0, 0, 1); // 7

        // left
        setVertexNUV(i++, -x, -y, +z, -1, 0, 0, 0, 0); // 8
        setVertexNUV(i++, -x, +y, +z, -1, 0, 0, 1, 0); // 9
        setVertexNUV(i++, -x, +y, -z, -1, 0, 0, 1, 1); // 10
        setVertexNUV(i++, -x, -y, -z, -1, 0, 0, 0, 1); // 11

        // right
        setVertexNUV(i++, +x, -y, -z, +1, 0, 0, 0, 0); // 12
        setVertexNUV(i++, +x, +y, -z, +1, 0, 0, 1, 0); // 13
        setVertexNUV(i++, +x, +y, +z, +1, 0, 0, 1, 1); // 14
        setVertexNUV(i++, +x, -y, +z, +1, 0, 0, 0, 1); // 15

        // front
        setVertexNUV(i++, +x, -y, +z, 0, 0, +1, 0, 0); // 16
        setVertexNUV(i++, +x, +y, +z, 0, 0, +1, 1, 0); // 17
        setVertexNUV(i++, -x, +y, +z, 0, 0, +1, 1, 1); // 18
        setVertexNUV(i++, -x, -y, +z, 0, 0, +1, 0, 1); // 19

        // back
        setVertexNUV(i++, -x, -y, -z, 0, 0, -1, 0, 0); // 20
        setVertexNUV(i++, -x, +y, -z, 0, 0, -1, 1, 0); // 21
        setVertexNUV(i++, +x, +y, -z, 0, 0, -1, 1, 1); // 22
        setVertexNUV(i++, +x, -y, -z, 0, 0, -1, 0, 1); // 23

        /////////////////////////
        // インデックス
        /////////////////////////
        int[] indices = {
                0, 1,
                2, 2,
                3, 0,
                4, 5,
                6, 6,
                7, 4,
                8, 9,
                10, 10,
                11, 8,
                12, 13,
                14, 14,
                15, 12,
                16, 17,
                18, 18,
                19, 16,
                20, 21,
                22, 22,
                23, 20,
            };

        for (i = 0; i < indices.length; i++) {
            indexTop_[i] = indices[i];
        }

        createNormal();

        return this;
    }

    /**
     * 球の頂点を生成する．
     *
     * @param radius 半径
     * @param ndiv 分割数
     *
     * @return 頂点
     */
    public Vertex createSphere(
        final float radius,
        int ndiv) {

        int ndiv2;
        ndiv += (ndiv & 1); // 偶数にする
        ndiv2 = ndiv / 2;

        // 頂点数 = 横分割数 * 縦分割数(上下のつむじの点を含めない) + 2(上下のつむじ)
        int vcount = (ndiv * (ndiv2 - 1)) + 2;

        // 最上段と最下段のポリゴンは3頂点(それ以外は４頂点)で構成される。
        //   ただし、4頂点のポリゴンは2枚にする。
        // 最上下段のインデックス数 = 横分割数 * ポリゴン数(3頂点) * 2(上下)
        // それ以外のインデックス数 = 横分割数 * 縦分割数-2(上下分を引く) * ポリゴン数(3頂点*2)
        int icount = (ndiv * 3) * 2;
        icount += (ndiv * (ndiv2 - 2) * (3 * 2));

        create(vcount, icount, 0);

        pGT3I_ = indexTop_;
        nGT3I_ = indexNum_ / 3;

        /////////////////////////
        // 頂点
        /////////////////////////
        float angz = 0;

        /////////////////////////
        // 頂点
        /////////////////////////
        float angy = 0;
        float u = 0;
        float v = 0;
        float nx = 0;
        float ny = 0;
        float nz = 0;
        float nr = 0;
        int num = 0;

        // 縦(上から下)
        for (int i = 0; i <= ndiv2; i++) {
            angz = (float) ((Math.PI * 2 * i) / ndiv);
            ny = (float) (Math.cos(angz));
            nr = (float) (Math.sin(angz));
            v = (float) i / (ndiv2);

            if ((i == 0) || (i == ndiv2)) {

                if (i == 0) {
                    setVertexNUV(num++, 0, radius * ny, 0, 0, ny, 0, u, v);
                } else {
                    setVertexNUV(num++, 0, radius * ny, 0, 0, ny, 0, u, v);
                }

                continue;
            }

            // 横
            for (int j = 0; j < ndiv; j++) {
                angy = (float) ((Math.PI * 2 * j) / ndiv);
                nx = (float) (nr * Math.sin(angy));
                nz = (float) (nr * Math.cos(angy));
                u = (float) j / ndiv;

                if (u > 1) {
                    u = 2 - u;
                }

                setVertexNUV(num++, radius * nx, radius * ny, radius * nz, nx,
                    ny, nz, u, v);
            }
        }

        /////////////////////////
        // インデックス
        /////////////////////////
        int[] pi = indexTop_;
        int idx = 0;
        int i0 = 0;
        int i1 = 0;
        int i2 = 0;
        int i3 = 0;
        int i = 0;
        int j = 0;
        int k = 0;

        for (i = 0; i < ndiv2; i++) {

            // 最上段頂点 + それ以外の頂点数
            idx = 1 + ((i - 1) * ndiv);

            if ((i == 0) || (i == (ndiv2 - 1))) {

                if (i == 0) {

                    for (j = 0; j < ndiv; j++) {

                        // 頂点並び
                        //    0
                        //  1 2
                        i0 = i;
                        i1 = 1 + j;
                        i2 = 1 + ((j + 1) % ndiv);

                        pi[k++] = i0;
                        pi[k++] = i1;
                        pi[k++] = i2;
                    }
                } else {

                    for (j = 0; j < ndiv; j++) {

                        // 頂点並び
                        //  0 2
                        //    1
                        i0 = idx + j;
                        i2 = idx + ((j + 1) % ndiv);
                        i1 = idx + ndiv;

                        pi[k++] = i0;
                        pi[k++] = i1;
                        pi[k++] = i2;
                    }
                }

                continue;
            }

            for (j = 0; j < ndiv; j++) {

                // 頂点並び
                //  0 3
                //  1 2
                i0 = idx + j;
                i3 = idx + ((j + 1) % ndiv);
                i1 = i0 + ndiv;
                i2 = i3 + ndiv;

                pi[k++] = i0;
                pi[k++] = i1;
                pi[k++] = i2;
                pi[k++] = i2;
                pi[k++] = i3;
                pi[k++] = i0;
            }
        }

        createNormal();

        return this;
    }

    /**
     * 円柱の頂点を生成する．
     *
     * @param radius 半径
     * @param height 高さ
     * @param ndiv 分割数
     *
     * @return 頂点
     */
    public Vertex createCylinder(
        final float radius,
        final float height,
        int ndiv) {
        ndiv += (ndiv & 1); // 偶数にする

        create(2 + (ndiv * 4), // 頂点数（上下つむじ＋分割数＊1分割内に含まれる頂点）
            ndiv * (3 * 4), // インデックス数（分割数＊３頂点ポリゴン＊４）
            0 // フラグ
        );

        pGT3I_ = indexTop_;
        nGT3I_ = indexNum_ / 3;

        /////////////////////////
        // 頂点
        /////////////////////////
        float angy;
        float u;
        float nx;
        float nz;
        float h = height;
        int num = 0;

        setVertexNUV(num++, 0, h, 0, 0, 1, 0, 0, 0);
        setVertexNUV(num++, 0, 0, 0, 0, -1, 0, 0, 0);

        for (int i = 0; i < ndiv; i++) {
            angy = (Rad.PI2 * i) / ndiv;
            nx = (float) Math.sin(angy);
            nz = (float) Math.cos(angy);
            u = (float) i / (ndiv); // qqq

            if (u > 1) {
                u = 2 - u;
            }

            // 上
            setVertexNUV(num++, radius * nx, h, radius * nz, 0, 1, 0, u, 1);

            // 真中
            setVertexNUV(num++, radius * nx, h, radius * nz, nx, 0, nz, u, 0);
            setVertexNUV(num++, radius * nx, 0, radius * nz, nx, 0, nz, u, 1);

            // 下
            setVertexNUV(num++, radius * nx, 0, radius * nz, 0, -1, 0, u, 1);
        }

        /////////////////////////
        // インデックス
        /////////////////////////
        int[] pi = indexTop_;
        int k = 0;

        for (int i = 0; i < ndiv; i++) {

            int j = (i + 1) % ndiv; // 次の列の頂点

            // 上辺頂点並び
            //  0
            //  1 2
            pi[k++] = 0; // 0 つむじ
            pi[k++] = 2 + (i * 4); // 1 上下つむじ:2 + i * (上下つむじ+上辺の円周頂点+下辺の円周頂点:4)
            pi[k++] = 2 + (j * 4); // 2 i+1 で次の頂点

            // 真中頂点並び１
            //  0
            //  1 2
            pi[k++] = 2 + (i * 4) + 1; // 0
            pi[k++] = 2 + (i * 4) + 2; // 1
            pi[k++] = 2 + (j * 4) + 2; // 2

            // 真中頂点並び２
            //  0 2
            //    1
            pi[k++] = 2 + (i * 4) + 1; // 0
            pi[k++] = 2 + (j * 4) + 2; // 1
            pi[k++] = 2 + (j * 4) + 1; // 2

            // 下辺頂点並び
            //  0
            //  2 1
            pi[k++] = 1; // 0
            pi[k++] = 2 + (j * 4) + 3; // 1
            pi[k++] = 2 + (i * 4) + 3; // 2
        }

        createNormal();

        return this;
    }
}
