# lab_1 MAP

--------------------------------------------------------------------------
Deutsch: 
Problem 1:

Die Java Universität hat die folgenden Benotung Regel:
 ● Jeder Student bekommt eine Note zwischen 0 und 100.
 ● Eine Note weniger als 40 ist eine nicht ausreichende Note.
 Der Professor rundet die Note mit die folgenden Regel ab:
 ● Ob die Differenz zwischen die Note und die nächste multipel von 5 weniger als 3 ist,
 dann wird die Note zu die nächste multipel von 5 abgerundet.
 ● Ob die Note weniger als 38 ist, wird die Note nicht abgerundet.
z.B. 84 => 85
 29 => 29
1. Schreiben Sie eine Methode, die ein Array von Noten bekommen soll. Als die
Rückgabewert soll die Methode ein Array mit nicht ausreichende Note liefern.
2. Schreiben Sie eine Methode, die ein Array von Noten bekommen soll. Als die
Rückgabewert soll die Methode den Durchschnittswert liefern.
3. Schreiben Sie eine Methode, die ein Array von Noten bekommen soll. Als die
Rückgabewert soll die Methode eine Array mit die abgerundete Note liefern.
4. Schreiben Sie eine Methode, die ein Array von Noten bekommen soll. Als die
Rückgabewert soll die Methode die maximale abgerundete Note liefern.

--------------------------------------------------------------------------

Problem 2: 
Es gibt eine Array mit n positive Zahlen.
1. Finden Sie die maximale Zahl.
2. Finden Sie die minimale Zahl.
3. Finden Sie die maximale Summe von n-1 Zahlen.
z.B. [4, 8, 3, 10, 17] => 4 + 8 + 10 + 17 = 39
4. Finden Sie die minimale Summe von n-1 Zahlen.
z.B. [4, 8, 3, 10, 17] => 4 + 8 + 3 + 10 = 25

--------------------------------------------------------------------------

Problem 3:
Es gibt 2 große Zahlen. Die Zahlen sind als Array dargestellt.
1. Berechnen Sie die Summe. Die Zahlen haben die gleiche Anzahl an Ziffern.
z.B. [1 3 0 0 0 0 0 0 0] + [8 7 0 0 0 0 0 0 0] = [1 0 0 0 0 0 0 0 0 0]
2. Berechnen Sie die Differenz. Die Zahlen haben die gleiche Anzahl an Ziffern.
z.B. [8 3 0 0 0 0 0 0 0] - [5 4 0 0 0 0 0 0 0] = [2 9 0 0 0 0 0 0 0]
3. Berechnen Sie die Multiplikation. Erste Zahl ist ein große Zahl, der Zweite Zahl ist nur
ein Ziffer.
z.B. [2 3 6 0 0 0 0 0 0] * 2 = [4 7 2 0 0 0 0 0 0]
4. Berechnen Sie die ganzzahlige Division. Erste Zahl ist ein große Zahl, der Zweite Zahl
ist nur ein Ziffer.
z.B. [2 3 6 0 0 0 0 0 0] / 2 = [1 1 8 0 0 0 0 0 0]
--------------------------------------------------------------------------

Problem 4:
Markus will ein USB Laufwerk und eine Tastatur kaufen. Der Elektronik-Shop hat
verschiedene USB Laufwerke und Tastaturen mit verschiedene Preise, als Array von Zahlen
dargestellt.
1. Schreiben Sie eine Methode, die die billigste Tastatur zurückgibt.
z.B. [40 35 70 15 45] => 15
2. Schreiben Sie eine Methode, die die teuerste Gegenstand zurückgibt.
z.B. Tastatur = [15 20 10 35], USB = [20, 15, 40 15] => 40
3. Schreiben Sie eine Methode, die die teuerste USB Laufwerk, die Markus kaufen kann,
zurückgibt.
z.B. Preise = [15 45 20], Budget = 30 => 20
4. Finden Sie, anhand des Markus Budget und der Preislisten für die Tastaturen und
USB-Laufwerke, den Geldbetrag Markus ausgeben wird. Wenn er nicht genug für
beide hat, geben Sie stattdessen -1 zurück. Er kauft nur die zwei benötigten
Gegenstände.
z.B. b=60, tastaturen = [40 50 60] und usb Laufwerke = [8 12] => 50 + 8 = 58



English: 
**Problem 1:**

The Java University has the following grading rules:
- Each student receives a grade between 0 and 100.
- A grade less than 40 is a failing grade.
The professor rounds the grade according to the following rule:
- If the difference between the grade and the next multiple of 5 is less than 3,
then the grade is rounded up to the next multiple of 5.
- If the grade is less than 38, the grade is not rounded.
e.g. 84 => 85
29 => 29

1. Write a method that should take an array of grades. As a return value, the method should provide an array with failing grades.
2. Write a method that should take an array of grades. As a return value, the method should provide the average grade.
3. Write a method that should take an array of grades. As a return value, the method should provide an array with the rounded grades.
4. Write a method that should take an array of grades. As a return value, the method should provide the maximum rounded grade.

**Problem 2:**
There is an array with n positive numbers.
1. Find the maximum number.
2. Find the minimum number.
3. Find the maximum sum of n-1 numbers.
e.g. [4, 8, 3, 10, 17] => 4 + 8 + 10 + 17 = 39
4. Find the minimum sum of n-1 numbers.
e.g. [4, 8, 3, 10, 17] => 4 + 8 + 3 + 10 = 25

**Problem 3:**
There are 2 large numbers represented as arrays.
1. Calculate the sum. The numbers have the same number of digits.
e.g. [1 3 0 0 0 0 0 0 0] + [8 7 0 0 0 0 0 0 0] = [1 0 0 0 0 0 0 0 0 0]
2. Calculate the difference. The numbers have the same number of digits.
e.g. [8 3 0 0 0 0 0 0 0] - [5 4 0 0 0 0 0 0 0] = [2 9 0 0 0 0 0 0 0]
3. Calculate the multiplication. The first number is a large number, the second number is just a digit.
e.g. [2 3 6 0 0 0 0 0 0] * 2 = [4 7 2 0 0 0 0 0 0]
4. Calculate the integer division. The first number is a large number, the second number is just a digit.
e.g. [2 3 6 0 0 0 0 0 0] / 2 = [1 1 8 0 0 0 0 0 0]

**Problem 4:**
Markus wants to buy a USB drive and a keyboard. The electronics shop has various USB drives and keyboards with different prices, represented as arrays of numbers.
1. Write a method that returns the cheapest keyboard.
e.g. [40 35 70 15 45] => 15
2. Write a method that returns the most expensive item.
e.g. Keyboard = [15 20 10 35], USB = [20, 15, 40 15] => 40
3. Write a method that returns the most expensive USB drive Markus can buy.
e.g. Prices = [15 45 20], Budget = 30 => 20
4. Find, based on Markus' budget and the price lists for the keyboards and USB drives, the amount of money Markus will spend. If he doesn't have enough for both, return -1 instead. He only buys the two required items.







