package net.tobebetter.lambda;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2017/2/13.
 */
public class OneFunctionInterfaceTest {

    @Test
    public void sortListDesc(){
        List<Integer> numbers = new ArrayList<Integer>();
        numbers.add(1);
        numbers.add(3);
        numbers.add(2);
        OneFunctionInterface.sortListDesc(numbers);
        Assert.assertEquals(new Integer(2), numbers.get(1));
    }


}
