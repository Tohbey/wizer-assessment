package com.example.wizerassessment.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Books extends BaseEntity{
    public Books(Long id){
        super(id);
    }
    private String name;
    private String author;

    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
