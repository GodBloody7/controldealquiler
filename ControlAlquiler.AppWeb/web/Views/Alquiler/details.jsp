<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlalquiler.entidadesdenegocio.Alquiler"%>
<% Alquiler alquiler = (Alquiler) request.getAttribute("alquiler");%>


<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle del Alquiler</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle del alquiler</h5>
             <div class="row">
                <div class="row">
                   
                    <div class="input-field col l4 s12">
                        <input id="txtCliente" type="text" value="<%=alquiler.getCliente().getNombre()%>" disabled>
                        <label for="txtCliente">Cliente</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtusuario" type="text" value="<%=alquiler.getUsario().getNombre()%>" disabled>
                        <label for="txtUsuario">Usuario</label>
                    </div>
            
                    
                </div>

                <div class="row">
                    <div class="col l12 s12">
                         <a href="Alquiler?accion=edit&id=<%=alquiler.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="Alquiler" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>
