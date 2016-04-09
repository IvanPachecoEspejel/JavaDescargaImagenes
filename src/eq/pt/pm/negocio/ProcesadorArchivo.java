package eq.pt.pm.negocio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ProcesadorArchivo {
	
	private	BufferedReader br;
	private String pathArchivo;
	
	public ProcesadorArchivo(String pathArchivo) throws Exception{
		try {
			this.pathArchivo = pathArchivo;
			br = new BufferedReader(new FileReader(pathArchivo));
		} catch (FileNotFoundException fnfe) {
			System.err.println("Error al leer el archivo de urls: "+fnfe.getMessage());
			fnfe.printStackTrace();
			throw fnfe;
		} catch (Exception e){
			System.err.println("Error desconocido al leer el archivo de urls: "+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public String getSigUrl() throws Exception{
		String linea;
		try {
			linea = br.readLine();
			if(linea != null && linea.isEmpty())
				return getSigUrl();
			return linea;
		} catch (IOException | NullPointerException  io_np_e) {
			System.err.println("Error al leer una linea del archivo: "+this.pathArchivo+" error: "+io_np_e.getMessage());
			io_np_e.printStackTrace();
			throw io_np_e;
		} catch (Exception e){
			System.err.println("Error desconocido al leer una linea del archivo: "+this.pathArchivo+" error: "+e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	
//	public static void main(String args[]){
//		try{
//			String linea;
//			ProcesadorArchivo pa = new ProcesadorArchivo("/home/ivan/Escritorio/preuba.txt");
//			while((linea = pa.getSigUrl()) != null)
//				System.out.println(linea);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
}
