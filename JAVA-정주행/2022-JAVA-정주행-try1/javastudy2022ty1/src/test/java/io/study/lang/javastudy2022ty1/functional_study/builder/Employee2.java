package io.study.lang.javastudy2022ty1.functional_study.builder;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class Employee2 {
    private String id;
    private String name;
    private BigDecimal salary;
    private String phoneNumber;
    private Integer nbaFinalCount;

    public Employee2(Builder builder){
        id = builder.id;
        name = builder.name;
        salary = builder.salary;
        phoneNumber = builder.phoneNumber;
        nbaFinalCount = builder.nbaFinalCount;
    }

    public static Builder builder(String id, String name){
        return new Builder(id, name);
    }

    public static class Builder{
        private String id;
        private String name;
        public BigDecimal salary;
        public String phoneNumber;
        public Integer nbaFinalCount;

        public Builder(String id, String name){
            this.id = id;
            this.name = name;
        }

        public Builder with(Consumer<Builder> builder){
            builder.accept(this);
            return this;
        }

        public Employee2 build(){
            return new Employee2(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee2{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", salary=").append(salary);
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", nbaFinalCount=").append(nbaFinalCount);
        sb.append('}');
        return sb.toString();
    }
}
