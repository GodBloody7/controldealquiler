
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlalquiler.entidadesdenegocio.DetalleAlquiler"%>
<% DetalleAlquiler = (DetalleAlquiler) request.getAttribute("detalle");%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Eliminar detalle</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Eliminar detalle</h5>
            <form action="DetalleAlquiler" method="post">  
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=detalle.getId()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtCantidad" type="number" value="<%=detalle.getCantidad()%>" disabled>
                        <label for="txtCantidad">Cantidad</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtPrecio" type="number" value="<%=detalle.getPrecio()%>" disabled>
                        <label for="txtPrecio">Precio</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtSubTotal" type="number" value="<%=detalle.getSubTotal()%>" disabled>
                        <label for="txtSubTotal">Sub total</label>
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
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">delete</i>Eliminar</button>
                        <a href="DetalleAlquiler" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>
