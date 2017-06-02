/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.model.Vehiculo;
import static com.model.Vehiculo_.color;
import static com.model.Vehiculo_.marca;
import static com.model.Vehiculo_.modelo;
import static com.model.Vehiculo_.placa;
import java.util.Date;
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
@Path("com.model.vehiculo")
public class VehiculoFacadeREST extends AbstractFacade<Vehiculo> {

    @PersistenceContext(unitName = "PracticaWeb2PU")
    private EntityManager em;

    public VehiculoFacadeREST() {
        super(Vehiculo.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Vehiculo entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Vehiculo entity) {
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
    public Vehiculo find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Vehiculo> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Vehiculo> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @POST
    @Path("ingresarDatos")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String ingresarDatos(@FormParam ("tipo")String tipo,@FormParam("marca")String marca,@FormParam("eliminado") boolean eliminado,@FormParam("fecha_creacion") Date fecha_Creacion,@FormParam("placa")String placa,@FormParam("modelo")String modelo,@FormParam("color")String color){
        String mensaje="{\"exitoso\":false}";
        try{
            if (controlesPlaca(placa)==null){
                create (new Vehiculo(tipo, marca, false, fecha_Creacion,placa,modelo,color));
                mensaje="{\"exitoso\":true}";
            }
            
        }catch(Exception e){
                
        }
         
        
        
        return mensaje;
    }
    
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Vehiculo controlesPlaca(String placa){
        Vehiculo v= null;
        TypedQuery<Vehiculo>qry;
        qry=getEntityManager().createQuery("SELECT v FROM Vehiculo v WHERE v.placa = :placa", Vehiculo.class);
        qry.setParameter("placa", placa);

        
        try {
            return qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
     public Vehiculo controlesModeloMarca(String marca,String modelo){
        Vehiculo v= null;
        TypedQuery<Vehiculo>qry;
        qry=getEntityManager().createQuery("SELECT v FROM Vehiculo v WHERE v.marca = :marca and  v.modelo = :modelo", Vehiculo.class);
        qry.setParameter("marca", marca);
        qry.setParameter("modelo", modelo);

        try {
            return qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
     
     public Vehiculo controlesColor(String color){
        Vehiculo v= null;
        TypedQuery<Vehiculo>qry;
        qry=getEntityManager().createQuery("SELECT v FROM Vehiculo v WHERE v.color = :color", Vehiculo.class);
        qry.setParameter("color", color);

        try {
            return qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
     
     
    
}
