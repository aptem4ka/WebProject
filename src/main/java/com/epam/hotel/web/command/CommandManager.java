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
        commands.put(CommandName.LOGIN, new Login());
        commands.put(CommandName.REGISTER, new Register());
        commands.put(CommandName.ROOM_LIST,new RoomList());
        commands.put(CommandName.LOGOUT,new Logout());
        commands.put(CommandName.SEARCH_RESULT, new SearchResult());
        commands.put(CommandName.SEARCH_RESULT_PAGE, new SearchResultPage());
        commands.put(CommandName.INDEX_PAGE, new IndexPage());
        commands.put(CommandName.REGISTER_PAGE, new RegisterPage());
        commands.put(CommandName.CHANGE_LOCALE, new ChangeLocale());
        commands.put(CommandName.LOGIN_PAGE, new LoginPage());
        commands.put(CommandName.ROOM_INFO, new RoomInfo());
        commands.put(CommandName.PROFILE, new Profile());
        commands.put(CommandName.BOOK, new Book());
        commands.put(CommandName.ORDER_DETAILS, new OrderDetailsPage());
        commands.put(CommandName.CONTROL, new ControlPage());
        commands.put(CommandName.UPDATE_ORDER_STATUS, new UpdateOrderStatus());
        commands.put(CommandName.SEARCH_USER_BY_ORDER, new SearchUserByOrder());
        commands.put(CommandName.CHANGE_ORDER, new EditOrderForm());
        commands.put(CommandName.CHANGE_ORDER_RESULT, new EditOrderSearchResult());
        commands.put(CommandName.CHANGE_ORDER_RESULT_PAGE, new EditOrderSearchResultPage());
        commands.put(CommandName.EDIT_BOOK, new EditOrderExecution());
        commands.put(CommandName.SEARCH_ORDER_BY_NAME, new SearchOrderByName());
        commands.put(CommandName.SEARCH_ORDER_BY_PHONE, new SearchOrderByPhone());
        commands.put(CommandName.REVIEWS_PAGE, new ReviewsPage());
        commands.put(CommandName.LEAVE_REVIEW, new LeaveReview());
        commands.put(CommandName.SEARCH_USER_BY_ID, new SearchUserByID());
        commands.put(CommandName.SUCCESS_REVIEW_PAGE, new SuccessReviewPage());
        commands.put(CommandName.REVIEW_MODERATION, new ReviewsModeration());
        commands.put(CommandName.UPDATE_REVIEW_STATUS, new UpdateReviewStatus());
        commands.put(CommandName.CHANGE_ORDER_CONGRATS_PAGE, new EditOrderCongratsPage());
        commands.put(CommandName.ABOUT_PAGE, new AboutPage());
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
