# StreamAPI - 정렬 (1)

> Stream API 정렬 기능 및 Comparator 사용예제  

  


# 참고자료

- [Guide to Java 8 Comparator.comparing() - Baeldung](https://www.baeldung.com/java-8-comparator-comparing)

- [stackoverflow - Reverse a comparator in Java 8](https://stackoverflow.com/questions/32995559/reverse-a-comparator-in-java-8)
- [Java 8의 Stream sorted 사용방법 및 예제](https://codechacha.com/ko/java8-stream-sorted/)

# Comparator

- 오름차순
  - Comparator\<T\>.natuaralOrder()
  - Comparing.comparingOOO()
    - Comparator.comparingInt(...), 
    - Comparator.comparingDouble(...), 
    - ...
- 내림차순
  - Comparator\<T\>.reverseOrder()
  - Comparator.comparingIntOOO()
    - Comparator.comparingInt(...).reversed()
    - Comparator.comparingDouble(...).reversed()
    - ...
    - e.g.) Comparator.comparingInt(Employee::getSalary).reversed()



## Comparator.comparingInt(...), comparingDouble(),...

## 오름차순

## 내림차순

```java
Employee e1 = new Employee ("소방관1", 1000);
Employee e2 = new Employee ("소방관2", 2000);
Employee e3 = new Employee ("소방관3", 3000);

List<Employee> list = Arrays.asList(e1, e2, e3);

@Comparator<Employee> employeeSalary_comparator = 
  Comparator.comaparingInt(Employee::getSalary).reversed();

List<Employee> rSortedList = list.stream()
  .sorted(employeeSalary_comparator)
  .collect(Collectors.toList());
```

