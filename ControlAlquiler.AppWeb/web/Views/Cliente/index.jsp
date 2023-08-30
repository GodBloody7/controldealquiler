<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlalquiler.entidadesdenegocio.Cliente"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Cliente> clientes = (ArrayList<Cliente>) request.getAttribute("clientes");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (clientes == null) {
        clientes = new ArrayList();
    } else if (clientes.size() > numReg) {
        double divNumPage = (double) clientes.size() / (double) numReg;
        numPage = (int) Math.ceil(divNumPage);
    }
    String strTop_aux = request.getParameter("top_aux");
    int top_aux = 10;
    if (strTop_aux != null && strTop_aux.trim().length() > 0) {
        top_aux = Integer.parseInt(strTop_aux);
    }
%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Lista de Clientes</title>
    </head>
     <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Cliente</h5>
            <form action="Cliente" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre">
                        <label for="txtNombre">Nombre</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" name="apellido">
                        <label for="txtApellido">Apellido</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtRazonsocial" type="text" name="razonsocial">
                        <label for="txtRazonsocial">Razon Social</label>
                    </div>                    
                    <div class="input-field col l4 s12">
                        <input  id="txtTelefono" type="text" name="telefono">
                        <label for="txtTelefono">Telefono</label>
                    </div>
                     <div class="input-field col l4 s12">
                        <input  id="txtDireccion" type="text" name="direccion">
                        <label for="txtDireccion">Direcion</label>
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Shared/selectTop.jsp">
                            <jsp:param name="top_aux" value="<%=top_aux%>" />                        
                        </jsp:include>                        
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="Cliente?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>                                     
                                    <th>Nombre</th>  
                                    <th>Apellido</th> 
                                    <th>Razon Social</th>  
                                    <th>Telefono</th>  
                                    <th>Direccion</th>     
                                    <th>Acciones</th>
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Cliente cliente : clientes) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">                                    
                                    <td><%=cliente.getNombre()%></td>  
                                    <td><%=cliente.getApellido()%></td>
                                    <td><%=cliente.getRazonsocial()%></td>  
                                    <td><%=cliente.getTelefono()%></td>
                                    <td><%=cliente.getDireccion()%></td>  
                                    <td>
                                        <div style="display:flex">
                                             <a href="Cliente?accion=edit&id=<%=cliente.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                            <i class="material-icons">edit</i>
                                        </a>
                                        <a href="Cliente?accion=details&id=<%=cliente.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                            <i class="material-icons">description</i>
                                        </a>
                                        <a href="cliente?accion=delete&id=<%=cliente.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
                                            <i class="material-icons">delete</i>
                                        </a>    
                                        </div>                                                                    
                                    </td>                                   
                                </tr>
                                <%}%>                                                       
                            </tbody>
                        </table>
                    </div>                  
                </div>
            </div>             
            <div class="row">
                <div class="col l12 s12">
                    <jsp:include page="/Views/Shared/paginacion.jsp">
                        <jsp:param name="numPage" value="<%= numPage%>" />                        
                    </jsp:include>
                </div>
            </div>
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>

