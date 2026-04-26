package com.mathcat.mathcat.dao;

import com.mathcat.mathcat.models.Cat;
import java.util.ArrayList;

/**
 * 
 */
public class CatDAO {
    private static ArrayList<Cat> cats = new ArrayList<>();
    private static int nextId = 1;

    /**
     * Saves a cat. If the cat has no ID, assigns one and adds it. If the cat already has an ID,
     * updates the existing entry.
     * 
     * @param cat the cat to save
     */
    public static void save(Cat cat) {
        if (cat.getCatId() == 0) {
            cat.setCatId(nextId++);
            cats.add(cat);
        }

        else {
            for (int i = 0; i < cats.size(); i++) {
                if (cats.get(i).getCatId() == cat.getCatId()) {
                    cats.set(i, cat);
                    break;
                }
            }
        }
    }

    /**
     * Retrieves the cat associated with the given user ID.
     * 
     * @param userId the ID of the owning user
     * @return the cat belonging to the user, or null if not found
     */
    public static Cat load(int userId) {
        for (Cat cat : cats) {
            if (cat.getUserId() == userId) {
                return cat;
            }
        }
        return null;
    }

    /**
     * Deletes the cat with the given ID.
     * 
     * @param catId the ID of the cat to delete
     */
    public static void delete(int catId) {
        cats.removeIf(cat -> cat.getCatId() == catId);
    }


}
