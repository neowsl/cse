#let subq-counter = counter("subquestion")

#let assignment(
  class: "CSE 311",
  assignment: "HW 1",
  collaborators: (),
  body,
) = {
  set page(
    margin: 1in,
    header: [
      #align(left)[#class #assignment]
      #line(length: 100%)
    ],
    numbering: "1",
  )

  align(center)[
    #outline(title: "Contents", depth: 1)

    #if collaborators != () [
      *Collaborators:* #collaborators.join(", ")
    ]

    #v(2em)
  ]

  set heading(numbering: "1.")
  show heading.where(level: 1): it => {
    subq-counter.update(0)
    block(spacing: 1.5em)[
      *Question #counter(heading).display() #it.body*
    ]
  }
  show heading.where(level: 2): it => {
    block(spacing: 1em)[*#it.body*]
  }

  body
}

#let solution(width: 100%, body) = {
  subq-counter.step()
  block(
    width: width,
    fill: rgb("f6f8ff"),
    stroke: rgb("ccd9ff") + 0.4pt,
    radius: 3mm,
    inset: 1.2em,
    spacing: 1em,
    breakable: true,
  )[
    *#context subq-counter.display("(a)")* #h(1em)

    #set enum(numbering: "i.")

    #body
  ]
}
