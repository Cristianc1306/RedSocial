package rs.test;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.TreeMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.datastructures.Graph;
//import datastructure.TreeMap;

import rs.datos.CargarParametros;
import rs.datos.Dato;
import rs.logica.Calculo;
import rs.modelo.Relacion;
import rs.modelo.Usuario;
public class TestRedesSociales {
	private Calculo calculo;
	private Usuario u1;
	private Usuario u2;
	private TreeMap<String, Usuario> usuarios;
	private List<Relacion> relaciones;
	
	@Before
	public void setUp() throws Exception {
		try {
			CargarParametros.parametros();
		} catch (IOException e) {
			System.err.print("Error al cargar parametros");
			System.exit(-1);
		}

		// Cargar datos
		usuarios = null;

		List<Relacion> relaciones = null;
		try {
			usuarios = Dato.cargarUsuarios(CargarParametros.getArchivoUsuario());

			relaciones = Dato.cargarRelaciones(CargarParametros.getArchivoRelacion(), usuarios);

		} catch (FileNotFoundException e) {
			System.err.print("Error al cargar archivos de datos");
			System.exit(-1);
		}

		calculo = new Calculo(usuarios, relaciones);
		
		u1 = usuarios.get("juan");
		u2 = usuarios.get("marcos");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAmigosDe() {
		List<Usuario> resultadObtenido = calculo.amigosDe(u1);
		assertTrue(calculo.amigosDe(u1).size() == resultadObtenido.size()
				&& calculo.amigosDe(u1).containsAll(resultadObtenido));		
	}
	
	@Test
	public void testTiempoDeAmistad() {
		assertEquals(calculo.tiempoDeAmistad(u1, u2), 3  , 0.01);
	}
	
	@Test
	public void testOrdenMasInfluyentes() {
		List<Usuario> resultadObtenido = calculo.masInfluyentes();
		assertTrue(calculo.masInfluyentes().size() == resultadObtenido.size()
				&& calculo.masInfluyentes().containsAll(resultadObtenido));		
	}
	
	@Test
	public void testUsuarioMasInfluyentes() {
		Usuario u3 = calculo.masInfluyente();
		assertTrue(calculo.masInfluyente().equals(u3));		
	}
	
	@Test
	public void testUsuarioQueMasInteractuanEntreSi() {
		TreeMap<String,Usuario> resultado= calculo.usuariosQueMasInteractúanEntreSi();

		assertTrue(calculo.usuariosQueMasInteractúanEntreSi().equals(resultado));		
	}
	
	@Test
	public void testUsuarioQueMasInteractuaEnRedes() {
		Usuario u3 = calculo.usuarioQueMasIteractuaEnRedes();
		assertTrue(calculo.usuarioQueMasIteractuaEnRedes().equals(u3));		
	}
	
	
	@Test
	public void testGradoPromedio() {
		double arcos = calculo.getRedSocial().numEdges() * 2;
		double vertices = calculo.getRedSocial().numVertices();
		assertEquals(calculo.gradoPromedio(), arcos/vertices , 0.1);
	}
		
	@Test
	public void testCaminoMasNuevo() {
		List<Usuario> resultadObtenido = calculo.caminoMasNuevo(u1.getId(),u2.getId());
		assertTrue(calculo.caminoMasNuevo(u1.getId(),u2.getId()).size() == resultadObtenido.size()
				&& calculo.caminoMasNuevo(u1.getId(), u2.getId()).containsAll(resultadObtenido));		
	}
	
	@Test
	public void testSugerenciaDeAmistad() {
		Usuario u3 = calculo.sugerenciaNuevaAmistad(u1);
		assertTrue(calculo.sugerenciaNuevaAmistad(u1).equals(u3));		
	}

	@Test
	public void testDeGrafo() {
		Graph<Usuario,Relacion> resultadObtenido = calculo.getRedSocial();
		assertTrue(calculo.getRedSocial().numEdges() == resultadObtenido.numEdges()
				&& calculo.getRedSocial().numVertices() == resultadObtenido.numVertices()
				&& calculo.getRedSocial().equals(resultadObtenido));
				
	}
}