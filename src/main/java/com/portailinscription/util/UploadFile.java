package com.portailinscription.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.jpeg.JpegDirectory;
import com.portailinscription.controller.TravailleurController;
import com.portailinscription.dao.impl.DocumentDAOImpl;
import com.portailinscription.dao.impl.TravailleurDAOImpl;
import com.portailinscription.form.TravailleurForm;
import com.portailinscription.model.Document;
import com.portailinscription.model.Travailleur;

public class UploadFile {
	static Logger log = Logger.getLogger(TravailleurController.class.getName());
	private SimpleDateFormat dt1;
	private CipherUtilSecret cipherUtil;
	
	public UploadFile() {
		super();
		dt1 = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
		cipherUtil = new CipherUtilSecret();
	}

	public void uploadFiles(HttpServletRequest request, TravailleurForm travailleurForm, int idTravailleur, List<String> listFichier, 
			Travailleur travailleur, DocumentDAOImpl documentDao, TravailleurDAOImpl travailleurDao, int id) throws IOException{
	    String idTravailleurCrypte = cipherUtil.encrypt(String.valueOf(idTravailleur));
		String rootPath = request.getServletContext().getRealPath("/resources");
		File dir = new File(rootPath + File.separator + "fichiers" + File.separator + idTravailleurCrypte);
		if (!dir.exists())
			dir.mkdirs();
		File[] list = dir.listFiles();
		boolean exist = false;
		MultipartFile multipartFile2 = travailleurForm.getPhotoFichier();
		File photo = multipartToFile(multipartFile2, rootPath, idTravailleurCrypte);
		for (int i = 0; i < travailleurForm.getFile().length; i++) {
			MultipartFile multipartFile = travailleurForm.getFile()[i];
			String date = travailleurForm.getDate()[i];
			File newFile = multipartToFile(multipartFile, rootPath, idTravailleurCrypte);
			Document document;
			if(list.length != 0) {
				for (File file : list) {
			    	if(file.isFile() && file.getName().equals(newFile.getName()) && FileUtils.contentEquals(file, newFile))
			    	{
			    		exist = true;
			    		break;
			    	}
				}
			}
			if(!exist) {
				File serverFile = new File(dir.getAbsolutePath() + File.separator + newFile.getName());
				try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))){
					byte[] bytes = multipartFile.getBytes();
					document = new Document();
					document.setNom(listFichier.get(i));
					document.setNomFichier(multipartFile.getOriginalFilename());
					document.setDateValidite(dt1.parse(date));
					document.setTravailleur(travailleur);
					documentDao.insert(document);
					stream.write(bytes);
					stream.close();	
				}
				catch(ParseException ex) {
					log.info(ex);
					throw new IllegalArgumentException(ex.getMessage());
				}
			}
			else {
				Travailleur travailleurFound = travailleurDao.find(id);
				document = documentDao.getDocumentByTravailleurAndName(id, listFichier.get(i));
				try{
					document.setDateValidite(dt1.parse(date));
				}
				catch(ParseException ex){
					log.info(ex);
					throw new IllegalArgumentException(ex.getMessage());
				}
				document.setTravailleur(travailleurFound);
				documentDao.update(document);
			}
		}
	}
	
	public void removeFiles(HttpServletRequest request, int idTravailleur) throws IOException{
		String rootPath = request.getServletContext().getRealPath("/resources");
		String idTravailleurCrypte = cipherUtil.encrypt(String.valueOf(idTravailleur));
		File dir = new File(rootPath + File.separator + "fichiers" + File.separator + idTravailleurCrypte);
		FileUtils.deleteQuietly(dir);
	}
	
	public File multipartToFile(MultipartFile multipart, String rootPath, String idTravailleurCrypte) throws IOException 
	{
	    File convFile = new File(rootPath + File.separator + "fichiers" + File.separator + idTravailleurCrypte + File.separator + multipart.getOriginalFilename());
	    multipart.transferTo(convFile);
	    return convFile;
	}
	
	public boolean imageInvalide(HttpServletRequest request, MultipartFile fichierPhoto, int idTravailleur) throws IOException, MetadataException
	{
		String idTravailleurCrypte = cipherUtil.encrypt(String.valueOf(idTravailleur));
		String rootPath = request.getServletContext().getRealPath("/resources");
		File dir = new File(rootPath + File.separator + "fichiers" + File.separator + idTravailleurCrypte);
		if (!dir.exists())
			dir.mkdirs();
		File photo = multipartToFile(fichierPhoto, rootPath, idTravailleurCrypte);
		int height = 0;
		int width = 0;
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(photo);
			JpegDirectory directory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
			height = directory.getImageHeight();
			width = directory.getImageWidth();
		} catch (ImageProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(height > 220 || width > 155)
		{
			removeFiles(request, idTravailleur);
			return true;
		}
		else
		{
			return false;
		}
	}
}
