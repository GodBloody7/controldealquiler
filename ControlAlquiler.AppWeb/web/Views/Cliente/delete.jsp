<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlalquiler.entidadesdenegocio.Cliente"%>
<% Cliente cliente = (Cliente) request.getAttribute("cliente");%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Eliminar Cliente</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Eliminar Cliente</h5>          
            <form action="Cliente" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">   
                <input type="hidden" name="id" value="<%=cliente.getId()%>">   
                <div class="row">
                    <div class="input-field col l4 s12">
                    <input disabled  id="txtNombre" type="text" value="<%=cliente.getNombre()%>">
                    <label for="txtNombre">Nombre</label>
                </div>      
                     <div class="input-field col l4 s12">
                    <input disabled  id="txtApellido" type="text" value="<%=cliente.getApellido()%>">
                    <label for="txtApellido">Apellido</label>
                </div> 
                     <div class="input-field col l4 s12">
                    <input disabled  id="txtRazonsocialocial" type="text" value="<%=cliente.getRazonsocial()%>">
                    <label for="txtRazonsocial">Razon Social</label>
                </div> 
                     <div class="input-field col l4 s12">
                    <input disabled  id="txtTelefono" type="text" value="<%=cliente.getTelefono()%>">
                    <label for="txtTelefono">Telefono</label>
                </div> 
                     <div class="input-field col l4 s12">
                    <input disabled  id="txtDireccion" type="text" value="<%=cliente.getDireccion()%>">
                    <label for="txtDireccion">Direccion</label>
                </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">delete</i>Eliminar</button>
                        <a href="Cliente" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>

