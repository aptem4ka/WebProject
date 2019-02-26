package com.epam.hotel;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {

        ResourceBundle bundle=ResourceBundle.getBundle("locale");
        Enumeration<String> enumeration=bundle.getKeys();


        while (enumeration.hasMoreElements()){
            String x=enumeration.nextElement();
            if (x.contains("facilities.famstudio")){
                System.out.println(x);
            }
        }
    }

}
