<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlalquiler.entidadesdenegocio.Usuario"%>
<%@page import="controlalquiler.accesoadatos.UsuarioDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Uusario> usuarios = UsuarioDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slUsuario" name="idUsuario">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Usuario usuario : usuarios) {%>
        <option <%=(id == usuario.getId()) ? "selected" : "" %>  value="<%=usuario.getId()%>"><%= usuario.getNombre()%></option>
    <%}%>
</select>
<label for="idUsuario">Usuario</label>