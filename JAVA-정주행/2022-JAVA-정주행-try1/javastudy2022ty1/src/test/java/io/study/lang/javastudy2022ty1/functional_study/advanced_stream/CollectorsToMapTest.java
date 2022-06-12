package io.study.lang.javastudy2022ty1.functional_study.advanced_stream;

import lombok.Getter;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorsToMapTest {
    @Test
    public void TEST_COLLECTORS_TO_MAP_1(){
        Map<String, String> map1 = Stream.of("Apple", "Banana", "Cherry")
                .collect(Collectors.toMap(x -> x, x -> "문자열의 길이 = " + x.length()));

        System.out.println("map1 = " + map1);

        // x->x 는 Function.identity() 로 바꿔서 쓸수 있다.
        Map<String, String> map2 = Stream.of("Apple", "Banana", "Cherry")
                .collect(Collectors.toMap(x -> x, x -> "문자열의 길이 = " + x.length()));

        System.out.println("map2 = " + map2);
    }

    @Test
    public void TEST_COLLECTORS_TO_MAP_2(){
        Map<String, Employee> employeeMap1 = sampleEmployeeList().stream()
                .collect(Collectors.toMap(v -> v.getId(), v -> v));

        System.out.println("employeeMap1 = " + employeeMap1);

        Map<String, Employee> employeeMap2 = sampleEmployeeList().stream()
                .collect(Collectors.toMap(Employee::getId, Function.identity()));

        System.out.println("employeeMap2 = " + employeeMap2);
    }

    @Test
    public void TEST_COLLECTORS_TO_MAP_3(){
        Map<String, JobType> idJobTypeMap1 = sampleEmployeeList()
                .stream().collect(Collectors.toMap(v-> v.getId(), v-> v.getJobType()));

        System.out.println("idJobTypeMap1 = " + idJobTypeMap1);

        Map<String, JobType> idJobTypeMap2 = sampleEmployeeList()
                .stream().collect(Collectors.toMap(Employee::getId, Employee::getJobType));

        System.out.println("idJobTypeMap2 = " + idJobTypeMap2);
    }


    enum JobType{
        DEVELOPER, MANAGER, MARKETER
    }

    @Getter
    @ToString
    class Employee{
        private String id;
        private String name;
        private BigDecimal salary;
        private JobType jobType;

        public Employee(String id, String name, BigDecimal salary, JobType jobType){
            this.id = id;
            this.name = name;
            this.salary = salary;
            this.jobType = jobType;
        }
    }

    public List<Employee> sampleEmployeeList(){
        return Arrays.asList(
            new Employee(UUID.randomUUID().toString(), "고든", BigDecimal.valueOf(600000000), JobType.DEVELOPER),
            new Employee(UUID.randomUUID().toString(), "케인", BigDecimal.valueOf(500000), JobType.MANAGER),
            new Employee(UUID.randomUUID().toString(), "웨인", BigDecimal.valueOf(500001), JobType.MARKETER),
            new Employee(UUID.randomUUID().toString(), "주드", BigDecimal.valueOf(500001), JobType.DEVELOPER)
        );
    }
}
