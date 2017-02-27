package net.tobebetter.lambda;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2017/2/13.
 */
public class LambdaUtilTest {
    private List<Integer> numbers;
    @Test
    public void sortListDesc(){
        LambdaUtil.sortListDesc(numbers);
        Assert.assertEquals(new Integer(2), numbers.get(1));
    }

    @Test
    public void filterList(){
        List<Integer> result = LambdaUtil.filterList(numbers);
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void mapList(){
        List<Integer> result = LambdaUtil.mapList(numbers);
        Assert.assertEquals(new Integer(3), result.get(0));
    }

    @Before
    public void initList(){
        numbers = new ArrayList<Integer>();
        numbers.add(1);
        numbers.add(3);
        numbers.add(2);
    }

}
