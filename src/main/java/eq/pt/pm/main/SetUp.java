package eq.pt.pm.main;

import java.io.File;
import java.util.List;

import eq.pt.pm.negocio.EnlaceImg;
import eq.pt.pm.negocio.EnlacePaginaWeb;
import eq.pt.pm.negocio.ProcesadorArchivo;

public class SetUp {

	public static ProcesadorArchivo pa;
	
	private static List<String> urlsPaginaWeb;
	private static int opcionCargaUrls;
	private static String sufijoImg;
	private static int numImg;

	public static void main(String args[]){
		if(args.length < 3 || args.length > 6){
			imprimirAyuda();
			return;
		}

		if(!args[1].endsWith(File.separator))
			args[1] += File.separator;
		EnlaceImg.DIRECCION_SALIDA = args[1];
		
		int numeroHilos;
		try{
			numeroHilos = Integer.parseInt(args[2]);
		}catch(NumberFormatException nfe){
			imprimirAyuda();
			return;
		}
		
		if(args.length == 6 && args[5].equals("1"))
			EnlaceImg.REEMPLAZAR_IMGS_EXISTENTES = true;
		else
			EnlaceImg.REEMPLAZAR_IMGS_EXISTENTES = false;
		
		sufijoImg = args[3];
		
		opcionCargaUrls = Integer.parseInt(args[4]);
		
		if(opcionCargaUrls == 1){
			try {
				pa = new ProcesadorArchivo(args[0]);
			} catch (Exception e1) {
				System.out.println("Error al cargar el archivo de urls: "+e1.getMessage());
				return;
			}
		}else{
			urlsPaginaWeb = EnlacePaginaWeb.getUrlImagesFromGoogleImagePage(args[0]);
		}
		
				
		for(int i = 0 ; i< numeroHilos; i++){
			try{
				new Descargador(args[0]).start();
			}catch(Exception e){
				System.err.println("Error al crear el hilo: "+i);
			}
		}

	}
	
	public static synchronized String getSigUrl(){
		try {
			if(opcionCargaUrls == 1)
				return pa.getSigUrl();
			else
				return urlsPaginaWeb.remove(0);
		} catch (Exception e) {
			System.out.println("Error al agregar url a la pila de urls por procesar: "+e.getMessage());
			return null;
		}
	}
	
	private static void imprimirAyuda(){
		System.out.println("Para ejecutar el programa es necesario pasar los siguientes argumentos:");
		System.out.println("java DescargarImagenes <ArchivoUrls> <CarpetaSalida> <numeroHilos> <sufijoImg> 1 <[opcional:false]Reempzar Imgs Existentes>");
		System.out.println(" o ");
		System.out.println("java DescargarImagenes <urlPaginaWebGoogleImage> <CarpetaSalida> <numeroHilos> <sufijoImg> 2 <[opcional:false]Reempzar Imgs Existentes>");
		System.out.println("El parametro <numeroHilos> debe ser entero");
		System.out.println("El paramentro <Reemplazar Imgs Existentes> debe ser 1 para reemplazar las imagenes existentes");
	}

	private static synchronized int getSigNumImg() {
		return numImg++;
	}

	public static class Descargador extends Thread{ 
		int numImgDescargadas;
		boolean pararHilo;
		public Descargador(String pathArchivoUrls)throws Exception{
			pararHilo = false;
		}

		public void run(){
			String linea;
			EnlaceImg ei;
			numImgDescargadas = 0;
			try{
				while(!pararHilo && (linea = getSigUrl()) != null){
					try {
						System.out.println("HILO :"+this.getId()+" descargadno y guardando : "+linea +" ...");
						ei = new EnlaceImg(linea, sufijoImg+"_"+getSigNumImg());
						ei.descargarImg();
						++numImgDescargadas;
						System.out.println("HILO :"+this.getId()+" descargadno y guardando : "+linea +" <ok>");
						
					} catch (Exception e) {
						System.err.println("HILO: "+this.getId()+" Error: "+e.getMessage());
					}
				}
			}catch(Exception e){
				System.out.println("No se ejecuta el hilo :"+this.getId()+" error: "+e.getMessage());
				return;
			}
			System.out.println("El hilo "+this.getId()+" descargo y guardo: "+numImgDescargadas+" imagenes");
		}
	}

}
