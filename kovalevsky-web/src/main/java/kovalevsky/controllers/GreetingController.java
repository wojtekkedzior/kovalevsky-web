package kovalevsky.controllers;

import java.awt.Image;
import java.io.IOException;

import javax.validation.Valid;

import kovalevsky.repositories.VisitCounterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GreetingController {

  @Autowired
  private VisitCounterRepository imageRepository;

  @RequestMapping("/greeting")
  public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {

//    Image findById = imageRepository.findById(1);
//    System.err.println(findById + name);

    // Iterable<Image> findAll = imageRepository.findAll();
    // StringBuffer buffer = new StringBuffer();
    // for (Image image : findAll) {
    // buffer.append(image.getId());
    // buffer.append("-");
    // buffer.append(image.getSomeId());
    // buffer.append(", ");
    // }
//    return findById.toString();
    return "";
    // return new Greeting(counter.incrementAndGet(),
    // String.format(template, name));
  }

  @ResponseBody
  @RequestMapping(value = { "/photo" }, method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
  // @RequestMapping(value = { "client/passwordChange" }, method = RequestMethod.PUT, produces = { MediaType.APPLICATION_XML_VALUE,
  // MediaType.APPLICATION_JSON_VALUE })
  public byte[] testphoto() throws IOException {
    // InputStream in = servletContext.getResourceAsStream("/images/no_image.jpg");
    // return IOUtils.toByteArray(in);
    return null;
  }

  @RequestMapping(value = "/person/add", method = RequestMethod.POST)
  public String addPersonFromForm(@Valid Image picture, BindingResult bindingResult, @RequestParam(value = "image", required = false) MultipartFile image) {

    if (!image.isEmpty()) {
      try {
        validateImage(image);

      } catch (RuntimeException re) {
        bindingResult.reject(re.getMessage());
        return "redirect:/person?new";
      }

//      try {
//        saveImage(person.getName() + ".jpg", image);
//      } catch (IOException e) {
//        bindingResult.reject(e.getMessage());
//        return "redirect:/person?new";
//      }
    }

//    getPersonService().addPerson(person);
    return "redirect:/allpersons";
  }

  private void validateImage(MultipartFile image) {
    if (!image.getContentType().equals("image/jpeg")) {
      throw new RuntimeException("Only JPG images are accepted");
    }
  }
}