package controlalquiler.appweb.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import controlalquiler.accesoadatos.CategoriaDAL;
import controlalquiler.accesoadatos.EquipoDAL;
import controlalquiler.appweb.utils.*;
import controlalquiler.entidadesdenegocio.Categoria;
import controlalquiler.entidadesdenegocio.Equipo;

@WebServlet(name = "EquipoServlet", urlPatterns = {"/Equipo"})
public class EquipoServlet extends HttpServlet {
    
    private Equipo obtenerEquipo(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Equipo equipo = new Equipo();
        if (accion.equals("create") == false) {
            equipo.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        equipo.setNombre(Utilidad.getParameter(request, "nombre", ""));
        equipo.setDescripcion(Utilidad.getParameter(request, "descripcion", ""));
        equipo.setCostoDia(Double.parseDouble(Utilidad.getParameter(request, "costoDia", "")));
        equipo.setStock(Integer.parseInt(Utilidad.getParameter(request, "stock", "")));
        equipo.setIdCategoria(Integer.parseInt(Utilidad.getParameter(request, "idCategoria", "0")));
        equipo.setEstatus(Byte.parseByte(Utilidad.getParameter(request, "status", "0")));
        
        if (accion.equals("index")) {
            equipo.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            equipo.setTop_aux(equipo.getTop_aux() == 0 ? Integer.MAX_VALUE : equipo.getTop_aux());
        }
        
        return equipo;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Equipo equipo = new Equipo();
            equipo.setTop_aux(10);
            ArrayList<Equipo> equipos = EquipoDAL.buscarIncluirCategoria(equipo);
            request.setAttribute("equipos", equipos);
            request.setAttribute("top_aux", equipo.getTop_aux());
            request.getRequestDispatcher("Views/Equipo/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Equipo equipo = obtenerEquipo(request);
            ArrayList<Equipo> equipos = EquipoDAL.buscarIncluirCategoria(equipo);
            request.setAttribute("equipos", equipos);
            request.setAttribute("top_aux", equipo.getTop_aux());
            request.getRequestDispatcher("Views/Equipo/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Equipo/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Equipo equipo = obtenerEquipo(request);
            int result = EquipoDAL.crear(equipo);
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
            Equipo equipo = obtenerEquipo(request);
            Equipo equipo_result = EquipoDAL.obtenerPorId(equipo);
            if (equipo_result.getId() > 0) {
                Categoria categoria = new Categoria();
                categoria.setId(equipo_result.getIdCategoria());
                equipo_result.setCategoria(CategoriaDAL.obtenerPorId(categoria));
                request.setAttribute("equipo", equipo_result);
            } else {
                Utilidad.enviarError("El Id:" + equipo_result.getId() + " no existe en la tabla de Equipos", request, response);
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
            Equipo equipo = obtenerEquipo(request);
            int result = EquipoDAL.modificar(equipo);
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
        request.getRequestDispatcher("Views/Equipo/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Equipo/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Equipo equipo = obtenerEquipo(request);
            int result = EquipoDAL.eliminar(equipo);
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
