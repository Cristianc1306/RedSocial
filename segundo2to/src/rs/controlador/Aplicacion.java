package rs.controlador;

import org.apache.log4j.Logger;

import rs.negocio.Subject;
import rs.gui.DesktopFrame;
import rs.gui.RelacionesForm;
import rs.gui.RelacionesList;
import rs.gui.UsuariosForm;
import rs.gui.UsuariosList;
import rs.negocio.Calculo;
import rs.negocio.RedSocial;
/**
 * Aplicación Red Social para ser presentado como proyecto ISFPP 2022
 * 
 * @author Cristian, Cesar
 *
 */
public class Aplicacion {
	
	final static Logger logger = Logger.getLogger(Aplicacion.class);
	
	private Subject subject;
    private RedSocial redSocial;
	private Coordinador coordinador;
	private Calculo calculo;
	private DesktopFrame desktopFrame;
	private UsuariosForm usuariosForm;
	private UsuariosList usuariosList;
	private RelacionesForm relacionesForm;
	private RelacionesList relacionesList;

	/** Java main*/
	public static void main(String[] args) {
		
		Aplicacion miAplicacion = new Aplicacion();
		miAplicacion.iniciar();

	}

	/**inicia la aplicación, el observer, la red social, el coordinador y las ventanas*/
	private void iniciar() {
		
		subject = new Subject();
		redSocial=new RedSocial(subject);
		calculo = new Calculo(subject,redSocial.getUsuarios(),redSocial.getRelaciones());
		coordinador = new Coordinador();
		desktopFrame = new DesktopFrame();
		usuariosForm = new UsuariosForm();
		usuariosList = new UsuariosList();
		relacionesForm = new RelacionesForm();
		relacionesList = new RelacionesList();
		
        calculo.setCoordinador(coordinador);
		desktopFrame.setCoordinador(coordinador);
		usuariosForm.setCoordinador(coordinador);
		usuariosList.setCoordinador(coordinador);
		relacionesForm.setCoordinador(coordinador);
		relacionesList.setCoordinador(coordinador);
	
        coordinador.setRedSocial(redSocial);
		coordinador.setCalculo(calculo);
		coordinador.setDesktopFrame(desktopFrame);
		coordinador.setUsuariosForm(usuariosForm);
		coordinador.setUsuariosList(usuariosList);
		coordinador.setRelacionesForm(relacionesForm);
		coordinador.setRelacionesList(relacionesList);
		
		logger.debug("Iniciando Aplicacion");
		desktopFrame.setVisible(true);
		
	}

}
