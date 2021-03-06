/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.examproject.projects;

import app.examproject.auth.User;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author marti
 */
@Entity
@Data @EqualsAndHashCode(callSuper = false)
/*
@NamedQuery(name = "MediaObject.findAllMediaObjects",
        query = "select m from MediaObjects m")
*/
public class MediaObject extends AbstractDomain {
    public static final String FIND_ALL_MEDIA_OBJECTS = "MediaObject.findAllMediaObjects";
    @Id
    String id;
    
    String name;
    
    long filesize;
    String mimeType;
    
    @JoinColumn(nullable = false)
    @ManyToOne
    User owner;

    protected MediaObject() {
        super();
    }
    
    public MediaObject(String id, User owner) {
        this();
        this.id = id;
        this.owner = owner;
    }

    public MediaObject(String id, User owner, String name, long filesize, String mimetype) {
        this();
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.filesize = filesize;
        this.mimeType = mimetype;
    }
}