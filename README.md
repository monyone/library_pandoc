底辺ライブラリ(ビルド)
==============================
[![Build Status](https://travis-ci.org/monyone/library_pandoc.svg?branch=master)](https://travis-ci.org/monyone/library_pandoc)

これは何?
---------

競技プログラミングで使っているコード(Java)のビルド/テスト/タイプセット環境.

どんな底辺でも使えて, CI出来るライブラリを目指しています.

依存ソフトウェア
----------------

+ pandoc
+ python3
+ LaTeX (platex, dvipdfmx, listings)
+ make (LaTeX のタイプセット用)
+ maven (ソースコードの一括テスト用)

注意
----
ダーティハックが多いので, あまり構成面は参考になりません.

+ 内部リンクは, LaTeX上では外部リンクにしてから内部リンクに差し替えてます.
+ jlisting に依存しているため, 別途インストールが必要です.
+ プログラムの張り付け方もいい加減です.
