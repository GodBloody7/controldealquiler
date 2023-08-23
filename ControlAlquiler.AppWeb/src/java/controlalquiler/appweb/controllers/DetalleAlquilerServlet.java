
package controlalquiler.appweb.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import controlalquiler.accesoadatos.AlquilerDAL;
import controlalquiler.accesoadatos.EquipoDAL;
import controlalquiler.accesoadatos.DetalleAlquilerDAL;
import controlalquiler.appweb.utils.*;
import controlalquiler.entidadesdenegocio.Alquiler;
import controlalquiler.entidadesdenegocio.Equipo;
import controlalquiler.entidadesdenegocio.DetalleAlquiler;

@WebServlet(name = "DetalleAlquilerServlet", urlPatterns = {"/DetalleAlquiler"})
public class DetalleAlquilerServlet extends HttpServlet {
    
    private DetalleAlquiler obtenerDetalleAlquiler(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        DetalleAlquiler detalle = new DetalleAlquiler();
        if (accion.equals("create") == false) {
            detalle.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        detalle.setCantidad(Integer.parseInt(Utilidad.getParameter(request, "cantidad", "")));
        detalle.setPrecio(Double.parseDouble(Utilidad.getParameter(request, "precio", "")));
        detalle.setSubTotal(Double.parseDouble(Utilidad.getParameter(request, "subTotal", "")));
        detalle.setIdAquiler(Integer.parseInt(Utilidad.getParameter(request, "idAlquiler", "0")));
        detalle.setIdEquipo(Integer.parseInt(Utilidad.getParameter(request, "idEquipo", "0")));
        
        if (accion.equals("index")) {
            detalle.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            detalle.setTop_aux(detalle.getTop_aux() == 0 ? Integer.MAX_VALUE : detalle.getTop_aux());
        }
        
        return detalle;
    }

    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DetalleAlquiler detalle = new DetalleAlquiler();
            detalle.setTop_aux(10);
            ArrayList<DetalleAlquiler> detalles = DetalleAlquilerDAL.buscarIncluirRelaciones(detalle);
            request.setAttribute("detalles", detalles);
            request.setAttribute("top_aux", detalle.getTop_aux());
            request.getRequestDispatcher("Views/DetalleAlquiler/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DetalleAlquiler detalle = obtenerDetalleAlquiler(request);
            ArrayList<DetalleAlquiler> detalles = DetalleAlquilerDAL.buscarIncluirRelaciones(detalle);
            request.setAttribute("detalles", detalles);
            request.setAttribute("top_aux", detalle.getTop_aux());
            request.getRequestDispatcher("Views/Empresa/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/DetalleAlquiler/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DetalleAlquiler detalle = obtenerDetalleAlquiler(request);
            int result = DetalleAlquilerDAL.crear(detalle);
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
            DetalleAlquiler detalle = obtenerDetalleAlquiler(request);
            DetalleAlquiler detalle_result = DetalleAlquilerDAL.obtenerPorId(detalle);
            if (detalle_result.getId() > 0) {
                Alquiler alquiler = new Alquiler();
                alquiler.setId(detalle_result.getIdAquiler());
                detalle_result.setAlquiler(AlquilerDAL.obtenerPorId(alquiler));              
                Equipo equipo = new Equipo();
                equipo.setId(detalle_result.getIdEquipo());
                detalle_result.setEquipo(EquipoDAL.obtenerPorId(equipo));
                
                request.setAttribute("detalle", detalle_result);
            } else {
                Utilidad.enviarError("El Id:" + detalle_result.getId() + " no existe en la tabla de Detalles Alquiler", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/DetalleAlquiler/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DetalleAlquiler detalle = obtenerDetalleAlquiler(request);
            int result = DetalleAlquilerDAL.modificar(detalle);
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
        request.getRequestDispatcher("Views/DetalleAlquiler/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/DetalleAlquiler/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            DetalleAlquiler detalle = obtenerDetalleAlquiler(request);
            int result = DetalleAlquilerDAL.eliminar(detalle);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
