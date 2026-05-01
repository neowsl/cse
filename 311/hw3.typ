#import "template.typ": assignment, solution

#show: assignment.with(
  assignment: "Homework 3",
)

= Euclidean, My Dear Watson

#solution[
  #set enum(numbering: "1.")

  1. Equation already in standard form.
  2. We start by finding $gcd(38, 7)$ (tableau on the right):
  $
    gcd(38, 7) & = gcd(7, 38 mod 7) & = gcd(7, 3) & | 38 = 5 dot 7 + 3 \
               & = gcd(3, 7 mod 3)  & = gcd(3, 1) & | 7 = 2 dot 3 + 1 \
               & = gcd(1, 3 mod 1)  & = gcd(1, 0) & | \
  $
  Next, solve the equations for $r$:
  $
    3 & = 38 - 5 dot 7 \
    1 & = 7 - 2 dot 3 \
  $
  Then, we substitute backwards:
  $
    1 & = 7 - 2 dot 3 \
      & = 7 - 2 dot (38 - 5 dot 7) \
      & = -2 dot 38 + 11 dot 7 \
  $
  Thus 11 is the multiplicative inverse of 7 modulo 38.

  Finally multiplying both sides by 11,
  $
    x equiv_38 11 dot 7x equiv_38 11 dot 5 equiv_38 17
  $
  3. $x = 17 + 38k$ for any integer $k$.
  4. The original modular equation was already in standard form.
  5. If we let $k = 1000$, then we get a solution $x = 17 + 38 dot 1000 = 3817$, which is $>= 1000$.
]

#solution[
  #set enum(numbering: "1.")

  1.
  $
    62x - 6 equiv_50 4 - 25x
  $
  Add 6 to both sides:
  $
    62x equiv_50 10 - 25x
  $
  Continued on next page $->$
  #colbreak()
  Add $25x$ to both sides:
  $
    87x equiv_50 10
  $
  2. We start by finding $gcd(87, 50)$ (tableau on the right):
  $
    gcd(87, 50) & = gcd(50, 87 mod 50) & = gcd(50, 37) & | 87 = 1 dot 50 + 37 \
                & = gcd(37, 50 mod 37) & = gcd(37, 13) & | 50 = 1 dot 37 + 13 \
                & = gcd(13, 37 mod 13) & = gcd(13, 11) & | 37 = 2 dot 13 + 11 \
                & = gcd(11, 13 mod 11) &  = gcd(11, 2) & | 13 = 1 dot 11 + 2 \
                & = gcd(2, 11 mod 2)   &   = gcd(2, 1) & | 11 = 5 dot 2 + 1 \
                & = gcd(1, 2 mod 1)    &   = gcd(1, 0) & | \
  $
  Next, we solve the equations for $r$:
  $
    37 & = 87 - 1 dot 50 \
    13 & = 50 - 1 dot 37 \
    11 & = 37 - 2 dot 13 \
     2 & = 13 - 1 dot 11 \
     1 & = 11 - 5 dot 2 \
  $
  Then, we substitute backwards:
  $
    1 & = 11 - 5 dot 2 \
      & = 11 - 5 dot (13 - 1 dot 11) \
      & = -5 dot 13 + 6 dot 11 \
      & = -5 dot 13 + 6 dot (37 - 2 dot 13) \
      & = 6 dot 37 - 17 dot 13 \
      & = 6 dot 37 - 17 dot (50 - 1 dot 37) \
      & = -17 dot 50 + 23 dot 37 \
      & = -17 dot 50 + 23 dot (87 - 1 dot 50) \
      & = 23 dot 87 - 40 dot 50 \
  $
  Thus 23 is the multiplicative inverse of 87 modulo 50.

  Finally, multiplying both sides by 23,
  $
    x equiv_50 23 dot 87x equiv_50 23 dot 10 equiv_50 30
  $
  3. $x = 30 + 50k$ for any integer $k$.
  4. All operations applied were reversible, so the solutions are the same.
  5. If we let $k = 1000$, then we get a solution $x = 30 + 50 dot 1000 = 50030$, which is $>= 1000$.
]

#pagebreak()

= Sum Kind of Wonderful

#solution[
  Let $P(n)$ be "$sum_(i = 0)^n (5 dot 6^i + 3) = 6^(n + 1) + 3n + 2$". We will show $P(n)$ is true for all non-negative integers by induction.

  Base Case: $sum_(i = 0)^0 (5 dot 6^i + 3) = 5 dot 6^0 + 3 = 8 = 6^(0 + 1) + 3(0) + 2$, so $P(0)$ is true.

  Suppose that $P(k)$ is true for some arbitrary integer $k >= 0$. We can see that
  $
    sum_(i = 0)^(k + 1) (5 dot 6^i + 3) & = sum_(i = 0)^k (5 dot 6^i + 3) + (5 dot 6^(k + 1) + 3) \
    & = 6^(k + 1) + 3k + 2 + 5 dot 6^(k + 1) + 3 & "    by IH" \
    & = 6 dot 6^(k + 1) + 3k + 5 \
    & = 6^((k + 1) + 1) + 3(k + 1) + 2
  $
  Therefore, $P(k + 1)$ is true.

  Thus, $P(n)$ is true for all non-negative integers by induction.
]

#solution[
  Let $P(n)$ be "$3^n >= 2n + 1$". We will show $P(n)$ is true for all integers $n >= 1$ by induction.

  Base Case: $3^1 = 3 >= 2(1) + 1$, so $P(1)$ is true.

  Suppose that $P(k)$ is true for some arbitrary integer $k >= 1$. We can see that
  $
    3^(k + 1) & = 3^k dot 3 \
              & >= (2k + 1) dot 3 & "   by IH" \
              & = 6k + 3 \
              & >= 2k + 2 + 1 \
              & = 2(k + 1) + 1
  $
  Therefore, $P(k + 1)$ is true.

  Thus, $P(n)$ is true for all integers $n >= 1$ by induction.
]

#pagebreak()

= Barking Up the Strong Tree

#solution[
  Let $P(n)$ be "$f(n) = b^n$". We will show $P(n)$ is true for all non-negative integers by strong induction.

  Base Cases:
  - $f(0) = 1 = b^0$, so $P(0)$ is true.
  - $f(1) = b = b^1$, so $P(1)$ is true.

  Suppose that $P(0) and ... and P(k)$ is true for some arbitrary integer $k >= 0$. We can see tat
  $
    f(k + 1) & = (b - 1) dot f((k + 1) - 1) + b dot f((k + 1) - 2) & "   def of f" \
             & = (b - 1) dot b^k + b dot f(k - 1)                  &   "    by IH" \
             & = (b - 1) dot b^k + b dot b^(k - 1)                 &   "    by IH" \
             & = b dot b^k - b^k + b^k \
             & = b dot b^k \
             & = b^(k + 1) \
  $
  Therefore, $P(k + 1)$ is true.

  Thus, $P(n)$ is true for all non-negative integers by strong induction.
]

#pagebreak()

= Optional Practice Problems

#solution[
  (Did work on scratch paper)

  / i.: 15 is the multiplicative inverse of 15 modulo 28.

    Multiplying both sides by 15,
    $
      x equiv_28 15 dot 15x equiv_28 15 dot 14 equiv_28 14
    $

  / ii.: Standard form: $12x equiv_7 3$

    3 is the multiplicative inverse of 12 modulo 7.

    Multiplying both sides by 3,
    $
      x equiv_7 3 dot 12x equiv_7 3 dot 3 equiv_7 2
    $
]

#solution[
  / i.: Let $a$ and $b$ be arbitrary integers, and $m$ and $n$ be arbitrary positive integers.

    Suppose $gcd(m, n) = 1$.

    By applying Bezout's Theorem, we get $gcd(m, n) = s m + t n$, for some integers $s$ and $t$. Then, substituting $gcd(m, n)$, we get $s m + t n = 1$.

    Notice that we can rearrange the equation to $s m - 1 = -t n$. Since there is an integer $q = -t$ such that $s m - 1 = q n$, by the definition of divides, $n | (s m - 1)$. Then, by the definition of modulo, $s m equiv_n 1$. If we multiply both sides by $b$, we get $b s m equiv_n b$.

    Similarly, we can rearrange the equation to $t n - 1 = -s m$. Since there is an integer $q = -s$ such that $t n - 1 = q m$, by the definition of divides, $m | (t n - 1)$. Then, by the definition of modulo, $t n equiv_m 1$. If we multiply both sides by $a$, we get $a t n equiv_m a$.

    Now let $x = b s m + a t n$. Under modulo $m$, the $b s m$ term becomes 0, so $x equiv_m a t n equiv_m a$. Similarly, under modulo $n$, the $a t n$ term becomes 0, so $x equiv_m b s m equiv_m b$

    Since we have found an integer $x$ that satisfies both $x equiv_m a$ and $x equiv_n b$, and $a$, $b$, $m$, and $n$ were arbitrary, we have proven the claim.
]

Continued on next page $->$

#pagebreak()

#solution[
  / i.: Let $P(n)$ be "$6 | (7^n - 1)$". We will show $P(n)$ is true for all integers $n >= 1$ by induction.

    Base Case: By the definition of divides, for $P(1)$ to be true, there must be an integer $q$ such that $7^1 - 1 = q dot 6$. $7^1 - 1 = 6 = 1 dot 6$, so $P(1)$ is true.

    Suppose that $P(k)$ is true for some arbitrary integer $k >= 1$. We see that
    $
                              6 | (7^k - 1) & "    by IH" \
                          7^k - 1 = q dot 6 & "    def of divides" \
            7 dot (7^k - 1) = 7 dot q dot 6 & "    multiply by 7" \
              7 dot 7^k - 7 = 7 dot q dot 6 & "    " \
              7^(k + 1) = 7 + 7 dot q dot 6 & "    " \
      7^(k + 1) - 1 = 7 - 1 + 7 dot q dot 6 & "    subtract 1" \
          7^(k + 1) - 1 = 6 + 7 dot q dot 6 & "    " \
             7^(k + 1) - 1 = (7q + 1) dot 6 & "    " \
                        6 | (7^(k + 1) - 1) & "    def of divides" \
    $

    Therefore, $P(k + 1)$ is true.

    Thus, $P(n)$ is true for all positive integers ($n >= 1$) by induction.

  / ii.: Let $P(n)$ be \"$"Even"(n) or "Odd"(n)$\". We will show $P(n)$ is true for all positive integers by induction.

    Base Case:
    - By the definition of Even, an integer $n$ is even if $n = 2k$ for some integer $k$. Since there is no integer $k$ such that $1 = 2k$, $"Even"(1)$ is false.
    - By the definition of Odd, an integer $n$ is odd if $n = 2k + 1$ for some integer $k$. Since $1 = 2(0) + 1$, $"Odd"(1)$ is true.
    - Therefore, $"Even"(1) or "Odd"(1)$ is true, so $P(1)$ is true.

    Suppose that $P(k)$ is true for some arbitrary integer $k >= 1$. Then $k$ is Even or Odd by the definition of $P$. We proceed by cases...

    If $k$ is Even, then by the definition of Even, there is some integer $s$ for which $k = 2s$. Then $k + 1 = 2s + 1$, so by the definition of Odd, $k + 1$ is Odd. Then the statement $"Even"(k + 1) or "Odd"(k + 1)$ is also true.

    If $k$ is Odd, then by the definition of Odd, there is some integer $s$ for which $k = 2s + 1$. Then $k + 1 = 2s + 2 = 2(s + 1)$, so by the definition of Even, $k + 1$ is Even. Then the statement $"Even"(k + 1) or "Odd"(k + 1)$ is also true.

    Since our cases were exhaustive, we have shown that $"Even"(k + 1) or "Odd"(k + 1)$ is true.

    Therefore, $P(k + 1)$ is true.

    Thus, $P(n)$ is true for all positive integers ($n >= 1$) by induction.
]
