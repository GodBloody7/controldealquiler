
package controlalquiler.accesoadatos;

import java.util.*;
import java.sql.*;
import controlalquiler.entidadesdenegocio.*;

public class ClienteDAL {
    
   static String obtenerCampos() {
        return "c.Id, c.Nombre, c.Apellido, c.RazonSocial, c.Telefono, c.Direccion";
    }
   private static String obtenerSelect(Cliente pCliente) {
        String sql;
        sql = "SELECT ";
        if (pCliente.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             sql += "TOP " + pCliente.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Cliente c");
        return sql;
    }
     private static String agregarOrderBy(Cliente pCliente) {
        String sql = " ORDER BY c.Id DESC";
        if (pCliente.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pCliente.getTop_aux() + " ";
        }
        return sql;
    }
    public static int crear(Cliente pCliente) throws Exception{
        int result;
        String sql;
        
        try(Connection conn = ComunDB.obtenerConexion();){
            sql = "INSERT INTO Cliente(Id,Nombre,Apellido,RazonSocil,Telefono,Direccion) VALUES(?,?,?,?,?,?)";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pCliente.getId());
                ps.setString(2, pCliente.getNombre());
                ps.setString(3, pCliente.getApellido());
                ps.setString(4, pCliente.getRazonsocial());
                ps.setString(5, pCliente.getTelefono());
                ps.setString(6, pCliente.getDireccion());
                result = ps.executeUpdate();
                ps.close();
            }
            catch(SQLException ex){
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex){
            throw ex;
        }
        return result;
    }
     public static int modificar(Cliente pCliente) throws Exception{
        int result;
        String sql;
        
        try(Connection conn = ComunDB.obtenerConexion();){
            sql = "UPDATE Cliente SET Id=?, Nombre=?, Apellido=?, RazonSocial=?, Yelefono=?, Direccion=? WHERE Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pCliente.getId());
                ps.setString(2, pCliente.getNombre());
                ps.setString(3, pCliente.getApellido());
                ps.setString(4, pCliente.getRazonsocial());
                ps.setString(5, pCliente.getTelefono());
                ps.setString(6, pCliente.getDireccion());
                result = ps.executeUpdate();
                ps.close();
            }
            catch(SQLException ex){
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex){
            throw ex;
        }
        return result;
    }
      public static int eliminar(Cliente pCliente) throws Exception{
        int result;
        String sql;
        try(Connection conn = ComunDB.obtenerConexion();){
            sql = "DELETE FROM Cliente WHERE Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pCliente.getId());
                result = ps.executeUpdate();
                ps.close();
            }
            catch(SQLException ex){
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex){
            throw ex;
        }
        return result;
    }
      static int asignarDatosResultSet(Cliente pCliente, ResultSet pResultSet, int pIndex) throws Exception{
        pIndex++;
        pCliente.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pCliente.setNombre(pResultSet.getString(pIndex));
        pIndex++;
        pCliente.setApellido(pResultSet.getString(pIndex));
        pIndex++;
        pCliente.setRazonsocial(pResultSet.getString(pIndex));
        pIndex++;
        pCliente.setTelefono(pResultSet.getString(pIndex));
        pIndex++;
        pCliente.setDireccion(pResultSet.getString(pIndex));
        return pIndex;
    }
      private static void obtenerDatos(PreparedStatement pPS, ArrayList<Cliente> pCliente) throws Exception{
        try(ResultSet resultSet = ComunDB.obtenerResultSet(pPS);){
            while(resultSet.next()){
                Cliente cliente = new Cliente();
                asignarDatosResultSet(cliente, resultSet, 0);
                pCliente.add(cliente);
            }
            resultSet.close();
        }
        catch(SQLException ex){
            throw ex;
        }
    }
        public static Cliente obtenerPorId(Cliente pCliente) throws Exception{
        Cliente cliente = new Cliente();
        ArrayList<Cliente> clientes = new ArrayList();
        try(Connection conn = ComunDB.obtenerConexion();){
            String sql = obtenerSelect(pCliente);
            sql += " WHERE c.Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pCliente.getId());
                obtenerDatos(ps, clientes);
                ps.close();
            }
            catch(SQLException ex){
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex){
            throw ex;
        }
        
        if(clientes.size() > 0){
            cliente = clientes.get(0);
        }
        return cliente;
    }
        public static ArrayList<Cliente> obtenerTodos() throws Exception{
        ArrayList<Cliente> clientes = new ArrayList();
        try(Connection conn = ComunDB.obtenerConexion();){
            String sql = obtenerSelect(new Cliente());
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                obtenerDatos(ps, clientes);
                ps.close();
            }
            catch(SQLException ex){
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex){
            throw ex;
        }
        return clientes;
    }
        static void querySelect(Cliente pCliente, ComunDB.utilQuery pUtilQuery) throws Exception{
        PreparedStatement statement = pUtilQuery.getStatement();
        if(pCliente.getId() > 0){
            pUtilQuery.AgregarNumWhere("c.Id=? ");
            if(statement != null){
                statement.setInt(pUtilQuery.getNumWhere(), pCliente.getId());
            }
        }
       
        if(pCliente.getNombre() != null && pCliente.getNombre().trim().isEmpty() == false){
            pUtilQuery.AgregarNumWhere(" c.Nombre LIKE ? ");
            if(statement != null){
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCliente.getNombre() + "%");
            }
        }
        
         if(pCliente.getApellido() != null && pCliente.getApellido().trim().isEmpty() == false){
            pUtilQuery.AgregarNumWhere(" c.Apellido LIKE ? ");
            if(statement != null){
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCliente.getApellido() + "%");
            }
        }
         
        if(pCliente.getRazonsocial() != null && pCliente.getRazonsocial().trim().isEmpty() == false){
            pUtilQuery.AgregarNumWhere(" c.RazonSocial LIKE ? ");
            if(statement != null){
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCliente.getRazonsocial() + "%");
            }
        }
        
         if(pCliente.getTelefono() != null && pCliente.getTelefono().trim().isEmpty() == false){
            pUtilQuery.AgregarNumWhere(" c.Telefono LIKE ? ");
            if(statement != null){
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCliente.getTelefono() + "%");
            }
        }
         
          if(pCliente.getDireccion() != null && pCliente.getDireccion().trim().isEmpty() == false){
            pUtilQuery.AgregarNumWhere(" c.Direccion LIKE ? ");
            if(statement != null){
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCliente.getDireccion() + "%");
            }
        }
    }
         public static ArrayList<Cliente> buscar(Cliente pCliente) throws Exception{
        ArrayList<Cliente> clientes = new ArrayList();
        try(Connection conn = ComunDB.obtenerConexion();){
            String sql = obtenerSelect(pCliente);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pCliente, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pCliente);
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pCliente, utilQuery);
                obtenerDatos(ps, clientes);
                ps.close();
            }
            catch(SQLException ex){
                throw ex;
            }
            conn.close();
        }
        catch(SQLException ex){
            throw ex;
        }
        return clientes;
    }
}
