/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.services;

import com.model.Usuario;
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
@Path("com.model.usuario")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "PracticaWeb2PU")
    private EntityManager em;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Usuario entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Usuario entity) {
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
    public Usuario find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @POST
    @Path("login")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public Usuario login(@FormParam ("usuario")String usuario,@FormParam("password")String password){
        Usuario u=login1(usuario,password);
        return u;
    }
    
    @POST
    @Path("crear")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String crear(@FormParam ("usuario")String usuario,@FormParam("password")String password){
        String mensaje="{\"exitoso\":false}";
        try{
            if (crear1(usuario)==null){
                create (new Usuario(usuario, password, false));
                mensaje="{\"exitoso\":true}";
            }
            
        }catch(Exception e){
                
        }
         
        
        
        return mensaje;
    }
    
    @POST
    @Path("editar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String editar(@FormParam ("idUsuario")int idUsuario,@FormParam("usuario")String usuario,@FormParam("password")String password){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Usuario u=BuscarPorId(idUsuario);
        if (u!=null){
            if (!(password.equals("") || usuario.equals(""))){
               u.setUsuario(usuario);
               u.setPassword(password);
                try{
                    edit(u);
                    mensaje="{\"exitoso\":true";
                }catch(Exception e){
                    mensaje+="\"Excepcion en base\"";
                } 
            }else{
                mensaje+="\"No se ingreso usuario o contraseña valida\"";
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
    public String eliminar(@FormParam ("idUsuario")int idUsuario){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Usuario u=BuscarPorId(idUsuario);
        if (u!=null){
            u.setEliminado(true);
            edit(u);
            mensaje="{\"exitoso\":true";
        }else{
            mensaje+="\"Datos no correctos\"";
        }
        
        mensaje+="}";
        return mensaje;
    }
    
    @POST
    @Path("activar")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
    public String activar(@FormParam ("idUsuario")int idUsuario){
        String mensaje="{\"exitoso\":false,\"motivo\":";
        Usuario u=Activar(idUsuario);
        if (u!=null){
            u.setEliminado(false);
            edit(u);
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
    
    public Usuario login1(String usuario,String password){
        Usuario u= null;
        TypedQuery<Usuario>qry;
        qry=getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario and u.password = :password and  u.eliminado = :eliminado", Usuario.class);
        qry.setParameter("usuario", usuario);
        qry.setParameter("password", password);
        qry.setParameter("eliminado", false);

        try {
            return qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        
    }
    
    public Usuario crear1(String usuario){
        Usuario u= null;
        TypedQuery<Usuario>qry;
        qry=getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario", Usuario.class);
        qry.setParameter("usuario", usuario);
        try {
            return qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Usuario BuscarPorId(int idUsuario){
        TypedQuery<Usuario>qry;
        qry=getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.idusuario = :idusuario and u.eliminado = :eliminado", Usuario.class);
        qry.setParameter("idusuario", idUsuario);
        qry.setParameter("eliminado", false);
        try {
            return qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Usuario Activar(int idUsuario){
        TypedQuery<Usuario>qry;
        qry=getEntityManager().createQuery("SELECT u FROM Usuario u WHERE u.idusuario = :idusuario and u.eliminado = :eliminado", Usuario.class);
        qry.setParameter("idusuario", idUsuario);
        qry.setParameter("eliminado", true);
        try {
            return qry.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
