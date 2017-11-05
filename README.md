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
(なので teihen_library の方はもう gh-pages 以外は更新しません。)

PDF版について
------------
どうやって配布などをしたらいいんだろうか?

依存ソフトウェア
----------------
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

注意
----
ダーティハックが多いので, あまり構成面は参考になりません.

+ 内部リンクは, LaTeX上では外部リンクにしてから内部リンクに差し替えてます.
+ jlisting に依存しているため, 別途インストールが必要です.
+ プログラムの張り付け方もいい加減です.
