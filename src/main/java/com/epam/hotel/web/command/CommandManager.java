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
        commands.put(CommandName.SEARCH_RESULT, new SearchResultCommand());
        commands.put(CommandName.INDEX_PAGE, new IndexPageCommand());
        commands.put(CommandName.REGISTER_PAGE, new RegisterPageCommand());
        commands.put(CommandName.CHANGE_LOCALE, new ChangeLocaleCommand());
        commands.put(CommandName.LOGIN_PAGE, new LoginPageCommand());
        commands.put(CommandName.ROOM_INFO, new RoomInfoCommand());
        commands.put(CommandName.PROFILE, new ProfileCommand());
        commands.put(CommandName.BOOK, new BookCommand());
        commands.put(CommandName.ORDER_DETAILS, new OrderDetailsCommand());
        commands.put(CommandName.CONTROL, new ControlCommand());
        commands.put(CommandName.UPDATE_ORDER_STATUS, new UpdateOrderStatusCommand());
        commands.put(CommandName.GUEST_BOOKING_STATUS, new GuestBookingStatusCommand());
    }

    public static CommandManager getInstance() {
        return instance;
    }

    public Command getCommand(String commandName){
        CommandName command=CommandName.valueOf(commandName.toUpperCase());
        return commands.get(command);
    }
}
