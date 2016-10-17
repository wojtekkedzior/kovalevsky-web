package kovalevsky.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kovalevsky.imaging.Algorithms;
import kovalevsky.imaging.DataProcessor;
import kovalevsky.imaging.formats.BMPFormat;
import kovalevsky.imaging.formats.ImageFormat;
import kovalevsky.imaging.formats.headers.Header;
import kovalevsky.services.AlgorithmUsageService;
import kovalevsky.services.VisitCounterService;

@Controller
@Scope("session")
public class FileUploadController {

	private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private DataProcessor dataProcessor;

	@Autowired
	private VisitCounterService visitCounterService;

	@Autowired
	private AlgorithmUsageService algorithUsageService;

	private File tempModifedFile;

	private File originalFile;

	private ImageFormat imageFormat;

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String provideUploadInfo(Model model) throws IOException {
		model.addAttribute("selectedAlgorithm", Algorithms.ORIGINAL.getName());
		return "uploadForm";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/image")
	@ResponseBody
	public ResponseEntity<?> getModifiedImage() throws FileNotFoundException {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		if (imageFormat == null) {
			return new ResponseEntity<byte[]>(new byte[0], headers, HttpStatus.CREATED);
		}

		byte[] completeImageAsByteArray = imageFormat.getCompleteImageAsByteArray();

		return new ResponseEntity<byte[]>(completeImageAsByteArray, headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/update")
	@ResponseBody
	public ModelAndView getUdpateRedirect() {
		return new ModelAndView("uploadForm");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/originalImage")
	@ResponseBody
	public ResponseEntity<?> getOriginalImage() throws FileNotFoundException {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);

		if (originalFile == null) {
			return new ResponseEntity<byte[]>(new byte[0], headers, HttpStatus.OK);
		}
		int[] data = read(originalFile);
		BMPFormat imageFormat = new BMPFormat(data);
		byte[] myBytes = imageFormat.getCompleteImageAsByteArray();

		visitCounterService.incrementVisitCounter();

		return new ResponseEntity<byte[]>(myBytes, headers, HttpStatus.CREATED);
	}

	@ModelAttribute("algorithms")
	public List<Algorithms> populateTypes() {
		return Arrays.asList(Algorithms.values());
	}

	@RequestMapping(method = RequestMethod.POST, value = "/update")
	@ResponseBody
	public ModelAndView updateImage(@RequestParam(name = "algorithm") String algorithm,
			@RequestParam(name = "windowSize", defaultValue = "0") String windowSize,
			@RequestParam(name = "sigma", defaultValue = "0") String sigma, RedirectAttributes redirectAttributes,
			Model model) throws IOException {

		if (tempModifedFile == null) {
			return new ModelAndView("uploadForm");
		}

		Algorithms valueOf = Algorithms.fromString(algorithm);

		log.info("update image. algorithm=" + algorithm + ", window size: " + windowSize + ", sigma: " + sigma);

		int wSize = Integer.parseInt(windowSize);
		int sSize = Integer.parseInt(sigma);
		int[] modifiedData = new int[0];

		switch (valueOf) {
		case FAST_AVERAGE:
			modifiedData = dataProcessor.fastAverage(imageFormat.getImageData(), imageFormat.getWidth(), imageFormat.getHeight(), wSize); 
			break;
		case FAST_AVERAGE_BOUNDARY:
			modifiedData = dataProcessor.fastAverageZeroBoundry(imageFormat.getImageData(), imageFormat.getWidth(), imageFormat.getHeight(), wSize); 
			
			Header header = imageFormat.getHeader();

			header.setSize(261178);

			header.setWidth(imageFormat.getWidth() - 2);
			header.setHeight(imageFormat.getHeight() - 2);

			imageFormat.setHeight(510);
			imageFormat.setWidth(510);

			break;
		case FAST_AVERAGE_REFLECTED:
			modifiedData = dataProcessor.fastAverageReflected(imageFormat.getImageData(), imageFormat.getWidth(), imageFormat.getHeight(), wSize);
			break;
		case FAST_AVERAGE_ZERO_PADDED:
			modifiedData = dataProcessor.fastAverageZeroPadded(imageFormat.getImageData(), imageFormat.getWidth(), imageFormat.getHeight(), wSize);
			break;
		case LAPLACIAN:
			modifiedData = dataProcessor.newLaplacian(imageFormat.getImageData(), imageFormat.getWidth(), imageFormat.getHeight());
			break;
		case MEDIAN:
			modifiedData = dataProcessor.median(imageFormat.getImageData(), imageFormat.getWidth(), imageFormat.getHeight(), wSize);
			break;
		case SIGMA:
			modifiedData = dataProcessor.sigma(imageFormat.getImageData(), imageFormat.getWidth(), imageFormat.getHeight(), wSize, sSize);
			break;
		case SOBEL:
			modifiedData = dataProcessor.sobel(imageFormat.getImageData(), imageFormat.getWidth(), imageFormat.getHeight());
			break;
		default:
			imageFormat = new BMPFormat(read(originalFile));
			modifiedData = imageFormat.getImageData();
			break;
		}
		
		imageFormat.setImageData(modifiedData);
		
		algorithUsageService.incrementAlgorithmUsage(valueOf);

		model.addAttribute("selectedAlgorithm", algorithm);
		model.addAttribute("windowSize", windowSize);
		model.addAttribute("sigma", sigma);

		return new ModelAndView("uploadForm");
	}

	@RequestMapping(method = RequestMethod.POST, value = "/")
	@ResponseBody
	public ModelAndView filterImage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
			Model model) throws IOException {

		model.addAttribute("selectedAlgorithm", Algorithms.ORIGINAL.getName());

		if (tempModifedFile == null) {
			tempModifedFile = File.createTempFile("pre", "suff");
			originalFile = File.createTempFile("original_pre", "original_suff");
		}

		if ("".equals(file.getOriginalFilename())) {
			ClassLoader classLoader = getClass().getClassLoader();
			File lenaFile = new File(classLoader.getResource("lena.bmp").getFile());

			Files.copy(lenaFile.toPath(), tempModifedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.copy(tempModifedFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			imageFormat = new BMPFormat(read(lenaFile));
		} else {
			file.transferTo(tempModifedFile);
			Files.copy(tempModifedFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			if (file.getOriginalFilename().endsWith(".bmp")) {
				imageFormat = new BMPFormat(read(tempModifedFile));
			}
		}
		return new ModelAndView("uploadForm");
	}

	private int[] read(File file) throws FileNotFoundException {
		int result = 0;
		int j = 0;

		int[] data = new int[(int) file.length() + 1];

		try (BufferedInputStream read = new BufferedInputStream(new FileInputStream(file))) {
			while (result != -1) {
				result = data[j] = read.read();
				j++;
			}
		} catch (Exception e) {
			System.err.println(e);
		}
		return data;
	}
}