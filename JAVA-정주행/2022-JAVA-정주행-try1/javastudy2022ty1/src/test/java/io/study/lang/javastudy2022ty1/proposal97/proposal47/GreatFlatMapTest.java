package io.study.lang.javastudy2022ty1.proposal97.proposal47;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GreatFlatMapTest {

    @Test
    @DisplayName("map 메서드 사용해보기")
    public void test1(){
        List<Integer> numbers = Stream.of(1, 2, 3, 4).map(x -> x * x).collect(Collectors.toList());
        System.out.println(numbers);
    }

    @Test
    @DisplayName("Stream 안에서 Stream 을 새로 생성하는 예제")
    public void test2(){
        List<Stream<Integer>> streamList = Stream.of(1, 2, 3, 4)
                .map(x -> Stream.of(-x, x, x + 1))
                .collect(Collectors.toList());

        System.out.println(streamList);
    }

    @Test
    @DisplayName("flatMap 사용해보기")
    public void test3(){
        List<Integer> numbers = Stream.of(1, 2, 3, 4)
                .flatMap(x -> Stream.of(-x, x, x + 1))
                .collect(Collectors.toList());

        System.out.println(numbers);
    }

    class Employee {
        private String firstName, lastName;
        private Integer yearSalary;

        public Employee(String firstName, String lastName, Integer yearSalary){
            this.firstName = firstName;
            this.lastName = lastName;
            this.yearSalary = yearSalary;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public Integer getYearSalary() {
            return yearSalary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", yearSalary=" + yearSalary +
                    '}';
        }
    }

    class Manager extends Employee{
        private List<Employee> employeeList;

        public Manager(String firstName, String lastName, Integer yearSalary, List<Employee> employeeList){
            super(firstName, lastName, yearSalary);
            this.employeeList = employeeList;
        }

        public List<Employee> getEmployeeList() {
            return employeeList;
        }

        public void setEmployeeList(List<Employee> employeeList) {
            this.employeeList = employeeList;
        }

        @Override
        public String toString() {
            return "Manager{" +
                    "employeeList=" + employeeList +
                    '}';
        }
    }

    @Test
    @DisplayName("매니저들과 매니저 직속 직원들의 월급을 계산하는 예제")
    public void test4(){
        Employee e1 = new Employee("의조", "황", 200);
        Employee e2 = new Employee("흥민", "손", 333);

        Employee e3 = new Employee("데브라이너", "케빈", 111);
        Employee e4 = new Employee("살라", "모하메드", 222);

        List<Manager> managers = Arrays.asList(
                new Manager("벤투", "파울로", 1000, Arrays.asList(e1, e2)),
                new Manager("위르겐", "클롭", 1100, Arrays.asList(e3, e4))
        );

        int sum = managers.stream()
                .flatMap(m -> Stream.concat(m.getEmployeeList().stream(), Stream.of(m)))
                .distinct()
                .mapToInt(Employee::getYearSalary)
                .sum();

        System.out.println("전체 월급 = " + sum);
    }
}
