package de.hochschule.require4testing.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "testcases")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "requirement_id")
    private Requirement requirement;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Requirement getRequirement() { return requirement; }
    public void setRequirement(Requirement requirement) { this.requirement = requirement; }
}
