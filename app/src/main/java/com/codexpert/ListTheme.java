package com.codexpert;

import java.util.ArrayList;

public class ListTheme extends ArrayList<Theme> {
    public ListTheme() {
        add(new Theme(0,"Classique", "Tous les thèmes !", "https://raw.githubusercontent.com/benandneuts/codexpert/master/questions.json", true));
        add(new Theme(0,"Classique", "Tous les thèmes !", "https://raw.githubusercontent.com/benandneuts/codexpert/master/questions.json", false));
    }
}
