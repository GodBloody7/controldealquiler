
package controlalquiler.appweb.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import controlalquiler.accesoadatos.ClienteDAL;
import controlalquiler.appweb.utils.*;
import controlalquiler.entidadesdenegocio.Cliente;

@WebServlet(name = "ClienteServlet", urlPatterns = {"/Cliente"})
public class ClienteServlet extends HttpServlet {
    
     private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Cliente cliente = new Cliente();
            cliente.setTop_aux(10);
            ArrayList<Cliente> clientes = ClienteDAL.buscar(cliente);
            request.setAttribute("clientes", clientes);
            request.setAttribute("top_aux", cliente.getTop_aux());
            request.getRequestDispatcher("Views/Cliente/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Cliente cliente = obtenerCliente(request);
            ArrayList<Cliente> clientes = ClienteDAL.buscar(cliente);
            request.setAttribute("clientes", clientes);
            request.setAttribute("top_aux", cliente.getTop_aux());
            request.getRequestDispatcher("Views/Cliente/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
  
    private Cliente obtenerCliente(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Cliente cliente = new Cliente();
        if (accion.equals("create") == false) {
            cliente.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        cliente.setNombre(Utilidad.getParameter(request, "nombre", ""));
        cliente.setApellido(Utilidad.getParameter(request, "apellido", ""));
        cliente.setRazonsocial(Utilidad.getParameter(request, "razonsocial", ""));
        cliente.setTelefono(Utilidad.getParameter(request, "telefono", ""));
        cliente.setDireccion(Utilidad.getParameter(request, "direccion", ""));
        
        if (accion.equals("index")) {
            cliente.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            cliente.setTop_aux(cliente.getTop_aux() == 0 ? Integer.MAX_VALUE : cliente.getTop_aux());
        }
        
        return cliente;
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Cliente/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Cliente cliente = obtenerCliente(request);
            int result = ClienteDAL.crear(cliente);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro guardar el nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }

    }

  private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Cliente cliente = obtenerCliente(request);
            Cliente cliente_result = ClienteDAL.obtenerPorId(cliente);
            if (cliente_result.getId() > 0) {
                request.setAttribute("cliente", cliente_result);
            } else {
                Utilidad.enviarError("El Id:" + cliente.getId() + " no existe en la tabla de Rol", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Empresa/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Cliente cliente = obtenerCliente(request);
            int result = ClienteDAL.modificar(cliente);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Cliente/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Cliente/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Cliente cliente = obtenerCliente(request);
            int result = ClienteDAL.eliminar(cliente);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doGetRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doGetRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doGetRequestDelete(request, response);
                    break;
                case "details":
                    request.setAttribute("accion", accion);
                    doGetRequestDetails(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doPostRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doPostRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doPostRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doPostRequestDelete(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
    
// </editor-fold> 
}
