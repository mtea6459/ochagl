### ここはどんなサイト？ ###
JOGLのデモを作っていくプロジェクトサイトです。JOGLとは、JAVAからOpneGLにアクセスするためのネイティブライブラリのこと。JOGLは日本ではからっきし流行ってませんが、海外ではそこそこ取り上げられてるっぽいです。

とりあえず剛体デモを作り中。ゆくゆくは車ゲーを作るつもりだがどうなることやら…。

### 最新デモ ###
最新デモは[こちら](http://ochagl.googlecode.com/files/rigidbody.jnlp)(Java Web Startで起動。起動しない方はJREをインストールしてください。)
![http://f.hatena.ne.jp/images/fotolife/o/o-cha/20080718/20080718074822.png](http://f.hatena.ne.jp/images/fotolife/o/o-cha/20080718/20080718074822.png)
#### 操作方法 ####

|移動|方向キー|
|:-----|:-----------|
|カメラ操作|WASD        |
|リセット|R           |
|カメラ切り替え|V           |
|カメラ切り替え後の操作|マウス   |

### ソースの落とし方 ###
SVNクライアントがあるかたは以下でソースをダウンロードできます。
```
svn co http://ochagl.googlecode.com/svn/ochagl/tags/REL-20080720-TRY-rigidbody rigidbody
```
SVNなんか使ってられるかボケー！さっさとソースよこさんか！という方は[こちら](http://code.google.com/p/ochagl/downloads/list)。

JDK1.5以上とantがインストールされてれば以下のコマンドでビルドできます。以下、デモのビルド方法を示します。
```
cd rigidbody
ant clean
ant
```
デモを起動するには
```
ant run
```
です。ただし「 ant run 」するにはjoglがインストールされてなければなりません。
joglのインストール方法は[こちら](http://itpro.nikkeibp.co.jp/article/COLUMN/20060710/242865/)。すみません。手抜きです。そのうち記事書きます…。

### SVNクライアントインストール方法 ###
SVNクライアントのない方は以下でインストールしてみてください（Windowsの場合のダウンロード方法）。
  * http://subversion.tigris.org/
  * 真ん中の左ブロックにあるWindowsBinallysをクリック
  * ページが切り替わったらWindowsアイコンのとなりにある「Apach2.2」のリンクをクリック。
  * svn-1.4.6-setup.exeをクリックしてダウンロード
  * zipだとパスを通したりしなければならず面倒なのでここではインストーラ版を落とした。
  * 落としたexeファイルを実行してエンターエンターでインストールおけ
subversionの操作方法は次のサイトが参考になります。
http://www.hyuki.com/techinfo/svninit.html
