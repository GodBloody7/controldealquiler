
package controlalquiler.accesoadatos;

import java.util.*;
import java.sql.*;
import controlalquiler.entidadesdenegocio.*;

public class DetalleAlquilerDAL {
    static String obtenerCampos(){
        return "d.Id, d.IdAlquiler, d.IdEquipo, d.Cantidad, d.Precio, d.SubTotal";
    }
    
    private static String obtenerSelect(DetalleAlquiler pDetalle){
        String sql;
        sql = "SELECT ";
        if(pDetalle.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER){
            sql += "TOP " + pDetalle.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + "FROM DetalleAlquiler d");
        return sql;
    }
    
    private static String agregarOrderBy(DetalleAlquiler pDetalle){
        String sql = " ORDER BY d.Id DESC";
        if(pDetalle.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL){
            sql += " LIMIT " + pDetalle.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(DetalleAlquiler pDetalle) throws Exception{
        int result;
        String sql;
        try(Connection conn = ComunDB.obtenerConexion();){
            sql = "INSERT INTO DetalleAlquiler(IdAlquiler, IdEquipo, Cantidad, Precio, SubTotal) VALUES(?,?,?,?,?,?)";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pDetalle.getIdAquiler());
                ps.setInt(2, pDetalle.getIdEquipo());
                ps.setInt(3, pDetalle.getCantidad());
                ps.setDouble(4, pDetalle.getPrecio());
                ps.setDouble(5, pDetalle.getSubTotal());
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
    
    public static int modificar(DetalleAlquiler pDetalle) throws Exception{
        int result;
        String sql;
        try(Connection conn = ComunDB.obtenerConexion();){
            sql = "UPDATE DetalleAlquiler SET IdAlquiler=?, IdEquipo=?, Cantidad=?, Precio=?, SubTotal=? WHERE Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pDetalle.getIdAquiler());
                ps.setInt(2, pDetalle.getIdEquipo());
                ps.setInt(3, pDetalle.getCantidad());
                ps.setDouble(4, pDetalle.getPrecio());
                ps.setDouble(5, pDetalle.getSubTotal());
                ps.setInt(6, pDetalle.getId());
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
    
    public static int eliminar(DetalleAlquiler pDetalle) throws Exception{
        int result;
        String sql;
        try(Connection conn = ComunDB.obtenerConexion();){
            sql = "DELETE FROM DetalleAlquiler WHERE Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pDetalle.getId());
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
    
    static int asignarDatosResultSet(DetalleAlquiler pDetalle, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pDetalle.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pDetalle.setIdAquiler(pResultSet.getInt(pIndex));
        pIndex++;
        pDetalle.setIdEquipo(pResultSet.getInt(pIndex));
        pIndex++;
        pDetalle.setCantidad(pResultSet.getInt(pIndex));
        pIndex++;
        pDetalle.setPrecio(pResultSet.getDouble(pIndex));
        pIndex++;
        pDetalle.setSubTotal(pResultSet.getDouble(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<DetalleAlquiler> pDetalles) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { 
            while (resultSet.next()) {
                DetalleAlquiler detalle = new DetalleAlquiler();
                asignarDatosResultSet(detalle, resultSet, 0);
                pDetalles.add(detalle);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    private static void obtenerDatosIncluirAlquiler(PreparedStatement pPS, ArrayList<DetalleAlquiler> pDetalles)throws Exception{
        try(ResultSet resultSet = ComunDB.obtenerResultSet(pPS);){
            HashMap<Integer, Alquiler> alquilerMap = new HashMap();
            while(resultSet.next()){
                DetalleAlquiler detalle = new DetalleAlquiler();
                int index = asignarDatosResultSet(detalle, resultSet, 0);
                if(alquilerMap.containsKey(detalle.getIdAquiler()) == false){
                    Alquiler alquiler = new Alquiler();
                    AlquilerDAL.asignarDatosResultSet(alquiler, resultSet, index);
                    alquilerMap.put(alquiler.getId(), alquiler);
                    detalle.setAlquiler(alquiler);
                }
                else{
                    detalle.setAlquiler(alquilerMap.get(detalle.getIdAquiler()));
                }
                pDetalles.add(detalle);
            }
            resultSet.close();
        }
        catch(SQLException ex){
            throw ex;
        }
    }
    
    private static void obtenerDatosIncluirEquipo(PreparedStatement pPS, ArrayList<DetalleAlquiler> pDetalles)throws Exception{
        try(ResultSet resultSet = ComunDB.obtenerResultSet(pPS);){
            HashMap<Integer, Equipo> equipoMap = new HashMap();
            while(resultSet.next()){
                DetalleAlquiler detalle = new DetalleAlquiler();
                int index = asignarDatosResultSet(detalle, resultSet, 0);
                if(equipoMap.containsKey(detalle.getIdEquipo()) == false){
                    Equipo equipo = new Equipo();
                    EquipoDAL.asignarDatosResultSet(equipo, resultSet, index);
                    equipoMap.put(equipo.getId(), equipo);
                    detalle.setEquipo(equipo);
                }
                else{
                    detalle.setEquipo(equipoMap.get(detalle.getIdEquipo()));
                }
                pDetalles.add(detalle);
            }
            resultSet.close();
        }
        catch(SQLException ex){
            throw ex;
        }
    }

    
    public static DetalleAlquiler obtenerPorId(DetalleAlquiler pDetalle) throws Exception {
        DetalleAlquiler detalle = new DetalleAlquiler();
        ArrayList<DetalleAlquiler> detalles = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pDetalle);
            sql += " WHERE d.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pDetalle.getId());
                obtenerDatos(ps, detalles);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (detalles.size() > 0) {
            detalle = detalles.get(0);
        }
        return detalle;
    }
    
    public static ArrayList<DetalleAlquiler> obtenerTodos() throws Exception {
        ArrayList<DetalleAlquiler> detalles;
        detalles = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new DetalleAlquiler()); 
            sql += agregarOrderBy(new DetalleAlquiler());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, detalles);
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return detalles;
    }
    
    static void querySelect(DetalleAlquiler pDetalle, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pDetalle.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" d.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pDetalle.getId());
            }
        }

        if (pDetalle.getIdAquiler()> 0) {
            pUtilQuery.AgregarNumWhere(" d.IdAlquiler=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pDetalle.getIdAquiler());
            }
        }
        
        if (pDetalle.getIdEquipo()> 0) {
            pUtilQuery.AgregarNumWhere(" d.IdEquipo=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pDetalle.getIdEquipo());
            }
        }
        
        if (pDetalle.getCantidad()> 0) {
            pUtilQuery.AgregarNumWhere(" d.Cantidad=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pDetalle.getCantidad());
            }
        }
        
        if (pDetalle.getPrecio()> 0) {
            pUtilQuery.AgregarNumWhere(" d.Precio=? ");
            if (statement != null) {
                statement.setDouble(pUtilQuery.getNumWhere(), pDetalle.getPrecio());
            }
        }
        
        if (pDetalle.getSubTotal() > 0) {
            pUtilQuery.AgregarNumWhere(" d.SubTotal=? ");
            if (statement != null) {
                statement.setDouble(pUtilQuery.getNumWhere(), pDetalle.getSubTotal());
            }
        }
    }
    
    public static ArrayList<DetalleAlquiler> buscar(DetalleAlquiler pDetalle) throws Exception {
        ArrayList<DetalleAlquiler> detalles = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pDetalle);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pDetalle, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pDetalle);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pDetalle, utilQuery);
                obtenerDatos(ps, detalles);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return detalles;
    }
    
    public static ArrayList<DetalleAlquiler> buscarIncluirAlquiler(DetalleAlquiler pDetalle) throws Exception{
        ArrayList<DetalleAlquiler> detalles = new ArrayList();
        try(Connection conn = ComunDB.obtenerConexion();){
            String sql = "SELECT ";
            if(pDetalle.getTop_aux()> 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER){
                sql += "TOP " + pDetalle.getTop_aux();
            }
            sql += obtenerCampos();
            sql += ",";
            sql += AlquilerDAL.obtenerCampos();
            sql += " FROM DetalleAlquiler d";
            sql += " JOIN Alquiler a on (d.IdAlquiler=a.Id";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pDetalle, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pDetalle);
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pDetalle, utilQuery);
                obtenerDatosIncluirAlquiler(ps, detalles);
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
        return detalles;
    }
    
    public static ArrayList<DetalleAlquiler> buscarIncluirEquipo(DetalleAlquiler pDetalle) throws Exception{
        ArrayList<DetalleAlquiler> detalles = new ArrayList();
        try(Connection conn = ComunDB.obtenerConexion();){
            String sql = "SELECT ";
            if(pDetalle.getTop_aux()> 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER){
                sql += "TOP " + pDetalle.getTop_aux();
            }
            sql += obtenerCampos();
            sql += ",";
            sql += EquipoDAL.obtenerCampos();
            sql += " FROM DetalleAlquiler d";
            sql += " JOIN Equipo e on (d.IdEquipo=e.Id";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pDetalle, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pDetalle);
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pDetalle, utilQuery);
                obtenerDatosIncluirEquipo(ps, detalles);
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
        return detalles;
    }
}
