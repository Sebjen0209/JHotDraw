package org.jhotdraw.samples.svg.bdd;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class DuplicateBDDTest extends ScenarioTest<GivenDuplicateTest, WhenDuplicateTest, ThenDuplicateTest> {

    @Test
    public void duplicateAnObject(){
        given().userWantsToDuplicate();
        when().userDuplicates();
        then().userGetsADuplicate();
    }
}
