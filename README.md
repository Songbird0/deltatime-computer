# DeltaTimeComputer

A sim-pol [`Calendar`](https://docs.oracle.com/javase/8/docs/api/java/util/Calendar.html) wrapper to manage 
date and hour easily.

## Examples

You can find all of these examples into the `test` directory.

```java
final Calendar now = new GregorianCalendar();
final int currentYear = now.get(Calendar.YEAR);
final Calendar tomorrow =  new DeltaTimeComputer(now).plus(2).year().computeIt();
// We are at the current date + 2 years!
assertThat(tomorrow.get(Calendar.YEAR), is(currentYear + 2));
```

Additionally, the delta times may be builded up.

```java
final Calendar now = new GregorianCalendar();
final int currentYear = now.get(Calendar.YEAR);
final int currentDay = now.get(Calendar.DAY_OF_MONTH);
final Calendar tomorrow = new DeltaTimeComputer(now)
        .plus(1)
        .day() // stacked
        .plus(1)
        .year() // stacked
        .computeIt();
assertThat(tomorrow.get(Calendar.YEAR), is(currentYear + 1));
assertThat(tomorrow.get(Calendar.DAY_OF_MONTH), is(currentDay + 1));
```
