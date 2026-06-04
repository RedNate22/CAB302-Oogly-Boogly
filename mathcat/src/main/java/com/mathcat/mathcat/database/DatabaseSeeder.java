package com.mathcat.mathcat.database;

import com.mathcat.mathcat.dao.CatDAO;
import com.mathcat.mathcat.dao.ItemDAO;
import com.mathcat.mathcat.dao.UserDAO;
import com.mathcat.mathcat.models.Cat;
import com.mathcat.mathcat.models.Item;
import com.mathcat.mathcat.models.ItemEffectType;
import com.mathcat.mathcat.models.SpriteConstants;
import com.mathcat.mathcat.models.User;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;
import java.util.Random;

/**
 * Seeds the database with a fixed set of test users and their cats on application startup.
 * Each user is only inserted once; subsequent runs skip any username that already exists.
 */
public final class DatabaseSeeder {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseSeeder.class);
    private static final String TEST_PASSWORD = "Password@123";

    private DatabaseSeeder() {}

    /**
     * Inserts the predefined test users and their cats into the database. Any user whose username
     * already exists is silently skipped, making this safe to call on every startup.
     */
    public static void seed() {
        // test1: level 1 (base), no accessory
        seedUser("test1", "test1@mail.com", 1, 0.0, "Percy",
                SpriteConstants.ORANGE_CAT, SpriteConstants.NO_ACCESSORY_SELECTED);

        // test2: level 3, blue bowtie unlocked
        seedUser("test2", "test2@mail.com", 3, 0.0, "Stella",
                SpriteConstants.SIAMESE_CAT, SpriteConstants.BLUE_BOWTIE_HAT);

        // test3: level 5, purple bowtie unlocked
        seedUser("test3", "test3@mail.com", 5, 0.0, "Bernie",
                SpriteConstants.TUXEDO_CAT, SpriteConstants.PURPLE_BOWTIE_HAT);

        // test4: level 8, green bowtie unlocked
        seedUser("test4", "test4@mail.com", 8, 0.0, "Cosmo",
                SpriteConstants.SIAMESE_CAT, SpriteConstants.GREEN_BOWTIE_HAT);

        // test5: level 9, red bowtie
        seedUser("test5", "test5@mail.com", 9, 0.0, "Rosie",
                SpriteConstants.ORANGE_CAT, SpriteConstants.RED_BOWTIE_HAT);

        // test6: level 10 (max), mega cowboy hat unlocked
        seedUser("test6", "test6@mail.com", 10, 0.0, "Theo",
                SpriteConstants.SIAMESE_CAT, SpriteConstants.MEGA_COWBOY_HAT);
    }

    private static void seedUser(String username, String email, int level, double xp,
            String catName, String sprite, String accessory) {
        try {
            if (UserDAO.findByUsername(username) != null) {
                return;
            }
            String hashedPassword = BCrypt.hashpw(TEST_PASSWORD, BCrypt.gensalt());
            UserDAO.insert(new User(username, email, hashedPassword));

            User saved = UserDAO.findByUsername(username);

            Cat cat = new Cat(catName);
            cat.setUserId(saved.getId());
            cat.setCatSprite(sprite);
            cat.setCatAccessory(accessory);
            cat.setLevel(level);
            cat.setXp(xp);
            CatDAO.save(cat);

            seedItems(cat.getCatId());
            LOG.debug("Seeded user: {} (level {})", username, level);
        } catch (SQLException e) {
            LOG.error("Error seeding user: {}", username, e);
        }
    }

    private static void seedItems(int catId) {
        Random rng = new Random();
        for (Item item : ItemDAO.getAll()) {
            if (item.getEffectType() == ItemEffectType.COSMETIC)
                continue;
            if (!rng.nextBoolean())
                continue;
            int quantity = rng.nextInt(3) + 1;
            for (int i = 0; i < quantity; i++) {
                ItemDAO.addItem(catId, item.getItemId());
            }
        }
    }
}
