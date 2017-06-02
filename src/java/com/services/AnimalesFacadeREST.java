/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.model.Animales;
import java.util.List;
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
@Path("com.model.animales")
public class AnimalesFacadeREST extends AbstractFacade<Animales> {

    @PersistenceContext(unitName = "PracticaWeb2PU")
    private EntityManager em;

    public AnimalesFacadeREST() {
        super(Animales.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Animales entity) {
        super.create(entity);
    }
    
    

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Animales entity) {
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
    public Animales find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Animales> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Animales> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @POST
    @Path("consultarValidos")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Animales> consultarValidos(@FormParam("eliminado") int eliminado) {
        List<Animales> retorno=obtenerPorEliminado(eliminado);
        return retorno;
    }
    
    @POST
    @Path("consultarExtinto")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Animales> consultarExtintos(@FormParam("extincion") int extincion) {
        List<Animales> retorno=obtenerPorEliminado(extincion);
        return retorno;
    }
    
    @POST
    @Path("login")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Animales> login(@FormParam("usuario") String NUsuario,@FormParam("contraseña") String contra,@FormParam("extincion") int extincion) {
        List<Animales> retorno=null;
        if (NUsuario.equals("Diegoch1992")&&contra.equals("diego")){
            retorno=obtenerPorEliminado(extincion);    
        }    
        return retorno;
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    List<Animales>obtenerPorEliminado(int valor){
        TypedQuery<Animales> qry;
        qry=getEntityManager().createQuery("SELECT a FROM Animales a WHERE a.eliminado = :eliminado", Animales.class);
        qry.setParameter("eliminado", valor);
        try{
            return qry.getResultList();
        }catch(NoResultException e){
            return null;
        }
        
    }
    
    List<Animales>obtenerExtincion(int valor){
        TypedQuery<Animales> qry;
        qry=getEntityManager().createQuery("SELECT a FROM Animales a WHERE a.extincion = :extincion", Animales.class);
        qry.setParameter("extincion", valor);
        try{
            return qry.getResultList();
        }catch(NoResultException e){
            return null;
        }
    }
    
    
    
    
    
}
