package eq.pt.pm.negocio;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;



public class EnlaceImg {
	
	public String urlImg;
	public static String DIRECCION_SALIDA = "~/Escritorio/Img/Bicicletas/";
	
	public String extension;
	public String nombre;
	
	public static boolean REEMPLAZAR_IMGS_EXISTENTES; //true: Reemplaza, False: Ignora
	
	public EnlaceImg(String url) throws Exception{
		try{
			
			this.urlImg = url.trim();
			
			this.nombre = this.urlImg.substring(this.urlImg.lastIndexOf('/')+1, 
					this.urlImg.lastIndexOf("."));
			
			this.extension = this.urlImg.substring(this.urlImg.lastIndexOf(".")+1);
		}catch(Exception e){
			System.err.println("Error al cargar la url: "+this.urlImg+" no es una direccion de imagen valida ");
			throw e;
		}
		
	}
	
	public EnlaceImg(String url, String nombre) throws Exception{
		try{
			
			this.urlImg = url.trim();
			
			this.nombre = nombre;
			
			this.extension = this.urlImg.substring(this.urlImg.lastIndexOf(".")+1);
		}catch(Exception e){
			System.err.println("Error al cargar la url: "+this.urlImg+" no es una direccion de imagen valida ");
			throw e;
		}
		
	}
	
	public void descargarImg() throws Exception{
		try{
			File archivoImagen = crearArchivoImagen(DIRECCION_SALIDA+nombre+"."+extension);
			BufferedImage bfImg = cargarImg();
			guardarImg(bfImg, archivoImagen);
		}catch(IOException ioe){
			System.err.println("Error al descargar la imagen: "+this.urlImg+", error: "+ioe.getMessage());
			//ioe.printStackTrace();
			throw ioe;
		}catch(Exception e){
			System.err.println("Error desconocido al descargar la imagen: "+this.urlImg+", error: "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
	}
	
	private void guardarImg(BufferedImage bfImg, File archivoSalida) throws IOException, Exception{
		try{
			ImageIO.write(bfImg, extension, archivoSalida);
		}catch(IOException ioe){
			System.err.println("Error al guardar la imagen, error: "+ioe.getMessage());
			//ioe.printStackTrace();
			throw ioe;
		}catch(Exception e){
			System.err.println("Error desconocido al guardar la imagen, error: "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
	}
	
	
	private BufferedImage cargarImg() throws IOException, Exception{
		try{
			URL url = new URL(urlImg);
			BufferedImage bi = ImageIO.read(url);
			if(bi == null)
				throw new IOException("\n\tNo se pudo leer la imagen desde: "+url);
			return bi;
		}catch(IOException ioe){
			System.err.println("Error al cargar la imagen: "+urlImg+" error: "+ioe.getMessage());
			//ioe.printStackTrace();
			throw ioe;
		}catch(Exception e){
			System.err.println("Error desconocido al cargar la imagen: "+urlImg+" error: "+e.getMessage());
			//e.printStackTrace();
			throw e;
		}
	}
	
	private File crearArchivoImagen(String pathImg) throws Exception{
		try{
			File archivoSalida = new File(pathImg);
			if(archivoSalida.exists() && !REEMPLAZAR_IMGS_EXISTENTES){
				String mnsjError;
				mnsjError = "\n\t************************ Error de sobre proceso de descarga *************************";
				mnsjError += "\n\tLa imagen "+pathImg+" ya existie, no se reemplazara";
				mnsjError += "\n\t************************ Error de sobre proceso de descarga *************************";
				throw new IOException(mnsjError);
			}
			return archivoSalida;
		}catch(IOException ioe){
			System.err.println("Error al Crear el archivo vacio para guardar la imagen: "+ioe.getMessage());
			throw ioe;
		}catch(Exception e){
			System.err.println("Error desconocido al Crear el archivo vacio para guardar la imagen: "+e.getMessage());
			throw e;
		}
		
	}
	
}
