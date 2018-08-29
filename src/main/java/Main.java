
import dao.Sql2oUsuarioDao;
import encapsulacion.Usuario;
import freemarker.template.Configuration;
import encapsulacion.Articulo;
import encapsulacion.Comentario;
import encapsulacion.Etiqueta;
import encapsulacion.Usuario;
import hibernate.HibernateUtil;
import servicios.*;
import org.sql2o.Sql2o;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import Rutas.RutasWeb;
import spark.template.freemarker.FreeMarkerEngine;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.*;



public class Main {

    public static void main(String[] args) throws SQLException {

        Hash hash = null;
        ConnectionService.startDb();
        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        configuration.setClassForTemplateLoading(Main.class, "/templates");
        Spark.staticFileLocation("/public/");

        HibernateUtil.buildSessionFactory().openSession().close();

        Sql2oUsuarioDao usuarioadmin = new Sql2oUsuarioDao(Usuario.class);
        if(usuarioadmin.searchByUsername("admin") == null){
        Usuario usuarioPorDefecto = new Usuario(1L, "admin", "Administrador", hash.sha1("admin"), true, false);
        usuarioadmin.add(usuarioPorDefecto);}

        HibernateUtil.openSession().close();

        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(configuration);


        new RutasWeb(freeMarkerEngine);


    }
}