package com.thundershop.service;

import org.springframework.web.multipart.MultipartFile;

public interface FirebaseStorageService {

    //Este metodo devuelve el url de la imagen 
    public String cargaImagen(
            MultipartFile archivoLocalCliente,
            String carpeta,
            Long id
    );

    //El BuketName es el <id_del_proyecto> + ".appspot.com#
    final String BucketName = "proyecto-daws.appspot.com";

    //Esta es la ruta básica de este proyecto Techshop
    final String rutaSuperiorStorage = "proyecto-daws";

    //Ubicación donde se encuentra el archivo de configuración Json
    final String rutaJsonFile = "firebase";

    //El nombre del archivo Json
    final String archivoJsonFile = "proyecto-daws-firebase-adminsdk-mkdsa-51d39d4de9.json";
}
