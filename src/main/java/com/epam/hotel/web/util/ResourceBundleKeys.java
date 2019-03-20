package com.epam.hotel.web.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Artsem Lashuk
 *
 * This class is designed to get keys from resource locale bundle
 *
 * @see Enumeration
 */

public class ResourceBundleKeys {
    /**
     * The enumeration of keys from resource bundle "locale"
     */
    private Enumeration<String> keys= ResourceBundle.getBundle("locale").getKeys();

    /**
     * @param pattern The part of the key name that we want to save
     *                from the enumeration of keys.
     *
     * @return list of keys that matches the pattern
     */
    public List<String> getKeysByPattern(String pattern){

        List<String> keysByPattern=new ArrayList<>();
        while (keys.hasMoreElements()){
            String x=keys.nextElement();
            if (x.contains(pattern)){
                keysByPattern.add(x);
            }
        }

        return keysByPattern;
    }
}
