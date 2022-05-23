package at.technikum.tourplanner.business.mapQuest;

import at.technikum.tourplanner.config.ConfigurationManager;
import at.technikum.tourplanner.config.ConfigurationManagerImpl;
import at.technikum.tourplanner.business.net.NetworkCommunicationService;
import at.technikum.tourplanner.business.net.NetworkCommunicationServiceImpl;
import at.technikum.tourplanner.database.dao.RouteImageDao;
import at.technikum.tourplanner.database.fileServer.FileAccess;
import at.technikum.tourplanner.database.fileServer.FileAccessImpl;
import at.technikum.tourplanner.database.sqlServer.RouteImageDaoImpl;
import at.technikum.tourplanner.models.Route;
import at.technikum.tourplanner.models.RouteImage;
import at.technikum.tourplanner.models.Tour;
import at.technikum.tourplanner.models.Transporter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.Time;


public class MapQuestServiceImpl implements MapQuestService {

    //LOGGER
    private final static Logger log = LogManager.getLogger(ConfigurationManagerImpl.class.getName());
    //CONFIG
    private static ConfigurationManager config = new ConfigurationManagerImpl();
    //NET
    private NetworkCommunicationService net = new NetworkCommunicationServiceImpl();

    private RouteImageDao routeImageDao = new RouteImageDaoImpl();
    private Tour currentTour;

    // START Image
    @Override
    public Route startRoute(String from, String to, Transporter transporter) {
        log.debug("start Route search - From : " + from + " To : " + to + "Transporter: " + transporter);
        final Route[] route = new Route[1];
        // SET ROUTE + IMAGE
        route[0] = searchRoute(from, to, transporter);
        if (route[0] == null) {
            return null;
        }
        //  set image setting
        route[0] = setImageSettingsToRoute(route[0]);
        // save in DataBase
        saveImageOnline(route[0].getRouteImage());
        // DOWNLOAD  IMAGE
        saveImageDataOnline(route[0]);
        return route[0];
    }

    @Override
    public Route startRoute(Tour tour) {
        currentTour = tour;
        return startRoute(tour.getFrom(), tour.getTo(), tour.getTransporter());
    }


    // ######################################################### //
    // 1. SEARCH
    @Override
    public Route searchRoute(String from, String to, Transporter transporter) {
        // Create a new Route - with Builder
        Route currentRoute = this.routeBuilder(from, to, transporter);
        return RouteMapper(currentRoute);
    }

    //1.1 BUILD - KEY  + FROM, TO TRANSPORT
    private Route routeBuilder(String from, String to, Transporter transporter) {
        log.debug("Build ROUTE");
        Route route = Route.builder()
                .key("?key=" + config.getMapQuestID())
                .from(from)
                .to(to)
                .build();
        route.setUrlRoute("http://www.mapquestapi.com/directions/v2/route" + route.getKey() + "&from=" + route.getFrom() + "&to=" + route.getTo() + "&routeType=" + transporter.toString() + "&unit=k");
        return route;
    }

    // 1.2 Mapper --> search in the INTERNET
    private Route RouteMapper(Route currentRoute) {
        log.debug("start ROUTE INTERNET SEARCH" + currentRoute);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String body = net.getResponseBodyByURL(currentRoute.getUrlRoute());

            if (body.equals("")) {
                log.error("route body - empty");
                return null;
            }

            if (!mapper.readTree(body).get("route").get("routeError").get("errorCode").toString().equals("-400")) {
                log.error("route body - error");
                return null;
            }
            currentRoute.setRouteBody(mapper.readTree(body).get("route"));
            currentRoute.setDistance(Double.valueOf(mapper.readTree(body).get("route").get("distance").toString()));
            currentRoute.setTime(Time.valueOf(mapper.readTree(body).get("route").get("formattedTime").toString().replace("\"", "")));
            currentRoute.setSessionID("&session=" + currentRoute.getRouteBody().get("sessionId").toString().replace("\"", ""));
            String lr = currentRoute.getRouteBody().get("boundingBox").get("lr").get("lng").toString() + "," + currentRoute.getRouteBody().get("boundingBox").get("lr").get("lat").toString();
            String ul = currentRoute.getRouteBody().get("boundingBox").get("ul").get("lng").toString() + "," + currentRoute.getRouteBody().get("boundingBox").get("lr").get("lat").toString();
            currentRoute.setBoundingBox("&boundingBox=" + lr + "," + ul);

            return currentRoute;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            log.error(e);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.error(e);
        }
        return null;
    }

    //2. SET IMAGE SETTINGS
    private Route setImageSettingsToRoute(Route currentRoute) {
        currentRoute.setSize("&size=" + currentRoute.getRouteImage().getWidth() + "," + currentRoute.getRouteImage().getHeight());
        currentRoute.setUrlMap(createDownloadURL(currentRoute, true, false));
        return copyRouteDataToImage(currentRoute);
    }

    //2.1 CREATE IMG-URL
    private String createDownloadURL(Route currentRoute, boolean defaultMarker, boolean zoom) {
        String params = currentRoute.getKey() + currentRoute.getSize() + currentRoute.getSessionID();
        if (!defaultMarker) {
            params += currentRoute.getRouteImage().getDefaultMarker();
        }
        if (zoom) {
            params += currentRoute.getRouteImage().getZoom();
        }
        return "http://www.mapquestapi.com/staticmap/v5/map" + params;
    }

    // 2.2 SAVE DATA TO IMAGE
    private Route copyRouteDataToImage(Route currentRoute) {
        String filename = currentRoute.getFrom() + "-" + currentRoute.getTo();
        if (currentTour.getTourID() != null) {
            currentRoute.getRouteImage().setImageID(currentTour.getTourID());
        } else {
            currentRoute.getRouteImage().setImageID(filename);
        }
        currentRoute.getRouteImage().setDownloadURL(currentRoute.getUrlRoute());
        currentRoute.getRouteImage().setFrom(currentRoute.getFrom());
        currentRoute.getRouteImage().setTo(currentRoute.getTo());
        currentRoute.getRouteImage().setFilePath(config.getImage() + currentRoute.getRouteImage().getImageID() + ".jpg");

        return currentRoute;
    }

    // 3 SAVE IMAGE in DATABASE
    private RouteImage saveImageOnline(RouteImage currentRouteImage) {
        return routeImageDao.insert(currentRouteImage);
    }

    // 4 LOAD IMAGE DATA FROM INTERNET
    private byte[] loadRouteImage(String downloadURL) {
        return net.loadImageByLink(downloadURL);
    }

    // 5 UPDATE
    private RouteImage updateImage(RouteImage currentRouteImage) {
        return routeImageDao.update(currentRouteImage);
    }

    // 6. DOWNLOAD IMAGE
    @Override
    public RouteImage downloadImage(Route route) {
        // kann auch IMG runterladen
        //FileAccess fileAccess = new FileAccessImpl();
        //fileAccess.writeFile(route.getRouteImage().getImageID() + ".jpg", currentImage);

        routeImageDao.updateImageData(route.getRouteImage());
        routeImageDao.update(route.getRouteImage());

        return route.getRouteImage();
    }

    // 7. LOAD IMAGE FROM INTERNET AND SAVE DATA IN DATABASE
    private RouteImage saveImageDataOnline(Route route) {
        byte[] currentImage = loadRouteImage(route.getUrlMap());
        route.getRouteImage().setData(currentImage);
        route.getRouteImage().setLocal(false);
        routeImageDao.updateImageData(route.getRouteImage());
        routeImageDao.update(route.getRouteImage());
        return route.getRouteImage();
    }

    // ######################################################### //

    // CREATE IMAGE
    private Image showLocalRouteImage(RouteImage routeImage) {
        FileAccess fileAccess = new FileAccessImpl();
        File file = fileAccess.readFile(routeImage.getImageID() + ".jpg");
        return new Image(file.getAbsolutePath());
    }

    // RETURN IMAGE FROM DATABASE
    @Override
    public Image showOnlineRouteImage(RouteImage routeImage) {
        byte[] image = routeImage.getData();
        return new Image(new ByteArrayInputStream(image));
    }
}
