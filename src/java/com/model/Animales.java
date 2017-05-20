/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "animales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Animales.findAll", query = "SELECT a FROM Animales a")
    , @NamedQuery(name = "Animales.findByIdAnimal", query = "SELECT a FROM Animales a WHERE a.idAnimal = :idAnimal")
    , @NamedQuery(name = "Animales.findByNombreAnimal", query = "SELECT a FROM Animales a WHERE a.nombreAnimal = :nombreAnimal")
    , @NamedQuery(name = "Animales.findByEspecie", query = "SELECT a FROM Animales a WHERE a.especie = :especie")
    , @NamedQuery(name = "Animales.findByExtincion", query = "SELECT a FROM Animales a WHERE a.extincion = :extincion")
    , @NamedQuery(name = "Animales.findByIdUsuarioRegisto", query = "SELECT a FROM Animales a WHERE a.idUsuarioRegisto = :idUsuarioRegisto")
    , @NamedQuery(name = "Animales.findByFechaHoraRegistro", query = "SELECT a FROM Animales a WHERE a.fechaHoraRegistro = :fechaHoraRegistro")
    , @NamedQuery(name = "Animales.findByVersion", query = "SELECT a FROM Animales a WHERE a.version = :version")
    , @NamedQuery(name = "Animales.findByEliminado", query = "SELECT a FROM Animales a WHERE a.eliminado = :eliminado")
    , @NamedQuery(name = "Animales.findByDetalleModificacion", query = "SELECT a FROM Animales a WHERE a.detalleModificacion = :detalleModificacion")
    , @NamedQuery(name = "Animales.findByIdUsuarioEdicion", query = "SELECT a FROM Animales a WHERE a.idUsuarioEdicion = :idUsuarioEdicion")
    , @NamedQuery(name = "Animales.findByFehaHoraEdicion", query = "SELECT a FROM Animales a WHERE a.fehaHoraEdicion = :fehaHoraEdicion")
    , @NamedQuery(name = "Animales.findByFechaHoraEliminacion", query = "SELECT a FROM Animales a WHERE a.fechaHoraEliminacion = :fechaHoraEliminacion")
    , @NamedQuery(name = "Animales.findByIdUsuarioEliminacion", query = "SELECT a FROM Animales a WHERE a.idUsuarioEliminacion = :idUsuarioEliminacion")})
public class Animales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_animal")
    private Integer idAnimal;
    @Size(max = 100)
    @Column(name = "nombre_animal")
    private String nombreAnimal;
    @Size(max = 100)
    @Column(name = "especie")
    private String especie;
    @Column(name = "extincion")
    private Short extincion;
    @Column(name = "id_usuario_registo")
    private Integer idUsuarioRegisto;
    @Column(name = "fecha_hora_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraRegistro;
    @Column(name = "version")
    private Integer version;
    @Column(name = "eliminado")
    private Short eliminado;
    @Size(max = 45)
    @Column(name = "detalle_modificacion")
    private String detalleModificacion;
    @Column(name = "id_usuario_edicion")
    private Integer idUsuarioEdicion;
    @Column(name = "feha_hora_edicion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fehaHoraEdicion;
    @Column(name = "fecha_hora_eliminacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraEliminacion;
    @Column(name = "id_usuario_eliminacion")
    private Integer idUsuarioEliminacion;

    public Animales() {
    }

    public Animales(Integer idAnimal) {
        this.idAnimal = idAnimal;
    }

    public Integer getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(Integer idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNombreAnimal() {
        return nombreAnimal;
    }

    public void setNombreAnimal(String nombreAnimal) {
        this.nombreAnimal = nombreAnimal;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Short getExtincion() {
        return extincion;
    }

    public void setExtincion(Short extincion) {
        this.extincion = extincion;
    }

    public Integer getIdUsuarioRegisto() {
        return idUsuarioRegisto;
    }

    public void setIdUsuarioRegisto(Integer idUsuarioRegisto) {
        this.idUsuarioRegisto = idUsuarioRegisto;
    }

    public Date getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(Date fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Short getEliminado() {
        return eliminado;
    }

    public void setEliminado(Short eliminado) {
        this.eliminado = eliminado;
    }

    public String getDetalleModificacion() {
        return detalleModificacion;
    }

    public void setDetalleModificacion(String detalleModificacion) {
        this.detalleModificacion = detalleModificacion;
    }

    public Integer getIdUsuarioEdicion() {
        return idUsuarioEdicion;
    }

    public void setIdUsuarioEdicion(Integer idUsuarioEdicion) {
        this.idUsuarioEdicion = idUsuarioEdicion;
    }

    public Date getFehaHoraEdicion() {
        return fehaHoraEdicion;
    }

    public void setFehaHoraEdicion(Date fehaHoraEdicion) {
        this.fehaHoraEdicion = fehaHoraEdicion;
    }

    public Date getFechaHoraEliminacion() {
        return fechaHoraEliminacion;
    }

    public void setFechaHoraEliminacion(Date fechaHoraEliminacion) {
        this.fechaHoraEliminacion = fechaHoraEliminacion;
    }

    public Integer getIdUsuarioEliminacion() {
        return idUsuarioEliminacion;
    }

    public void setIdUsuarioEliminacion(Integer idUsuarioEliminacion) {
        this.idUsuarioEliminacion = idUsuarioEliminacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnimal != null ? idAnimal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Animales)) {
            return false;
        }
        Animales other = (Animales) object;
        if ((this.idAnimal == null && other.idAnimal != null) || (this.idAnimal != null && !this.idAnimal.equals(other.idAnimal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.model.Animales[ idAnimal=" + idAnimal + " ]";
    }
    
}
