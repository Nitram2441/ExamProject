/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.examproject.projects;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author marti
 */
@Singleton
@Startup
public class ProjectOnStartup {
        @PersistenceContext
    EntityManager em;
    
    
    @PostConstruct
    public void init() {
        
    }
}
