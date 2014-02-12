package pl.edu.uw.dsk.dev.farel.rest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import pl.edu.uw.dsk.dev.farel.entites.Service;
import pl.edu.uw.dsk.dev.farel.repository.ServiceRepository;

@Controller
@RequestMapping(value = "/services")
public class ServicesController {

    @Autowired
    private ServiceRepository serviceRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Service>> findAll() {
        List<Service> services = serviceRepository.findAll();
        return new ResponseEntity<List<Service>>(services, HttpStatus.OK);
    }

    @RequestMapping(value = "/addall", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> saveAll(@RequestBody List<Service> serviceList,
                    UriComponentsBuilder builder) {
        UriComponents uri = builder.path("/services").build();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.toUri());
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        boolean unique = true;
        for (Service service : serviceList) {
            if (null != readService(service.getName())) {
                unique = false;
            }
        }
        if (unique) {
            for (Service service : serviceList) {
                serviceRepository.save(service);
            }
            httpStatus = HttpStatus.CREATED;
        }
        return new ResponseEntity<Void>(headers, httpStatus);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deleteAll() {
        serviceRepository.deleteAll();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{serviceName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Service readService(@PathVariable("serviceName") String name) {
        return serviceRepository.findOneServiceByName(name);
    }

    @RequestMapping(value = "/{serviceName}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deleteProject(@PathVariable("serviceName") String name) {
        serviceRepository.delete(serviceRepository.findOneServiceByName(name));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{serviceName}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> updateProjectData(@PathVariable("serviceName") String serviceName,
                    @RequestBody Service service, UriComponentsBuilder builder) {
        if (null != readService(service.getName())) {
            serviceRepository.save(service);
            UriComponents uri = builder.path("/services/" + service.getName()).build();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uri.toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> submitProject(@RequestBody Service service,
                    UriComponentsBuilder builder) {
        if (null == readService(service.getName())) {
            serviceRepository.save(service);
            UriComponents uri = builder.path("/services/" + service.getName()).build();
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uri.toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
    }
}
