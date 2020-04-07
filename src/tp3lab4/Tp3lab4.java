/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3lab4;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Leandro
 */
public class Tp3lab4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Logger mongoLog = Logger.getLogger("org.mongodb.drive");

        mongoLog.setLevel(Level.SEVERE);
        String uri = "mongodb+srv://leandro:leandro@leandro-rptpo.mongodb.net/test?retryWrites=true&w=majority";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("paises_db");
        MongoCollection<Document> collection = mongoDatabase.getCollection("paises");
        Document document = new Document();

        String datosJson = "https://restcountries.eu/rest/v2/callingcode/";

        JSONParser parser = new JSONParser();
 for (int i = 1; i <= 300; i++) {

            try {

                URL link = new URL(datosJson + i);
                URLConnection yc = link.openConnection();

                BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                JSONArray arrjson = (JSONArray) parser.parse(in.readLine());
                if (arrjson != null) {
                    for (Object object : arrjson) {
                        JSONObject paisJson = (JSONObject) object;
                        document.append("codigoPais", i);
                        document.append("nombrePais", paisJson.get("name"));
                        document.append("capitalPais", paisJson.get("capital"));
                        document.append("region", paisJson.get("region"));
                        document.append("poblacion", paisJson.get("population"));
                        List coord = (List) paisJson.get("latlng");
                        document.append("latitud", (double) coord.get(0));
                        document.append("longitud", (double) coord.get(1));
                        document.append("superficie", paisJson.get("area"));
                        collection.insertOne(document);
                        document.clear();
                        System.out.println("Existe el pais , código: " + i);
                    }
                } else {
                    continue;
                }
                in.close();
            } catch (Exception e) {
                System.out.println("No existe un pais con el código: " + i);
            }
        }
         System.gc();
         Metodos met = new Metodos();
         met.imprimeAmericas(collection);
         met.regionPoblacion(collection);
         met.distintoAfrica(collection);
         met.updateEgypt(collection);
         met.deleteCod258(collection);
         met.poblacionMayorqueMenorque(collection);
         met.ordenadoPorNombre(collection);
         met.codigoAindex(collection);
         
    }

}
