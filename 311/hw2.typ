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
