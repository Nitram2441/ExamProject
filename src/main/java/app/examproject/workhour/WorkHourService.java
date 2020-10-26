/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.examproject.workhour;

import app.examproject.auth.Group;
import app.examproject.auth.User;
import app.examproject.projects.Project;
import java.time.LocalDateTime;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author marti
 */
@Path("workhour")
@Stateless
@DeclareRoles({Group.USER})
public class WorkHourService {
    
    @Context
    SecurityContext sc;
    
    @PersistenceContext
    EntityManager em;
    
    @POST
    @Path("create")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Group.ADMIN, Group.USER})
    public Response create(
            @FormParam("uid") String employeeId,
            @FormParam("pid") String projectId,
            @FormParam("comment") String comment){
        WorkHourEntity workHour;
        LocalDateTime start = LocalDateTime.now();
        //Integer userIdAsInt = Integer.parseInt(employeeId);
        User user = em.find(User.class, employeeId);
        //Integer projectIdAsInt = Integer.parseInt(projectId);
        Project project = em.find(Project.class, projectId);
        
        workHour = new WorkHourEntity(user, project, start, null, comment);
        
        return Response.ok(workHour).build();
        
    }
}
