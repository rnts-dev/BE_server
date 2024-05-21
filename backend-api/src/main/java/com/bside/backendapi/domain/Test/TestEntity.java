package com.bside.backendapi.domain.Test;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Setter;

@Entity
@Setter
public class TestEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    public static TestEntity createTest(String text){
        TestEntity testEntity = new TestEntity();
        testEntity.setText(text);
        return testEntity;
    }
}
