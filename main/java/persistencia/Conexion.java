package persistencia;

import logica.Contacto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author X4BCD
 */
public class Conexion {

    private int identificacion;
    private String nombre;
    private String apellido;
    private String genero;
    private String tipoIdentificacion;
    private String telefono;
    private String direccion;
    private String correo;

    public boolean actualizarContacto() {
        ConexionBD conexion = new ConexionBD();
        String Sentencia = "UPDATE `contactos` SET nombre='" + this.nombre + "',apellido='" + this.apellido + "',genero='" + this.genero
                + "',tipoIdentificacion='" + this.tipoIdentificacion + "',telefono='" + this.telefono + "',direccion='" + this.direccion + "',correo='" + this.correo
                + "' WHERE identificacion=" + this.identificacion + ";";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(Sentencia)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public List<Contacto> listarContactos() throws SQLException {
        ConexionBD conexion = new ConexionBD();
        List<Contacto> listaContactos = new ArrayList<>();
        String sql = "select * from contactos order by identificacion asc";
        ResultSet rs = conexion.consultarBD(sql);
        Contacto c;
        while (rs.next()) {
            c = new Contacto();
            c.setIdentificacion(rs.getInt("identificacion"));
            c.setNombre(rs.getString("nombre"));
            c.setApellido(rs.getString("apellido"));
            c.setGenero(rs.getString("genero"));
            c.setTipoIdentificacion(rs.getString("tipoIdentificacion"));
            c.setTelefono(rs.getString("telefono"));
            c.setDireccion(rs.getString("direccion"));
            c.setCorreo(rs.getString("correo"));
            listaContactos.add(c);
        }
        conexion.cerrarConexion();
        return listaContactos;
    }

    public boolean guardarContacto() {
        ConexionBD conexion = new ConexionBD();
        String sentencia = "INSERT INTO contactos(identificacion, nombre, apellido, genero, tipoIdentificacion, telefono, direccion, correo) "
                + " VALUES ( '" + this.identificacion + "','" + this.nombre + "',"
                + "'" + this.apellido + "','" + this.genero + "','" + this.tipoIdentificacion + "',"
                + "'" + this.telefono + "','" + this.direccion + "','" + this.correo + "');  ";
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.insertarBD(sentencia)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public boolean borrarContacto(int identificacion) {
        String Sentencia = "DELETE FROM `contactos` WHERE `identificacion`='" + identificacion + "'";
        ConexionBD conexion = new ConexionBD();
        if (conexion.setAutoCommitBD(false)) {
            if (conexion.actualizarBD(Sentencia)) {
                conexion.commitBD();
                conexion.cerrarConexion();
                return true;
            } else {
                conexion.rollbackBD();
                conexion.cerrarConexion();
                return false;
            }
        } else {
            conexion.cerrarConexion();
            return false;
        }
    }

    public Conexion getContacto() throws SQLException {
        ConexionBD conexion = new ConexionBD();
        String sql = "select * from contactos where identificacion='" + this.identificacion + "'";
        ResultSet rs = conexion.consultarBD(sql);
        if (rs.next()) {
            this.identificacion = rs.getInt("identificacion");
            this.nombre = rs.getString("nombre");
            this.apellido = rs.getString("apellido");
            this.genero = rs.getString("genero");
            this.tipoIdentificacion = rs.getString("tipoIdentificacion");
            this.telefono = rs.getString("telefono");
            this.direccion = rs.getString("direccion");
            this.correo = rs.getString("correo");
            conexion.cerrarConexion();
            return this;

        } else {
            conexion.cerrarConexion();
            return null;
        }

    }

    @Override
    public String toString() {
        return "Contacto{" + "identificacion=" + identificacion + ", nombre=" + nombre + ", apellido=" + apellido
                + ", genero=" + genero + ", tipoIdentificacion=" + tipoIdentificacion + ", telefono=" + telefono
                + ", direccion=" + direccion + ", correo=" + correo + '}';
    }

}