
package controlalquiler.accesoadatos;

import java.util.*;
import java.sql.*;
import controlalquiler.entidadesdenegocio.*;

public class EquipoDAL {
    
    static String obtenerCampos(){
        return "e.Id, e.IdCategoria, e.Nombre, e.Descripcion, e.CostoDia, e.Stock, e.Estatus";
    }
    
    private static String obtenerSelect(Equipo pEquipo){
        String sql;
        sql = "SELECT ";
        if(pEquipo.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER){
            sql += "TOP " + pEquipo.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + "FROM Equipo e");
        return sql;
    }
    
    private static String agregarOrderBy(Equipo pEquipo){
        String sql = " ORDER BY e.Id DESC";
        if(pEquipo.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL){
            sql += " LIMIT " + pEquipo.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Equipo pEquipo) throws Exception{
        int result;
        String sql;
        
        try(Connection conn = ComunDB.obtenerConexion();){
            sql = "INSERT INTO Equipo(IdCategoria,Nombre,Descripcion,CostoDia,Stock,Estatus) VALUES(?,?,?,?,?,?)";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pEquipo.getIdCategoria());
                ps.setString(2, pEquipo.getNombre());
                ps.setString(3, pEquipo.getDescripcion());
                ps.setDouble(4, pEquipo.getCostoDia());
                ps.setInt(5, pEquipo.getStock());
                ps.setByte(6, pEquipo.getEstatus());
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
    
    public static int modificar(Equipo pEquipo) throws Exception{
        int result;
        String sql;
        
        try(Connection conn = ComunDB.obtenerConexion();){
            sql = "UPDATE Equipo SET IdCategoria=?, Nombre=?, Descripcion=?, CostoDia=?, Stock=?, Estatus=? WHERE Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pEquipo.getIdCategoria());
                ps.setString(2, pEquipo.getNombre());
                ps.setString(3, pEquipo.getDescripcion());
                ps.setDouble(4, pEquipo.getCostoDia());
                ps.setInt(5, pEquipo.getStock());
                ps.setInt(6, pEquipo.getEstatus());
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
    
    public static int eliminar(Equipo pEquipo) throws Exception{
        int result;
        String sql;
        try(Connection conn = ComunDB.obtenerConexion();){
            sql = "DELETE FROM Equipo WHERE Id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pEquipo.getId());
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
    
    static int asignarDatosResultSet(Equipo pEquipo, ResultSet pResultSet, int pIndex) throws Exception{
        pIndex++;
        pEquipo.setId(pResultSet.getInt(pIndex));
        pIndex++;
        pEquipo.setIdCategoria(pResultSet.getInt(pIndex));
        pIndex++;
        pEquipo.setNombre(pResultSet.getString(pIndex));
        pIndex++;
        pEquipo.setDescripcion(pResultSet.getString(pIndex));
        pIndex++;
        pEquipo.setCostoDia(pResultSet.getDouble(pIndex));
        pIndex++;
        pEquipo.setStock(pResultSet.getInt(pIndex));
        pIndex++;
        pEquipo.setEstatus(pResultSet.getByte(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Equipo> pEquipos) throws Exception{
        try(ResultSet resultSet = ComunDB.obtenerResultSet(pPS);){
            while(resultSet.next()){
                Equipo equipo = new Equipo();
                asignarDatosResultSet(equipo, resultSet, 0);
                pEquipos.add(equipo);
            }
            resultSet.close();
        }
        catch(SQLException ex){
            throw ex;
        }
    }
    
    private static void obtenerDatosIncluirCategoria(PreparedStatement pPS, ArrayList<Equipo> pEquipos) throws Exception{
        try(ResultSet resultSet = ComunDB.obtenerResultSet(pPS);){
            HashMap<Integer, Categoria> categoriaMap = new HashMap();
            while(resultSet.next()){
                Equipo equipo = new Equipo();
                int index = asignarDatosResultSet(equipo, resultSet, 0);
                if(categoriaMap.containsKey(equipo.getIdCategoria()) == false){
                    Categoria categoria = new Categoria();
                    CategoriaDAL.asignarDatosResultSet(categoria, resultSet, index);
                    categoriaMap.put(categoria.getId(), categoria);
                    equipo.setCategoria(categoria);
                }
                else{
                    equipo.setCategoria(categoriaMap.get(equipo.getIdCategoria()));
                }
                pEquipos.add(equipo);
            }
            resultSet.close();
        }
        catch(SQLException ex){
            throw ex;
        }
    }
    
    public static Equipo obtenerPorId(Equipo pEquipo) throws Exception{
        Equipo equipo = new Equipo();
        ArrayList<Equipo> equipos = new ArrayList();
        try(Connection conn = ComunDB.obtenerConexion();){
            String sql = obtenerSelect(pEquipo);
            sql += " WHERE e.id=?";
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                ps.setInt(1, pEquipo.getId());
                obtenerDatos(ps, equipos);
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
        
        if(equipos.size() > 0){
            equipo = equipos.get(0);
        }
        return equipo;
    }
    
    public static ArrayList<Equipo> obtenerTodos() throws Exception{
        ArrayList<Equipo> equipos = new ArrayList();
        try(Connection conn = ComunDB.obtenerConexion();){
            String sql = obtenerSelect(new Equipo());
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                obtenerDatos(ps, equipos);
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
        return equipos;
    }
    
    static void querySelect(Equipo pEquipo, ComunDB.utilQuery pUtilQuery) throws Exception{
        PreparedStatement statement = pUtilQuery.getStatement();
        if(pEquipo.getId() > 0){
            pUtilQuery.AgregarNumWhere("e.Id=? ");
            if(statement != null){
                statement.setInt(pUtilQuery.getNumWhere(), pEquipo.getId());
            }
        }
        
        if(pEquipo.getIdCategoria() > 0){
            pUtilQuery.AgregarNumWhere("e.IdCategoria=? ");
            if(statement != null){
                statement.setInt(pUtilQuery.getNumWhere(), pEquipo.getIdCategoria());
            }
        }
        
        if(pEquipo.getNombre() != null && pEquipo.getNombre().trim().isEmpty() == false){
            pUtilQuery.AgregarNumWhere(" e.Nombre LIKE ? ");
            if(statement != null){
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEquipo.getNombre() + "%");
            }
        }
        
        if(pEquipo.getDescripcion() != null && pEquipo.getDescripcion().trim().isEmpty() == false){
            pUtilQuery.AgregarNumWhere(" e.Descripcion LIKE ? ");
            if(statement != null){
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEquipo.getDescripcion() + "%");
            }
        }
        
        if(pEquipo.getCostoDia() > 0){
            pUtilQuery.AgregarNumWhere("e.CostoDia=? ");
            if(statement != null){
                statement.setDouble(pUtilQuery.getNumWhere(), pEquipo.getCostoDia());
            }
        }
        
        if(pEquipo.getStock() > 0){
            pUtilQuery.AgregarNumWhere("e.Stock=? ");
            if(statement != null){
                statement.setInt(pUtilQuery.getNumWhere(), pEquipo.getStock());
            }
        }
        
        if(pEquipo.getEstatus() > 0){
            pUtilQuery.AgregarNumWhere("e.Estatus=? ");
            if(statement != null){
                statement.setInt(pUtilQuery.getNumWhere(), pEquipo.getEstatus());
            }
        }
    }
    
    public static ArrayList<Equipo> buscar(Equipo pEquipo) throws Exception{
        ArrayList<Equipo> equipos = new ArrayList();
        try(Connection conn = ComunDB.obtenerConexion();){
            String sql = obtenerSelect(pEquipo);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pEquipo, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pEquipo);
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pEquipo, utilQuery);
                obtenerDatos(ps, equipos);
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
        return equipos;
    }
    
    public static ArrayList<Equipo> buscarIncluirCategoria(Equipo pEquipo) throws Exception{
        ArrayList<Equipo> equipos = new ArrayList();
        try(Connection conn = ComunDB.obtenerConexion();){
            String sql = "SELECT ";
            if(pEquipo.getTop_aux()> 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER){
                sql += "TOP " + pEquipo.getTop_aux();
            }
            sql += obtenerCampos();
            sql += ",";
            sql += CategoriaDAL.obtenerCampos();
            sql += " FROM Equipo e";
            sql += " JOIN Categoria c on (e.IdCategoria=c.Id";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pEquipo, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pEquipo);
            try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pEquipo, utilQuery);
                obtenerDatosIncluirCategoria(ps, equipos);
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
        return equipos;
    }
}
