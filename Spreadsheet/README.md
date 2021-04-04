This is the task from the Java Programming course at the Jagiellonian University.

It was about implementing some simple spreadsheet.
The worksheet operates only on integers
in the range limited by the int type.

Supported operations:
- value: if the cell contains just a value,
leave it
- reference: if the cell starts from $, it is referenced to the other cell;
for example $A1 means the first row and the first column
- formula: if the cell starts with =, it contains a formula; only a value, or a reference
can be a parameter of a formula.

Formulas:
- ADD - addition
- SUB - subtraction
- MUL - multiplication
- DIV - division
- MOD - modulo

The worksheet does not contain recurring references (A1 to B1 and then again B1 to A1).

