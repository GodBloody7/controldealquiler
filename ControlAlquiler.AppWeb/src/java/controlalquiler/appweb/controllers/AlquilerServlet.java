package controlalquiler.appweb.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import controlalquiler.accesoadatos.UsuarioDAL;
import controlalquiler.accesoadatos.AlquilerDAL;
import controlalquiler.accesoadatos.ClienteDAL;
import controlalquiler.appweb.utils.*;
import controlalquiler.entidadesdenegocio.Usuario;
import controlalquiler.entidadesdenegocio.Alquiler;
import controlalquiler.entidadesdenegocio.Cliente;


@WebServlet(name = "AlquilerServlet", urlPatterns = {"/Alquiler"})
public class AlquilerServlet extends HttpServlet {
    
    
 private Alquiler obtenerAlquiler(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Alquiler alquiler = new Alquiler();
        alquiler.setIdCliente(Integer.parseInt(Utilidad.getParameter(request, "idCliente", "0")));
        alquiler.setIdUsuario(Integer.parseInt(Utilidad.getParameter(request, "idUsuario", "0")));
        
        if (accion.equals("index")) {
            alquiler.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            alquiler.setTop_aux(alquiler.getTop_aux() == 0 ? Integer.MAX_VALUE : alquiler.getTop_aux());
        }
        
        
        return alquiler;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Alquiler alquiler = new Alquiler();
            alquiler.setTop_aux(10);
            ArrayList<Alquiler> alquileres = AlquilerDAL.buscarIncluirRelaciones(alquiler);
            request.setAttribute("alquileres", alquileres);
            request.setAttribute("top_aux", alquiler.getTop_aux());
            request.getRequestDispatcher("Views/Alquiler/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Alquiler alquiler = obtenerAlquiler(request);
            ArrayList<Alquiler> alquileres = AlquilerDAL.buscarIncluirRelaciones(alquiler);
            request.setAttribute("alquileres", alquileres);
            request.setAttribute("top_aux", alquiler.getTop_aux());
            request.getRequestDispatcher("Views/Alquiler/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Alquiler/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Alquiler alquiler = obtenerAlquiler(request);
            int result = AlquilerDAL.crear(alquiler);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }

    }

    private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Alquiler alquiler = obtenerAlquiler(request);
            Alquiler alquiler_result = AlquilerDAL.obtenerPorId(alquiler);
            if (alquiler_result.getId() > 0) {
                Cliente cliente = new Cliente();
                cliente.setId(alquiler_result.getIdCliente());
                alquiler_result.setCliente(ClienteDAL.obtenerPorId(cliente));
                request.setAttribute("alquiler", alquiler_result);
            } else {
                Utilidad.enviarError("El Id:" + alquiler_result.getId() + " no existe en la tabla de Alquiler", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Alquiler/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Alquiler alquiler = obtenerAlquiler(request);
            int result = AlquilerDAL.modificar(alquiler);
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
        request.getRequestDispatcher("Views/Alquiler/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Alquiler/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Alquiler alquiler = obtenerAlquiler(request);
            int result = AlquilerDAL.eliminar(alquiler);
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