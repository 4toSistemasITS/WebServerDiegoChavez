/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.model.Area;
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
@Path("com.model.materia")
public class MateriaFacadeREST extends AbstractFacade<Materia> {

    @PersistenceContext(unitName = "PracticaWeb2PU")
    private EntityManager em;
    
    @EJB
    AreaFacadeREST areaFacadeREST;

    public MateriaFacadeREST() {
        super(Materia.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Materia entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Materia entity) {
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
    public Materia find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Materia> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Materia> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public String crear(@FormParam("nombre_materia")String nombre_materia,@FormParam("idarea")int idarea){
        String mensaje="{\"exitoso\":false}";
        Area a=areaFacadeREST.find(idarea);
        try{
            if (crear1(nombre_materia)==null){
                create(new Materia(nombre_materia, false, a));
                mensaje="{\"exitoso\":true}";
            } 
        }catch(Exception ex){      
        }
        return mensaje;
    }
    
    @POST
    @Path("consultarIdarea")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Materia> consultarValidos(@FormParam("idarea") int idarea) {
        List<Materia> retorno=obtenerPorIdarea(idarea);
        return retorno;
    }
    
    @POST
    @Path("editar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String editar(@FormParam ("idmateria")int idestudiante,@FormParam("nombre_materia")String nombre_materia,@FormParam("idarea")int idarea){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Materia ma=BuscarPorId(idestudiante);
        Area a=areaFacadeREST.find(idarea);
        if (ma!=null){
            if (!(nombre_materia.equals("") ||idarea==0)){
               ma.setNombreMateria(nombre_materia);
               ma.setIdarea(a);
                try{
                    edit(ma);
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
    @Path("eliminar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String eliminar(@FormParam ("idmateria")int idmateria){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Materia ma=BuscarPorId(idmateria);
        if (ma!=null){
            ma.setEliminado(true);
            edit(ma);
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
    
    public Materia crear1(String nombre_materia){
        Materia mat= null;
        TypedQuery<Materia>qry;
        qry=getEntityManager().createQuery("SELECT m FROM Materia m WHERE m.nombreMateria = :nombreMateria", Materia.class);
        qry.setParameter("nombreMateria", nombre_materia);
        try {
            
            return qry.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
   
    
    List<Materia>obtenerPorIdarea(int idarea){
        TypedQuery<Materia> qry;
        qry=getEntityManager().createQuery("SELECT m FROM Materia m WHERE m.idarea.idarea = :idarea", Materia.class);
        qry.setParameter("idarea", idarea);
        try{
            return qry.getResultList();
        }catch(NoResultException e){
            return null;
        }
        
    }
    
    public Materia BuscarPorId(int idmateria){
        TypedQuery<Materia>qry;
        qry=getEntityManager().createQuery("SELECT m FROM Materia m WHERE m.idmateria = :idmateria and m.eliminado = :eliminado", Materia.class);
        qry.setParameter("idmateria", idmateria);
        qry.setParameter("eliminado", false);
        try {
            return qry.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
}
