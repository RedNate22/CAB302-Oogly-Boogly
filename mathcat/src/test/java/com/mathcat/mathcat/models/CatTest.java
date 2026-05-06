package com.mathcat.mathcat.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class CatTest {
    private Cat cat;

    @BeforeEach
    void setUp() {
        cat = new Cat("Rosie");
    }

    @Nested
    class Constructor {
        @Test
        void setsName() {
            assertEquals("Rosie", cat.getCatName());
        }

        @Test
        void defaultHappiness() {
            assertEquals(100.0, cat.getHappiness(), 0.001);
        }

        @Test
        void defaultFullness() {
            assertEquals(100.0, cat.getFullness(), 0.001);
        }

        @Test
        void defaultEnergy() {
            assertEquals(100.0, cat.getEnergy(), 0.001);
        }

        @Test
        void defaultLevel() {
            assertEquals(1, cat.getLevel());
        }

        @Test
        void defaultXp() {
            assertEquals(0.0, cat.getXp(), 0.001);
        }

        @Test
        void defaultInventoryEmpty() {
            assertTrue(cat.getItems().isEmpty());
        }

        @Test
        void defaultLastSavedIsNull() {
            assertNull(cat.getLastSaved());
        }

        @Test
        void defaultCatIdIsZero() {
            assertEquals(0, cat.getCatId());
        }

        @Test
        void defaultUserIdIsZero() {
            assertEquals(0, cat.getUserId());
        }
    }
}
