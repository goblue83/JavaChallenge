package com.tw.javachallenge;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.annotation.WebServlet;
import com.google.gson.JsonObject;
import com.tw.javachallenge.DatabaseConnection;


/**
 * Servlet implementation class AppServlet
 */
@WebServlet("/AppServlet")
public class AppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        String id_str = request.getParameter("id");
        int idInt = Integer.parseInt(id_str);
        String message = request.getParameter("message");
        // get number of words in message
        int count = 0;
        char charArray[] = new char[message.length()];
        for (int i = 0; i < message.length();i++) {
        	charArray[i] = message.charAt(i);
        	if (((i > 0) && (charArray[i] != ' ') && (charArray[i-1] == ' ')) || ((charArray[0] != ' ') && (i == 0))) {
        		count++;
        	}
        }

        String countStr ="";
		// send to DB for storage
		// Initialize the database
        Connection con;
		try {
			
			con = DatabaseConnection.initializeDatabase();
			// check if id exists
			PreparedStatement stmt1 = con.prepareStatement("SELECT ID FROM Entries WHERE ID=?");
	        stmt1.setInt(1, idInt);
	        ResultSet rs0 = stmt1.executeQuery();
	        // if result set is 0, ID does not exist. 
	        boolean empty = rs0.next();
		    if (!empty) {
		    	// id does not exist, insert new entry
		        stmt1 = con
		               .prepareStatement("INSERT INTO Entries VALUES(?, ?)");
		        // parameter 1 is id, this is a unique field. Additional entries with same id
		        // will be rejected
		        stmt1.setInt(1, Integer.valueOf(id_str));
		        // parameter 2 is number of words
		        stmt1.setInt(2, count);

		        stmt1.executeUpdate();
		        stmt1.close();
		    }
		    
	        // new request to get total number of words 
	        Statement stmt2 = con.createStatement();
	        String query = "SELECT SUM(Count) FROM Entries";
	        ResultSet rs = stmt2.executeQuery(query);
	        rs.next();
	        countStr = rs.getString(1);
	        // Close remaining connections    
	        stmt2.close();
	        con.close();
	        
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("Count", countStr);

        PrintWriter out1 = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out1.print(jsonObject);
        out1.flush();
	}

}
