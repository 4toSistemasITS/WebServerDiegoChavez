/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.model.Estudiante;
import com.model.Materia;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author DELL
 */
@Stateless
@Path("com.model.estudiante")
public class EstudianteFacadeREST extends AbstractFacade<Estudiante> {

    @PersistenceContext(unitName = "PracticaWeb2PU")
    private EntityManager em;
    
    @EJB
    MateriaFacadeREST materiaFacadeREST;
    
    public EstudianteFacadeREST() {
        super(Estudiante.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Estudiante entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Estudiante entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Estudiante find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Estudiante> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Estudiante> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @POST
    @Path("Crear")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String crear(@FormParam("nombres")String nombres,@FormParam("idmateria")int idmateria){
        String mensaje="{\"exitoso\":false}";
        Materia m=materiaFacadeREST.find(idmateria);
        try{
            if (crear1(nombres)==null){
                create(new Estudiante(nombres, false, m));
                mensaje="{\"exitoso\":true}";
            } 
        }catch(Exception ex){      
        }
        return mensaje;
    }
    
    @POST
    @Path("editar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String editar(@FormParam ("idestudiante")int idestudiante,@FormParam("nombres")String nombres,@FormParam("idmateria")int idmateria){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Estudiante e=BuscarPorId(idestudiante);
        Materia m=materiaFacadeREST.find(idmateria);
        if (e!=null){
            if (!(nombres.equals("") ||idmateria==0)){
               e.setNombres(nombres);
               e.setIdmateria(m);
                try{
                    edit(e);
                    mensaje="{\"exitoso\":true";
                }catch(Exception ex){
                    mensaje+="\"Excepcion en base\"";
                } 
            }else{
                mensaje+="\"No se ingreso datos validos\"";
            }
              
        }else{
            mensaje+="\"Datos no correctos\"";
        }
        
        mensaje+="}";
        return mensaje;
    }
    
    
    @POST
    @Path("consultarIdmateria")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Estudiante> consultarValidos(@FormParam("idmateria") int idmateria) {
        List<Estudiante> retorno=obtenerPorIdempleado(idmateria);
        return retorno;
    }
    
    @POST
    @Path("eliminar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String eliminar(@FormParam ("idestudiante")int idestudiante){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Estudiante e=BuscarPorId(idestudiante);
        if (e!=null){
            e.setEliminado(true);
            edit(e);
            mensaje="{\"exitoso\":true";
        }else{
            mensaje+="\"Datos no correctos\"";
        }
        
        mensaje+="}";
        return mensaje;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Estudiante crear1(String nombres){
        Estudiante e= null;
        TypedQuery<Estudiante>qry;
        qry=getEntityManager().createQuery("SELECT e FROM Estudiante e WHERE e.nombres = :nombres", Estudiante.class);
        qry.setParameter("nombres", nombres);
        try {
            
            return qry.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    public Estudiante BuscarPorId(int idestudiante){
        TypedQuery<Estudiante>qry;
        qry=getEntityManager().createQuery("SELECT e FROM Estudiante e WHERE e.idestudiante = :idestudiante and e.eliminado = :eliminado", Estudiante.class);
        qry.setParameter("idestudiante", idestudiante);
        qry.setParameter("eliminado", false);
        try {
            return qry.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    List<Estudiante>obtenerPorIdempleado(int idmateria){
        TypedQuery<Estudiante> qry;
        qry=getEntityManager().createQuery("SELECT e FROM Estudiante e WHERE e.idmateria.idmateria = :idmateria", Estudiante.class);
        qry.setParameter("idmateria", idmateria);
        try{
            return qry.getResultList();
        }catch(NoResultException e){
            return null;
        }
        
    }
    
}
