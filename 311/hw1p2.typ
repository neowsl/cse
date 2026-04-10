#import "template.typ": assignment, solution

#show: assignment.with(
  assignment: "HW 1 Part 2",
)

= With a Fine-Truth Comb

#solution[
  #table(
    columns: (auto, auto, auto, auto, auto, auto),
    align: center,
    table.header(
      $P$, $Q$, $P <-> Q$, $not P$, $P and Q$, $not P <-> (P and Q)$
    ),
    [F], [F], [T], [T], [F], [F],
    [F], [T], [F], [T], [F], [F],
    [T], [F], [F], [F], [F], [T],
    [T], [T], [T], [F], [T], [F],
  )

  $P <-> Q equiv.not not P <-> (P and Q)$ because the results of the expressions differ in rows 1, 2, and 4.
]

#solution[
  #table(
    columns: (auto, auto, auto, auto, auto, auto, auto, auto),
    align: center,
    table.header(
      $P$,
      $Q$,
      $R$,
      $P <-> Q$,
      $(P <-> Q) -> R$,
      $P or R$,
      $Q -> R$,
      $(P or R) and (Q -> R)$,
    ),
    [F], [F], [F], [T], [F], [F], [T], [F],
    [F], [F], [T], [T], [T], [T], [T], [T],
    [F], [T], [F], [F], [T], [F], [F], [F],
    [F], [T], [T], [F], [T], [T], [T], [T],
    [T], [F], [F], [F], [T], [T], [T], [T],
    [T], [F], [T], [F], [T], [T], [T], [T],
    [T], [T], [F], [T], [F], [T], [F], [F],
    [T], [T], [T], [T], [T], [T], [T], [T],
  )

  $(P <-> Q) -> R equiv.not (P or R) and (Q -> R)$ because the results of the expressions differ in row 3.
]

#pagebreak()

= Too Cool For Rule

#solution[
  $(not P or Q) and (not P and Q) & equiv ((not P or Q) and not P) and Q && "Associativity" \
  & equiv (not P and (not P or Q)) and Q && "Commutativity" \
  & equiv not P and Q && "Absorption"$
]

#solution[
  $(P -> not Q) and (not P -> not Q) & equiv (P -> not Q) and (Q -> P) && "Contrapositive" \
  & equiv (not P or not Q) and (Q -> P) && "Law of Implication" \
  & equiv (not P or not Q) and (not Q or P) && "Law of Implication" \
  & equiv (not Q or not P) and (not Q or P) && "Commutativity" \
  & equiv not Q or (not P and P) && "Distributivity" \
  & equiv not Q or (P and not P) && "Commutativity" \
  & equiv not Q or F && "Law of Non-Contradiction" \
  & equiv not Q && "Domination"$
]

#solution[
  $(P or Q -> R) -> Q & equiv (not (P or Q) or R) -> Q && "Law of Implication" \
  & equiv not (not (P or Q) or R) or Q && "Law of Implication" \
  & equiv (not not (P or Q) and not R) or Q && "De Morgan's Law" \
  & equiv ((P or Q) and not R) or Q && "Double Negation" \
  & equiv Q or ((P or Q) and not R) && "Commutativity" \
  & equiv (Q or P or Q) and (Q or not R) && "Distributivity" \
  & equiv (P or Q or Q) and (Q or not R) && "Commutativity" \
  & equiv (P or Q) and (Q or not R) && "Idempotency" \
  & equiv (P or Q) and (not R or Q) && "Commutativity" \
  & equiv (not not P or Q) and (not R or Q) && "Double Negation" \
  & equiv (not P -> Q) and (not R or Q) && "Law of Implication" \
  & equiv (not P -> Q) and (R -> Q) && "Law of Implication"$
]

#pagebreak()

= Truth or Feb?

#solution[
  #set enum(numbering: "i.")

  + Every month with an even number of days has exactly 30 days.

    This is false because February can have 28 days.

  + Every month with an even number of days, excluding February, has exactly 30 days.

    This is true because it handles the case of February.

  + If every month has an even number of days and isn't February, then every month also has exactly thirty days.

    This is true because the premise is false - February exists.
]

#solution[
  #set enum(numbering: "i.")
]

#solution[
  #set enum(numbering: "i.")

  + $not exists x ("HasThirtyDays"(x) and "IsFebruary"(x))$
  + $exists x (not "HasEvenDays"(x) and not "IsFebruary"(x))$
  + $"HasEvenDays"(x) and not "HasThirtyDays"(x) -> "IsFebruary"(x)$
]
