package com.codexpert;

import java.util.ArrayList;

public class ListTheme extends ArrayList<Theme> {
    public ListTheme() {
        add(new Theme(R.drawable.code,"Dangers", "Tous sur les dangers", "https://raw.githubusercontent.com/benandneuts/codexpert/master/questions.json", true));
        add(new Theme(R.drawable.code,"Classique", "Tous les thèmes !", "https://raw.githubusercontent.com/benandneuts/codexpert/master/questions.json", false));
        add(new Theme(R.drawable.code,"Panneaux", "Tous sur les panneaux !", "https://raw.githubusercontent.com/benandneuts/codexpert/master/questions.json", true));
    }
}
