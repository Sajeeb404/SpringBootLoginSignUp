package com.example.HomeToHomeSpring.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
@Data

public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Boolean active;

    //    @CreatedDate
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    private Integer createdBy;

    //    @LastModifiedDate
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Integer updatedBy;

    @PrePersist
    public void prePersist() {
        this.active = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseModel baseModel = (BaseModel) o;
        return Objects.equals(id, baseModel.id) && Objects.equals(active, baseModel.active) && Objects.equals(createdAt, baseModel.createdAt) && Objects.equals(createdBy, baseModel.createdBy) && Objects.equals(updatedAt, baseModel.updatedAt) && Objects.equals(updatedBy, baseModel.updatedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, active, createdAt, createdBy, updatedAt, updatedBy);
    }


}
