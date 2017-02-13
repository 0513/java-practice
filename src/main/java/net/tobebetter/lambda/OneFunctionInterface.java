package net.tobebetter.lambda;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static javafx.scene.input.KeyCode.T;

/**
 * 函数接口
 * 对于只一个方法的接口，匿名实时时，lambda表达式可根据参数类型推断出具体接口及使用的方法
 */
public class OneFunctionInterface {

    public static void sortListDesc(List<Integer> numbers){
        Collections.sort(numbers, (x, y) -> y - x);
    }

    /**
     * 从lambda 表达式引用的本地变量必须是最终变量或实际上的最终变量
     */
    public static void getTotal(List<Integer> numbers){
//        long total = 0l;
//        numbers.forEach((number)->total+=number);
    }

    public static void main(String[] args) {
        List input = Arrays.asList(new String[] {"apple", "orange", "pear"});
        input.forEach((v) -> System.out.println(v));
        input.forEach(System.out::println);
    }
}