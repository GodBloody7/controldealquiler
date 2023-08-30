<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlalquiler.entidadesdenegocio.Alquiler"%>
<% Alquiler alquiler = (Alquiler) request.getAttribute("empresa");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Eliminar Alquiler</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Eliminar Alquiler</h5>
            <form action="Alquiler" method="post">  
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=alquiler.getId()%>">  
                <div class="row">
                  
                    <div class="input-field col l4 s12">
                        <input id="txtCliente" type="text" value="<%=alquiler.getCliente().getNombre()%>" disabled>
                        <label for="txtCliente">Cliente</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtUsuario" type="text" value="<%=alquiler.getUsuario().getNombre()%>" disabled>
                        <label for="txtUsuario">Usuario</label>
                    </div>
                   
                    
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">delete</i>Eliminar</button>
                        <a href="Alquiler" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>