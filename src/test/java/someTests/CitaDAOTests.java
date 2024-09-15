/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package someTests;

import DAO.EntityDAOPool;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author W10
 */
public class CitaDAOTests {

    public CitaDAOTests() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @Test
    public void test() {
        EntityDAOPool.init("jdbc:mysql://localhost:3306/lab4", "root", "pass");
        var dao = EntityDAOPool.instance().getCitaDAO();
        System.out.println(dao.getAllCitas(22));
    }
}
