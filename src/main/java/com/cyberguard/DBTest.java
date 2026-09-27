/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cyberguard;
import java.sql.Connection;

/**
 *
 * @author SEJAL TIWARI
 */
public class DBTest {
  


    public static void main(String[] args) {

        Connection con = DBConnection.getConnection();

        if (con != null) {
            System.out.println("Database Connection Successful!");
        } else {
            System.out.println("Database Connection Failed!");
        }
    }
}

