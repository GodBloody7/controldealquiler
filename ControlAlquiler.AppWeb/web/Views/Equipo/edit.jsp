<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlalquiler.entidadesdenegocio.*"%>
<% Equipo equipo = (Equipo) request.getAttribute("equipo");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Equipo</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Equipo</h5>
            <form action="Equipo" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=equipo.getId()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" required class="validate" maxlength="50">
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtDescripcion" type="text" name="descripcion" required class="validate" maxlength="30">
                        <label for="txtDescrpcion">Descripcion</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtCostoDia" type="number" name="costoDia" required class="validate" maxlength="25">
                        <label for="txtCostoDia">Costo Dia</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtStock" type="number" name="stock" required class="validate" minlength="5" maxlength="32">
                        <label for="txtStock">Stock</label>
                    </div> 
                    <div class="input-field col l4 s12">   
                        <select id="slEstatus" name="estatus" class="validate">
                            <option value="0" <%=(equipo.getEstatus() == 10) ? "selected" : ""%>>SELECCIONAR</option>
                            <option value="<%=Equipo.EstatusEquipo.ACTIVO%>"  <%=(equipo.getEstatus() == Equipo.EstatusEquipo.ACTIVO) ? "selected" : ""%>>ACTIVO</option>
                            <option value="<%=Equipo.EstatusEquipo.INACTIVO%>"  <%=(equipo.getEstatus() == Equipo.EstatusEquipo.INACTIVO) ? "selected" : ""%>>INACTIVO</option>
                        </select>       
                        <label for="slEstatus">Estatus</label>
                        <span id="slEstatus_error" style="color:red" class="helper-text"></span>
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Categoria/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                        <span id="slCategoria_error" style="color:red" class="helper-text"></span>
                    </div>
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Equipo" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
                        
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;                
                var slCategoria = document.getElementById("slCategoria");
                var slCategoria_error = document.getElementById("slCategoria_error");
                if (slCategoria.value == 0) {
                    slCategoria_error.innerHTML = "La Categoria es obligatoria";
                    result = false;
                } else {
                    slCategoria_error.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>

