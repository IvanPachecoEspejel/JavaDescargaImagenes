package eq.pt.pm.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnlacePaginaWeb {
	
	private static BufferedReader br;

	public static List<String> getUrlImagesFromGoogleImagePage(String urlFilePageWeb){
		String linea;
		List<String> urls = new ArrayList<>();
		String url;
		try {
			br = new BufferedReader(new FileReader(new File(urlFilePageWeb)));
			while((linea = br.readLine()) != null){
				String auxUrls[] = linea.split("imgres");
				if(auxUrls.length>1)
					for(int i = 1; i<auxUrls.length; i++){
						url = auxUrls[i];
						url = url.substring(url.indexOf('=')+1, url.indexOf('&'));
						url = url.replaceAll("%3A", ":");
						url = url.replace("%2F", "/");
						url = url.replace("%25C3%25B1", "ñ");
						url = url.replace("%3F", "?");
						url = url.replace("%3D", "=");
						url = url.replace("%25", "%");
						urls.add(url);
					}
			}
		} catch (IOException e) {
			System.err.println("Error al leer las urls de la pagina: "+urlFilePageWeb+" -- Error: "+e.getMessage());
			e.printStackTrace();
		}
		finally{
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return urls;
	}
	
	public static void main(String args[]){
		List<String> urls = getUrlImagesFromGoogleImagePage("/home/ivan/Escritorio/WebPage.html");
		for(String url: urls){
			System.out.println(url);
		}
		
		System.out.println("FIN: "+urls.size());
		
	}

}
