package utils;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by ivan on 03/07/2014.
 */
public class NameCorrectnessTest {

    @Test
    public void testAVariableNameCantContainDash()  {
        String toTest = "ima-test";
        assertTrue(!NameCorrectness.format(toTest).contains("-"));
    }

    @Test
    public void testAVariableNameCantContainSlash()  {
        String toTest = "ima/test";
        assertTrue(!NameCorrectness.format(toTest).contains("/"));
    }

    @Test
    public void testAVariableNameCantContainAntiSlash()  {
        String toTest = "ima\\test";
        assertTrue(!NameCorrectness.format(toTest).contains("\\"));
    }

    @Test(expected = NullPointerException.class)
    public void testAVariableNameCantBeNull()  {
        String toTest = null;
        NameCorrectness.format(toTest);
    }
}
