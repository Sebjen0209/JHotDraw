package org.jhotdraw.samples.svg.bdd;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.samples.svg.bdd.GivenDuplicateTest;
import org.jhotdraw.samples.svg.bdd.ThenDuplicateTest;
import org.jhotdraw.samples.svg.bdd.WhenDuplicateTest;
import org.junit.Test;

public class DuplicateBDDTest extends ScenarioTest<GivenDuplicateTest, WhenDuplicateTest, ThenDuplicateTest> {

    @Test
    public void duplicateAnObject(){
        given().userWantsToDuplicate();
        when().userDuplicates();
        then().userGetsADuplicate();
    }
}
