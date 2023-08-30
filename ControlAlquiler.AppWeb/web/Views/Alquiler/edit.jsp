<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="controlalquiler.entidadesdenegocio.Alquiler"%>
<% Alquiler alquiler = (Alquiler) request.getAttribute("alquiler");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Alquiler</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Alquiler</h5>
            <form action="Alquiler" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=alquiler.getId()%>">  
                <div class="row">
                                       
  
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Cliente/select.jsp">                           
                            <jsp:param name="id" value="<%=alquiler.getIdCliente() %>" />  
                        </jsp:include>  
                        <span id="slCliente_error" style="color:red" class="helper-text"></span>
                    </div>
                    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Usuario/select.jsp">                           
                            <jsp:param name="id" value="<%=alquiler.getIdUsuario() %>" />  
                        </jsp:include>  
                        <span id="slUsuario_error" style="color:red" class="helper-text"></span>
                    </div>
                    
                   
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Alquiler" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
                        
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;
                var slCliente = document.getElementById("slCliente");
                var slCliente_error = document.getElementById("slCliente_error");
                if (slCliente.value == 0) {
                    slCliente_error.innerHTML = "El Cliente es obligatorio";
                    result = false;
                } else {
                    slCliente_error.innerHTML = "";
                }
                
                var slUsuario = document.getElementById("slUsuario");
                var slUsuario_error = document.getElementById("slUsuario_error");
                if (slUsuario.value == 0) {
                    slUsuario_error.innerHTML = "El Usuario es obligatoria";
                    result = false;
                } else {
                    slUsuario_error.innerHTML = "";
                }
            
                return result;
            }
        </script>
    </body>
</html>