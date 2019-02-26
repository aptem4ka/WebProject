package com.epam.hotel.web.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

public class ResourceBundleKeys {
    private Enumeration<String> keys= ResourceBundle.getBundle("locale").getKeys();

    public List<String> getKeysByPattern(String pattern){
        List<String> keysByPattern=new ArrayList<>();
        while (keys.hasMoreElements()){
            String x=keys.nextElement();
            if (x.contains(pattern)){
                keysByPattern.add(x);
                System.out.println(x);
            }
        }

        return keysByPattern;
    }
}
