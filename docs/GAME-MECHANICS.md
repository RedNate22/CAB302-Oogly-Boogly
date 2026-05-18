# MathCat Game Mechanics

## Cat Stats

| Stat | Range | Description |
|------|-------|-------------|
| Happiness | 0-100 | Cat's emotional state |
| Fullness | 0-100 | How well fed the cat is |
| Energy | 0-100 | Spent on math challenges to earn rewards |

---

## Stat Decay (ticks every minute)

| Stat | Change per tick | Condition |
|------|----------------|-----------|
| Happiness | -0.07 | Always |
| Happiness | -0.14 (extra) | When Fullness ≤ 25 (total -0.21/min) |
| Fullness | -0.07 | Always |
| Energy | +(Fullness / 100) * 1.0 | Always, up to daily cap |

At base rates, both Happiness and Fullness hit 0 after 24 hours of no interaction (not counting the hunger penalty).

**Energy daily cap:** Once 100 Energy has been gained in a day, Energy stops regenerating until the next day.

**Example:** A cat at 50 Fullness regenerates `0.5 * 1.0 = 0.5 Energy` per tick.

Decay applies both online (via the scheduler) and offline (calculated on login using `lastSaved` timestamp).

---

## Math Challenges

### Gameplay Flow

1. User completes a math challenge
2. The cat gains **+1 Happiness** regardless of energy
3. If the cat has enough energy for the difficulty (Easy: 5, Medium: 10, Hard: 20), that energy is spent and XP + item rewards are given
4. If the cat does not have enough energy, no energy is spent and no XP or items are awarded

Energy does not gate whether the user can attempt a problem, only whether they are rewarded.

---

## XP Rewards

### Base XP by Difficulty

| Difficulty | Base XP |
|-----------|---------|
| EASY | 10 XP |
| MEDIUM | 20 XP |
| HARD | 35 XP |

### XP Bonuses (additive)

| Bonus | Condition | XP |
|-------|-----------|-----|
| No AI used | AI chatbot not used on this problem | +5 XP |
| Happy cat | Cat Happiness > 75 | +5 XP |
| Well fed | Cat Fullness > 25 | +3 XP |

**Max XP per problem:** Base + all bonuses (e.g. HARD with all bonuses = 35 + 13 = 48 XP)

### Problems to Reach Level 10 (from Level 1)

| Difficulty | No bonuses | Full bonuses |
|-----------|-----------|-------------|
| EASY | ~410 | ~178 |
| MEDIUM | ~205 | ~124 |
| HARD | ~117 | ~85 |

---

## Level System

XP thresholds to reach each level:

| Level | XP Required |
|-------|------------|
| 1 | 0 (starting level) |
| 2 | 100 |
| 3 | 250 |
| 4 | 500 |
| 5 | 850 |
| 6 | 1300 |
| 7 | 1850 |
| 8 | 2500 |
| 9 | 3250 |
| 10 | 4100 |

Excess XP carries over to the next level. Max level is 10.

---

## Item Rewards

### Item Drop Rates by Difficulty

| Difficulty | Drop Chance |
|-----------|------------|
| EASY | 15% |
| MEDIUM | 25% |
| HARD | 40% |

### Drop Trigger

- Each energy-rewarded correct answer rolls against the difficulty's drop chance
- If the roll succeeds, 1 item is chosen randomly from the item catalog
- Expected drops per 30-question pool: EASY ~5, MEDIUM ~8, HARD ~12

### Item Quality (Out of Scope for now)

Higher difficulties will eventually have a weighted chance of dropping better item variants. For now all items are treated equally regardless of difficulty.

