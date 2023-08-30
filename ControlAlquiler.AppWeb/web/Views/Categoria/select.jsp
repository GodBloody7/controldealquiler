<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cfhn.controlalquiler.entidadesdenegocio.Categoria"%>
<%@page import="cfhn.controlalquiler.accesoadatos.CategoriaDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Categoria> categoria = CategoriaDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slCategoria" name="idCategoria">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Categoria categoria : categorias) {%>
    <option <%=(id == categoria.getId()) ? "selected" : ""%>  value="<%=categoria.getId()%>"><%= categoria.getNombre()%></option>
    <%}%>
</select>
<label for="idCategoria">Categoria</label>
