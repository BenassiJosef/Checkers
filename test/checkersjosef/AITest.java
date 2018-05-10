/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package checkersjosef;

import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author josefbenassi
 */
public class AITest {
    
    public AITest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of whatCanUpBoardPlay method, of class AI.
     */
    @Test
    public void testWhatCanUpBoardPlay() {
        System.out.println("whatCanUpBoardPlay");
        Vector<String> primary = null;
        Vector<String> secondary = null;
        String[] expResult = null;
        String[] result = AI.whatCanUpBoardPlay(primary, secondary);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of whatCanDownBoardPlay method, of class AI.
     */
    @Test
    public void testWhatCanDownBoardPlay() {
        System.out.println("whatCanDownBoardPlay");
        Vector<String> primary = null;
        Vector<String> secondary = null;
        String[] expResult = null;
        String[] result = AI.whatCanDownBoardPlay(primary, secondary);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
