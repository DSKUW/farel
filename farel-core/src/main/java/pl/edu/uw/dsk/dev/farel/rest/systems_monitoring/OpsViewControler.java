package pl.edu.uw.dsk.dev.farel.rest.systems_monitoring;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.edu.uw.dsk.dev.farel.entites.systems_monitoring.HostStatus;
import pl.edu.uw.dsk.dev.farel.information_source.systems_monitoring.OpsViewManager;
import pl.edu.uw.dsk.dev.farel.utils.LoginInfo;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

@Controller
@RequestMapping("/opsview")
public class OpsViewControler {

    private static final String OPSVIEW_BASE_URL = "https://adres.strony/rest/";
    private static final String OPSVIEW_LOGIN = "login";
    private static final String OPSVIEW_PASSWORD = "password";

    private static MongoClient mongoClient;
    static {
        try {
            mongoClient = new MongoClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    private static final DB db = mongoClient.getDB("farel");
    private static final DBCollection coll = db.getCollection("test");

    private ObjectMapper jsonMapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody String generalInfo() throws JsonGenerationException, JsonMappingException,
                    IOException {
        return "General info about Opsview";
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", value = "/{projectId}")
    public @ResponseBody HostStatus projectInfo(@PathVariable("projectId") String id) throws JsonGenerationException, JsonMappingException,
                    IOException {
        HostStatus hostStatus = getExistingRecord(id);
        if (null == hostStatus) {
            hostStatus = getNewRecord(id);
        }
        return hostStatus;
    }

    private HostStatus getExistingRecord(String id) throws JsonParseException, JsonMappingException, IOException {
        DBCursor cursor = coll.find(new BasicDBObject("date", new BasicDBObject("$gt", new Date().getTime() - 60000)));
        if (cursor.hasNext()) {
            BasicDBObject basicDBObject = (BasicDBObject) cursor.next();
            /*JsonNode rootNode = jsonMapper.readValue(basicDBObject.toString(), JsonNode.class);
            JsonNode dateNode = rootNode.get("date");
            System.out.println(dateNode.asLong());*/
            return jsonMapper.readValue(basicDBObject.toString(), HostStatus.class);
        }
        return null;
    }

    private HostStatus getNewRecord(String id) throws JsonGenerationException, JsonMappingException, IOException {
        LoginInfo opsViewLoginInfo = new LoginInfo(OPSVIEW_LOGIN, OPSVIEW_PASSWORD);
        OpsViewManager opsViewManager = new OpsViewManager(OPSVIEW_BASE_URL, opsViewLoginInfo);
        HostStatus hostStatus = opsViewManager.getStatus(id);
        persistToDB(hostStatus);
        return hostStatus;
    }

    private void persistToDB(HostStatus hostStatus) throws JsonGenerationException, JsonMappingException, IOException {
        Object o = JSON.parse(jsonMapper.writeValueAsString(hostStatus));
        BasicDBObject dbObj = (BasicDBObject) o;
        dbObj.append("date", new Date().getTime());
        coll.insert(dbObj);
    }
}
