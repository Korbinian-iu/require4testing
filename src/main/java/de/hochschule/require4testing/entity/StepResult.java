package de.hochschule.require4testing.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "step_results")
public class StepResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "execution_id")
    private TestExecution execution;

    @ManyToOne
    @JoinColumn(name = "step_id")
    private TestStep step;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TestResultStatus status;

    @Column(length = 1000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "tested_by")
    private User testedBy;

    private LocalDateTime recordedAt = LocalDateTime.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TestExecution getExecution() { return execution; }
    public void setExecution(TestExecution execution) { this.execution = execution; }
    public TestStep getStep() { return step; }
    public void setStep(TestStep step) { this.step = step; }
    public TestResultStatus getStatus() { return status; }
    public void setStatus(TestResultStatus status) { this.status = status; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public User getTestedBy() { return testedBy; }
    public void setTestedBy(User testedBy) { this.testedBy = testedBy; }
    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}