底辺ライブラリ(ビルド)
==============================
source: [![Build Status](https://travis-ci.org/monyone/library_pandoc.svg?branch=master)](https://travis-ci.org/monyone/library_pandoc) [![Coverage Status](https://coveralls.io/repos/monyone/library_pandoc/badge.svg?branch=master&service=github)](https://coveralls.io/github/monyone/library_pandoc?branch=master)

これは何?
---------
競技プログラミングで使っているコード(Java)のビルド/テスト/タイプセット環境. 

どんな底辺でも使えて, CI出来るライブラリを目指しています.

Web版について
------------
ここでビルドしたのを teihen_library の gh-pages に張り付ける事にしました。  
(リンク貼り直すのも面倒くさいので...)


なので teihen_library の方はもう gh-pages 以外は基本的に更新しません。

PDF版について
------------
どうやって配布をしたらいいんだろうか?

依存ソフトウェア
----------------

大体 JDK8 と texlive-lang-cjk とmaven2 と python3 があれば動く

+ source (ソースのビルド、簡単なテスト用)  
  + Java (OracleJDK 8)
  + maven2 (ビルド, テスト)

+ verify (実際に問題に張り付けられる体裁で管理する) 
  + Java (OracleJDK 8)
  + maven2 (ビルド)
  + python3 (貼り付け)

+ library (pdf 版 と html 版 のタイプセット)
  + pandoc
  + python3 (切り抜き, 貼り付け用)
  + LaTeX (platex, dvipdfmx, listings)
  + make (LaTeX のタイプセット用)

使い方
------
+ source
  + ここ直下にコード片が置いてあります。(XX_Include.java という形で)
  + `mvn compile` でちゃんとコンパイル出来るか確認できます。
  + `mvn test jacoco:report` で用意したテストのカバレッジが見れます。

+ verify
  + オンラインジャッジなどの問題でコピペで正解できるか確認する所です。
  + library の update-verify.sh をすると @paste が参照コードに置き換わります。
  + `mvn compile` をするとコンパイルに通るか確認できます。
  + 自動サブミット機能は無いので生成したコードを自分で投げる必要があります。 

+ library
  + ここにライブラリと verify 状況を記述しています。 
  + `.mdc` というのがコード部分の無い説明用 markdown です。
  + `make pdf` で pdf 形式でライブラリが出力されます。
  + `make html` で html が出力されます。 
  + `make html-sc` で self-contained な htnl が出力されます。 

注意
----
ダーティハックが多いので, あまり構成面は参考になりません.

+ 内部リンクは, LaTeX上では外部リンクにしてから内部リンクに差し替えてます.
+ jlisting に依存しているため, 別途インストールが必要です.
+ プログラムの張り付け方もいい加減です.
