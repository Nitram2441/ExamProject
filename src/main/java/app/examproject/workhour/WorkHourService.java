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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
            @FormParam("pid") int projectId,
            @FormParam("comment") String comment){
        WorkHourEntity workHour;
        LocalDateTime start = LocalDateTime.now();
        
        /* this is how to send time as a string, because you cannot send the localdatetime.
        CharSequence startChar = start.toString();
        LocalDateTime startTwo = LocalDateTime.parse(startChar);
        System.out.println(start.toString());
        System.out.println(startTwo.toString());
*/
        //System.out.println(em.find(Project.class, projectId));
        //System.out.println(em.find(User.class, employeeId));
        //Integer userIdAsInt = Integer.parseInt(employeeId);
        User user = em.find(User.class, employeeId);
        
        //Integer projectIdAsInt = Integer.parseInt(projectId);
        Project project = em.find(Project.class, projectId);
        
        workHour = new WorkHourEntity(user, project, start, null, comment);
        
        em.persist(workHour);

        
        Response r = Response.ok(workHour).build();
        
        
        
        return Response.ok(workHour).build();
    }
    
    @PUT
    @Path("adminedit")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Group.ADMIN, Group.USER})
    public Response edit(
            @FormParam("uid") String employeeId,
            @FormParam("pid") int projectId,
            @FormParam("comment") String comment,
            @FormParam("wid") int workHourId,
            @FormParam("start") String workStart,
            @FormParam("end") String workEnd
    )
            {
        String inputComment = comment;
        WorkHourEntity workHour = em.find(WorkHourEntity.class, workHourId);
        System.out.println(workHour + "This should be work hour + " + inputComment);
        if(inputComment != null){
            workHour.setComment(comment);
            em.merge(workHour);
        }
        
        
        
        return Response.ok(workHour).build();
    }
    
    @PUT
    @Path("endwork")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Group.ADMIN, Group.USER})
    public Response endWork(@FormParam("uid") String employeeId,
            @FormParam("comment") String comment){
            User user = em.find(User.class, employeeId);
            int workHourId = getLastWorkHour(user.getUserid());
            WorkHourEntity workHour = em.find(WorkHourEntity.class, workHourId);
            LocalDateTime end = LocalDateTime.now();
            if(comment != null){
            workHour.setComment(comment);
            em.merge(workHour);
        }
            return Response.ok(workHour).build();
    }

    
    @GET
    @Path("getworkhours")
    //@Produces(MediaType.APPLICATION_JSON))
    public List getAllWorkHours(){
        return em.createNamedQuery("WorkHourEntity.findAllWorkHours", WorkHourEntity.class).getResultList();
    }
    //it will do... for now
    public int getLastWorkHour(String username){
        List<WorkHourEntity> all = em.createNamedQuery("WorkHourEntity.findAllWorkHours", WorkHourEntity.class).getResultList();
        for (WorkHourEntity var : all) 
{           
            if (var.employee.getUserid().equals(username) && var.workEnd == null){
                return var.entityId;
            }
}
        return -1;
    }
    
}
