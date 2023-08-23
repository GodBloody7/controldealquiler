
package controlalquiler.accesoadatos;

import java.util.*;
import java.sql.*;
import controlalquiler.entidadesdenegocio.*;
import java.time.LocalDate;

public class AlquilerDAL {
   
    
    static String obtenerCampos(){
        return "a.Id, a.IdCliente, a.IdUsuario, a.FechaEntrega, a.FechaDevolucion, a.FechaRegistrado";
    }
    
    private static String obtenerSelect(Alquiler pAlquiler){
        String sql;
        sql = "SELECT ";
        if(pAlquiler.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER){
            sql += "TOP " + pAlquiler.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Alquiler a");
        return sql;
    }
    
    private static String agregarOrderBy(Alquiler pAlquiler){
        String sql = " ORDER BY a.Id DESC";
        if(pAlquiler.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL){
            sql += " LIMIT " + pAlquiler.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear (Alquiler pAlquiler)throws Exception{
        int result;
        String sql;
       try(Connection conn = ComunDB.obtenerConexion();){
           sql = " INSERT INTO Alquiler(IdCliente, IdUsuario, FechaEntrega, FechaDevolucion, FechaRegistrado) VALUES(?,?,?,?,?,?,?)";
           try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
               ps.setInt(1, pAlquiler.getIdCliente());
               ps.setInt(2, pAlquiler.getIdUsuario());
               ps.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
               ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
               ps.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
               result = ps.executeUpdate();
               ps.close();
           }catch(SQLException ex){
           throw ex;
       }
        conn.close();   
       }catch(SQLException ex){
        throw ex;
       
    }
    return result;
}
  public static int modificar(Alquiler pAlquiler) throws Exception{
      int result;
      String sql;
      try(Connection conn = ComunDB.obtenerConexion();){
          sql = "UPDATE Alquiler SET IdCliente=?, IdUsuario=?, Estatus=? WHERE Id=? ";
      try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
          ps.setInt(1, pAlquiler.getIdCliente());
          ps.setInt(2, pAlquiler.getIdUsuario());
          ps.setInt(4, pAlquiler.getId());
          result = ps.executeUpdate();
          ps.close();
          
      }catch (SQLException ex){
          throw ex;
      }
      conn.close();
      
      }
      catch (SQLException ex){
          throw ex;
      }
      return result;
  }  
  
  public static int eliminar (Alquiler pAlquiler)throws Exception{
      int result;
      String sql;
      try(Connection conn= ComunDB.obtenerConexion();){
          sql = "DELETE FROM Alquiler WHERE Id=?";
          try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
              ps.setInt(1, pAlquiler.getId());
              result = ps.executeUpdate();
              ps.close();
          }catch (SQLException ex){
              throw ex;
          }
          conn.close();
      }catch (SQLException ex){
          throw ex;
      }
      return result;
  }
  static int asignarDatosResultSet(Alquiler pAlquiler, ResultSet pResultSet, int pIndex) throws Exception{
      pIndex++;
      pAlquiler.setId(pResultSet.getInt(pIndex));
      pIndex++;
      pAlquiler.setIdCliente(pResultSet.getInt(pIndex));
      pIndex++;
      pAlquiler.setIdUsuario(pResultSet.getInt(pIndex));
      pIndex++;
      pAlquiler.setFechaEntrega(pResultSet.getDate(pIndex).toLocalDate());
      pIndex++;
      pAlquiler.setFechaDevolucion(pResultSet.getDate(pIndex).toLocalDate());
      pIndex++;
      pAlquiler.setFechaRegistrado(pResultSet.getDate(pIndex).toLocalDate());
      pIndex++;
      return pIndex;
  }
  
  private static void obtenerDatos(PreparedStatement pPS, ArrayList<Alquiler>pAlquileres) throws Exception{
      try( ResultSet resultSet = ComunDB.obtenerResultSet(pPS);){
          while(resultSet.next()){
              Alquiler alquiler = new Alquiler();
              asignarDatosResultSet(alquiler ,resultSet, 0);
              pAlquileres.add(alquiler);
          }
          resultSet.close();
      }catch(SQLException ex){
          throw ex;
      }
  }
  
  private static void obtenerDatosIncluirRelaciones(PreparedStatement pPS, ArrayList<Alquiler>pAlquileres) throws Exception{
      try(ResultSet resultSet= ComunDB.obtenerResultSet(pPS);){
          HashMap<Integer, Usuario>  usuarioMap = new HashMap(); 
          HashMap<Integer, Cliente> clienteMap = new HashMap();
          while(resultSet.next()){
              Alquiler alquiler = new Alquiler();
              int index = asignarDatosResultSet(alquiler, resultSet, 0);
              if(usuarioMap.containsKey(alquiler.getIdUsuario()) == false){
                  Usuario usuario = new Usuario();
                  UsuarioDAL.asignarDatosResultSet(usuario, resultSet, index);
                  usuarioMap.put(usuario.getId(), usuario);
                  alquiler.setUsuario(usuario);
              }else {
                  alquiler.setUsuario(usuarioMap.get(alquiler.getIdUsuario()));
              }
              if (clienteMap.containsKey(alquiler.getIdCliente()) == false){
                  Cliente cliente = new Cliente();
                  ClienteDAL.asignarDatosResultSet(cliente, resultSet, index);
                  clienteMap.put(cliente.getId(), cliente);
                 alquiler.setCliente(cliente);
              }
              else{
                  alquiler.setCliente(clienteMap.get(alquiler.getIdCliente()));
              }
              pAlquileres.add(alquiler);
          }
          resultSet.close();
               }catch(SQLException ex){
                   throw ex;
               }
  }
  public static Alquiler obtenerPorId(Alquiler pAlquiler)throws Exception{
      Alquiler alquiler = new Alquiler();
      ArrayList<Alquiler>alquileres = new   ArrayList();
      try(Connection conn = ComunDB.obtenerConexion();){
          String sql = obtenerSelect(pAlquiler);
          sql +=" WHERE a.Id=? ";
          try(PreparedStatement ps = ComunDB.createPreparedStatement(conn,  sql);){
              ps.setInt(1, pAlquiler.getId());
              obtenerDatos(ps, alquileres);
              ps.close();
          }catch (SQLException ex){
              throw ex;
          }
          conn.close();
      }
      catch(SQLException ex){
          throw ex;
      }
      if(alquileres.size() > 0){
          alquiler = alquileres.get(0);
      }
      return alquiler;
  }
  public static ArrayList<Alquiler>obtenerTodos() throws Exception{
      ArrayList<Alquiler> alquileres;
      alquileres = new ArrayList<>();
      try(Connection conn = ComunDB.obtenerConexion();){
          String sql = obtenerSelect(new Alquiler());
          sql += agregarOrderBy(new Alquiler());
          try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
              obtenerDatos(ps, alquileres);
              ps.close();
          }catch(SQLException ex){
              throw ex;
          }
          conn.close();
      }catch(SQLException ex){
          throw ex;
      }
      return alquileres;
  }
  
  
  static void querySelect(Alquiler pAlquiler, ComunDB.utilQuery pUtilQuery)throws SQLException{
      PreparedStatement statement = pUtilQuery.getStatement();
      if(pAlquiler.getId() > 0){
          pUtilQuery.AgregarNumWhere(" a.Id=? ");
          if(statement != null){
              statement.setInt(pUtilQuery.getNumWhere(),pAlquiler.getId());
          }
      }
      if(pAlquiler.getIdCliente() > 0){
          pUtilQuery.AgregarNumWhere(" a.IdCliente=? ");
          if(statement != null){
              statement.setInt(pUtilQuery.getNumWhere(), pAlquiler.getIdCliente());
              
          }
      }
      if (pAlquiler.getIdUsuario() > 0){
          pUtilQuery.AgregarNumWhere(" a.IdUsuario=? ");
          if(statement != null){
              statement.setInt(pUtilQuery.getNumWhere(), pAlquiler.getIdUsuario());
          }
      }
  }
  
  public static ArrayList<Alquiler>buscar(Alquiler pAlquiler) throws Exception{
      ArrayList<Alquiler>alquileres = new ArrayList();
      try(Connection conn = ComunDB.obtenerConexion();){
          String sql = obtenerSelect(pAlquiler);
          ComunDB comundb = new ComunDB();
          ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
          querySelect(pAlquiler, utilQuery);
          sql = utilQuery.getSQL();
          sql += agregarOrderBy(pAlquiler);
          try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
              utilQuery.setStatement(ps);
              utilQuery.setSQL(null);
              utilQuery.setNumWhere(0);
              querySelect(pAlquiler, utilQuery);
              obtenerDatos(ps, alquileres);
              ps.close();
          }
          catch (SQLException ex){
              throw ex;
          }
          conn.close();
      }
      catch (SQLException ex){
          throw ex;
      }
      return alquileres;
  }
  
  public static ArrayList<Alquiler> buscarIncluirRelaciones(Alquiler pAlquiler) throws Exception{
      ArrayList<Alquiler> alquileres = new ArrayList();
      try(Connection conn = ComunDB.obtenerConexion();){
          String sql = " SELECT ";
          if(pAlquiler.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER){
              sql += " TOP " + pAlquiler.getTop_aux() + " ";
          }
          sql += obtenerCampos();
          sql += ",";
          sql += UsuarioDAL.obtenerCampos();
          sql += ",";
           sql += ClienteDAL.obtenerCampos();
          sql += ",";
          sql += " FROM Alquiler a";
          sql += " INNER JOIN Usuarios U on (a.IdUsuario = p.Id)";
          sql += " INNER JOIN Cliente c on (a.IdCliente = c.Id)";
          ComunDB comundb = new ComunDB();
          ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
          querySelect(pAlquiler, utilQuery);
          sql = utilQuery.getSQL();
          sql += agregarOrderBy(pAlquiler);
          try(PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);){
              utilQuery.setStatement(ps);
              utilQuery.setSQL(null);
              utilQuery.setNumWhere(0);
              querySelect(pAlquiler, utilQuery);
              obtenerDatosIncluirRelaciones(ps, alquileres);
              ps.close();
          }catch (SQLException ex){
              throw ex;
          }conn.close();
         
      }catch (SQLException ex){
          throw ex;
      }
      return alquileres;

  }
}
