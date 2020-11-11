/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.examproject.workhour;

import app.examproject.auth.User;
import app.examproject.projects.Project;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author marti
 */
@Entity
@Data @EqualsAndHashCode(exclude = {"seller"}, callSuper = false)
@AllArgsConstructor

@NamedQuery(name = "WorkHourEntity.findAllWorkHours",
        query = "select w from WorkHourEntity w")
  
public class WorkHourEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int entityId;
    
    @ManyToOne
    @JoinColumn(name="employee", referencedColumnName = "userid")
    User employee;
    
    @ManyToOne
    @JoinColumn(name="project", referencedColumnName = "id")
    Project project;
    
    LocalDateTime workStart;
    
    
    LocalDateTime workEnd;
    
    String comment;
    
    public WorkHourEntity(){
        
    }
    
    public WorkHourEntity(User employee, Project project, LocalDateTime workStart, LocalDateTime workEnd, String comment){
        this.employee = employee;
        this.project = project;
        this.workStart = workStart;
        this.workEnd = workEnd;
        this.comment = comment;
    }
}
