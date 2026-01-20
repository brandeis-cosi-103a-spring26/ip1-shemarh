package edu.brandeis.cosi103a.ip1;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for the Dice Rolling Game (App)
 */
public class AppTest 
{
    /**
     * Test that rollDie returns a value between 1 and 6 (inclusive)
     */
    @Test
    public void testRollDieReturnsValidRange()
    {
        for (int i = 0; i < 100; i++) {
            int roll = App.rollDie();
            assertTrue("Die roll should be >= 1", roll >= 1);
            assertTrue("Die roll should be <= 6", roll <= 6);
        }
    }

    /**
     * Test that rollDie can produce all possible values (1-6)
     */
    @Test
    public void testRollDieCoversAllValues()
    {
        boolean[] found = new boolean[7]; // indices 0-6, we use 1-6
        
        // Roll the die 1000 times to get statistical coverage
        for (int i = 0; i < 1000; i++) {
            int roll = App.rollDie();
            found[roll] = true;
        }
        
        // Check that all values 1-6 were found
        for (int i = 1; i <= 6; i++) {
            assertTrue("Die should produce value " + i, found[i]);
        }
    }

    /**
     * Test that rollDie produces different values (not always the same)
     */
    @Test
    public void testRollDieRandomness()
    {
        int firstRoll = App.rollDie();
        boolean differentRollFound = false;
        
        // Roll multiple times to find at least one different value
        for (int i = 0; i < 100; i++) {
            if (App.rollDie() != firstRoll) {
                differentRollFound = true;
                break;
            }
        }
        
        assertTrue("Die rolls should vary, not always produce the same number", differentRollFound);
    }

    /**
     * Test that rollDie returns an integer
     */
    @Test
    public void testRollDieReturnsInteger()
    {
        int roll = App.rollDie();
        assertEquals("Die roll should be an integer type", roll, (int) roll);
    }

    /**
     * Test rollDie with edge case - checking minimum value
     */
    @Test
    public void testRollDieMinimumValue()
    {
        // While we can't guarantee a 1 on the first roll,
        // we can verify that 1 is a valid output
        boolean foundOne = false;
        for (int i = 0; i < 1000; i++) {
            if (App.rollDie() == 1) {
                foundOne = true;
                break;
            }
        }
        assertTrue("Die should eventually produce minimum value 1", foundOne);
    }

    /**
     * Test rollDie with edge case - checking maximum value
     */
    @Test
    public void testRollDieMaximumValue()
    {
        // While we can't guarantee a 6 on the first roll,
        // we can verify that 6 is a valid output
        boolean foundSix = false;
        for (int i = 0; i < 1000; i++) {
            if (App.rollDie() == 6) {
                foundSix = true;
                break;
            }
        }
        assertTrue("Die should eventually produce maximum value 6", foundSix);
    }

    /**
     * Simple test that passes (original test)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue(true);
    }

    /**
     * Test that constants have expected values
     */
    @Test
    public void testGameConstants()
    {
        // These tests document expected game parameters
        // We use reflection to access private static constants
        try {
            int numTurns = (int) App.class.getDeclaredField("NUM_TURNS").get(null);
            int maxRerolls = (int) App.class.getDeclaredField("MAX_REROLLS").get(null);
            int dieSides = (int) App.class.getDeclaredField("DIE_SIDES").get(null);
            
            assertEquals("Each player should get 10 turns", 10, numTurns);
            assertEquals("Players should be able to re-roll up to 2 times", 2, maxRerolls);
            assertEquals("Die should have 6 sides", 6, dieSides);
        } catch (Exception e) {
            fail("Could not access game constants: " + e.getMessage());
        }
    }
}

