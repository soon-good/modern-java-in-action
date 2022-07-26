# Intellij Error - IDEA Start Failed: Address already in use



첨고 : [IDEA Start Failed: Address already in use – IDEs Support (IntelliJ Platform) | JetBrains](https://intellij-support.jetbrains.com/hc/en-us/community/posts/360006880600-IDEA-Start-Failed-Address-already-in-use)

업데이트도 최신버전으로 해도 안됐었는데, 위 QnA 게시판 댓글의 가장 마지막 댓글대로 해보니 됐다. 다 해보고 마지막 댓글에서 되다니 ㅋㅋ .... 역시 마지막 댓글이 승자인가?<br>

아무튼 위와 같은 에러가 발생하면 아래처럼 윈도우 드라이버를 껐다가 다시키면됨. (윈도우에서만 나타나는 문제)

```plain
net stop winnat
net start winnat
```

<br>

오늘 새벽은 무사히 지나가나 했더니 이렇게 30분을 날렸다....ㅋㅋㅋ<br>

<br>

