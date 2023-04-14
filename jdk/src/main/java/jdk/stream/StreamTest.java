package jdk.stream;

import java.util.List;
import java.util.stream.Collectors;

public class StreamTest {

    /**
     * 从一个整数列表中筛选出所有偶数，并将它们乘以2，然后输出前5个结果。
     */
    public List<Integer> test(List<Integer> nums) {
        List<Integer> collect = nums.stream().filter(num -> {
                    return num % 2 == 0;
                }).map(num -> num * 2).limit(5)
                .collect(Collectors.toList());
        return collect;
    }
}
