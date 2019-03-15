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
        commands.put(CommandName.SEARCH_RESULT_PAGE, new SearchResultPageCommand());
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
        commands.put(CommandName.SEARCH_USER_BY_ORDER, new SearchUserByOrderCommand());
        commands.put(CommandName.CHANGE_ORDER, new ChangeOrderCommand());
        commands.put(CommandName.CHANGE_ORDER_RESULT, new ChangeOrderResultCommand());
        commands.put(CommandName.CHANGE_ORDER_RESULT_PAGE, new ChangeOrderResultPageCommand());
        commands.put(CommandName.EDIT_BOOK, new EditBookCommand());
        commands.put(CommandName.SEARCH_ORDER_BY_NAME, new SearchOrderByNameCommand());
        commands.put(CommandName.SEARCH_ORDER_BY_PHONE, new SearchOrderByPhoneCommand());
        commands.put(CommandName.REVIEWS_PAGE, new ReviewsPageCommand());
        commands.put(CommandName.LEAVE_REVIEW, new LeaveReviewCommand());
        commands.put(CommandName.SEARCH_USER_BY_ID, new SearchUserByIDCommand());
        commands.put(CommandName.SUCCESS_REVIEW_PAGE, new SuccessReviewPageCommand());
        commands.put(CommandName.REVIEW_MODERATION, new ReviewModerationCommand());
        commands.put(CommandName.UPDATE_REVIEW_STATUS, new UpdateReviewStatusCommand());
    }

    public static CommandManager getInstance() {
        return instance;
    }

    public Command getCommand(String commandName){
        CommandName command=CommandName.valueOf(commandName.toUpperCase());
        return commands.get(command);
    }
}
