package de.hochschule.require4testing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "testcases")
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Titel ist erforderlich")
    private String title;

    @Column(length = 2000)
    private String precondition;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TestCasePriority priority;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TestCaseType type;

    @ManyToMany
    @JoinTable(
        name = "testcase_requirements",
        joinColumns = @JoinColumn(name = "testcase_id"),
        inverseJoinColumns = @JoinColumn(name = "requirement_id")
    )
    private Set<Requirement> requirements = new HashSet<>();

    @OneToMany(mappedBy = "testCase", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("stepNumber ASC")
    private List<TestStep> steps = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPrecondition() { return precondition; }
    public void setPrecondition(String precondition) { this.precondition = precondition; }
    public TestCasePriority getPriority() { return priority; }
    public void setPriority(TestCasePriority priority) { this.priority = priority; }
    public TestCaseType getType() { return type; }
    public void setType(TestCaseType type) { this.type = type; }
    public Set<Requirement> getRequirements() { return requirements; }
    public void setRequirements(Set<Requirement> requirements) { this.requirements = requirements; }
    public List<TestStep> getSteps() { return steps; }
    public void setSteps(List<TestStep> steps) { this.steps = steps; }
}