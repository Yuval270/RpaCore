package me.yuval270.rpacore.lang;

import me.yuval270.rpacore.util.Chat;

public class Lang {// todo this package will multiple classes that would contain language and config stuff
    public static final String STARTED_NEW_CHARACTER = Chat.translate("&eYou've started character reset process, " +
            "please choose your nickname by typing /nick");
    public static final String TO_CONTINUE = Chat.translate("&eAfter you've chose your nickname, please type /resetcharacter proceed");
    public static final String NOT_IN_PROGRESS = Chat.translate("&eYou must be at the nickname part of the character reset");
    public static final String NICK_NAME_CHOSEN = Chat.translate("&eYou've chosen your nickname");

}
