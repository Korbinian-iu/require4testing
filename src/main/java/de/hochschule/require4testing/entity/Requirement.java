package de.hochschule.require4testing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "requirements")
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "req_key", unique = true, length = 20)
    private String reqKey;

    @NotBlank(message = "Titel ist erforderlich")
    private String title;

    @Column(length = 2000)
    private String description;

    @NotNull(message = "Priorität ist erforderlich")
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private MoSCoWPriority priority;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private RequirementStatus status = RequirementStatus.ENTWURF;

    @Column(name = "acceptance_criteria", length = 2000)
    private String acceptanceCriteria;

    @Column(length = 100)
    private String category;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getReqKey() { return reqKey; }
    public void setReqKey(String reqKey) { this.reqKey = reqKey; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public MoSCoWPriority getPriority() { return priority; }
    public void setPriority(MoSCoWPriority priority) { this.priority = priority; }
    public RequirementStatus getStatus() { return status; }
    public void setStatus(RequirementStatus status) { this.status = status; }
    public String getAcceptanceCriteria() { return acceptanceCriteria; }
    public void setAcceptanceCriteria(String acceptanceCriteria) { this.acceptanceCriteria = acceptanceCriteria; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
