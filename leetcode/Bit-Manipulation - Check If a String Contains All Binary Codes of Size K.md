# Check If a String Contains All Binary Codes of Size K

문제 링크 : [Check If a String Contains All Binary codes of Size K](https://leetcode.com/problems/check-if-a-string-contains-all-binary-codes-of-size-k/)<br>

Given a binary string `s` and an integer `k`, return `true` *if every binary code of length* `k` *is a substring of* `s`. Otherwise, return `false`.<br>

**Example 1:**

```
Input: s = "00110110", k = 2
Output: true
Explanation: The binary codes of length 2 are "00", "01", "10" and "11". They can be all found as substrings at indices 0, 1, 3 and 2 respectively.
```

**Example 2:**

```
Input: s = "0110", k = 1
Output: true
Explanation: The binary codes of length 1 are "0" and "1", it is clear that both exist as a substring. 
```

**Example 3:**

```
Input: s = "0110", k = 2
Output: false
Explanation: The binary code "00" is of length 2 and does not exist in the array.
```

<br>

# 문제풀이 아이디어

세자리의 이진수 중 `100` 을 만들려면?

- `1<<2` : 
  - "**1을 두자리수 만큼 이동시키겠다**"
  - 1을 두자리 시프트한다. 
  - 100(2) 가 되는데, 십진수로는 4가 된다.
  

<br>

두자리 기반의 이진수의 최댓값을 구하려면?

- `1<<2` -1 :
  - 두자리수 이진수의 최댓값은 3 이다.(11(2))
  - 그리고 3의 다음값은 100(2) 이다. 100(2) 는 `1<<2` 로 표현가능하다.
  - 100(2) - 1(2) = 십진수표현) 4 -1 = 3
  - 그리고 두자리수 내에서 모든 비트가 1로 세팅된 값이다.
  - 나는 이 비트표현식을 보통 변수명으로 `TRUE_MASK` 라고 부르기로 했다.

<br>

비트를 이동시킨 후에 1인 자릿수들만을 변수에 기억하려면?

- int shift\_and\_fiter = (movingBits << 1) & TRUE_MASK

- 변수 TRUE_MASK 는 위에서 구한 `1<<2 -1` 값이다.

<br>

문자열에서 1인 자릿수가 있을 때만 시프트 연산을 통해서 경우의 수를 카운팅하려면?

```java
for(int i=0; i<s.length(); i++){
    int shift_and_filter = (movingBits<<1)&TRUE_MASK;
    int kth_digit = s.charAt(i) - '0';
    movingBits = shift_and_filter | kth_digit;
    // ...
}
```

<br>

설명이 거지같긴한데, 일단 문제풀이 아이디어는 여기에서부터 출발한다.

아래는 문제풀이를 위해 연습해본 코드다.

```java
// 문제풀이 컨셉 연습
public static void TEST(){
    String s = "00110110";
    int k = 2;
    int movingBits = 0;

    System.out.println("s = " + s + ", k = " + k + ", movingBits = " + movingBits);

    System.out.println();

    int MAX_ANS = 1<<k; // 1<<k => 100(2), 10진수:4
    System.out.println("MAX_ANS = " + MAX_ANS + ", binaryString = " + Integer.toBinaryString(MAX_ANS));

    System.out.println();
    int TRUE_MASK = MAX_ANS-1;
    System.out.println("TRUE_MASK = " + TRUE_MASK + ", binaryString = " + Integer.toBinaryString(TRUE_MASK));

    System.out.println();
    System.out.println("[(0<<1)] movingBits << 1  --> " + (movingBits<<1) + " , binaryString = " + Integer.toBinaryString((movingBits<<1)));		
    System.out.println();


    StringBuilder b1 = new StringBuilder();
    for(int i=0; i<s.length(); i++){
        // b1.append((s.charAt(i) - '0'))
        //   .append(",");
        int SHIFT_AND_MASKED = ((movingBits <<1) & TRUE_MASK);
        int charAt = s.charAt(i) - '0';
        movingBits = SHIFT_AND_MASKED | charAt;
        b1
            .append("charAt = ").append(charAt)
            .append(", SHIFT_AND_MASKED >> ").append(Integer.toBinaryString(SHIFT_AND_MASKED))
            .append(", movingBits = ").append(Integer.toBinaryString(movingBits))
            .append("\n");
    }
    System.out.println(b1.toString());
}
```

<br>

위코드의 출력결과다. 나중에 내가 다시 보고 눈풀로만 이해가 가야하기에 출력결과를...

```plain
s = 00110110, k = 2, movingBits = 0

MAX_ANS = 4, binaryString = 100

TRUE_MASK = 3, binaryString = 11

[(0<<1)] movingBits << 1  --> 0 , binaryString = 0

charAt = 0, SHIFT_AND_MASKED >> 0, movingBits = 0
charAt = 0, SHIFT_AND_MASKED >> 0, movingBits = 0
charAt = 1, SHIFT_AND_MASKED >> 0, movingBits = 1
charAt = 1, SHIFT_AND_MASKED >> 10, movingBits = 11
charAt = 0, SHIFT_AND_MASKED >> 10, movingBits = 10
charAt = 1, SHIFT_AND_MASKED >> 0, movingBits = 1
charAt = 1, SHIFT_AND_MASKED >> 10, movingBits = 11
charAt = 0, SHIFT_AND_MASKED >> 10, movingBits = 10
```

<br>

# 문풀

아래와 같이 풀었었다. 설명을 못하겠다. 계속 설명을 추가해나가야겠다.<br>

이 정도로 머리아프면 안되는데, 비트 연산은 오늘 처음 접한 신세계였다.<br>

앞으로도 많은 종류의 예제를 접해보고 쫄지말아야 한다.<br>

<br>

```java
public boolean sol2(String s, int k){
    int MAX_ANS     = 1<<k;
    int TRUE_MASK   = MAX_ANS-1;
    int movingBits  = 0;

    boolean [] checked = new boolean [MAX_ANS];

    for(int i=0; i<s.length(); i++){
        int shift_and_filter = (movingBits<<1) & TRUE_MASK;
        int kth_digit = s.charAt(i) - '0';

        movingBits = shift_and_filter | kth_digit;

        if(i>=k-1 && checked[movingBits] == false){
            checked[movingBits] = true;
            MAX_ANS--;
            if(MAX_ANS == 0) return true;
        }
    }
    return false;
}
```

