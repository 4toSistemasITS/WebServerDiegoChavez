/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.model.Empresa;
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
@Path("com.model.empresa")
public class EmpresaFacadeREST extends AbstractFacade<Empresa> {

    @PersistenceContext(unitName = "PracticaWeb2PU")
    private EntityManager em;

    public EmpresaFacadeREST() {
        super(Empresa.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Empresa entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Empresa entity) {
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
    public Empresa find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Empresa> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Empresa> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public String crear(@FormParam("nombreEmpresa")String nombre_empresa){
        String mensaje="{\"exitoso\":false}";
        try{
            if (crear1(nombre_empresa)==null){
                create(new Empresa(nombre_empresa, false));
                mensaje="{\"exitoso\":true}";
            } 
        }catch(Exception ex){      
        }
        return mensaje;
    }
    
    @POST
    @Path("consultarIdempresa")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Empresa> consultarValidos(@FormParam("idempresa") int idempresa) {
        List<Empresa> retorno=obtenerPorIdempresa(idempresa);
        return retorno;
    }
    
    @POST
    @Path("editar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String editar(@FormParam ("idempresa")int idempresa,@FormParam("nombre_empresa")String nombre_empresa){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Empresa e=BuscarPorId(idempresa);
        
        if (e!=null){
            if (!(nombre_empresa.equals("") )){
               e.setNombreEmpresa(nombre_empresa);
               
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
    @Path("eliminar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String eliminar(@FormParam ("idempresa")int idempresa){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Empresa e=BuscarPorId(idempresa);
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
    
    public Empresa crear1(String nombreEmpresa){
        Empresa e= null;
        TypedQuery<Empresa>qry;
        qry=getEntityManager().createQuery("SELECT e FROM Empresa e WHERE e.nombreEmpresa = :nombreEmpresa", Empresa.class);
        qry.setParameter("nombreEmpresa", nombreEmpresa);
        try {
            
            return qry.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    List<Empresa>obtenerPorIdempresa(int idempresa){
        TypedQuery<Empresa> qry;
        qry=getEntityManager().createQuery("SELECT e FROM Empresa e WHERE e.idempresa = :idempresa", Empresa.class);
        qry.setParameter("idempresa", idempresa);
        try{
            return qry.getResultList();
        }catch(NoResultException e){
            return null;
        }
        
    }
    
    public Empresa BuscarPorId(int idempresa){
        TypedQuery<Empresa>qry;
        qry=getEntityManager().createQuery("SELECT e FROM Empresa e WHERE e.idempresa = :idempresa and e.eliminado = :eliminado", Empresa.class);
        qry.setParameter("idempresa", idempresa);
        qry.setParameter("eliminado", false);
        try {
            return qry.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
