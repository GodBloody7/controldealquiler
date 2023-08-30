
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cfhn.controlalquiler.entidadesdenegocio.DetalleAlquiler"%>
<%@page import="cfhn.controlalquiler.entidadesdenegocio.*"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<DetalleAlquiler> detalles = (ArrayList<DetalleAlquiler>) request.getAttribute("detalles");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (detalles == null) {
        detalles = new ArrayList();
    } else if (detalles.size() > numReg) {
        double divNumPage = (double) detalles.size() / (double) numReg;
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
        <title>Lista de detalles de alquileres</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Detalle Alquiler</h5>
            <form action="DetalleAlquiler" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtCantidad" type="number" name="cantidad">
                        <label for="txtCantidad">Cantidad</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtPrecio" type="number" name="precio">
                        <label for="txtPrecio">Precio</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtSubTotal" type="number" name="subTotal">
                        <label for="txtSubTotal">Sub total</label>
                    </div>  
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Alquiler/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>                        
                    </div>
                    <div class="input-field col l4 s12">
                        <jsp:include page="/Views/Equipo/select.jsp">
                            <jsp:param name="id" value="0" />
                        </jsp:include>
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
                        <a href="DetalleAlquiler?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>    
                                    <th>Alquiler</th>
                                    <th>Equipo</th>
                                    <th>Cantidad</th>  
                                    <th>Precio</th> 
                                    <th>SubTotal</th> 
                                    <th>Acciones</th>
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (DetalleAlquiler detalle : detalles) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">                                    
                                    <td><%=detalle.getCantidad()%></td>  
                                    <td><%=detalle.getPrecio()%></td>
                                    <td><%=detalle.getSubTotal()%></td>  
                                    <td><%=detalle.getAlquiler().getNombre()%></td>
                                    <td><%=detalle.getEquipo().getNombre()%></td>  
                                    <td>
                                        <div style="display:flex">
                                             <a href="DetalleAlquiler?accion=edit&id=<%=detalle.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                            <i class="material-icons">edit</i>
                                        </a>
                                        <a href="DetalleAlquiler?accion=details&id=<%=detalle.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                            <i class="material-icons">description</i>
                                        </a>
                                        <a href="DetalleAlquiler?accion=delete&id=<%=detalle.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
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
