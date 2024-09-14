/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Auth;

import jakarta.servlet.http.HttpSession;

/**
 *
 * @author W10
 */
public class Authorize {
    public static boolean authPermission(HttpSession session, String requiredLevel) {
        if (session == null) {
            return false;
        }
        if (session.getAttribute("auth") != null && session.getAttribute("auth").equals(requiredLevel)) {
            return true;
        } else {
            return false;
        }
    }
}
