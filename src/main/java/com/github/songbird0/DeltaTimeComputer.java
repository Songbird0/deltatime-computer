package com.github.songbird0;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class DeltaTimeComputer {

    private final Calendar initialTime;

    /**
     * The list of the delta times to apply.
     */
    private final List<DeltaTime> deltaTimeList = new ArrayList<>();

    /**
     * The current amount which will be used to build the next delta time to apply.
     * <p><strong>Note</strong>: {@code Integer.MIN_VALUE} doesn't mean much. This value is used to inform the rest of the system
     * the amount has not been modified yet.</p>
     */
    private int currentAmount = Integer.MIN_VALUE;
    /**
     * The current field which will be used to build the next delta time to apply.
     * <p><strong>Note</strong>: {@code Integer.MIN_VALUE} doesn't mean much. This value is used to inform the rest of the system
     * the field has not been modified yet.</p>
     */
    private int currentField = Integer.MIN_VALUE;

    public DeltaTimeComputer(final Calendar yourTime) {
        this.initialTime = yourTime;
    }

    public final class DeltaTime {

        private int amount;
        /*Measurement unit*/
        private int field;

        public DeltaTime(final int amount, final int field) {
            if (amount <= 0) {
                throw new RuntimeException("Cannot be null or negative.");
            }

            this.amount = amount;
            this.field = field;
        }

        public final void incrementAmountOf(final int amountToAdd) {
            if (amountToAdd <= 0) {
                throw new RuntimeException("Cannot be null or negative.");
            }
            this.amount += amountToAdd;
        }
    }

    public final DeltaTimeComputer plus(int amount) {
        if (amount <= 0) {
            throw new RuntimeException("`amount` cannot be negative or null.");
        }
        /*The amount will be modified. `Integer.MIN_VALUE` isn't useful anymore.*/
        if (this.currentAmount == Integer.MIN_VALUE) {
            this.currentAmount &= 0;
            assert this.currentAmount == 0;
        }
        this.currentAmount += amount;
        assert currentAmount > 0;
        return this;
    }

    /**
     * Resets both of {@code currentAmount} and {@code currentField} properties at the end of the cycle.
     */
    private void resetCurrentValue() {
        this.currentAmount = 0;
        this.currentField = 0;
    }

    public final DeltaTimeComputer second() {
        this.currentField = Calendar.SECOND;
        deltaTimeList.add(new DeltaTime(this.currentAmount, this.currentField));
        /*Resetting the values to inform the cycle is terminated.*/
        this.resetCurrentValue();
        return this;
    }
    public final DeltaTimeComputer minute() {
        this.currentField = Calendar.MINUTE;
        deltaTimeList.add(new DeltaTime(this.currentAmount, this.currentField));
        this.resetCurrentValue();
        return this;
    }
    public final DeltaTimeComputer hour() {
        this.currentField = Calendar.HOUR;
        deltaTimeList.add(new DeltaTime(this.currentAmount, this.currentField));
        this.resetCurrentValue();
        return this;
    }
    public final DeltaTimeComputer day() {
        this.currentField = Calendar.DAY_OF_MONTH;
        deltaTimeList.add(new DeltaTime(this.currentAmount, this.currentField));
        this.resetCurrentValue();
        return this;
    }
    public final DeltaTimeComputer month() {
        this.currentField = Calendar.MONTH;
        deltaTimeList.add(new DeltaTime(this.currentAmount, this.currentField));
        this.resetCurrentValue();
        return this;
    }
    public final DeltaTimeComputer year() {
        this.currentField = Calendar.YEAR;
        deltaTimeList.add(new DeltaTime(this.currentAmount, this.currentField));
        this.resetCurrentValue();
        return this;
    }
    public final Calendar computeIt() {
        if (this.currentAmount == Integer.MIN_VALUE) {
            throw new RuntimeException("`currentAmount` has not been modified. You can't call `computeIt()` with uninitialized value.");
        }
        if (this.currentField == Integer.MIN_VALUE) {
            throw new RuntimeException("`currentField` has not been modified. You can't call `computeIt()` with uninitialized value.");
        }

        this.deltaTimeList.forEach(deltaTime -> this.initialTime.add(deltaTime.field, deltaTime.amount));
        return initialTime;
    }
}