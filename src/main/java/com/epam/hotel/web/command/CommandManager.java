package com.epam.hotel.web.command;

import com.epam.hotel.web.command.impl.*;
import com.epam.hotel.web.command.impl.admin.*;
import com.epam.hotel.web.command.impl.user.*;

import java.util.HashMap;
import java.util.Map;

/**
 * This singleton class contains all the commands bound with {@link CommandName}
 *
 * @author Artsem Lashuk
 *
 * @see Command
 */

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
        commands.put(CommandName.PROFILE, new Profile());
        commands.put(CommandName.BOOK, new BookCommand());
        commands.put(CommandName.ORDER_DETAILS, new OrderDetailsPageCommand());
        commands.put(CommandName.CONTROL, new ControlPage());
        commands.put(CommandName.UPDATE_ORDER_STATUS, new UpdateOrderStatus());
        commands.put(CommandName.SEARCH_USER_BY_ORDER, new SearchUserByOrder());
        commands.put(CommandName.CHANGE_ORDER, new EditOrderForm());
        commands.put(CommandName.CHANGE_ORDER_RESULT, new EditOrderSearchResult());
        commands.put(CommandName.CHANGE_ORDER_RESULT_PAGE, new EditOrderSearchResultPage());
        commands.put(CommandName.EDIT_BOOK, new EditOrderExecution());
        commands.put(CommandName.SEARCH_ORDER_BY_NAME, new SearchOrderByName());
        commands.put(CommandName.SEARCH_ORDER_BY_PHONE, new SearchOrderByPhone());
        commands.put(CommandName.REVIEWS_PAGE, new ReviewsPageCommand());
        commands.put(CommandName.LEAVE_REVIEW, new LeaveReviewCommand());
        commands.put(CommandName.SEARCH_USER_BY_ID, new SearchUserByID());
        commands.put(CommandName.SUCCESS_REVIEW_PAGE, new SuccessReviewPageCommand());
        commands.put(CommandName.REVIEW_MODERATION, new ReviewsModeration());
        commands.put(CommandName.UPDATE_REVIEW_STATUS, new UpdateReviewStatus());
        commands.put(CommandName.CHANGE_ORDER_CONGRATS_PAGE, new EditOrderCongratsPage());
        commands.put(CommandName.ABOUT_PAGE, new AboutPageCommand());
    }

    /**
     * @return instance of {@link CommandManager}
     */
    public static CommandManager getInstance() {
        return instance;
    }

    /**
     * Get command according to {@link CommandName}
     * @param commandName String from request with the name of the command.
     * @return {@link Command} implementation
     */
    public Command getCommand(String commandName){
        CommandName command=CommandName.valueOf(commandName.toUpperCase());
        return commands.get(command);
    }
}
