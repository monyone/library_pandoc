底辺ライブラリ(ビルド)
==============================
source: [![Build Status](https://travis-ci.org/monyone/library_pandoc.svg?branch=master)](https://travis-ci.org/monyone/library_pandoc)

これは何?
---------

競技プログラミングで使っているコード(Java)のビルド/テスト/タイプセット環境.

どんな底辺でも使えて, CI出来るライブラリを目指しています.

依存ソフトウェア
----------------
+ source
  + Java (OracleJDK 8)
  + maven2 (ビルド, テスト)

+ verify
  + Java (OracleJDK 8)
  + maven2 (ビルド)
  + python3 (貼り付け)

+ library
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
