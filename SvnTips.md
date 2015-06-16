
# Heading #
## Subheading ##
### Level 3 ###
#### Level 4 ####
##### Level 5 #####
###### Level 6 ######

| **Year** | **Temperature (low)** | **Temperature (high)** |
|:---------|:----------------------|:-----------------------|
| 1900     | -10                   | 25                     |
| 1910     | -15                   | 30                     |
| 1920     | -10                   | 32                     |
| 1930     | _N/A_                 | _N/A_                  |
| 1940     | -2                    | 40                     |

チェックアウト
```
svn co https://ochagl.googlecode.com/svn/ochagl/trunk/ --username ocha.awk
```

コミット
```
svn ci -m "MIMEを設定" dist\rigidbody.jnlp --username ocha.awk
```

リリースタグの例
```
svn cp -m "Release 20080719" https://ochagl.googlecode.com/svn/ochagl/trunk https://ochagl.googlecode.com/svn/ochagl/tags/REL-20080719 --username ocha.awk
```

ブランチの例
```
svn cp -m "Shooting Game" https://ochagl.googlecode.com/svn/ochagl/trunk https://ochagl.googlecode.com/svn/ochagl/branches/TRY-ShooGame --username ocha.awk
```

ブランチチェックアウト
```
svn co https://ochagl.googlecode.com/svn/ochagl/branches/TRY-ShooGame ochashoo
```

削除
```
svn rm -m "" https://ochagl.googlecode.com/svn/ochagl/tags/REL-20070807
```

MIMEタイプの設定方法
※ローカルのファイルを指定する
```
cd rigidbody
svn propset svn:mime-type "application/x-java-jnlp-file" dist\rigidbody.jnlp
```

プロパティの一覧を見る
```
svn proplist dist/rigidbody.jnlp
```

プロパティの中身を見る
```
svn propget prop-name file
```

ディレクトリを管理対象外とする
```
cd 対象ディレクトリの親ディレクトリ
svn propedit prop-name .
# エディタで対象ディレクトリを一行おきに記述する
```