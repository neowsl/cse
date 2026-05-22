#import "template.typ": assignment, solution
#import "@preview/finite:0.5.1": automaton

#show: assignment.with(
  assignment: "Homework 5",
)

#let powerset = $cal(P)$

= Relatin' Canes

#solution[
  Define $R subset ZZ times ZZ$ by $(a, b) in R$ iff $a + 2b$ is odd.

  We know $a$ must be odd, since $2b$ is even. Therefore, $R$ is:

  - #strike[Reflexive]: $(2, 2) in.not R$
  - #strike[Symmetric]: $(1, 2) in R$ but $(2, 1) in.not R$
  - #strike[Antisymmetric]: $(1, 3) in R$ and $(3, 1) in R$
  - *Transitive*
]

#solution[
  Define $S subset NN times NN$ by $(a, b) in S$ iff $a dot b >= 0$.

  $S$ is:

  - *Reflexive*
  - *Symmetric*
  - #strike[Antisymmetric]: $(1, 2) in S$ and $(2, 1) in S$
  - *Transitive*
]

#solution[
  Let $A = {k in ZZ : k < 0}$ be the set of negative integers. Define $T subset A times A$ by $(a, b) in T$ iff $a times b < 0$.

  $T$ is:

  - #strike[Reflexive]: $(-1, -1) in.not T$
  - *Symmetric*
  - *Antisymmetric*
  - *Transitive*
]

Continued on next page $->$

#pagebreak()

#solution[
  let $B = powerset(ZZ)$. Define $U subset B times B$ by $(X, Y) in U$ iff $X union NN subset Y inter NN$.

  For $X union NN subset Y inter NN$ to be true:
  - $NN subset Y$
  - $X subset NN$

  Therefore, $U$ is:

  - #strike[Reflexive]: Suppose $(X, Y) = ({1}, {1})$. Then $X union NN = NN$ and $Y inter NN = {1}$. Since $NN subset.not {1}$, $({1}, {1}) in.not U$.
  - #strike[Symmetric]: $({1}, NN) in U$ but $(NN, {1}) in.not U$
  - *Antisymmetric*
  - *Transitive*
]

= Better Get Proving

#solution[
  Let $a$ and $b$ be arbitrary elements.

  Suppose $(a, b) in R compose R$ and $(b, a) in R compose R$.

  By the definition of compose, there is some element $c$ such that $(a, c) in R$ and $(c, b) in R$. Also for the latter, by the definition of compose, there is some element $d$ such that $(b, d) in R$ and $(d, a) in R$.

  We're given that $R$ is transitive. Since $(a, c) in R$ and $(c, b) in R$, then by the definition of transitivity, $(a, b) in R$. Furthermore, since $(b, d) in R$ and $(d, a) in R$, then by the definition of transitivity, $(b, a) in R$.

  We're also given that $R$ is antisymmetric. Since $(a, b) in R$ and $(b, a) in R$, then by the definition of antisymmetric, $a = b$.

  Therefore, since $a$ and $b$ were arbitrary, we have proven, by the definition of antisymmetric, that $R compose R$ is antisymmetric.
]

#pagebreak()

= Don't Stop BelEven'

#solution[
  #automaton(
    (
      s0: (s1: 0, s2: 1),
      s1: (s0: 0, s3: 1),
      s2: (s3: 0, s0: 1),
      s3: (s2: 0, s1: 0),
    ),
    initial: "s0",
    final: "s0",
  )

  - $s_0$: Even number of 0's and even number of 1's
  - $s_1$: Odd number of 0's and even number of 1's
  - $s_2$: Even number of 0's and odd number of 1's
  - $s_3$: Odd number of 0's and odd number of 1's
]

#solution[
  $(00 union 11 union ((01 union 10) (00 union 11)^* (01 union 10)))^*$
]

#solution[
  $S$ is a CFG that generates $M$:

  $S -> S S | 0 T 1 | 1 T 0 | epsilon$

  $T -> S T | T S | 0 S 1 | 1 S 0$

  - $S$ generates strings with the same number of 0's and 1's and an even number of 0's and 1's.
  - $T$ generates strings with the same number of 0's and 1's and an odd number of 0's and 1's.
]
