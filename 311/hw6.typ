#import "template.typ": assignment, solution
#import "@preview/finite:0.5.1"
#import "@preview/finite:0.5.1": automaton, cetz

#show: assignment.with(
  assignment: "Homework 6",
)

= The Great Expression

#solution[
  #cetz.canvas({
    import cetz.draw: set-style
    import finite.draw: state, transition
    state((0, 0), "q0", initial: true)
    state((1, 2), "q1")
    state((2, 2), "q2")
    state((3, 2), "q3")
    state((4, 2), "q4")
    state((5, 2), "q5")
    state((6, 3), "q6")
    state((7, 3), "q7")
    state((8, 3), "q8")
    state((9, 3), "q9")
    state((6, 1), "q10")
    state((7, 1), "q11")
    state((10, 2), "q12")
    state((11, 2), "q13")
    state((12, 2), "q14")
    state((13, 2), "q15", final: true)
    state((1, -1), "q16")
    state((2, -1), "q17")
    state((3, -1), "q18")
    state((4, -1), "q19")
    state((5, -1), "q20")
    state((6, -1), "q21")
    state((7, -1), "q22")
    state((8, -1), "q23", final: true)
    transition("q0", "q1", inputs: sym.epsilon)
    transition("q1", "q2", inputs: 1)
    transition("q2", "q3", inputs: sym.epsilon)
    transition("q3", "q4", inputs: 0)
    transition("q4", "q5", inputs: sym.epsilon)
    transition("q5", "q6", inputs: sym.epsilon)
    transition("q6", "q7", inputs: 0)
    transition("q7", "q8", inputs: sym.epsilon)
    transition("q8", "q9", inputs: 1)
    transition("q9", "q5", inputs: sym.epsilon, curve: 0)
    transition("q5", "q10", inputs: sym.epsilon)
    transition("q10", "q11", inputs: 1)
    transition("q11", "q5", inputs: sym.epsilon, curve: 1.3)
    transition("q9", "q12", inputs: sym.epsilon)
    transition("q11", "q12", inputs: sym.epsilon, curve: 0)
    transition("q12", "q13", inputs: 1)
    transition("q13", "q14", inputs: sym.epsilon)
    transition("q14", "q15", inputs: 1)
    transition("q0", "q16", inputs: sym.epsilon)
    transition("q16", "q17", inputs: 0)
    transition("q17", "q18", inputs: sym.epsilon)
    transition("q18", "q19", inputs: 1)
    transition("q19", "q20", inputs: sym.epsilon)
    transition("q20", "q21", inputs: 1)
    transition("q21", "q22", inputs: sym.epsilon)
    transition("q22", "q23", inputs: 0)
  })
]

#solution[
  #cetz.canvas({
    import cetz.draw: set-style
    import finite.draw: state, transition
    state((0, 0), "q0", initial: true)
    state((1, 0), "q1")
    state((2, 0), "q2")
    state((3, 1), "q3")
    state((4, 1), "q4")
    state((5, 1), "q5")
    state((6, 1), "q6")
    state((3, -1), "q7")
    state((4, -1), "q8")
    state((5, -1), "q9")
    state((6, -1), "q10")
    state((7, 0), "q11", final: true)
    transition("q0", "q1", inputs: 1)
    transition("q1", "q2", inputs: sym.epsilon)
    transition("q2", "q3", inputs: sym.epsilon)
    transition("q3", "q4", inputs: 0)
    transition("q4", "q5", inputs: sym.epsilon)
    transition("q5", "q6", inputs: 1)
    transition("q2", "q7", inputs: sym.epsilon)
    transition("q7", "q8", inputs: 1)
    transition("q8", "q9", inputs: sym.epsilon)
    transition("q9", "q10", inputs: 0)
    transition("q6", "q2", inputs: sym.epsilon, curve: 0)
    transition("q10", "q2", inputs: sym.epsilon, curve: 0)
    transition("q6", "q11", inputs: sym.epsilon)
    transition("q10", "q11", inputs: sym.epsilon)
    transition("q11", "q0", inputs: sym.epsilon, curve: 3)
  })
]

#pagebreak()

= Puedo ir(regular) al bano

#solution[
  Suppose, for the sake of contradiction, that $S$ is regular. Then there is a DFA $M$ such that $M$ accepts exactly $S$.

  Let $R = {"("^n : n >= 0}$. Because the DFA is finite and $R$ is infinite, there are two different strings $x$, $y$ in $R$ such that $x$ and $y$ go to the same state when read by $M$. Since both are in $R$, $x = "("^a$ for some integer $a >= 0$, and $y = "("^b$ for some integer $b >= 0$, with $a != b$.

  Consider the string $z = ")"^a$.

  Since $x$, $y$ led to the same state and $M$ is deterministic, $x z$ and $y z$ will also lead to the same state $q$ in $M$. Observe that $x z = "("^a ")"^a$, so $x z in S$, but $y z = "("^a ")"^b$, so $y z in.not S$. Since $q$ can only be either an accept or a reject state, but not both, $M$ does not actually recognise $S$. That's a contradiction!

  Therefore, $S$ is an irregular language.
]

#solution[
  Suppose, for the sake of contradiction, that $L = {0^m 1^n : m, n in NN "and" m | n$ is regular. Then there is a DFA $M$ such that $M$ accepts exactly $L$.

  Let $S = {0^n : n in NN "and" n "is prime"}$. Since there are infinitely many prime numbers, and because the DFA is finite and $S$ is infinite, there are two different strings $x$, $y$ in $S$ such that $x$ and $y$ go to the same state when read by $M$. Since both are in $S$, $x = 0^a$ for some prime integer $a$, and $y = 0^b$ for some prime integer $b$.

  Consider the string $z = 1^a$.

  Since $x$, $y$ led to the same state and $M$ is deterministic, $x z$ and $y z$ will also lead to the same state $q$ in $M$. Observe that $x z = 0^a 1^a$, and since $a$ divides $a$, $x z in L$. However, $y z = 0^b 1^a$, and since $a$ is prime and can only be divided by itself and 1, and $b != 1$ since $b$ is prime, $y z in.not L$. Since $q$ can only be either an accept or a reject state, but not both, $M$ does not actually recognise $L$. That's a contradiction!

  Therefore, $L$ is an irregular language.
]

Continued on next page $->$

#pagebreak()

#solution[
  Suppose, for the sake of contradiction, that $L = {0^m 1^n : m, n in NN "and" m <= n^2$ is regular. Then there is a DFA $M$ such that $M$ accepts exactly $L$.

  Let $S = {0^n : n in NN "and" n > 0 "is a square number"}$. Since there are infinitely many square numbers, and because the DFA is finite and $S$ is infinite, there are two different strings $x$, $y$ in $S$ such that $x$ and $y$ go to the same state when read by $M$. Since both are in $S$, $x = 0^a$ for some square number $a > 0$, and $y = 0^b$ for some square number $b > 0$.

  Note that since $a != b$, $a > b$ or $b > a$. We proceed by cases...

  Suppose that $a > b$. Consider the string $z = 1^(sqrt(b))$. Note that this is valid, since $b$ is a square number.

  Since $x$, $y$ led to the same state and $M$ is deterministic, $x z$ and $y z$ will also lead to the same state $q$ in $M$. Observe that $y z = 0^b 1^(sqrt(b))$, and since $b = sqrt(b)^2$, $y z in L$. However, $x z = 0^a 1^(sqrt(b))$, and since $a > sqrt(b)^2$, $y z in.not L$. Since $q$ can only be either an accept or a reject state, but not both, $M$ does not actually recognise $L$. That's a contradiction! Therefore, if $a > b$, then $L$ is an irregular language.

  Now suppose that $b > a$. Consider the string $z = 1^(sqrt(a))$. Note that this is valid, since $a$ is a square number.

  Since $x$, $y$ led to the same state and $M$ is deterministic, $x z$ and $y z$ will also lead to the same state $q$ in $M$. Observe that $x z = 0^a 1^(sqrt(a))$, and since $a = sqrt(a)^2$, $x z in L$. However, $y z = 0^b 1^(sqrt(a))$, and since $b > sqrt(a)^2$, $y z in.not L$. Since $q$ can only be either an accept or a reject state, but not both, $M$ does not actually recognise $L$. That's a contradiction! Therefore, if $b > a$, then $L$ is an irregular language.

  Since our cases were exhaustive, we have shown that $L$ is an irregular language.
]

#pagebreak()

= Count Yourself Blist!

#solution[
  We will use the dovetailing method to show that $A$ is countable.

  1. Each element $L in A$ can be assigned to group $N$, where $N = "len"(L)$.
  2. Since $N in NN$, we can use the natural ordering of numbers to order these groups.
  3. Within each group $N$, for each element $L$, let $B$ be $sum_(i = 0)^N L[i] dot 2^i$, essentially converting each list to a binary encoding. Since $B$ is a unique finite number, we can order the elements of $N$ by the natural ordering of numbers.

  Therefore, we have shown, by dovetailing, that $A$ is countable.
]

#solution[
  Consider an arbitrary element $L in B$. $L$ is also an element of $A$ (from the previous solution), since $A$ contains *all* lists whose elements are in ${0, 1}$.

  Since $L$ was arbitrary, by definition of subset, $B$ is a subset of the countable set $A$.

  Thus, $B$ must be countable, because it is a subset of a countable set.
]

#solution[
  Suppose, for the sake of contradiction, that $C$ is countable.

  Then, there exists a surjection (onto function) $f : NN -> C$. So for every natural number $i$, we have some element $C_i$ that $i$ maps to.

  We now construct an element $x$ of $S$ such that $x[i] = ~C_i [i]$. That is, $x[i]$ is 1 if $C_i [i]$ is 0, and $x[i]$ is 0 if $C_i [i]$ is 1.

  So, no $f(i)$ maps to the element $x$ of $C$. But then $f$ is not a surjection and there exists no way for us to enumerate all elements of $C$. That's a contradiction!

  Therefore, $C$ is uncountable.
]
