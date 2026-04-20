#import "template.typ": assignment, solution

#show: assignment.with(
  assignment: "Homework 2",
)

= Make the First Prove

#solution[
  / 1.: $(p and q) -> r$ Given
  / 2.: $not r or s$ Given
  #pad(left: 2em)[
    / 3.1: $not s$ Assumption
    / 3.2: $s or not r$ Commutativity: 2
    / 3.3: $not r$ Elim $or$: 3.2, 3.1
    / 3.4: $not r -> not (p and q)$ Contrapositive: 1
    / 3.5: $not (p and q)$ Modus Ponens: 3.3, 3.4
    / 3.6: $not p or not q$ De Morgan's Law: 3.5
  ]
  / 3.: $not s -> (not p or not q)$ Direct Proof
]

#solution[
  / 1.: $forall x, (P(x) and Q(x)) -> R(x)$ Given
  / 2.: $forall x, P(x)$ Given
  #pad(left: 2em)[
    Let $a$ be arbitrary.
    #pad(left: 2em)[
      / 3.1.1: $Q(a)$ Assumption
      / 3.1.2: $P(a)$ Elim $forall$: 2
      / 3.1.3: $P(a) and Q(a)$ Intro $and$: 3.1.2, 3.1.1
      / 3.1.4: $(P(a) and Q(a)) -> R(a)$ Elim $forall$: 1
      / 3.1.5: $R(a)$ Modus Ponens: 3.1.3, 3.1.4
    ]
    / 3.1: $Q(a) -> R(a)$ Direct Proof
  ]
  / 3.: $forall x, Q(x) -> R(x)$ Intro $forall$
]

#pagebreak()

= Pack Up the Proving Van

#solution[
  / 1.: $p or not q$ Given
  / 2.: $p -> r$ Given
  / 3.: $q or s$ Given
  / 4.: $(r or s) -> (v and u)$ Given
  #pad(left: 2em)[
    / 5.1: $p$ Assumption
    / 5.2: $r$ Modus Ponens: 5.1, 2
    / 5.3: $r or s$ Intro $or$: 5.2
  ]
  / 5.: $p -> (r or s)$ Direct Proof
  #pad(left: 2em)[
    / 6.1: $not q$ Assumption
    / 6.2: $s$ Elim $or$: 3, 6.1
    / 6.3: $r or s$: Intro $or$: 6.2
  ]
  / 6.: $not q -> (r or s)$ Direct Proof
  / 7.: $r or s$ Cases: 1, 5, 6
  / 8.: $v and u$ Modus Ponens: 7, 4
  / 9.: $not r or (v and u)$ Intro $or$: 8
]

#solution[
  / 1.: $s -> (p or not q)$ Given
  / 2.: $(q and p) or (s and not p)$ Given
  / 3.: $q or p$ Given
  #pad(left: 2em)[
    / 4.1: $s and not p$ Assumption
    / 4.2: $s$ Elim $and$: 4.1
    / 4.3: $not p$ Elim $and$: 4.1
    / 4.4: $p or not q$ Modus Ponens: 4.2, 1
    / 4.5: $not q$: Elim $or$: 4.4, 4.3
    / 4.6: $p$ Elim $or$: 3, 4.5
    / 4.7: $F$ Principium Contradictionis: 4.6, 4.3
  ]
  / 4.: $not (s and not p)$ Reductio Ad Absurdum
  #pad(left: 2em)[
    / 5.1: $q and p$ Assumption
  ]
  / 5.: $(q and p) -> (q and p)$ Direct Proof
  #pad(left: 2em)[
    / 6.1: $s and not p$ Assumption
    / 6.2: $F$ Principium Contradictionis: 6.1, 4
    / 6.3: $q and p$ Ex Falso Quodlibet: 6.2
  ]
  / 6.: $(s and not p) -> (q and p)$ Direct Proof
  / 7.: $q and p$ Cases: 2, 5, 6
  / 8.: $q$ Elim $and$: 7
]

= Div and Let Div

#solution[
  #pad(left: 2em)[
    #pad(left: 2em)[
      #pad(left: 2em)[
        Let $x$, $y$, and $z$ be arbitrary integers.
        #pad(left: 2em)[
          / 1.1.1.1.1.: $(x | y) and (x | z)$ Assumption
          / 1.1.1.1.2.: $(x | y)$ Elim $and$: 1.1.1.1.1
          / 1.1.1.1.3.: $(x | z)$ Elim $and$: 1.1.1.1.1
          / 1.1.1.1.4.: $exists q_y (y = q_y x)$ Def Divides: 1.1.1.1.2
          / 1.1.1.1.5.: $exists q_z (z = q_z x)$ Def Divides: 1.1.1.1.3
          / 1.1.1.1.6.: $y = n_y x$ Elim $exists$: 1.1.1.1.4
          / 1.1.1.1.7.: $z = n_z x$ Elim $exists$: 1.1.1.1.5
          / 1.1.1.1.8.: $y + z = n_y x + n_z x$ Algebra
          / 1.1.1.1.9.: $y + z = (n_y + n_z) x$ Algebra
          / 1.1.1.1.10.: $exists n (y + z = n x)$ Intro $exists$: 1.1.1.1.9
          / 1.1.1.1.11.: $x | (y + z)$ Undef Divides: 1.1.1.1.10
        ]
        / 1.1.1.1.: $((x | y) and (x | z) -> (x | (y + z)))$ Direct Proof
      ]
      / 1.1.1.: $forall c ((x | y) and (x | c) -> (x | (y + c)))$ Intro $forall$
    ]
    / 1.1.: $forall b forall c ((x | b) and (x | c) -> (x | (b + c)))$ Intro $forall$
  ]
  / 1.: $forall a forall b forall c ((a | b) and (a | c) -> (a | (b + c)))$ Intro $forall$
]

#solution[
  Let $x$, $y$, and $z$ be arbitrary integers. Suppose $x | y$ and $x | z$. By the definition of divides, know $y = n_y x$ and $z = n_z x$ for some integers $n_y$ and $n_z$. Next, $y + z = n_y x + n_z x = (n_y + n_z) x$. Since addition is closed under the integers, and there is an integer $n$ such that $y + z = n x$, we know that $x | (y + z)$ by the definition of divides. Therefore, since $x$, $y$, and $z$ were arbitrary, we can conclude that the claim holds.
]

#pagebreak()

= Optional Practice Problems

#solution[
  / 1.: $p or q$ Given
  / 2.: $r and not p$ Given
  / 3.: $not (q and not s)$ Given
  / 4.: $r$ Elim $and$: 2
  / 5.: $not p$ Elim $and$: 2
  / 6.: $q$ Elim $or$: 1, 5
  / 7.: $not not q$ Double Negation: 6
  / 8.: $not q or not not s$ De Morgan's Law: 3
  / 9.: $not not s$: Elim $or$: 8
  / 10.: $s$: Double Negation 9
  / 10.: $s and r$ Intro $and$: 10, 4
]

#solution[
  / 1.: $exists x P(x)$ Given
  / 2.: $forall x R(x, c)$ Given
  / 3.: $$ Given
]

#solution[
  / i.: Let $x$ and $y$ be arbitrary integers. Suppose $x$ and $y$ are both odd. By the definition of odd, $x = 2n + 1$ and $y = 2m + 1$ for some integers $n$ and $m$. Then, $x dot y = (2n + 1)(2m + 1) = 4 n m + 2n + 2m + 1 = 2(2 n m + n + m) + 1$. Since addition is closed under the integers, and there is an integer $z$ such that $x dot y = 2z + 1$, $x dot y$ is odd by the definition of odd. Therefore, since $x$ and $y$ were arbitrary, we can conclude that the claim holds.
]
