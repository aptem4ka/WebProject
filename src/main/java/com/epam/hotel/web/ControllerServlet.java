package com.epam.hotel.web;

import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.command.CommandManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {
    private final String COMMAND="command";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // req.setCharacterEncoding("UTF-8");
        Command command=CommandManager.getInstance().getCommand(req.getParameter(COMMAND));

        command.execute(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // req.setCharacterEncoding("UTF-8");
        Command command=CommandManager.getInstance().getCommand(req.getParameter(COMMAND));

        command.execute(req,resp);

    }
}
