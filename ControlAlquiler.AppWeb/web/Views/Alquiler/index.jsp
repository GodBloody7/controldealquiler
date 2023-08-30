<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlalquiler.entidadesdenegocio.*"%>
<%@page import="java.util.ArrayList"%>
    <% ArrayList<Alquiler> alquileres = (ArrayList<Alquiler>) request.getAttribute("alquileres");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (alquileres == null) {
        alquileres = new ArrayList();
    } else if (alquileres.size() > numReg) {
        double divNumPage = (double) alquileres.size() / (double) numReg;
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
        <title>Lista de Alquileres</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Alquiler</h5>
            <form action="Alquiler" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                                      
  
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Cliente/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                        <span id="slCliente_error" style="color:red" class="helper-text"></span>
                    </div>
                    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Usuario/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                        <span id="slUsuario_error" style="color:red" class="helper-text"></span>
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
                        <a href="Alquiler?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>                                     
                                   
                                    <th>Cliente</th> 
                                    <th>Usuario</th>  
                                    <th>Fecha Entrega</th>  
                                    <th>Fecha Devolución</th>     
                                    <th>Fecha Registro</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Alquiler alquiler : alquileres) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">                                    
                                    <td><%=alquiler.getCliente().getNombre()%></td>
                                    <td><%=alquiler.getUsuario().getNombre()%></td>  
                                    <td><%=alquiler.getFechaEntrega()%></td>
                                    <td><%=alquiler.getFechaDevolucion()%></td>
                                    <td><%=alquiler.getFechaRegistrado()%></td>
                                     
                                    <td>
                                        <div style="display:flex">
                                             <a href="Alquiler?accion=edit&id=<%=alquiler.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                            <i class="material-icons">edit</i>
                                        </a>
                                        <a href="Alquiler?accion=details&id=<%=alquiler.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                            <i class="material-icons">description</i>
                                        </a>
                                        <a href="Alquiler?accion=delete&id=<%=alquiler.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
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