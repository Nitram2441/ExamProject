/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.examproject.projects;

import app.examproject.auth.Group;
import app.examproject.auth.User;

import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author marti
 */
@Path("project")
@Stateless
@DeclareRoles({Group.USER})
public class ProjectService {
    
    @Context
    SecurityContext sc;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    @ConfigProperty(name = "photo.storage.path", defaultValue = "projectphotos")
    String photoPath;
    
    @GET
    @Path("test")
    public Response test(){
        Project project = null;
        project = new Project("Navn", "Beskrivelse");
        System.out.println(project);
        em.persist(project);
        return Response.ok(project).build();
    }
    
    
    @GET
    @Path("getprojects")
    //@Produces(MediaType.APPLICATION_JSON))
    public List getAllProjects(){
        return em.createNamedQuery("Project.findAllProjects", Project.class).getResultList();
    }
    
    @POST
    @Path("create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Group.ADMIN})
    public Response addProject(
            @FormDataParam("name") String name,
            @FormDataParam("description") String description,
            FormDataMultiPart multiPart){
        
        MediaObject photo = null;
        Project project = null;
        try{
            
            List<FormDataBodyPart> images = multiPart.getFields("image");
            User user = em.find(User.class, sc.getUserPrincipal().getName());
            project = new Project(name, description);
            if (images != null){
                for(FormDataBodyPart part : images){
                    InputStream is = part.getEntityAs(InputStream.class);
                    ContentDisposition meta = part.getContentDisposition();
                    String pid = UUID.randomUUID().toString();
                    Files.createDirectories(Paths.get(getPhotoPath()));
                    Files.copy(is, Paths.get(getPhotoPath(), pid));
                    
                    photo = new MediaObject(pid, user, meta.getFileName(), meta.getSize(), meta.getType());
                    em.persist(photo);
                    project.addPhoto(photo);
                    
                }
            }
        System.out.println("hei");
        //System.out.println(projectNumber);
        System.out.println(name);
        System.out.println(description);
        System.out.println(photo);
        System.out.println("hei");
        System.out.println(project);
        em.persist(project);
        }
        
        catch(IOException ex){
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }


        return Response.ok(project).build();

    }
    
    private String getPhotoPath() {
            return photoPath;
    }
}
