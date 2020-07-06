package ws;

import service.InicioService;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author KEVIN
 */
@WebService(serviceName = "InicioWebService")
public class InicioWebService {

    InicioService inicioServie = new InicioService();

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "iniciarSesion")
    public String iniciarSesion(@WebParam(name = "usuario") String usuario, @WebParam(name = "contraseña") String contraseña) {
        return inicioServie.IniciarSesion(usuario, contraseña);
    }
}
