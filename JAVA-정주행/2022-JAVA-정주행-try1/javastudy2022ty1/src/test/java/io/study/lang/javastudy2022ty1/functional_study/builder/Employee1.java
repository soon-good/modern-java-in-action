package io.study.lang.javastudy2022ty1.functional_study.builder;

import java.math.BigDecimal;

public class Employee1 {
    String id;
    String name;
    BigDecimal salary;

    public Employee1(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.salary = builder.salary;
    }

    public static Builder builder(String id, String name){
        return new Builder(id, name);
    }

    public static class Builder{
        private String id;
        private String name;
        private BigDecimal salary;

        public Builder(String id, String name){
            this.id = id;
            this.name = name;
        }

        public Builder(String id, String name, BigDecimal salary){
            this.id = id;
            this.name = name;
            this.salary = salary;
        }

        public Builder withId(String id){
            this.id = id;
            return this;
        }

        public Builder withName(String name){
            this.name = name;
            return this;
        }

        public Builder withSalary(BigDecimal salary){
            this.salary = salary;
            return this;
        }

        public Employee1 build(){
            return new Employee1(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee1{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", salary=").append(salary);
        sb.append('}');
        return sb.toString();
    }
}
