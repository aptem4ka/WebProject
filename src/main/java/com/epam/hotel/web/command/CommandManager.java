package com.epam.hotel.web.command;

import com.epam.hotel.web.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final CommandManager instance=new CommandManager();
    private final Map<CommandName, Command> commands=new HashMap<>();

    private CommandManager(){
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.REGISTER, new RegisterCommand());
        commands.put(CommandName.ROOM_LIST,new RoomListCommand());
        commands.put(CommandName.LOGOUT,new LogoutCommand());
        commands.put(CommandName.SEARCH_RESULT, new SearchResult());
        commands.put(CommandName.INDEX_PAGE, new IndexPageCommand());
        commands.put(CommandName.REGISTER_PAGE, new RegisterPageCommand());
        commands.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
        commands.put(CommandName.LOGIN_PAGE, new LoginPageCommand());
        commands.put(CommandName.ROOM_INFO, new RoomInfoCommand());
    }

    public static CommandManager getInstance() {
        return instance;
    }

    public Command getCommand(String commandName){
        CommandName command=CommandName.valueOf(commandName.toUpperCase());
        return commands.get(command);
    }
}
