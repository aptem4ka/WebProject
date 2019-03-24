package com.epam.hotel.web.command.impl;


import com.epam.hotel.entity.User;
import com.epam.hotel.exception.ServiceException;
import com.epam.hotel.service.UserService;
import com.epam.hotel.service.impl.UserServiceImpl;
import com.epam.hotel.web.command.Command;
import com.epam.hotel.web.util.constants.StringConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {


    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private HttpSession session;

    @InjectMocks
    private Login command;

    @Before
    public void init()throws ServiceException {
        MockitoAnnotations.initMocks(this);
        when(req.getSession()).thenReturn(session);
    }

    @Test
    public void loginTest() throws ServletException, IOException, ServiceException {
        when(req.getParameter(StringConstants.EMAIL)).thenReturn("aptem4ka@gmail.com");
        when(req.getParameter(StringConstants.PASSWORD)).thenReturn("LGD802g2");


        command.execute(req, resp);


        verify(req).getParameter(StringConstants.EMAIL);
        verify(req).getParameter(StringConstants.PASSWORD);
        verify(req).getSession();
    }

    @Test
    public void incorrectEmailTest()throws ServletException, IOException{
        when(req.getParameter(StringConstants.EMAIL)).thenReturn("aptem4kmail.com");
        when(req.getParameter(StringConstants.PASSWORD)).thenReturn("LGD802g2");


        command.execute(req, resp);

        verify(req).getParameter(StringConstants.EMAIL);
        verify(req).getParameter(StringConstants.PASSWORD);
        verify(req, never()).getSession();
    }





}
