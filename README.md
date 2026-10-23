# Stock Portfolio Tracker (Java)

A text-based stock portfolio simulator built with object-oriented Java.
Start with $10,000 of virtual cash, buy and sell stocks, and simulate market days
where every price moves randomly between -5% and +5%.

## Concepts Demonstrated
- **Object-oriented design:** three classes with clear responsibilities
- **Encapsulation:** private fields with getters/setters
- **ArrayList:** dynamic collection of `Stock` objects
- **Loops and searching:** linear search, enhanced for-loops, menu loop
- **Randomness:** `java.util.Random` for market simulation
- **Input validation:** `try/catch` around number parsing

## Structure
| File | Role |
|------|------|
| `Stock.java` | Data model for one position (ticker, prices, shares, ROI) |
| `Portfolio.java` | Engine: buy, sell, market simulation, total wealth |
| `Main.java` | Text menu and user input |

## Run It
```bash
javac *.java
java Main
```
