package com.rossi.testspringjava8.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "faculty")
@Data
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "faculty_code")
    private String facultyCode;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "faculty_name")
    private String facultyName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Basic(optional = false)
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private Character status;

    @OneToMany(mappedBy = "faculty")
    private List<Department> departments;

    @PrePersist
    public void onCreate() {
        updateDate = createDate = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        updateDate = new Date();
    }
}
