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
  Add $25x$ to both sides:
  $
    87x equiv_50 10
  $
  Continued on next page $->$
  #colbreak()
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
