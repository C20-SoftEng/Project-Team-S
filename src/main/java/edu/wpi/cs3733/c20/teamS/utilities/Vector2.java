package edu.wpi.cs3733.c20.teamS.utilities;

import java.util.Objects;

/**
 * A vector of two doubles.
 */
public final class Vector2 {
    private final double x_;
    private final double y_;

    /**
     * Creates a new Vector2 with the specified x and y coordinates.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Vector2(double x, double y){
        this.x_ = x;
        this.y_ = y;
    }

    /**
     * The x-coordinate of the vector.
     * @return The x-coordinate of the vector.
     */
    public double x(){
        return this.x_;
    }

    /**
     * The y-coordinate of the vector.
     * @return The y-coordinate of the vector.
     */
    public double y(){
        return this.y_;
    }

    /**
     * Creates a new Vector2 by replacing the current Vector2's x-coordinate with the specified value.
     * @param newX The x-coordinate of the new Vector2.
     * @return A new Vector2, same as the current, but with the specified x-coordinate.
     */
    public Vector2 withX(double newX) {
        return new Vector2(newX, this.y_);
    }

    /**
     * Creates a new Vector2 by replacing the current Vector2's y-coordinate with the specified value.
     * @param newY The y-coordinate of the new Vector2.
     * @return A new Vector2, same as the current, but with the specified y-coordinate.
     */
    public Vector2 withY(double newY) {
        return new Vector2(this.x_, newY);
    }

    /**
     * The Euclidian length of the vector.
     * @return The length of the vector.
     */
    public double length(){
        return Math.sqrt(Math.pow(this.x_,2) + Math.pow(this.y_,2));
    }

    /**
     * Gets a unit vector in the direction of the current vector.
     * @return a unit vector in the direction of the vector.
     */
    public Vector2 unit(){
        double unitX = this.x_/this.length();
        double unitY = this.y_/this.length();
        return new Vector2(unitX, unitY);
    }

    /**
     * Creates a new Vector2 by adding the other Vector2 to the current one. The current instance
     * is not modified.
     * @param other another vector.
     * @return A new Vector2 equal to the sum of the vector with the other vector.
     */
    public Vector2 add(Vector2 other){
        double x = this.x() + other.x();
        double y = this.y() + other.y();

        return new Vector2(x, y);
    }

    /**
     * Creates a new Vector2 by taking the difference between the current Vector2 and the other Vector2.
     * @param other another vector.
     * @return A new Vector2 equal to the difference of the current Vector2 and the other Vector2.
     */
    public Vector2 subtract(Vector2 other) {
        double x = this.x() - other.x();
        double y = this.y() - other.y();

        return new Vector2(x, y);
    }

    /**
     * Creates a new Vector2 by performing scalar-multiplication on the current Vector2.
     * @param scalar a scalar.
     * @return A new Vector2 equal to the product of the current Vector2 and the specified scalar.
     */
    public Vector2 multiply(double scalar){
        double x = this.x() * scalar;
        double y = this.y() * scalar;

        return new Vector2(x, y);
    }

    public Vector2 multiply(double scalarX, double scalarY){
        double x = this.x() * scalarX;
        double y = this.y() * scalarY;

        return  new Vector2(x,y);
    }

    /**
     * Returns the quotient between the vector and a scalar.
     * @param scalar a scalar.
     * @return The quotient between the vector and a scalar.
     */
    public Vector2 divide(double scalar){
        double x = this.x() / scalar;
        double y = this.y() / scalar;

        return new Vector2(x, y);
    }

    /**
     * Returns the dot product between the vector and another vector.
     * @param other another vector.
     * @return The dot product between the vector and another vector.
     */
    public double dot(Vector2 other){
        double productX = this.x() * other.x();
        double productY = this.y() * other.y();

        return productX + productY;
    }

    public double distanceTo(double x, double y) {
        double xOffset = this.x_ - x;
        double yOffset = this.y_ - y;
        return Math.sqrt(xOffset * xOffset + yOffset * yOffset);
    }
    public double distanceTo(Vector2 other) {
        return distanceTo(other.x(), other.y());
    }

    /**
     * Compares the two vectors for value-equality.
     * @return True if both are equal.
     */
    public static boolean equals(Vector2 left, Vector2 right) {
        if (left == right)
            return true;
        if (left == null || right == null)
            return false;

        return left.x_ == right.x_ &&
                left.y_ == right.y_;
    }

    /**
     * Overridden to have value semantics.
     * @return A well-distributed hash code for the current Vector2.
     */
    @Override
    public int hashCode() {
        int xHash = Double.hashCode(x_);
        int yHash = Double.hashCode(y_);

        return Objects.hash(x_, y_);
    }

    /**
     * Overridden to have value-semantics.
     * @param other The other object.
     * @return True if equal.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Vector2)
            return equals(this, (Vector2)other);

        return false;
    }

    /**
     * Determines if the current vector is equal to the other vector within
     * the specified tolerance.
     * @param other The other vector to compare to.
     * @param epsilon The tolerance to use for the comparison.
     * @return True if the coordinates of the current vector are within the tolerance of the
     * coordinates of the other vector.
     */
    public boolean equals(Vector2 other, double epsilon) {
        if (other == null)
            return false;

        return Math.abs(x_ - other.x_) <= epsilon &&
                Math.abs(y_ - other.y_) <= epsilon;
    }

    /**
     * Overridden to be useful.
     * @return (x, y)
     */
    @Override
    public String toString() {
        return "(" + x_ + ", " + y_ + ")";
    }
}
