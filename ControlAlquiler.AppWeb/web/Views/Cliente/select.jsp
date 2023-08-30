<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlalquiler.entidadesdenegocio.Cliente"%>
<%@page import="controlalquiler.accesoadatos.ClienteDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Cliente> clientes = ClienteDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slCliente" name="idCliente">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Cliente cliente : clientes) {%>
    <option <%=(id == cliente.getId()) ? "selected" : ""%>  value="<%=cliente.getId()%>"><%= cliente.getNombre()%></option>
    <%}%>
</select>
<label for="idCliente">Cliente</label>
