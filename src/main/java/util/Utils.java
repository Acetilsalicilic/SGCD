/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author W10
 */
public class Utils {
    public static boolean isNumeric(String query) {
        try {
            Integer.parseInt(query);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
