package de.hochschule.require4testing.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "test_steps")
public class TestStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "step_number")
    private int stepNumber;

    @Column(length = 1000)
    private String action;

    @Column(name = "expected_result", length = 1000)
    private String expectedResult;

    @ManyToOne
    @JoinColumn(name = "testcase_id")
    private TestCase testCase;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getStepNumber() { return stepNumber; }
    public void setStepNumber(int stepNumber) { this.stepNumber = stepNumber; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getExpectedResult() { return expectedResult; }
    public void setExpectedResult(String expectedResult) { this.expectedResult = expectedResult; }
    public TestCase getTestCase() { return testCase; }
    public void setTestCase(TestCase testCase) { this.testCase = testCase; }
}