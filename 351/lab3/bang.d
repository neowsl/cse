
bang.o:     file format elf64-x86-64


Disassembly of section .text:

0000000000000000 <.text>:
   0:	68 20 10 40 00       	push   $0x401020
   5:	49 ba d1 61 d1 6e 01 	movabs $0x5d745016ed161d1,%r10
   c:	45 d7 05 
   f:	49 c7 c3 08 23 60 00 	mov    $0x602308,%r11
  16:	4d 89 13             	mov    %r10,(%r11)
  19:	c3                   	ret
