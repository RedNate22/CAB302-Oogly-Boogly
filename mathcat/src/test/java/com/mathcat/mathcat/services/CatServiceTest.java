package com.mathcat.mathcat.services;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Item;
import com.mathcat.mathcat.models.ItemEffectType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CatServiceTest {
    private Cat cat;

    @BeforeEach
    void setUp() {
        CatDAO.clearForTesting();
        cat = new Cat("Whiskers"); // starts at: happiness=100, fullness=100, energy=0
    }

    @Nested
    class IsValidCatName {
        @Test
        void singleLetter() {
            assertTrue(CatService.isValidCatName("A"));
        }

        @Test
        void tenLetters() {
            assertTrue(CatService.isValidCatName("Abcdefghij"));
        }

        @Test
        void elevenLetters_invalid() {
            assertFalse(CatService.isValidCatName("Abcdefghijk"));
        }

        @Test
        void empty_invalid() {
            assertFalse(CatService.isValidCatName(""));
        }

        @Test
        void containsNumbers_invalid() {
            assertFalse(CatService.isValidCatName("Cat123"));
        }

        @Test
        void containsSpaces_invalid() {
            assertFalse(CatService.isValidCatName("My Cat"));
        }
    }

    @Nested
    class IncreaseHappiness {
        @Test
        void addsValue() {
            cat.setHappiness(50.0);
            CatService.increaseHappiness(cat, 20.0);
            assertEquals(70.0, cat.getHappiness(), 0.001);
        }

        @Test
        void clampedAtMax() {
            cat.setHappiness(90.0);
            CatService.increaseHappiness(cat, 20.0);
            assertEquals(CatService.MAX_STAT, cat.getHappiness(), 0.001);
        }
    }

    @Nested
    class DecreaseHappiness {
        @Test
        void subtractsValue() {
            cat.setHappiness(50.0);
            CatService.decreaseHappiness(cat, 20.0);
            assertEquals(30.0, cat.getHappiness(), 0.001);
        }

        @Test
        void clampedAtMin() {
            cat.setHappiness(10.0);
            CatService.decreaseHappiness(cat, 20.0);
            assertEquals(CatService.MIN_STAT, cat.getHappiness(), 0.001);
        }
    }

    @Nested
    class IncreaseFullness {
        @Test
        void addsValue() {
            cat.setFullness(50.0);
            CatService.increaseFullness(cat, 20.0);
            assertEquals(70.0, cat.getFullness(), 0.001);
        }

        @Test
        void clampedAtMax() {
            cat.setFullness(90.0);
            CatService.increaseFullness(cat, 20.0);
            assertEquals(CatService.MAX_STAT, cat.getFullness(), 0.001);
        }
    }

    @Nested
    class DecreaseFullness {
        @Test
        void subtractsValue() {
            cat.setFullness(50.0);
            CatService.decreaseFullness(cat, 20.0);
            assertEquals(30.0, cat.getFullness(), 0.001);
        }

        @Test
        void clampedAtMin() {
            cat.setFullness(10.0);
            CatService.decreaseFullness(cat, 20.0);
            assertEquals(CatService.MIN_STAT, cat.getFullness(), 0.001);
        }
    }

    @Nested
    class IncreaseEnergy {
        @Test
        void addsValue() {
            cat.setEnergy(0.0);
            CatService.increaseEnergy(cat, 30.0);
            assertEquals(30.0, cat.getEnergy(), 0.001);
        }

        @Test
        void clampedAtMax() {
            cat.setEnergy(90.0);
            CatService.increaseEnergy(cat, 20.0);
            assertEquals(CatService.MAX_STAT, cat.getEnergy(), 0.001);
        }
    }

    @Nested
    class DecreaseEnergy {
        @Test
        void subtractsValue() {
            cat.setEnergy(50.0);
            CatService.decreaseEnergy(cat, 20.0);
            assertEquals(30.0, cat.getEnergy(), 0.001);
        }

        @Test
        void clampedAtMin() {
            cat.setEnergy(10.0);
            CatService.decreaseEnergy(cat, 20.0);
            assertEquals(CatService.MIN_STAT, cat.getEnergy(), 0.001);
        }
    }

    @Nested
    class RegenerateEnergy {
        @Test
        void atMaxFullness_addsFullRate() {
            cat.setFullness(100.0);
            cat.setEnergy(0.0);
            CatService.regenerateEnergy(cat);
            assertEquals(CatService.ENERGY_REGEN_RATE, cat.getEnergy(), 0.001);
        }

        @Test
        void atHalfFullness_addsHalfRate() {
            cat.setFullness(50.0);
            cat.setEnergy(0.0);
            CatService.regenerateEnergy(cat);
            assertEquals(CatService.ENERGY_REGEN_RATE * 0.5, cat.getEnergy(), 0.001);
        }

        @Test
        void atZeroFullness_noRegen() {
            cat.setFullness(0.0);
            cat.setEnergy(0.0);
            CatService.regenerateEnergy(cat);
            assertEquals(0.0, cat.getEnergy(), 0.001);
        }

        @Test
        void clampedAtMax() {
            cat.setFullness(100.0);
            cat.setEnergy(99.5);
            CatService.regenerateEnergy(cat);
            assertEquals(CatService.MAX_STAT, cat.getEnergy(), 0.001);
        }
    }

    @Nested
    class IsHungry {
        @Test
        void belowThreshold() {
            cat.setFullness(CatService.HUNGER_THRESHOLD - 1);
            assertTrue(CatService.isHungry(cat));
        }

        @Test
        void atThreshold() {
            cat.setFullness(CatService.HUNGER_THRESHOLD);
            assertTrue(CatService.isHungry(cat));
        }

        @Test
        void aboveThreshold() {
            cat.setFullness(CatService.HUNGER_THRESHOLD + 1);
            assertFalse(CatService.isHungry(cat));
        }
    }

    @Nested
    class ApplyHungerPenalty {
        @Test
        void whenHungry_decreasesHappiness() {
            cat.setFullness(20.0);
            cat.setHappiness(50.0);
            CatService.applyHungerPenalty(cat);
            assertTrue(cat.getHappiness() < 50.0);
        }

        @Test
        void whenNotHungry_noChange() {
            cat.setFullness(50.0);
            cat.setHappiness(50.0);
            CatService.applyHungerPenalty(cat);
            assertEquals(50.0, cat.getHappiness(), 0.001);
        }
    }

    @Nested
    class ApplyOfflineDecay {
        @Test
        void nullLastSaved_noChange() {
            cat.setHappiness(80.0);
            cat.setFullness(80.0);
            // lastSaved is null by default on a new Cat
            CatService.applyOfflineDecay(cat);
            assertEquals(80.0, cat.getHappiness(), 0.001);
            assertEquals(80.0, cat.getFullness(), 0.001);
        }

        @Test
        void appliesCorrectDecay() {
            cat.setHappiness(80.0);
            cat.setFullness(80.0);
            cat.setLastSaved(LocalDateTime.now().minusMinutes(60));

            CatService.applyOfflineDecay(cat);

            double expectedDecay = 60 * CatService.HAPPINESS_DECAY_RATE;
            assertEquals(80.0 - expectedDecay, cat.getHappiness(), 0.001);
            assertEquals(80.0 - expectedDecay, cat.getFullness(), 0.001);
        }

        @Test
        void clampedAtMin() {
            cat.setHappiness(1.0);
            cat.setFullness(1.0);
            cat.setLastSaved(LocalDateTime.now().minusDays(7)); // far enough to guarantee full decay
            CatService.applyOfflineDecay(cat);
            assertEquals(CatService.MIN_STAT, cat.getHappiness(), 0.001);
            assertEquals(CatService.MIN_STAT, cat.getFullness(), 0.001);
        }
    }

    @Nested
    class Inventory {
        @Test
        void addItem_addsToInventory() {
            Item item = new Item("FOOD_TUNA", "Tuna", "img.png", ItemEffectType.FULLNESS, 30.0);
            CatService.addItem(cat, item);
            assertTrue(cat.getItems().contains(item));
        }

        @Test
        void useItem_appliesEffectAndRemovesFromInventory() {
            Item item = new Item("FOOD_TUNA", "Tuna", "img.png", ItemEffectType.FULLNESS, 30.0);
            cat.setFullness(50.0);
            CatService.addItem(cat, item);

            boolean result = CatService.useItem(cat, item);

            assertTrue(result);
            assertFalse(cat.getItems().contains(item));
            assertEquals(80.0, cat.getFullness(), 0.001);
        }

        @Test
        void useItem_itemNotInInventory_returnsFalse() {
            Item item = new Item("FOOD_TUNA", "Tuna", "img.png", ItemEffectType.FULLNESS, 30.0);
            assertFalse(CatService.useItem(cat, item));
        }

        @Test
        void removeItem_removesWithoutApplyingEffect() {
            Item item = new Item("FOOD_TUNA", "Tuna", "img.png", ItemEffectType.FULLNESS, 30.0);
            cat.setFullness(50.0);
            CatService.addItem(cat, item);

            CatService.removeItem(cat, item);

            assertFalse(cat.getItems().contains(item));
            assertEquals(50.0, cat.getFullness(), 0.001); // stat unchanged
        }
    }
}
