/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.model.Empleado;
import com.model.Empresa;
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
@Path("com.model.empleado")
public class EmpleadoFacadeREST extends AbstractFacade<Empleado> {
    
    @EJB
    EmpresaFacadeREST empresaFacadeREST;
    
    @PersistenceContext(unitName = "PracticaWeb2PU")
    private EntityManager em;
    
    
    
    public EmpleadoFacadeREST() {
        super(Empleado.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Empleado entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Empleado entity) {
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
    public Empleado find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Empleado> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Empleado> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    public String crear(@FormParam("nombre")String nombre,@FormParam("telefono")String telefono,@FormParam("idempresa")int idempresa){
        String mensaje="{\"exitoso\":false}";
        Empresa e=empresaFacadeREST.find(idempresa);
        try{
            if (crear1(nombre)==null){
                create(new Empleado(nombre, telefono, false, e));
                mensaje="{\"exitoso\":true}";
            } 
        }catch(Exception ex){      
        }
        return mensaje;
    }
    
    @POST
    @Path("editar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String editar(@FormParam ("idempleado")int idempleado,@FormParam("nombre")String nombre,@FormParam("telefono")String telefono,@FormParam("idempresa")int idempresa){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Empleado e=BuscarPorId(idempleado);
        Empresa emp=empresaFacadeREST.find(idempresa);
        if (e!=null){
            if (!(nombre.equals("") || telefono.equals("") || idempresa==0)){
               e.setNombre(nombre);
               e.setTelefono(telefono);
               e.setIdempresa(emp);
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
    @Path("consultarIdempresa")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public List<Empleado> consultarValidos(@FormParam("idempresa") int idempresa) {
        List<Empleado> retorno=obtenerPorIdempleado(idempresa);
        return retorno;
    }
    
    @POST
    @Path("eliminar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String eliminar(@FormParam ("idempleado")int idempleado){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Empleado e=BuscarPorId(idempleado);
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
    
    public Empleado crear1(String nombre){
        Empleado e= null;
        TypedQuery<Empleado>qry;
        qry=getEntityManager().createQuery("SELECT e FROM Empleado e WHERE e.nombre = :nombre", Empleado.class);
        qry.setParameter("nombre", nombre);
        try {
            
            return qry.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    public Empleado BuscarPorId(int idempleado){
        TypedQuery<Empleado>qry;
        qry=getEntityManager().createQuery("SELECT e FROM Empleado e WHERE e.idempleado = :idempleado and e.eliminado = :eliminado", Empleado.class);
        qry.setParameter("idempleado", idempleado);
        qry.setParameter("eliminado", false);
        try {
            return qry.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    List<Empleado>obtenerPorIdempleado(int idempresa){
        TypedQuery<Empleado> qry;
        qry=getEntityManager().createQuery("SELECT e FROM Empleado e WHERE e.idempresa.idempresa = :idempresa", Empleado.class);
        qry.setParameter("idempresa", idempresa);
        try{
            return qry.getResultList();
        }catch(NoResultException e){
            return null;
        }
        
    }
    
}
