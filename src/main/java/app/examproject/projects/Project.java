/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.examproject.projects;

import app.examproject.auth.User;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
/*
@NamedQuery(name = "Listing.findById",
        query = "select l from Listing l where l.id = :id")
*/
@NamedQuery(name = "Project.findAllProjects",
        query = "select p from Project p")
        
public class Project extends AbstractDomain{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    //int projectNumber;
    int projectNumber;
            
    String name;
    
    String description;
    
    String customer;
    
    
    @JsonbTypeAdapter(MediaObjectAdapter.class)
    @OneToMany
    List<MediaObject> photos;
    
    @ManyToOne
    @JoinColumn(name="project_manager", referencedColumnName = "userid")
    User manager;
    /*
    @ManyToOne
    @JoinColumn(name="buyer_id", referencedColumnName = "userid")
    User buyer;
    */
    public Project(String name, String description){
        this.name = name;
        this.description = description;

    }
    
    public Project(User manager, String name, String description, String customer){
        this.manager = manager;
        this.name = name;
        this.description = description;
        this.customer = customer;

    }
    
    public void addPhoto(MediaObject photo){
        if(this.photos == null){
            this.photos = new ArrayList<>();
        }
        this.photos.add(photo);
    }
    
    public Project(){
        
    }
}
