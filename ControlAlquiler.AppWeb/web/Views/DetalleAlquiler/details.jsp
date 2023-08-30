
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cfhn.controlalquiler.entidadesdenegocio.DetalleAlquiler"%>
<% DetalleAlquiler detalle = (DetalleAlquiler) request.getAttribute("detalle");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle del alquiler</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle del alquiler</h5>
             <div class="row">
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtCantidad" type="number" value="<%=detalle.getCantidad()%>" disabled>
                        <label for="txtCantidad">Cantidad</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtPrecio" type="number" value="<%=detalle.getPrecio()%>" disabled>
                        <label for="txtPrecio">Precio</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtSubTotal" type="number" value="<%=detalle.getSubTotal()%>" disabled>
                        <label for="txtSubTotal">SubTotal</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtAlquiler" type="text" value="<%=detalle.getAlquiler().getNombre()%>" disabled>
                        <label for="txtAlquiler">Alquiler</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtEquipo" type="text" value="<%=detalle.getEquipo().getNombre()%>" disabled>
                        <label for="txtEquipo">Equipo</label>
                    </div>
                </div>

                <div class="row">
                    <div class="col l12 s12">
                         <a href="DetalleAlquiler?accion=edit&id=<%=detalle.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="DetalleAlquiler" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>