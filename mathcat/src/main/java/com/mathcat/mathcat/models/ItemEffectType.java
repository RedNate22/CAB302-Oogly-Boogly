package com.mathcat.mathcat.models;

/**
 * Defines the type of effect an {@link Item} applies to the cat's stats.
 */
public enum ItemEffectType {
    /** Increases the cat's happiness stat when used. */
    HAPPINESS,
    /** Increases the cat's fullness stat when used. */
    FULLNESS,
    /** Increases the cat's energy stat when used. */
    ENERGY,
    /** Applies a visual change with no stat effect. */
    COSMETIC
}
