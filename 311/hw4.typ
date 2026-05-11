#import "template.typ": assignment, solution

#show: assignment.with(
  assignment: "Homework 4",
)

#let powerset = $cal(P)$

= Keeping Up With the Cartesians

#solution[
  $B times A = {(1, 1), (2, 1)}$

  $C times B = {(1, 1), (1, 2), (2, 1), (2, 2), (3, 1), (3, 2)}$

  The claim holds.
]

#solution[
  $B times A = {(1, 1), (1, 2)}$

  $C times B = {(1, 1), (2, 1), (3, 1)}$

  The claim does not hold. ${1, 2}$ is an element of $B times A$ but is not an element of $C times B$.
]

#solution[
  Let $(b, a)$ be an arbitrary tuple of (integer, integer).

  Suppose that $(b, a) in (B times A)$. By the definition of Cartesian product, $b in B$ and $a in A$.

  We are given that $A subset B$. Since $a in A$, by the definition of subset, $a in B$.

  We are also given that $B subset C$. Since $b in B$, by the definition of subset, $b in C$.

  Since $a in B$ and $b in C$, by the definition of Cartesian product, $(b, a) in (C times B)$.

  Since $(b, a)$ was arbitrary, we have proven, by definition of subset, that $(B times A) subset (C times B)$.
]

#pagebreak()

= Our Finest Power

#solution[
  $powerset(A inter (B union C)) = powerset({1, 2}) = {emptyset, {1}, {2}, {1, 2}}$

  $powerset(A inter B) union powerset(A inter C) = powerset({1}) union powerset({2}) = {emptyset, {1}} union {emptyset, {2}} = {emptyset, {1}, {2}}$

  The claim does not hold. ${1, 2}$ is an element of $powerset(A inter (B union C))$ but is not an element of $powerset(A inter B) union powerset(A inter C)$.
]

#solution[
  $powerset(A inter (B union C)) = powerset({1}) = {emptyset, {1}}$

  $powerset(A inter B) union powerset(A inter C) = powerset({1}) union powerset({1}) = {emptyset, {1}} union {emptyset, {1}} = {emptyset, {1}}$

  The claim holds.
]

#solution[
  Let $X$ be an arbitrary set.

  Suppose $X in powerset(A inter (B union C))$. By applying the Distributivity of Set Theorem, we get $X in powerset((A inter B) union (A inter C))$.

  By the definition of power set, $X subset ((A inter B) union (A inter C))$.

  Let $x$ be an arbitrary element of $X$. Since $X subset ((A inter B) union (A inter C))$, then $x in ((A inter B) union (A inter C))$.

  By the definition of union, $x in (A inter B) or x in (A inter C)$. We continue by cases:

  Suppose $x in (A inter B)$. Since we are given $(A inter B) subset (A inter C)$, by the definition of subset, $x in (A inter C)$.

  Suppose $x in (A inter C)$. Then $x in (A inter C)$ is already true.

  Since our cases were exhaustive, $x in (A inter C)$. Then, since $x$ was an arbitrary element of $X$, by the definition of subset, $X subset (A inter C)$.

  By the definition of power set, since $X subset (A inter C)$, then $X in powerset(A inter C)$.

  By the definition of union, since $X in powerset(A inter C)$, then $X in powerset(A inter B) union powerset(A inter C)$.

  Since $X$ was arbitrary, we have proven, by the definition of subset, that $powerset(A inter (B union C)) subset powerset(A inter B) union powerset(A inter C)$.
]

#pagebreak()

= Parmesan, Romano, and Meta

#solution[
  The claim is true.

  Let $x$ be an arbitrary element.

  The stated biconditional holds since

  $
    x in ((A inter B) union (A inter overline(B))) & equiv x in (A inter B) or x in (A inter overline(B)) & "  Def of " union \
    & equiv (x in A and x in B) or (x in A and x in overline(B)) & "  Def of " inter \
    & equiv x in A and (x in B or x in overline(B)) & "  Distributivity" \
    & equiv x in A and T & "  Excluded Middle" \
    & equiv x in A & "  Identity"
  $

  Since $x$ was arbitrary, we have proven, by the definition of set equality, that $(A inter B) union (A inter overline(B)) = A$.
]

#solution[
  The claim is false.

  Suppose $A = emptyset$, $B = {1}$, and $C = emptyset$.

  $(A union B) \\ C = {1} \\ emptyset = {1}$

  $(A union C) \\ B = emptyset \\ {1} = emptyset$

  Since a counterexample exists, the claim is false.
]

#solution[
  The claim is true.

  Let $x$ be an arbitrary element.

  The stated biconditional holds since

  $
    x in (A \\ (B inter C)) & equiv x in A and not(x in (B inter C)) & "  Def of set difference" \
    & equiv x in A and not(x in B and x in C) & "  Def of " inter \
    & equiv x in A and (x in.not B or x in.not C) & "  DeMorgan's Law" \
    & equiv (x in A and x in.not B) or (x in A and x in.not C) & "  Distributivity" \
    & equiv x in (A \\ B) or x in (A \\ C) & "  Def of set difference" \
    & equiv x in ((A \\ B) union (A \\ C)) & "  Def of " union
  $

  Since $x$ was arbitrary, we have proven, by the definition of set equality, that $(A \\ (B inter C)) = (A \\ B) union (A \\ C)$.
]
