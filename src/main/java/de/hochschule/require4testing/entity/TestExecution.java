package de.hochschule.require4testing.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "testexecutions")
public class TestExecution {

    public enum Result { PENDING, PASSED, FAILED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "testrun_id")
    private TestRun testRun;

    @ManyToOne
    @JoinColumn(name = "testcase_id")
    private TestCase testCase;

    @ManyToOne
    @JoinColumn(name = "tester_id")
    private User tester;

    @Enumerated(EnumType.STRING)
    private Result result = Result.PENDING;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TestRun getTestRun() { return testRun; }
    public void setTestRun(TestRun testRun) { this.testRun = testRun; }
    public TestCase getTestCase() { return testCase; }
    public void setTestCase(TestCase testCase) { this.testCase = testCase; }
    public User getTester() { return tester; }
    public void setTester(User tester) { this.tester = tester; }
    public Result getResult() { return result; }
    public void setResult(Result result) { this.result = result; }
}