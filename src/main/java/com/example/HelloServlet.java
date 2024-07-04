package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/todo")
public class TodoServlet extends HttpServlet {
    // Simulate a simple in-memory task list
    private List<String> tasks = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Display task list
            displayTaskList(response);
        } else if (action.equals("add")) {
            // Add task form
            showAddTaskForm(response);
        } else {
            // Invalid action
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action.equals("add")) {
            // Add a new task
            String taskName = request.getParameter("taskName");
            addTask(taskName);
            displayTaskList(response);
        } else {
            // Invalid action
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void displayTaskList(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Todo List</title></head><body>");
        out.println("<h1>Todo List</h1>");
        out.println("<ul>");
        for (String task : tasks) {
            out.println("<li>" + task + "</li>");
        }
        out.println("</ul>");
        out.println("<a href=\"?action=add\">Add Task</a>");
        out.println("</body></html>");
    }

    private void showAddTaskForm(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Add Task</title></head><body>");
        out.println("<h1>Add Task</h1>");
        out.println("<form method=\"post\">");
        out.println("Task Name: <input type=\"text\" name=\"taskName\"><br>");
        out.println("<input type=\"hidden\" name=\"action\" value=\"add\">");
        out.println("<input type=\"submit\" value=\"Add Task\">");
        out.println("</form>");
        out.println("</body></html>");
    }

    private void addTask(String taskName) {
        tasks.add(taskName);
    }
}
